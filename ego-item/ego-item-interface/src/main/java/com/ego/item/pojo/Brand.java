package com.ego.item.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author YangYao
 * @date 2020/9/13 11:29
 * @Description
 */
@Table(name = "tb_brand")
@Data
public class Brand {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;
    private String name;// 品牌名称
    private String image;// 品牌图片
    private Character letter;
}
