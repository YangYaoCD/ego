package com.ego.cart.interceptor;

import com.ego.auth.entity.UserInfo;
import com.ego.auth.utils.JwtUtils;
import com.ego.cart.config.JwtProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import utils.CookieUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author YangYao
 * @date 2020/9/22 10:22
 * @Description
 */
@Component
@EnableConfigurationProperties(JwtProperties.class)
public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private JwtProperties jwtProperties;

    private static ThreadLocal<UserInfo> threadLocal=new ThreadLocal<>();

    public static UserInfo getUser() {
        return threadLocal.get();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取用户信息
        String token = CookieUtils.getCookieValue(request, jwtProperties.getCookieName());
        try {
            UserInfo userInfo = JwtUtils.getInfoFromToken(token, jwtProperties.getPublicKey());
            //将用户信息存当前线程中
            threadLocal.set(userInfo);
        } catch (Exception e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }
        return true;
    }
}
