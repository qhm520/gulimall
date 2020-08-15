package com.qian.gulimall.product.api.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 属性分组
 *
 * @author QIAN
 * @email 794308528@qq.com
 * @date 2020-07-25 16:06:04
 */
@Data
public class AttrGroupDto implements Serializable {

    private static final long serialVersionUID = -2040886474185672133L;
    /**
     * 分组id
     */
    private Long attrGroupId;
    /**
     * 组名
     */
    private String attrGroupName;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 描述
     */
    private String descript;
    /**
     * 组图标
     */
    private String icon;
    /**
     * 所属分类id
     */
    private Long catelogId;

}
