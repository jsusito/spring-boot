package com.tokioschool.spring.store.config;

import java.nio.file.Path;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix ="application.store")
public record StoreConfigProperties(String basePath) {
	
	
	public Path getPath(String resourceName) {
		return Path.of(basePath.toString(), resourceName);
	}
}
