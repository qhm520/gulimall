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
public class AttrGroupVo implements Serializable {


    private static final long serialVersionUID = 5444447838443584055L;
    /**
     * 分组id
     */
    private Long attrGroupId;

    /**
     * 属性id
     */
    private Long attrId;

}
