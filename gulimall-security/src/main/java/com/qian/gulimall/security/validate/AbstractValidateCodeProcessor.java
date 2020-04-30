package com.qian.gulimall.security.validate;

import com.qian.gulimall.security.exception.ValidateCodeException;
import com.qian.gulimall.security.properties.contsant.ValidateCodeTypeEnum;
import com.qian.gulimall.security.validate.image.ImageCode;
import com.qian.gulimall.security.validate.sms.SmsCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * Created by IntelliJ IDEA.
 * AbstractValidateCodeProcessor is
 *
 * @author QIAN
 * Date 2020/04/18
 * Time 17:01
 */

public abstract class AbstractValidateCodeProcessor<C extends ValidateCode,G extends ValidateCodeGenerator> implements ValidateCodeProcessor {

    @Autowired
    private ValidateCodeRepository validateCodeRepository;

    @Override
    public void process(ServletWebRequest request) throws Exception {
        C validateCode = generate(request);
        save(request, validateCode);
        send(request, validateCode);
    }

    private void save(ServletWebRequest request, C validateCode) {
        ValidateCode codeToSave = new ValidateCode(validateCode.getCode(), validateCode.getExpireTime());
        validateCodeRepository.save(request, codeToSave, getValidateCodeType(validateCode));
    }

    @SuppressWarnings("unchecked")
    private C generate(ServletWebRequest request) throws Exception {
        return (C) getValidateCodeGenerator().generate(request);
    }

    private ValidateCodeTypeEnum getValidateCodeType(ValidateCode validateCode) {
        if (validateCode instanceof ImageCode) {
            return ValidateCodeTypeEnum.IMAGE;
        } else if (validateCode instanceof SmsCode) {
            return ValidateCodeTypeEnum.SMS;
        } else {
            throw new ValidateCodeException("不支持的validateCode类型" + validateCode.getClass().getCanonicalName());
        }
    }

    protected abstract G getValidateCodeGenerator();

    protected abstract void send(ServletWebRequest request, C validateCode) throws Exception;
}
