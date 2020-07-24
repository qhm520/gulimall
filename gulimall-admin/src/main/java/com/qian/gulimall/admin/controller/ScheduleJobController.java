package com.qian.gulimall.admin.controller;

import com.qian.gulimall.admin.api.criteria.ScheduleJobCriteria;
import com.qian.gulimall.admin.api.dto.ScheduleJobDto;
import com.qian.gulimall.admin.entity.ScheduleJobEntity;
import com.qian.gulimall.admin.service.ScheduleJobService;
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

/**
 * Created by IntelliJ IDEA.
 * ScheduleJobController is  定时任务
 *
 * @author QIAN
 * Date 2020/07/18
 * Time 09:12
 */
@RestController
@RequestMapping("/sys/schedule")
public class ScheduleJobController {

    @Autowired
    private ScheduleJobService scheduleJobService;

    /**
     * 定时任务列表
     */
    @RequestMapping("/list")
    @PreAuthorize("hasPermission('', 'sys:schedule:list')")
    public R list(Pageable pageable, @ModelAttribute ScheduleJobCriteria scheduleJobCriteria){
        PageUtils page = scheduleJobService.queryPage(pageable, scheduleJobCriteria);

        return R.ok().put("page", page);
    }

    /**
     * 定时任务信息
     */
    @RequestMapping("/info/{jobId}")
    @PreAuthorize("hasPermission('', 'sys:schedule:info')")
    public R info(@PathVariable("jobId") Long jobId){
        ScheduleJobDto schedule = scheduleJobService.getScheduleJobByJobId(jobId);

        return R.ok().put("schedule", schedule);
    }

    /**
     * 保存定时任务
     */
    @SysLog("保存定时任务")
    @RequestMapping("/save")
    @PreAuthorize("hasPermission('', 'sys:schedule:save')")
    public R save(@RequestBody ScheduleJobDto scheduleJobDto){
//        ValidatorUtils.validateEntity(scheduleJob);

        scheduleJobService.saveJob(scheduleJobDto);

        return R.ok();
    }

    /**
     * 修改定时任务
     */
    @SysLog("修改定时任务")
    @RequestMapping("/update")
    @PreAuthorize("hasPermission('', 'sys:schedule:update')")
    public R update(@RequestBody ScheduleJobEntity scheduleJob){
//        ValidatorUtils.validateEntity(scheduleJob);

        scheduleJobService.update(scheduleJob);

        return R.ok();
    }

    /**
     * 删除定时任务
     */
    @SysLog("删除定时任务")
    @RequestMapping("/delete")
    @PreAuthorize("hasPermission('', 'sys:schedule:delete')")
    public R delete(@RequestBody Long[] jobIds){
        scheduleJobService.deleteBatch(jobIds);

        return R.ok();
    }

    /**
     * 立即执行任务
     */
    @SysLog("立即执行任务")
    @RequestMapping("/run")
    @PreAuthorize("hasPermission('', 'sys:schedule:run')")
    public R run(@RequestBody Long[] jobIds){
        scheduleJobService.run(jobIds);

        return R.ok();
    }

    /**
     * 暂停定时任务
     */
    @SysLog("暂停定时任务")
    @RequestMapping("/pause")
    @PreAuthorize("hasPermission('', 'sys:schedule:pause')")
    public R pause(@RequestBody Long[] jobIds){
        scheduleJobService.pause(jobIds);

        return R.ok();
    }

    /**
     * 恢复定时任务
     */
    @SysLog("恢复定时任务")
    @RequestMapping("/resume")
    @PreAuthorize("hasPermission('', 'sys:schedule:resume')")
    public R resume(@RequestBody Long[] jobIds){
        scheduleJobService.resume(jobIds);

        return R.ok();
    }
}
