package com.qian.gulimall.product.feign;

import com.qian.gulimall.common.entity.result.UserDetailsResult;
import com.qian.gulimall.common.utils.R;
import com.qian.gulimall.product.config.FeignConfiguration;
import com.qian.gulimall.product.feign.fallback.AdminFeignServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by IntelliJ IDEA.
 * SysLogFeignService is
 *
 * @author QIAN
 * Date 2020/04/26
 * Time 14:56
 */
@FeignClient(value = "gulimall-admin", configuration = FeignConfiguration.class, fallback = AdminFeignServiceFallback.class)
public interface AdminFeignService {

    /**
     * 保存
     */
    @GetMapping("/me")
    public R me();
}
