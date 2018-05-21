package com.ias.assembly.orm;

import java.io.Serializable;

import com.ias.assembly.orm.basic.dao.BasicDao;

public interface GenericDao<T, PK extends Serializable> extends BasicDao<T, PK> {
	
}
