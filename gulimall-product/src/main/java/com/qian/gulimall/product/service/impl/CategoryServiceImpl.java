package com.qian.gulimall.product.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qian.gulimall.common.utils.BeanKit;
import com.qian.gulimall.product.api.dto.CategoryDto;
import com.qian.gulimall.product.dao.CategoryDao;
import com.qian.gulimall.product.entity.CategoryEntity;
import com.qian.gulimall.product.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {


    @Override
    public List<CategoryDto> listWithTree() {
        //1、查出所有分类
        List<CategoryEntity> categoryEntityList = baseMapper.selectList(null);
        if (!CollectionUtils.isEmpty(categoryEntityList)) {
            //2、组装成父子的树形结构

            //2.1）、找到所有的一级分类
            List<CategoryDto> categoryDtoList = BeanKit.convertList(CategoryDto.class, categoryEntityList);
            List<CategoryDto> level1Menus = categoryDtoList.stream().filter(categoryEntity ->
                    categoryEntity.getParentCid() == 0
            ).map((menu) -> {
                menu.setChildren(getChildrens(menu, categoryDtoList));
                return menu;
            }).sorted((menu1, menu2) -> {
                return (menu1.getSort() == null ? 0 : menu1.getSort()) - (menu2.getSort() == null ? 0 : menu2.getSort());
            }).collect(Collectors.toList());

            return level1Menus;
        }
        return null;
    }

    //递归查找所有菜单的子菜单
    private List<CategoryDto> getChildrens(CategoryDto root, List<CategoryDto> all) {

        List<CategoryDto> children = all.stream().filter(categoryEntity -> {
            return categoryEntity.getParentCid() == root.getCatId();
        }).map(categoryEntity -> {
            //1、找到子菜单
            categoryEntity.setChildren(getChildrens(categoryEntity, all));
            return categoryEntity;
        }).sorted((menu1, menu2) -> {
            //2、菜单的排序
            return (menu1.getSort() == null ? 0 : menu1.getSort()) - (menu2.getSort() == null ? 0 : menu2.getSort());
        }).collect(Collectors.toList());

        return children;
    }
}