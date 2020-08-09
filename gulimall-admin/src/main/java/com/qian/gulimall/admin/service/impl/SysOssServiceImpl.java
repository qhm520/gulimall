package com.qian.gulimall.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qian.gulimall.admin.api.criteria.SysOssCriteria;
import com.qian.gulimall.admin.api.result.SysOssResult;
import com.qian.gulimall.admin.dao.SysOssDao;
import com.qian.gulimall.admin.entity.SysOssEntity;
import com.qian.gulimall.admin.service.SysOssService;
import com.qian.gulimall.common.utils.BeanKit;
import com.qian.gulimall.common.utils.PageUtils;
import com.qian.gulimall.common.utils.Pageable;
import com.qian.gulimall.common.utils.Query;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service("sysOssService")
public class SysOssServiceImpl extends ServiceImpl<SysOssDao, SysOssEntity> implements SysOssService {

    @Value("${fdfs.service}")
    private String fdfsService;

    /**
     * 分页查询
     *
     * @param pageable
     * @param sysOssCriteria
     * @return
     */
    @Override
    public PageUtils queryPage(Pageable pageable, SysOssCriteria sysOssCriteria) {
        Page<SysOssCriteria> sysOssCriteriaPage = new Page<SysOssCriteria>(pageable.getPageNumber(), pageable.getPageSize());
        IPage<SysOssResult> sysOssResultIPage = this.baseMapper.querySysOssPage(sysOssCriteriaPage, sysOssCriteria);
        if (!CollectionUtils.isEmpty(sysOssResultIPage.getRecords())) {
            for (SysOssResult sysOssResult : sysOssResultIPage.getRecords()) {
                // 加上IP地址
                sysOssResult.setUrl(fdfsService + "/" + sysOssResult.getUrl());
            }
            // 加上IP地址
//            List<SysOssResult> collect = sysOssResultIPage.getRecords().stream().map(result -> {
//                result.setUrl(fdfsService + "/" + result.getUrl());
//                return result;
//            }).collect(Collectors.toList());
        }
        return new PageUtils(sysOssResultIPage, sysOssResultIPage.getRecords());
    }

}
