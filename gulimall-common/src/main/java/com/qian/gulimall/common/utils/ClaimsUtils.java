package com.qian.gulimall.common.utils;

import com.qian.gulimall.common.exception.CommonEnum;
import com.qian.gulimall.common.exception.GulimallAuthenticationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;

import static com.qian.gulimall.common.security.SecurityConstants.AUTHORIZATION_BEARER;
import static com.qian.gulimall.common.security.SecurityConstants.AUTHORIZATION_NAME;
import static com.qian.gulimall.common.security.SecurityConstants.AUTHORIZATION_SIGNING_KEY;

/**
 * Created by IntelliJ IDEA.
 * ClaimsUtils is jwt
 *
 * @author QIAN
 * Date 2020/04/26
 * Time 14:30
 */
public class ClaimsUtils {

    /**
     * 获取jwt签名中的内容
     *
     * @return
     */
    public static Claims authorizationToClaims() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        String header = request.getHeader(AUTHORIZATION_NAME);
        if (header == null || !header.startsWith(AUTHORIZATION_BEARER)) {
            throw new GulimallAuthenticationException(CommonEnum.SIGNATURE_NOT_MATCH.getCode(), CommonEnum.SIGNATURE_NOT_MATCH.getMsg());
        }

        BASE64Encoder base64Encoder = new BASE64Encoder();
        String encode = base64Encoder.encode(AUTHORIZATION_SIGNING_KEY.getBytes());
        String authorization = StringUtils.substringAfter(header, AUTHORIZATION_BEARER);
        Claims claims = Jwts.parser() // 得到DefaultJwtParser
                .setSigningKey(encode) // 设置签名的秘钥
                .parseClaimsJws(authorization).getBody();// 设置需要解析的jwt

        return claims;
    }
}
