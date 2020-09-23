package com.ego.item.Bo;

import com.ego.item.pojo.Sku;
import com.ego.item.pojo.Spu;
import com.ego.item.pojo.SpuDetail;
import lombok.Data;

import java.util.List;

/**
 * @author YangYao
 * @date 2020/9/14 16:44
 * @Description
 */
@Data
public class SpuBo extends Spu {
    private String categoryName;
    private String brandName;

    private List<Sku> skus;
    private SpuDetail spuDetail;
}
