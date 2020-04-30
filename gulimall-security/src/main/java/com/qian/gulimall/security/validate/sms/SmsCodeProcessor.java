package com.qian.gulimall.security.validate.sms;

import com.qian.gulimall.security.validate.AbstractValidateCodeProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * Created by IntelliJ IDEA.
 * SmsCodeProcessor is
 *
 * @author QIAN
 * Date 2020/04/18
 * Time 17:29
 */

@Component
public class SmsCodeProcessor extends AbstractValidateCodeProcessor <SmsCode, SmsCodeGenerator> {

    @Autowired
    private SmsCodeGenerator smsCodeGenerator;

    @Autowired
    private SmsCodeSender smsCodeSender;

    @Override
    protected SmsCodeGenerator getValidateCodeGenerator() {
        return smsCodeGenerator;
    }

    @Override
    protected void send(ServletWebRequest request, SmsCode validateCode) throws Exception {
        String mobile = ServletRequestUtils.getStringParameter(request.getRequest(), "mobile");
        smsCodeSender.send(mobile, validateCode.getCode());
    }

}
