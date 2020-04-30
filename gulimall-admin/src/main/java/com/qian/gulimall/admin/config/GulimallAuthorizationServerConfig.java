package com.qian.gulimall.admin.config;

import com.qian.gulimall.admin.authentication.GulimallAuthenticationFailureHandler;
import com.qian.gulimall.admin.authentication.GulimallAuthenticationSuccessHandler;
import com.qian.gulimall.admin.jwt.GulimallJwtTokenEnhancer;
import com.qian.gulimall.security.properties.OAuth2ClientProperties;
import com.qian.gulimall.security.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * GulimallAuthorizationServerConfig is 标注为认证授权服务器
 *
 * @author QIAN
 * Date 2020/04/18
 * Time 17:42
 */
@Order(Integer.MIN_VALUE)
@Configuration
@EnableAuthorizationServer
public class GulimallAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    @Bean
    @ConditionalOnMissingBean(value = AuthenticationSuccessHandler.class)
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new GulimallAuthenticationSuccessHandler();
    }



    @Bean
    @ConditionalOnMissingBean(value = AuthenticationFailureHandler.class)
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new GulimallAuthenticationFailureHandler();
    }

    /**
     * 转换器， 设置jwt密签key
     *
     * @return
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        String signingKey = securityProperties.getOauth2().getSigningKey();
        converter.setSigningKey(signingKey);
        return converter;
    }

    @Bean
    @ConditionalOnMissingBean(name = "jwtTokenEnhancer")
    public TokenEnhancer jwtTokenEnhancer() {
        return new GulimallJwtTokenEnhancer();
    }

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private TokenStore tokenStore;

    @Autowired(required = false)
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @Autowired(required = false)
    private TokenEnhancer jwtTokenEnhancer;

    @Bean
    public TokenStore redisTokenStore() {
        return new RedisTokenStore(redisConnectionFactory);
    }

    /**
     * 给TokanEndpoint配置
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenEnhancer(jwtTokenEnhancer)  // jwt返回带的参数
                .accessTokenConverter(jwtAccessTokenConverter())  // jwt
                .tokenStore(tokenStore) // redis存储
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService);

        if (jwtAccessTokenConverter != null && jwtTokenEnhancer != null) {
            TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
            List <TokenEnhancer> enhancers = new ArrayList <>();
            enhancers.add(jwtTokenEnhancer);
            enhancers.add(jwtAccessTokenConverter);
            enhancerChain.setTokenEnhancers(enhancers);

            endpoints
                    .tokenEnhancer(enhancerChain)
                    .accessTokenConverter(jwtAccessTokenConverter);
        }
    }

    /**
     * 决定能给哪些clientId, clientSecret发令牌
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        InMemoryClientDetailsServiceBuilder builder = clients.inMemory();
        for (OAuth2ClientProperties config : securityProperties.getOauth2().getClients()) {
            builder.withClient(config.getClientId())
                    .secret(config.getClientSecret())
                    .accessTokenValiditySeconds(config.getAccessTokenValiditySeconds())
                    .authorizedGrantTypes("refresh_token", "password")
                    .scopes("all", "read", "write");
        }
    }

    // 认证服务器的安全配置，配置的 isAuthenticated() 是SpringSecurity的授权表达式，默认是 denyAll()，拒绝所有访问
    // 它的意思是访问访问认证服务器的tokenKey的时候，需要进行认证
    // tokenKey 就是对 JWT进行签名的key，即gulimall
/*    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.checkTokenAccess("isAuthenticated()")
                .tokenKeyAccess("permitAll()");
    }*/
}
