package com.tokioschool.spring.store.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(value = StoreConfigProperties.class)
public class StoreConfig {

}
