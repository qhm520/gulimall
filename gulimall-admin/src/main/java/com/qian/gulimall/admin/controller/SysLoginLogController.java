package com.qian.gulimall.admin.controller;

import com.qian.gulimall.admin.api.criteria.SysLoginLogCriteria;
import com.qian.gulimall.admin.api.dto.SysLoginLogDto;
import com.qian.gulimall.admin.service.SysLoginLogService;
import com.qian.gulimall.common.utils.PageUtils;
import com.qian.gulimall.common.utils.Pageable;
import com.qian.gulimall.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;



/**
 * 系统登录日志
 *
 * @author QIAN
 * @email 794308528@qq.com
 * @date 2020-07-24 15:17:44
 */
@RestController
@RequestMapping("sys/login")
public class SysLoginLogController {
    @Autowired
    private SysLoginLogService sysLoginLogService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("admin:sysloginlog:list")
    public R list(Pageable pageable, @ModelAttribute SysLoginLogCriteria sysLoginLogCriteria){
        PageUtils page = sysLoginLogService.queryPage(pageable, sysLoginLogCriteria);

        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("admin:sysloginlog:info")
    public R info(@PathVariable("id") Long id){
        SysLoginLogDto sysLoginLog = sysLoginLogService.getSysLoginLogById(id);

        return R.ok().put("info", sysLoginLog);
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("admin:sysloginlog:delete")
    public R delete(@RequestBody Long[] ids){
		sysLoginLogService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
