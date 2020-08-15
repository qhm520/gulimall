package com.qian.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qian.gulimall.product.dao.BrandDao;
import com.qian.gulimall.product.dao.CategoryBrandRelationDao;
import com.qian.gulimall.product.dao.CategoryDao;
import com.qian.gulimall.product.entity.BrandEntity;
import com.qian.gulimall.product.entity.CategoryBrandRelationEntity;
import com.qian.gulimall.product.entity.CategoryEntity;
import com.qian.gulimall.product.service.CategoryBrandRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("categoryBrandRelationService")
public class CategoryBrandRelationServiceImpl extends ServiceImpl<CategoryBrandRelationDao, CategoryBrandRelationEntity> implements CategoryBrandRelationService {

    @Autowired
    private BrandDao brandDao;

    @Autowired
    private CategoryDao categoryDao;

    @Override
    public Integer saveDetail(CategoryBrandRelationEntity categoryBrandRelation) {
        CategoryBrandRelationEntity result = this.getOne(new QueryWrapper<CategoryBrandRelationEntity>()
                .eq("brand_id", categoryBrandRelation.getBrandId())
                .eq("catelog_id", categoryBrandRelation.getCatelogId())
        );
        if (result == null) {
            Long brandId = categoryBrandRelation.getBrandId();
            Long catelogId = categoryBrandRelation.getCatelogId();
            //1、查询详细名字
            BrandEntity brandEntity = brandDao.selectById(brandId);
            CategoryEntity categoryEntity = categoryDao.selectById(catelogId);

            categoryBrandRelation.setBrandName(brandEntity.getName());
            categoryBrandRelation.setCatelogName(categoryEntity.getName());
            this.save(categoryBrandRelation);
            return 1;
        }

        return 0;
    }

    @Override
    public void updateBrand(Long brandId, String name) {
        CategoryBrandRelationEntity relationEntity = new CategoryBrandRelationEntity();
        relationEntity.setBrandId(brandId);
        relationEntity.setBrandName(name);
        this.update(relationEntity,new UpdateWrapper<CategoryBrandRelationEntity>().eq("brand_id",brandId));
    }
}