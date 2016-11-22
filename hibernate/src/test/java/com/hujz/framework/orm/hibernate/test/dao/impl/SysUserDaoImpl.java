package com.hujz.framework.orm.hibernate.test.dao.impl;

import org.springframework.stereotype.Repository;

import com.hujz.framework.orm.hibernate.test.dao.SysUserDao;
import com.hujz.framework.orm.hibernate.test.entity.SysUser;

@Repository
public class SysUserDaoImpl extends TestGenericDao<SysUser, String> implements SysUserDao{

}
