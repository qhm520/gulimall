package com.qian.gulimall.security.config;

import com.qian.gulimall.security.properties.SecurityProperties;
import com.qian.gulimall.security.validate.ValidateCodeFilter;
import com.qian.gulimall.security.validate.ValidateCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * ValidateCodeSecurityConfig is 验证码验证配置
 *
 * @author QIAN
 * Date 2020/04/18
 * Time 16:32
 */

@Component
public class ValidateCodeSecurityConfig extends SecurityConfigurerAdapter <DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private ValidateCodeRepository validateCodeRepository;

    @Override
    public void configure(HttpSecurity http) throws Exception {

        ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
        validateCodeFilter.setValidateCodeRepository(validateCodeRepository);
        validateCodeFilter.setAuthenticationFailureHandler(authenticationFailureHandler);
        validateCodeFilter.setSecurityProperties(securityProperties);
        validateCodeFilter.afterPropertiesSet();

        http
                .addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class);
    }

}
