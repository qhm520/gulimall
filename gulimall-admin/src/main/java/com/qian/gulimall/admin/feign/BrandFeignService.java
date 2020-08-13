package com.qian.gulimall.admin.feign;

import com.qian.gulimall.admin.config.FeignConfiguration;
import com.qian.gulimall.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Created by IntelliJ IDEA.
 * SysOssFeignService is
 *
 * @author QIAN
 * Date 2020/08/12
 * Time 12:05
 */
@FeignClient(value = "gulimall-product", path = "/product/brand", configuration = FeignConfiguration.class)
public interface BrandFeignService {

    /**
     * 导航菜单
     */
    @GetMapping("/query/logo")
    public R queryAllLogo();
}
