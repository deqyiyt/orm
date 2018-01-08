package com.hujz.config;

import java.util.Properties;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hujz.framework.orm.GenericDao;

import tk.mybatis.spring.mapper.MapperScannerConfigurer;

@Configuration
@AutoConfigureAfter(MybatisAutoConfiguration.class)
public class MyBatisMapperScannerConfig {

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
    	 MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
    	 mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
         mapperScannerConfigurer.setBasePackage("com.hujz.**.dao");
         Properties properties = new Properties();
         properties.setProperty("mappers", GenericDao.class.getName());
         properties.setProperty("notEmpty", "false");
         properties.setProperty("IDENTITY", "MYSQL");
         mapperScannerConfigurer.setProperties(properties);
        return mapperScannerConfigurer;
    }
}
