package com.hujz.business.dao;

import java.util.List;

import com.hujz.business.entity.SysUser;
import com.hujz.framework.orm.GenericDao;
import com.hujz.framework.orm.bean.PageTools;
import com.hujz.framework.orm.util.QueryCondition;

public interface SysUserDao extends GenericDao<SysUser, String>{
	
	List<SysUser> queryByPage(QueryCondition cond);
	
	List<SysUser> findByPage(PageTools pageTools);
}
