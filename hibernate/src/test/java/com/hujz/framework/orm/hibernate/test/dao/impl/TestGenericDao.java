package com.hujz.framework.orm.hibernate.test.dao.impl;

import java.io.Serializable;

import javax.annotation.Resource;

import org.springframework.orm.hibernate4.HibernateTemplate;

import com.hujz.framework.orm.hibernate.impl.HibernateGenericDao;

/**
 * <p>
 * dao超类实现
 * </p>
 * 
 * @author liurong
 * @version $Id: LaziHibernateGenericDao.java,v 0.1 2009-5-13 上午10:52:05 liurong Exp $
 */
/**
 *********************************************** 
 * @Create Date: 2014-2-24 下午2:09:45
 * @Create Author: jiuzhou.hu
 * @Function: hibernate公共dao超类实现
 * @Last version: 1.0
 * @Last Update Date:
 * @Change Log:
 ************************************************* 
 */
public class TestGenericDao<T, PK extends Serializable> extends HibernateGenericDao<T, PK> {
	
	/**
	 * @Title hibernateTemplate
	 * @type HibernateTemplate
	 * @date 2015年9月15日 下午9:42:22
	 * 含义 如果切换到hibernate需要注入hibernateTemplate
	 */
	@Resource(name="test-hibernate-hibernateTemplate")
	private HibernateTemplate hibernateTemplate;

	/**
	 * hibernateTemplate的获取.
	 * @return HibernateTemplate
	 */
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	/**
	 * 设定hibernateTemplate的值.
	 * @param HibernateTemplate
	 */
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
}
