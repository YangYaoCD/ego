package com.ego.sms.listener;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.ego.sms.utils.SmsUtil;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author YangYao
 * @date 2020/9/20 16:51
 * @Description
 */
@Component
public class SmsListener {
    @Autowired
    private SmsUtil smsUtil;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "ego.sms.queue", durable = "true"),
            exchange = @Exchange(value = "ego.sms.exchange",
                    ignoreDeclarationExceptions = "true"),
            key = {"sms.verify.code"}))
    public void listenSms(Map<String, String> msg) throws Exception {
        String phone = msg.get("phone");
        String code = msg.get("code");

        //调用阿里大于短信平台发送
        System.out.println("正在发送短信。。。"+code);
        try {
            SendSmsResponse sendSmsResponse = smsUtil.sendSms(phone, code);
        } catch (ClientException e) {
            e.printStackTrace();
        }

    }
}