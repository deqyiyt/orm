/**
 *copyright(C)2009
 *大众科技有限公司 & Service Corporation All rights reserved
 */
package com.hujz.framework.orm.hibernate.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.type.Type;

import com.hujz.framework.orm.bean.OrderEntry;
import com.hujz.framework.orm.bean.PageTools;
import com.hujz.framework.orm.bean.QueryPropert;
import com.hujz.framework.orm.hibernate.query.QueryUtil;
import com.hujz.framework.orm.util.QueryCondition;
import com.hujz.framework.orm.util.QueryResult;
import com.hujz.soasoft.util.RandomUtils;
import com.hujz.soasoft.util.type.ObjectUtil;
import com.hujz.soasoft.util.type.StringUtil;

/**
 *<b>系统名称:</b><b> &nbsp;&nbsp;&nbsp;&nbsp;
 * 
 *Hibernate通用Dao系统</b> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br>
 * 
 *<b>子系统名：</b><br>
 *&nbsp;&nbsp;&nbsp;&nbsp;
 * 
 *Hibernate通用条件类<br>
 * 
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br>
 * 
 *<b>文件名:</b><br>
 *&nbsp;&nbsp;&nbsp;&nbsp;
 * 
 *HibernateHelperNew.java<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br>
 * 
 *<b>类名:</b><br>
 *&nbsp;&nbsp;&nbsp;&nbsp;
 * 
 *HibernateHelperNew类.<br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br>
 * 
 *<br>
 *<b>概要说明</b><br>
 *&nbsp;&nbsp;&nbsp;&nbsp;
 * 
 * 
 *HibernateHelperNew类的概要说明<br>
 **&nbsp;*&nbsp;*&nbsp;*&nbsp;*&nbsp;*&nbsp;*&nbsp;
 * 
 *1：根据QueryCondition组合HQl的条件<br>
 *2：通过Hibernate查询或修改或删除或保存实体对象<br>
 * 
 *<b>***History*** </b/><br>
 *更新年月日， 更改原因， 姓名， 更新内容<br>
 *2009-8-29, 99999, hujiuzhou@hotoa.com, 新建<br>
 * 
 *@author :hujiuzhou@hotoa.com
 *@since :2009-8-29
 *@version:1.0
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class HibernateHelper {
	
    /**
     * @Project SB
     * @Package com.hujz.framework.orm.hibernate.impl
     * @Method batchUpdateHqlByQueryCondition方法.<br>
     * @Description TODO 执行批量编辑
     * @author 胡久洲
     * @date 2013-8-6 上午10:17:32
     * @param session
     * @param clazz
     * @param qc
     */
    public static final int batchUpdateHqlByQueryCondition(Session session,ClassMetadata metadata, QueryCondition qc) {
        // 获取组合的条件
    	String hql = buildUpdateHql(metadata, qc);
        String condition = QueryUtil.buildConditionByQueryCondition(metadata, qc).toString();
        
        if(!StringUtil.empty(condition) && condition.length() > 0) {
            hql += " where 1 = 1 ";
            hql += condition;
        }
       
        Query query = session.createQuery(hql);
        for (QueryPropert propert : qc.getParams()) {
        	if(propert.getValue() instanceof Collection){
        		query.setParameterList(propert.getParam(), (Collection)propert.getValue(), (Type)propert.getType());
        	}else{
        		query.setParameter(propert.getParam(), propert.getValue(), (Type)propert.getType());
        	}
		}
        qc.getParams().clear();
        return query.executeUpdate();
    }

    /**
     * @Project SB
     * @Package com.hujz.framework.orm.hibernate.impl
     * @Method batchDeleteHqlByQueryCondition方法.<br>
     * @Description TODO 执行批量删除
     * @author 胡久洲
     * @date 2013-8-6 上午10:17:41
     * @param session
     * @param clazz
     * @param qc
     */
    public static final int batchDeleteHqlByQueryCondition(Session session, ClassMetadata metadata, QueryCondition qc) {
        // 获取组合的条件
    	String hql = buildDeleteHql(metadata);
        String condition = QueryUtil.buildConditionByQueryCondition(metadata, qc).toString();
        
        if(!StringUtil.empty(condition) && condition.length() > 0) {
            hql += " where 1 = 1 ";
            hql += condition;
        }
        
        Query query = session.createQuery(hql);
        for (QueryPropert propert : qc.getParams()) {
        	if(propert.getValue() instanceof Collection){
        		query.setParameterList(propert.getParam(), (Collection)propert.getValue(), (Type)propert.getType());
        	}else{
        		query.setParameter(propert.getParam(), propert.getValue(), (Type)propert.getType());
        	}
		}
        qc.getParams().clear();
        return query.executeUpdate();
    }

    /**
     * @Project SB
     * @Package com.hujz.framework.orm.hibernate.impl
     * @Method selectDbSql方法.<br>
     * @Description TODO 使用原生的标准数据库sql
     * @author 胡久洲
     * @date 2013-8-6 上午10:17:48
     * @param session
     * @param clazz
     * @param qc
     * @param qr
     * @param dbSql
     * @param countSql
     * @throws HibernateException
     */
    public static final void selectDbSql(Session session, Class clazz,
            QueryCondition qc, QueryResult qr, String dbSql,String countSql)
            throws HibernateException {
        Query query = session.createSQLQuery(dbSql).addEntity(clazz);
        // 设置翻页
        PageTools pageTools = qc.getPageTools();
        setQueryPage(query, pageTools);
        //
        List<Query> listQuery = query.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY).list();
        qr.addAll(listQuery);
        // 总数查询
        Query queryCount = session.createSQLQuery(countSql);
        Long totalCountl = (Long)queryCount.iterate().next();
        int totalCount = totalCountl.intValue();
        pageTools.setRecordCount(totalCount);
        qr.setPageTools(pageTools);
    }

    /**
     * @Project SB
     * @Package com.hujz.framework.orm.hibernate.impl
     * @Method selectHqlByQueryCondition方法.<br>
     * @Description TODO Hql查询
     * @author 胡久洲
     * @date 2013-8-6 上午10:18:03
     * @param session
     * @param clazz
     * @param qc
     * @param qr
     * @throws HibernateException
     */
    public static final void selectHqlByQueryCondition(Session session,
    		ClassMetadata metadata, QueryCondition qc, List qr)
            throws HibernateException {
    	String alias = ObjectUtil.getBaseClassName(metadata.getEntityName()).toLowerCase();
        // 获取组合的条件
        String condition = QueryUtil.buildConditionByQueryCondition(metadata, qc).toString();
        StringBuffer hql = new StringBuffer(buildQueryHql(metadata, condition));
        hql.append(addOrderby(qc, alias).toString());
        Query query = session.createQuery(hql.toString());
        for (QueryPropert propert : qc.getParams()) {
        	if(propert.getValue() instanceof Collection){
        		query.setParameterList(propert.getParam(), (Collection)propert.getValue(), (Type)propert.getType());
        	}else{
        		query.setParameter(propert.getParam(), propert.getValue(), (Type)propert.getType());
        	}
		}
        // 设置翻页
        PageTools pageTools = qc.getPageTools();
        setQueryPage(query, pageTools);
        
        if (qc.isCacheable()) {
			query.setCacheable(true);
		}
        
        if(pageTools != null && pageTools.getPageSize() > 0){
        	setQueryPage(query, pageTools);
        	if(pageTools.isQueryTotalCount()) {
	        	// 总数查询
	        	hql.delete(0, hql.length())
	        		.append("select count(")
	            	.append(alias).append(".").append(metadata.getIdentifierPropertyName())
	            	.append(") from ").append(metadata.getEntityName()).append(" as ").append(alias)
	            	.append(" where 1 = 1 ")
	            	.append(condition);
	            Query queryCount = session.createQuery(hql.toString());
	            for (QueryPropert propert : qc.getParams()) {
	            	if(propert.getValue() instanceof Collection){
	            		queryCount.setParameterList(propert.getParam(), (Collection)propert.getValue(), (Type)propert.getType());
	            	}else{
	            		queryCount.setParameter(propert.getParam(), propert.getValue(), (Type)propert.getType());
	            	}
	    		}
	            Long totalCountl = (Long)queryCount.iterate().next();
	            int totalCount = totalCountl.intValue();
	            pageTools.setRecordCount(totalCount);
        	}
        }
        List<Query> listQuery = query.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY).list();
        qr.addAll(listQuery);
        qc.getParams().clear();
    }
    
    /**
     * @Project SB
     * @Package com.hujz.framework.orm.hibernate.impl
     * @Method selectHqlByQueryCondition方法.<br>
     * @Description TODO Hql查询
     * @author 胡久洲
     * @date 2013-8-6 上午10:18:03
     * @param session
     * @param clazz
     * @param qc
     * @param qr
     * @throws HibernateException
     */
    public static final Long selectHqlByQueryCountCondition(Session session,
    		ClassMetadata metadata, QueryCondition qc)
            throws HibernateException {
    	String alias = ObjectUtil.getBaseClassName(metadata.getEntityName()).toLowerCase();
        // 获取组合的条件
        String condition = QueryUtil.buildConditionByQueryCondition(metadata, qc).toString();
        
        	// 总数查询
        StringBuffer hql = new StringBuffer()
    		.append("select count(")
        	.append(alias).append(".").append(metadata.getIdentifierPropertyName())
        	.append(") from ").append(metadata.getEntityName()).append(" as ").append(alias)
        	.append(" where 1 = 1 ")
        	.append(condition);
            Query queryCount = session.createQuery(hql.toString());
            for (QueryPropert propert : qc.getParams()) {
            	if(propert.getValue() instanceof Collection){
            		queryCount.setParameterList(propert.getParam(), (Collection)propert.getValue(), (Type)propert.getType());
            	}else{
            		queryCount.setParameter(propert.getParam(), propert.getValue(), (Type)propert.getType());
            	}
    		}
        return (Long)queryCount.iterate().next();
    }

    // 设置查询页
    public static final void setQueryPage(Query query,
    		PageTools queryPageJavaBean) {
        if(null != queryPageJavaBean) {
            int startIndex = queryPageJavaBean.getStartRow();
            int eachPageShowRows = queryPageJavaBean.getPageSize();
            // 小于0表示全选
            if(eachPageShowRows >= 0) {
                query.setMaxResults(eachPageShowRows);
            }

            // 由于web传入的参数是1起始的，而hibernate是0起始的，因此进行减1处理。
            
           //startIndex--;
            if(startIndex <= 0) {
                startIndex = 0;
            }
            query.setFirstResult(startIndex);
        }

    }

    /**
     * @Project SB
     * @Package com.hujz.framework.orm.hibernate.impl
     * @Method buildUpdateHql方法.<br>
     * @Description TODO 批量修改
     * @author 胡久洲
     * @date 2013-8-6 上午10:18:15
     * @param clazz
     * @param alias
     * @param qc
     * @param condition
     * @return
     */
    private static final String buildUpdateHql(ClassMetadata metadata, QueryCondition qc) {
        StringBuilder hql = new StringBuilder();
        // 组合修改的内容
        Map<String, Object> batchUpdateMap = qc.getBatchUpdateMap();
        int i = 0;
        if(null != batchUpdateMap && batchUpdateMap.size() > 0) {
        	String objectAlias = ObjectUtil.getBaseClassName(metadata.getEntityName()).toLowerCase();
            hql.append("update ");
            hql.append(metadata.getEntityName());
            hql.append(" as ");
            hql.append(ObjectUtil.getBaseClassName(metadata.getEntityName()).toLowerCase());
            hql.append(" set ");
            for(Iterator<Map.Entry<String, Object>> it = batchUpdateMap
                    .entrySet().iterator(); it.hasNext();) {
            	Map<String,Object> map = new HashMap<String,Object>();
                Map.Entry<String, Object> entry = (Map.Entry<String, Object>)it.next();
                String condKey = entry.getKey();
                Object condVal = entry.getValue();
                String fieldAlist = condKey + RandomUtils.getRandomValue(5);
                hql.append(objectAlias).append(".").append(condKey).append(" = :").append(fieldAlist);
                Type type = metadata.getPropertyType(condKey);
				map.put(fieldAlist, QueryUtil.getValue(type,condVal));
                qc.setParams(fieldAlist, QueryUtil.getValue(type,condVal), type);
                if(i != batchUpdateMap.size() - 1) {
                    hql.append(",");
                }
                i++;
            }
        }

        return hql.toString();
    }

    /**
     * @Project SB
     * @Package com.hujz.framework.orm.hibernate.impl
     * @Method buildDeleteHql方法.<br>
     * @Description TODO 批量删除
     * @author 胡久洲
     * @date 2013-8-6 上午10:18:22
     * @param clazz
     * @param alias
     * @param condition
     * @return
     */
    private static final String buildDeleteHql(ClassMetadata metadata) {
    	String objectAlias = ObjectUtil.getBaseClassName(metadata.getEntityName()).toLowerCase();
        StringBuilder hql = new StringBuilder();
        hql.append("delete ");
        hql.append(metadata.getEntityName());
        hql.append(" as ");
        hql.append(objectAlias);

        return hql.toString();
    }

    /**
     * @Project SB
     * @Package com.hujz.framework.orm.hibernate.impl
     * @Method buildQueryHql方法.<br>
     * @Description TODO 查询Hql
     * @author 胡久洲
     * @date 2013-8-6 上午10:18:30
     * @param clazz
     * @param alias
     * @param condition
     * @return
     */
    private static final String buildQueryHql(ClassMetadata metadata, String condition) {
        StringBuilder hql = new StringBuilder();
        hql.append(" from ");
        hql.append(metadata.getEntityName());
        hql.append(" as ");
        hql.append(ObjectUtil.getBaseClassName(metadata.getEntityName()).toLowerCase());
        if(!StringUtil.empty(condition) && condition.length() > 0) {
            hql.append(" where 1 = 1 ").append(condition);
        }

        return hql.toString();
    }

    /**
     * @Project SB
     * @Package com.hujz.framework.orm.hibernate.impl
     * @Method addOrderby方法.<br>
     * @Description TODO 排序
     * @author 胡久洲
     * @date 2013-8-6 上午10:18:36
     * @param qc
     * @param objectAlias
     * @return
     */
    private static final String addOrderby(QueryCondition qc, String objectAlias) {
        List<OrderEntry> orderList = qc.getOrderby();
        StringBuffer buffer = new StringBuffer("");
        if(null != orderList) {
            buffer.append(" order by ");
            for(int i = 0; i < orderList.size(); i++) {
                OrderEntry orderEntry = (OrderEntry)orderList.get(i);
                String orderKey = (String)orderEntry.getKey();
                if(StringUtil.empty(orderKey)) {
                    continue;
                }
                if(i > 0) {
                    buffer.append(", ");
                }
                // if (OrderEntry.ORDER_ORACLE_FUNCTION ==
                // orderEntry.getDirection()) {//表示是oracle 的函数，不用转换，直接作为sql排序条件
                // buffer.append(orderKey);
                // } else
                if(OrderEntry.ORDER_DESC == orderEntry.getOrder()) {
                    buffer.append(objectAlias);
                    buffer.append(".");
                    buffer.append(orderKey);
                    buffer.append(" ");
                    buffer.append(" desc ");
                    buffer.append(" ");
                    // criteria.addOrder(Order.desc(orderKey));
                }else {
                    buffer.append(objectAlias);
                    buffer.append(".");
                    buffer.append(orderKey);
                    buffer.append(" ");
                    buffer.append(" asc ");
                }
            }
        }
        return buffer.toString();
    }
}
