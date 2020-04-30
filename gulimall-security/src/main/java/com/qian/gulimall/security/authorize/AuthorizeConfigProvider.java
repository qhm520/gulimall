package com.qian.gulimall.security.authorize;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

/**
 * Created by IntelliJ IDEA.
 * AuthorizeConfigProvider is
 *
 * @author QIAN
 * Date 2020/04/18
 * Time 17:33
 */
public interface AuthorizeConfigProvider {

    void config(ExpressionUrlAuthorizationConfigurer <HttpSecurity>.ExpressionInterceptUrlRegistry config);
}
