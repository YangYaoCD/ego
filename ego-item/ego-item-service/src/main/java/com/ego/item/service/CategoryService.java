package com.ego.item.service;

import com.ego.item.mapper.CategoryMapper;
import com.ego.item.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.sql.rowset.Predicate;
import java.util.List;

/**
 * @author YangYao
 * @date 2020/9/13 9:59
 * @Description
 */
@Service
public class CategoryService {
    @Resource
    private CategoryMapper categoryMapper;

    public List<Category> findByParentId(Long parentId) {
        Category category = new Category();
        category.setParentId(parentId);
        return categoryMapper.select(category);
    }

    public List<Category> queryCategoryListByBid(Long bid) {

        return categoryMapper.queryCategoryListByBid(bid);
    }

    public List<Category> getListByCids(List<Long> cids) {
        Example example = new Example(Category.class);
        Example.Criteria criteria = example.createCriteria().andIn("id",cids);
        return categoryMapper.selectByExample(example);
    }

    public List<Category> queryCategegoryListByBid(Long bid) {

        return categoryMapper.queryCategoryListByBid(bid);
    }


}
