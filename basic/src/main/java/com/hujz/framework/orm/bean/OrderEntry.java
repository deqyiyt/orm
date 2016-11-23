package com.hujz.framework.orm.bean;

import java.io.Serializable;

/**
 *********************************************** 
 * @Create Date: 2015年9月12日 下午12:57:01
 * @Create Author: jiuzhou.hu
 * @File Name: OrderEntry
 * @Function: 排序类封装
 * @Last version: 1.0
 * @Last Update Date:
 * @Change Log:
 ************************************************* 
 */
public class OrderEntry implements Serializable{

    /**
	 * @Title serialVersionUID
	 * @type long
	 * @date 2015-1-21 下午8:40:10
	 * 含义 TODO
	 */
	private static final long serialVersionUID = 3520927826725876790L;
	/**
     * 降序标志常数
     */
    public static final int ORDER_DESC = -1;
    /**
     * 升序标志常数
     */
    public static final int ORDER_ASC = 1;
    /**
     * 排序键
     */
    private Object key = null;
    /**
     * 默认的排序
     */
    private int order = ORDER_ASC;
 

    /**
     * 空构造函数
     */
    public OrderEntry() {
    }

    /**
     * 通过构造函数设置排序的键和排序方式
     * @param key 排序的键
     * @param order 排序方式
     */
    public OrderEntry(Object key, int order) {
        this.key = key;
        this.order = order;
    }

    /**
     * 通过构造函数设置排序的键
     * @param key 排序的键 
     */
    public OrderEntry(Object key) {
        this(key, 0);
    }

    public void setKey(Object key) {
        this.key = key;
    }

    public Object getKey() {
        return key;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getOrder() {
        return order;
    }
}
