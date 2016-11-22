package com.hujz.framework.orm.hibernate.test.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hujz.framework.orm.GenericDao;
import com.hujz.framework.orm.hibernate.test.dao.SysUserDao;
import com.hujz.framework.orm.hibernate.test.dto.SysUserDto;
import com.hujz.framework.orm.hibernate.test.entity.SysUser;
import com.hujz.framework.orm.hibernate.test.service.SysUserService;
import com.hujz.sb.framework.service.BasicSuperService;

@Service
public class SysUserServiceImpl extends BasicSuperService<SysUserDto, SysUser, String> implements SysUserService {
	
	@Autowired
	private SysUserDao sysUserDao;

	@Override
	public GenericDao<SysUser, String> getBasicDao() {
		return sysUserDao;
	}

}
