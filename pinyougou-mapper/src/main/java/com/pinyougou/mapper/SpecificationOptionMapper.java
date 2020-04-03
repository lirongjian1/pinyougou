package com.pinyougou.mapper;

import com.pinyougou.pojo.Specification;
import tk.mybatis.mapper.common.Mapper;

import com.pinyougou.pojo.SpecificationOption;

import java.io.Serializable;

/**
 * SpecificationOptionMapper 数据访问接口
 * @date 2020-03-23 19:12:51
 * @version 1.0
 */
public interface SpecificationOptionMapper extends Mapper<SpecificationOption>{


     void save(Specification specification);

    void deleteAll(Serializable[] ids);
}