package com.qian.gulimall.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * 用户admin密码：9ec9750e709431dad22365cabc5c625482e574c74adaebba7dd02f1129e4ce1d
 * 用户qian密码：$2a$10$ejuKZ.x4YB9T8aOOVkfrX.gN7xceg7YYY490GywQ5g23IgOLcqtXq
 * token: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJhZG1pbiIsInNjb3BlIjpbImFsbCIsInJlYWQiLCJ3cml0ZSJdLCJjb21wYW55IjoiZ3VsaW1hbGwiLCJleHAiOjE1ODk5NDE0MjksImF1dGhvcml0aWVzIjpbInN5czpzY2hlZHVsZTppbmZvIiwic3lzOm1lbnU6dXBkYXRlIiwic3lzOmNvbmZpZzppbmZvIiwic3lzOmNvbmZpZzpzYXZlIiwic3lzOm1lbnU6bGlzdCIsInN5czpjb25maWc6dXBkYXRlIiwic3lzOnNjaGVkdWxlOnJlc3VtZSIsInN5czp1c2VyOmRlbGV0ZSIsIlJPTEVfVVNFUiIsInN5czpjb25maWc6bGlzdCIsInN5czptZW51OmluZm8iLCJzeXM6cm9sZTpsaXN0Iiwic3lzOnVzZXI6dXBkYXRlIiwic3lzOm1lbnU6c2VsZWN0Iiwic3lzOnNjaGVkdWxlOnVwZGF0ZSIsInN5czpzY2hlZHVsZTpzYXZlIiwic3lzOnJvbGU6c2VsZWN0Iiwic3lzOnVzZXI6bGlzdCIsInN5czptZW51OnNhdmUiLCJzeXM6b3NzOmFsbF0iLCJzeXM6cm9sZTpzYXZlIiwic3lzOnNjaGVkdWxlOmxvZyIsInN5czpyb2xlOmluZm8iLCJzeXM6c2NoZWR1bGU6ZGVsZXRlIiwiW3N5czptZW51OmRlbGV0ZSIsInN5czpyb2xlOnVwZGF0ZSIsInN5czpzY2hlZHVsZTpsaXN0Iiwic3lzOnVzZXI6aW5mbyIsInN5czpjb25maWc6ZGVsZXRlIiwic3lzOnNjaGVkdWxlOnJ1biIsInN5czpyb2xlOmRlbGV0ZSIsIlJPTEVfQURNSU4iLCJzeXM6dXNlcjpzYXZlIiwic3lzOnNjaGVkdWxlOnBhdXNlIiwic3lzOmxvZzpsaXN0Il0sImp0aSI6IjJmZmM1N2NiLWEyYTAtNGNhMC04ZjNkLTJmMTJhOGRkMmY2OSIsImNsaWVudF9pZCI6Imd1bGltYWxsIn0.mTyLu4Nk03XzCla8Syz7fzeS6YCDLeWdHEWMe2zuZCM
 * Created by IntelliJ IDEA.
 * GulimallAdminApplication is
 *
 * @author QIAN
 * Date 2020/04/16
 * Time 23:38
 */
//@EnableAspectJAutoProxy
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan({"com.qian.gulimall.admin", "com.qian.gulimall.security"})
@EnableFeignClients(basePackages = "com.qian.gulimall.admin.feign")
@Slf4j
public class GulimallAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(GulimallAdminApplication.class, args);
    }

    /**
     * 所有初始化bean
     */
/*    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            log.error("Let's inspect the beans provided by Spring Boot:");

            String[] beanNames = ctx.getBeanDefinitionNames();
            System.out.println("spring bean count：" + beanNames.length);
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                log.info("-------" + beanName);
            }
        };
    }*/

}
