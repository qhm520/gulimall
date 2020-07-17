package com.qian.gulimall.admin.service.impl;

import com.qian.gulimall.common.utils.Query;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qian.gulimall.common.utils.PageUtils;

import com.qian.gulimall.admin.dao.SysUserTokenDao;
import com.qian.gulimall.admin.entity.SysUserTokenEntity;
import com.qian.gulimall.admin.service.SysUserTokenService;


@Service("sysUserTokenService")
public class SysUserTokenServiceImpl extends ServiceImpl<SysUserTokenDao, SysUserTokenEntity> implements SysUserTokenService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
//        IPage<SysUserTokenEntity> page = this.page(
//                new Query <SysUserTokenEntity>().getPage(params),
//                new QueryWrapper<SysUserTokenEntity>()
//        );
//
//        return new PageUtils(page);
        return null;
    }

}
