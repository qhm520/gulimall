package com.qian.gulimall.product.controller;

import com.qian.gulimall.common.utils.PageUtils;
import com.qian.gulimall.common.utils.Pageable;
import com.qian.gulimall.common.utils.R;
import com.qian.gulimall.product.api.criteria.BrandCriteria;
import com.qian.gulimall.product.api.dto.BrandDto;
import com.qian.gulimall.product.entity.BrandEntity;
import com.qian.gulimall.product.feign.SysOssFeignService;
import com.qian.gulimall.product.service.BrandService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 品牌
 *
 * @author QIAN
 * @email 794308528@qq.com
 * @date 2020-07-25 16:06:04
 */
@RestController
@RequestMapping("product/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    @Autowired
    private SysOssFeignService sysOssFeignService;

    /**
     * 列表
     */
    @RequestMapping("/list")
//    @PreAuthorize("hasPermission('', 'product:brand:list')")
    public R list(Pageable pageable, @ModelAttribute BrandCriteria brandCriteria) {
        PageUtils page = brandService.queryPage(pageable, brandCriteria);

        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{brandId}")
    //@RequiresPermissions("product:brand:info")
    public R info(@PathVariable("brandId") Long brandId) {
        BrandEntity brand = brandService.getById(brandId);
        if (StringUtils.isNotBlank(brand.getLogo())) {
            R info = sysOssFeignService.url(Long.valueOf(brand.getLogo()));
            if (info.getCode() == 0) {
                String url = (String) info.get("data");
                brand.setLogo(url);
            }
        }
        return R.ok().put("data", brand);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:brand:save")
    public R save(@Valid @RequestBody BrandDto brandDto) {
        R upload = sysOssFeignService.upload(brandDto.getLogo());
        if (upload.getCode() == 0) {
            brandDto.setLogo(String.valueOf(upload.get("id")));
            brandService.saveBrand(brandDto);
        } else {
            return upload;
        }
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:brand:update")
    public R update(@Valid @RequestBody BrandDto brandDto) {
        R upload = sysOssFeignService.upload(brandDto.getLogo());
        if (upload.getCode() == 0) {
            brandDto.setLogo(String.valueOf(upload.get("id")));
            brandService.updateBrand(brandDto);
        } else {
            return upload;
        }
        return R.ok();
    }

    /**
     * 更新状态
     */
    @RequestMapping("/update/status")
    //@RequiresPermissions("product:brand:update")
    public R updateShowStatus(@RequestBody BrandEntity brand) {
        brandService.updateById(brand);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:brand:delete")
    public R delete(@RequestBody Long[] brandIds) {
        brandService.removeByIds(Arrays.asList(brandIds));

        return R.ok();
    }

    @RequestMapping("/query/logo")
    public R queryLogo() {
        List<BrandEntity> list = brandService.list(null);
        if (!CollectionUtils.isEmpty(list)) {
            List<String> collect = list.stream().map(brand -> brand.getLogo()).collect(Collectors.toList());
            return R.ok().put("list", collect);
        }

        return R.error("查询为空");
    }
}
