package com.qian.gulimall.security.properties;

import lombok.Data;

/**
 * Created by IntelliJ IDEA.
 * OAuth2ClientProperties is
 *
 * @author QIAN
 * Date 2020/04/18
 * Time 16:58
 */
@Data
public class OAuth2ClientProperties {

    private String clientId;

    private String clientSecret;

    private int accessTokenValiditySeconds;

}
