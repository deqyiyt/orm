/**
 * 
 */

package com.hujz.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import com.hujz.framework.orm.GenericDao;
import com.hujz.framework.orm.mybatis.interceptor.PageInterceptor;
import com.hujz.framework.orm.mybatis.interceptor.PerformanceInterceptor;

@Configuration
@EnableAutoConfiguration(exclude = {GenericDao.class})
@EnableTransactionManagement
public class MybatisAutoConfiguration implements TransactionManagementConfigurer {
	
    private final static Logger logger = LoggerFactory.getLogger(MybatisAutoConfiguration.class);
	
	public MybatisAutoConfiguration() {
        logger.info("++++加载的mybatis信息");
	}
	
	@Autowired
    DataSource dataSource;
	
	@Autowired(required = false)
    private Interceptor[] interceptors;
	
	@Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactoryBean() {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(dataSource);
        
        if (this.interceptors != null && this.interceptors.length > 0) {
            factory.setPlugins(this.interceptors);
        }
        
        factory.setTypeAliasesPackage("com.hujz.business.entity");
        
        Properties p = new Properties();
        p.put("mapUnderscoreToCamelCase", true);//是否开启自动驼峰命名规则
        factory.setConfigurationProperties(p);

        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
        	factory.setMapperLocations(resolver.getResources("classpath*:/mapper/*.xml"));
            return factory.getObject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Bean
    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }
    
    @Bean
    public PerformanceInterceptor performanceInterceptor() {
    	return new PerformanceInterceptor();
    }
    
    @Bean
    public PageInterceptor pageInterceptor() {
    	PageInterceptor page = new PageInterceptor();
    	page.setDatabaseType("mysql");
    	page.setRegEx(".*ByPage");
    	return page;
    }
}
