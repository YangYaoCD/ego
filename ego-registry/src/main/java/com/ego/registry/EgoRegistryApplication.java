package com.ego.registry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author YangYao
 * @date 2020/9/12 10:49
 * @Description
 */
@SpringBootApplication
@EnableEurekaServer
public class EgoRegistryApplication {
    public static void main(String[] args) {
        SpringApplication.run(EgoRegistryApplication.class,args);
    }
}
