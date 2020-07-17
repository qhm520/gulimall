package com.qian.gulimall.admin.api.criteria;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * SysRoleCriteria is
 *
 * @author QIAN
 * Date 2020/07/16
 * Time 16:51
 */
@Data
public class SysRoleCriteria implements Serializable {

    private static final long serialVersionUID = -328726808634613399L;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTimeStart;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTimeEnd;
}
