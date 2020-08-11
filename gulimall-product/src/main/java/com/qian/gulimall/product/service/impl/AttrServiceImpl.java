package com.qian.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qian.gulimall.common.utils.BeanKit;
import com.qian.gulimall.common.utils.PageUtils;
import com.qian.gulimall.common.utils.Pageable;
import com.qian.gulimall.common.utils.Query;
import com.qian.gulimall.product.api.criteria.AttrCriteria;
import com.qian.gulimall.product.api.result.AttrResult;
import com.qian.gulimall.product.dao.AttrDao;
import com.qian.gulimall.product.entity.AttrEntity;
import com.qian.gulimall.product.service.AttrService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;


@Service("attrService")
public class AttrServiceImpl extends ServiceImpl<AttrDao, AttrEntity> implements AttrService {


    @Override
    public PageUtils queryPage(Pageable pageable, AttrCriteria attrCriteria) {

        IPage<AttrEntity> page = this.page(
                // 分页
                new Query<AttrEntity>().getPage(pageable),
                // 查询条件
                new QueryWrapper<AttrEntity>()
                        // name
                        .like(StringUtils.isNotBlank(attrCriteria.getAttrName()), "attr_name", attrCriteria.getAttrName())
                // show_status
//                        .eq(null != attrCriteria.getSearchType(), "show_status", attrCriteria.getSearchType())
        );
        // 封装返回数据到result对象中
        return new PageUtils(page, BeanKit.convertList(AttrResult.class, page.getRecords()));
    }
}