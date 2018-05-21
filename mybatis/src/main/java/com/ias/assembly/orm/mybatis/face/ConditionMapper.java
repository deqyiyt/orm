package com.ias.assembly.orm.mybatis.face;

import java.io.Serializable;

import com.ias.assembly.orm.mybatis.face.condition.DeleteByConditionMapper;
import com.ias.assembly.orm.mybatis.face.condition.SelectByConditionMapper;
import com.ias.assembly.orm.mybatis.face.condition.UpdateByConditionMapper;

/**
 *********************************************** 
 * @Create Date: 2015年9月9日 下午8:50:55
 * @Create Author: 352deqyiyt@163.com
 * @File Name: ConditionMapper
 * @Function: 通用Mapper接口,通过QueryCondition操作
 * @Last version: 1.0
 * @Last Update Date:
 * @Change Log:
 ************************************************* 
 */
public interface ConditionMapper<T, PK extends Serializable> 
											extends DeleteByConditionMapper<T, PK>
											, SelectByConditionMapper<T, PK>
											, UpdateByConditionMapper<T, PK>{

	
}
