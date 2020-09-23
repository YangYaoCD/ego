package com.ego.item.api;

import com.ego.common.pojo.PageResult;
import com.ego.item.pojo.Brand;
import com.ego.item.pojo.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/spec")
public interface SpecApi {
    @GetMapping("{id}")
    ResponseEntity<String> querySpecificationByCategoryId(@PathVariable("id") Long id);

    @PutMapping
    ResponseEntity<Void> update(Specification specification);

}
