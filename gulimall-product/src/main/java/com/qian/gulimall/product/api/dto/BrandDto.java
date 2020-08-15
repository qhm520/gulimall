package com.qian.gulimall.product.api.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 品牌
 * 
 * @author QIAN
 * @email 794308528@qq.com
 * @date 2020-07-25 16:06:04
 */
@Data
public class BrandDto implements Serializable {

	private static final long serialVersionUID = 7287596222702694991L;
	/**
	 * 品牌id
	 */
	private Long brandId;
	/**
	 * 品牌名
	 */
	@NotBlank(message = "品牌名不能为空")
	private String name;
	/**
	 * 品牌logo地址
	 */
	@NotBlank(message = "品牌logo地址不能为空")
	private String logo;
	/**
	 * 介绍
	 */
	@NotBlank(message = "介绍logo地址不能为空")
	private String descript;
	/**
	 * 显示状态[0-不显示；1-显示]
	 */
	@NotNull(message = "显示状态不能为空")
	private Integer showStatus;
	/**
	 * 检索首字母
	 */
	@NotBlank(message = "检索首字母不能为空")
	private String firstLetter;
	/**
	 * 排序
	 */
	@NotNull(message = "排序不能为空")
	private Integer sort;

}
