package com.qian.gulimall.product.interceptor;

import com.qian.gulimall.common.exception.CommonEnum;
import com.qian.gulimall.common.exception.GulimallAuthenticationException;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static com.qian.gulimall.common.utils.ClaimsUtils.authorizationToClaims;

/**
 * Created by IntelliJ IDEA.
 * AuthorizationInterceptor is
 *
 * @author QIAN
 * Date 2020/01/16
 * Time 14:56
 */
@Component
@Slf4j
public class AuthorizationInterceptor implements HandlerInterceptor {


    /**
     * 在业务处理器处理请求之前被调用。预处理，可以进行编码、安全控制、权限校验等处理；
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Claims claims = authorizationToClaims();

        Instant instant = claims.getExpiration().toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime exp = LocalDateTime.ofInstant(instant, zone);
        if (exp.isBefore(LocalDateTime.now())) {
            throw new GulimallAuthenticationException(CommonEnum.SIGNATURE_NOT_MATCH.getCode(), "数字签名已过期不匹配!");
        }

        log.info(((HandlerMethod) handler).getBean().getClass().getName());
        log.info(((HandlerMethod) handler).getMethod().getName());
        return true;
    }

    /**
     * 在业务处理器处理请求执行完成后，生成视图之前执行。后处理（调用了Service并返回ModelAndView，但未进行页面渲染），有机会修改ModelAndView （这个博主就基本不怎么用了）
     *
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        log.info("postHandle");
//        Long start = (Long) request.getAttribute("startTime");
//        log.info("time interceptor 耗时:" + (System.currentTimeMillis() - start));
    }


    /**
     * 在DispatcherServlet完全处理完请求后被调用，可用于清理资源等。返回处理（已经渲染了页面）
     *
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        log.info("afterCompletion");
//        Long start = (Long) request.getAttribute("startTime");
//        log.info("time interceptor 耗时:" + (System.currentTimeMillis() - start));
//        log.info("ex is " + ex);
    }

}
