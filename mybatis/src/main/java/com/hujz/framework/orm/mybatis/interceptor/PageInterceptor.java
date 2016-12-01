package com.hujz.framework.orm.mybatis.interceptor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

import javax.sql.DataSource;

import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;

import com.hujz.framework.orm.bean.PageTools;
import com.hujz.framework.orm.mybatis.pagehelper.Dialect;
import com.hujz.framework.orm.mybatis.pagehelper.SqlUtil;
import com.hujz.framework.orm.mybatis.pagehelper.parser.SqlParser;
import com.hujz.framework.orm.mybatis.pagehelper.parser.SqlServer;
import com.hujz.framework.orm.mybatis.util.StringUtil;
import com.hujz.framework.orm.util.ObjectUtils;
import com.hujz.framework.orm.util.QueryCondition;

/**
 * 
 *
 * @Pr：yscm
 * @Desc: 分页拦截器，用于拦截需要进行分页查询的操作，然后对其进行分页处理。 利用拦截器实现Mybatis分页的原理：
 *        要利用JDBC对数据库进行操作就必须要有一个对应的Statement对象，
 *        Mybatis在执行Sql语句前就会产生一个包含Sql语句的Statement对象，而且对应的Sql语句
 *        是在Statement之前产生的，所以我们就可以在它生成Statement之前对用来生成Statement的Sql语句下手。
 *        在Mybatis中Statement语句是通过RoutingStatementHandler对象的
 *        prepare方法生成的。所以利用拦截器实现Mybatis分页的一个思路就是拦截StatementHandler接口的prepare方法，
 *        然后在拦截器方法中把Sql语句改成对应的分页查询Sql语句，之后再调用
 *        StatementHandler对象的prepare方法，即调用invocation.proceed()。
 *        对于分页而言，在拦截器里面我们还需要做的一个操作就是统计满足当前条件的记录一共有多少，这是通过获取到了原始的Sql语句后，
 *        把它改为对应的统计语句再利用Mybatis封装好的参数和设
 *        置参数的功能把Sql语句中的参数进行替换，之后再执行查询记录数的Sql语句进行总记录数的统计。
 * @Author: lijun
 * @Date: 2015年5月25日 下午5:21:59
 * @Copyright: ©2005-2013 yschome.com Inc. All rights reserved
 */
@Intercepts({ @Signature(method = "prepare", type = StatementHandler.class, args = { Connection.class }) })
public class PageInterceptor implements Interceptor {
	private static final SqlServer sqlServer = new SqlServer();

	private String databaseType;// 数据库类型，不同的数据库有不同的分页方法
	private String regEx;// mapper.xml中需要拦截的ID(正则匹配)

	// 缓存
	private Map<String, SqlUtil> urlSqlUtilMap = new ConcurrentHashMap<String, SqlUtil>();
	private SqlParser parser = new SqlParser();

