package com.qian.gulimall.security.validate;

import com.qian.gulimall.common.service.RedisTemplateService;
import com.qian.gulimall.common.utils.IPUtils;
import com.qian.gulimall.security.exception.ValidateCodeException;
import com.qian.gulimall.security.properties.contsant.ValidateCodeTypeEnum;
import org.springframework.web.context.request.ServletWebRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * Created by IntelliJ IDEA.
 * RedisValidateCodeRepository is 使用redis存储验证码
 *
 * @author QIAN
 * Date 2020/04/18
 * Time 16:44
 */
public class RedisValidateCodeRepository implements ValidateCodeRepository {

    public static final String MOBILE_HEADER_NAME = "mobile";

    public static final String VALIDATECODE_REDISKEY_PREFIX = "code:";
    @Resource
    private RedisTemplateService redisTemplateService;

    @Override
    public void save(ServletWebRequest request, ValidateCode code, ValidateCodeTypeEnum codeType) {
        redisTemplateService.set(getRedisKey(request, codeType), code, 30*60);
    }

    @Override
    public ValidateCode get(ServletWebRequest request, ValidateCodeTypeEnum codeType) {
        Object codeInRedis = redisTemplateService.get(getRedisKey(request, codeType));
        if (codeInRedis != null) {
            return (ValidateCode) codeInRedis;
        } else {
            return null;
        }
    }

    @Override
    public void remove(ServletWebRequest request, ValidateCodeTypeEnum codeType) {
        redisTemplateService.del(getRedisKey(request, codeType));
    }

    private String getRedisKey(ServletWebRequest request, ValidateCodeTypeEnum codeType) {
        String mobile = getIPAddress(request);
        StringBuilder buff = new StringBuilder(VALIDATECODE_REDISKEY_PREFIX);
        buff.append(codeType).append(":").append(mobile);
        return buff.toString();
    }

    private String getIPAddress(ServletWebRequest request) {
        String ipAddress = IPUtils.getIPAddress(request.getRequest());
        if (ipAddress == null) {
            throw new ValidateCodeException("获取IP地址失败");
        }
        return ipAddress;
    }


}
