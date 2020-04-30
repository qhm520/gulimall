package com.qian.gulimall.product.config;

import com.qian.gulimall.product.interceptor.AuthorizationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * Created by IntelliJ IDEA.
 * GullimallProductWebMvcConfigurationSupport is  拦截器配置
 *
 * @author QIAN
 * Date 2020/04/22
 * Time 14:39
 */
@Configuration
public class GullimallProductWebMvcConfigurationSupport extends WebMvcConfigurationSupport {

    @Autowired
    private AuthorizationInterceptor authorizationInterceptor;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 可添加多个
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/**");
    }
}
