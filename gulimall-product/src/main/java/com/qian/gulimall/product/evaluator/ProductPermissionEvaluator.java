package com.qian.gulimall.product.evaluator;

import com.qian.gulimall.common.entity.result.UserDetailsResult;
import com.qian.gulimall.common.utils.R;
import com.qian.gulimall.product.feign.AdminFeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.Set;

import static com.qian.gulimall.common.security.SecurityConstants.DEFAULT_RESULT;

/**
 * Created by IntelliJ IDEA.
 * ProductPermissionEvaluator is 自定义权限过滤器
 *
 * @author QIAN
 * Date 2020/04/17
 * Time 11:01
 */
@Slf4j
@Configuration
public class ProductPermissionEvaluator implements PermissionEvaluator {

    @Autowired
    private AdminFeignService adminFeignService;

    /**
     * 通过请求过来的url和数据库中的角色对应url来对应校验权限
     *
     * @param authentication
     * @param targetUrl
     * @param targetPermission
     * @return
     */
    @Override
    public boolean hasPermission(Authentication authentication, Object targetUrl, Object targetPermission) {
        if (log.isInfoEnabled()) {
            log.info("targetPermission: {}", targetPermission);
        }
        UserDetailsResult userDetailsResult = null;
        // 获取当前用户信息
        R r = adminFeignService.me();
        if (r.getCode() == 0) {
            userDetailsResult = (UserDetailsResult) r.get(DEFAULT_RESULT);
            if (!CollectionUtils.isEmpty(userDetailsResult.getAuthorities())) {
                Set <String> authorities = userDetailsResult.getAuthorities();
                return authorities.contains(targetPermission);
            }
        }
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object targetPermission) {
        return false;
    }
}
