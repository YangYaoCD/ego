package com.ego.auth;

import com.ego.auth.entity.UserInfo;
import com.ego.auth.utils.JwtUtils;
import com.ego.auth.utils.RsaUtils;
import org.junit.Before;
import org.junit.Test;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @author YangYao
 * @date 2020/9/20 21:23
 * @Description
 */
public class JwtTest {
    private static final String pubKeyPath = "D:\\Desktop\\Study_Material\\java\\tmp\\rsa\\rsa.pub";
    private static final String priKeyPath = "D:\\Desktop\\Study_Material\\java\\tmp\\rsa\\rsa.pri";
    private PublicKey publicKey;
    private PrivateKey privateKey;
    /**
     * 注释@Before代码运行
     * @throws Exception
     */
    @Test
    public void testRsa() throws Exception {
        RsaUtils.generateKey(pubKeyPath, priKeyPath, "234");
    }
//    @Before
    public void testGetRsa() throws Exception {
        this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
        this.privateKey = RsaUtils.getPrivateKey(priKeyPath);
    }
    @Test
    public void testGenerateToken() throws Exception {
// 生成token
        String token = JwtUtils.generateToken(new UserInfo(20L, "jack"), privateKey, 5);
        System.out.println("token = " + token);
    }
    @Test
    public void testParseToken() throws Exception {
        String token =
                "eyJhbGciOiJSUzI1NiJ9.eyJpZCI6MjAsInVzZXJuYW1lIjoiamFjayIsImV4cCI6MTUzMzI4MjQ3N30.EPo35Vyg1 IwZAtXvAx2TCWuOPnRwPclRNAM4ody5CHk8RF55wdfKKJxjeGh4H3zgruRed9mEOQzWy79iF1nGAnvbkraGlD6iM9zDW8M1G9if4MX579Mv1x57lFewzEo-zKnPdFJgGlAPtNWDPv4iKvbKOk1-U7NUtRmMsF1Wcg";
// 解析token
        UserInfo user = JwtUtils.getInfoFromToken(token, publicKey);
        System.out.println("id: " + user.getId());
        System.out.println("userName: " + user.getUsername());
    }
}
