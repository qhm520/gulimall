package com.qian.gulimall.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Created by IntelliJ IDEA.
 * ValidateCodeException is 验证码异常类
 *
 * @author QIAN
 * Date 2020/04/18
 * Time 16:34
 */
public class ValidateCodeException  extends AuthenticationException {
    private static final long serialVersionUID = -8412526856916278539L;

    public ValidateCodeException(String msg) {
        super(msg);
    }
}
