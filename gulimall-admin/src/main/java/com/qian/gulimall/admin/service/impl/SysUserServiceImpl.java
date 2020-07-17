package com.qian.gulimall.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qian.gulimall.admin.api.criteria.SysUserCriteria;
import com.qian.gulimall.admin.api.result.SysUserResult;
import com.qian.gulimall.admin.dao.SysUserDao;
import com.qian.gulimall.admin.entity.SysUserEntity;
import com.qian.gulimall.admin.service.SysRoleService;
import com.qian.gulimall.admin.service.SysUserRoleService;
import com.qian.gulimall.admin.service.SysUserService;
import com.qian.gulimall.common.utils.BeanKit;
import com.qian.gulimall.common.utils.Constant;
import com.qian.gulimall.common.utils.PageUtils;
import com.qian.gulimall.common.utils.Pageable;
import com.qian.gulimall.common.utils.Query;
import com.qian.gulimall.common.utils.RRException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;


@Slf4j
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUserEntity> implements SysUserService {

    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 分页查询用户信息
     * @param pageable 分页
     * @param sysUserCriteria 查询条件
     * @return
     */
    @Override
    public PageUtils queryPage(Pageable pageable, SysUserCriteria sysUserCriteria) {
        IPage<SysUserEntity> page = this.page(
                // 分页
                new Query<SysUserEntity>().getPage(pageable),
                // 查询条件
                new QueryWrapper<SysUserEntity>()
                        // username
                        .like(StringUtils.isNotBlank(sysUserCriteria.getUsername()),"username", sysUserCriteria.getUsername())
                        // mobile
                        .like(StringUtils.isNotBlank(sysUserCriteria.getMobile()),"mobile", sysUserCriteria.getMobile())
                        // status
                        .eq(null != sysUserCriteria.getStatus(), "status", sysUserCriteria.getStatus())
                        // create_time
                        .between((sysUserCriteria.getCreateTimeStart() != null && sysUserCriteria.getCreateTimeEnd() != null), "create_time", sysUserCriteria.getCreateTimeStart(), sysUserCriteria.getCreateTimeEnd())
        );
        // 封装返回数据到result对象中
        return new PageUtils(page, BeanKit.convertList(SysUserResult.class, page.getRecords()));
    }

    @Override
    public List<String> queryAllPerms(Long userId) {
        return baseMapper.queryAllPerms(userId);
    }

    @Override
    public List<Long> queryAllMenuId(Long userId) {
        return baseMapper.queryAllMenuId(userId);
    }

    @Override
    public SysUserEntity queryByUserName(String username) {
        return baseMapper.queryByUserName(username);
    }

    @Override
    @Transactional
    public void saveUser(SysUserEntity user) {
        user.setCreateTime(new Date());
        //sha256加密
        String salt = RandomStringUtils.randomAlphanumeric(20);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setSalt(salt);
        this.save(user);

        //检查角色是否越权
        checkRole(user);

        //保存用户与角色关系
        sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
    }

    @Override
    @Transactional
    public void update(SysUserEntity user) {
        if(StringUtils.isBlank(user.getPassword())){
            user.setPassword(null);
        }else{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        this.updateById(user);

        //检查角色是否越权
        checkRole(user);

        //保存用户与角色关系
        sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
    }

    @Override
    public void deleteBatch(Long[] userId) {
        this.removeByIds(Arrays.asList(userId));
    }

    @Override
    public boolean updatePassword(Long userId, String password, String newPassword) {
        SysUserEntity userEntity = new SysUserEntity();
        userEntity.setPassword(newPassword);
        return this.update(userEntity,
                new QueryWrapper<SysUserEntity>().eq("user_id", userId).eq("password", password));
    }

    /**
     * 检查角色是否越权
     */
    private void checkRole(SysUserEntity user){
        if(user.getRoleIdList() == null || user.getRoleIdList().size() == 0){
            return;
        }
        //如果不是超级管理员，则需要判断用户的角色是否自己创建
        if(user.getCreateUserId() == Constant.SUPER_ADMIN){
            return ;
        }

        //查询用户创建的角色列表
        List<Long> roleIdList = sysRoleService.queryRoleIdList(user.getCreateUserId());

        //判断是否越权
        if(!roleIdList.containsAll(user.getRoleIdList())){
            throw new RRException("新增用户所选角色，不是本人创建");
        }
    }
}
