package com.hujz.framework.orm.mybatis.face.base;

import java.io.Serializable;

import org.apache.ibatis.annotations.InsertProvider;

import com.hujz.framework.orm.dao.BasicDao;
import com.hujz.framework.orm.mybatis.provider.base.BaseInsertProvider;

/**
 *********************************************** 
 * @Copyright (c) by soap All right reserved.
 * @Create Date: 2015年9月9日 下午8:55:08
 * @Create Author: hujiuzhou
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
	 * @Project framework
	 * @Package com.hujz.sb.framework.common.dao
	 * @Method save方法.<br>
	 * @Description TODO 
	 * @author 
	 * @date 2014-3-24 下午2:49:47
	 * @param object
	 * @return
	 */
	@InsertProvider(type = BaseInsertProvider.class, method = "dynamicSQL")
	void save(T object);
}
