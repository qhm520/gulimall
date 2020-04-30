package com.qian.gulimall.admin.controller;

import com.qian.gulimall.common.annotation.SysLog;
import com.qian.gulimall.admin.entity.SysLogEntity;
import com.qian.gulimall.admin.service.SysLogService;
import com.qian.gulimall.common.entity.vo.SysLogVo;
import com.qian.gulimall.common.service.RedisTemplateService;
import com.qian.gulimall.common.utils.PageUtils;
import com.qian.gulimall.common.utils.R;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * 系统日志
 *
 * @author QIAN
 * @email 794308528@qq.com
 * @date 2020-04-19 08:57:20
 */
@RestController
@RequestMapping("sys/log")
public class SysLogController {

    @Autowired
    private SysLogService sysLogService;

    @Autowired
    private RedisTemplateService redisTemplateService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @PreAuthorize("hasPermission('', 'ROLE_ADMIN')")
    //@RequiresPermissions("admin:syslog:list")
    public R list(@RequestParam Map <String, Object> params) {
        PageUtils page = sysLogService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @SysLog("查询日志信息")
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("admin:syslog:info")
    public R info(@PathVariable("id") Long id) {
        SysLogEntity sysLog = sysLogService.getById(id);
        redisTemplateService.lSet("syslog"+String.valueOf(id), sysLog);
        List <Object> objects = redisTemplateService.lGet("syslog" + String.valueOf(id), 0, -1);

        return R.ok().put("sysLog", sysLog);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    //@RequiresPermissions("admin:syslog:save")
    public R save(@RequestBody SysLogVo sysLog) {

        SysLogEntity sysLogEntity = new SysLogEntity();
        BeanUtils.copyProperties(sysLog, sysLogEntity);
        sysLogService.save(sysLogEntity);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("admin:syslog:update")
    public R update(@RequestBody SysLogEntity sysLog) {
        sysLogService.updateById(sysLog);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("admin:syslog:delete")
    public R delete(@RequestBody Long[] ids) {
        sysLogService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
