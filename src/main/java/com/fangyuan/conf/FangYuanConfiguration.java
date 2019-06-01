package com.fangyuan.conf;

import com.fangyuan.interceptor.PassportInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class FangYuanConfiguration implements WebMvcConfigurer{
    @Autowired
    PassportInterceptor passportInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // TODO Auto-generated method stub
        registry.addInterceptor(passportInterceptor).excludePathPatterns("/login/do_login*")
                .excludePathPatterns("/register/do_register*")
        .excludePathPatterns("/tag/*");
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
