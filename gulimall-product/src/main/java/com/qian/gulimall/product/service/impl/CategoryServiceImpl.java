package com.qian.gulimall.product.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qian.gulimall.common.utils.BeanKit;
import com.qian.gulimall.product.api.dto.CategoryDto;
import com.qian.gulimall.product.api.result.CategoryResult;
import com.qian.gulimall.product.dao.CategoryDao;
import com.qian.gulimall.product.entity.CategoryEntity;
import com.qian.gulimall.product.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {


    /**
     * 所有菜单组装成父子的树形结构
     * @return
     */
    @Override
    public List<CategoryResult> listWithTree() {
        //1、查出所有分类
        List<CategoryEntity> categoryEntityList = baseMapper.selectList(null);
        if (!CollectionUtils.isEmpty(categoryEntityList)) {
            //2、组装成父子的树形结构

            //2.1）、找到所有的一级分类
            List<CategoryResult> categoryDtoList = BeanKit.convertList(CategoryResult.class, categoryEntityList);
            List<CategoryResult> level1Menus = categoryDtoList.stream().filter(categoryEntity ->
                    categoryEntity.getParentCid().longValue() == 0
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

    @Override
    public void removeMenuByIds(List<Long> ids) {
        // TODO 1.检查当前删除的菜单，是否被别的地方引用
        baseMapper.deleteBatchIds(ids);
    }

    /**
     * 递归查找所有菜单的子菜单
     * @param root
     * @param all
     * @return
     */
    private List<CategoryResult> getChildrens(CategoryResult root, List<CategoryResult> all) {

        List<CategoryResult> children = all.stream().filter(categoryEntity -> {
            // TODO 坑坑坑坑
            // 如果值在[-128, 127]之间，会放在缓存里面，而超过这个范围就要new一个新的对象，
            // 也就是说==不能判断对象是否相等。
            // 当然，如果值是在[-128, 127]之间的话是测不出来什么问题的
            // 可以使用.longValue() 或者.equals()进行比较。
            return categoryEntity.getParentCid().longValue() == root.getCatId().longValue();
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