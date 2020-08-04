package com.qian.gulimall.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Created by IntelliJ IDEA.
 * GulimallSearchApplication is
 *
 * @author QIAN
 * Date 2020/07/30
 * Time 19:50
 */
@EnableDiscoveryClient
@SpringBootApplication
public class GulimallSearchApplication {
    public static void main(String[] args) {
        SpringApplication.run(GulimallSearchApplication.class, args);
    }
}
