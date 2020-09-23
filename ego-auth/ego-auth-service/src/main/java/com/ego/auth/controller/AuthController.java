package com.ego.auth.controller;

import com.ego.auth.client.UserClient;
import com.ego.auth.config.JwtProperties;
import com.ego.auth.entity.UserInfo;
import com.ego.auth.utils.JwtUtils;
import com.ego.auth.utils.RsaUtils;
import com.ego.user.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import utils.CookieUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author YangYao
 * @date 2020/9/20 21:39
 * @Description
 */
@RestController
@EnableConfigurationProperties(JwtProperties.class)
public class AuthController {
    @Autowired
    private UserClient userClient;
    @Autowired
    private JwtProperties jwtProperties;

    @PostMapping("login")
    public ResponseEntity<Void> login(@RequestParam("username") String username,
                                      @RequestParam("password") String password,
                                      HttpServletResponse response,
                                      HttpServletRequest request) {
        //判断用户是否存在
        User user = userClient.queryUser(username, password).getBody();
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        //生成令牌
        UserInfo userInfo = new UserInfo(user.getId(), user.getUsername());
        try {
            String token = JwtUtils.generateToken(userInfo, jwtProperties.getPrivateKey(), jwtProperties.getExpire());
            //令牌放入Cookie
//            Cookie cookie = new Cookie(jwtProperties.getCookieName(), token);
//            cookie.setMaxAge(jwtProperties.getCookieMaxAge());
//            cookie.setDomain("www.ego.com");
            CookieUtils.setCookie(request, response, jwtProperties.getCookieName(),
                    token, jwtProperties.getCookieMaxAge(), true);
//            response.addCookie(cookie);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("verify")
    public ResponseEntity<UserInfo> verify(HttpServletRequest request,HttpServletResponse response){
        String token = CookieUtils.getCookieValue(request, jwtProperties.getCookieName());
        if (token==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        try {
            UserInfo userInfo = JwtUtils.getInfoFromToken(token, jwtProperties.getPublicKey());

            //刷新token，只要在玩30分钟就不会过期
            token = JwtUtils.generateToken(userInfo, jwtProperties.getPrivateKey(), jwtProperties.getExpire());
            CookieUtils.setCookie(request, response, jwtProperties.getCookieName(),
                    token, jwtProperties.getCookieMaxAge(), true);

            return ResponseEntity.ok(userInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
