package com.qian.gulimall.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qian.gulimall.admin.api.criteria.SysConfigCriteria;
import com.qian.gulimall.common.utils.PageUtils;
import com.qian.gulimall.admin.entity.SysConfigEntity;
import com.qian.gulimall.common.utils.Pageable;

import java.util.Map;

/**
 * 系统配置信息表
 *
 * @author QIAN
 * @email 794308528@qq.com
 * @date 2020-04-19 08:57:20
 */
public interface SysConfigService extends IService<SysConfigEntity> {

    PageUtils queryPage(Pageable pageable, SysConfigCriteria sysConfigCriteria);
}

