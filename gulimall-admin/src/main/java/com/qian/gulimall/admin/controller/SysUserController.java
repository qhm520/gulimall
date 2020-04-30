package com.qian.gulimall.admin.controller;

import java.util.Arrays;
import java.util.Map;

import com.qian.gulimall.common.entity.vo.UserDetailsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.qian.gulimall.admin.entity.SysUserEntity;
import com.qian.gulimall.admin.service.SysUserService;
import com.qian.gulimall.common.utils.PageUtils;
import com.qian.gulimall.common.utils.R;



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

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("admin:sysuser:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = sysUserService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 获取登录的用户信息
     */
    @GetMapping("/info")
    public R info(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsVo userDetailsVo = (UserDetailsVo)authentication.getPrincipal();
        return R.ok().put("user", userDetailsVo);
    }



    /**
     * 信息
     */
    @RequestMapping("/info/{userId}")
    //@RequiresPermissions("admin:sysuser:info")
    public R info(@PathVariable("userId") Long userId){
		SysUserEntity sysUser = sysUserService.getById(userId);

        return R.ok().put("sysUser", sysUser);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @PreAuthorize("hasPermission('', 'sys:user:save')")
    public R save(@RequestBody SysUserEntity sysUser){
		sysUserService.save(sysUser);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @PreAuthorize("hasPermission('', 'sys:user:update')")
    public R update(@RequestBody SysUserEntity sysUser){
		sysUserService.updateById(sysUser);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("admin:sysuser:delete")
    public R delete(@RequestBody Long[] userIds){
		sysUserService.removeByIds(Arrays.asList(userIds));
        return R.ok();
    }

}
