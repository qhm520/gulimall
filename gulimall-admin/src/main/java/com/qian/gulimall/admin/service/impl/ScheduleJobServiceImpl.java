package com.qian.gulimall.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qian.gulimall.admin.api.criteria.ScheduleJobCriteria;
import com.qian.gulimall.admin.api.dto.ScheduleJobDto;
import com.qian.gulimall.admin.api.result.ScheduleJobResult;
import com.qian.gulimall.admin.dao.ScheduleJobDao;
import com.qian.gulimall.admin.entity.ScheduleJobEntity;
import com.qian.gulimall.admin.service.ScheduleJobService;
import com.qian.gulimall.admin.util.ScheduleJob;
import com.qian.gulimall.admin.util.ScheduleUtils;
import com.qian.gulimall.common.utils.BeanKit;
import com.qian.gulimall.common.utils.Constant;
import com.qian.gulimall.common.utils.PageUtils;
import com.qian.gulimall.common.utils.Pageable;
import com.qian.gulimall.common.utils.Query;
import org.apache.commons.lang.StringUtils;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * ScheduleJobServiceImpl is
 *
 * @author QIAN
 * Date 2020/07/18
 * Time 09:14
 */
@Service("scheduleJobService")
public class ScheduleJobServiceImpl extends ServiceImpl<ScheduleJobDao, ScheduleJobEntity> implements ScheduleJobService {


    @Autowired
    private Scheduler scheduler;

    /**
     * 项目启动时，初始化定时器
     */
    @PostConstruct
    public void init() {
        List<ScheduleJobEntity> scheduleJobList = this.list();
        for(ScheduleJobEntity scheduleJob : scheduleJobList){
            CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, scheduleJob.getJobId());
            //如果不存在，则创建
            if(cronTrigger == null) {
                ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
            }else {
                ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
            }
        }
    }


    @Override
    public PageUtils queryPage(Pageable pageable, ScheduleJobCriteria scheduleJobCriteria) {
        IPage<ScheduleJobEntity> page = this.page(
                // 分页
                new Query<ScheduleJobEntity>().getPage(pageable),
                // 查询条件
                new QueryWrapper<ScheduleJobEntity>()
                        // username
                        .like(StringUtils.isNotBlank(scheduleJobCriteria.getBeanName()), "bean_name", scheduleJobCriteria.getBeanName())
                        // status
                        .eq(null != scheduleJobCriteria.getStatus(), "status", scheduleJobCriteria.getStatus())

                        .between((scheduleJobCriteria.getCreateTimeStart() != null && scheduleJobCriteria.getCreateTimeEnd() != null), "create_time", scheduleJobCriteria.getCreateTimeStart(), scheduleJobCriteria.getCreateTimeEnd())
                        // create_time
                        .orderBy(true, false, "create_time")
        );
        // 封装返回数据到result对象中
        return new PageUtils(page, BeanKit.convertList(ScheduleJobResult.class, page.getRecords()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveJob(ScheduleJobDto scheduleJobDto) {
        ScheduleJobEntity scheduleJobEntity = BeanKit.convertBean(ScheduleJobEntity.class, scheduleJobDto);
        scheduleJobEntity.setCreateTime(new Date());
        scheduleJobEntity.setStatus(Constant.ScheduleStatus.NORMAL.getValue());
        this.save(scheduleJobEntity);

        ScheduleUtils.createScheduleJob(scheduler, scheduleJobEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ScheduleJobEntity scheduleJob) {
        ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);

        this.updateById(scheduleJob);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(Long[] jobIds) {
        for(Long jobId : jobIds){
            ScheduleUtils.deleteScheduleJob(scheduler, jobId);
        }

        //删除数据
        this.removeByIds(Arrays.asList(jobIds));
    }

    @Override
    public int updateBatch(Long[] jobIds, int status){
        Map<String, Object> map = new HashMap<>(2);
        map.put("list", Arrays.asList(jobIds));
        map.put("status", status);
        return baseMapper.updateBatch(map);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void run(Long[] jobIds) {
        for(Long jobId : jobIds){
            ScheduleUtils.run(scheduler, this.getById(jobId));
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void pause(Long[] jobIds) {
        for(Long jobId : jobIds){
            ScheduleUtils.pauseJob(scheduler, jobId);
        }

        updateBatch(jobIds, Constant.ScheduleStatus.PAUSE.getValue());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resume(Long[] jobIds) {
        for(Long jobId : jobIds){
            ScheduleUtils.resumeJob(scheduler, jobId);
        }

        updateBatch(jobIds, Constant.ScheduleStatus.NORMAL.getValue());
    }

    @Override
    public ScheduleJobDto getScheduleJobByJobId(Long jobId) {
        ScheduleJobEntity scheduleJobEntity = this.getById(jobId);

        if (scheduleJobEntity != null) {
            return BeanKit.convertBean(ScheduleJobDto.class, scheduleJobEntity);
        }
        return null;
    }
}
