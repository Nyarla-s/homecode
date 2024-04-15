package com.example.usercontrol.config;

import com.example.usercontrol.interceptor.AdminInterceptor;
import com.example.usercontrol.interceptor.ResourceInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;


@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport {

    @Autowired
    AdminInterceptor adminInterceptor;
    @Autowired
    ResourceInterceptor resourceInterceptor;

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(adminInterceptor).addPathPatterns("/admin/addUser");
        registry.addInterceptor(resourceInterceptor).addPathPatterns("/user/**");
        super.addInterceptors(registry);
    }
}
