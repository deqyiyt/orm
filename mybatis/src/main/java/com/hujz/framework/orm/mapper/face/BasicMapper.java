package com.hujz.framework.orm.mapper.face;

import java.io.Serializable;

import com.hujz.framework.orm.mapper.face.base.BaseDeleteMapper;
import com.hujz.framework.orm.mapper.face.base.BaseInsertMapper;
import com.hujz.framework.orm.mapper.face.base.BaseQueryMapper;
import com.hujz.framework.orm.mapper.face.base.BaseUpdateMapper;

/**
 *********************************************** 
 * @Create Date: 2015年9月9日 下午8:53:27
 * @Create Author: 352deqyiyt@163.com
 * @File Name: BaseMapper
 * @Function: 通用Mapper接口,操作单个实体类的基本接口
 * @Last version: 1.0
 * @Last Update Date:
 * @Change Log:
 ************************************************* 
 */
public interface BasicMapper<T, PK extends Serializable> extends BaseDeleteMapper<T, PK>
										, BaseInsertMapper<T, PK>
										, BaseQueryMapper<T, PK>
										, BaseUpdateMapper<T, PK>{

}
