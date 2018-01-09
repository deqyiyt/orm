package com.hujz.framework.orm;

import java.io.Serializable;

import com.hujz.framework.orm.mapper.face.BasicMapper;
import com.hujz.framework.orm.mapper.face.ConditionMapper;

import tk.mybatis.mapper.common.Marker;

/**
 *********************************************** 
 * @Copyright (c) by soap All right reserved.
 * @Create Date: 2015年9月9日 下午8:53:27
 * @Create Author: 352deqyiyt@163.com
 * @File Name: BaseMapper
 * @Function: 通用Mapper接口
 * @Last version: 1.0
 * @Last Update Date:
 * @Change Log:
 ************************************************* 
 */
public interface GenericDao<T, PK extends Serializable> extends BasicMapper<T, PK>, ConditionMapper<T, PK>{

}