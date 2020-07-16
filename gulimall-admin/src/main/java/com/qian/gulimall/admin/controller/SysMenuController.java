package com.qian.gulimall.admin.controller;

import com.qian.gulimall.admin.entity.SysMenuEntity;
import com.qian.gulimall.admin.service.SysMenuService;
import com.qian.gulimall.common.annotation.SysLog;
import com.qian.gulimall.common.entity.vo.UserDetailsVo;
import com.qian.gulimall.common.utils.PageUtils;
import com.qian.gulimall.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;



/**
 * 菜单管理
 *
 * @author QIAN
 * @email 794308528@qq.com
 * @date 2020-04-19 08:57:20
 */
@RestController
@RequestMapping("sys/menu")
public class SysMenuController {
    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 导航菜单
     */
    @GetMapping("/nav")
    public R nav(){
        UserDetailsVo userDetailsVo = getUserDetailsVo();
        List <SysMenuEntity> menuList = sysMenuService.getUserMenuList(userDetailsVo);
        return R.ok().put("menuList", menuList).put("permissions", userDetailsVo.getAuthorities().stream().map(auth -> auth.getAuthority()).collect(Collectors.toSet()));
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("admin:sysmenu:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = sysMenuService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 选择菜单(添加、修改菜单)
     */
    @GetMapping("/select")
    public R select(){
        //查询列表数据
        List<SysMenuEntity> menuList = sysMenuService.queryNotButtonList();

        //添加顶级菜单
        SysMenuEntity root = new SysMenuEntity();
        root.setMenuId(0L);
        root.setName("一级菜单");
        root.setParentId(-1L);
        root.setOpen(true);
        menuList.add(root);

        return R.ok().put("menuList", menuList);
    }

    /**
     * 菜单信息
     */
    @RequestMapping("/info/{menuId}")
    //@RequiresPermissions("admin:sysmenu:info")
    public R info(@PathVariable("menuId") Long menuId){
		SysMenuEntity sysMenu = sysMenuService.getById(menuId);

        return R.ok().put("sysMenu", sysMenu);
    }

    /**
     * 保存
     */
    @SysLog("保存菜单")
    @RequestMapping("/save")
    //@RequiresPermissions("admin:sysmenu:save")
    public R save(@RequestBody SysMenuEntity sysMenu){
		sysMenuService.save(sysMenu);

        return R.ok();
    }

    /**
     * 修改
     */
    @SysLog("修改菜单")
    @RequestMapping("/update")
    //@RequiresPermissions("admin:sysmenu:update")
    public R update(@RequestBody SysMenuEntity sysMenu){
		sysMenuService.updateById(sysMenu);

        return R.ok();
    }

    /**
     * 删除
     */
    @SysLog("删除菜单")
    @RequestMapping("/delete")
    //@RequiresPermissions("admin:sysmenu:delete")
    public R delete(@RequestBody Long[] menuIds){
		sysMenuService.removeByIds(Arrays.asList(menuIds));

        return R.ok();
    }

    /**
     * 获取用户id
     * @return
     */
    private UserDetailsVo getUserDetailsVo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsVo userDetailsVo = (UserDetailsVo)authentication.getPrincipal();
        return userDetailsVo;
    }

}
