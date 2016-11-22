package com.hujz.framework.orm.mybatis.face.condition;

import java.io.Serializable;

import org.apache.ibatis.annotations.SelectProvider;

import com.hujz.framework.orm.dao.BasicDao;
import com.hujz.framework.orm.mybatis.provider.condition.ConditionDeleteProvider;
import com.hujz.framework.orm.util.QueryCondition;

/**
 *********************************************** 
 * @Copyright (c) by soap All right reserved.
 * @Create Date: 2015年9月9日 下午8:52:37
 * @Create Author: hujiuzhou
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
	 * @Project framework
	 * @Package com.hujz.sb.framework.common.dao
	 * @Method batchDelete方法.<br>
	 * @Description TODO(用一句话描述该类做什么)
	 * @author 胡久洲
	 * @date 2014-3-24 下午2:54:07
	 * @param cond
	 * @return
	 */
	@SelectProvider(type = ConditionDeleteProvider.class, method = "dynamicSQL")
	void batchDelete(QueryCondition cond);
}
