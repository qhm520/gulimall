package com.qian.gulimall.admin.jwt;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * GulimallJwtTokenEnhancer is 令牌增强器：存放一些额外的信息
 *
 * @author QIAN
 * Date 2020/04/18
 * Time 17:45
 */
public class GulimallJwtTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        // 注意添加的额外信息，最好不要和已有的json对象中的key重名，容易出现错误
        Map <String, Object> info = new HashMap <>();
        info.put("company", "gulimall");

        ((DefaultOAuth2AccessToken)accessToken).setAdditionalInformation(info);
        return accessToken;
    }

}
