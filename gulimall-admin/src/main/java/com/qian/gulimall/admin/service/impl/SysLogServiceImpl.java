package com.qian.gulimall.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qian.gulimall.admin.api.criteria.SysLogCriteria;
import com.qian.gulimall.admin.api.result.SysLogResult;
import com.qian.gulimall.admin.dao.SysLogDao;
import com.qian.gulimall.admin.entity.SysLogEntity;
import com.qian.gulimall.admin.entity.SysUserEntity;
import com.qian.gulimall.admin.service.SysLogService;
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


@Service("sysLogService")
public class SysLogServiceImpl extends ServiceImpl<SysLogDao, SysLogEntity> implements SysLogService {

    @Override
    public PageUtils queryPage(Pageable pageable, SysLogCriteria sysLogCriteria) {
        IPage<SysLogEntity> page = this.page(
                new Query<SysLogEntity>().getPage(pageable),
                // 查询条件
                new QueryWrapper<SysLogEntity>()
                // username
                .like(StringUtils.isNotBlank(sysLogCriteria.getUsername()),"username", sysLogCriteria.getUsername())
                // operation
                .like(StringUtils.isNotBlank(sysLogCriteria.getOperation()),"operation", sysLogCriteria.getOperation())
                // ip
                .like(StringUtils.isNotBlank(sysLogCriteria.getIp()), "ip", sysLogCriteria.getIp())
                // create_date
                .between((sysLogCriteria.getCreateDateStart() != null && sysLogCriteria.getCreateDateEnd() != null), "create_date", sysLogCriteria.getCreateDateStart(), sysLogCriteria.getCreateDateEnd())
                // create_time
                .orderBy(true, false, "create_date")
        );
        return new PageUtils(page, BeanKit.convertList(SysLogResult.class, page.getRecords()));
    }

   /* @Override
    public void saveLoginSysLog(HttpServletRequest request, String operation, String method) {
        try {
            LoginInfoVo loginInfoVo = (LoginInfoVo) request.getAttribute(SecurityConstants.DEFAULT_LOGIN_INFO);
            if (loginInfoVo != null) {
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
                sysLogEntity.setUsername(loginInfoVo.getUsername());
                baseMapper.insert(sysLogEntity);
            }
//        sysLogEntity.setCreateDate(new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

}
