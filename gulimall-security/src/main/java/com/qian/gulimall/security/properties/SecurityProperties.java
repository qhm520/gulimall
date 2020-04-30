package com.qian.gulimall.security.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * SecurityProperties is
 *
 * @author QIAN
 * Date 2020/04/18
 * Time 16:59
 */
@Data
@ConfigurationProperties(prefix = "gulimall.security")
@Component(value = "globalSecurityProperties")
public class SecurityProperties {

    private ValidateCodeProperties code = new ValidateCodeProperties();

    private OAuth2Properties oauth2 = new OAuth2Properties();
}
