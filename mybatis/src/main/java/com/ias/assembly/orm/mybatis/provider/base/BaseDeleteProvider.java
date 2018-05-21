package com.ias.assembly.orm.mybatis.provider.base;

import java.util.Set;

import org.apache.ibatis.mapping.MappedStatement;

import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.mapperhelper.EntityHelper;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import com.ias.assembly.orm.mybatis.mapperhelper.MapperTemplate;

public class BaseDeleteProvider extends MapperTemplate {

    public BaseDeleteProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    /**
     * 通过主键删除
     *
     * @param ms
     */
    public String delete(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        sql.append(deleteFromTable(entityClass, tableName(entityClass)));
        sql.append(wherePKColumns(entityClass));
        return sql.toString();
    }
    
    /**
     * 通过主键集合删除
     *
     * @param ms
     */
    public String deleteAll(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
    	Set<EntityColumn> entityColumns = EntityHelper.getPKColumns(entityClass);
        //开始拼sql
    	StringBuilder sql = new StringBuilder();
        sql.append(deleteFromTable(entityClass, tableName(entityClass)));
        sql.append("<where>");
        for (EntityColumn column : entityColumns) {
        	sql.append(" and ")
        	.append(column.getColumn())
        	.append(" in (")
        	.append(" <foreach collection=\"array\" index=\"index\" item=\"item\" separator=\",\" close=\"\">")
        	.append(" #{item}")
        	.append(" </foreach>")
        	.append(" )");
        }
        sql.append("</where>");
        
        return sql.toString();
    }
}
