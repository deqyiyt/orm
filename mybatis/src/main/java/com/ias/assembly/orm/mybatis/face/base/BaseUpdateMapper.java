package com.ias.assembly.orm.mybatis.face.base;

import java.io.Serializable;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.UpdateProvider;

import com.ias.assembly.orm.basic.dao.BasicDao;
import com.ias.assembly.orm.mybatis.provider.base.BaseUpdateProvider;

/**
 *********************************************** 
 * @Create Date: 2015年9月9日 下午8:55:29
 * @Create Author: 352deqyiyt@163.com
 * @File Name: BaseUpdateMapper
 * @Function: 通用Mapper接口,修改单个实体类的基本接口
 * @Last version: 1.0
 * @Last Update Date:
 * @Change Log:
 ************************************************* 
 */
public interface BaseUpdateMapper<T, PK extends Serializable> extends BasicDao<T, PK>{
	/**
	 * 更新对象
	 * @author 352deqyiyt@163.com
	 * @date 2014-3-24 下午2:49:24
	 * @param object
	 */
	@UpdateProvider(type = BaseUpdateProvider.class, method = "dynamicSQL")
	@Options(useCache = false, useGeneratedKeys = false)
	void update(T object);
}
