package com.qian.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qian.gulimall.common.utils.BeanKit;
import com.qian.gulimall.common.utils.PageUtils;
import com.qian.gulimall.common.utils.Pageable;
import com.qian.gulimall.common.utils.Query;
import com.qian.gulimall.common.utils.R;
import com.qian.gulimall.product.api.criteria.BrandCriteria;
import com.qian.gulimall.product.api.dto.BrandDto;
import com.qian.gulimall.product.api.result.BrandResult;
import com.qian.gulimall.product.dao.BrandDao;
import com.qian.gulimall.product.entity.BrandEntity;
import com.qian.gulimall.product.feign.SysOssFeignService;
import com.qian.gulimall.product.service.BrandService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;


@Service("brandService")
public class BrandServiceImpl extends ServiceImpl<BrandDao, BrandEntity> implements BrandService {

    @Autowired
    private SysOssFeignService sysOssFeignService;

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

        List<BrandResult> brandResultList = BeanKit.convertList(BrandResult.class, page.getRecords());
        if (!CollectionUtils.isEmpty(brandResultList)) {
            List<BrandResult> data = brandResultList.stream().map(item -> {
                if (StringUtils.isNotBlank(item.getLogo())) {
                    R r = sysOssFeignService.url(Long.valueOf(item.getLogo()));
                    if (r.getCode() == 0) {
                        item.setLogo(String.valueOf(r.get("data")));
                        return item;
                    }
                }
                return item;
            }).collect(Collectors.toList());
            return new PageUtils(page, data);
        }
        // 封装返回数据到result对象中
        return new PageUtils(page, brandResultList);
    }

    @Override
    public void saveBrand(BrandDto brandDto) {
        BrandEntity brandEntity = BeanKit.convertBean(BrandEntity.class, brandDto);
        this.baseMapper.insert(brandEntity);
    }

    @Override
    public void updateBrand(BrandDto brandDto) {
        BrandEntity brandEntity = BeanKit.convertBean(BrandEntity.class, brandDto);
        this.baseMapper.updateById(brandEntity);
    }
}