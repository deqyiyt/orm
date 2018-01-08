package com.hujz.framework.orm.mybatis.provider.condition;

import org.apache.ibatis.mapping.MappedStatement;

import com.hujz.framework.orm.mybatis.mapperhelper.MapperHelper;
import com.hujz.framework.orm.mybatis.mapperhelper.MapperSqlTemplate;

public class ConditionDeleteProvider extends MapperSqlTemplate {

    public ConditionDeleteProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
    	super(mapperClass, mapperHelper);
	}
    
    /**
     * 根据QueryCondition删除
     *
     * @param ms
     * @return
     */
    public String batchDelete(MappedStatement ms) {
    	final Class<?> entityClass = getSelectReturnType(ms);
        //开始拼sql
        StringBuilder sql = new StringBuilder("delete from ")
	        .append(tableName(entityClass))
	        .append(resolveQueryConditionToSql(entityClass.getName()));
        
        return sql.toString();
    }
}
