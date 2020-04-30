package com.qian.gulimall.admin.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qian.gulimall.common.utils.PageUtils;
import com.qian.gulimall.common.utils.Query;

import com.qian.gulimall.admin.dao.SysCaptchaDao;
import com.qian.gulimall.admin.entity.SysCaptchaEntity;
import com.qian.gulimall.admin.service.SysCaptchaService;


@Service("sysCaptchaService")
public class SysCaptchaServiceImpl extends ServiceImpl<SysCaptchaDao, SysCaptchaEntity> implements SysCaptchaService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysCaptchaEntity> page = this.page(
                new Query<SysCaptchaEntity>().getPage(params),
                new QueryWrapper<SysCaptchaEntity>()
        );

        return new PageUtils(page);
    }

}
