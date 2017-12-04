package com.hujz.framework.orm.mybatis.test.dao;

import com.hujz.framework.orm.GenericDao;
import com.hujz.framework.orm.mybatis.test.entity.SystemUser;

public interface SysUserDao extends GenericDao<SystemUser, String>{
	
	SystemUser selectById(String id);
}
