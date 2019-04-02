package com.rip.load.config;

import com.rip.load.Interceptor.AuthorityInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebAppConfigurer implements WebMvcConfigurer {

    @Autowired
    private AuthorityInterceptor authorityInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //登录拦截的管理器
        InterceptorRegistration registration = registry.addInterceptor(authorityInterceptor);     //拦截的对象会进入这个类中进行判断
        registration.addPathPatterns("/**");                    //所有路径都被拦截
        registration.excludePathPatterns("/swagger-resources/**", "/webjars/**","/swagger-ui.html/**", "/error","/csrf", "/", "/api/user/login");       //添加不拦截路径
    }
}