package com.hujz.framework.orm.mapper.face.condition;

import java.io.Serializable;

import org.apache.ibatis.annotations.UpdateProvider;

import com.hujz.framework.orm.dao.BasicDao;
import com.hujz.framework.orm.mapper.provider.condition.ConditionUpdateProvider;
import com.hujz.framework.orm.util.QueryCondition;

/**
 *********************************************** 
 * @Create Date: 2015年9月9日 下午8:53:03
 * @Create Author: 352deqyiyt@163.com
 * @File Name: UpdateByConditionMapper
 * @Function: 通用Mapper接口,通过QueryCondition修改
 * @Last version: 1.0
 * @Last Update Date:
 * @Change Log:
 ************************************************* 
 */
public interface UpdateByConditionMapper<T, PK extends Serializable> extends BasicDao<T, PK>{
	/**
	 * 根据条件批量修改
	 * @author 352deqyiyt@163.com
	 * @date 2014-3-24 下午2:53:01
	 * @param cond
	 * @return
	 */
	@UpdateProvider(type = ConditionUpdateProvider.class, method = "dynamicSQL")
	int batchUpdate(QueryCondition cond);
}