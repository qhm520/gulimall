package com.qian.gulimall.admin.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qian.gulimall.admin.entity.SysLogEntity;
import com.qian.gulimall.admin.service.SysLogService;
import com.qian.gulimall.common.security.SecurityConstants;
import com.qian.gulimall.common.utils.IPUtils;
import com.qian.gulimall.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * GulimallAuthenticationFailureHandler is 登录失败处理
 *
 * @author QIAN
 * Date 2020/04/18
 * Time 17:42
 */
@Slf4j
public class GulimallAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    /**
     * spring启动的时候会自动注册
     */
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SysLogService sysLogService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        // 记录登录失败日志
        String method = this.getClass().getName() + "." + Thread.currentThread() .getStackTrace()[1].getMethodName();
        String operation = "登陆失败: " + exception.getMessage();
        sysLogService.saveLoginSysLog(request, operation, method);
        log.info(operation);
        request.removeAttribute(SecurityConstants.DEFAULT_LOGIN_INFO);
        response.setStatus(HttpStatus.OK.value());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString( R.error(HttpStatus.BAD_REQUEST.value(), exception.getMessage())));

    }



}
