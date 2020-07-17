package com.qian.gulimall.admin.api.result;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * SysRoleResult is
 *
 * @author QIAN
 * Date 2020/07/16
 * Time 20:01
 */
@Data
public class SysRoleResult implements Serializable {

    private static final long serialVersionUID = 6100243515328082123L;
    /**
     *
     */
    private Long roleId;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 备注
     */
    private String remark;
    /**
     * 创建者ID
     */
    private Long createUserId;
    /**
     * 创建时间
     */
    private Date createTime;
}
