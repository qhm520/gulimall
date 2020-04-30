package com.qian.gulimall.security.validate;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by IntelliJ IDEA.
 * ValidateCode is 所有验证码实体类的父类
 *
 * @author QIAN
 * Date 2020/04/18
 * Time 16:26
 */
@Data
public class ValidateCode implements Serializable {

    private static final long serialVersionUID = -4584317733389271385L;

    /**
     * 验证码
     */
    protected String code;

    /**
     * 过期时间
     */
    protected LocalDateTime expireTime;

    public ValidateCode(String code, int expireIn) {
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
    }

    public ValidateCode(String code, LocalDateTime expireTime) {
        this.code = code;
        this.expireTime = expireTime;
    }

    public boolean isExpired() {
        return expireTime.isBefore(LocalDateTime.now());
    }
}
