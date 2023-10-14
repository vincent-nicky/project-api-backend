package com.wsj.apiconsumerapp.config;

import com.wsj.apiconsumerapp.aop.IsLoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorsConfig implements WebMvcConfigurer {

    @Autowired
    private IsLoginInterceptor isLoginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册拦截器，（只能注入拦截器，而不是new。不能手动new，否则dubbo服务注入不进去）
        registry.addInterceptor(isLoginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(new String[]{"/user/login","/user/register","/user/logout","/error"});


//        registry.addInterceptor(new IsLoginInterceptor())
//                .addPathPatterns("/**")
//                .excludePathPatterns(new String[]{"/user/login","/user/register","/user/logout","/error"});
    }
}
