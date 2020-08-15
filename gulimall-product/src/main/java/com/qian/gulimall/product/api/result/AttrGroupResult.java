package com.qian.gulimall.product.api.result;

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
public class AttrGroupResult implements Serializable {

    private static final long serialVersionUID = 235852149680502763L;
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

    /**
     * 完整路径
     */
    private Long[] catelogPath;

}
