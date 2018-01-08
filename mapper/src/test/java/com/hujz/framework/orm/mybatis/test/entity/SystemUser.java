package com.hujz.framework.orm.mybatis.test.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.hujz.framework.orm.mybatis.test.entity.base.AbstractSysUser;

@Entity
@Table(name="t_sys_user")
public class SystemUser extends AbstractSysUser {
	
	/**
	 * @Title serialVersionUID
	 * @type long
	 * @date 2015年9月10日 下午12:47:34
	 */
	private static final long serialVersionUID = -2153494239555218571L;

	public SystemUser() {
		super();
	}
}
