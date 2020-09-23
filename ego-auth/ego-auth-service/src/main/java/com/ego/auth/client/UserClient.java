package com.ego.auth.client;

import com.ego.user.Api.UserApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author YangYao
 * @date 2020/9/20 21:44
 * @Description
 */
@FeignClient("user-service")
public interface UserClient extends UserApi {
}
