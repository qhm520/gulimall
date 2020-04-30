package com.qian.gulimall.security.validate.sms;

/**
 * Created by IntelliJ IDEA.
 * SmsCodeSender is
 *
 * @author QIAN
 * Date 2020/04/18
 * Time 17:29
 */
public interface SmsCodeSender {
    void send(String mobile, String code);
}

