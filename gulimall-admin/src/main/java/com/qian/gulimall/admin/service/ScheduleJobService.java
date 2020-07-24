package com.qian.gulimall.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qian.gulimall.admin.api.criteria.ScheduleJobCriteria;
import com.qian.gulimall.admin.api.dto.ScheduleJobDto;
import com.qian.gulimall.admin.entity.ScheduleJobEntity;
import com.qian.gulimall.common.utils.PageUtils;
import com.qian.gulimall.common.utils.Pageable;

/**
 * Created by IntelliJ IDEA.
 * ScheduleJobService is 定时任务
 *
 * @author QIAN
 * Date 2020/07/18
 * Time 09:13
 */
public interface ScheduleJobService  extends IService<ScheduleJobEntity> {
    /**
     * 分页查询
     * @param pageable
     * @param scheduleJobCriteria
     * @return
     */
    PageUtils queryPage(Pageable pageable, ScheduleJobCriteria scheduleJobCriteria);

    /**
     * 保存定时任务
     */
    void saveJob(ScheduleJobDto scheduleJobDto);

    /**
     * 更新定时任务
     */
    void update(ScheduleJobEntity scheduleJob);

    /**
     * 批量删除定时任务
     */
    void deleteBatch(Long[] jobIds);

    /**
     * 批量更新定时任务状态
     */
    int updateBatch(Long[] jobIds, int status);

    /**
     * 立即执行
     */
    void run(Long[] jobIds);

    /**
     * 暂停运行
     */
    void pause(Long[] jobIds);

    /**
     * 恢复运行
     */
    void resume(Long[] jobIds);

    ScheduleJobDto getScheduleJobByJobId(Long jobId);
}
