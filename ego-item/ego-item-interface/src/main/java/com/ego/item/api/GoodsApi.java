package com.ego.item.api;

import com.ego.common.pojo.CartDto;
import com.ego.common.pojo.PageResult;
import com.ego.item.Bo.SpuBo;
import com.ego.item.pojo.Sku;
import com.ego.item.pojo.SpuDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.CacheRequest;
import java.util.List;


@RequestMapping("/goods")
public interface GoodsApi {

    @GetMapping("spu/page")
    public ResponseEntity<PageResult<SpuBo>> page(
            @RequestParam(value = "key") String key,
            @RequestParam(value = "saleable") Boolean saleable,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows
    );


    @PostMapping
    public ResponseEntity<Void> save(@RequestBody SpuBo spuBO);

    @GetMapping("/sku/list/{spuId}")
    public ResponseEntity<List<Sku>> querySkuListBySpuId(@PathVariable("spuId") Long spuId);

    @GetMapping("/spuDetail/{spuId}")
    public ResponseEntity<SpuDetail> querySpuDetailBySpuId(@PathVariable("spuId")Long spuId);

    @GetMapping("/spuBo/{spuId}")
    ResponseEntity<SpuBo> queryGoodsById(@PathVariable("spuId") Long spuId);

    @GetMapping("/sku/{skuId}")
    ResponseEntity<Sku> querySkuBySkuId(@PathVariable(name = "skuId") Long skuId);

    @PostMapping("stock/decrease")
    void decreaseStock(@RequestBody List<CartDto> cartDtos);

    @PostMapping("stock/seckill/decrease")
    void decreaseSeckillStock(@RequestBody CartDto cartDto);
}
