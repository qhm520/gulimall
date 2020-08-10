package com.qian.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qian.gulimall.common.utils.BeanKit;
import com.qian.gulimall.common.utils.PageUtils;
import com.qian.gulimall.common.utils.Pageable;
import com.qian.gulimall.common.utils.Query;
import com.qian.gulimall.product.api.criteria.BrandCriteria;
import com.qian.gulimall.product.api.result.BrandResult;
import com.qian.gulimall.product.dao.BrandDao;
import com.qian.gulimall.product.entity.BrandEntity;
import com.qian.gulimall.product.service.BrandService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;


@Service("brandService")
public class BrandServiceImpl extends ServiceImpl<BrandDao, BrandEntity> implements BrandService {


    @Override
    public PageUtils queryPage(Pageable pageable, BrandCriteria brandCriteria) {

        IPage<BrandEntity> page = this.page(
                // 分页
                new Query<BrandEntity>().getPage(pageable),
                // 查询条件
                new QueryWrapper<BrandEntity>()
                        // name
                        .like(StringUtils.isNotBlank(brandCriteria.getName()), "name", brandCriteria.getName())
                        // show_status
                        .eq(null != brandCriteria.getShowStatus(), "show_status", brandCriteria.getShowStatus())
        );
        // 封装返回数据到result对象中
        return new PageUtils(page, BeanKit.convertList(BrandResult.class, page.getRecords()));
    }
}