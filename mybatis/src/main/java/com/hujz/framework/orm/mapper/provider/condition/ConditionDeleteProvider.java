package com.hujz.framework.orm.mapper.provider.condition;

import org.apache.ibatis.mapping.MappedStatement;

import com.hujz.framework.orm.mapper.mapperhelper.MapperTemplate;

import tk.mybatis.mapper.mapperhelper.MapperHelper;

public class ConditionDeleteProvider extends MapperTemplate {

    public ConditionDeleteProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
    	super(mapperClass, mapperHelper);
	}
    
    /**
     * 根据QueryCondition删除
     * @param ms
     * @return
     */
    public String batchDelete(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        //开始拼sql
        StringBuilder sql = new StringBuilder()
            .append(deleteFromTable(entityClass, tableName(entityClass)))
	        .append(resolveQueryConditionToSql(entityClass.getName()));
        return sql.toString();
    }
}
