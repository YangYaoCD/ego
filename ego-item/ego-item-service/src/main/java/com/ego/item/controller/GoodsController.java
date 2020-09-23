package com.ego.item.controller;

import com.ego.common.pojo.CartDto;
import com.ego.common.pojo.PageResult;
import com.ego.item.Bo.SpuBo;
import com.ego.item.pojo.Sku;
import com.ego.item.pojo.SpuDetail;
import com.ego.item.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author YangYao
 * @date 2020/9/14 16:46
 * @Description http://api.ego.com/api/item/goods/spu/page?key=&saleable=1&page=1&rows=5
 */
@RestController
@RequestMapping("goods")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    @GetMapping("spu/page")
    public ResponseEntity<PageResult<SpuBo>> page(
            @RequestParam(value = "key") String key,
            @RequestParam(value = "saleable") Boolean saleable,
            @RequestParam(value = "page",defaultValue = "1") Integer page,
            @RequestParam(value = "rows",defaultValue = "5") Integer rows
    ){
        PageResult<SpuBo> result=goodsService.page(key,saleable,page,rows);
        if (result==null||result.getItems().size()==0){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

//    接受json对象
    @PostMapping
    public ResponseEntity<Void> save(@RequestBody SpuBo spuBo){
        goodsService.save(spuBo);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/sku/list/{spuId}")
    public ResponseEntity<List<Sku>> querySkuListBySpuId(@PathVariable("spuId") Long spuId){
        List<Sku> result = goodsService.querySkuListBySpuId(spuId);

        if(result==null)
        {
            return  ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(result);
    }

    @GetMapping("/spuDetail/{spuId}")
    public ResponseEntity<SpuDetail> querySpuDetailBySpuId(@PathVariable("spuId")Long spuId){
        SpuDetail result = goodsService.querySpuDetailBySpuId(spuId);

        if(result==null)
        {
            return  ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(result);
    }

    @GetMapping("/spuBo/{spuId}")
    public ResponseEntity<SpuBo> queryGoodsById(@PathVariable("spuId") Long spuId) {
        SpuBo spuBo=goodsService.queryGoodsById(spuId);
        if (spuBo==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(spuBo);
    }

    @GetMapping("/sku/{skuId}")
    ResponseEntity<Sku> querySkuBySkuId(@PathVariable(name = "skuId") Long skuId) {
        Sku sku=goodsService.querySkuBySkuId(skuId);
        if (sku==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(sku);
    }

    @PostMapping("stock/decrease")
    public ResponseEntity<Void> decreaseStock(@RequestBody List<CartDto> cartDtos){
        goodsService.decreaseStock(cartDtos);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }


    @PostMapping("stock/seckill/decrease")
    public ResponseEntity<Void> decreaseSeckillStock(@RequestBody CartDto cartDTO){
        goodsService.decreaseSeckillStock(cartDTO);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
