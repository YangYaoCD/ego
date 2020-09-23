package com.ego.cart.service;

import com.ego.auth.entity.UserInfo;
import com.ego.cart.client.GoodsClient;
import com.ego.cart.interceptor.LoginInterceptor;
import com.ego.cart.pojo.Cart;
import com.ego.item.pojo.Sku;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import utils.JsonUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author YangYao
 * @date 2020/9/22 10:08
 * @Description
 */
@Service
public class CartService {
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private GoodsClient goodsClient;
    private final static String prefix="ego:cart:uid:";

    public void addCart(Cart cart) {
        UserInfo userInfo= LoginInterceptor.getUser();

        //操作redis map
        BoundHashOperations<String, Object, Object> carts = redisTemplate.boundHashOps(prefix + userInfo.getId());
        //判断redis中是否有该购物项
        Boolean hasKey = carts.hasKey(cart.getSkuId().toString());
        Integer num = cart.getNum();
        if (hasKey){
            cart= JsonUtils.parse(carts.get(cart.getSkuId().toString()).toString(),Cart.class);
            cart.setNum(cart.getNum()+num);
        }else {
            Sku sku=goodsClient.querySkuBySkuId(cart.getSkuId()).getBody();
            cart.setUserId(userInfo.getId());
            cart.setOwnSpec(sku.getOwnSpec());
            cart.setPrice(sku.getPrice());
            cart.setImage(sku.getImages());
            cart.setTitle(sku.getTitle());
        }
        carts.put(cart.getSkuId().toString(),JsonUtils.serialize(cart));
    }

    public List<Cart> queryCart() {
        UserInfo user = LoginInterceptor.getUser();
        BoundHashOperations<String, Object, Object> carts = redisTemplate.boundHashOps(prefix + user.getId());
        return carts.values().stream().map(json->JsonUtils.parse(json.toString(),Cart.class)).collect(Collectors.toList());
    }

    public void updateNum(Cart cart) {
        UserInfo user = LoginInterceptor.getUser();
        BoundHashOperations<String, Object, Object> carts = redisTemplate.boundHashOps(prefix + user.getId());
        String json = carts.get(cart.getSkuId().toString()).toString();
        Integer num = cart.getNum();
        cart = JsonUtils.parse(json, Cart.class);
        cart.setNum(num);
        carts.put(cart.getSkuId().toString(),JsonUtils.serialize(cart));
    }
}
