package com.qian.gulimall.security.controller;

import com.qian.gulimall.common.utils.R;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.qian.gulimall.common.security.SecurityConstants.AUTHORIZATION_BEARER;
import static com.qian.gulimall.common.security.SecurityConstants.AUTHORIZATION_NAME;

/**
 * Created by IntelliJ IDEA.
 * LogoutController is 退出登录
 *
 * @author QIAN
 * Date 2020/04/22
 * Time 08:46
 */
@RestController
@RequestMapping("/authentication")
public class LogoutController {

    @Autowired
    @Qualifier("consumerTokenServices")
    private ConsumerTokenServices consumerTokenServices;

    /**
     * 退出登录,并清除redis中的token
     **/
    @GetMapping("/logout")
    public R removeToken(HttpServletRequest request, HttpServletResponse response) {
        String authorization = request.getHeader(AUTHORIZATION_NAME);
        String accessToken = StringUtils.substringAfter(authorization, AUTHORIZATION_BEARER);
        boolean success = consumerTokenServices.revokeToken(accessToken);
        return R.ok().put("success", success);
    }
}
