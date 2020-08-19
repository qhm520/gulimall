package com.qian.gulimall.product.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qian.gulimall.product.api.criteria.AttrGroupCriteria;
import com.qian.gulimall.product.api.result.AttrResult;
import com.qian.gulimall.product.entity.AttrAttrgroupRelationEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 属性&属性分组关联
 * 
 * @author QIAN
 * @email 794308528@qq.com
 * @date 2020-07-25 16:06:04
 */
@Mapper
public interface AttrAttrgroupRelationDao extends BaseMapper<AttrAttrgroupRelationEntity> {


    public IPage<AttrResult> queryAttrRelationPage(Page<AttrGroupCriteria> page, @Param("group") AttrGroupCriteria attrGroupCriteria);

    void deleteRelationBatch(@Param("list") List<AttrAttrgroupRelationEntity> attrAttrgroupRelationEntities);
}
