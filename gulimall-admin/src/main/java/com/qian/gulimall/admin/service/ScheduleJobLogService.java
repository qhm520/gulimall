package com.qian.gulimall.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qian.gulimall.admin.api.criteria.ScheduleJobLogCriteria;
import com.qian.gulimall.admin.api.dto.ScheduleJobLogDto;
import com.qian.gulimall.admin.entity.ScheduleJobLogEntity;
import com.qian.gulimall.common.utils.PageUtils;
import com.qian.gulimall.common.utils.Pageable;

/**
 * Created by IntelliJ IDEA.
 * ScheduleJobLogService is
 *
 * @author QIAN
 * Date 2020/07/22
 * Time 19:05
 */
public interface ScheduleJobLogService extends IService<ScheduleJobLogEntity> {

    /**
     * 分页查询
     *
     * @param pageable
     * @param scheduleJobCriteria
     * @return
     */
    PageUtils queryPage(Pageable pageable, ScheduleJobLogCriteria scheduleJobLogCriteria);

    ScheduleJobLogDto getScheduleJobLogById(Long logId);
}
