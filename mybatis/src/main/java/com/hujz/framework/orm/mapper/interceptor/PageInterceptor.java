/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2017 abel533@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.hujz.framework.orm.mapper.interceptor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import com.hujz.framework.orm.bean.PageTools;
import com.hujz.framework.orm.mapper.parser.SqlParser;
import com.hujz.framework.orm.mapper.parser.SqlServer;
import com.hujz.framework.orm.util.ObjectUtils;
import com.hujz.framework.orm.util.QueryCondition;

/**
 * QueryInterceptor 规范
 *
 * 详细说明见文档：https://github.com/pagehelper/Mybatis-PageHelper/blob/master/wikis/zh/Interceptor.md
 *
 * @author liuzh/abel533/isea533
 * @version 1.0.0
 */
@Intercepts(
    {
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})
    }
)
public class PageInterceptor implements Interceptor {
    
    private static final SqlServer sqlServer = new SqlServer();
    
    private SqlParser parser = new SqlParser();

    private String databaseType;// 数据库类型，不同的数据库有不同的分页方法
    private String regEx;// mapper.xml中需要拦截的ID(正则匹配)

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];
        Object parameter = args[1];
        PageTools pageTools = proccessPage(parameter, ms);
        if (pageTools != null && pageTools.getPageSize() > 0) {
            RowBounds rowBounds = (RowBounds) args[2];
            ResultHandler resultHandler = (ResultHandler) args[3];
            Executor executor = (Executor) invocation.getTarget();
            CacheKey cacheKey;
            BoundSql boundSql;
            //由于逻辑关系，只会进入一次
            if(args.length == 4){
                //4 个参数时
                boundSql = ms.getBoundSql(parameter);
                cacheKey = executor.createCacheKey(ms, parameter, rowBounds, boundSql);
            } else {
                //6 个参数时
                cacheKey = (CacheKey) args[4];
                boundSql = (BoundSql) args[5];
            }
        
            // 拦截到的prepare方法参数是一个Connection对象
            Connection connection = executor.getTransaction().getConnection();
            // 获取当前要执行的Sql语句，也就是我们直接在Mapper映射语句中写的Sql语句
            String sql = boundSql.getSql();
            // 给当前的page参数对象设置总记录数
            if (pageTools.isQueryTotalCount()) {
                this.setTotalRecord(pageTools, boundSql, ms, connection);
            }
            // 获取分页Sql语句
            String pageSql = this.getPageSql(pageTools, sql);
            // 利用反射设置当前BoundSql对应的sql属性为我们建立好的分页Sql语句
            ObjectUtils.setFieldValue(boundSql, "sql", pageSql);
            return executor.query(ms, parameter, rowBounds, resultHandler, cacheKey, boundSql);
        }
        //注：下面的方法可以根据自己的逻辑调用多次，在分页插件中，count 和 page 各调用了一次
        return invocation.proceed();
    }
    
    /**
     * 给当前的参数对象page设置总记录数
     * 
     * @throws SQLException
     */
    private void setTotalRecord(PageTools pageTools, BoundSql boundSql, final MappedStatement mappedStatement,
            final Connection connection) throws SQLException {
        // 拿到当前绑定Sql的参数对象，就是我们在调用对应的Mapper映射语句时所传入的参数对象
        // 分页SQL<select>中parameterType属性对应的实体参数，即Mapper接口中执行分页方法的参数,该参数不得为空
        Object parameterObject = boundSql.getParameterObject();
        // 获取到我们自己写在Mapper映射语句中对应的Sql语句
        String sql = boundSql.getSql();
        // 通过查询Sql语句获取到对应的计算总记录数的sql语句
        // String countSql = this.getCountSql(sql);
        String countSql = parser.getSmartCountSql(sql);
        // 通过connection建立一个countSql对应的PreparedStatement对象。
        PreparedStatement countStmt = connection.prepareStatement(countSql);
        DefaultParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, parameterObject,
                boundSql);
        parameterHandler.setParameters(countStmt);

        // 之后就是执行获取总记录数的Sql语句和获取结果了
        ResultSet rs = countStmt.executeQuery();
        int count = 0;
        if (rs.next()) {
            count = rs.getInt(1);
        }
        rs.close();
        countStmt.close();

        pageTools.setRecordCount(count);
    }
    
    /**
     * 根据page对象获取对应的分页查询Sql语句，这里只做了两种数据库类型，Mysql和Oracle 其它的数据库都 没有进行分页
     *
     * @param page
     *            分页对象
     * @param sql
     *            原sql语句
     * @return
     */
    private String getPageSql(final PageTools page, final String sql) {
        StringBuffer sqlBuffer = new StringBuffer(sql);
        if ("mysql".equalsIgnoreCase(databaseType)) {
            return getMysqlPageSql(page, sqlBuffer);
        } else if ("oracle".equalsIgnoreCase(databaseType)) {
            return getOraclePageSql(page, sqlBuffer);
        } else if ("sqlserver".equalsIgnoreCase(databaseType)) {
            return getSqlServerPageSql(page, sqlBuffer);
        }
        return sqlBuffer.toString();
    }
    
    /**
     * 获取Mysql数据库的分页查询语句
     * 
     * @param page
     *            分页对象
     * @param sqlBuffer
     *            包含原sql语句的StringBuffer对象
     * @return Mysql数据库分页语句
     */
    private String getMysqlPageSql(PageTools page, StringBuffer sqlBuffer) {
        // 计算第一条记录的位置，Mysql中记录的位置是从0开始的。
        int offset = (page.getPageNo() - 1) * page.getPageSize();
        sqlBuffer.append(" limit ").append(offset).append(",").append(page.getPageSize());
        return sqlBuffer.toString();
    }

    /**
     * 获取Oracle数据库的分页查询语句
     * 
     * @param page
     *            分页对象
     * @param sqlBuffer
     *            包含原sql语句的StringBuffer对象
     * @return Oracle数据库的分页查询语句
     */
    private String getOraclePageSql(final PageTools page, final StringBuffer sqlBuffer) {
        // 计算第一条记录的位置，Oracle分页是通过rownum进行的，而rownum是从1开始的
        int offset = (page.getPageNo() - 1) * page.getPageSize() + 1;
        sqlBuffer.insert(0, "select u.*, rownum r from (").append(") u where rownum < ")
                .append(offset + page.getPageSize());
        sqlBuffer.insert(0, "select * from (").append(") where r >= ").append(offset);

        return sqlBuffer.toString();
    }

    /**
     * 获取Mysql数据库的分页查询语句
     * 
     * @param page
     *            分页对象
     * @param sqlBuffer
     *            包含原sql语句的StringBuffer对象
     * @return Mysql数据库分页语句
     */
    private String getSqlServerPageSql(PageTools page, StringBuffer sqlBuffer) {
        // 计算第一条记录的位置，Mysql中记录的位置是从0开始的。
        int offset = (page.getPageNo() - 1) * page.getPageSize();
        return sqlServer.convertToPageSql(sqlBuffer.toString(), offset, page.getPageSize());
    }

    
    // 是否需要分页
    @SuppressWarnings("unchecked")
    public PageTools proccessPage(Object obj, MappedStatement mappedStatement) {
        if (obj instanceof QueryCondition) {
            QueryCondition qc = (QueryCondition) obj;
            return qc != null ? qc.getPageTools() : null;
        }

        if (mappedStatement.getId().matches(regEx)) {
            if (obj instanceof PageTools) { // 参数就是Page实体
                return (PageTools) obj;
            }
            if (obj instanceof Map) {
                PageTools pageTools = null;
                Map<Object, Object> params = (Map<Object, Object>) obj;
                for (Object key : params.keySet()) {
                    if (params.get(key) instanceof PageTools) {
                        pageTools = (PageTools) params.get(key);
                        break;
                    }
                }
                return pageTools;
            }
        }

        return null;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }
    
    /**
     * 设定databaseType的值.
     * 
     * @param String
     */
    public void setDatabaseType(String databaseType) {
        this.databaseType = databaseType;
    }

    /**
     * 设定regEx的值.
     * 
     * @param String
     */
    public void setRegEx(String regEx) {
        this.regEx = regEx;
    }

}
