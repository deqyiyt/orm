package com.hujz.framework.orm.mybatis.face.base;

import java.io.Serializable;

import org.apache.ibatis.annotations.InsertProvider;

import com.hujz.framework.orm.dao.BasicDao;
import com.hujz.framework.orm.mybatis.provider.base.BaseInsertProvider;

/**
 *********************************************** 
 * @Create Date: 2015年9月9日 下午8:55:08
 * @Create Author: 352deqyiyt@163.com
 * @File Name: BaseInsertMapper
 * @Function: 通用Mapper接口,插入单个实体类的基本接口
 * @Last version: 1.0
 * @Last Update Date:
 * @Change Log:
 ************************************************* 
 */
public interface BaseInsertMapper<T, PK extends Serializable> extends BasicDao<T, PK>{
	/**
	 * 保存对象
	 * @author 352deqyiyt@163.com
	 * @date 2014-3-24 下午2:49:47
	 * @param object
	 * @return
	 */
	@InsertProvider(type = BaseInsertProvider.class, method = "dynamicSQL")
	void save(T object);
}
