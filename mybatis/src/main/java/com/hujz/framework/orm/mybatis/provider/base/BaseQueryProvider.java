package com.hujz.framework.orm.mybatis.provider.base;

import java.util.LinkedList;
import java.util.List;

import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.scripting.xmltags.MixedSqlNode;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.scripting.xmltags.StaticTextSqlNode;
import org.apache.ibatis.scripting.xmltags.WhereSqlNode;

import com.hujz.framework.orm.mybatis.mapperhelper.EntityHelper;
import com.hujz.framework.orm.mybatis.mapperhelper.MapperHelper;
import com.hujz.framework.orm.mybatis.mapperhelper.MapperTemplate;

public class BaseQueryProvider extends MapperTemplate {

    public BaseQueryProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    /**
     * 根据主键进行查询
     *
     * @param ms
     */
    public void get(MappedStatement ms) {
        final Class<?> entityClass = getSelectReturnType(ms);
        //获取主键字段映射
        List<ParameterMapping> parameterMappings = getPrimaryKeyParameterMappings(ms);
        //开始拼sql
        String sql = new SQL() {{
            //select全部列
            SELECT(EntityHelper.getSelectColumns(entityClass));
            //from表
            FROM(tableName(entityClass));
            //where条件，主键字段=#{property}
            WHERE(EntityHelper.getPrimaryKeyWhere(entityClass));
        }}.toString();
        //使用静态SqlSource
        StaticSqlSource sqlSource = new StaticSqlSource(ms.getConfiguration(), sql, parameterMappings);
        //替换原有的SqlSource
        setSqlSource(ms, sqlSource);
        //将返回值修改为实体类型
        setResultType(ms, entityClass);
    }
    
    /**
     * 查询
     *
     * @param ms
     * @return
     */
    public SqlNode queryOneByEntity(MappedStatement ms) {
        Class<?> entityClass = getSelectReturnType(ms);
        //修改返回值类型为实体类型
        setResultType(ms, entityClass);
        List<SqlNode> sqlNodes = new LinkedList<SqlNode>();
        //静态的sql部分:select column ... from table
        sqlNodes.add(new StaticTextSqlNode("SELECT "
                + EntityHelper.getSelectColumns(entityClass)
                + " FROM "
                + tableName(entityClass)));
        //将if添加到<where>
        sqlNodes.add(new WhereSqlNode(ms.getConfiguration(), getAllIfColumnNode(entityClass)));
        return new MixedSqlNode(sqlNodes);
    }
    
    /**
     * 查询
     *
     * @param ms
     * @return
     */
    public SqlNode queryByEntity(MappedStatement ms) {
        Class<?> entityClass = getSelectReturnType(ms);
        //修改返回值类型为实体类型
        setResultType(ms, entityClass);
        List<SqlNode> sqlNodes = new LinkedList<SqlNode>();
        //静态的sql部分:select column ... from table
        sqlNodes.add(new StaticTextSqlNode("SELECT "
                + EntityHelper.getSelectColumns(entityClass)
                + " FROM "
                + tableName(entityClass)));
        //将if添加到<where>
        sqlNodes.add(new WhereSqlNode(ms.getConfiguration(), getAllIfColumnNode(entityClass)));
        String orderByClause = EntityHelper.getOrderByClause(entityClass);
        if (orderByClause.length() > 0) {
            sqlNodes.add(new StaticTextSqlNode("ORDER BY " + orderByClause));
        }
        return new MixedSqlNode(sqlNodes);
    }
    
    /**
     * 查询总数
     *
     * @param ms
     * @return
     */
    public SqlNode queryCountByEntity(MappedStatement ms) {
        Class<?> entityClass = getSelectReturnType(ms);
        List<SqlNode> sqlNodes = new LinkedList<SqlNode>();
        //select count(*) from table
        sqlNodes.add(new StaticTextSqlNode("SELECT COUNT(*) FROM " + tableName(entityClass)));
        //获取全部列的where,if条件
        sqlNodes.add(new WhereSqlNode(ms.getConfiguration(), getAllIfColumnNode(entityClass)));
        return new MixedSqlNode(sqlNodes);
    }
}
