package com.example.car_showroom.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class AppRepositoryConfig implements RepositoryRestConfigurer {
    /**
     * this method to turn off the auto generated endpoints in openapi 3.0
     * @param config
     * @param cors
     */
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        config.setExposeRepositoryMethodsByDefault(false);
    }
}