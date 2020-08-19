package com.qian.gulimall.product.api.criteria;

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
public class AttrGroupCriteria implements Serializable {


    /**
     * 分组id
     */
    private Long attrGroupId;

    /**
     * 组名
     */
    private String attrGroupName;
    /**
     * 所属分类id
     */
    private Long catelogId;

    /**
     * 属性id
     */
    private Long attrId;
    /**
     * 属性名
     */
    private String attrName;

}
