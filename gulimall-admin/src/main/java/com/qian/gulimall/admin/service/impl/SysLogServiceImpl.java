package com.qian.gulimall.admin.service.impl;

import com.qian.gulimall.common.entity.vo.LoginInfoVo;
import com.qian.gulimall.common.security.SecurityConstants;
import com.qian.gulimall.common.utils.IPUtils;
import org.springframework.stereotype.Service;

import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qian.gulimall.common.utils.PageUtils;
import com.qian.gulimall.common.utils.Query;

import com.qian.gulimall.admin.dao.SysLogDao;
import com.qian.gulimall.admin.entity.SysLogEntity;
import com.qian.gulimall.admin.service.SysLogService;

import javax.servlet.http.HttpServletRequest;


@Service("sysLogService")
public class SysLogServiceImpl extends ServiceImpl<SysLogDao, SysLogEntity> implements SysLogService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysLogEntity> page = this.page(
                new Query<SysLogEntity>().getPage(params),
                new QueryWrapper<SysLogEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveLoginSysLog(HttpServletRequest request, String operation, String method) {
        LoginInfoVo loginInfoVo = (LoginInfoVo) request.getAttribute(SecurityConstants.DEFAULT_LOGIN_INFO);

        String params = loginInfoVo.toString();
//        String username = request.getParameter("username");
        String ip = IPUtils.getIPAddress(request);
        SysLogEntity sysLogEntity = new SysLogEntity();
//        sysLogEntity.setUsername(username);
        sysLogEntity.setOperation(operation);
        sysLogEntity.setMethod(method);
        sysLogEntity.setParams(params);
        sysLogEntity.setTime(0L);
        sysLogEntity.setIp(ip);

        if (loginInfoVo != null) {
            sysLogEntity.setUsername(loginInfoVo.getUsername());
        }
//        sysLogEntity.setCreateDate(new Date());
        baseMapper.insert(sysLogEntity);
    }

}
