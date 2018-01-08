package com.hujz.framework.orm.mapper.face.condition;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.SelectProvider;

import com.hujz.framework.orm.dao.BasicDao;
import com.hujz.framework.orm.mapper.provider.condition.ConditionQueryProvider;
import com.hujz.framework.orm.util.QueryCondition;

public interface SelectByConditionMapper<T, PK extends Serializable> extends BasicDao<T, PK>{
	/**
	 * 通过条件查询
	 * @author 352deqyiyt@163.com
	 * @date 2014-3-24 下午2:54:31
	 * @param cond
	 * @return
	 */
	@SelectProvider(type = ConditionQueryProvider.class, method = "dynamicSQL")
	List<T> query(QueryCondition cond);
	
	/**
	 * 通过条件查询总条数
	 * @author 352deqyiyt@163.com
	 * @date 2014-3-24 下午2:54:38
	 * @param cond
	 * @return
	 */
	@SelectProvider(type = ConditionQueryProvider.class, method = "dynamicSQL")
	Long queryCount(QueryCondition cond);
	
	/**
	 * 通过条件查询实体是否存在
	 * @author 352deqyiyt@163.com
	 * @date 2014-3-24 下午2:54:53
	 * @param cond
	 * @return
	 */
	@SelectProvider(type = ConditionQueryProvider.class, method = "dynamicSQL")
	boolean isUnique(QueryCondition cond);
	
}
