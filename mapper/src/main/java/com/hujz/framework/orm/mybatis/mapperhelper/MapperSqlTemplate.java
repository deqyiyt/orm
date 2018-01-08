/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 abel533@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.hujz.framework.orm.mybatis.mapperhelper;

import java.util.List;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.apache.ibatis.scripting.xmltags.IfSqlNode;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.scripting.xmltags.StaticTextSqlNode;
import org.apache.ibatis.scripting.xmltags.TextSqlNode;

import com.hujz.framework.orm.mybatis.util.StringUtil;


/**
 * 通用Mapper模板类，扩展通用Mapper时需要继承该类
 *
 * @author liuzh
 */
public abstract class MapperSqlTemplate extends MapperTemplate{

    public MapperSqlTemplate(Class<?> mapperClass, MapperHelper mapperHelper) {
		super(mapperClass, mapperHelper);
	}
    
    public String resolveQueryConditionToSql(String tableName) {
    	String field = "${@"+EntityHelper.class.getName()+"@getColumnByProperty(\""+ tableName + "\",%s)}";
    	String toBetween = "${@"+StringUtil.class.getName()+"@toBetween(%s)}";
    	StringBuilder sql = new StringBuilder()
    		.append("<where>")
	    	.append(" <if test=\"likeEqualsMap != null\">")
			.append(" <foreach collection=\"likeEqualsMap\" index=\"key\"  item=\"value\" separator=\" and \" open=\" and \" close=\"\"> ")
			.append(" ").append(String.format(field,"key")).append(" like CONCAT('%',#{value},'%') ")
//			.append(" ").append(String.format(field,"key")).append(" like '%${value}%'")
			.append(" </foreach> ")
			.append(" </if> ")
			.append(" <if test=\"equalsMap != null\"> ")
			.append(" <foreach collection=\"equalsMap\" index=\"key\"  item=\"value\" separator=\" and \" open=\" and \" close=\"\"> ")
			.append("  ").append(String.format(field,"key")).append(" = #{value} ")
			.append(" </foreach> ")
			.append(" </if> ")
			.append(" <if test=\"notEqualsMap != null\"> ")
			.append(" <foreach collection=\"notEqualsMap\" index=\"key\"  item=\"value\" separator=\" and \" open=\" and \" close=\"\"> ")
			.append(" ").append(String.format(field,"key")).append(" != #{value} ")
			.append(" </foreach> ")
			.append(" </if> ")
			.append(" <if test=\"greateMap != null\"> ")
			.append(" <foreach collection=\"greateMap\" index=\"key\"  item=\"value\" separator=\" and \" open=\" and \" close=\"\"> ")
			.append(" ").append(String.format(field,"key")).append(" &gt; #{value} ")
			.append(" </foreach> ")
			.append(" </if> ")
			.append(" <if test=\"greateEqualsMap != null\"> ")
			.append(" <foreach collection=\"greateEqualsMap\" index=\"key\"  item=\"value\" separator=\" and \" open=\" and \" close=\"\"> ")
			.append(" ").append(String.format(field,"key")).append(" &gt;= #{value} ")
			.append(" </foreach> ")
			.append(" </if> ")
			.append(" <if test=\"lessMap != null\"> ")
			.append(" <foreach collection=\"lessMap\" index=\"key\"  item=\"value\" separator=\" and \" open=\" and \" close=\"\"> ")
			.append(" ").append(String.format(field,"key")).append(" &lt; #{value} ")
			.append(" </foreach> ")
			.append(" </if> ")
			.append(" <if test=\"lessEqualsMap != null\"> ")
			.append(" <foreach collection=\"lessEqualsMap\" index=\"key\"  item=\"value\" separator=\" and \" open=\" and \" close=\"\"> ")
			.append(" ").append(String.format(field,"key")).append(" &lt;= #{value} ")
			.append(" </foreach> ")
			.append(" </if> ")
			.append(" <if test=\"nullMap != null\"> ")
			.append(" <foreach collection=\"nullMap\" index=\"key\"  item=\"value\" separator=\" and \" open=\" and \" close=\"\"> ")
			.append(" (")
			.append(" ").append(String.format(field,"key")).append(" is null ")
			.append(" or ").append(String.format(field,"key")).append(" = '' ")
			.append(" )")
			.append(" </foreach> ")
			.append(" </if> ")
			.append(" <if test=\"notNullMap != null\"> ")
			.append(" <foreach collection=\"notNullMap\" index=\"key\"  item=\"value\" separator=\" and \" open=\" and \" close=\"\"> ")
			.append(" (")
            .append(" ").append(String.format(field,"key")).append(" is not null ")
            .append(" and ").append(String.format(field,"key")).append(" != '' ")
            .append(" )")
			.append(" </foreach> ")
			.append(" </if> ")
			.append(" <if test=\"inMap != null\"> ")
			.append(" <foreach collection=\"inMap\" index=\"key\"  item=\"value\" separator=\" and \" open=\" and (\" close=\")\"> ")
			.append(" <foreach collection=\"value\" item=\"itemin\" index=\"index\" separator=\" or \" open=\"(\" close=\")\"> ")
			.append(" ").append(String.format(field,"key")).append(" = #{itemin} ")
			.append(" </foreach> ")
			.append(" </foreach> ")
			.append(" </if> ")
			.append(" <if test=\"notInMap != null\"> ")
			.append(" <foreach collection=\"notInMap\" index=\"key\"  item=\"value\" separator=\" and \" open=\" and (\" close=\")\"> ")
			.append(" <foreach collection=\"value\" item=\"itemin\" index=\"index\" separator=\" and \" close=\"\"> ")
			.append(" ").append(String.format(field,"key")).append(" != #{itemin} ")
			.append(" </foreach> ")
			.append(" </foreach> ")
			.append(" </if> ")
			
			.append(" <if test=\"betweenInMap != null\"> ")
			.append(" <foreach collection=\"betweenInMap\" index=\"key\"  item=\"value\" separator=\" and \" open=\" and \" close=\"\"> ")
			.append(" ").append(String.format(field,"key")).append(" between ").append(String.format(toBetween,"value"))
			.append(" </foreach> ")
			.append(" </if> ")
			
			.append("</where>")
			.append(" <if test=\"orderby != null\"> ")
			.append(" order by ")
			.append(" <foreach collection=\"orderby\" item=\"item\" separator=\",\" close=\"\"> ")
			.append(" ").append(String.format(field,"item.key")).append(" ")
			.append(" <if test=\"item.order == 1\"> ")
			.append(" asc ")
			.append(" </if> ")
			.append(" <if test=\"item.order == -1\"> ")
			.append(" desc ")
			.append(" </if> ")
			.append(" </foreach> ")
			.append(" </if> ");
    	return sql.toString();
    }
    
    
    public void resolveQueryConditionToSql(MappedStatement ms, List<SqlNode> ifNodes) {
    	ifNodes.add(new IfSqlNode(new StaticTextSqlNode(" and "), "likeEqualsMap != null"));
        ifNodes.add(new IfSqlNode(new ForEachSqlNode(ms.getConfiguration(), new TextSqlNode(" ${key} like CONCAT('%',#{value},'%') "), "likeEqualsMap", "key", "value", "", "", "and"), "likeEqualsMap != null"));
        
        ifNodes.add(new IfSqlNode(new StaticTextSqlNode(" and "), "equalsMap != null"));
        ifNodes.add(new IfSqlNode(new ForEachSqlNode(ms.getConfiguration(), new TextSqlNode(" ${key} = #{value} "), "equalsMap", "key", "value", "", "", "and"), "equalsMap != null"));
        
        ifNodes.add(new IfSqlNode(new StaticTextSqlNode(" and "), "notEqualsMap != null"));
        ifNodes.add(new IfSqlNode(new ForEachSqlNode(ms.getConfiguration(), new TextSqlNode(" ${key} != #{value} "), "notEqualsMap", "key", "value", "", "", "and"), "notEqualsMap != null"));
        
        ifNodes.add(new IfSqlNode(new StaticTextSqlNode(" and "), "greateMap != null"));
        ifNodes.add(new IfSqlNode(new ForEachSqlNode(ms.getConfiguration(), new TextSqlNode(" ${key} &gt; #{value} "), "greateMap", "key", "value", "", "", "and"), "greateMap != null"));
        
        ifNodes.add(new IfSqlNode(new StaticTextSqlNode(" and "), "greateEqualsMap != null"));
        ifNodes.add(new IfSqlNode(new ForEachSqlNode(ms.getConfiguration(), new TextSqlNode(" ${key} &gt;= #{value} "), "greateEqualsMap", "key", "value", "", "", "and"), "greateEqualsMap != null"));
        
        ifNodes.add(new IfSqlNode(new StaticTextSqlNode(" and "), "lessMap != null"));
        ifNodes.add(new IfSqlNode(new ForEachSqlNode(ms.getConfiguration(), new TextSqlNode(" ${key} &lt; #{value} "), "lessMap", "key", "value", "", "", "and"), "lessMap != null"));
        
        ifNodes.add(new IfSqlNode(new StaticTextSqlNode(" and "), "lessEqualsMap != null"));
        ifNodes.add(new IfSqlNode(new ForEachSqlNode(ms.getConfiguration(), new TextSqlNode(" ${key} &lt;= #{value} "), "lessEqualsMap", "key", "value", "", "", "and"), "lessEqualsMap != null"));
		
        ifNodes.add(new IfSqlNode(new StaticTextSqlNode(" and "), "nullMap != null"));
        ifNodes.add(new IfSqlNode(new ForEachSqlNode(ms.getConfiguration(), new TextSqlNode(" ${key} is null "), "nullMap", "key", "value", "", "", "and"), "nullMap != null"));
		
        ifNodes.add(new IfSqlNode(new StaticTextSqlNode(" and "), "notNullMap != null"));
        ifNodes.add(new IfSqlNode(new ForEachSqlNode(ms.getConfiguration(), new TextSqlNode(" ${key} is not null "), "notNullMap", "key", "value", "", "", "and"), "notNullMap != null"));
    }
}