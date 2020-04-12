package com.pinyougou.mapper;

import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import com.pinyougou.pojo.Seller;

import java.util.List;

/**
 * SellerMapper 数据访问接口
 * @date 2020-03-23 19:12:51
 * @version 1.0
 */
public interface SellerMapper extends Mapper<Seller>{



    List<Seller> findByPage(Seller seller);

    //根据id查询注册了的公司
    @Select("select * from tb_seller where seller_id = #{username}")
    Seller findById(String username);
}