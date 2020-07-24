package com.qian.gulimall.admin.api.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 系统登录日志
 * 
 * @author QIAN
 * @email 794308528@qq.com
 * @date 2020-07-24 15:17:44
 */
@Data
public class SysLoginLogDto implements Serializable {

	private static final long serialVersionUID = 3740738847003618833L;
	/**
	 * ID
	 */
	private Long id;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 用户操作
	 */
	private String operation;
	/**
	 * 请求方法
	 */
	private String method;
	/**
	 * 请求参数
	 */
	private String params;
	/**
	 * IP地址
	 */
	private String ip;
	/**
	 * 创建时间
	 */
	private Date createDate;

}
