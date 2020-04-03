package com.pinyougou.mapper;

import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import com.pinyougou.pojo.Specification;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * SpecificationMapper 数据访问接口
 * @date 2020-03-23 19:12:51
 * @version 1.0
 */
public interface SpecificationMapper extends Mapper<Specification>{


    List<Specification> findAll(Specification specification);

    //添加规格选项
    void save(Specification specification);

    void deleteAll(Serializable[] ids);

    //查询所有规格
    @Select("select id,spec_name as text from tb_specification")
    List<Map<String,Object>> findSpecification();
}