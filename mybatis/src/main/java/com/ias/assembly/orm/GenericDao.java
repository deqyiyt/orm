package com.ias.assembly.orm;

import java.io.Serializable;

import com.ias.assembly.orm.mybatis.face.BasicMapper;
import com.ias.assembly.orm.mybatis.face.ConditionMapper;

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