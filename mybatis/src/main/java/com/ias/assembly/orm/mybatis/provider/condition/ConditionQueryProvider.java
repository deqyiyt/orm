package com.ias.assembly.orm.mybatis.provider.condition;

import java.util.Iterator;
import java.util.Set;

import org.apache.ibatis.mapping.MappedStatement;

import com.ias.assembly.orm.mybatis.mapperhelper.MapperTemplate;

import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.mapperhelper.EntityHelper;
import tk.mybatis.mapper.mapperhelper.MapperHelper;

public class ConditionQueryProvider extends MapperTemplate {

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
       Class<?> entityClass = getEntityClass(ms);
       setResultType(ms, entityClass);
       StringBuilder sql = new StringBuilder();
           sql.append(selectAllColumns(entityClass));
           sql.append(fromTable(entityClass, tableName(entityClass)))
           .append(resolveQueryConditionToSql(entityClass.getName()));
        return sql.toString();
    }
    
    /**
     * 根据QueryCondition查询总条数
     *
     * @param ms
     * @return
     */
    public String queryCount(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        StringBuilder sql = new StringBuilder()
            .append(selectCount(entityClass))
            .append(fromTable(entityClass, tableName(entityClass)))
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
        Class<?> entityClass = getEntityClass(ms);
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
