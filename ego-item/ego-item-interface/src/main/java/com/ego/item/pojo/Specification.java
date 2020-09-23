package com.ego.item.pojo;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author YangYao
 * @date 2020/9/14 16:00
 * @Description 规格参数
 */
@Data
@Table(name = "tb_specification")
public class Specification {
    @Id
    private Long categoryId;
    private String specifications;
}
