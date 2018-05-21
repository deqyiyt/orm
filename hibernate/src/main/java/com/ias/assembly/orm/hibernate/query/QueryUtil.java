package com.ias.assembly.orm.hibernate.query;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.type.Type;

import com.ias.assembly.orm.basic.bean.OrderEntry;
import com.ias.assembly.orm.basic.bean.PageTools;
import com.ias.assembly.orm.basic.util.QueryCondition;
import com.ias.common.utils.bean.ClassUtil;
import com.ias.common.utils.date.TimeUtil;
import com.ias.common.utils.number.NumberUtils;
import com.ias.common.utils.random.RandomUtils;
import com.ias.common.utils.string.StringUtil;

public class QueryUtil {
	
	private QueryUtil(){}
	
	public static final String ORDER_BY = "ORDER";
	
	public static final String FROM = "FROM";
	
	public static final String HQL_FETCH = "FETCH";
	
	public static final String GROUP_BY = "GROUP";
	
	public static final String ROW_COUNT = "SELECT COUNT(*) ";
	
	/**
	 * 添加分页
	 * @Title: setQueryPage
	 * @author hjz
	 * @param 设定文件
	 * @return 返回类型
	 */
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
            if(startIndex <= 1) {
                startIndex = 0;
            }
            query.setFirstResult(startIndex);
        }

    }
	
    
 // 组合Hql的条件
    public static final StringBuilder buildConditionByQueryCondition(ClassMetadata metadata, QueryCondition qc) {
        StringBuilder builder = buildConditionByEntry(metadata, qc);
        // //////////////////////////////////////////////////
        // 模糊等于
        addLikeEquals(qc, metadata, builder);
        // 等于
        addEquals(qc, metadata, builder);
        // 不等于
        addNotEquals(qc, metadata, builder);
        // 大于
        addGreate(qc, metadata, builder);
        // 大于等于
        addGreateEquals(qc, metadata, builder);
        // 小于
        addLess(qc, metadata, builder);
        // 小于等于
        addLessEquals(qc, metadata, builder);
        // 值为空
        addNull(qc, metadata, builder);
        // 值非空
        addNotNull(qc, metadata, builder);
        // 等于列表中的某个值
        addIn(qc, metadata, builder);
        // 不等于列表中的任意一个值
        addNotIn(qc, metadata, builder);
        // 大于等于某个值并且小于等于另外一个值
        addBetweenIn(qc, metadata, builder);
        // 小于开始值或者大于结束值
        addNotBetweenIn(qc, metadata, builder);

        // //////////////////////////////////////////////////
        return builder;
    }
    
    public static final StringBuilder buildConditionByEntry(ClassMetadata metadata, QueryCondition qc){
    	StringBuilder builder = new StringBuilder();
    	if(qc.getEntry()!=null){
	    	Class<?> dtoClass = qc.getEntry().getClass();
			Field[] fields = dtoClass.getDeclaredFields();
			String objectAlias = ClassUtil.getBaseClassName(metadata.getEntityName()).toLowerCase();
			for (short i = 0; i < fields.length; i++) {
				String fieldName = fields[i].getName();
				Object value = ClassUtil.getProperty(qc.getEntry(), fieldName);
				if (value == null) {
					continue;
				} else{
					String alist = fieldName + RandomUtils.getRandomValue(5);
					builder.append(" and ")
							.append(objectAlias).append(".").append(fieldName)
							.append(" = :").append(alist);
					Type type = metadata.getPropertyType(fieldName);
	                qc.setParams(alist, getValue(type,value), type);
				}
			}
    	}
    	return builder;
    }
    
    /**
     * 拼接排序语句
     * @Title: addOrderby
     * @author hjz
     * @param 设定文件
     * @return 返回类型
     */
    public static final String addOrderby(QueryCondition qc,
            String objectAlias) {
        List<OrderEntry> orderList = qc.getOrderby();
        StringBuffer buffer = new StringBuffer("");
        if(null != orderList) {
            buffer.append(" order by ");
            for(int i = 0; i < orderList.size(); i++) {
                OrderEntry orderEntry = (OrderEntry)orderList.get(i);
                String orderKey = (String)orderEntry.getKey();
                if(StringUtil.isEmpty(orderKey)) {
                    continue;
                }
                if(i > 0) {
                    buffer.append(", ");
                }
                if(OrderEntry.ORDER_DESC == orderEntry.getOrder()) {
                    buffer.append(objectAlias);
                    buffer.append(".");
                    buffer.append(orderKey);
                    buffer.append(" ");
                    buffer.append(" desc ");
                    buffer.append(" ");
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

    /**
     * 小于开始值或者大于结束值
     * @author hjz
     * @param 设定文件
     * @return 返回类型
     */
    private static final void addNotBetweenIn(QueryCondition qc, ClassMetadata metadata, StringBuilder builder) {

        Map<String, Object> notBetweenMap = qc.getNotBetweenInMap();
        //
        if(null != notBetweenMap && notBetweenMap.size() > 0) {
        	String objectAlias = ClassUtil.getBaseClassName(metadata.getEntityName()).toLowerCase();
            for(Iterator<Map.Entry<String, Object>> it = notBetweenMap
                    .entrySet().iterator(); it.hasNext();) {
                Map.Entry<String, Object> entry = (Map.Entry<String, Object>)it
                        .next();
                String condKey = entry.getKey();
                Object condVal = entry.getValue();
                Object[] condVals = StringUtil.split((String)condVal, "_");
                if(null != condVal) {
					String alist = condKey + RandomUtils.getRandomValue(5);
					String alistEnd = condKey + RandomUtils.getRandomValue(5);
                    builder.append(" and ")
                    		.append(" ").append(objectAlias).append(".").append(condKey)
                    		.append(" not between :").append(alist)
                    		.append(" and :").append(alistEnd);
                    Type type = metadata.getPropertyType(condKey);
	                qc.setParams(alist, getValue(type,condVals[0]), type);
	                qc.setParams(alistEnd, getValue(type,condVals[1]), type);
                }
            }
        }

    }

    /**
     * 大于等于某个值并且小于等于另外一个值
     * @author hjz
     * @param 设定文件
     * @return 返回类型
     */
    private static final void addBetweenIn(QueryCondition qc, ClassMetadata metadata, StringBuilder builder) {
        Map<String, Object> betweenMap = qc.getBetweenInMap();
        //
        if(null != betweenMap && betweenMap.size() > 0) {
        	String objectAlias = ClassUtil.getBaseClassName(metadata.getEntityName()).toLowerCase();
            for(Iterator<Map.Entry<String, Object>> it = betweenMap.entrySet()
                    .iterator(); it.hasNext();) {
                Map.Entry<String, Object> entry = (Map.Entry<String, Object>)it
                        .next();
                String condKey = entry.getKey();
                Object condVal = entry.getValue();
                Object[] condVals = StringUtil.split((String)condVal, "_");
                if(null != condVal) {
					String alist = condKey + RandomUtils.getRandomValue(5);
					String alistEnd = condKey + RandomUtils.getRandomValue(5);
							 builder.append(" and ")
		             		.append(" ").append(objectAlias).append(".").append(condKey)
		             		.append(" between :").append(alist)
		             		.append(" and :").append(alistEnd);
					 Type type = metadata.getPropertyType(condKey);
		             qc.setParams(alist, getValue(type,condVals[0]), type);
		             qc.setParams(alistEnd, getValue(type,condVals[1]), type);
                }
            }
        }
    }

    /**
     * 不等于列表中的任意一个值
     * @author hjz
     * @param 设定文件
     * @return 返回类型
     */
    private static final void addNotIn(QueryCondition qc, ClassMetadata metadata, StringBuilder builder) {
        Map<String, List<Object>> notInMap = qc.getNotInMap();
        //
        if(null != notInMap && notInMap.size() > 0) {
        	String objectAlias = ClassUtil.getBaseClassName(metadata.getEntityName()).toLowerCase();
            for(Iterator<Map.Entry<String, List<Object>>> it = notInMap
                    .entrySet().iterator(); it.hasNext();) {
                Map.Entry<String, List<Object>> entry = (Map.Entry<String, List<Object>>)it
                        .next();
                String condKey = entry.getKey();
                List<Object> condVal = (List<Object>)entry.getValue();
                if(null != condVal && condVal.size() > 0) {
					String alist = condKey + RandomUtils.getRandomValue(5);
                    builder.append(" and ")
                    		.append(" ").append(objectAlias).append(".").append(condKey)
                        	.append(" not in (:").append(alist).append(") ");
                    Type type = metadata.getPropertyType(condKey);
                    List<Object> list = new ArrayList<Object>();
                    for(Object val:condVal){
                    	list.add(getValue(type,val));
                    }
                    
                    qc.setParams(alist, list, type);
                }
            }
        }

    }

    /**
     * 等于列表中的某个值
     * @author hjz
     * @param 设定文件
     * @return 返回类型
     */
    private static final void addIn(QueryCondition qc, ClassMetadata metadata, StringBuilder builder) {
        Map<String, List<Object>> inMap = qc.getInMap();
        //
        if(null != inMap && inMap.size() > 0) {
        	String objectAlias = ClassUtil.getBaseClassName(metadata.getEntityName()).toLowerCase();
            for(Iterator<Map.Entry<String, List<Object>>> it = inMap.entrySet()
                    .iterator(); it.hasNext();) {
                Map.Entry<String, List<Object>> entry = (Map.Entry<String, List<Object>>)it
                        .next();
                String condKey = entry.getKey();
                List<Object> condVal = (List<Object>)entry.getValue();
                if(null != condVal && condVal.size() > 0) {
                	Type type = metadata.getPropertyType(condKey);
                    List<Object> list = new ArrayList<Object>();
                    for(Object val:condVal){
                    	list.add(getValue(type,val));
                    }
                	
					String alist = condKey + RandomUtils.getRandomValue(5);
                    builder.append(" and ")
                    		.append(" ").append(objectAlias).append(".").append(condKey)
                        	.append(" in (:").append(alist).append(") ");
                    
                    qc.setParams(alist, list, type);
                }
            }
        }
    }

    /**
     * 值非空
     * @author hjz
     * @param 设定文件
     * @return 返回类型
     */
    private static final void addNotNull(QueryCondition qc,
    		ClassMetadata metadata, StringBuilder builder) {
        Map<String, Object> notNullMap = qc.getNotNullMap();
        //
        if(null != notNullMap && notNullMap.size() > 0) {
        	String objectAlias = ClassUtil.getBaseClassName(metadata.getEntityName()).toLowerCase();
            for(Iterator<Map.Entry<String, Object>> it = notNullMap.entrySet()
                    .iterator(); it.hasNext();) {
                Map.Entry<String, Object> entry = (Map.Entry<String, Object>)it
                        .next();
                String condKey = entry.getKey();
                Object condVal = entry.getValue();
                if(null != condVal) {
                	builder.append(" and ( ")
                	.append(objectAlias)
                	.append(".")
                	.append(condKey)
                	.append(" is not null or ")
                	.append(objectAlias)
                	.append(".")
                	.append(condKey)
                	.append(" <> '' ) ");
                }
            }
        }

    }

    /**
     * 值为空
     * @author hjz
     * @param 设定文件
     * @return 返回类型
     */
    private static final void addNull(QueryCondition qc, ClassMetadata metadata,
            StringBuilder builder) {
        Map<String, Object> nullMap = qc.getNullMap();
        //
        if(null != nullMap && nullMap.size() > 0) {
        	String objectAlias = ClassUtil.getBaseClassName(metadata.getEntityName()).toLowerCase();
            for(Iterator<Map.Entry<String, Object>> it = nullMap.entrySet()
                    .iterator(); it.hasNext();) {
                Map.Entry<String, Object> entry = (Map.Entry<String, Object>)it
                        .next();
                String condKey = entry.getKey();
                Object condVal = entry.getValue();
                if(null != condVal) {
                    builder.append(" and ( ")
                    	.append(objectAlias)
                    	.append(".")
                    	.append(condKey)
                    	.append(" is null or ")
                    	.append(objectAlias)
                    	.append(".")
                    	.append(condKey)
                    	.append(" = '' ) ");
                }
            }
        }

    }

    /**
     * 小于等于
     * @author hjz
     * @param 设定文件
     * @return 返回类型
     */
    private static final void addLessEquals(QueryCondition qc,
    		ClassMetadata metadata, StringBuilder builder) {
        Map<String, Object> lessEqualsMap = qc.getLessEqualsMap();
        //
        if(null != lessEqualsMap && lessEqualsMap.size() > 0) {
        	String objectAlias = ClassUtil.getBaseClassName(metadata.getEntityName()).toLowerCase();
            for(Iterator<Map.Entry<String, Object>> it = lessEqualsMap
                    .entrySet().iterator(); it.hasNext();) {
                Map.Entry<String, Object> entry = (Map.Entry<String, Object>)it
                        .next();
                String condKey = entry.getKey();
                Object condVal = entry.getValue();
                if(null != condVal) {
					String alist = condKey + RandomUtils.getRandomValue(5);
                    builder.append(" and ")
                    		.append(" ").append(objectAlias).append(".").append(condKey)
                			.append(" ").append(" <= :").append(alist);
                    Type type = metadata.getPropertyType(condKey);
                    qc.setParams(alist, getValue(type,condVal), type);
                }
            }
        }

    }

    /**
     * 小于
     * @author hjz
     * @param 设定文件
     * @return 返回类型
     */
    private static final void addLess(QueryCondition qc, ClassMetadata metadata,
            StringBuilder builder) {
        Map<String, Object> lessMap = qc.getLessMap();
        //
        if(null != lessMap && lessMap.size() > 0) {
        	String objectAlias = ClassUtil.getBaseClassName(metadata.getEntityName()).toLowerCase();
            for(Iterator<Map.Entry<String, Object>> it = lessMap.entrySet()
                    .iterator(); it.hasNext();) {
                Map.Entry<String, Object> entry = (Map.Entry<String, Object>)it
                        .next();
                String condKey = entry.getKey();
                Object condVal = entry.getValue();
                if(null != condVal) {
					String alist = condKey + RandomUtils.getRandomValue(5);
                    builder.append(" and ")
                    		.append(" ").append(objectAlias).append(".").append(condKey)
                			.append(" ").append(" < :").append(alist);
                    Type type = metadata.getPropertyType(condKey);
                    qc.setParams(alist, getValue(type,condVal), type);
                }
            }
        }

    }

    /**
     * 大于等于
     * @author hjz
     * @param 设定文件
     * @return 返回类型
     */
    private static final void addGreateEquals(QueryCondition qc,
    		ClassMetadata metadata, StringBuilder builder) {
        Map<String, Object> greatEqualsMap = qc.getGreateEqualsMap();
        //
        if(null != greatEqualsMap && greatEqualsMap.size() > 0) {
        	String objectAlias = ClassUtil.getBaseClassName(metadata.getEntityName()).toLowerCase();
            for(Iterator<Map.Entry<String, Object>> it = greatEqualsMap
                    .entrySet().iterator(); it.hasNext();) {
                Map.Entry<String, Object> entry = (Map.Entry<String, Object>)it
                        .next();
                String condKey = entry.getKey();
                Object condVal = entry.getValue();
                if(null != condVal) {
					String alist = condKey + RandomUtils.getRandomValue(5);
                    builder.append(" and ")
                    		.append(" ").append(objectAlias).append(".").append(condKey)
                			.append(" ").append(" >= :").append(alist);
                    Type type = metadata.getPropertyType(condKey);
                    qc.setParams(alist, getValue(type,condVal), type);
                }
            }
        }

    }

    /**
     * 大于
     * @author hjz
     * @param 设定文件
     * @return 返回类型
     */
    private static final void addGreate(QueryCondition qc,
    		ClassMetadata metadata, StringBuilder builder) {
        Map<String, Object> greatMap = qc.getGreateMap();
        //
        if(null != greatMap && greatMap.size() > 0) {
        	String objectAlias = ClassUtil.getBaseClassName(metadata.getEntityName()).toLowerCase();
            for(Iterator<Map.Entry<String, Object>> it = greatMap.entrySet()
                    .iterator(); it.hasNext();) {
                Map.Entry<String, Object> entry = (Map.Entry<String, Object>)it
                        .next();
                String condKey = entry.getKey();
                Object condVal = entry.getValue();
                if(null != condVal) {
					String alist = condKey + RandomUtils.getRandomValue(5);
                    builder.append(" and ")
                    		.append(" ").append(objectAlias).append(".").append(condKey)
                			.append(" ").append(" > :").append(alist);
                    Type type = metadata.getPropertyType(condKey);
                    qc.setParams(alist, getValue(type,condVal), type);
                }
            }
        }

    }
    
    /**
     * 不等于
     * @author hjz
     * @param 设定文件
     * @return 返回类型
     */
    private static final void addNotEquals(QueryCondition qc,
    		ClassMetadata metadata, StringBuilder builder) {
        Map<String, Object> notEqualsMap = qc.getNotEqualsMap();
        //
        if(null != notEqualsMap && notEqualsMap.size() > 0) {
        	String objectAlias = ClassUtil.getBaseClassName(metadata.getEntityName()).toLowerCase();
            for(Iterator<Map.Entry<String, Object>> it = notEqualsMap
                    .entrySet().iterator(); it.hasNext();) {
                Map.Entry<String, Object> entry = (Map.Entry<String, Object>)it
                        .next();
                String condKey = entry.getKey();
                Object condVal = entry.getValue();
                if(null != condVal) {
					String alist = condKey + RandomUtils.getRandomValue(5);
                    builder.append(" and ")
                    		.append(" ").append(objectAlias).append(".").append(condKey)
                    		.append(" ").append(" <> :").append(alist);
                    Type type = metadata.getPropertyType(condKey);
                    qc.setParams(alist, getValue(type,condVal), type);
                }
            }
        }

    }

    /**
     * 等于
     * @author hjz
     * @param 设定文件
     * @return 返回类型
     */
    private static final void addEquals(QueryCondition qc,
    		ClassMetadata metadata, StringBuilder builder) {
        Map<String, Object> equalsMap = qc.getEqualsMap();
        //
        if(null != equalsMap && equalsMap.size() > 0) {
        	String objectAlias = ClassUtil.getBaseClassName(metadata.getEntityName()).toLowerCase();
            for(Iterator<Map.Entry<String, Object>> it = equalsMap.entrySet()
                    .iterator(); it.hasNext();) {
                Map.Entry<String, Object> entry = (Map.Entry<String, Object>)it
                        .next();
                String condKey = entry.getKey();
                Object condVal = entry.getValue();
                if(null != condVal) {
					String alist = condKey + RandomUtils.getRandomValue(5);
                    builder.append(" and ")
                    		.append(" ").append(objectAlias).append(".").append(condKey)
                    		.append(" ").append(" = :").append(alist);
                    Type type = metadata.getPropertyType(condKey);
                    qc.setParams(alist, getValue(type,condVal), type);
                }
            }
        }

    }

    /**
     * 模糊等于
     * @author hjz
     * @param 设定文件
     * @return 返回类型
     */
    private static final void addLikeEquals(QueryCondition qc,
    		ClassMetadata metadata, StringBuilder builder) {
        Map<String, Object> likeEqualsMap = qc.getLikeEqualsMap();
        //
        if(null != likeEqualsMap && likeEqualsMap.size() > 0) {
        	String objectAlias = ClassUtil.getBaseClassName(metadata.getEntityName()).toLowerCase();
            for(Iterator<Map.Entry<String, Object>> it = likeEqualsMap
                    .entrySet().iterator(); it.hasNext();) {
                Map.Entry<String, Object> entry = (Map.Entry<String, Object>)it
                        .next();
                String condKey = entry.getKey();
                Object condVal = entry.getValue();
                if(null != condVal) {
					String alist = condKey + RandomUtils.getRandomValue(5);
                    builder.append(" and ")
                    		.append(" ").append(objectAlias).append(".").append(condKey)
                    		.append(" ").append(" like :").append(alist);
                    Type type = metadata.getPropertyType(condKey);
                    qc.setParams(alist, "%"+getValue(type,condVal)+"%", type);
                }
            }
        }
    }
    
    /**
     * 根据hibernate配置的字段属性类型转换value值
     * @author jiuzhou.hu
     * @date 2013-9-12 下午2:23:55
     * @param type	hibernate配置的type类型
     * @param value	需要转换的值
     * @return
     */
    public static final Object getValue(Type type,Object value){
    	Class<?> typeClass = type.getReturnedClass();
    	if(value == null){
    		return null;
    	} else if(StringUtil.equals((value+"").toLowerCase(), "null")){
    		return null;
    	} else if(typeClass == Date.class){
    		if(value instanceof Date){
    			Date date = (Date)value;
    			return new java.sql.Date(date.getTime());
    		}else{
    			return new java.sql.Date(TimeUtil.toCalendar(value.toString()).getTimeInMillis());
    		}
    	} else if(typeClass == GregorianCalendar.class){
    		if(value instanceof Date){
    			Date date = (Date)value;
    			return new java.sql.Date(date.getTime());
    		}else{
    			return new java.sql.Date(TimeUtil.toCalendar(value.toString()).getTimeInMillis());
    		}
    	} else if(typeClass == Integer.class){
    		return NumberUtils.toInt(value);
    	} else if(typeClass == Long.class){
    		return NumberUtils.toLong(value+"");
    	} else if(typeClass == Short.class){
    		return Short.valueOf(value.toString());
    	} else if(typeClass == Double.class){
    		return NumberUtils.toDouble(value+"");
    	} else if(typeClass == Float.class){
    		return NumberUtils.toFloat(value+"");
    	} else if(typeClass == java.math.BigDecimal.class){
    		return java.math.BigDecimal.valueOf(NumberUtils.toLong(value));
    	} else if(typeClass == Byte.class){
    		return Byte.valueOf(value.toString());
    	} else if(typeClass == Boolean.class){
    		return Boolean.valueOf(value.toString());
    	} else if(typeClass == String.class){
			return value.toString();
    	} else {
    		return value;
    	}
    }
    
    public static String getRowCountHql(String hql) {
		return getRowCountBaseHql(hql, ORDER_BY);
	}
    
    private static String getRowCountBaseHql(String hql, String indexKey) {
		int fromIndex = hql.toUpperCase().indexOf(FROM);
		String projectionHql = hql.substring(0, fromIndex);

		hql = hql.substring(fromIndex);
		String rowCountHql = hql.replace(HQL_FETCH, "");

		int index = rowCountHql.indexOf(indexKey);
		if (index > 0) {
			rowCountHql = rowCountHql.substring(0, index);
		}
		return wrapProjection(projectionHql) + rowCountHql;
	}
    
    private static String wrapProjection(String projection) {
		if (projection.indexOf("SELECT") == -1) {
			return ROW_COUNT;
		} else {
			return projection.replace("SELECT", "SELECT COUNT(") + ") ";
		}
	}
}
