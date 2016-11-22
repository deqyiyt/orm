package com.hujz.framework.orm.mybatis.face;

import java.io.Serializable;

import com.hujz.framework.orm.mybatis.face.base.BaseDeleteMapper;
import com.hujz.framework.orm.mybatis.face.base.BaseInsertMapper;
import com.hujz.framework.orm.mybatis.face.base.BaseQueryMapper;
import com.hujz.framework.orm.mybatis.face.base.BaseUpdateMapper;

/**
 *********************************************** 
 * @Copyright (c) by soap All right reserved.
 * @Create Date: 2015年9月9日 下午8:53:27
 * @Create Author: hujiuzhou
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
