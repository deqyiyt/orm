package com.hujz.framework.orm.mybatis.face.base;

import java.io.Serializable;

import org.apache.ibatis.annotations.UpdateProvider;

import com.hujz.framework.orm.dao.BasicDao;
import com.hujz.framework.orm.mybatis.provider.base.BaseUpdateProvider;

/**
 *********************************************** 
 * @Copyright (c) by soap All right reserved.
 * @Create Date: 2015年9月9日 下午8:55:29
 * @Create Author: hujiuzhou
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
	 * @Project framework
	 * @Package com.hujz.sb.framework.common.dao
	 * @Method update方法.<br>
	 * @Description TODO 
	 * @author 胡久洲
	 * @date 2014-3-24 下午2:49:24
	 * @param object
	 */
	@UpdateProvider(type = BaseUpdateProvider.class, method = "dynamicSQL")
	void update(T object);
}
