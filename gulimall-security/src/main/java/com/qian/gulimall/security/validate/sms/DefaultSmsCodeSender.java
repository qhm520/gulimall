package com.qian.gulimall.security.validate.sms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by IntelliJ IDEA.
 * DefaultSmsCodeSender is
 *
 * @author QIAN
 * Date 2020/04/18
 * Time 17:31
 */
public class DefaultSmsCodeSender implements SmsCodeSender {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void send(String mobile, String code) {
        logger.info(String.format("向手机%s发送验证码%s", mobile, code));
    }
}
