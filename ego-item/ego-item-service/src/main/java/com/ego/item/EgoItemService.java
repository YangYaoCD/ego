package com.ego.item;

import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author YangYao
 * @date 2020/9/12 11:51
 * @Description
 */
@SpringBootApplication
@EnableDiscoveryClient
public class EgoItemService {
    public static void main(String[] args) {
        SpringApplication.run(EgoItemService.class, args);
    }
}