	/**
	 * 拦截后要执行的方法
	 */
	@Override
	public Object intercept(final Invocation invocation) throws Throwable {
		// 对于StatementHandler其实只有两个实现类，一个是RoutingStatementHandler，另一个是抽象类BaseStatementHandler，
		// BaseStatementHandler有三个子类，分别是SimpleStatementHandler，PreparedStatementHandler和CallableStatementHandler，
		// SimpleStatementHandler是用于处理Statement的，PreparedStatementHandler是处理PreparedStatement的，而CallableStatementHandler是
		// 处理CallableStatement的。Mybatis在进行Sql语句处理的时候都是建立的RoutingStatementHandler，而在RoutingStatementHandler里面拥有一个
		// StatementHandler类型的delegate属性，RoutingStatementHandler会依据Statement的不同建立对应的BaseStatementHandler，即SimpleStatementHandler、
		// PreparedStatementHandler或CallableStatementHandler，在RoutingStatementHandler里面所有StatementHandler接口方法的实现都是调用的delegate对应的方法。
		// 我们在PageInterceptor类上已经用@Signature标记了该Interceptor只拦截StatementHandler接口的prepare方法，又因l为Mybatis只有在建立RoutingStatementHandler的时候
		// 是通过Interceptor的plugin方法进行包裹的，所以我们这里拦截到的目标对象肯定是RoutingStatementHandler对象。
		RoutingStatementHandler handler = (RoutingStatementHandler) invocation.getTarget();
		// 通过反射获取到当前RoutingStatementHandler对象的delegate属性
		StatementHandler delegate = (StatementHandler) ObjectUtils.getFieldValue(handler, "delegate");
		// 获取到当前StatementHandler的
		// boundSql，这里不管是调用handler.getBoundSql()还是直接调用delegate.getBoundSql()结果是一样的，因为之前已经说过了
		// RoutingStatementHandler实现的所有StatementHandler接口方法里面都是调用的delegate对应的方法。
		BoundSql boundSql = delegate.getBoundSql();
		// 拿到当前绑定Sql的参数对象，就是我们在调用对应的Mapper映射语句时所传入的参数对象
		Object obj = boundSql.getParameterObject();

		// 通过反射获取delegate父类BaseStatementHandler的mappedStatement属性
		MappedStatement mappedStatement = (MappedStatement) ObjectUtils.getFieldValue(delegate, "mappedStatement");

		// 这里我们简单的通过传入的是Page对象就认定它是需要进行分页操作的。
		PageTools pageTools = proccessPage(obj, mappedStatement);
		if (pageTools != null && pageTools.getPageSize() > 0) {
			// 拦截到的prepare方法参数是一个Connection对象
			Connection connection = (Connection) invocation.getArgs()[0];
			// 获取当前要执行的Sql语句，也就是我们直接在Mapper映射语句中写的Sql语句
			String sql = boundSql.getSql();
			// 给当前的page参数对象设置总记录数
			if (pageTools.isQueryTotalCount()) {
				this.setTotalRecord(pageTools, boundSql, mappedStatement, connection);
			}
			// 获取分页Sql语句
			String pageSql = this.getPageSql(pageTools, sql);
			// 利用反射设置当前BoundSql对应的sql属性为我们建立好的分页Sql语句
			ObjectUtils.setFieldValue(boundSql, "sql", pageSql);
		}
		return invocation.proceed();
	}

	// 是否需要分页
	@SuppressWarnings("unchecked")
	public PageTools proccessPage(Object obj, MappedStatement mappedStatement) {
		if (obj instanceof QueryCondition) {
			QueryCondition qc = (QueryCondition) obj;
			return qc != null ? qc.getPageTools() : null;
		}

		if (mappedStatement.getId().matches(regEx)) {
			if (obj instanceof PageTools) { // 参数就是Page实体
				return (PageTools) obj;
			}
			if (obj instanceof Map) {
				PageTools pageTools = null;
				Map<Object, Object> params = (Map<Object, Object>) obj;
				for (Object key : params.keySet()) {
					if (params.get(key) instanceof PageTools) {
						pageTools = (PageTools) params.get(key);
						break;
					}
				}
				return pageTools;
			}
		}

		return null;
	}

	/**
	 * 拦截器对应的封装原始对象的方法
	 */
	@Override
	public Object plugin(final Object target) {
		return Plugin.wrap(target, this);
	}

	/**
	 * 设置注册拦截器时设定的属性
	 */
	@Override
	public void setProperties(final Properties properties) {

	}

	/**
	 * 根据page对象获取对应的分页查询Sql语句，这里只做了两种数据库类型，Mysql和Oracle 其它的数据库都 没有进行分页
	 *
	 * @param page
	 *            分页对象
	 * @param sql
	 *            原sql语句
	 * @return
	 */
	private String getPageSql(final PageTools page, final String sql) {
		StringBuffer sqlBuffer = new StringBuffer(sql);
		if ("mysql".equalsIgnoreCase(databaseType)) {
			return getMysqlPageSql(page, sqlBuffer);
		} else if ("oracle".equalsIgnoreCase(databaseType)) {
			return getOraclePageSql(page, sqlBuffer);
		} else if ("sqlserver".equalsIgnoreCase(databaseType)) {
			return getSqlServerPageSql(page, sqlBuffer);
		}
		return sqlBuffer.toString();
	}

	/**
	 * 获取Mysql数据库的分页查询语句
	 * 
	 * @param page
	 *            分页对象
	 * @param sqlBuffer
	 *            包含原sql语句的StringBuffer对象
	 * @return Mysql数据库分页语句
	 */
	private String getMysqlPageSql(PageTools page, StringBuffer sqlBuffer) {
		// 计算第一条记录的位置，Mysql中记录的位置是从0开始的。
		int offset = (page.getPageNo() - 1) * page.getPageSize();
		sqlBuffer.append(" limit ").append(offset).append(",").append(page.getPageSize());
		return sqlBuffer.toString();
	}

