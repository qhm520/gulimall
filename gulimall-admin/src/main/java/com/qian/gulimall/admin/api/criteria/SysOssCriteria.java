package com.qian.gulimall.admin.api.criteria;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class SysOssCriteria implements Serializable {

	private static final long serialVersionUID = 3214391485433431969L;
	/**
	 *
	 */
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
	private Long uploadUserId;

    /**
     * 上传者IP地址
	 */
	private String ip;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 状态   0：隐藏   1：显示
	 */
	private Integer status;

	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createDateStart;

	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createDateEnd;

}
