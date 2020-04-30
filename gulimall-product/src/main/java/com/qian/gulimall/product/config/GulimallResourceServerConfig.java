package com.qian.gulimall.product.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by IntelliJ IDEA.
 * GulimallResourceServerConfig is 标注为资源服务器
 *
 * @author QIAN
 * Date 2020/04/17
 * Time 11:01
 */
@Configuration
public class GulimallResourceServerConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                // 表单登陆相关配置
                .formLogin()
                .and()
                .authorizeRequests().antMatchers("/**").permitAll()
                .and()
                // csrf配置
                .csrf()
                .disable();
    }
}
