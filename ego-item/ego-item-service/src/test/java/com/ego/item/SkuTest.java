package com.ego.item;

import com.ego.item.mapper.SkuMapper;
import com.ego.item.pojo.Sku;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.List;

/**
 * @author YangYao
 * @date 2020/9/18 10:05
 * @Description
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EgoItemService.class)
public class SkuTest {
    @Autowired
    private SkuMapper skuMapper;

    @Test
    public void qury(){
        Example example1 = new Example(Sku.class);
        Example.Criteria criteria = example1.createCriteria().andEqualTo("spuId", 11);
//        example.setSpuId(spuId);
        List<Sku> skuList = skuMapper.selectByExample(example1);
        System.out.println(skuList.size());
    }
}
