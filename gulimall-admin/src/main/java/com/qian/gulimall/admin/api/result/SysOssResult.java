package com.qian.gulimall.admin.api.result;

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
public class SysOssResult implements Serializable {

	private static final long serialVersionUID = 8087613497146124073L;
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
	 * 上传者
	 */
	private String uploadUser;

    /**
     * 上传者IP地址
	 */
	private String ip;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 创建时间
	 */
	private Date createDate;

}
