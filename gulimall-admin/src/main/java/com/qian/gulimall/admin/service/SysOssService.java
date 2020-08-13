package com.qian.gulimall.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qian.gulimall.admin.api.criteria.SysOssCriteria;
import com.qian.gulimall.admin.api.dto.SysOssDto;
import com.qian.gulimall.admin.entity.SysOssEntity;
import com.qian.gulimall.common.utils.PageUtils;
import com.qian.gulimall.common.utils.Pageable;

import java.util.List;

/**
 * 文件上传
 *
 * @author QIAN
 * @email 794308528@qq.com
 * @date 2020-04-19 08:57:20
 */
public interface SysOssService extends IService<SysOssEntity> {

    PageUtils queryPage(Pageable pageable, SysOssCriteria sysOssCriteria);

    Long saveSysOss(SysOssDto sysOssDto);

    Integer updateStatusByIds(Integer stauts, List<String> list);
}

