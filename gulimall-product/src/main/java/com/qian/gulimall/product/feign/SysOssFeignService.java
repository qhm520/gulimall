package com.qian.gulimall.product.feign;

import com.qian.gulimall.common.utils.R;
import com.qian.gulimall.product.config.FeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by IntelliJ IDEA.
 * SysOssFeignService is
 *
 * @author QIAN
 * Date 2020/08/12
 * Time 12:05
 */
@FeignClient(value = "gulimall-admin", path = "/sys/oss", configuration = FeignConfiguration.class)
public interface SysOssFeignService {

    /**
     * 导航菜单
     */
    @PostMapping("upload")
    public R upload(String file);

    @GetMapping("/url/{id}")
    public R url(@PathVariable("id") Long id);
}
