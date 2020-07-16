package com.qian.gulimall.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qian.gulimall.admin.dao.SysMenuDao;
import com.qian.gulimall.admin.entity.SysMenuEntity;
import com.qian.gulimall.admin.entity.SysRoleMenuEntity;
import com.qian.gulimall.admin.entity.SysUserRoleEntity;
import com.qian.gulimall.admin.service.SysUserService;
import com.qian.gulimall.common.entity.vo.UserDetailsVo;
import com.qian.gulimall.admin.service.SysMenuService;
import com.qian.gulimall.admin.service.SysRoleMenuService;
import com.qian.gulimall.admin.service.SysUserRoleService;
import com.qian.gulimall.common.utils.Constant;
import com.qian.gulimall.common.utils.PageUtils;
import com.qian.gulimall.common.utils.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@Service("sysMenuService")
public class SysMenuServiceImpl extends ServiceImpl <SysMenuDao, SysMenuEntity> implements SysMenuService {

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    private SysUserService sysUserService;

    @Override
    public List <SysMenuEntity> queryListParentId(Long parentId, List <Long> menuIdList) {
        List <SysMenuEntity> menuList = queryListParentId(parentId);
        if (menuIdList == null) {
            return menuList;
        }

        List <SysMenuEntity> userMenuList = new ArrayList <>();
        menuList.stream()
                .filter(menu -> menuIdList.contains(menu.getMenuId()))
                .forEach(menu -> userMenuList.add(menu));
        return userMenuList;
    }

    @Override
    public List <SysMenuEntity> queryNotButtonList() {
        return baseMapper.queryNotButtonList();
    }

    @Override
    public List <SysMenuEntity> getUserMenuList(Long userId) {
        //系统管理员，拥有最高权限
        if(userId == Constant.SUPER_ADMIN){
            return getAllMenuList(null);
        }

        //用户菜单列表
        List<Long> menuIdList = sysUserService.queryAllMenuId(userId);
        return getAllMenuList(menuIdList);
    }

    @Override
    public void delete(Long menuId) {

    }

    @Override
    public List<SysMenuEntity> queryListParentId(Long parentId) {
        return baseMapper.selectList(new QueryWrapper <SysMenuEntity>().eq("parent_id", parentId));
    }


    @Override
    public PageUtils queryPage(Map <String, Object> params) {
        IPage <SysMenuEntity> page = this.page(
                new Query <SysMenuEntity>().getPage(params),
                new QueryWrapper <SysMenuEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List <SysMenuEntity> getUserMenuList(UserDetailsVo userDetailsVo) {
        //系统管理员，拥有最高权限
        if (userDetailsVo.getUserId() == Constant.SUPER_ADMIN) {
            return getAllMenuList(null);
        }

        if (userDetailsVo.getRoleIds() != null) {
            List <SysMenuEntity> sysMenuEntityList = sysMenuService.list(new QueryWrapper <SysMenuEntity>().in("menu_id", userDetailsVo.getRoleIds()));
            if (!CollectionUtils.isEmpty(sysMenuEntityList)) {
                return getAllMenuList(sysMenuEntityList.stream().map(menu -> menu.getMenuId()).collect(Collectors.toList()));
            }
        } else {
            // TODO  先查询redis缓存，如果没有再数据库，把查询到数据放入缓存

            //用户菜单列表
            List <SysUserRoleEntity> sysUserRoleEntityList = sysUserRoleService.list(new QueryWrapper <SysUserRoleEntity>().eq("user_id", userDetailsVo.getUserId()));
            if (!CollectionUtils.isEmpty(sysUserRoleEntityList)) {
                Set <Long> sysUserRoleSet = sysUserRoleEntityList.stream().map(sysUser -> sysUser.getRoleId()).collect(Collectors.toSet());
                List <SysRoleMenuEntity> sysRoleMenuEntityList = sysRoleMenuService.list(new QueryWrapper <SysRoleMenuEntity>().in("role_id", sysUserRoleSet));
                if (!CollectionUtils.isEmpty(sysRoleMenuEntityList)) {
                    Set <Long> sysRoleSet = sysRoleMenuEntityList.stream().map(sysRoleMenu -> sysRoleMenu.getMenuId()).collect(Collectors.toSet());
                    List <SysMenuEntity> sysMenuEntityList = sysMenuService.list(new QueryWrapper <SysMenuEntity>().in("menu_id", sysRoleSet));
                    if (!CollectionUtils.isEmpty(sysMenuEntityList)) {
                        return getAllMenuList(sysMenuEntityList.stream().map(menu -> menu.getMenuId()).collect(Collectors.toList()));
                    }
                }
            }
        }
        return null;
    }

    /**
     * 获取所有菜单列表
     */
    private List <SysMenuEntity> getAllMenuList(List <Long> menuIdList) {
        //查询根菜单列表
        List <SysMenuEntity> menuList = queryListParentId(0L, menuIdList);
        //递归获取子菜单
        getMenuTreeList(menuList, menuIdList);

        return menuList;
    }

    /**
     * 递归
     */
    private List <SysMenuEntity> getMenuTreeList(List <SysMenuEntity> menuList, List <Long> menuIdList) {
        List <SysMenuEntity> subMenuList = new ArrayList <SysMenuEntity>();

        for (SysMenuEntity entity : menuList) {
            //目录
            if (entity.getType() == Constant.MenuType.CATALOG.getValue()) {
                entity.setList(getMenuTreeList(queryListParentId(entity.getMenuId(), menuIdList), menuIdList));
            }
            subMenuList.add(entity);
        }

        return subMenuList;
    }

}
