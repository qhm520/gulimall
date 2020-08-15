package com.qian.gulimall.product.controller;

import com.qian.gulimall.common.utils.BeanKit;
import com.qian.gulimall.common.utils.PageUtils;
import com.qian.gulimall.common.utils.Pageable;
import com.qian.gulimall.common.utils.R;
import com.qian.gulimall.product.api.criteria.AttrGroupCriteria;
import com.qian.gulimall.product.api.criteria.BrandCriteria;
import com.qian.gulimall.product.api.result.AttrGroupResult;
import com.qian.gulimall.product.entity.AttrGroupEntity;
import com.qian.gulimall.product.service.AttrGroupService;
import com.qian.gulimall.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;


/**
 * 属性分组
 *
 * @author QIAN
 * @email 794308528@qq.com
 * @date 2020-07-25 16:06:04
 */
@RestController
@RequestMapping("product/attrgroup")
public class AttrGroupController {
    @Autowired
    private AttrGroupService attrGroupService;

    @Autowired
    private CategoryService categoryService;

    /**
     * 列表
     */
    @RequestMapping("/list")
//    @PreAuthorize("hasPermission('', 'product:attrgroup:list')")
    public R list(Pageable pageable, @ModelAttribute AttrGroupCriteria attrGroupCriteria) {
        PageUtils page = attrGroupService.queryPage(pageable, attrGroupCriteria);

        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{attrGroupId}")
    //@RequiresPermissions("product:attrgroup:info")
    public R info(@PathVariable("attrGroupId") Long attrGroupId){
		AttrGroupEntity attrGroup = attrGroupService.getById(attrGroupId);

        Long catelogId = attrGroup.getCatelogId();
        Long[] path = categoryService.findCatelogPath(catelogId);
        AttrGroupResult attrGroupResult = BeanKit.convertBean(AttrGroupResult.class, attrGroup);

        attrGroupResult.setCatelogPath(path);

        return R.ok().put("attrGroup", attrGroupResult);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:attrgroup:save")
    public R save(@RequestBody AttrGroupEntity attrGroup){
		attrGroupService.save(attrGroup);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:attrgroup:update")
    public R update(@RequestBody AttrGroupEntity attrGroup){
		attrGroupService.updateById(attrGroup);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:attrgroup:delete")
    public R delete(@RequestBody Long[] attrGroupIds){
		attrGroupService.removeByIds(Arrays.asList(attrGroupIds));

        return R.ok();
    }

}
