package com.ego.search.controller;

import com.ego.common.pojo.PageResult;
import com.ego.search.bo.SearchRequest;
import com.ego.search.pojo.Goods;
import com.ego.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author YangYao
 * @date 2020/9/15 21:51
 * @Description
 */
@RestController
//@RequestMapping("search")
public class SearchController {
    @Autowired
    private SearchService searchService;

//    http://api.ego.com/api/search/page
    @PostMapping("page")
    public ResponseEntity<PageResult<Goods>> page(@RequestBody SearchRequest searchRequest){
        PageResult<Goods> result=searchService.page(searchRequest);
        if (result.getItems().size()==0||result==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }
}
