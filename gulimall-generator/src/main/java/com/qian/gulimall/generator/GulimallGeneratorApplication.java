package com.qian.gulimall.generator;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.qian.gulimall.generator.dao")
public class GulimallGeneratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(GulimallGeneratorApplication.class, args);
	}
}
