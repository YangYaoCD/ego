package com.ego.goods.listener;

import com.ego.goods.service.GoodsService;
import com.netflix.discovery.converters.Auto;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author YangYao
 * @date 2020/9/19 21:37
 * @Description
 */
@Component
public class GoodsListener {
    @Autowired
    private GoodsService goodsService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "ego.create.web_index.queue",durable = "true"),
            exchange = @Exchange(
                    value = "ego.item.exchange",
                    ignoreDeclarationExceptions = "true",
                    type = ExchangeTypes.TOPIC),
            key = {"item.insert","item.update"}))
    public void listenCreate(Long id)throws Exception{
        Map<String, Object> modelMap = goodsService.loadModel(id);
        //异步生成静态页面
        goodsService.buildStaticHtml(modelMap,id);
    }
}
