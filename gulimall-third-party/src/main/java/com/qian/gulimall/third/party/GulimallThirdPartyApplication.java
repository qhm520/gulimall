package com.qian.gulimall.third.party;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Created by IntelliJ IDEA.
 * GulimallThirdPartyApplication is
 *
 * @author QIAN
 * Date 2020/04/21
 * Time 15:13
 */
@EnableDiscoveryClient
@SpringBootApplication
public class GulimallThirdPartyApplication {

    public static void main(String[] args) {
        SpringApplication.run(GulimallThirdPartyApplication.class, args);
    }
}
