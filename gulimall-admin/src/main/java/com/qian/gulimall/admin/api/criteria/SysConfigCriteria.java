package com.qian.gulimall.admin.api.criteria;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * SysConfigCriteria is
 *
 * @author QIAN
 * Date 2020/07/16
 * Time 15:34
 */
@Data
public class SysConfigCriteria implements Serializable {

    private static final long serialVersionUID = -3577992098559048001L;

    /**
     * key
     */
    private String paramKey;
    /**
     * 状态   0：隐藏   1：显示
     */
    private Integer status;
}
