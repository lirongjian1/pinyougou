package com.pinyougou.mapper;

import com.pinyougou.pojo.Brand;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface BrandMapper {
    //第一天 查询所有品牌
    @Select("select * from tb_brand")
    List<Brand> findAll();
}
