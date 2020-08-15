package com.qian.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qian.gulimall.common.utils.PageUtils;
import com.qian.gulimall.common.utils.Pageable;
import com.qian.gulimall.common.utils.Query;
import com.qian.gulimall.product.api.criteria.AttrGroupCriteria;
import com.qian.gulimall.product.dao.AttrGroupDao;
import com.qian.gulimall.product.entity.AttrGroupEntity;
import com.qian.gulimall.product.service.AttrGroupService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;


@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {


    @Override
    public PageUtils queryPage(Pageable pageable, AttrGroupCriteria attrGroupCriteria) {

        QueryWrapper<AttrGroupEntity> attrGroupEntityQueryWrapper = new QueryWrapper<>();
        attrGroupEntityQueryWrapper
                // attr_group_id
                .like(attrGroupCriteria.getAttrGroupId() != null, "attr_group_id", attrGroupCriteria.getAttrGroupId())
                // attr_group_name
                .like(StringUtils.isNotBlank(attrGroupCriteria.getAttrGroupName()), "attr_group_name", attrGroupCriteria.getAttrGroupName());
        // catelogId == 0 查询全部
        if (attrGroupCriteria.getCatelogId() != 0) {
            attrGroupEntityQueryWrapper
                    // catelog_id
                    .eq(attrGroupCriteria.getCatelogId() != null, "catelog_id", attrGroupCriteria.getCatelogId());
        }
        IPage<AttrGroupEntity> page = this.page(
                // 分页
                new Query<AttrGroupEntity>().getPage(pageable),
                // 查询条件
                attrGroupEntityQueryWrapper
        );

        // 封装返回数据到result对象中
        return new PageUtils(page, page.getRecords());
    }
}