package com.ego.search.client;

import com.ego.common.pojo.PageResult;
import com.ego.item.Bo.SpuBo;
import com.ego.item.api.GoodsApi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author YangYao
 * @date 2020/9/15 15:19
 * @Description
 */
@FeignClient("item-service")
public interface GoodsClient extends GoodsApi {
}
