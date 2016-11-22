package com.hujz.framework.orm.mybatis.face.condition;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.SelectProvider;

import com.hujz.framework.orm.dao.BasicDao;
import com.hujz.framework.orm.mybatis.provider.condition.ConditionQueryProvider;
import com.hujz.framework.orm.util.QueryCondition;
import com.hujz.framework.orm.util.QueryResult;

public interface SelectByConditionMapper<T, PK extends Serializable> extends BasicDao<T, PK>{
	/**
	 * 通过条件查询
	 * @Project framework
	 * @Package com.hujz.sb.framework.common.dao
	 * @Method query方法.<br>
	 * @Description TODO(用一句话描述该类做什么)
	 * @author 胡久洲
	 * @date 2014-3-24 下午2:54:31
	 * @param cond
	 * @return
	 */
	@SelectProvider(type = ConditionQueryProvider.class, method = "dynamicSQL")
	List<T> query(QueryCondition cond);
	
	/**
	 * 通过条件查询总条数
	 * @Project framework
	 * @Package com.hujz.sb.framework.common.dao
	 * @Method queryCount方法.<br>
	 * @Description TODO(用一句话描述该类做什么)
	 * @author 胡久洲
	 * @date 2014-3-24 下午2:54:38
	 * @param cond
	 * @return
	 */
	@SelectProvider(type = ConditionQueryProvider.class, method = "dynamicSQL")
	Long queryCount(QueryCondition cond);
	
	/**
	 * 获取当前对象里的所有记录
	 * @Project framework
	 * @Package com.hujz.sb.framework.common.dao
	 * @Method queryAll方法.<br>
	 * @Description TODO 
	 * @author 胡久洲
	 * @date 2014-3-24 下午2:50:42
	 * @return
	 */
	@SelectProvider(type = ConditionQueryProvider.class, method = "dynamicSQL")
	List<T> queryAll();
	
	/**
	 * 通过hql语句查询，这里不做重写
	 * @Project System Base
	 * @Package com.hujz.sb.framework.dao
	 * @Method query方法.<br>
	 * @Description TODO(用一句话描述该类做什么)
	 * @author hjz
	 * @date 2014-12-23 下午4:52:38
	 * @param cond
	 * @param hql
	 * @return
	 */
	QueryResult<?> query(QueryCondition cond, String hql);
	
	/**
	 * 通过条件查询实体是否存在
	 * @Project framework
	 * @Package com.hujz.sb.framework.common.dao
	 * @Method isUnique方法.<br>
	 * @Description TODO(用一句话描述该类做什么)
	 * @author 胡久洲
	 * @date 2014-3-24 下午2:54:53
	 * @param cond
	 * @return
	 */
	@SelectProvider(type = ConditionQueryProvider.class, method = "dynamicSQL")
	boolean isUnique(QueryCondition cond);
	
}
