package com.pinyougou.mapper;

import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import com.pinyougou.pojo.Brand;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * BrandMapper 数据访问接口
 * @date 2020-03-23 19:12:51
 * @version 1.0
 */
public interface BrandMapper extends Mapper<Brand>{

  //day3 多条件查询品牌
  List<Brand> findAll(Brand brand);

  //day3 批量删除
  void deleteAll(Serializable[] ids);

  //查询所有品牌只需要返回两类数据{ id:1,text:"手机"} 所有用map封装
  @Select(" select id,name as text from tb_brand")
    List<Map<String,Object>> findBrand();
}