package com.qian.gulimall.admin.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Created by IntelliJ IDEA.
 * FeignConfig is OpenFeignClient配置
 *
 * @author QIAN
 * Date 2020/04/17
 * Time 11:01
 */
@Configuration
public class FeignConfig {
    /**
     * feignClient配置日志级别
     *
     * @return
     */
    @Bean
    Logger.Level feignLoggerLevel() {
        //  // 请求和响应的头信息,请求和响应的正文及元数据
        return Logger.Level.FULL;
    }
}
