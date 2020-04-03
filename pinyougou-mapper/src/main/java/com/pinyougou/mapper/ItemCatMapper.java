package com.pinyougou.mapper;

import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import com.pinyougou.pojo.ItemCat;

import java.io.Serializable;
import java.util.List;

/**
 * ItemCatMapper 数据访问接口
 * @date 2020-03-23 19:12:51
 * @version 1.0
 */
public interface ItemCatMapper extends Mapper<ItemCat>{


    //分类查询
    @Select("select * from tb_item_cat where parent_id=#{parentId}")
    List<ItemCat> findModule(Integer parentId);

    void deleteAll(Serializable[] ids);
}