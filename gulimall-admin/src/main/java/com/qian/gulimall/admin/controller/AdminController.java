package com.qian.gulimall.admin.controller;

import com.qian.gulimall.common.entity.result.UserDetailsResult;
import com.qian.gulimall.common.entity.vo.UserDetailsVo;
import com.qian.gulimall.common.utils.R;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Set;
import java.util.stream.Collectors;

import static com.qian.gulimall.common.security.SecurityConstants.DEFAULT_RESULT;

/**
 * Created by IntelliJ IDEA.
 * AdminController is
 *
 * @author QIAN
 * Date 2020/04/21
 * Time 15:15
 */
@RestController
public class AdminController {

    /**
     * 当前用户
     * @return
     */
    @GetMapping("/me")
    public R me(Authentication authentication) {
        UserDetailsVo userDetailsVo = (UserDetailsVo)authentication.getPrincipal();
        UserDetailsResult userDetailsResult = new UserDetailsResult();
        BeanUtils.copyProperties(userDetailsVo, userDetailsResult);
        if (!CollectionUtils.isEmpty(userDetailsVo.getAuthorities())) {
            // 该用户拥有的权限
            Set <String> collect = userDetailsVo.getAuthorities().stream().map(authority -> authority.getAuthority()).collect(Collectors.toSet());
            userDetailsResult.setAuthorities(collect);
        }
        return R.ok().put(DEFAULT_RESULT, userDetailsResult);
    }
}
