package com.ego.gateway.filter;

import com.ego.auth.entity.UserInfo;
import com.ego.auth.utils.JwtUtils;
import com.ego.gateway.properties.FilterProperties;
import com.ego.gateway.properties.JwtProperties;
import com.netflix.discovery.converters.Auto;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import utils.CookieUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**鉴权过滤器
 * @author YangYao
 * @date 2020/9/21 18:34
 * @Description
 */
@Component
@EnableConfigurationProperties({FilterProperties.class, JwtProperties.class})
public class AuthFilter extends ZuulFilter {
    @Autowired
    private FilterProperties filterProperties;
    @Autowired
    private JwtProperties jwtProperties;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        //判断是否过滤
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();

        // api/auth/verify    /api/auth
        String requestURL = request.getRequestURI();
        boolean result = filterProperties.getAllowPaths().stream().anyMatch(path -> requestURL.startsWith(path));
        return !result;
    }

    @Override
    public Object run() throws ZuulException {
        //鉴权
        //判断token是否正确
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        HttpServletResponse response = context.getResponse();
        String token = CookieUtils.getCookieValue(request, jwtProperties.getCookieName());
        try {
            JwtUtils.getInfoFromToken(token, jwtProperties.getPublicKey());
        } catch (Exception e) {
            e.printStackTrace();
            //不正确 -》提示没有权限
            context.setSendZuulResponse(false);//停止访问微服务
//            CookieUtils.deleteCookie(request,response,jwtProperties.getCookieName());
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        }
        //正确-》放行
            //不正确-》提示没有权限
        return null;
    }
}
