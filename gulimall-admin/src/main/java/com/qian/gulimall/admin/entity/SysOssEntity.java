package com.qian.gulimall.admin.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 文件上传
 *
 * @author QIAN
 * @email 794308528@qq.com
 * @date 2020-04-19 08:57:20
 */
@Data
@TableName("sys_oss")
public class SysOssEntity implements Serializable {

	private static final long serialVersionUID = 6056087424450298008L;
	/**
	 *
	 */
	@TableId
	private Long id;

	/**
	 * URL地址
	 */
	private String url;

	/**
	 * 原文件名称
	 */
	private String originalFilename;

	/**
	 * 上传者ID
	 */
	private String uploadUser;

	/**
	 * 上传者IP地址
	 */
	private String ip;

	/**
	 * 状态   0：隐藏   1：显示
	 */
	private Integer status;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 创建时间
	 */
	@TableField(value = "create_date", fill = FieldFill.INSERT)
	private Date createDate;

}
