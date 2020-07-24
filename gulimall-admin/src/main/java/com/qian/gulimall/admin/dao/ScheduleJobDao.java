package com.qian.gulimall.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qian.gulimall.admin.entity.ScheduleJobEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * ScheduleJobDao is 定时任务
 *
 * @author QIAN
 * Date 2020/07/18
 * Time 09:14
 */
@Mapper
public interface ScheduleJobDao extends BaseMapper<ScheduleJobEntity> {

    /**
     * 批量更新状态
     */
    int updateBatch(Map<String, Object> map);
}
