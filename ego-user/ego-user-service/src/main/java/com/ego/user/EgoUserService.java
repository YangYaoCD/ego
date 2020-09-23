package com.ego.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author YangYao
 * @date 2020/9/20 15:35
 * @Description
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.ego.user.mapper")
public class EgoUserService {
    public static void main(String[] args) {
        SpringApplication.run(EgoUserService.class, args);
    }
}
