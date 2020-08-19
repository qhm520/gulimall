package com.qian.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qian.gulimall.common.constant.ProductConstant;
import com.qian.gulimall.common.utils.BeanKit;
import com.qian.gulimall.common.utils.PageUtils;
import com.qian.gulimall.common.utils.Pageable;
import com.qian.gulimall.common.utils.Query;
import com.qian.gulimall.product.api.criteria.AttrGroupCriteria;
import com.qian.gulimall.product.api.dto.AttrGroupVo;
import com.qian.gulimall.product.api.result.AttrGroupResult;
import com.qian.gulimall.product.api.result.AttrResult;
import com.qian.gulimall.product.dao.AttrAttrgroupRelationDao;
import com.qian.gulimall.product.dao.AttrGroupDao;
import com.qian.gulimall.product.entity.AttrAttrgroupRelationEntity;
import com.qian.gulimall.product.entity.AttrEntity;
import com.qian.gulimall.product.entity.AttrGroupEntity;
import com.qian.gulimall.product.service.AttrGroupService;
import com.qian.gulimall.product.service.AttrService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;


@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {

    @Autowired
    private AttrAttrgroupRelationDao attrAttrgroupRelationDao;

    @Autowired
    private AttrService attrService;


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

    @Override
    public List<AttrGroupResult> queryAttrGroupInfoByCatelogId(Long catelogId) {

        QueryWrapper<AttrGroupEntity> attrGroupEntityQueryWrapper = new QueryWrapper<AttrGroupEntity>().eq("catelog_id", catelogId);

        List<AttrGroupEntity> attrGroupEntities = this.baseMapper.selectList(attrGroupEntityQueryWrapper);
        if (!CollectionUtils.isEmpty(attrGroupEntities)) {
            return BeanKit.convertList(AttrGroupResult.class, attrGroupEntities);
        }

        return null;
    }

    @Override
    public PageUtils queryAttrRelationPage(Pageable pageable, AttrGroupCriteria attrGroupCriteria) {
        Page<AttrGroupCriteria> attrGroupCriteriaPage = new Page<>(pageable.getPageNumber(), pageable.getPageSize());
        IPage<AttrResult> attrResultIPage = attrAttrgroupRelationDao.queryAttrRelationPage(attrGroupCriteriaPage, attrGroupCriteria);
        return new PageUtils(attrGroupCriteriaPage, attrResultIPage.getRecords());
    }

    @Override
    public void deleteAttrGroupRelation(List<AttrGroupVo> attrGroupVoList) {

        List<AttrAttrgroupRelationEntity> attrAttrgroupRelationEntities = BeanKit.convertList(AttrAttrgroupRelationEntity.class, attrGroupVoList);
        attrAttrgroupRelationDao.deleteRelationBatch(attrAttrgroupRelationEntities);
    }

}