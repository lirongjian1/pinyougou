package com.pinyougou.mapper;

import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import com.pinyougou.pojo.TypeTemplate;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * TypeTemplateMapper 数据访问接口
 * @date 2020-03-23 19:12:51
 * @version 1.0
 */
public interface TypeTemplateMapper extends Mapper<TypeTemplate>{



    List<TypeTemplate> findAll(TypeTemplate typeTemplate);

    void deleteAll(Serializable[] ids);

    @Select("select id,name from tb_type_template order by id asc")
    List<Map<String,Object>> findTypeTemplateList();
}