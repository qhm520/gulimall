package com.qian.gulimall.product.dao;

import com.qian.gulimall.product.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品三级分类
 * 
 * @author QIAN
 * @email 794308528@qq.com
 * @date 2020-07-25 16:06:03
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
	
}
