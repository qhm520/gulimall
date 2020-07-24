package com.qian.gulimall.admin.api.result;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * ScheduleJobResult is 定时任务结果
 *
 * @author QIAN
 * Date 2020/07/18
 * Time 09:16
 */
@Data
public class ScheduleJobResult implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 任务id
	 */
	private Long jobId;

	/**
	 * spring bean名称
	 */
	private String beanName;
	
	/**
	 * 参数
	 */
	private String params;
	
	/**
	 * cron表达式
	 */
	private String cronExpression;

	/**
	 * 任务状态
	 */
	private Integer status;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 创建时间
	 */
	private Date createTime;
}
