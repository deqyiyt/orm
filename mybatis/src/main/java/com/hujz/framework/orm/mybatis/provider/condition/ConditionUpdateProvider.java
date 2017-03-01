package com.hujz.framework.orm.mybatis.provider.condition;

import org.apache.ibatis.mapping.MappedStatement;

import com.hujz.framework.orm.mybatis.mapperhelper.EntityHelper;
import com.hujz.framework.orm.mybatis.mapperhelper.MapperHelper;
import com.hujz.framework.orm.mybatis.mapperhelper.MapperSqlTemplate;

public class ConditionUpdateProvider extends MapperSqlTemplate {

    public ConditionUpdateProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
    	super(mapperClass, mapperHelper);
	}
    
    /**
     * 根据QueryCondition修改
     *
     * @param ms
     * @return
     */
    public String batchUpdate(MappedStatement ms) {
    	final Class<?> entityClass = getSelectReturnType(ms);
    	String field = "${@"+EntityHelper.class.getName()+"@getColumnByProperty(\""+ entityClass.getName() + "\",%s)}";
        //开始拼sql
        StringBuilder sql = new StringBuilder("update ")
	        .append(tableName(entityClass))
	        .append(" set ")
	        .append(" <foreach collection=\"batchUpdateMap\" index=\"key\"  item=\"value\" separator=\",\" close=\"\">")
	        .append(" ").append(String.format(field,"key")).append(" = #{value} ")
			.append(" </foreach> ")
	        .append(resolveQueryConditionToSql(entityClass.getName()));
        
        return sql.toString();
    }
}
