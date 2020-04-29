package com.pinyougou.mapper;

import tk.mybatis.mapper.common.Mapper;

import com.pinyougou.pojo.Content;

import java.util.List;

/**
 * ContentMapper 数据访问接口
 * @date 2020-03-23 19:12:51
 * @version 1.0
 */
public interface ContentMapper extends Mapper<Content>{


    List<Content> findContentByCategoryId(Long categoryId);
}