package com.qian.gulimall.product.feign;

import com.qian.gulimall.common.entity.vo.SysLogVo;
import com.qian.gulimall.common.utils.R;
import com.qian.gulimall.product.config.FeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by IntelliJ IDEA.
 * SysLogFeignService is
 *
 * @author QIAN
 * Date 2020/04/26
 * Time 14:56
 */
@FeignClient(value = "gulimall-admin", path = "/sys/log", configuration = FeignConfiguration.class)
public interface SysLogFeignService {

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody SysLogVo sysLog);
}
