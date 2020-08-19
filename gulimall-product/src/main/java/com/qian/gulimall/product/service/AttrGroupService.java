package com.qian.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qian.gulimall.common.utils.PageUtils;
import com.qian.gulimall.common.utils.Pageable;
import com.qian.gulimall.common.utils.R;
import com.qian.gulimall.product.api.criteria.AttrGroupCriteria;
import com.qian.gulimall.product.api.dto.AttrGroupVo;
import com.qian.gulimall.product.api.result.AttrGroupResult;
import com.qian.gulimall.product.entity.AttrGroupEntity;

import java.util.List;
import java.util.Map;

/**
 * 属性分组
 *
 * @author QIAN
 * @email 794308528@qq.com
 * @date 2020-07-25 16:06:04
 */
public interface AttrGroupService extends IService<AttrGroupEntity> {

    PageUtils queryPage(Pageable pageable, AttrGroupCriteria attrGroupCriteria);

    List<AttrGroupResult> queryAttrGroupInfoByCatelogId(Long catelogId);

    PageUtils queryAttrRelationPage(Pageable pageable, AttrGroupCriteria attrGroupCriteria);

    void deleteAttrGroupRelation(List<AttrGroupVo> attrGroupVoList);

}

