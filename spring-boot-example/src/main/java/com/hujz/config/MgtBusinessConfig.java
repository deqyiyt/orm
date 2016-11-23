package com.hujz.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(
        basePackages = {"com.hujz.business"}
)
@EnableCaching
public class MgtBusinessConfig {
	
}
