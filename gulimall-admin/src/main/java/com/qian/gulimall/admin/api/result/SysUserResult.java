package com.qian.gulimall.admin.api.result;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * SysUserResult is 用户返回结果
 *
 * @author QIAN
 * Date 2020/07/16
 * Time 14:48
 */
@Data
public class SysUserResult implements Serializable {

    private static final long serialVersionUID = 6620273851746886074L;

    /**
     *
     */
    private Long userId;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 盐
     */
    private String salt;
    /**
     * 邮箱
     */
    private String email;
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
     * 创建者ID
     */
    private Long createUserId;
    /**
     * 创建时间
     */
    private Date createTime;
}
