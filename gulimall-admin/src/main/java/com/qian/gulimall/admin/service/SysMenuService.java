package com.qian.gulimall.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qian.gulimall.admin.api.criteria.SysMenuCriteria;
import com.qian.gulimall.admin.entity.SysMenuEntity;
import com.qian.gulimall.common.entity.vo.UserDetailsVo;
import com.qian.gulimall.common.utils.PageUtils;
import com.qian.gulimall.common.utils.Pageable;

import java.util.List;

/**
 * 菜单管理
 *
 * @author QIAN
 * @email 794308528@qq.com
 * @date 2020-04-19 08:57:20
 */
public interface SysMenuService extends IService<SysMenuEntity> {

    PageUtils queryPage(Pageable pageable, SysMenuCriteria sysMenuCriteria);

    List<SysMenuEntity> getUserMenuList(UserDetailsVo userDetailsVo);

    /**
     * 根据父菜单，查询子菜单
     * @param parentId 父菜单ID
     * @param menuIdList  用户菜单ID
     */
    List<SysMenuEntity> queryListParentId(Long parentId, List<Long> menuIdList);

    /**
     * 根据父菜单，查询子菜单
     * @param parentId 父菜单ID
     */
    List<SysMenuEntity> queryListParentId(Long parentId);

    /**
     * 获取不包含按钮的菜单列表
     */
    List<SysMenuEntity> queryNotButtonList();

    /**
     * 获取用户菜单列表
     */
    List<SysMenuEntity> getUserMenuList(Long userId);

    /**
     * 删除
     */
    void delete(Long menuId);
}

