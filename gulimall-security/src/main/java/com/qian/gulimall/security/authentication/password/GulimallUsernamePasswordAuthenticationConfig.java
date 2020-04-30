package com.qian.gulimall.security.authentication.password;

import com.qian.gulimall.common.security.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * UsernamePasswordAuthenticationConfig is 自定义用户名和密码登录配置
 *
 * @author QIAN
 * Date 2020/04/20
 * Time 16:31
 */
@Component
public class GulimallUsernamePasswordAuthenticationConfig extends SecurityConfigurerAdapter <DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        GulimallUsernamePasswordAuthenticationFilter gulimallUsernamePasswordAuthenticationFilter = new GulimallUsernamePasswordAuthenticationFilter(SecurityConstants.DEFAULT_SIGNIN_PROCESS_URL_LOGIN);
        gulimallUsernamePasswordAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        gulimallUsernamePasswordAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        gulimallUsernamePasswordAuthenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler);

        http
                .addFilterAfter(gulimallUsernamePasswordAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
