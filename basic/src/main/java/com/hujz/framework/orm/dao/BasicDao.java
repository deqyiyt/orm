package com.hujz.framework.orm.dao;

import java.io.Serializable;
import java.util.List;

import com.hujz.framework.orm.util.QueryCondition;

public interface BasicDao<T, PK extends Serializable> {
	/**
	 * 获取当前对象里的所有记录
	 * @author jiuzhou.hu
	 * @date 2014-3-24 下午2:50:42
	 * @return
	 */
	List<T> queryAll();

	/**
	 * 通过ID获取对象
	 * @author jiuzhou.hu
	 * @date 2014-3-24 下午2:50:33
	 * @param id
	 * @return
	 */
	T get(PK id);

	/**
	 * 保存对象
	 * @author jiuzhou.hu
	 * @date 2014-3-24 下午2:49:47
	 * @param object
	 * @return
	 */
	void save(T object);

	/**
	 * 通过ID删除对象
	 * @author jiuzhou.hu
	 * @date 2014-3-24 下午2:49:41
	 * @param id
	 */
	void delete(PK id);

	/**
	 * 更新对象
	 * @author jiuzhou.hu
	 * @date 2014-3-24 下午2:49:24
	 * @param object
	 */
	void update(T object);
	
	/**
	 * 保存或更新对象
	 * @author jiuzhou.hu
	 * @date 2014-3-24 下午2:49:14
	 * @param object
	 */
	void saveOrUpdate(T object);
	
	/**
	 * 批量新增或修改对象
	 * @author jiuzhou.hu
	 * @date 2013-7-11 上午6:26:36
	 * @param list
	 */
	void saveOrUpdateAll(List<T> list);

    
	/**
	 * 根据条件批量修改
	 * @author jiuzhou.hu
	 * @date 2014-3-24 下午2:53:01
	 * @param cond
	 * @return
	 */
	int batchUpdate(QueryCondition cond);
	
	/**
	 * 通过主键删除
	 * @author jiuzhou.hu
	 * @date 2014-3-24 下午2:53:52
	 * @param ids
	 */
	void deleteAll(@SuppressWarnings("unchecked") PK... ids);
	
	/**
	 * 根据条件批量删除
	 * @author jiuzhou.hu
	 * @date 2014-3-24 下午2:54:07
	 * @param cond
	 * @return
	 */
	int batchDelete(QueryCondition cond);
	
	/**
	 * 通过条件查询
	 * @author jiuzhou.hu
	 * @date 2014-3-24 下午2:54:31
	 * @param cond
	 * @return
	 */
	List<T> query(QueryCondition cond);
	
	/**
	 * 通过条件查询总条数
	 * @author jiuzhou.hu
	 * @date 2014-3-24 下午2:54:38
	 * @param cond
	 * @return
	 */
	Long queryCount(QueryCondition cond);
	/**
	 * 通过条件查询实体是否存在
	 * @author jiuzhou.hu
	 * @date 2014-3-24 下午2:54:53
	 * @param cond
	 * @return
	 */
	boolean isUnique(final QueryCondition cond);
	
	/**
	 * 根据实体查询单条数据，仅mybatis中实现
	 * @author jiuzhou.hu
	 * @date 2015年10月10日 下午1:57:44
	 * @param record
	 * @return
	 */
	T queryOneByEntity(T record);
	
	/**
	 * 根据实体查询总条数，仅mybatis中实现
	 * @author jiuzhou.hu
	 * @date 2015年10月10日 下午1:58:16
	 * @param record
	 * @return
	 */
	int queryCountByEntity(T record);
	
	/**
	 * 根据实体查询结果集，仅mybatis中实现
	 * @author jiuzhou.hu
	 * @date 2015年10月10日 下午1:58:52
	 * @param record
	 * @return
	 */
	List<T> queryByEntity(T record);
}
