package com.qian.gulimall.admin.util;

import com.qian.gulimall.admin.api.dto.ScheduleJobLogDto;
import com.qian.gulimall.admin.entity.ScheduleJobEntity;
import com.qian.gulimall.admin.entity.ScheduleJobLogEntity;
import com.qian.gulimall.admin.service.ScheduleJobLogService;
import com.qian.gulimall.admin.task.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * ScheduleJob is 定时任务
 *
 * @author QIAN
 * Date 2020/07/22
 * Time 19:02
 */
@Slf4j
@Component
public class ScheduleJob extends QuartzJobBean {

    /**
     * 获取spring bean
     */
    @Autowired
    private ScheduleJobLogService scheduleJobLogService;

    /**
     * 所有TaskService的实现Bean
     */
    @Autowired
    private Map<String, TaskService> taskServiceMap;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        ScheduleJobEntity scheduleJob = (ScheduleJobEntity) context.getMergedJobDataMap()
                .get(ScheduleJobEntity.JOB_PARAM_KEY);

        //数据库保存执行记录
//        ScheduleJobLogEntity scheduleJobLogEntity = ScheduleJobLogEntity.builder()
//                .beanName(scheduleJob.getBeanName())
//                .jobId(scheduleJob.getJobId())
//                .params(scheduleJob.getParams())
//                .build();

        ScheduleJobLogEntity scheduleJobLogEntity = new ScheduleJobLogEntity();
        scheduleJobLogEntity.setJobId(scheduleJob.getJobId());
        scheduleJobLogEntity.setBeanName(scheduleJob.getBeanName());
        scheduleJobLogEntity.setParams(scheduleJob.getParams());
//        scheduleJobLogEntity.setCreateTime(new Date());

        //任务开始时间
        long startTime = System.currentTimeMillis();

        try {
            //执行任务
            log.debug("任务准备执行，任务ID：" + scheduleJob.getJobId());

            Object target = taskServiceMap.get(scheduleJob.getBeanName());
            Method method = target.getClass().getDeclaredMethod("run", String.class);
            method.invoke(target, scheduleJob.getParams());

            //任务执行总时长
            long times = System.currentTimeMillis() - startTime;
            scheduleJobLogEntity.setTimes((int) times);
            //任务状态    0：成功    1：失败
            scheduleJobLogEntity.setStatus(0);

            log.debug("任务执行完毕，任务ID：" + scheduleJob.getJobId() + "  总共耗时：" + times + "毫秒");
        } catch (Exception e) {
            log.error("任务执行失败，任务ID：" + scheduleJob.getJobId(), e);

            //任务执行总时长
            long times = System.currentTimeMillis() - startTime;
            scheduleJobLogEntity.setTimes((int) times);

            //任务状态    0：成功    1：失败
            scheduleJobLogEntity.setStatus(1);
            scheduleJobLogEntity.setError(StringUtils.substring(e.toString(), 0, 2000));
        } finally {
            scheduleJobLogService.save(scheduleJobLogEntity);
        }
    }
}
