package com.qian.gulimall.admin.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qian.gulimall.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * GulimallAuthExceptionEntryPoint is 用来解决匿名用户访问无权限资源时的异常
 *
 * @author QIAN
 * Date 2020/04/17
 * Time 11:01
 */
@Component
public class GulimallAuthExceptionEntryPoint implements AuthenticationEntryPoint {

    /**
     * spring启动的时候会自动注册
     */
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws ServletException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        try {
//            ObjectMapper mapper = new ObjectMapper();
            objectMapper.writeValue(response.getOutputStream(), R.error(HttpStatus.UNAUTHORIZED.value(), "没有权限，请联系管理员授权"));
        } catch (Exception e) {
            throw new ServletException();
        }
    }
}
