package com.qian.gulimall.admin.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qian.gulimall.admin.service.SysLogService;
import com.qian.gulimall.common.security.SecurityConstants;
import com.qian.gulimall.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.qian.gulimall.common.security.SecurityConstants.AUTHORIZATION_BASIC;
import static com.qian.gulimall.common.security.SecurityConstants.AUTHORIZATION_NAME;


/**
 * Created by IntelliJ IDEA.
 * GulimallAuthenticationSuccessHandler is 登录成功处理
 *
 * @author QIAN
 * Date 2020/04/18
 * Time 17:42
 */
@Slf4j
public class GulimallAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    /**
     * spring启动的时候会自动注册
     */
    @Autowired
    private ObjectMapper objectMapper;


    @Autowired
    private ClientDetailsService clientDetailService;

    @Autowired
    private AuthorizationServerTokenServices authorizationServerTokenServices;

    @Autowired
    private SysLogService sysLogService;

    /**
     * Authentication是spring security的核心接口，封装了认证信息
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        String header = request.getHeader(AUTHORIZATION_NAME);

        if (header == null || !header.startsWith(AUTHORIZATION_BASIC)) {
            throw new UnapprovedClientAuthenticationException("请求头无clien信息");
        }

        String[] tokens = extractAndDecodeHeader(header, request);
        assert tokens.length == 2;

        String clientId = tokens[0];
        String clientSecret = tokens[1];

        ClientDetails clientDetails = clientDetailService.loadClientByClientId(clientId);

        if (clientDetails == null) {
            throw new UnapprovedClientAuthenticationException("clientId对应的配置信息不存在" + clientId);
        } else if (!StringUtils.equals(clientDetails.getClientSecret(), clientSecret)) {
            throw new UnapprovedClientAuthenticationException("clientSecret不匹配" + clientId);
        }

        @SuppressWarnings("unchecked")
        TokenRequest tokenRequest = new TokenRequest(MapUtils.EMPTY_MAP, clientId, clientDetails.getScope(), "custom");

        OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);

        OAuth2Authentication oauth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);
        OAuth2AccessToken oauth2AccessToken = authorizationServerTokenServices.createAccessToken(oauth2Authentication);

        // 记录登录失败日志
        String method = this.getClass().getName() + "." + Thread.currentThread() .getStackTrace()[1].getMethodName();
        String operation = "登陆成功";
        sysLogService.saveLoginSysLog(request, operation, method);
        // 从session删除
        request.removeAttribute(SecurityConstants.DEFAULT_LOGIN_INFO);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(R.ok().put("token", oauth2AccessToken)));
    }

    /**
     * Decodes the header into a username and password.
     *
     * @throws BadCredentialsException if the Basic header is not present or is not valid
     *                                 Base64
     */
    private String[] extractAndDecodeHeader(String header, HttpServletRequest request)
            throws IOException {

        byte[] base64Token = header.substring(6).getBytes(StandardCharsets.UTF_8);
        byte[] decoded;
        try {
            decoded = Base64.decode(base64Token);
        } catch (IllegalArgumentException e) {
            throw new BadCredentialsException(
                    "Failed to decode basic authentication token");
        }

        String token = new String(decoded, StandardCharsets.UTF_8);

        int delim = token.indexOf(":");

        if (delim == -1) {
            throw new BadCredentialsException("Invalid basic authentication token");
        }
        return new String[]{token.substring(0, delim), token.substring(delim + 1)};
    }
}
