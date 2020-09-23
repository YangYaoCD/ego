package com.ego.item.mapper;

import com.ego.item.pojo.Brand;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.additional.idlist.SelectByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author YangYao
 * @date 2020/9/13 11:34
 * @Description
 */
@org.apache.ibatis.annotations.Mapper
public interface BrandMapper extends Mapper<Brand>, SelectByIdListMapper<Brand,Long> {
    @Insert("insert into tb_category_brand(brand_id,category_id) values(#{bid},#{cid})")
    void insertBrandAndCategory(@Param("cid") Long cid, @Param("bid") Long bid);

    @Select("select b.id,b.name,b.image,b.letter from tb_category_brand cb join tb_brand b on cb.brand_id=b.id where cb.category_id=#{cid}")
    List<Brand> queryListByCid(@Param("cid") Long cid);

}
