package com.qian.gulimall.common.entity.result;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * UserDetailsVo is
 *
 * @author QIAN
 * Date 2020/04/18
 * Time 17:59
 */
@Data
public class UserDetailsResult implements Serializable {

    private static final long serialVersionUID = -5806903958507265726L;
    /**
     *
     */
    private Long userId;

    private Set <Long> roleIds;
    /**
     * 盐
     */
    private String salt;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 状态  0：禁用   1：正常
     */
    private Integer status;

    private String password;

    private String username;

    private Set<String> authorities;

    private boolean accountNonExpired;

    private boolean accountNonLocked;

    private boolean credentialsNonExpired;

    private boolean enabled;
}
