package com.qian.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qian.gulimall.common.constant.ProductConstant;
import com.qian.gulimall.common.utils.BeanKit;
import com.qian.gulimall.common.utils.PageUtils;
import com.qian.gulimall.common.utils.Pageable;
import com.qian.gulimall.common.utils.Query;
import com.qian.gulimall.product.api.criteria.AttrCriteria;
import com.qian.gulimall.product.api.dto.AttrDto;
import com.qian.gulimall.product.api.result.AttrResult;
import com.qian.gulimall.product.dao.AttrAttrgroupRelationDao;
import com.qian.gulimall.product.dao.AttrDao;
import com.qian.gulimall.product.dao.AttrGroupDao;
import com.qian.gulimall.product.dao.CategoryDao;
import com.qian.gulimall.product.entity.AttrAttrgroupRelationEntity;
import com.qian.gulimall.product.entity.AttrEntity;
import com.qian.gulimall.product.entity.AttrGroupEntity;
import com.qian.gulimall.product.entity.CategoryEntity;
import com.qian.gulimall.product.service.AttrService;
import com.qian.gulimall.product.service.CategoryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("attrService")
public class AttrServiceImpl extends ServiceImpl<AttrDao, AttrEntity> implements AttrService {

    @Autowired
    private AttrAttrgroupRelationDao attrAttrgroupRelationDao;

    @Autowired
    private AttrGroupDao attrGroupDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private CategoryService categoryService;


