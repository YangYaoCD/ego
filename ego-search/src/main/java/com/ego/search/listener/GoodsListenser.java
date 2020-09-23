package com.ego.search.listener;

import com.ego.search.service.SearchService;
import com.netflix.discovery.converters.Auto;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author YangYao
 * @date 2020/9/19 20:01
 * @Description
 */
@Component
public class GoodsListenser {
    @Autowired
    private SearchService searchService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "ego.create.index.queue",durable = "true"),
            exchange = @Exchange(
                    value = "ego.item.exchange",
                    ignoreDeclarationExceptions = "true",
                    type = ExchangeTypes.TOPIC),
            key = {"item.insert","item.update"}))
    public void listenCreate(Long id)throws Exception{
        if (id==null){
            return;
        }
        //创建或更新索引
        this.searchService.createIndex(id);
    }
}
