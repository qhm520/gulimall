package com.qian.gulimall.admin.api.criteria;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * SysUserCriteria is 用户查询参数
 *
 * @author QIAN
 * Date 2020/07/16
 * Time 14:44
 */
@Data
public class SysUserCriteria implements Serializable {

    private static final long serialVersionUID = -7841525353992596394L;

    /**
     * 用户名
     */
    private String username;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 状态  0：禁用   1：正常
     */
//	@TableLogic(value = "1", delval = "0")
    private Integer status;
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
