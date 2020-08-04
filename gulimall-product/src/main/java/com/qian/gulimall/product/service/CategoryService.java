package com.qian.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qian.gulimall.product.api.dto.CategoryDto;
import com.qian.gulimall.product.entity.CategoryEntity;

import java.util.List;

/**
 * 商品三级分类
 *
 * @author QIAN
 * @email 794308528@qq.com
 * @date 2020-07-25 16:06:03
 */
public interface CategoryService extends IService<CategoryEntity> {

    List<CategoryDto> listWithTree();
}

