package com.ias.assembly.orm.mybatis.face.base;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.SelectProvider;

import com.ias.assembly.orm.basic.dao.BasicDao;
import com.ias.assembly.orm.mybatis.provider.base.BaseQueryProvider;

/**
 *********************************************** 
 * @Create Date: 2015年9月9日 下午8:55:18
 * @Create Author: 352deqyiyt@163.com
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
	 * @author 352deqyiyt@163.com
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
    
    /**
	 * @Title 随机取一条
	 * @Method getRandomOne方法.<br>
	 * @author jiuzhou.hu
	 * @date 2016年8月16日 下午2:41:51
	 * @return
	 */
	@SelectProvider(type = BaseQueryProvider.class, method = "dynamicSQL")
	T getRandomOne();
}
