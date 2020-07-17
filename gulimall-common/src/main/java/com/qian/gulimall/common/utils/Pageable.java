package com.qian.gulimall.common.utils;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * Pageable is
 *
 * @author QIAN
 * Date 2020/07/16
 * Time 14:53
 */
@Data
public class Pageable implements Serializable {

    private static final long serialVersionUID = -7428917360046148374L;

    private Long pageNumber;

    private Long pageSize;

    private String sidx;

    private String order;
}
