package com.qian.gulimall.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qian.gulimall.admin.api.criteria.SysLoginLogCriteria;
import com.qian.gulimall.admin.api.dto.SysLoginLogDto;
import com.qian.gulimall.admin.api.result.SysLoginLogResult;
import com.qian.gulimall.admin.dao.SysLoginLogDao;
import com.qian.gulimall.admin.entity.SysLogEntity;
import com.qian.gulimall.admin.entity.SysLoginLogEntity;
import com.qian.gulimall.admin.service.SysLoginLogService;
import com.qian.gulimall.common.entity.vo.LoginInfoVo;
import com.qian.gulimall.common.security.SecurityConstants;
import com.qian.gulimall.common.utils.BeanKit;
import com.qian.gulimall.common.utils.IPUtils;
import com.qian.gulimall.common.utils.PageUtils;
import com.qian.gulimall.common.utils.Pageable;
import com.qian.gulimall.common.utils.Query;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;


@Service("sysLoginLogService")
public class SysLoginLogServiceImpl extends ServiceImpl<SysLoginLogDao, SysLoginLogEntity> implements SysLoginLogService {

    @Override
    public PageUtils queryPage(Pageable pageable, SysLoginLogCriteria sysLoginLogCriteria) {
        IPage<SysLoginLogEntity> page = this.page(
                new Query<SysLoginLogEntity>().getPage(pageable),
                // 查询条件
                new QueryWrapper<SysLoginLogEntity>()
                        // username
                        .like(StringUtils.isNotBlank(sysLoginLogCriteria.getUsername()),"username", sysLoginLogCriteria.getUsername())
                        // operation
                        .like(StringUtils.isNotBlank(sysLoginLogCriteria.getOperation()),"operation", sysLoginLogCriteria.getOperation())
                        // ip
                        .like(StringUtils.isNotBlank(sysLoginLogCriteria.getIp()), "ip", sysLoginLogCriteria.getIp())
                        // create_date
                        .between((sysLoginLogCriteria.getCreateDateStart() != null && sysLoginLogCriteria.getCreateDateEnd() != null), "create_date", sysLoginLogCriteria.getCreateDateStart(), sysLoginLogCriteria.getCreateDateEnd())
                        // create_time
                        .orderBy(true, false, "create_date")
        );
        return new PageUtils(page, BeanKit.convertList(SysLoginLogResult.class, page.getRecords()));
    }

    @Override
    public SysLoginLogDto getSysLoginLogById(Long id) {
        SysLoginLogEntity sysLoginLogEntity = this.getById(id);
        if (sysLoginLogEntity != null) {
            return BeanKit.convertBean(SysLoginLogDto.class, sysLoginLogEntity);
        }
        return null;
    }

    @Override
    public void saveLoginSysLog(HttpServletRequest request, String operation, String method) {
        try {
            LoginInfoVo loginInfoVo = (LoginInfoVo) request.getAttribute(SecurityConstants.DEFAULT_LOGIN_INFO);
            if (loginInfoVo != null) {
                String params = loginInfoVo.toString();
                String ip = IPUtils.getIPAddress(request);
                SysLoginLogEntity sysLoginLogEntity = new SysLoginLogEntity();
                sysLoginLogEntity.setOperation(operation);
                sysLoginLogEntity.setMethod(method);
                sysLoginLogEntity.setParams(params);
                sysLoginLogEntity.setIp(ip);
                sysLoginLogEntity.setUsername(loginInfoVo.getUsername());
                baseMapper.insert(sysLoginLogEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}