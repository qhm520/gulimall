package com.qian.gulimall.admin.api.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 文件上传
 *
 * @author QIAN
 * @email 794308528@qq.com
 * @date 2020-04-19 08:57:20
 */
@Data
public class SysOssDto implements Serializable {

    private static final long serialVersionUID = -1587679338865543497L;
    /**
     *
     */
    private Long id;

    /**
     * URL地址
     */
    private String url;

    /**
     * 原文件名称
     */
    private String originalFilename;

    /**
     * 上传者
     */
    private String uploadUser;

    /**
     * 上传者IP地址
     */
    private String ip;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date createDate;

}
