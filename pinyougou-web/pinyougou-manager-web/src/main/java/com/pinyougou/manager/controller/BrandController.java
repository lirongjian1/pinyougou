package com.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.common.pojo.PageResult;
import com.pinyougou.manager.util.StringUtil;
import com.pinyougou.pojo.Brand;
import com.pinyougou.service.BrandService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/brand")
public class BrandController {
    /**
     * 引用服务接口代理对象
     * timeout: 调用服务接口方法超时时间毫秒数
     */
    @Reference(timeout = 10000)
    private BrandService brandService;

    //  查询所有品牌只需要返回两类数据{ id:1,text:"手机"} 所有用map封装
    @GetMapping("/findAll")
    public List<Map<String, Object>> findAll() {
        return brandService.findAll();
    }

    //day3 删除多条品牌数据
    @GetMapping("/delete")
    public boolean delete(Integer[] ids) {
        try {
            brandService.deleteAll(ids);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //day3 多条件分页查询所有品牌 StringUtils 是lang3里面的类
    @GetMapping("/findByPage")
    public PageResult findByPage(Brand brand, Integer page, Integer rows) {
        //GET 请求中文转码
        if (brand != null) {
            brand.setName(StringUtil.iSO8859ToUTF8(brand.getName()));
        }
        return brandService.findByPage(brand, page, rows);
    }

    //day2 添加品牌
    @PostMapping("/save")
    public Boolean save(@RequestBody Brand brand) {
        try {
            brandService.save(brand);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //day2 修改品牌
    @PostMapping("/update")
    public Boolean update(@RequestBody Brand brand) {
        try {
            brandService.update(brand);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //day2 修改品牌
    @GetMapping("/findByName")
    @ResponseBody
    public List<Brand> findByName(@RequestParam("name") String name) {
        //GET 请求中文转码
        if (name != null) {
             name = StringUtil.iSO8859ToUTF8(name);
        }
          return  brandService.findByName(name);
    }


}
