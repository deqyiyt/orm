package com.hujz.framework.orm;

import java.io.Serializable;

import com.hujz.framework.orm.dao.BasicDao;

public interface GenericDao<T, PK extends Serializable> extends BasicDao<T, PK> {
	
}
