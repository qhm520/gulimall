//package com.qian.gulimall.admin.config;
//
//import com.qian.gulimall.admin.jwt.GulimallJwtTokenEnhancer;
//import com.qian.gulimall.security.properties.SecurityProperties;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.security.oauth2.provider.token.TokenEnhancer;
//import org.springframework.security.oauth2.provider.token.TokenStore;
//import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
//import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
//import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
//
///**
// * Created by IntelliJ IDEA.
// * TokenStoreConfig is
// *
// * @author QIAN
// * Date 2020/04/18
// * Time 17:45
// */
////@Configuration
//public class TokenStoreConfig {
//
//    @Autowired
//    private RedisConnectionFactory redisConnectionFactory;
//
//    @Bean
//    @ConditionalOnProperty(prefix = "gulimall.security.oauth2", name = "store-type", havingValue = "redis")
//    public TokenStore redisTokenStore() {
//        return new RedisTokenStore(redisConnectionFactory);
//    }
//
//    @Configuration
//    @ConditionalOnProperty(prefix = "gulimall.security.oauth2", name = "store-type", havingValue = "jwt", matchIfMissing = true)
//    public static class JwtTokenConfig {
//
//        @Autowired
//        private SecurityProperties securityProperties;
//
//        @Bean
//        public TokenStore jwtTokenStore() {
//            return new JwtTokenStore(jwtAccessTokenConverter());
//        }
//
//        /**
//         * 转换器， 设置jwt密签key
//         *
//         * @return
//         */
//        @Bean
//        public JwtAccessTokenConverter jwtAccessTokenConverter() {
//            JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
//            converter.setSigningKey(securityProperties.getOauth2().getSigningKey());
//            return converter;
//        }
//
//        @Bean
//        @ConditionalOnMissingBean(name = "jwtTokenEnhancer")
//        public TokenEnhancer jwtTokenEnhancer() {
//            return new GulimallJwtTokenEnhancer();
//        }
//    }
//}
