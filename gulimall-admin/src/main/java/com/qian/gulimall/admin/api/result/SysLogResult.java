package com.qian.gulimall.admin.api.result;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * SysLogResult is
 *
 * @author QIAN
 * Date 2020/07/16
 * Time 16:44
 */
@Data
public class SysLogResult implements Serializable {


    private static final long serialVersionUID = 1771748945417080224L;

    /**
     *
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
     * 执行时长(毫秒)
     */
    private Long time;
    /**
     * IP地址
     */
    private String ip;
    /**
     * 创建时间
     */
    private Date createDate;
}
