package com.qian.gulimall.security.authorize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * GulimallAuthorizeConfigManager is
 *
 * @author QIAN
 * Date 2020/04/18
 * Time 17:33
 */
@Component
public class GulimallAuthorizeConfigManager implements AuthorizeConfigManager {

    @Autowired
    private List <AuthorizeConfigProvider> authorizeConfigProviders;

    @Override
    public void config(ExpressionUrlAuthorizationConfigurer <HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        for (AuthorizeConfigProvider authorizeConfigProvider : authorizeConfigProviders) {
            authorizeConfigProvider.config(config);
        }
        // 其它所有请求需要身份验证
		config.anyRequest().authenticated();
    }

}
