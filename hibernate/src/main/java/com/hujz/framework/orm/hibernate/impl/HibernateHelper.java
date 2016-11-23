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
import com.hujz.framework.orm.util.ObjectUtils;
import com.hujz.framework.orm.util.QueryCondition;
import com.hujz.framework.orm.util.QueryResult;
import com.hujz.framework.orm.util.RandomUtils;
import com.hujz.framework.orm.util.StringUtils;

/**
 *********************************************** 
 * Hibernate通用Dao系统
 * @Function:  1：根据QueryCondition组合HQl的条件<br>
 *				2：通过Hibernate查询或修改或删除或保存实体对象<br>
 * @Create Author: jiuzhou.hu
 * @Last Update Date:
 * @Change Log:
 ************************************************* 
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class HibernateHelper {
	
    /**
     * @author jiuzhou.hu
     * @date 2013-8-6 上午10:17:32
     * @param session
     * @param clazz
     * @param qc
     */
    public static final int batchUpdateHqlByQueryCondition(Session session,ClassMetadata metadata, QueryCondition qc) {
        // 获取组合的条件
    	String hql = buildUpdateHql(metadata, qc);
        String condition = QueryUtil.buildConditionByQueryCondition(metadata, qc).toString();
        
        if(StringUtils.isNotEmpty(condition) && condition.length() > 0) {
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
     * 执行批量删除
     * @author jiuzhou.hu
     * @date 2013-8-6 上午10:17:41
     * @param session
     * @param clazz
     * @param qc
     */
    public static final int batchDeleteHqlByQueryCondition(Session session, ClassMetadata metadata, QueryCondition qc) {
        // 获取组合的条件
    	String hql = buildDeleteHql(metadata);
        String condition = QueryUtil.buildConditionByQueryCondition(metadata, qc).toString();
        
        if(StringUtils.isNotEmpty(condition) && condition.length() > 0) {
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
     * 使用原生的标准数据库sql
     * @author jiuzhou.hu
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
     * Hql查询
     * @author jiuzhou.hu
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
    	String alias = ObjectUtils.getBaseClassName(metadata.getEntityName()).toLowerCase();
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
     * Hql查询
     * @author jiuzhou.hu
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
    	String alias = ObjectUtils.getBaseClassName(metadata.getEntityName()).toLowerCase();
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
     * 批量修改
     * @author jiuzhou.hu
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
        	String objectAlias = ObjectUtils.getBaseClassName(metadata.getEntityName()).toLowerCase();
            hql.append("update ");
            hql.append(metadata.getEntityName());
            hql.append(" as ");
            hql.append(ObjectUtils.getBaseClassName(metadata.getEntityName()).toLowerCase());
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
     * 批量删除
     * @author jiuzhou.hu
     * @date 2013-8-6 上午10:18:22
     * @param clazz
     * @param alias
     * @param condition
     * @return
     */
    private static final String buildDeleteHql(ClassMetadata metadata) {
    	String objectAlias = ObjectUtils.getBaseClassName(metadata.getEntityName()).toLowerCase();
        StringBuilder hql = new StringBuilder();
        hql.append("delete ");
        hql.append(metadata.getEntityName());
        hql.append(" as ");
        hql.append(objectAlias);

        return hql.toString();
    }

    /**
     * 查询Hql
     * @author jiuzhou.hu
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
        hql.append(ObjectUtils.getBaseClassName(metadata.getEntityName()).toLowerCase());
        if(StringUtils.isNotEmpty(condition) && condition.length() > 0) {
            hql.append(" where 1 = 1 ").append(condition);
        }

        return hql.toString();
    }

    /**
     * 排序
     * @author jiuzhou.hu
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
                if(StringUtils.isEmpty(orderKey)) {
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
