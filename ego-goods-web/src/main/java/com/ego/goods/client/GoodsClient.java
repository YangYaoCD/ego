package com.ego.goods.client;

import com.ego.item.api.GoodsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author YangYao
 * @date 2020/9/15 15:19
 * @Description
 */
@FeignClient("item-service")
public interface GoodsClient extends GoodsApi {
}
