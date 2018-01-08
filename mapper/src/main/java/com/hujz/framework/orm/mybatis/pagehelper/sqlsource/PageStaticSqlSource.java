package com.hujz.framework.orm.mybatis.pagehelper.sqlsource;

import com.hujz.framework.orm.mybatis.pagehelper.PageHelper;
import com.hujz.framework.orm.mybatis.pagehelper.parser.OrderByParser;
import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.Configuration;

import java.util.List;

/**
 * 支持orderby和分页
 *
 * @author liuzh
 * @since 2015-06-27
 */
public class PageStaticSqlSource extends PageSqlSource {
    private String sql;
    private List<ParameterMapping> parameterMappings;
    private Configuration configuration;
    private SqlSource original;

    @SuppressWarnings("unchecked")
    public PageStaticSqlSource(StaticSqlSource sqlSource) {
        MetaObject metaObject = SystemMetaObject.forObject(sqlSource);
        this.sql = (String) metaObject.getValue("sql");
        this.parameterMappings = (List<ParameterMapping>) metaObject.getValue("parameterMappings");
        this.configuration = (Configuration) metaObject.getValue("configuration");
        this.original = sqlSource;
    }

    @Override
    protected BoundSql getDefaultBoundSql(Object parameterObject) {
        String tempSql = sql;
        String orderBy = PageHelper.getOrderBy();
        if (orderBy != null) {
            tempSql = OrderByParser.converToOrderBySql(sql, orderBy);
        }
        return new BoundSql(configuration, tempSql, parameterMappings, parameterObject);
    }

    @Override
    protected BoundSql getCountBoundSql(Object parameterObject) {
        return new BoundSql(configuration, localParser.get().getCountSql(sql), parameterMappings, parameterObject);
    }

    @Override
    protected BoundSql getPageBoundSql(Object parameterObject) {
        String tempSql = sql;
        String orderBy = PageHelper.getOrderBy();
        if (orderBy != null) {
            tempSql = OrderByParser.converToOrderBySql(sql, orderBy);
        }
        tempSql = localParser.get().getPageSql(tempSql);
        return new BoundSql(configuration, tempSql, localParser.get().getPageParameterMapping(configuration, original.getBoundSql(parameterObject)), parameterObject);
    }

}
