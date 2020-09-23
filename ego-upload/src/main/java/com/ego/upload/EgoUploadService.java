package com.ego.upload;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author YangYao
 * @date 2020/9/13 15:57
 * @Description 上传微服务
 */
@SpringBootApplication
@EnableDiscoveryClient
public class EgoUploadService {
    public static void main(String[] args) {
        SpringApplication.run(EgoUploadService.class,args);
    }
}
