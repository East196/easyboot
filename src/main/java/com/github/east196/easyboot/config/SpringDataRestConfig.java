package com.github.east196.easyboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

/**
 * restful默认不返回主键id, 需要新增配置文件
 */
@Configuration
class SpringDataRestConfig {
    @Bean
    public RepositoryRestConfigurer repositoryRestConfigurer() {
        return new RepositoryRestConfigurerAdapter() {
            @Override
            public void configureRepositoryRestConfiguration( RepositoryRestConfiguration config) {
//                config.exposeIdsFor(Person.class);
                config.setDefaultMediaType(org.springframework.http.MediaType.APPLICATION_JSON);
                config.setDefaultPageSize(1000);
            }
        };
    }

}