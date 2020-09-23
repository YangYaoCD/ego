package com.ego.user.controller;

import com.ego.user.mapper.UserMapper;
import com.ego.user.pojo.User;
import com.ego.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utils.CodecUtils;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.websocket.server.PathParam;

/**
 * @author YangYao
 * @date 2020/9/20 15:50
 * @Description
 */
@RestController
public class UserController {
    @Autowired
    private UserService userService;


    //http://api.ego.com/api/user/check/10201400324/1
    @GetMapping("check/{data}/{type}")
    public ResponseEntity<Boolean> check(@PathVariable("data")String data,@PathVariable("type")Integer type){
        Boolean isOk=userService.check(data,type);
        if (isOk==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(!isOk);
    }

    @PostMapping("send")
    public ResponseEntity<Boolean> send(@RequestParam("phone") String phone){
        Boolean flag = userService.check(phone, 2);
        if (flag==null||flag){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Boolean isOk=userService.sendMessage(phone);
        return ResponseEntity.ok(isOk);
    }

    @PostMapping("register")
    public ResponseEntity<Boolean> register(@Valid User user, @PathParam("code") String code){
        Boolean isOk=userService.register(user,code);
        if (!isOk){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("query")
    public ResponseEntity<User> queryUser(
            @RequestParam("username") String username,
            @RequestParam("password") String password
    ) {
        User user = this.userService.queryUser(username, password);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(user);
    }


}
