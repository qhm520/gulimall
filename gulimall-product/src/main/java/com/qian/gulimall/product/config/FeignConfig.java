package com.qian.gulimall.product.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Created by IntelliJ IDEA.
 * FeignConfig is OpenFeign 日志打印
 *
 * @author QIAN
 * Date 2020/04/17
 * Time 11:01
 */
@Configuration
public class FeignConfig {

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}
