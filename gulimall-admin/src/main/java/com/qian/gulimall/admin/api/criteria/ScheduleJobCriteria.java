package com.qian.gulimall.admin.api.criteria;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * ScheduleJobCriteria is 定时任务查询Bean
 *
 * @author QIAN
 * Date 2020/07/18
 * Time 09:16
 */
@Data
public class ScheduleJobCriteria implements Serializable {

    private static final long serialVersionUID = -8689679510146881500L;

    /**
     * spring bean名称
     */
    private String beanName;

    /**
     * 任务状态
     */
    private Integer status;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTimeStart;


    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTimeEnd;


}
