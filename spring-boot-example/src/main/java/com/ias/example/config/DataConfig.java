package com.ias.example.config;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;
import com.ias.assembly.orm.mybatis.interceptor.PageInterceptor;

import tk.mybatis.spring.annotation.MapperScan;


@Configuration
@EnableTransactionManagement
@MapperScan(value = {"com.ias.**.dao"})
public class DataConfig {
    
    @Bean
    public PageInterceptor pageInterceptor() {
        PageInterceptor page = new PageInterceptor();
        page.setDatabaseType("mysql");
        page.setRegEx(".*ByPage");
        return page;
    }
    
    @Bean
    @ConfigurationProperties("spring.datasource.druid.wall.config")
    public WallConfig wallConfig(){
        return new WallConfig();
    }

    @Bean
    public WallFilter wallFilter(){
        WallFilter filter = new WallFilter();
        filter.setConfig(wallConfig());
        filter.setDbType("mysql");
        return filter;
    }

    @Bean
    public DataSource dataSource(Environment env){
        DruidDataSource dataSource = DruidDataSourceBuilder
                .create()
                .build(env,"spring.datasource.druid.");
        List<Filter> filters = new ArrayList<>();
        filters.add(wallFilter());
        dataSource.setProxyFilters(filters);
        return dataSource;
    }
}
