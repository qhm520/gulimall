package com.qian.gulimall.admin.config;

import com.qian.gulimall.admin.exception.GulimallAuthExceptionEntryPoint;
import com.qian.gulimall.admin.exception.GulimallAccessDeniedHandler;
import com.qian.gulimall.security.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.qian.gulimall.security.authentication.password.GulimallUsernamePasswordAuthenticationConfig;
import com.qian.gulimall.security.authorize.AuthorizeConfigManager;
import com.qian.gulimall.security.config.ValidateCodeSecurityConfig;
import com.qian.gulimall.security.properties.SecurityProperties;
import com.qian.gulimall.common.security.SecurityConstants;
import com.qian.gulimall.security.validate.RedisValidateCodeRepository;
import com.qian.gulimall.security.validate.ValidateCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * Created by IntelliJ IDEA.
 * GulimallResourceServerConfig is 标注为资源服务器
 *
 * @author QIAN
 * Date 2020/04/17
 * Time 11:01
 */
@Configuration
@EnableResourceServer
public class GulimallResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;

    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Autowired
    private GulimallUsernamePasswordAuthenticationConfig gulimallUsernamePasswordAuthenticationConfig;

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private AuthorizeConfigManager authorizeConfigManager;

    @Autowired
    private GulimallAuthExceptionEntryPoint gulimallAuthExceptionEntryPoint;

    @Autowired
    private GulimallAccessDeniedHandler gulimallAccessDeniedHandler;

    @Bean
    @ConditionalOnMissingBean(value = ValidateCodeRepository.class)
    public ValidateCodeRepository validateCodeRepository() {
        return new RedisValidateCodeRepository();
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http
                // 验证码校验相关配置
                .apply(validateCodeSecurityConfig)
                .and()
                .apply(gulimallUsernamePasswordAuthenticationConfig)
                .and()
                // 验证码登陆相关配置
                .apply(smsCodeAuthenticationSecurityConfig)
                .and()
                // 表单登陆相关配置
                .formLogin()
                .loginPage(SecurityConstants.DEFAULT_SIGNIN_PROCESS_URL_LOGIN) //表单登陆URL
                .loginProcessingUrl(SecurityConstants.DEFAULT_SIGNIN_PROCESS_URL_FORM) //处理登陆请求的URL
                .successHandler(authenticationSuccessHandler) // 登陆成功处理器
                .failureHandler(authenticationFailureHandler) // 登陆失败处理器
                .and()
                // csrf配置
                .csrf()
                .disable();

        authorizeConfigManager.config(http.authorizeRequests());
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.authenticationEntryPoint(gulimallAuthExceptionEntryPoint)
                .accessDeniedHandler(gulimallAccessDeniedHandler);
    }


}
