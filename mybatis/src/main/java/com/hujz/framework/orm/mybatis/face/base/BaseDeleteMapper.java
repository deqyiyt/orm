package com.hujz.framework.orm.mybatis.face.base;

import java.io.Serializable;

import org.apache.ibatis.annotations.DeleteProvider;

import com.hujz.framework.orm.dao.BasicDao;
import com.hujz.framework.orm.mybatis.provider.base.BaseDeleteProvider;

/**
 *********************************************** 
 * @Copyright (c) by soap All right reserved.
 * @Create Date: 2015年9月9日 下午8:54:58
 * @Create Author: hujiuzhou
 * @File Name: BaseDeleteMapper
 * @Function: 通用Mapper接口,删除单个实体类的基本接口
 * @Last version: 1.0
 * @Last Update Date:
 * @Change Log:
 ************************************************* 
 */
public interface BaseDeleteMapper<T, PK extends Serializable> extends BasicDao<T, PK>{
	/**
	 * 通过ID删除对象
	 * @Project framework
	 * @Package com.hujz.sb.framework.common.dao
	 * @Method delete方法.<br>
	 * @Description TODO 
	 * @author 胡久洲
	 * @date 2014-3-24 下午2:49:41
	 * @param id
	 */
	@DeleteProvider(type = BaseDeleteProvider.class, method = "dynamicSQL")
	void delete(PK id);
	
	/**
	 * 通过主键删除
	 * @Project framework
	 * @Package com.hujz.sb.framework.common.dao
	 * @Method deleteAll方法.<br>
	 * @Description TODO(用一句话描述该类做什么)
	 * @author 胡久洲
	 * @date 2014-3-24 下午2:53:52
	 * @param ids
	 */
	@SuppressWarnings("unchecked")
	@DeleteProvider(type = BaseDeleteProvider.class, method = "dynamicSQL")
	void deleteAll(PK... ids);
}
