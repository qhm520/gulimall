package com.qian.gulimall.common.entity.vo;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

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
//@Data
public class UserDetailsVo extends User {


    private static final long serialVersionUID = 7280905418257916241L;
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

    public UserDetailsVo(String username, String password, Collection <? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Set <Long> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(Set <Long> roleIds) {
        this.roleIds = roleIds;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
