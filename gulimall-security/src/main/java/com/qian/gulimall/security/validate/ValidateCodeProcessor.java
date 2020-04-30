package com.qian.gulimall.security.validate;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * Created by IntelliJ IDEA.
 * ValidateCodeProcessor is
 *
 * @author QIAN
 * Date 2020/04/18
 * Time 17:04
 */
public interface ValidateCodeProcessor {

    /**
     * 处理
     * @param request
     * @throws Exception
     */
    void process(ServletWebRequest request) throws Exception;
}
