package com.hujz.framework.orm.hibernate;

import org.hibernate.cfg.ImprovedNamingStrategy;
import org.hibernate.cfg.NamingStrategy;

public class HibernateNamingStrategy extends ImprovedNamingStrategy implements NamingStrategy { 

    /**
	 * @Title serialVersionUID
	 * @type long
	 * @date 2014-1-9 下午1:40:47
	 * 含义 TODO
	 */
	private static final long serialVersionUID = 2496743485363610914L;
	
//	private String currentTablePrefix; 

    /**
     * @Project orm-hibernate
     * @Package com.hujz.framework.orm.hibernate
     * @see org.hibernate.cfg.ImprovedNamingStrategy#classToTableName(java.lang.String)
     * @Method classToTableName方法.<br>
     * @Description 如果在<class>元素中没有显示设置表名，Hibernate调用该方法
     * @author jiuzhou.hu@baozun.cn
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
     * @Project orm-hibernate
     * @Package com.hujz.framework.orm.hibernate
     * @see org.hibernate.cfg.ImprovedNamingStrategy#propertyToColumnName(java.lang.String)
     * @Method propertyToColumnName方法.<br>
     * @Description 如果在<property>元素中没有显示设置字段名，Hibernate调用该方法
     * @author jiuzhou.hu@baozun.cn
     * @date 2016年1月6日 下午6:22:49
     * @param propertyName
     * @return
     */
    @Override 
    public String propertyToColumnName(String propertyName) { 
        return /*currentTablePrefix +*/ addUnderscores(propertyName).toLowerCase(); 
    } 

    /**
     * @Project orm-hibernate
     * @Package com.hujz.framework.orm.hibernate
     * @see org.hibernate.cfg.ImprovedNamingStrategy#columnName(java.lang.String)
     * @Method columnName方法.<br>
     * @Description 如果在<property>元素中显示设置了字段名，Hibernate调用该方法
     * @author jiuzhou.hu@baozun.cn
     * @date 2016年1月6日 下午6:23:02
     * @param columnName
     * @return
     */
    @Override 
    public String columnName(String columnName) {
    	return columnName;
    } 

    /**
     * @Project orm-hibernate
     * @Package com.hujz.framework.orm.hibernate
     * @see org.hibernate.cfg.ImprovedNamingStrategy#tableName(java.lang.String)
     * @Method tableName方法.<br>
     * @Description 如果在<class>元素中显示设置了表名，Hibernate调用该方法
     * @author jiuzhou.hu@baozun.cn
     * @date 2016年1月6日 下午6:22:19
     * @param tableName
     * @return
     */
    @Override 
    public String tableName(String tableName) { 
        return addUnderscores(tableName).toLowerCase(); 
    } 
} 