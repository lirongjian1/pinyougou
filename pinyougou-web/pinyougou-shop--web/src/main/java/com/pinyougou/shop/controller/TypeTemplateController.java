package com.pinyougou.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TypeTemplate;
import com.pinyougou.service.TypeTemplateService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @autor lrj
 * @date 2020/4/12 0012 0:44
 */
@RestController
@RequestMapping("typeTemplate")
public class TypeTemplateController {

    @Reference
    private TypeTemplateService typeTemplateService;


    //根据id查询类型模板
    @GetMapping("findOne")
    public TypeTemplate findItemCatByParentId(Long id){
        try {
            return typeTemplateService.findOne(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //根据类型模板id查询对应的规格 以及规格选项 用List<Map>封装
    @GetMapping("/findSpecByTemplateId")
    public List<Map> findSpecByTemplateId(Integer id){
        try {
            return typeTemplateService.findSpecByTemplateId(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
