package com.qian.gulimall.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qian.gulimall.admin.api.criteria.SysConfigCriteria;
import com.qian.gulimall.admin.dao.SysConfigDao;
import com.qian.gulimall.admin.entity.SysConfigEntity;
import com.qian.gulimall.admin.service.SysConfigService;
import com.qian.gulimall.common.utils.PageUtils;
import com.qian.gulimall.common.utils.Pageable;
import com.qian.gulimall.common.utils.Query;
import org.springframework.stereotype.Service;


@Service("sysConfigService")
public class SysConfigServiceImpl extends ServiceImpl<SysConfigDao, SysConfigEntity> implements SysConfigService {

    @Override
    public PageUtils queryPage(Pageable pageable, SysConfigCriteria sysConfigCriteria) {
        IPage<SysConfigEntity> page = this.page(
                new Query<SysConfigEntity>().getPage(pageable),
                new QueryWrapper<SysConfigEntity>()
        );

        return new PageUtils(page);
    }

}
