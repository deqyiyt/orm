package com.ias.assembly.orm.hibernate;

import org.hibernate.cfg.ImprovedNamingStrategy;
import org.hibernate.cfg.NamingStrategy;

public class HibernateNamingStrategy extends ImprovedNamingStrategy implements NamingStrategy { 

    /**
	 * @Title serialVersionUID
	 * @type long
	 * @date 2014-1-9 下午1:40:47
	 */
	private static final long serialVersionUID = 2496743485363610914L;
	
//	private String currentTablePrefix; 

    /**
     * 如果在<class>元素中没有显示设置表名，Hibernate调用该方法
     * @author jiuzhou.hu
     * @date 2016年1月6日 下午6:21:56
     * @param className
     * @return
     */
    @Override 
    public String classToTableName(String className) { 
//        currentTablePrefix = className.substring(0, 3).toLowerCase() + "_";
        return "t_" + tableName(className); 
    } 

    /**
     * 如果在<property>元素中没有显示设置字段名，Hibernate调用该方法
     * @author jiuzhou.hu
     * @date 2016年1月6日 下午6:22:49
     * @param propertyName
     * @return
     */
    @Override 
    public String propertyToColumnName(String propertyName) { 
        return /*currentTablePrefix +*/ addUnderscores(propertyName).toLowerCase(); 
    } 

    /**
     * 如果在<property>元素中显示设置了字段名，Hibernate调用该方法
     * @author jiuzhou.hu
     * @date 2016年1月6日 下午6:23:02
     * @param columnName
     * @return
     */
    @Override 
    public String columnName(String columnName) {
    	return columnName;
    } 

    /**
     * 如果在<class>元素中显示设置了表名，Hibernate调用该方法
     * @author jiuzhou.hu
     * @date 2016年1月6日 下午6:22:19
     * @param tableName
     * @return
     */
    @Override 
    public String tableName(String tableName) { 
        return addUnderscores(tableName).toLowerCase(); 
    } 
} 