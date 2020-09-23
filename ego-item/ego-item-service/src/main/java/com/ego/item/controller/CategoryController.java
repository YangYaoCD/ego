package com.ego.item.controller;

import com.ego.item.pojo.Category;
import com.ego.item.service.CategoryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author YangYao
 * @date 2020/9/13 9:45
 * @Description http://api.ego.com/api/item/category/list?pid=0
 */
@RestController
@RequestMapping("category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("list")
    public ResponseEntity<List<Category>> list(@RequestParam(value = "pid", required = true) Long parentId) {
        List<Category> result = categoryService.findByParentId(parentId);
        if (result == null || result.size() == 0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("bid/{bid}")
    public ResponseEntity<List<Category>> queryCategoryListByBid(@PathVariable(value = "bid", required = true) Long bid) {
        List<Category> result=categoryService.queryCategoryListByBid(bid);
        if (result==null||result.size()==0){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/cnames")
    public ResponseEntity<String> queryNamesByCids(@RequestParam("cids") List<Long> cids){
        List<Category> result = categoryService.getListByCids(cids);
        List<String> cnameList = result.stream().map(category -> category.getName()).collect(Collectors.toList());
        if (result == null || result.size() == 0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(StringUtils.join(cnameList,","));
    }

    @GetMapping("/list/cid")
    public ResponseEntity<List<Category>> queryCategoryByIds(@RequestParam("cids") List<Long> ids) {
        List<Category> result=categoryService.getListByCids(ids);
        if (result == null || result.size() == 0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }
}
