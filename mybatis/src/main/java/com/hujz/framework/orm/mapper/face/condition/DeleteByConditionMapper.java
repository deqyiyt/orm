package com.hujz.framework.orm.mapper.face.condition;

import java.io.Serializable;

import org.apache.ibatis.annotations.DeleteProvider;

import com.hujz.framework.orm.dao.BasicDao;
import com.hujz.framework.orm.mapper.provider.condition.ConditionDeleteProvider;
import com.hujz.framework.orm.util.QueryCondition;

/**
 *********************************************** 
 * @Create Date: 2015年9月9日 下午8:52:37
 * @Create Author: 352deqyiyt@163.com
 * @File Name: DeleteByConditionMapper
 * @Function: 通用Mapper接口,通过QueryCondition删除
 * @Last version: 1.0
 * @Last Update Date:
 * @Change Log:
 ************************************************* 
 */
public interface DeleteByConditionMapper<T, PK extends Serializable> extends BasicDao<T, PK>{
	/**
	 * 根据条件批量删除
	 * @author 352deqyiyt@163.com
	 * @date 2014-3-24 下午2:54:07
	 * @param cond
	 * @return
	 */
	@DeleteProvider(type = ConditionDeleteProvider.class, method = "dynamicSQL")
	int batchDelete(QueryCondition cond);
}
