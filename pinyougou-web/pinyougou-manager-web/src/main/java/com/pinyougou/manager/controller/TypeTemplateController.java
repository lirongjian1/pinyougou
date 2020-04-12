package com.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.common.pojo.PageResult;
import com.pinyougou.manager.util.StringUtil;
import com.pinyougou.pojo.TypeTemplate;
import com.pinyougou.service.TypeTemplateService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("typeTemplate")
public class TypeTemplateController {

    @Reference(timeout = 10000)
    private TypeTemplateService typeTemplateService;




    //查询模板表格 要获取它的id和name 要根据id添加分类商品 name显示给用看
    @GetMapping("findTypeTemplateList")
    public List<Map<String,Object>> findTypeTemplateList(){
        return typeTemplateService.findTypeTemplateList();
    }

    @GetMapping("findByPage")
    public PageResult findByPage(TypeTemplate typeTemplate, Integer page, Integer rows){
        //GET 请求中文转码
        if (typeTemplate != null) {
            typeTemplate.setName(StringUtil.iSO8859ToUTF8(typeTemplate.getName()));
        }
        return typeTemplateService.findByPage(typeTemplate,page,rows);

    }
    //新增数据
    @PostMapping("save")
    public boolean save(@RequestBody TypeTemplate typeTemplate){
        try {
            typeTemplateService.save(typeTemplate);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //修改数据
    @PostMapping("update")
    public boolean update(@RequestBody TypeTemplate typeTemplate){
        try {
            typeTemplateService.save(typeTemplate);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @GetMapping("delete")
    public boolean deleteAll(Integer[] ids){
        try {
           typeTemplateService.deleteAll(ids);
           return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
