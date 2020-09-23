package com.ego.user.Api;

import com.ego.user.pojo.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

/**
 * @author YangYao
 * @date 2020/9/20 21:42
 * @Description
 */
public interface UserApi {
    @GetMapping("check/{data}/{type}")
    ResponseEntity<Boolean> check(@PathVariable("data")String data, @PathVariable("type")Integer type);

    @PostMapping("send")
    ResponseEntity<Boolean> send(@RequestParam("phone") String phone);

    @GetMapping("query")
    ResponseEntity<User> queryUser(
            @RequestParam("username") String username,
            @RequestParam("password") String password
    );
}
