package com.qian.gulimall.admin.controller;

import com.qian.gulimall.admin.api.criteria.ScheduleJobLogCriteria;
import com.qian.gulimall.admin.api.dto.ScheduleJobLogDto;
import com.qian.gulimall.admin.service.ScheduleJobLogService;
import com.qian.gulimall.common.annotation.SysLog;
import com.qian.gulimall.common.utils.PageUtils;
import com.qian.gulimall.common.utils.Pageable;
import com.qian.gulimall.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * Created by IntelliJ IDEA.
 * ScheduleJobLogController is 定时任务日志
 *
 * @author QIAN
 * Date 2020/07/22
 * Time 19:20
 */
@RestController
@RequestMapping("/sys/scheduleLog")
public class ScheduleJobLogController {
    @Autowired
    private ScheduleJobLogService scheduleJobLogService;

    /**
     * 定时任务日志列表
     */
    @RequestMapping("/list")
    @PreAuthorize("hasPermission('', 'sys:schedule:log')")
    public R list(Pageable pageable, @ModelAttribute ScheduleJobLogCriteria scheduleJobLogCriteria) {
        PageUtils page = scheduleJobLogService.queryPage(pageable, scheduleJobLogCriteria);

        return R.ok().put("page", page);
    }

    /**
     * 定时任务日志信息
     */
    @RequestMapping("/info/{logId}")
    public R info(@PathVariable("logId") Long logId) {
        ScheduleJobLogDto log = scheduleJobLogService.getScheduleJobLogById(logId);
        return R.ok().put("log", log);
    }

    /**
     * 删除定时任务
     */
    @SysLog("删除定时日志")
    @RequestMapping("/delete")
//    @PreAuthorize("hasPermission('', 'sys:schedule:delete')")
    public R delete(@RequestBody Long[] logIds){
        scheduleJobLogService.removeByIds(Arrays.asList(logIds));

        return R.ok();
    }
}
