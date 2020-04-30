package com.qian.gulimall.product.feign.fallback;

import com.qian.gulimall.common.entity.result.UserDetailsResult;
import com.qian.gulimall.common.entity.vo.UserDetailsVo;
import com.qian.gulimall.common.utils.R;
import com.qian.gulimall.product.feign.AdminFeignService;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * AdminFeignServiceFallback is
 *
 * @author QIAN
 * Date 2020/04/26
 * Time 21:07
 */
//@Component
public class AdminFeignServiceFallback implements AdminFeignService {
    @Override
    public R me() {
        return R.error("服务异常，请稍后再试");
    }
}
