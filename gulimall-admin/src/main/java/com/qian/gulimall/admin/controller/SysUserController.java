package com.qian.gulimall.admin.controller;

import com.qian.gulimall.admin.api.criteria.SysUserCriteria;
import com.qian.gulimall.admin.entity.SysUserEntity;
import com.qian.gulimall.admin.service.SysUserRoleService;
import com.qian.gulimall.admin.service.SysUserService;
import com.qian.gulimall.common.annotation.SysLog;
import com.qian.gulimall.common.entity.vo.UserDetailsVo;
import com.qian.gulimall.common.utils.PageUtils;
import com.qian.gulimall.common.utils.Pageable;
import com.qian.gulimall.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;


/**
 * 系统用户
 *
 * @author QIAN
 * @email 794308528@qq.com
 * @date 2020-04-19 08:57:20
 */

@RestController
@RequestMapping("sys/user")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserRoleService sysUserRoleService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("admin:sysuser:list")
    public R list(Pageable pageable, @ModelAttribute SysUserCriteria sysUserCriteria) {
        PageUtils page = sysUserService.queryPage(pageable, sysUserCriteria);

        return R.ok().put("page", page);
    }

    /**
     * 获取登录的用户信息
     */
    @GetMapping("/info")
    public R info() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsVo userDetailsVo = (UserDetailsVo) authentication.getPrincipal();
        return R.ok().put("user", userDetailsVo);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{userId}")
    //@RequiresPermissions("admin:sysuser:info")
    public R info(@PathVariable("userId") Long userId) {
        SysUserEntity sysUser = sysUserService.getById(userId);
            //获取用户所属的角色列表
        List<Long> roleIdList = sysUserRoleService.queryRoleIdList(userId);
        sysUser.setRoleIdList(roleIdList);
        return R.ok().put("user", sysUser);
    }

    /**
     * 保存
     */
    @SysLog("新增用户")
    @RequestMapping("/save")
    @PreAuthorize("hasPermission('', 'sys:user:save')")
    public R save(@RequestBody SysUserEntity sysUser, Authentication authentication) {
        UserDetailsVo userDetailsVo = (UserDetailsVo) authentication.getPrincipal();
        sysUser.setCreateUserId(userDetailsVo.getUserId());
        sysUserService.saveUser(sysUser);
        return R.ok();
    }

    /**
     * 修改
     */
    @SysLog("修改用户")
    @RequestMapping("/update")
    @PreAuthorize("hasPermission('', 'sys:user:update')")
    public R update(@RequestBody SysUserEntity sysUser, Authentication authentication) {
        UserDetailsVo userDetailsVo = (UserDetailsVo) authentication.getPrincipal();
        sysUser.setCreateUserId(userDetailsVo.getUserId());
        sysUserService.update(sysUser);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("admin:sysuser:delete")
    public R delete(@RequestBody Long[] userIds) {
        sysUserService.removeByIds(Arrays.asList(userIds));
        return R.ok();
    }

   /* @GetMapping("query/{id}")
    public R querySysUser(@PathVariable("id") Integer id) {
        SysUserEntity sysUserEntity = sysUserService.querySysUserById(id);
        return R.ok().put("data", sysUserEntity);
    }*/

}
