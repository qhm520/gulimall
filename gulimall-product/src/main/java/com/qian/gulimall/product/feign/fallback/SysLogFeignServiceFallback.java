package com.qian.gulimall.product.feign.fallback;

import com.qian.gulimall.common.entity.vo.SysLogVo;
import com.qian.gulimall.common.utils.R;
import com.qian.gulimall.product.feign.SysLogFeignService;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * SysLogFeignServiceFallback is
 *
 * @author QIAN
 * Date 2020/04/26
 * Time 21:08
 */
@Component
public class SysLogFeignServiceFallback implements SysLogFeignService {
    @Override
    public R save(SysLogVo sysLog) {
        return R.error(403, "服务异常，请稍后再试");
    }
}
