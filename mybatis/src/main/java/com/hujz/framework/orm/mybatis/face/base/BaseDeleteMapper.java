package com.hujz.framework.orm.mybatis.face.base;

import java.io.Serializable;

import org.apache.ibatis.annotations.DeleteProvider;

import com.hujz.framework.orm.dao.BasicDao;
import com.hujz.framework.orm.mybatis.provider.base.BaseDeleteProvider;

/**
 *********************************************** 
 * @Create Date: 2015年9月9日 下午8:54:58
 * @Create Author: 352deqyiyt@163.com
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
	 * @author 352deqyiyt@163.com
	 * @date 2014-3-24 下午2:49:41
	 * @param id
	 */
	@DeleteProvider(type = BaseDeleteProvider.class, method = "dynamicSQL")
	void delete(PK id);
	
	/**
	 * 通过主键删除
	 * @author 352deqyiyt@163.com
	 * @date 2014-3-24 下午2:53:52
	 * @param ids
	 */
	@SuppressWarnings("unchecked")
	@DeleteProvider(type = BaseDeleteProvider.class, method = "dynamicSQL")
	void deleteAll(PK... ids);
}
