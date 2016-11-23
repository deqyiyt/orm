package com.hujz.framework.orm.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.druid.support.json.JSONUtils;
import com.hujz.framework.orm.bean.OrderEntry;
import com.hujz.framework.orm.bean.PageTools;
import com.hujz.framework.orm.bean.QueryPropert;

/**
 *********************************************** 
 * @Title Hibernate通用条件类
 * @Last version: 1.0
 * @Create Date: 2016年11月22日 下午5:57:53
 * @Create Author: jiuzhou.hu
 * @Last Update Date:
 * @Change Log:
 ************************************************* 
 */
public class QueryCondition implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1817422219749819592L;

	/**
	 * @Title pageTools
	 * @type PageTools
	 * @date 2014-2-20 下午2:32:31
	 * 含义 分页实体类
	 */
	private PageTools pageTools;
    
    /**
     * @Title likeEqualsMap
     * @type Map<String,Object>
     * @date 2013-8-6 上午10:09:25
     * 含义 模糊等于
     */
    private Map<String, Object> likeEqualsMap;

    /**
     * @Title equalsMap
     * @type Map<String,Object>
     * @date 2013-8-6 上午10:09:34
     * 含义 等于
     */
    private Map<String, Object> equalsMap;

    /**
     * @Title notEqualsMap
     * @type Map<String,Object>
     * @date 2013-8-6 上午10:09:40
     * 含义 不等于
     */
    private Map<String, Object> notEqualsMap;

    /**
     * @Title greateMap
     * @type Map<String,Object>
     * @date 2013-8-6 上午10:09:46
     * 含义 大于
     */
    private Map<String, Object> greateMap;

    /**
     * @Title greateEqualsMap
     * @type Map<String,Object>
     * @date 2013-8-6 上午10:09:52
     * 含义 大于等于
     */
    private Map<String, Object> greateEqualsMap;

    /**
     * @Title lessMap
     * @type Map<String,Object>
     * @date 2013-8-6 上午10:09:58
     * 含义 小于
     */
    private Map<String, Object> lessMap;

    /**
     * @Title lessEqualsMap
     * @type Map<String,Object>
     * @date 2013-8-6 上午10:10:05
     * 含义 小于等于
     */
    private Map<String, Object> lessEqualsMap;

    /**
     * @Title nullMap
     * @type Map<String,Object>
     * @date 2013-8-6 上午10:10:10
     * 含义 值为空
     */
    private Map<String, Object> nullMap;

    /**
     * @Title notNullMap
     * @type Map<String,Object>
     * @date 2013-8-6 上午10:10:16
     * 含义 值非空
     */
    private Map<String, Object> notNullMap;

    /**
     * @Title inMap
     * @type Map<String,List<Object>>
     * @date 2013-8-6 上午10:10:22
     * 含义 等于列表中的某个值
     */
    private Map<String, List<Object>> inMap;

    /**
     * @Title notInMap
     * @type Map<String,List<Object>>
     * @date 2013-8-6 上午10:10:29
     * 含义 不等于列表中的任意一个值
     */
    private Map<String, List<Object>> notInMap;

    /**
     * @Title betweenInMap
     * @type Map<String,Object>
     * @date 2013-8-6 上午10:10:35
     * 含义 大于等于某个值并且小于等于另外一个值
     */
    private Map<String, Object> betweenInMap;

    /**
     * @Title notBetweenInMap
     * @type Map<String,Object>
     * @date 2013-8-6 上午10:10:44
     * 含义 小于开始值或者大于结束值
     */
    private Map<String, Object> notBetweenInMap;
    
    /**
     * @Title paramValues
     * @type List<Object>
     * @date 2013-8-6 上午10:10:51
     * 含义 存储hql中的值
     */
    private List<QueryPropert> params = new ArrayList<QueryPropert>();

    /**
     * @Title orderby
     * @type List<OrderEntry>
     * @date 2013-8-6 上午10:11:09
     * 含义 排序
     */
    private List<OrderEntry> orderby;
    
    /**
     * @Title entry
     * @type Object
     * @date 2013-8-6 上午10:11:15
     * 含义 按照实体类当做条件
     */
    private Object entry;
    
    /**
     * 是否使用查询缓存
     * @Title cacheable
     * @type boolean
     * @date 2014-12-23 下午4:02:42
     * 含义
     */
    private boolean cacheable = false;
    
    /**
     * 返回的实体类
     * @Title clazz
     * @type Class<?>
     * @date 2014-12-23 下午4:02:40
     * 含义
     */
    private Class<?> clazz;
    
    /**
     * @Title batchUpdateMap
     * @type Map<String,Object>
     * @date 2013-8-6 上午10:11:32
     * 含义 批量编辑数据库
     */
    private Map<String, Object> batchUpdateMap;
    
    /**
	 * @Project SC
	 * @Package com.hujz.framework.orm.hibernate.query
	 * @Description 不分页查询
	 * @author hujz
	 * @date 2014-3-2 下午12:17:08
	 */
	public QueryCondition(){
	}
	
	/**
	 * 分页查询
	 * @author jiuzhou.hu
	 * @date 2015年9月12日 下午12:58:41
	 * @param eachPageShowRows		查询条数
	 * @param pageNumber			查询第几页
	 */
	public QueryCondition(int eachPageShowRows,int pageNumber) {
		this(eachPageShowRows,pageNumber,true);
	}
	
	/**
	 * 分页查询
	 * @author jiuzhou.hu
	 * @date 2015年9月12日 下午12:58:53
	 * @param eachPageShowRows		查询条数
	 * @param pageNumber			查询第几页
	 * @param queryTotalCount		是否查询总条数
	 */
	public QueryCondition(int eachPageShowRows,int pageNumber,boolean queryTotalCount) {
		this.pageTools = new PageTools();
		this.pageTools.setPageSize(eachPageShowRows);
		this.pageTools.setPageNo(getStartIndex(pageNumber, eachPageShowRows));
		this.pageTools.setQueryTotalCount(queryTotalCount);
	}
    
    
    /**
     *likeEqualsMap的获取.
     * 
     * @return likeEqualsMap值返回.
     */
    public Map<String, Object> getLikeEqualsMap() {
        return likeEqualsMap;
    }

    /**
     *orderby的获取.
     *@return orderby值返回.
     */
    public List<OrderEntry> getOrderby() {
        return orderby;
    }

    /**
     *equalsMap的获取.
     * 
     * @return equalsMap值返回.
     */
    public Map<String, Object> getEqualsMap() {
        return equalsMap;
    }

    /**
     *notEqualsMap的获取.
     * 
     * @return notEqualsMap值返回.
     */
    public Map<String, Object> getNotEqualsMap() {
        return notEqualsMap;
    }

    /**
     *greateMap的获取.
     * 
     * @return greateMap值返回.
     */
    public Map<String, Object> getGreateMap() {
        return greateMap;
    }

    /**
     *greateEqualsMap的获取.
     * 
     * @return greateEqualsMap值返回.
     */
    public Map<String, Object> getGreateEqualsMap() {
        return greateEqualsMap;
    }

    /**
     *lessMap的获取.
     * 
     * @return lessMap值返回.
     */
    public Map<String, Object> getLessMap() {
        return lessMap;
    }

    /**
     *lessEqualsMap的获取.
     * 
     * @return lessEqualsMap值返回.
     */
    public Map<String, Object> getLessEqualsMap() {
        return lessEqualsMap;
    }

    /**
     *nullMap的获取.
     * 
     * @return nullMap值返回.
     */
    public Map<String, Object> getNullMap() {
        return nullMap;
    }

    /**
     *notNullMap的获取.
     * 
     * @return notNullMap值返回.
     */
    public Map<String, Object> getNotNullMap() {
        return notNullMap;
    }

    /**
     *inMap的获取.
     * 
     * @return inMap值返回.
     */
    public Map<String, List<Object>> getInMap() {
        return inMap;
    }

    /**
     *notInMap的获取.
     * 
     * @return notInMap值返回.
     */
    public Map<String, List<Object>> getNotInMap() {
        return notInMap;
    }

    /**
     *betweenInMap的获取.
     * 
     * @return betweenInMap值返回.
     */
    public Map<String, Object> getBetweenInMap() {
        return betweenInMap;
    }

    /**
     *notBetweenInMap的获取.
     * 
     * @return notBetweenInMap值返回.
     */
    public Map<String, Object> getNotBetweenInMap() {
        return notBetweenInMap;
    }

    /**
     *batchUpdateMap的获取.
     *@return batchUpdateMap值返回.
     */
    public Map<String, Object> getBatchUpdateMap() {
        return batchUpdateMap;
    }
    
    

    // ///////////////////////////////////////////////////////
    /**
     * 判断Map对象是否已经实例化
     * @author jiuzhou.hu
     * @date 2015年9月12日 下午12:59:42
     * @param map 被判断的Map对象
     * @return 已经实例化的Map对象
     * @return
     */
    private Map<String, Object> checkMap(Map<String, Object> map) {
        if(map == null) {
            return new HashMap<String, Object>(5);
        }
        return map;
    }
 

    // ////////////////////////////////////////////////
    /**
     * 模糊等于
     * @author jiuzhou.hu
     * @date 2013-8-6 上午10:12:15
     * @param key 	属性名称
     * @param value	值
     */
    public void put(String key, Object value) {
        likeEqualsMap = checkMap(likeEqualsMap);
        likeEqualsMap.put(key, value);
    }

    /**
     * 等于
     * @author jiuzhou.hu
     * @date 2013-8-6 上午10:12:41
     * @param key	属性名称
     * @param value	值
     */
    public void equals(String key, Object value) {
        equalsMap = checkMap(equalsMap);
        equalsMap.put(key, value);
    }

    /**
     * 不等于
     * @author jiuzhou.hu
     * @date 2013-8-6 上午10:12:58
     * @param key	属性名称
     * @param value	值
     */
    public void notEquals(String key, Object value) {
        notEqualsMap = checkMap(notEqualsMap);
        notEqualsMap.put(key, value);
    }

    /**
     * 大于
     * @author jiuzhou.hu
     * @date 2013-8-6 上午10:13:10
     * @param key	属性名称
     * @param value	值
     */
    public void greate(String key, Object value) {
        greateMap = checkMap(greateMap);
        greateMap.put(key, value);
    }

    /**
     * 大于等于
     * @author jiuzhou.hu
     * @date 2013-8-6 上午10:13:22
     * @param key	属性名称
     * @param value	值
     */
    public void greateEquals(String key, Object value) {
        greateEqualsMap = checkMap(greateEqualsMap);
        greateEqualsMap.put(key, value);
    }

    /**
     * 小于
     * @author jiuzhou.hu
     * @date 2013-8-6 上午10:13:34
     * @param key	属性名称
     * @param value	值
     */
    public void less(String key, Object value) {
        lessMap = checkMap(lessMap);
        lessMap.put(key, value);
    }

    /**
     * 小于等于
     * @author jiuzhou.hu
     * @date 2013-8-6 上午10:13:46
     * @param key	属性名称
     * @param value	值
     */
    public void lessEquals(String key, Object value) {
        lessEqualsMap = checkMap(lessEqualsMap);
        lessEqualsMap.put(key, value);
    }

    /**
     * 值为空
     * @author jiuzhou.hu
     * @date 2013-8-6 上午10:13:58
     * @param key	属性名称
     */
    public void isNull(String key) {
        nullMap = checkMap(nullMap);
        nullMap.put(key, key);
    }

    /**
     * 值非空
     * @author jiuzhou.hu
     * @date 2013-8-6 上午10:14:10
     * @param key	属性名称
     */
    public void isNotNull(String key) {
        notNullMap = checkMap(notNullMap);
        notNullMap.put(key, key);
    }

    /**
     * 等于列表中的某个值
     * @author jiuzhou.hu
     * @date 2013-8-6 上午10:14:21
     * @param key	属性名称
     * @param value	值
     */
    public void in(String key, List<Object> value) {
        if(null==inMap){
            inMap=new HashMap<String, List<Object>>(5);
        }
        inMap.put(key, value);
    }
    /**
     * 等于列表中的某个值
     * @author jiuzhou.hu
     * @date 2013-8-6 上午10:14:39
     * @param key	属性名称
     * @param value	值
     */
    public void in(String key, Object... value) {
        if(null==inMap){
            inMap=new HashMap<String, List<Object>>(5);
        }
        List<Object> list=new ArrayList<Object>();
        if(value!=null&&value.length>0){
            for(int i=0;i<value.length;i++){
                list.add(value[i]);
            }
        }
        inMap.put(key, list);
    }

    /**
     * 不等于列表中的任意一个值
     * @author jiuzhou.hu
     * @date 2013-8-6 上午10:15:10
     * @param key	属性名称
     * @param value	值
     */
    public void notIn(String key, List<Object> value) {
        if(null==notInMap){
            notInMap = new HashMap<String, List<Object>>(5);
        }
        notInMap.put(key, value);
    }
    
    /**
     * 等于列表中的某个值
     * @author jiuzhou.hu
     * @date 2013-8-6 上午10:14:39
     * @param key	属性名称
     * @param value	值
     */
    public void notIn(String key, Object... value) {
        if(null==notInMap){
        	notInMap = new HashMap<String, List<Object>>(5);
        }
        List<Object> list = new ArrayList<Object>();
        if(value!=null&&value.length>0){
            for(int i=0;i<value.length;i++){
                list.add(value[i]);
            }
        }
        notInMap.put(key, list);
    }

    /**
     * 大于等于某个值并且小于等于另外一个值
     * @author jiuzhou.hu
     * @date 2013-8-6 上午10:15:27
     * @param key		属性名称
     * @param beginValue
     * @param endValue
     */
    public void between(String key, Object beginValue,Object endValue) {
        betweenInMap = checkMap(betweenInMap);
        betweenInMap.put(key, beginValue+"_"+endValue);
    }

    /**
     * 小于开始值或者大于结束值
     * @author jiuzhou.hu
     * @date 2013-8-6 上午10:15:46
     * @param key
     * @param beginValue
     * @param endValue
     */
    public void notBetween(String key, Object beginValue,Object endValue) {
        notBetweenInMap = checkMap(notBetweenInMap);
        notBetweenInMap.put(key, beginValue+"_"+endValue);
    }

    /**
     * 小于开始值或者大于结束值
     * @author jiuzhou.hu
     * @date 2013-8-6 上午10:15:53
     * @param key
     * @param value
     */
    public void batchUpdate(String key, Object value) {
        batchUpdateMap = checkMap(batchUpdateMap);
        batchUpdateMap.put(key, value);
    }
    
    
    /**
     * 倒序排序，先调用本方法的后排序
     * @author hjz
     * @param 设定文件
     * @return 返回类型
     */
    public void order(String key, int direction) {
        OrderEntry oe = new OrderEntry(key, direction);
        if(orderby == null) {
            orderby=new ArrayList<OrderEntry>(5);
        }
        if(orderby.contains(oe)) {
            orderby.remove(oe);
        }
        orderby.add(0, new OrderEntry(key, direction));
    }

    /**
     * 升序
     * @author jiuzhou.hu
     * @date 2013-8-6 上午10:16:07
     * @param key
     */
    public void orderAsc(String key) {
        order(key, OrderEntry.ORDER_ASC);
    }

    /**
     * 降序
     * @author jiuzhou.hu
     * @date 2013-8-6 上午10:16:17
     * @param key
     */
    public void orderDesc(String key) {
        order(key, OrderEntry.ORDER_DESC);
    }
    
	/**
	 * 获得实体类条件
	 * @author jiuzhou.hu
	 * @date 2013-8-6 上午10:16:30
	 * @return
	 */
	public Object getEntry() {
		return entry;
	}
	
	/**
	 * 设置实体类条件
	 * @author jiuzhou.hu
	 * @date 2013-8-6 上午10:16:38
	 * @param entry
	 */
	public void setEntry(Object entry) {
		this.entry = entry;
	}
	
	/**
	 * 是否使用查询缓存
	 * 
	 * @return
	 */
	public boolean isCacheable() {
		return cacheable;
	}

	/**
	 * 设置是否使用查询缓存
	 * 
	 * @param cacheable
	 * @see Query#setCacheable(boolean)
	 */
	public void setCacheable(boolean cacheable) {
		this.cacheable = cacheable;
	}
	
	/**
	 * paramValues的获取.
	 * @return List<Object>
	 */
	public List<QueryPropert> getParams() {
		return params;
	}
	
	/**
	 * 设定paramValues的值.
	 * @param List<Object>
	 */
	public void setParams(String param, Object value) {
		this.setParams(param, value, null);
	}
	/**
	 * 设定paramValues的值.
	 * @param List<Object>
	 */
	public void setParams(String param, Object value, Object type) {
		this.params.add(new QueryPropert(param, value, type));
	}

	/**
	 * 设定likeEqualsMap的值.
	 * @param Map<String,Object>
	 */
	public void setLikeEqualsMap(Map<String, Object> likeEqualsMap) {
		this.likeEqualsMap = likeEqualsMap;
	}

	/**
	 * 设定equalsMap的值.
	 * @param Map<String,Object>
	 */
	public void setEqualsMap(Map<String, Object> equalsMap) {
		this.equalsMap = equalsMap;
	}

	/**
	 * 设定notEqualsMap的值.
	 * @param Map<String,Object>
	 */
	public void setNotEqualsMap(Map<String, Object> notEqualsMap) {
		this.notEqualsMap = notEqualsMap;
	}

	/**
	 * 设定greateMap的值.
	 * @param Map<String,Object>
	 */
	public void setGreateMap(Map<String, Object> greateMap) {
		this.greateMap = greateMap;
	}

	/**
	 * 设定greateEqualsMap的值.
	 * @param Map<String,Object>
	 */
	public void setGreateEqualsMap(Map<String, Object> greateEqualsMap) {
		this.greateEqualsMap = greateEqualsMap;
	}

	/**
	 * 设定lessMap的值.
	 * @param Map<String,Object>
	 */
	public void setLessMap(Map<String, Object> lessMap) {
		this.lessMap = lessMap;
	}

	/**
	 * 设定lessEqualsMap的值.
	 * @param Map<String,Object>
	 */
	public void setLessEqualsMap(Map<String, Object> lessEqualsMap) {
		this.lessEqualsMap = lessEqualsMap;
	}

	/**
	 * 设定nullMap的值.
	 * @param Map<String,Object>
	 */
	public void setNullMap(Map<String, Object> nullMap) {
		this.nullMap = nullMap;
	}

	/**
	 * 设定notNullMap的值.
	 * @param Map<String,Object>
	 */
	public void setNotNullMap(Map<String, Object> notNullMap) {
		this.notNullMap = notNullMap;
	}

	/**
	 * 设定inMap的值.
	 * @param Map<String,List<Object>>
	 */
	public void setInMap(Map<String, List<Object>> inMap) {
		this.inMap = inMap;
	}

	/**
	 * 设定notInMap的值.
	 * @param Map<String,List<Object>>
	 */
	public void setNotInMap(Map<String, List<Object>> notInMap) {
		this.notInMap = notInMap;
	}

	/**
	 * 设定betweenInMap的值.
	 * @param Map<String,Object>
	 */
	public void setBetweenInMap(Map<String, Object> betweenInMap) {
		this.betweenInMap = betweenInMap;
	}

	/**
	 * 设定notBetweenInMap的值.
	 * @param Map<String,Object>
	 */
	public void setNotBetweenInMap(Map<String, Object> notBetweenInMap) {
		this.notBetweenInMap = notBetweenInMap;
	}

	/**
	 * 设定orderby的值.
	 * @param List<OrderEntry>
	 */
	public void setOrderby(List<OrderEntry> orderby) {
		this.orderby = orderby;
	}
	
	/**
	 * clazz的获取.
	 * @return Class<?>
	 */
	public Class<?> getClazz() {
		return clazz;
	}

	/**
	 * 设定clazz的值.
	 * @param Class<?>
	 */
	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	/**
	 * 设定batchUpdateMap的值.
	 * @param Map<String,Object>
	 */
	public void setBatchUpdateMap(Map<String, Object> batchUpdateMap) {
		this.batchUpdateMap = batchUpdateMap;
	}
	
	public PageTools getPageTools() {
		return pageTools;
	}
	public void setPageTools(PageTools pageTools) {
		this.pageTools = pageTools;
	}
	
	private int getStartIndex(int curPage, int countPerPage) {
		if (curPage <= 0) {
			curPage = 1;
		}
		return curPage;
	}
	public String cacheKey() {
		return Md5Utils.md5(JSONUtils.toJSONString(this).getBytes());
	}
}
