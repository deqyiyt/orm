package com.hujz.framework.orm.mybatis.provider.base;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.scripting.xmltags.MixedSqlNode;
import org.apache.ibatis.scripting.xmltags.SetSqlNode;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.scripting.xmltags.StaticTextSqlNode;
import org.apache.ibatis.scripting.xmltags.VarDeclSqlNode;
import org.apache.ibatis.scripting.xmltags.WhereSqlNode;

import com.hujz.framework.orm.mybatis.entity.EntityColumn;
import com.hujz.framework.orm.mybatis.mapperhelper.EntityHelper;
import com.hujz.framework.orm.mybatis.mapperhelper.MapperHelper;
import com.hujz.framework.orm.mybatis.mapperhelper.MapperTemplate;

public class BaseUpdateProvider extends MapperTemplate {

    public BaseUpdateProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    /**
     * 通过主键更新全部字段
     *
     * @param ms
     */
    public SqlNode update(MappedStatement ms) {
        Class<?> entityClass = getSelectReturnType(ms);
        List<SqlNode> sqlNodes = new LinkedList<SqlNode>();
        //update table
        sqlNodes.add(new StaticTextSqlNode("UPDATE " + tableName(entityClass)));
        //获取全部列
        Set<EntityColumn> columnList = EntityHelper.getColumns(entityClass);
        List<SqlNode> ifNodes = new LinkedList<SqlNode>();
        for (EntityColumn column : columnList) {
        	 if(column.isUpdateTime()) {
         		//设置修改时间
        		 sqlNodes.add(new VarDeclSqlNode(column.getProperty() + "_update", getNow()));
        		 ifNodes.add(getIfIsNull(column, new StaticTextSqlNode(column.getColumn() + " = #{" + column.getProperty() + "_update}, ")));
             } else if (!column.isId()) {
            	 ifNodes.add(new StaticTextSqlNode(column.getColumn() + " = #{" + column.getProperty() + "}, "));
        	 }
            
        }
        sqlNodes.add(new SetSqlNode(ms.getConfiguration(), new MixedSqlNode(ifNodes)));
        //获取全部的主键的列
        columnList = EntityHelper.getPKColumns(entityClass);
        List<SqlNode> whereNodes = new LinkedList<SqlNode>();
        boolean first = true;
        //where 主键=#{property} 条件
        for (EntityColumn column : columnList) {
            whereNodes.add(getColumnEqualsProperty(column, first));
            first = false;
        }
        sqlNodes.add(new WhereSqlNode(ms.getConfiguration(), new MixedSqlNode(whereNodes)));
        return new MixedSqlNode(sqlNodes);
    }
}