package com.pinyougou.mapper;

import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import com.pinyougou.pojo.Goods;

import java.util.List;
import java.util.Map;

/**
 * GoodsMapper 数据访问接口
 * @date 2020-03-23 19:12:51
 * @version 1.0
 */
public interface GoodsMapper extends Mapper<Goods>{


    List<Map<String,Object>> findAll(Goods goods);

    void updateStatus(@Param("columnName") String columnName,@Param("ids") Long[] ids, @Param("status") Integer status);
////商品上下架
    void updateMarketable(@Param("ids") Long[] ids,@Param("marketable") String marketable);
}