package com.qian.gulimall.security.properties;

import lombok.Data;

/**
 * Created by IntelliJ IDEA.
 * SmsCodeProperties is 短信验证码
 *
 * @author QIAN
 * Date 2020/04/18
 * Time 16:54
 */
@Data
public class SmsCodeProperties {

    private int length = 6;

    private int expireIn = 300;

    private String url;
}
