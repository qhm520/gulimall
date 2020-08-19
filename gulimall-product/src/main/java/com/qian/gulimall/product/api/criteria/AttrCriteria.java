package com.qian.gulimall.product.api.criteria;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 商品属性
 * 
 * @author QIAN
 * @email 794308528@qq.com
 * @date 2020-07-25 16:06:04
 */
@Data
public class AttrCriteria implements Serializable {

	private static final long serialVersionUID = 1194443657237587772L;
	/**
	 * 属性id
	 */
	private Long attrId;
	/**
	 * 属性名
	 */
	private String attrName;

	/**
	 * 属性类型[0-销售属性，1-基本属性]
	 */
	private Integer attrType;

	/**
	 * 所属分类
	 */
	private Long catelogId;

	/**
	 * 分组id
	 */
	private Long attrGroupId;


}
