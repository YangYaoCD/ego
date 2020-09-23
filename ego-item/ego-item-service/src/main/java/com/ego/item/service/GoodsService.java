package com.ego.item.service;

import com.ego.common.enums.ExceptionEnum;
import com.ego.common.exception.PayException;
import com.ego.common.pojo.CartDto;
import com.ego.common.pojo.PageResult;
import com.ego.item.Bo.SpuBo;
import com.ego.item.mapper.*;
import com.ego.item.pojo.*;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Sqls;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author YangYao
 * @date 2020/9/14 16:52
 * @Description
 */
@Service
public class GoodsService {
    @Resource
    private SpuMapper spuMapper;
    @Resource
    private BrandMapper brandMapper;
    @Autowired
    private CategoryService categoryService;
    @Resource
    private SpuDetailsMapper spuDetailsMapper;
    @Resource
    private SkuMapper skuMapper;
    @Resource
    private StockMapper stockMapper;
    @Autowired
    private AmqpTemplate amqpTemplate;

    public PageResult<SpuBo> page(String key, Boolean saleable, Integer page, Integer rows) {
        PageHelper.startPage(page, rows);

        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
        if (!StringUtils.isBlank(key)) {
            criteria.andLike("title", "%" + key + "%").orLike("subTitle", "%" + key + "%");
        }
        if (saleable != null) {
            criteria.andEqualTo("saleable", saleable);
        }
        Page<Spu> pageInfo = (Page<Spu>) spuMapper.selectByExample(example);

        //将Spu转SpuBo
        List<SpuBo> spuBoList = pageInfo.stream().map(spu -> {
            SpuBo spuBo = new SpuBo();
            BeanUtils.copyProperties(spu, spuBo);
            spuBo.setBrandName(brandMapper.selectByPrimaryKey(spu.getBrandId()).getName());
            List<Category> categoryList = categoryService.getListByCids(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()));
            List<String> names = categoryList.stream().map(category -> category.getName()).collect(Collectors.toList());
            spuBo.setCategoryName(StringUtils.join(names, "/"));
            return spuBo;
        }).collect(Collectors.toList());
        return new PageResult<SpuBo>(Long.valueOf(spuBoList.size()), spuBoList);
    }

    public void save(SpuBo spuBo) {
        //保存spu & detail
        spuBo.setSaleable(true);
        spuBo.setValid(true);
        spuBo.setCreateTime(new Date());
        spuBo.setLastUpdateTime(spuBo.getCreateTime());
        spuMapper.insertSelective(spuBo);

        spuBo.getSpuDetail().setSpuId(spuBo.getId());
        spuDetailsMapper.insertSelective(spuBo.getSpuDetail());

        //保存sku & stock
        if (spuBo.getSkus() != null) {
            spuBo.getSkus().forEach(sku -> {
                //设置关系
                sku.setSpuId(spuBo.getId());
                sku.setCreateTime(new Date());
                sku.setLastUpdateTime(sku.getCreateTime());
                skuMapper.insertSelective(sku);

                Stock stock = sku.getStock();
                stock.setSkuId(sku.getId());
                stockMapper.insertSelective(stock);
            });
        }

        //发送消息到交换机(通知其他微服务)
        amqpTemplate.convertAndSend("item.insert",spuBo.getId());
    }

    public List<Sku> querySkuListBySpuId(Long spuId) {
        Sku sku = new Sku();
        sku.setSpuId(spuId);
        return skuMapper.select(sku);
    }

    public SpuDetail querySpuDetailBySpuId(Long spuId) {
        return spuDetailsMapper.selectByPrimaryKey(spuId);
    }

    public SpuBo queryGoodsById(Long spuId) {
        SpuBo result = new SpuBo();
        //查询spu、spuDeatail、sku
        Spu spu = spuMapper.selectByPrimaryKey(spuId);
        BeanUtils.copyProperties(spu, result);
        SpuDetail spuDetail = spuDetailsMapper.selectByPrimaryKey(spuId);
        result.setSpuDetail(spuDetail);

//        Sku example = new Sku();
        Example example1 = new Example(Sku.class);
        Example.Criteria criteria = example1.createCriteria().andEqualTo("spuId", spuId);
//        example.setSpuId(spuId);
        List<Sku> skuList = skuMapper.selectByExample(example1);
        skuList.forEach(sku -> {
            Stock stock = stockMapper.selectByPrimaryKey(sku.getId());
            sku.setStock(stock);
        });
        result.setSkus(skuList);
        return result;
    }

    public Sku querySkuBySkuId(Long skuId) {
        return skuMapper.selectByPrimaryKey(skuId);
    }

    @Transactional
    public void decreaseStock(List<CartDto> cartDtos) {
        for (CartDto cartDto : cartDtos) {
            int count=stockMapper.decreaseStock(cartDto.getSkuId(),cartDto.getNum());
            if (count!=1){
                throw new PayException(ExceptionEnum.STOCK_NOT_ENOUGH);
            }
        }
    }

    @Transactional
    public void decreaseSeckillStock(CartDto cartDto) {
        int count = stockMapper.decreaseSeckillStock(cartDto.getSkuId(), cartDto.getNum());
        if (count != 1) {
            throw new PayException(ExceptionEnum.STOCK_NOT_ENOUGH);
        }
    }
}