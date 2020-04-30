package com.qian.gulimall.security.config;

import com.qian.gulimall.security.properties.SecurityProperties;
import com.qian.gulimall.security.validate.image.DefaultImageCodeGenerator;
import com.qian.gulimall.security.validate.image.ImageCodeGenerator;
import com.qian.gulimall.security.validate.sms.DefaultSmsCodeGenerator;
import com.qian.gulimall.security.validate.sms.DefaultSmsCodeSender;
import com.qian.gulimall.security.validate.sms.SmsCodeGenerator;
import com.qian.gulimall.security.validate.sms.SmsCodeSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by IntelliJ IDEA.
 * SecurityConfig is
 * CoreSecurityConfig
 * @author QIAN
 * Date 2020/04/18
 * Time 16:21
 */
@Configuration
public class SecurityConfig {

    @Autowired
    private SecurityProperties securityProperties;

    @Bean
    @ConditionalOnMissingBean(value = ImageCodeGenerator.class)
    public ImageCodeGenerator imageCodeGenerator() {
        DefaultImageCodeGenerator imageCodeGenerator = new DefaultImageCodeGenerator();
        imageCodeGenerator.setSecurityProperties(securityProperties);
        return imageCodeGenerator;
    }

    @Bean
    @ConditionalOnMissingBean(value = SmsCodeGenerator.class)
    public SmsCodeGenerator smsCodeGenerator() {
        DefaultSmsCodeGenerator smsCodeGenerator = new DefaultSmsCodeGenerator();
        smsCodeGenerator.setSecurityProperties(securityProperties);
        return smsCodeGenerator;
    }

    @Bean
    @ConditionalOnMissingBean(value = SmsCodeSender.class)
    public SmsCodeSender smsCodeSender() {
        DefaultSmsCodeSender smsCodeSender = new DefaultSmsCodeSender();
        return smsCodeSender;
    }
}
