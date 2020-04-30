package com.qian.gulimall.product.feign;

import com.qian.gulimall.common.utils.R;
import com.qian.gulimall.product.config.FeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by IntelliJ IDEA.
 * SysMenuFeignService is
 *
 * @author QIAN
 * Date 2020/04/21
 * Time 15:34
 */
@FeignClient(value = "gulimall-admin", configuration = FeignConfiguration.class)
public interface SysMenuFeignService {

    /**
     * 导航菜单
     */
    @RequestMapping("/sys/menu/nav")
    public R nav();
}
