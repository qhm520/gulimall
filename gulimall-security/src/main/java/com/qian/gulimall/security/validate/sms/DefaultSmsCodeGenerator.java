package com.qian.gulimall.security.validate.sms;

import com.qian.gulimall.security.properties.SecurityProperties;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * Created by IntelliJ IDEA.
 * DefaultSmsCodeGenerator is
 *
 * @author QIAN
 * Date 2020/04/18
 * Time 17:28
 */

public class DefaultSmsCodeGenerator implements SmsCodeGenerator {

    private SecurityProperties securityProperties;

    @Override
    public SmsCode generate(ServletWebRequest request) {
        String code = RandomStringUtils.randomNumeric(securityProperties.getCode().getSms().getLength());
        return new SmsCode(code, securityProperties.getCode().getSms().getExpireIn());
    }

    public void setSecurityProperties(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }
}
