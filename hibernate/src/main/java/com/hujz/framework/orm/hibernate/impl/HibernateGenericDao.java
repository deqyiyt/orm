package com.hujz.framework.orm.hibernate.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;

import com.hujz.framework.orm.GenericDao;
import com.hujz.framework.orm.util.QueryCondition;

/**
 * <p>
 * dao超类实现
 * </p>
 * 
 * @author liurong
 * @version $Id: HibernateGenericDao.java,v 0.1 2009-5-13 上午10:52:05 liurong Exp $
 */
/**
 *********************************************** 
 * @Copyright (c) by ysc All right reserved.
 * @Create Date: 2014-2-24 下午2:09:45
 * @Create Author: hujz
 * @File Name: HibernateGenericDao
 * @Function: hibernate公共dao超类实现
 * @Last version: 1.0
 * @Last Update Date:
 * @Change Log:
 ************************************************* 
 */
public abstract class HibernateGenericDao<T, PK extends Serializable> implements GenericDao<T, PK> {
	
	/**
	 * 获得注入的持久类
	 * @Project pd-framework
	 * @Package com.hujz.framework.common.dao
	 * @Method getClassType方法.<br>
	 * @Description 
	 * @author 胡久洲
	 * @date 2014-3-24 下午2:55:37
	 * @param index
	 * @return
	 */
	public Class<?> getGenericType() {
        Type genType = getClass().getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {
            return Object.class;
        }
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if (!(params[0] instanceof Class)) {
            return Object.class;
        }
        return (Class<?>) params[0];
    }

	/**
	 * 保存或者更新对象
	 * @Project pd-framework
	 * @Package com.hujz.sb.framework.common.dao
	 * @see com.hujz.sb.framework.dao.GenericDao#saveOrUpdate(java.lang.Object)
	 * @Method saveOrUpdate方法.<br>
	 * @Description 
	 * @author 胡久洲
	 * @date 2014-3-24 下午2:58:58
	 * @param object
	 */
	@Override
	public void saveOrUpdate(T object) {
		getHibernateTemplate().saveOrUpdate(object);
	}
	
	/**
	 * 批量新增或修改
	 * @Project pd-framework
	 * @Package com.hujz.sb.framework.common.dao
	 * @see com.hujz.sb.framework.dao.GenericDao#saveOrUpdateAll(java.util.List)
	 * @Method saveOrUpdateAll方法.<br>
	 * @Description 
	 * @author 胡久洲
	 * @date 2014-3-24 下午2:59:07
	 * @param list
	 */
	@Override
	public void saveOrUpdateAll(List<T> list) {
		for(T t :list) {
			saveOrUpdate(t);
		}
	}

	/**
	 * 获取当前对象里的所有记录
	 * @Project pd-framework
	 * @Package com.hujz.sb.framework.common.dao
	 * @see com.hujz.sb.framework.dao.GenericDao#queryAll()
	 * @Method queryAll方法.<br>
	 * @Description 
	 * @author 胡久洲
	 * @date 2014-3-24 下午2:59:33
	 * @return
	 */
	@Override
	public List<T> queryAll() {
		@SuppressWarnings("unchecked")
		List<T> list = (List<T>) getHibernateTemplate().loadAll(getGenericType());
		getHibernateTemplate().clear();
		return list;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public T get(PK id) {
		return (T)getHibernateTemplate().get(getGenericType(), id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void save(T object) {
		getHibernateTemplate().save(object);
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void delete(PK id) {
		getHibernateTemplate().delete(this.get(id));
	}

	@Override
	public void update(T object) {
		getHibernateTemplate().update(object);
	}

    

	@Override
	public void deleteAll(@SuppressWarnings("unchecked") PK... ids){
		for (int i = 0; i < ids.length; i++) {
			T t = this.get( ids[i]);
			getHibernateTemplate().delete(t);
		}
	}

    // 批量修改
	@Override
    public void batchUpdate(final QueryCondition qc) {
		getHibernateTemplate().execute(new HibernateCallback<Integer>() {
			public Integer doInHibernate(Session session)
					throws HibernateException {
				int i = 0;
				ClassMetadata metadata = session.getSessionFactory().getClassMetadata(getGenericType());
	        	i = HibernateHelper.batchUpdateHqlByQueryCondition(session, metadata, qc);
	        	return i;
			}
		});
    }

	@Override
	public void batchDelete(final QueryCondition cond) {
		getHibernateTemplate().execute(new HibernateCallback<Integer>() {
			public Integer doInHibernate(Session session)
					throws HibernateException {
				int i = 0;
				ClassMetadata metadata = session.getSessionFactory().getClassMetadata(getGenericType());
	            i = HibernateHelper.batchDeleteHqlByQueryCondition(session, metadata, cond);
	        	return i;
			}
		});
	}

	@Override
	public List<T> query(final QueryCondition cond) {
		return getHibernateTemplate().execute(new HibernateCallback<List<T>>() {
			public List<T> doInHibernate(Session session)
					throws HibernateException {
				
				List<T> qr = new ArrayList<T>();
				ClassMetadata metadata = session.getSessionFactory().getClassMetadata(getGenericType());
				HibernateHelper.selectHqlByQueryCondition(session, metadata, cond, qr);
				return qr;
			}
		});
	}
	
	// 根据qc的组合条件进行查询总条数
	@Override
    public Long queryCount(final QueryCondition cond) {
    	return (Long)getHibernateTemplate().execute(new HibernateCallback<Long>() {
			public Long doInHibernate(Session session)
					throws HibernateException {
				ClassMetadata metadata = session.getSessionFactory().getClassMetadata(getGenericType());
				return HibernateHelper.selectHqlByQueryCountCondition(session, metadata, cond);
			}
		});
    }

	@Override
	public boolean isUnique(final QueryCondition cond)
	{
		Long cnt=queryCount(cond);
	    if(cnt>0)
	    {
	    	return false;
	    }
	    else
	    {
	    	return true;
	    }
	}
	
	/**
	 * hibernateTemplate的获取.
	 * @return HibernateTemplate
	 */
	public abstract HibernateTemplate getHibernateTemplate();

	
	
	@Override
	public T queryOneByEntity(T record) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int queryCountByEntity(T record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<T> queryByEntity(T record) {
		// TODO Auto-generated method stub
		return null;
	}
}
