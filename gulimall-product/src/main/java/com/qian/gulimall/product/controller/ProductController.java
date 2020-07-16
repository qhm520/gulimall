package com.qian.gulimall.product.controller;

import com.qian.gulimall.common.annotation.SysLog;
import com.qian.gulimall.common.utils.R;
import com.qian.gulimall.product.feign.SysMenuFeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by IntelliJ IDEA.
 * ProductController is
 *
 * @author QIAN
 * Date 2020/04/21
 * Time 15:31
 */
@Slf4j
@RestController
public class ProductController {

    @Autowired
    private SysMenuFeignService sysMenuFeignService;

    @GetMapping("/menu")
    public R getMenu() {
        return sysMenuFeignService.nav();
    }

    @GetMapping("/index")
    @SysLog("测试保存日志")
    @PreAuthorize("hasPermission('', 'ROLE_ADMIN')")
    public R index() {
        return R.ok();
    }

    @GetMapping("/auto")
    public R auto() {

        return R.ok();
    }
}
