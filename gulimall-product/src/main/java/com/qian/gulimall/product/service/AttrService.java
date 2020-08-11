package com.qian.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qian.gulimall.common.utils.PageUtils;
import com.qian.gulimall.common.utils.Pageable;
import com.qian.gulimall.product.api.criteria.AttrCriteria;
import com.qian.gulimall.product.entity.AttrEntity;

/**
 * 商品属性
 *
 * @author QIAN
 * @email 794308528@qq.com
 * @date 2020-07-25 16:06:04
 */
public interface AttrService extends IService<AttrEntity> {

    PageUtils queryPage(Pageable pageable, AttrCriteria attrCriteria);
}

