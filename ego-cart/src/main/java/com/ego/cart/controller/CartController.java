package com.ego.cart.controller;

import com.ego.auth.entity.UserInfo;
import com.ego.cart.interceptor.LoginInterceptor;
import com.ego.cart.pojo.Cart;
import com.ego.cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author YangYao
 * @date 2020/9/22 10:03
 * @Description
 */
@RestController
public class CartController {
    @Autowired
    CartService cartService;
    @PostMapping
    public ResponseEntity<Void> addCart(@RequestBody Cart cart){
        cartService.addCart(cart);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<Cart>> queryCart(){
        List<Cart> result=cartService.queryCart();
        if (result==null||result.size()==0){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @PutMapping
    public ResponseEntity<Void> updateNum(@RequestBody Cart cart){
        cartService.updateNum(cart);
        return ResponseEntity.ok(null);
    }
}
