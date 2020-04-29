package com.pinyougou.mapper;

import tk.mybatis.mapper.common.Mapper;

import com.pinyougou.pojo.Item;

import java.util.List;

/**
 * ItemMapper 数据访问接口
 * @date 2020-03-23 19:12:51
 * @version 1.0
 */
public interface ItemMapper extends Mapper<Item>{


    List<Item> findAll(String s);
    //根据goodsId 查询item表对应的数据有多条
    List<Item> findItemListByGoodsId(Long goodsId);
}