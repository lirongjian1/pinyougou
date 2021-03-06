package com.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.common.pojo.PageResult;
import com.pinyougou.manager.util.StringUtil;
import com.pinyougou.pojo.Specification;
import com.pinyougou.pojo.SpecificationOption;
import com.pinyougou.service.SpecificationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("specification")
public class SpecificationConrtoller {

    @Reference(timeout = 10000)
    private SpecificationService specificationService;

    //查询所有规格
    @GetMapping("findAll")
    public List<Map<String, Object>> findSpecification() {
        return specificationService.findSpecification();
    }

    @GetMapping("findByPage")
    public PageResult findByPage(Specification specification, Integer page, Integer rows) {
        //GET 请求中文转码
        if (specification != null) {
            specification.setSpecName(StringUtil.iSO8859ToUTF8(specification.getSpecName()));
        }
        return specificationService.findByPage(specification, page, rows);
    }

    //day3 添加规格
    @PostMapping("save")
    public boolean save(@RequestBody Specification specification) {
        try {
            specificationService.save(specification);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //根据规格id查询规格选项
    @GetMapping("findSpecOption")
    public List<SpecificationOption> findSpecOption(@RequestParam("id") long id) {
        return specificationService.findSpecOption(id);
    }

    //修改规格
    @PostMapping("update")
    public boolean update(@RequestBody Specification specification) {
        try {
            specificationService.update(specification);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //批量删除
    @GetMapping("delete")
    public boolean delete(Integer[] ids) {
        try {
            specificationService.deleteAll(ids);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
