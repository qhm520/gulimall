package com.qian.gulimall.product.service.impl;

import com.qian.gulimall.common.utils.BeanKit;
import com.qian.gulimall.product.api.dto.AttrGroupVo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qian.gulimall.common.utils.Constant;
import com.qian.gulimall.common.utils.PageUtils;
import com.qian.gulimall.common.utils.Pageable;
import com.qian.gulimall.common.utils.Query;

import com.qian.gulimall.product.dao.AttrAttrgroupRelationDao;
import com.qian.gulimall.product.entity.AttrAttrgroupRelationEntity;
import com.qian.gulimall.product.service.AttrAttrgroupRelationService;
import org.springframework.util.CollectionUtils;


@Service("attrAttrgroupRelationService")
public class AttrAttrgroupRelationServiceImpl extends ServiceImpl<AttrAttrgroupRelationDao, AttrAttrgroupRelationEntity> implements AttrAttrgroupRelationService {


    @Override
    public void saveBatch(List<AttrGroupVo> attrGroupVoList) {

        if (!CollectionUtils.isEmpty(attrGroupVoList)) {
            List<AttrAttrgroupRelationEntity> attrAttrgroupRelationEntities = BeanKit.convertList(AttrAttrgroupRelationEntity.class, attrGroupVoList);
            this.saveBatch(attrAttrgroupRelationEntities);
        }
    }
}