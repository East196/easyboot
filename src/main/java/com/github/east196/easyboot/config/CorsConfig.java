package com.github.east196.easyboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class CorsConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/people").allowedOrigins("http://localhost:8081");
                registry.addMapping("/**")  
                .allowedOrigins("*")    //允许所有前端站点调用
                .allowCredentials(true)  
                .allowedMethods("GET", "POST", "DELETE", "PUT")  
                .maxAge(1728000); 
            }
        };
    }
}
