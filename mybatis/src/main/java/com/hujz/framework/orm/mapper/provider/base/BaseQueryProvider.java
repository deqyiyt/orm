package com.hujz.framework.orm.mapper.provider.base;

import org.apache.ibatis.mapping.MappedStatement;

import tk.mybatis.mapper.mapperhelper.MapperHelper;
import com.hujz.framework.orm.mapper.mapperhelper.MapperTemplate;

public class BaseQueryProvider extends MapperTemplate {

    public BaseQueryProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    /**
     * 根据主键进行查询
     *
     * @param ms
     */
    public String get(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        //将返回值修改为实体类型
        setResultType(ms, entityClass);
        StringBuilder sql = new StringBuilder();
        sql.append(selectAllColumns(entityClass));
        sql.append(fromTable(entityClass, tableName(entityClass)));
        sql.append(wherePKColumns(entityClass));
        return sql.toString();
    }
    
    /**
     * 查询
     *
     * @param ms
     * @return
     */
    public String queryOneByEntity(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        //修改返回值类型为实体类型
        setResultType(ms, entityClass);
        StringBuilder sql = new StringBuilder();
        sql.append(selectAllColumns(entityClass));
        sql.append(fromTable(entityClass, tableName(entityClass)));
        sql.append(whereAllIfColumns(entityClass, isNotEmpty()));
        return sql.toString();
    }
    
    /**
     * 查询
     *
     * @param ms
     * @return
     */
    public String queryByEntity(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        //修改返回值类型为实体类型
        setResultType(ms, entityClass);
        StringBuilder sql = new StringBuilder();
        sql.append(selectAllColumns(entityClass));
        sql.append(fromTable(entityClass, tableName(entityClass)));
        sql.append(whereAllIfColumns(entityClass, isNotEmpty()));
        sql.append(orderByDefault(entityClass));
        return sql.toString();
    }
    
    /**
     * 查询总数
     *
     * @param ms
     * @return
     */
    public String queryCountByEntity(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        sql.append(selectCount(entityClass));
        sql.append(fromTable(entityClass, tableName(entityClass)));
        sql.append(whereAllIfColumns(entityClass, isNotEmpty()));
        return sql.toString();
    }
    
    /**
     * 根据主键进行查询
     *
     * @param ms
     */
    public String getRandomOne(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        //修改返回值类型为实体类型
        setResultType(ms, entityClass);
        StringBuilder sql = new StringBuilder();
        sql.append(selectAllColumns(entityClass));
        sql.append(fromTable(entityClass, tableName(entityClass)));
        sql.append(whereAllIfColumns(entityClass, isNotEmpty()));
        sql.append(" ORDER BY RAND() LIMIT 1");
        return sql.toString();
    }
}
