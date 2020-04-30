package com.qian.gulimall.security.validate.sms;

import com.qian.gulimall.security.validate.ValidateCode;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by IntelliJ IDEA.
 * SmsCode is 短信验证码
 *
 * @author QIAN
 * Date 2020/04/18
 * Time 16:31
 */
@Data
public class SmsCode extends ValidateCode implements Serializable {

    private static final long serialVersionUID = 6041770288168075131L;

    public SmsCode(String code, int expireIn) {
        super(code, expireIn);
    }

    public SmsCode(String code, LocalDateTime expireTime) {
        super(code, expireTime);
    }
}
