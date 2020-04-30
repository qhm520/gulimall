package com.qian.gulimall.security.properties;

import com.qian.gulimall.common.security.SecurityConstants;
import lombok.Data;

/**
 * Created by IntelliJ IDEA.
 * OAuth2Properties is
 *
 * @author QIAN
 * Date 2020/04/18
 * Time 16:58
 */
@Data
public class OAuth2Properties {

    private String signingKey = SecurityConstants.AUTHORIZATION_SIGNING_KEY;

    private OAuth2ClientProperties[] clients = {};
}
