package com.qian.gulimall.common.entity.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * LoginInfoVo is 登录信息
 *
 * @author QIAN
 * Date 2020/04/20
 * Time 16:59
 */
@Data
public class LoginInfoVo implements Serializable {


    private static final long serialVersionUID = -6472472934722237322L;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * uuid
     */
    private String uuid;

    /**
     * 图片验证码
     */
    private String imageCode;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 短信验证码
     */
    private String smsCode;
}
