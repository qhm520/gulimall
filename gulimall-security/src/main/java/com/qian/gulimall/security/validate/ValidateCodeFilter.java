package com.qian.gulimall.security.validate;

import com.google.gson.Gson;
import com.qian.gulimall.common.security.RequestHandleUtils;
import com.qian.gulimall.security.exception.ValidateCodeException;
import com.qian.gulimall.security.properties.SecurityProperties;
import com.qian.gulimall.common.security.SecurityConstants;
import com.qian.gulimall.security.properties.contsant.ValidateCodeTypeEnum;
import com.qian.gulimall.common.entity.vo.LoginInfoVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * ValidateCodeFilter is 图片验证码和短信验证码验证
 *
 * @author QIAN
 * Date 2020/04/18
 * Time 17:53
 */

public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private AuthenticationFailureHandler authenticationFailureHandler;

    private SecurityProperties securityProperties;

    private Map <String, ValidateCodeTypeEnum> urlMap = new LinkedHashMap <>();

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    private ValidateCodeRepository validateCodeRepository;

    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();

        urlMap.put(SecurityConstants.DEFAULT_SIGNIN_PROCESS_URL_LOGIN, ValidateCodeTypeEnum.IMAGE);
        urlMap.put(SecurityConstants.DEFAULT_SIGNIN_PROCESS_URL_MOBILE, ValidateCodeTypeEnum.SMS);

        addUrlToMap(securityProperties.getCode().getImage().getUrl(), ValidateCodeTypeEnum.IMAGE);
        addUrlToMap(securityProperties.getCode().getSms().getUrl(), ValidateCodeTypeEnum.SMS);
    }

    private void addUrlToMap(String validateCodeUrl, ValidateCodeTypeEnum validateCodeType) {
        String[] validateCodeUrls = StringUtils.splitByWholeSeparator(validateCodeUrl, ",");
        for (String url : validateCodeUrls) {
            urlMap.put(url, validateCodeType);
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        logger.info("ValidateCodeFilter: " + request.getRequestURL());

        ValidateCodeTypeEnum validateCodeType = null;
        for (Map.Entry <String, ValidateCodeTypeEnum> entry : urlMap.entrySet()) {
            if (antPathMatcher.match(entry.getKey(), request.getRequestURI())) {
                validateCodeType = entry.getValue();
                break;
            }
        }

        if (validateCodeType != null) {
            try {
                validate(new ServletWebRequest(request), validateCodeType);
            } catch (ValidateCodeException e) {
                authenticationFailureHandler.onAuthenticationFailure(request, response, e);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private void validate(ServletWebRequest request, ValidateCodeTypeEnum needCodeType) throws ServletRequestBindingException {

        ValidateCode codeInRepository = validateCodeRepository.get(request, needCodeType);

        if (codeInRepository == null) {
            throw new ValidateCodeException(String.format("需要%s验证码", needCodeType));
        }

        String codeInRequest = getCodeInRequest(request, needCodeType);

        // 验证码不能为空
        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValidateCodeException("请填写验证码");
        }

        // 验证码不存在
        if (codeInRequest == null) {
            throw new ValidateCodeException("请先获取验证码");
        }

        // 验证码已过期
        if (codeInRepository.isExpired()) {
            validateCodeRepository.remove(request, needCodeType);
            throw new ValidateCodeException("验证码已过期");
        }

        // 验证码不匹配
        if (!StringUtils.equalsIgnoreCase(codeInRepository.getCode(), codeInRequest)) {
            throw new ValidateCodeException("验证码不匹配");
        }

        validateCodeRepository.remove(request, needCodeType);
    }

    private String getCodeInRequest(ServletWebRequest request, ValidateCodeTypeEnum validateCodeType) {
        String loginInfo = RequestHandleUtils.loginInfo(request.getRequest());
        LoginInfoVo loginInfoVo = new Gson().fromJson(loginInfo, LoginInfoVo.class);
        request.getRequest().setAttribute(SecurityConstants.DEFAULT_LOGIN_INFO, loginInfoVo);

        if (validateCodeType.getParameterNameOnValidate().equals(ValidateCodeTypeEnum.IMAGE.getParameterNameOnValidate())) {
            return loginInfoVo.getImageCode();
        } else if (validateCodeType.getParameterNameOnValidate().equals(ValidateCodeTypeEnum.SMS.getParameterNameOnValidate())){
            return loginInfoVo.getSmsCode();
        }
//        String codeInRequest = request.getParameter(validateCodeType.getParameterNameOnValidate());
        return null;
    }

    public AuthenticationFailureHandler getAuthenticationFailureHandler() {
        return authenticationFailureHandler;
    }

    public void setAuthenticationFailureHandler(AuthenticationFailureHandler authenticationFailureHandler) {
        this.authenticationFailureHandler = authenticationFailureHandler;
    }

    public SecurityProperties getSecurityProperties() {
        return securityProperties;
    }

    public void setSecurityProperties(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    public ValidateCodeRepository getValidateCodeRepository() {
        return validateCodeRepository;
    }

    public void setValidateCodeRepository(ValidateCodeRepository validateCodeRepository) {
        this.validateCodeRepository = validateCodeRepository;
    }

}
