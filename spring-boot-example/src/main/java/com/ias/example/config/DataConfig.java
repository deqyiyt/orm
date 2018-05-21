package com.ias.example.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.ias.assembly.orm.mybatis.interceptor.PageInterceptor;


@Configuration
@EnableTransactionManagement
@PropertySources({
    @PropertySource("classpath:config/ias-example-boot.properties"),
    @PropertySource(value = "file:/ias/config/ias-example-boot.properties", ignoreResourceNotFound = true)
})
@MapperScan(value = {"com.ias.**.dao"})
public class DataConfig {
    
    @Bean
    public PageInterceptor pageInterceptor() {
        PageInterceptor page = new PageInterceptor();
        page.setDatabaseType("mysql");
        page.setRegEx(".*ByPage");
        return page;
    }
}
