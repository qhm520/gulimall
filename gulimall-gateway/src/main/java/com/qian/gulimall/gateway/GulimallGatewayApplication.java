package com.qian.gulimall.gateway;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Created by IntelliJ IDEA.
 * GulimallGatewayApplication is
 * * 1、开启服务注册发现
 *  *  (配置nacos的注册中心地址)
 *  * 2、编写网关配置文件
 * @author QIAN
 * Date 2020/04/16
 * Time 23:12
 */
@EnableDiscoveryClient
@SpringBootApplication
public class GulimallGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GulimallGatewayApplication.class, args);
    }

}
