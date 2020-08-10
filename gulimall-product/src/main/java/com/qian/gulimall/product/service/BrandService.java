package com.qian.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qian.gulimall.common.utils.PageUtils;
import com.qian.gulimall.common.utils.Pageable;
import com.qian.gulimall.product.api.criteria.BrandCriteria;
import com.qian.gulimall.product.entity.BrandEntity;

/**
 * 品牌
 *
 * @author QIAN
 * @email 794308528@qq.com
 * @date 2020-07-25 16:06:04
 */
public interface BrandService extends IService<BrandEntity> {

    PageUtils queryPage(Pageable pageable, BrandCriteria brandCriteria);
}

