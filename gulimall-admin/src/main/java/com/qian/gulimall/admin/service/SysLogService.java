package com.qian.gulimall.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qian.gulimall.admin.api.criteria.SysLogCriteria;
import com.qian.gulimall.common.utils.PageUtils;
import com.qian.gulimall.admin.entity.SysLogEntity;
import com.qian.gulimall.common.utils.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 系统日志
 *
 * @author QIAN
 * @email 794308528@qq.com
 * @date 2020-04-19 08:57:20
 */
public interface SysLogService extends IService<SysLogEntity> {

    PageUtils queryPage(Pageable pageable, SysLogCriteria sysLogCriteria);

    /**
     * 保存登录日志
     * @param request
     * @param operation
     * @param method
     */
    void saveLoginSysLog(HttpServletRequest request, String operation, String method);
}

