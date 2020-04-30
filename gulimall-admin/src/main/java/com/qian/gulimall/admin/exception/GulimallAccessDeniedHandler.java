package com.qian.gulimall.admin.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qian.gulimall.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
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
 * GulimallAccessDeniedHandler is 用来解决认证过的用户访问无权限资源时的异常
 *
 * @author QIAN
 * Date 2020/04/17
 * Time 11:01
 */
@Component
public class GulimallAccessDeniedHandler implements AccessDeniedHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException)
            throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        R r = R.error(HttpStatus.BAD_REQUEST.value(), "没有权限，请联系管理员授权");
        response.getWriter().write(objectMapper.writeValueAsString(r));
    }
}
