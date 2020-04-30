package com.qian.gulimall.security.validate;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * Created by IntelliJ IDEA.
 * ValidateCodeGenerator is
 *
 * @author QIAN
 * Date 2020/04/18
 * Time 17:02
 */
public interface ValidateCodeGenerator {

    ValidateCode generate(ServletWebRequest request);
}
