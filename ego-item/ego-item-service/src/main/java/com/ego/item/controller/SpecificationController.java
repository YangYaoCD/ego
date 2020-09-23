package com.ego.item.controller;

import com.ego.item.pojo.Specification;
import com.ego.item.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * @author YangYao
 * @date 2020/9/14 16:04
 * @Description
 */
@RestController
@RequestMapping("spec")
public class SpecificationController {
    @Autowired
    private SpecificationService specificationService;
    @GetMapping("{id}")
    public ResponseEntity<String> querySpecificationByCategoryId(@PathVariable("id") Long id)
    {
        Specification spec = this.specificationService.queryById(id);
        if (spec == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(spec.getSpecifications());
    }
    @PutMapping
    @Transactional
    public ResponseEntity<Void> update(Specification specification){
        specificationService.update(specification);
        return ResponseEntity.ok(null);
    }
}
