package com.ego.sms.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author YangYao
 * @date 2020/9/20 19:07
 * @Description
 */
@Data
@ConfigurationProperties(prefix = "ego.sms")
public class SmsProperties {
    private String accesskeyId;
    private String accesskeySecret;
    private String signName;
    private String verifyCodeTemplate;
}
