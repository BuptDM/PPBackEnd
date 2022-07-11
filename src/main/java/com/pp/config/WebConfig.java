package com.pp.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    final  AppProperties appProperties;
    public WebConfig(AppProperties appProperties) {
        this.appProperties = appProperties;
    }
    @Override
    public void addResourceHandlers(@NotNull ResourceHandlerRegistry registry) {
        WebMvcConfigurer.super.addResourceHandlers(registry);
        // 注册静态文件访问
        registry.addResourceHandler("/files/**").addResourceLocations("file:"+appProperties.getFileDirectory());
    }
}