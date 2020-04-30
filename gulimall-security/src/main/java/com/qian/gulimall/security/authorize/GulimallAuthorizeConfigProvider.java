package com.qian.gulimall.security.authorize;

import com.qian.gulimall.security.properties.SecurityProperties;
import com.qian.gulimall.common.security.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * GulimallAuthorizeConfigProvider is
 *
 * @author QIAN
 * Date 2020/04/18
 * Time 17:34
 */
@Component
@Order(Integer.MIN_VALUE)
public class GulimallAuthorizeConfigProvider implements AuthorizeConfigProvider {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void config(ExpressionUrlAuthorizationConfigurer <HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        config.antMatchers(
                SecurityConstants.DEFAULT_SINGUP_PROCESS_URL,
                SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
                SecurityConstants.DEFAULT_VALIDATE_CODE_URL_IMAGE,
                SecurityConstants.DEFAULT_VALIDATE_CODE_URL_SMS,
                SecurityConstants.DEFAULT_SIGNIN_PROCESS_URL_FORM,
                SecurityConstants.DEFAULT_SIGNIN_PROCESS_URL_LOGIN,
                SecurityConstants.DEFAULT_SIGNIN_PROCESS_URL_MOBILE,
                SecurityConstants.DEFAULT_SIGNOUT_URL
        )
                //放行
                .permitAll();
    }
}
