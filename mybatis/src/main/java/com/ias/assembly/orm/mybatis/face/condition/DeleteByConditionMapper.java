package com.ias.assembly.orm.mybatis.face.condition;

import java.io.Serializable;

import org.apache.ibatis.annotations.DeleteProvider;

import com.ias.assembly.orm.basic.dao.BasicDao;
import com.ias.assembly.orm.basic.util.QueryCondition;
import com.ias.assembly.orm.mybatis.provider.condition.ConditionDeleteProvider;

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
