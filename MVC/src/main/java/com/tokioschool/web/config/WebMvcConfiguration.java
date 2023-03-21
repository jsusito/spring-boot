package com.tokioschool.web.config;

import java.time.Duration;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.tokioschool.web.domain.Film;
import com.tokioschool.web.domain.User;
import com.tokioschool.web.service.impl.FirstLaunchImpl;

@Configuration

public class WebMvcConfiguration implements WebMvcConfigurer{

	@Autowired
	FirstLaunchImpl firstLauch;
	
	@Bean(value = "currentUser")
	@SessionScope
	public User user() {
		return new User();
	}
	
	@Bean
	@SessionScope
	public Film film() {
		return new Film();
	}
	
	@Bean
	public void checkFirts() {
		firstLauch.checkInit();
	}
	
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.setConnectTimeout(Duration.ofSeconds(3))
			.setReadTimeout(Duration.ofSeconds(3)).build();
		}
	
	
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		String operativeSystem = System.getProperty("os.name").toLowerCase();
		String file = null;
		if(operativeSystem.contains("window"))
			file = "file:///";
		else
			file = "file:/";
		registry.addResourceHandler("/external/**").addResourceLocations(file + ConstantsConfig.toDir() + "/");
		
	}
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