	/**
	 * 获取Oracle数据库的分页查询语句
	 * 
	 * @param page
	 *            分页对象
	 * @param sqlBuffer
	 *            包含原sql语句的StringBuffer对象
	 * @return Oracle数据库的分页查询语句
	 */
	private String getOraclePageSql(final PageTools page, final StringBuffer sqlBuffer) {
		// 计算第一条记录的位置，Oracle分页是通过rownum进行的，而rownum是从1开始的
		int offset = (page.getPageNo() - 1) * page.getPageSize() + 1;
		sqlBuffer.insert(0, "select u.*, rownum r from (").append(") u where rownum < ")
				.append(offset + page.getPageSize());
		sqlBuffer.insert(0, "select * from (").append(") where r >= ").append(offset);

		return sqlBuffer.toString();
	}

	/**
	 * 获取Mysql数据库的分页查询语句
	 * 
	 * @param page
	 *            分页对象
	 * @param sqlBuffer
	 *            包含原sql语句的StringBuffer对象
	 * @return Mysql数据库分页语句
	 */
	private String getSqlServerPageSql(PageTools page, StringBuffer sqlBuffer) {
		// 计算第一条记录的位置，Mysql中记录的位置是从0开始的。
		int offset = (page.getPageNo() - 1) * page.getPageSize();
		return sqlServer.convertToPageSql(sqlBuffer.toString(), offset, page.getPageSize());
	}

	/**
	 * 给当前的参数对象page设置总记录数
	 * 
	 * @throws SQLException
	 */
	private void setTotalRecord(PageTools pageTools, BoundSql boundSql, final MappedStatement mappedStatement,
			final Connection connection) throws SQLException {
		// 拿到当前绑定Sql的参数对象，就是我们在调用对应的Mapper映射语句时所传入的参数对象
		// 分页SQL<select>中parameterType属性对应的实体参数，即Mapper接口中执行分页方法的参数,该参数不得为空
		Object parameterObject = boundSql.getParameterObject();
		// 获取到我们自己写在Mapper映射语句中对应的Sql语句
		String sql = boundSql.getSql();
		// 通过查询Sql语句获取到对应的计算总记录数的sql语句
		// String countSql = this.getCountSql(sql);
		String countSql = parser.getSmartCountSql(sql);
		// 通过connection建立一个countSql对应的PreparedStatement对象。
		PreparedStatement countStmt = connection.prepareStatement(countSql);
		DefaultParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, parameterObject,
				boundSql);
		parameterHandler.setParameters(countStmt);

		// 之后就是执行获取总记录数的Sql语句和获取结果了
		ResultSet rs = countStmt.executeQuery();
		int count = 0;
		if (rs.next()) {
			count = rs.getInt(1);
		}
		rs.close();
		countStmt.close();

		pageTools.setRecordCount(count);
	}

	/**
	 * 设定databaseType的值.
	 * 
	 * @param String
	 */
	public void setDatabaseType(String databaseType) {
		this.databaseType = databaseType;
	}

	/**
	 * 设定regEx的值.
	 * 
	 * @param String
	 */
	public void setRegEx(String regEx) {
		this.regEx = regEx;
	}

	/**
	 * 根据daatsource创建对应的sqlUtil
	 *
	 * @param invocation
	 */
	public SqlUtil getSqlUtil(Invocation invocation) {
		MappedStatement ms = (MappedStatement) invocation.getArgs()[0];
		// 改为对dataSource做缓存
		DataSource dataSource = ms.getConfiguration().getEnvironment().getDataSource();
		String url = getUrl(dataSource);
		if (urlSqlUtilMap.containsKey(url)) {
			return urlSqlUtilMap.get(url);
		}
		ReentrantLock lock = new ReentrantLock();
		try {
			lock.lock();
			if (urlSqlUtilMap.containsKey(url)) {
				return urlSqlUtilMap.get(url);
			}
			if (StringUtil.isEmpty(url)) {
				throw new RuntimeException("无法自动获取jdbcUrl，请在分页插件中配置dialect参数!");
			}
			String dialect = Dialect.fromJdbcUrl(url);
			if (dialect == null) {
				throw new RuntimeException("无法自动获取数据库类型，请通过dialect参数指定!");
			}
			SqlUtil sqlUtil = new SqlUtil(dialect);
			urlSqlUtilMap.put(url, sqlUtil);
			return sqlUtil;
		} finally {
			lock.unlock();
		}
	}

	/**
	 * 获取url
	 *
	 * @param dataSource
	 * @return
	 */
	public String getUrl(DataSource dataSource) {
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			return conn.getMetaData().getURL();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// ignore
				}
			}
		}
	}

}