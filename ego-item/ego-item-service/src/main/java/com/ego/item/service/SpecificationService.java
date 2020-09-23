package com.ego.item.service;

import com.ego.item.mapper.SpecificationMapper;
import com.ego.item.pojo.Specification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author YangYao
 * @date 2020/9/14 16:03
 * @Description
 */
@Service
public class SpecificationService {
    @Resource
    private SpecificationMapper specificationMapper;
    public Specification queryById(Long id) {
        return this.specificationMapper.selectByPrimaryKey(id);
    }

    public void update(Specification specification) {
        specificationMapper.updateByPrimaryKey(specification);
    }
}
