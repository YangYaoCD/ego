package com.ego.item.service;

import com.ego.common.pojo.PageResult;
import com.ego.item.mapper.BrandMapper;
import com.ego.item.pojo.Brand;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author YangYao
 * @date 2020/9/13 11:31
 * @Description
 */
@Service
public class BrandService {
    @Resource
    private BrandMapper brandMapper;

    @Transactional(readOnly = true)
    public PageResult<Brand> page(Integer page,Integer pageSize,Boolean descending,String sortBy,String key){
        //分页助手分页
        PageHelper.startPage(page,pageSize);

        Example example = new Example(Brand.class);
        Example.Criteria criteria = example.createCriteria();

        //查询条件
        if (StringUtils.isNotBlank(key)){
            criteria.andLike("name","%+key+%").orEqualTo("letter",key);
        }

        //排序
        if (StringUtils.isNotBlank(sortBy)){
            example.setOrderByClause(sortBy+(descending==false?"":" desc"));
        }
        Page<Brand> pageInfo = (Page<Brand>) brandMapper.selectByExample(example);

        return new PageResult<>(pageInfo.getTotal(),pageInfo.getResult());
    }

    @Transactional
    public void save(Brand brand, List<Long> cids) {
        //保存品牌
        brandMapper.insertSelective(brand);
        //保存品牌和类别的中间关系
        if (cids!=null||cids.size()!=0){
            for (Long cid : cids) {
                brandMapper.insertBrandAndCategory(cid,brand.getId());
            }
        }
    }

    public List<Brand> queryListByCid(Long cid) {
        List<Brand> brandsList=brandMapper.queryListByCid(cid);
        return brandsList;
    }

    public Brand queryBrandByBid(Long bid) {
        return brandMapper.selectByPrimaryKey(bid);
    }

    public List<Brand> queryListByIds(List<Long> ids) {
        return brandMapper.selectByIdList(ids);
    }
}
