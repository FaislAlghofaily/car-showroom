package com.example.car_showroom.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MessageConfig implements WebMvcConfigurer {
    @Bean("customMessageSource")
    public MessageSource getMessageResource() {
        ReloadableResourceBundleMessageSource msgResource = new ReloadableResourceBundleMessageSource();
        msgResource.setBasename("classpath:msg/messages");
        msgResource.setDefaultEncoding("UTF-8");
        return msgResource;
    }
}
