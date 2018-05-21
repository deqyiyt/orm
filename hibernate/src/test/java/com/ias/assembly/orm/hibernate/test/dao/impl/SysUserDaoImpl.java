package com.ias.assembly.orm.hibernate.test.dao.impl;

import org.springframework.stereotype.Repository;

import com.ias.assembly.orm.hibernate.test.dao.SysUserDao;
import com.ias.assembly.orm.hibernate.test.entity.SysUser;

@Repository
public class SysUserDaoImpl extends TestGenericDao<SysUser, String> implements SysUserDao{

}