    @Override
    public PageUtils queryPage(Pageable pageable, AttrCriteria attrCriteria) {

        // 属性类型[0-销售属性，1-基本属性]
        QueryWrapper<AttrEntity> queryWrapper = new QueryWrapper<AttrEntity>()
                .eq("attr_type", attrCriteria.getAttrType())
                .like(StringUtils.isNotBlank(attrCriteria.getAttrName()), "attr_name", attrCriteria.getAttrName());

        if (attrCriteria.getCatelogId() != 0) {
            queryWrapper.eq("catelog_id", attrCriteria.getCatelogId());
        }
        IPage<AttrEntity> page = this.page(
                // 分页
                new Query<AttrEntity>().getPage(pageable),
                // 查询条件
                queryWrapper
        );

        PageUtils pageUtils = new PageUtils(page);
        List<AttrResult> attrResults = BeanKit.convertList(AttrResult.class, page.getRecords());
        attrResults.stream().map(attrResult -> {

            // 属性类型[0-销售属性，1-基本属性]
            if (attrCriteria.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()) {
                AttrAttrgroupRelationEntity attrId = attrAttrgroupRelationDao.selectOne(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrResult.getAttrId()));
                if (attrId != null && attrId.getAttrGroupId() != null) {
                    AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrId.getAttrGroupId());
                    attrResult.setGroupName(attrGroupEntity.getAttrGroupName());
                }
            }

            CategoryEntity categoryEntity = categoryDao.selectById(attrResult.getCatelogId());
            if (categoryEntity != null) {
                attrResult.setCatelogName(categoryEntity.getName());
            }
            return attrResult;
        }).collect(Collectors.toList());


        // 封装返回数据到result对象中
        return new PageUtils(page, attrResults);
    }

    /**
     * 获取当前分组没有关联的所有属性
     *
     * @param pageable
     * @param attrCriteria
     * @return
     */
    @Override
    public PageUtils queryNoAttrRelationPage(Pageable pageable, AttrCriteria attrCriteria) {
        //1、当前分组只能关联自己所属的分类里面的所有属性
        AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrCriteria.getAttrGroupId());
        Long catelogId = attrGroupEntity.getCatelogId();
        //2、当前分组只能关联别的分组没有引用的属性
        //2.1)、当前分类下的其他分组
        List<AttrGroupEntity> group = attrGroupDao.selectList(new QueryWrapper<AttrGroupEntity>().eq("catelog_id", catelogId));
        List<Long> collect = group.stream().map(item -> {
            return item.getAttrGroupId();
        }).collect(Collectors.toList());

        //2.2)、这些分组关联的属性
        List<AttrAttrgroupRelationEntity> groupId = attrAttrgroupRelationDao.selectList(new QueryWrapper<AttrAttrgroupRelationEntity>().in("attr_group_id", collect));
        List<Long> attrIds = groupId.stream().map(item -> {
            return item.getAttrId();
        }).collect(Collectors.toList());

        //2.3)、从当前分类的所有属性中移除这些属性；
        QueryWrapper<AttrEntity> wrapper = new QueryWrapper<AttrEntity>().eq("catelog_id", catelogId).eq("attr_type", ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode());
        if (attrIds != null && attrIds.size() > 0) {
            wrapper.notIn("attr_id", attrIds);
        }
        if (attrCriteria.getAttrId() != null) {
            wrapper.and((w) -> {
                w.eq("attr_id", attrCriteria.getAttrId());
            });
        }
        if (StringUtils.isNotBlank(attrCriteria.getAttrName())) {
            wrapper.and((w) -> {
                w.like("attr_name", attrCriteria.getAttrName());
            });
        }
        IPage<AttrEntity> page = this.page(new Query<AttrEntity>().getPage(pageable), wrapper);

        PageUtils pageUtils = new PageUtils(page);

        return pageUtils;
    }

    @Transactional
    @Override
    public void saveAttr(AttrDto attrDto) {
        // 保存基本数据
        AttrEntity attrEntity = BeanKit.convertBean(AttrEntity.class, attrDto);
        this.save(attrEntity);
        // 属性类型[0-销售属性，1-基本属性]
        if (attrDto.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()) {
            // 保存关联关系
            AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = new AttrAttrgroupRelationEntity();
            attrAttrgroupRelationEntity.setAttrGroupId(attrDto.getAttrGroupId());
            attrAttrgroupRelationEntity.setAttrId(attrEntity.getAttrId());
            attrAttrgroupRelationDao.insert(attrAttrgroupRelationEntity);
        }


    }

    @Override
    public AttrResult getAttrInfo(Long attrId) {

        AttrEntity attrEntity = this.getById(attrId);
        if (attrEntity != null) {
            AttrResult attrResult = BeanKit.convertBean(AttrResult.class, attrEntity);

            // 属性类型[0-销售属性，1-基本属性]
            if (attrEntity.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()) {
                // 1.设置分组信息
                AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = attrAttrgroupRelationDao.selectOne(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrId));
                if (attrAttrgroupRelationEntity != null) {
                    attrResult.setAttrGroupId(attrAttrgroupRelationEntity.getAttrGroupId());
                    AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrAttrgroupRelationEntity.getAttrGroupId());
                    if (attrGroupEntity != null) {
                        attrResult.setGroupName(attrGroupEntity.getAttrGroupName());
                    }
                }
            }
            // 2.设置分类信息
            Long[] catelogPath = categoryService.findCatelogPath(attrEntity.getCatelogId());
            attrResult.setCatelogPath(catelogPath);

            CategoryEntity categoryEntity = categoryDao.selectById(attrEntity.getCatelogId());
            if (categoryEntity != null) {
                attrResult.setCatelogName(categoryEntity.getName());
            }

            return attrResult;
        }
        return null;
    }

    @Override
    public void updateAttr(AttrDto attrDto) {
        AttrEntity attrEntity = BeanKit.convertBean(AttrEntity.class, attrDto);
        this.updateById(attrEntity);
        // 属性类型[0-销售属性，1-基本属性]
        if (attrDto.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()) {
            // 设置关联关系
            AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = new AttrAttrgroupRelationEntity();
            attrAttrgroupRelationEntity.setAttrGroupId(attrDto.getAttrGroupId());
            attrAttrgroupRelationEntity.setAttrId(attrDto.getAttrId());
            Integer count = attrAttrgroupRelationDao.selectCount(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrDto.getAttrId()));
            if (count > 0) {
                attrAttrgroupRelationDao.update(attrAttrgroupRelationEntity, new UpdateWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrDto.getAttrId()));
            } else {
                attrAttrgroupRelationDao.insert(attrAttrgroupRelationEntity);
            }
        }
    }

    @Override
    public List<AttrResult> queryAttrRelation(Long attrGroupId) {
        List<AttrAttrgroupRelationEntity> attrAttrgroupRelationEntityList = attrAttrgroupRelationDao.selectList(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_group_id", attrGroupId));
        if (!CollectionUtils.isEmpty(attrAttrgroupRelationEntityList)) {
            List<Long> attrIdList = attrAttrgroupRelationEntityList.stream().map(item -> item.getAttrId()).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(attrIdList)) {
                List<AttrEntity> attrEntities = this.listByIds(attrIdList);
                return BeanKit.convertList(AttrResult.class, attrEntities);
            }
        }
        return null;
    }

}