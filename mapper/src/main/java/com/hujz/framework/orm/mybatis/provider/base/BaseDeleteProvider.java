package com.hujz.framework.orm.mybatis.provider.base;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;

import com.hujz.framework.orm.mybatis.entity.EntityColumn;
import com.hujz.framework.orm.mybatis.mapperhelper.EntityHelper;
import com.hujz.framework.orm.mybatis.mapperhelper.MapperHelper;
import com.hujz.framework.orm.mybatis.mapperhelper.MapperTemplate;

public class BaseDeleteProvider extends MapperTemplate {

    public BaseDeleteProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    /**
     * 通过主键删除
     *
     * @param ms
     */
    public void delete(MappedStatement ms) {
        final Class<?> entityClass = getSelectReturnType(ms);
        List<ParameterMapping> parameterMappings = getPrimaryKeyParameterMappings(ms);
        //开始拼sql
        String sql = new SQL() {{
            //delete from table
            DELETE_FROM(tableName(entityClass));
            //where 主键=#{property} 条件
            WHERE(EntityHelper.getPrimaryKeyWhere(entityClass));
        }}.toString();
        //静态SqlSource
        StaticSqlSource sqlSource = new StaticSqlSource(ms.getConfiguration(), sql, parameterMappings);
        //替换原有的SqlSource
        setSqlSource(ms, sqlSource);
    }
    
    /**
     * 通过主键集合删除
     *
     * @param ms
     */
    public String deleteAll(MappedStatement ms) {
    	final Class<?> entityClass = getSelectReturnType(ms);
    	Set<EntityColumn> entityColumns = EntityHelper.getPKColumns(entityClass);
        //开始拼sql
        StringBuilder sql = new StringBuilder()
        	.append("delete from ")
        	.append(tableName(entityClass))
        	.append(" where 1 = 1");
        for (EntityColumn column : entityColumns) {
        	sql.append(" and ")
        	.append(column.getColumn())
        	.append(" in (")
        	.append(" <foreach collection=\"array\" index=\"index\" item=\"item\" separator=\",\" close=\"\">")
        	.append(" #{item}")
        	.append(" </foreach>")
        	.append(" )");
        }
        
        return sql.toString();
    }
}
