package com.qian.gulimall.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qian.gulimall.admin.api.criteria.SysRoleCriteria;
import com.qian.gulimall.admin.api.result.SysRoleResult;
import com.qian.gulimall.admin.dao.SysRoleDao;
import com.qian.gulimall.admin.entity.SysRoleEntity;
import com.qian.gulimall.admin.service.SysRoleMenuService;
import com.qian.gulimall.admin.service.SysRoleService;
import com.qian.gulimall.admin.service.SysUserRoleService;
import com.qian.gulimall.admin.service.SysUserService;
import com.qian.gulimall.common.utils.BeanKit;
import com.qian.gulimall.common.utils.Constant;
import com.qian.gulimall.common.utils.PageUtils;
import com.qian.gulimall.common.utils.Pageable;
import com.qian.gulimall.common.utils.Query;
import com.qian.gulimall.common.utils.RRException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;


@Service("sysRoleService")
public class SysRoleServiceImpl extends ServiceImpl<SysRoleDao, SysRoleEntity> implements SysRoleService {

    @Autowired
    private SysRoleMenuService sysRoleMenuService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Override
    public PageUtils queryPage(Pageable pageable, SysRoleCriteria sysRoleCriteria) {

        IPage<SysRoleEntity> page = this.page(
                new Query<SysRoleEntity>().getPage(pageable),
                new QueryWrapper<SysRoleEntity>()
                        // role_name
                        .like(StringUtils.isNotBlank(sysRoleCriteria.getRoleName()),"role_name", sysRoleCriteria.getRoleName())
                        // create_time
                        .between((sysRoleCriteria.getCreateTimeStart() != null && sysRoleCriteria.getCreateTimeEnd() != null), "create_time", sysRoleCriteria.getCreateTimeStart(), sysRoleCriteria.getCreateTimeEnd())
        );

        return new PageUtils(page, BeanKit.convertList(SysRoleResult.class, page.getRecords()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveRole(SysRoleEntity role) {
        role.setCreateTime(new Date());
        this.save(role);

        //检查权限是否越权
        checkPrems(role);

        //保存角色与菜单关系
        sysRoleMenuService.saveOrUpdate(role.getRoleId(), role.getMenuIdList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysRoleEntity role) {
        this.updateById(role);

        //检查权限是否越权
        checkPrems(role);

        //更新角色与菜单关系
        sysRoleMenuService.saveOrUpdate(role.getRoleId(), role.getMenuIdList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(Long[] roleIds) {
        //删除角色
        this.removeByIds(Arrays.asList(roleIds));

        //删除角色与菜单关联
        sysRoleMenuService.deleteBatch(roleIds);

        //删除角色与用户关联
        sysUserRoleService.deleteBatch(roleIds);
    }


    @Override
    public List <Long> queryRoleIdList(Long createUserId) {
        return baseMapper.queryRoleIdList(createUserId);
    }

    /**
     * 检查权限是否越权
     */
    private void checkPrems(SysRoleEntity role){
        //如果不是超级管理员，则需要判断角色的权限是否超过自己的权限
        if(role.getCreateUserId() == Constant.SUPER_ADMIN){
            return ;
        }

        //查询用户所拥有的菜单列表
        List<Long> menuIdList = sysUserService.queryAllMenuId(role.getCreateUserId());

        //判断是否越权
        if(!menuIdList.containsAll(role.getMenuIdList())){
            throw new RRException("新增角色的权限，已超出你的权限范围");
        }
    }

}
