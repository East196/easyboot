package com.github.east196.easyboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Context {

	public static ApplicationContext app;

	public static <T> T getBean(Class<T> requiredType) {
		return app.getBean(requiredType);
	}

	@Autowired
	ApplicationContext applicationContext;

	@Bean
	String init() {
		app = applicationContext;
		return "app init ok ^-^";
	}

	public static ApplicationContext getApp() {
		return app;
	}

}
