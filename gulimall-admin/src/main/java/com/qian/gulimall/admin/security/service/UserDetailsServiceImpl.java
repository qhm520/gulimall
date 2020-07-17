package com.qian.gulimall.admin.security.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qian.gulimall.admin.entity.SysMenuEntity;
import com.qian.gulimall.admin.entity.SysRoleMenuEntity;
import com.qian.gulimall.admin.entity.SysUserEntity;
import com.qian.gulimall.admin.entity.SysUserRoleEntity;
import com.qian.gulimall.common.entity.vo.UserDetailsVo;
import com.qian.gulimall.admin.service.SysMenuService;
import com.qian.gulimall.admin.service.SysRoleMenuService;
import com.qian.gulimall.admin.service.SysUserRoleService;
import com.qian.gulimall.admin.service.SysUserService;
import com.qian.gulimall.common.utils.Constant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by IntelliJ IDEA.
 * UserDetailsServiceImpl is 身份认证
 *
 * @author QIAN
 * Date 2020/04/18
 * Time 14:52
 */
@Slf4j
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    @Autowired
    private SysMenuService sysMenuService;

    @Override
    public UserDetailsVo loadUserByUsername(String s) throws UsernameNotFoundException {
        SysUserEntity sysUserEntity = sysUserService.getOne(new QueryWrapper <SysUserEntity>().eq("username", s));
        //用户权限列表
        Set <String> perms = new HashSet <>();
        Set <Long> roleIdSet = null;
        perms.add("ROLE_USER");
        perms.add("ROLE_ADMIN");
        if (sysUserEntity == null) {
            throw new UsernameNotFoundException("账户不存在");
        }
        // TODO 要改为用redis缓存
        //系统管理员，拥有最高权限
        if (sysUserEntity.getUserId() == Constant.SUPER_ADMIN) {
            List <SysMenuEntity> list = sysMenuService.list();
            if (!CollectionUtils.isEmpty(list)) {
                list.stream().filter(menu -> StringUtils.isNotBlank(menu.getPerms())).forEach(menu -> perms.add(menu.getPerms().trim()));
            }
        } else {
            List <SysUserRoleEntity> sysUserRoleEntityList = sysUserRoleService.list(new QueryWrapper <SysUserRoleEntity>().eq("user_id", sysUserEntity.getUserId()));
            if (!CollectionUtils.isEmpty(sysUserRoleEntityList)) {
                roleIdSet = sysUserRoleEntityList.stream().map(sysUser -> sysUser.getRoleId()).collect(Collectors.toSet());
                List <SysRoleMenuEntity> sysRoleMenuEntityList = sysRoleMenuService.list(new QueryWrapper <SysRoleMenuEntity>().in("role_id", roleIdSet));
                if (!CollectionUtils.isEmpty(sysRoleMenuEntityList)) {
                    Set <Long> menuIdSet = sysRoleMenuEntityList.stream().map(sysRoleMenu -> sysRoleMenu.getMenuId()).collect(Collectors.toSet());
                    List <SysMenuEntity> sysMenuEntityList = sysMenuService.list(new QueryWrapper <SysMenuEntity>().in("menu_id", menuIdSet));
                    if (!CollectionUtils.isEmpty(sysMenuEntityList)) {
                        sysMenuEntityList.stream().filter(menu -> StringUtils.isNotBlank(menu.getPerms())).forEach(menu -> perms.add(menu.getPerms()));
                    }
                }
            }
        }
        log.info("登陆用户名：" + s + "登陆成功");
        UserDetailsVo userDetailsVo = new UserDetailsVo(s, sysUserEntity.getPassword(),
                // toString方法 去掉中括号
                AuthorityUtils.commaSeparatedStringToAuthorityList(StringUtils.strip(perms.toString(),"[]")));
        BeanUtils.copyProperties(sysUserEntity, userDetailsVo);
        userDetailsVo.setRoleIds(roleIdSet);
        return userDetailsVo;

    }
}
