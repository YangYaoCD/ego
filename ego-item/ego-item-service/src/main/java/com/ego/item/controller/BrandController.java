package com.ego.item.controller;

import com.ego.common.pojo.PageResult;
import com.ego.item.pojo.Brand;
import com.ego.item.pojo.Category;
import com.ego.item.service.BrandService;
import com.ego.item.service.CategoryService;
import org.jboss.logging.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author YangYao
 * @date 2020/9/13 11:14
 * @Description
 */
@RestController
@RequestMapping("brand")
public class BrandController {
    @Autowired
    private BrandService brandService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("page")
    public ResponseEntity<PageResult<Brand>> page(@RequestParam(value = "pageNo", defaultValue = "1") Integer page,
                                                  @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                  @RequestParam(value = "descending", defaultValue = "true") Boolean descending,
                                                  @RequestParam(value = "sortBy") String sortBy,
                                                  @RequestParam(value = "key") String key
    ) {

        PageResult<Brand> result = brandService.page(page, pageSize, descending, sortBy, key);
        if (result == null || result.getItems().size() == 0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping()
    public ResponseEntity<Void> save(Brand brand, @RequestParam("cids") List<Long> cids) {
        brandService.save(brand, cids);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("cid/{cid}")
    public ResponseEntity<List<Brand>> queryListByCid(@PathVariable("cid") Long cid) {
        List<Brand> result = brandService.queryListByCid(cid);
        if (result.size() == 0 || result == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

//    @GetMapping("/bid/{bid}")
//    public ResponseEntity<List<Category>> queryCategegoryListByBid(@PathVariable(value = "bid") Long bid) {
//        List<Category> result = categoryService.queryCategegoryListByBid(bid);
//        if (result == null || result.size() == 0) {
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.ok(result);
//    }

    @GetMapping("/bid/{bid}")
    public ResponseEntity<Brand> queryBrandByBid(@PathVariable("bid") Long bid){
        Brand result = brandService.queryBrandByBid(bid);
        if(result==null)
        {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Brand>> queryListByIds(@RequestParam("ids")List<Long> ids){
        List<Brand> result = brandService.queryListByIds(ids);
        if(result==null)
        {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }
}
