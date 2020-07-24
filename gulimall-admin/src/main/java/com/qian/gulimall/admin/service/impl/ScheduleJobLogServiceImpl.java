package com.qian.gulimall.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qian.gulimall.admin.api.criteria.ScheduleJobLogCriteria;
import com.qian.gulimall.admin.api.dto.ScheduleJobLogDto;
import com.qian.gulimall.admin.api.result.ScheduleJobLogResult;
import com.qian.gulimall.admin.dao.ScheduleJobLogDao;
import com.qian.gulimall.admin.entity.ScheduleJobLogEntity;
import com.qian.gulimall.admin.service.ScheduleJobLogService;
import com.qian.gulimall.common.utils.BeanKit;
import com.qian.gulimall.common.utils.PageUtils;
import com.qian.gulimall.common.utils.Pageable;
import com.qian.gulimall.common.utils.Query;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

@Service("scheduleJobLogService")
public class ScheduleJobLogServiceImpl extends ServiceImpl<ScheduleJobLogDao, ScheduleJobLogEntity> implements ScheduleJobLogService {


    @Override
    public PageUtils queryPage(Pageable pageable, ScheduleJobLogCriteria scheduleJobLogCriteria) {
        IPage<ScheduleJobLogEntity> page = this.page(
                // 分页
                new Query<ScheduleJobLogEntity>().getPage(pageable),
                // 查询条件
                new QueryWrapper<ScheduleJobLogEntity>()
                        // username
                        .like(StringUtils.isNotBlank(scheduleJobLogCriteria.getBeanName()), "bean_name", scheduleJobLogCriteria.getBeanName())
                        // status
                        .eq(null != scheduleJobLogCriteria.getStatus(), "status", scheduleJobLogCriteria.getStatus())

                        .between((scheduleJobLogCriteria.getCreateTimeStart() != null && scheduleJobLogCriteria.getCreateTimeEnd() != null), "create_time", scheduleJobLogCriteria.getCreateTimeStart(), scheduleJobLogCriteria.getCreateTimeEnd())
                        // create_time
                        .orderBy(true, false, "create_time")
        );
        // 封装返回数据到result对象中
        return new PageUtils(page, BeanKit.convertList(ScheduleJobLogResult.class, page.getRecords()));
    }

    @Override
    public ScheduleJobLogDto getScheduleJobLogById(Long logId) {
        ScheduleJobLogEntity scheduleJobLogEntity = this.getById(logId);
        if (scheduleJobLogEntity != null) {
            return BeanKit.convertBean(ScheduleJobLogDto.class, scheduleJobLogEntity);
        }
        return null;
    }
}