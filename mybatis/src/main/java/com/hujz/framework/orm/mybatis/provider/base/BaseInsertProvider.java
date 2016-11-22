package com.hujz.framework.orm.mybatis.provider.base;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.scripting.xmltags.MixedSqlNode;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.scripting.xmltags.StaticTextSqlNode;
import org.apache.ibatis.scripting.xmltags.TrimSqlNode;
import org.apache.ibatis.scripting.xmltags.VarDeclSqlNode;

import com.hujz.framework.orm.mybatis.entity.EntityColumn;
import com.hujz.framework.orm.mybatis.mapperhelper.EntityHelper;
import com.hujz.framework.orm.mybatis.mapperhelper.MapperHelper;
import com.hujz.framework.orm.mybatis.mapperhelper.MapperTemplate;
import com.hujz.framework.orm.mybatis.util.StringUtil;

/**
 * BaseInsertProvider实现类，基础方法实现类
 *
 * @author liuzh
 */
public class BaseInsertProvider extends MapperTemplate {

    public BaseInsertProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    /**
     * 插入全部
     *
     * @param ms
     * @return
     */
    public SqlNode save(MappedStatement ms) {
        Class<?> entityClass = getSelectReturnType(ms);
        List<SqlNode> sqlNodes = new LinkedList<SqlNode>();
        //insert into table
        sqlNodes.add(new StaticTextSqlNode("INSERT INTO " + tableName(entityClass)));
        //获取全部列
        Set<EntityColumn> columnList = EntityHelper.getColumns(entityClass);
        //Identity列只能有一个
        Boolean hasIdentityKey = false;
        //处理所有的主键策略
        for (EntityColumn column : columnList) {
            //序列的情况，直接写入sql中，不需要额外的获取值
            if (StringUtil.isNotEmpty(column.getSequenceName())) {
            } else if (column.isIdentity()) {
                //这种情况下,如果原先的字段有值,需要先缓存起来,否则就一定会使用自动增长
                //这是一个bind节点
                sqlNodes.add(new VarDeclSqlNode(column.getProperty() + "_cache", column.getProperty()));
                //如果是Identity列，就需要插入selectKey
                //如果已经存在Identity列，抛出异常
                if (hasIdentityKey) {
                    //jdbc类型只需要添加一次
                    if (column.getGenerator() != null && column.getGenerator().equals("JDBC")) {
                        continue;
                    }
                    throw new RuntimeException(ms.getId() + "对应的实体类" + entityClass.getCanonicalName() + "中包含多个MySql的自动增长列,最多只能有一个!");
                }
                //插入selectKey
                newSelectKeyMappedStatement(ms, column);
                hasIdentityKey = true;
            } else if (column.isUuid()) {
                //uuid的情况，直接插入bind节点
                sqlNodes.add(new VarDeclSqlNode(column.getProperty() + "_bind", getUUID()));
            } else if(column.isCreateTime()) {
        		//设置创建时间
        		sqlNodes.add(new VarDeclSqlNode(column.getProperty() + "_create", getNow()));
            }
        }
        //插入全部的(列名,列名...)
        sqlNodes.add(new StaticTextSqlNode("(" + EntityHelper.getAllColumns(entityClass) + ")"));
        List<SqlNode> ifNodes = new LinkedList<SqlNode>();
        //处理所有的values(属性值,属性值...)
        for (EntityColumn column : columnList) {
            //优先使用传入的属性值,当原属性property!=null时，用原属性
            //自增的情况下,如果默认有值,就会备份到property_cache中,所以这里需要先判断备份的值是否存在
            if (column.isIdentity()) {
                ifNodes.add(getIfCacheNotNull(column, new StaticTextSqlNode("#{" + column.getProperty() + "_cache },")));
            } else {
                //其他情况值仍然存在原property中
                ifNodes.add(getIfNotNull(column, new StaticTextSqlNode("#{" + column.getProperty() + "},")));
            }
            //当属性为null时，如果存在主键策略，会自动获取值，如果不存在，则使用null
            //序列的情况
            if (StringUtil.isNotEmpty(column.getSequenceName())) {
                ifNodes.add(getIfIsNull(column, new StaticTextSqlNode(getSeqNextVal(column) + " ,")));
            } else if (column.isIdentity()) {
                ifNodes.add(getIfCacheIsNull(column, new StaticTextSqlNode("#{" + column.getProperty() + " },")));
            } else if (column.isUuid()) {
                ifNodes.add(getIfIsNull(column, new StaticTextSqlNode("#{" + column.getProperty() + "_bind },")));
            } else if(column.isCreateTime()) {
            	//设置创建时间
        		ifNodes.add(getIfIsNull(column, new StaticTextSqlNode("#{" + column.getProperty() + "_create},")));
            } else {
                //当null的时候，如果不指定jdbcType，oracle可能会报异常，指定VARCHAR不影响其他
                ifNodes.add(getIfIsNull(column, new StaticTextSqlNode("#{" + column.getProperty() + ",jdbcType=VARCHAR},")));
            }
        }
        //values(#{property},#{property}...)
        sqlNodes.add(new TrimSqlNode(ms.getConfiguration(), new MixedSqlNode(ifNodes), "VALUES (", null, ")", ","));
        return new MixedSqlNode(sqlNodes);
    }
}

