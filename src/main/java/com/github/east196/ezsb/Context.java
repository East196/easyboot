package com.github.east196.ezsb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

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

	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

	public static ApplicationContext getApp() {
		return app;
	}
	
	public static Object getBean(String name) {
		return app.getBean(name);
	}

	public static <T> T getBean(String name, Class<T> requiredType) {
		return app.getBean(name, requiredType);
	}

	public static boolean containsBean(String name) {
		return app.containsBean(name);
	}

	public static boolean isSingleton(String name) {
		return app.isSingleton(name);
	}

	public static Class<? extends Object> getType(String name) {
		return app.getType(name);
	}

	public static HttpServletRequest getHttpServletRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}

	public static String getDomain(){
		HttpServletRequest request = getHttpServletRequest();
		StringBuffer url = request.getRequestURL();
		return url.delete(url.length() - request.getRequestURI().length(), url.length()).toString();
	}

	public static String getOrigin(){
		HttpServletRequest request = getHttpServletRequest();
		return request.getHeader("Origin");
	}

}