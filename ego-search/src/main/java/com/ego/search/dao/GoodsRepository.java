package com.ego.search.dao;

import com.ego.search.pojo.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author YangYao
 * @date 2020/9/15 15:52
 * @Description
 */
public interface GoodsRepository extends ElasticsearchRepository<Goods,Long> {
}
