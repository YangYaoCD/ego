package com.ego.gateway.properties;

import com.ego.auth.utils.RsaUtils;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;
import java.io.File;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @author YangYao
 * @date 2020/9/20 21:32
 * @Description
 */
@Data
@ConfigurationProperties(prefix = "ego.jwt")
public class JwtProperties {
    private String pubKeyPath;// 公钥
    private PublicKey publicKey; // 公钥
    private String cookieName; //cookie名字

    public JwtProperties() {
    }

    @PostConstruct
    public void init() {
        try {
            publicKey=RsaUtils.getPublicKey(pubKeyPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
