package com.qian.gulimall.admin.evaluator;

import com.qian.gulimall.common.entity.vo.UserDetailsVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by IntelliJ IDEA.
 * GulimallPermissionEvaluator is 自定义权限过滤器
 *
 * @author QIAN
 * Date 2020/04/17
 * Time 11:01
 */
@Slf4j
@Configuration
public class GulimallPermissionEvaluator implements PermissionEvaluator {

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
        // 获得loadUserByUsername()方法的结果
        UserDetailsVo userDetailsVo = (UserDetailsVo)authentication.getPrincipal();
        // 获得loadUserByUsername()中注入的角色
        Collection <GrantedAuthority> authorities = userDetailsVo.getAuthorities();
        List <GrantedAuthority> collect = authorities.stream().filter((authority) -> authority.getAuthority().equals(targetPermission)).collect(Collectors.toList());
        return collect.size() > 0;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object targetPermission) {
        return false;
    }
}
