package com.qian.gulimall.security.validate;

import com.qian.gulimall.security.properties.contsant.ValidateCodeTypeEnum;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * Created by IntelliJ IDEA.
 * ValidateCodeRepository is  验证码存储
 *
 * @author QIAN
 * Date 2020/04/18
 * Time 16:35
 */
public interface ValidateCodeRepository {

    /**
     * 保存验证码
     * @param request 请求
     * @param code 验证码
     * @param codeType 短信或图片验证码
     */
    void save(ServletWebRequest request, ValidateCode code, ValidateCodeTypeEnum codeType);

    /**
     * 获取验证码
     * @param request 请求
     * @param codeType 短信或图片验证码
     * @return  验证码
     */
    ValidateCode get(ServletWebRequest request, ValidateCodeTypeEnum codeType);

    /**
     * 删除 验证码
     * @param request 请求
     * @param codeType 短信或图片验证码
     */
    void remove(ServletWebRequest request, ValidateCodeTypeEnum codeType);
}
