package com.qian.gulimall.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qian.gulimall.admin.api.criteria.SysLoginLogCriteria;
import com.qian.gulimall.admin.api.dto.SysLoginLogDto;
import com.qian.gulimall.admin.entity.SysLoginLogEntity;
import com.qian.gulimall.common.utils.PageUtils;
import com.qian.gulimall.common.utils.Pageable;

import javax.servlet.http.HttpServletRequest;

/**
 * 系统登录日志
 *
 * @author QIAN
 * @email 794308528@qq.com
 * @date 2020-07-24 15:17:44
 */
public interface SysLoginLogService extends IService<SysLoginLogEntity> {

    PageUtils queryPage(Pageable pageable, SysLoginLogCriteria sysLoginLogCriteria);

    SysLoginLogDto getSysLoginLogById(Long id);

    public void saveLoginSysLog(HttpServletRequest request, String operation, String method);
}

