package com.qian.gulimall.security.properties;

import lombok.Data;

/**
 * Created by IntelliJ IDEA.
 * ValidateCodeProperties is
 *
 * @author QIAN
 * Date 2020/04/18
 * Time 16:57
 */
@Data
public class ValidateCodeProperties {

    private ImageCodeProperties image = new ImageCodeProperties();

    private SmsCodeProperties sms = new SmsCodeProperties();
}
