package com.hujz.framework.orm.mybatis.face.base;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.SelectProvider;

import com.hujz.framework.orm.dao.BasicDao;
import com.hujz.framework.orm.mybatis.provider.base.BaseQueryProvider;

/**
 *********************************************** 
 * @Copyright (c) by soap All right reserved.
 * @Create Date: 2015年9月9日 下午8:55:18
 * @Create Author: hujiuzhou
 * @File Name: BaseSelectMapper
 * @Function: 通用Mapper接口,查询单个实体类的基本接口
 * @Last version: 1.0
 * @Last Update Date:
 * @Change Log:
 ************************************************* 
 */
public interface BaseQueryMapper<T, PK extends Serializable> extends BasicDao<T, PK>{
	
	/**
	 * 通过ID获取对象
	 * @Project framework
	 * @Package com.hujz.sb.framework.common.dao
	 * @Method get方法.<br>
	 * @Description TODO 
	 * @author 胡久洲
	 * @date 2014-3-24 下午2:50:33
	 * @param id
	 * @return
	 */
	@SelectProvider(type = BaseQueryProvider.class, method = "dynamicSQL")
	T get(PK id);
	
	/**
     * 根据实体中的属性值进行查询，查询条件使用等号
     *
     * @param record
     * @return
     */
    @SelectProvider(type = BaseQueryProvider.class, method = "dynamicSQL")
    List<T> queryByEntity(T record);
    
    /**
     * 根据实体中的属性查询总数，查询条件使用等号
     *
     * @param record
     * @return
     */
    @SelectProvider(type = BaseQueryProvider.class, method = "dynamicSQL")
    int queryCountByEntity(T record);
    
    /**
     * 根据实体中的属性进行查询，只能有一个返回值，有多个结果是抛出异常，查询条件使用等号
     *
     * @param record
     * @return
     */
    @SelectProvider(type = BaseQueryProvider.class, method = "dynamicSQL")
    T queryOneByEntity(T record);
}
