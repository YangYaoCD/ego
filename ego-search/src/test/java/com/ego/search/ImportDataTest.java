package com.ego.search;

import com.ego.common.pojo.PageResult;
import com.ego.item.Bo.SpuBo;
import com.ego.search.client.GoodsClient;
import com.ego.search.dao.GoodsRepository;
import com.ego.search.pojo.Goods;
import com.ego.search.service.SearchService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author YangYao
 * @date 2020/9/15 15:00
 * @Description
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ImportDataTest {
    @Autowired//feign
    private GoodsClient goodsClient;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    @Autowired
    private GoodsRepository goodsRepository;
    @Autowired
    private SearchService searchService;

    @Test
    public void createIndex(){
        elasticsearchTemplate.createIndex(Goods.class);
        elasticsearchTemplate.putMapping(Goods.class);
    }

    @Test
    public void testImportData(){
        //将mysql的所有在售商品数据全导出到es中
        int size;
        int page=1;
        do {
            //分批次导入数据
            PageResult<SpuBo> result=goodsClient.page("",true,page++,10).getBody();
            List<SpuBo> items = result.getItems();
            size=items.size();

            ArrayList<Goods> goodsList = new ArrayList<>();
            //保存数据
            items.forEach(spuBo -> {
                //spuBo=>goods
                Goods goods=searchService.buildGoods(spuBo);
                goodsList.add(goods);
            });

            goodsRepository.saveAll(goodsList);
        }while (size==10);
    }

}
