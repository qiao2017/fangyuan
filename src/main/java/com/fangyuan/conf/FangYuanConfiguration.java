package com.fangyuan.conf;

import com.fangyuan.interceptor.AllowAccess;
import com.fangyuan.interceptor.PassportInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class FangYuanConfiguration implements WebMvcConfigurer{
    @Autowired
    PassportInterceptor passportInterceptor;
    @Autowired
    AllowAccess allowAccess;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // TODO Auto-generated method stub
        registry.addInterceptor(passportInterceptor)
                .addPathPatterns("/room/*").addPathPatterns("/login/do_logout");
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
