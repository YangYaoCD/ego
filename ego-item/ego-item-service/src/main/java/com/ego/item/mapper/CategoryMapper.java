package com.ego.item.mapper;

import com.ego.item.pojo.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author YangYao
 * @date 2020/9/13 10:01
 * @Description
 */
@Mapper
public interface CategoryMapper extends tk.mybatis.mapper.common.Mapper<Category> {
    @Select("select c.id,c.name,c.parent_id,c.is_parent,c.sort from tb_category c join tb_category_brand cb on c.id=cb.category_id where cb.brand_id=#{bid}")
    List<Category> queryCategoryListByBid(@Param("bid") Long bid);

}
