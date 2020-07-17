package com.qian.gulimall.admin.service.impl;

import com.qian.gulimall.common.utils.Query;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qian.gulimall.common.utils.PageUtils;

import com.qian.gulimall.admin.dao.TbUserDao;
import com.qian.gulimall.admin.entity.TbUserEntity;
import com.qian.gulimall.admin.service.TbUserService;


@Service("tbUserService")
public class TbUserServiceImpl extends ServiceImpl<TbUserDao, TbUserEntity> implements TbUserService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
//        IPage<TbUserEntity> page = this.page(
//                new Query <TbUserEntity>().getPage(params),
//                new QueryWrapper<TbUserEntity>()
//        );
//
//        return new PageUtils(page);
        return null;
    }

}
