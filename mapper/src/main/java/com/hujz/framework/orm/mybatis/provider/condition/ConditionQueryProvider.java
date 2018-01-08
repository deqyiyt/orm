package com.hujz.framework.orm.mybatis.provider.condition;

import java.util.Iterator;
import java.util.Set;

import org.apache.ibatis.mapping.MappedStatement;

import com.hujz.framework.orm.mybatis.entity.EntityColumn;
import com.hujz.framework.orm.mybatis.mapperhelper.EntityHelper;
import com.hujz.framework.orm.mybatis.mapperhelper.MapperHelper;
import com.hujz.framework.orm.mybatis.mapperhelper.MapperSqlTemplate;

public class ConditionQueryProvider extends MapperSqlTemplate {

    public ConditionQueryProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
    	super(mapperClass, mapperHelper);
	}
    
    /**
     * 根据QueryCondition查询
     *
     * @param ms
     * @return
     */
   public String query(MappedStatement ms) {
    	 final Class<?> entityClass = getSelectReturnType(ms);
         //修改返回值类型为实体类型
         setResultType(ms, entityClass);
         //开始拼sql
         StringBuilder sql = new StringBuilder("")
        	.append("<if test=\"beforeSql != null\">")
        	.append("${beforeSql} ")
        	.append("</if>")
        	.append("<if test=\"beforeSql == null\">")
        	.append("select ")
         	.append(EntityHelper.getSelectColumns(entityClass))
         	.append(" from ")
         	.append(tableName(entityClass))
         	.append("</if>")
     		.append(resolveQueryConditionToSql(entityClass.getName()));
        return sql.toString();
    }
    
    /**
     * 查询全部结果
     *
     * @param ms
     * @return
     */
    public String queryAll(MappedStatement ms) {
        final Class<?> entityClass = getSelectReturnType(ms);
        //修改返回值类型为实体类型
        setResultType(ms, entityClass);
        //开始拼sql
        StringBuilder sql = new StringBuilder("select ")
        	.append(EntityHelper.getSelectColumns(entityClass))
        	.append(" from ")
        	.append(tableName(entityClass));
        return sql.toString();
    }
    
    /**
     * 根据QueryCondition查询总条数
     *
     * @param ms
     * @return
     */
    public String queryCount(MappedStatement ms) {
    	final Class<?> entityClass = getSelectReturnType(ms);
        //开始拼sql
        StringBuilder sql = new StringBuilder("select count(1) from ")
        .append(tableName(entityClass))
        .append(resolveQueryConditionToSql(entityClass.getName()));
        
        return sql.toString();
    }
    
    /**
     * 通过条件查询实体是否存在
     *
     * @param ms
     * @return
     */
    public String isUnique(MappedStatement ms) {
    	final Class<?> entityClass = getSelectReturnType(ms);
    	Set<EntityColumn> entityColumns = EntityHelper.getPKColumns(entityClass);
    	Iterator<EntityColumn> it = entityColumns.iterator();
        //开始拼sql
        StringBuilder sql = new StringBuilder("select exists(select ")
        	.append(it.next().getColumn())
        	.append(" from ")
        	.append(tableName(entityClass))
	        .append(resolveQueryConditionToSql(entityClass.getName()))
	        .append(")");
        
        return sql.toString();
    }
}
