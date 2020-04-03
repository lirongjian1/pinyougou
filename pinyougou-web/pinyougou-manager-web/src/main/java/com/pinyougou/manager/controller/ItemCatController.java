package com.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.ItemCat;
import com.pinyougou.service.ItemCatService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("itemCat")
public class ItemCatController {

    @Reference(timeout = 10000)
    private ItemCatService itemCatService;

    //添加商品分类
    @PostMapping("save")
    public boolean save(@RequestBody ItemCat itemCat){
        try {
             itemCatService.save(itemCat);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //修改商品
    @PostMapping("update")
    public boolean update(@RequestBody  ItemCat itemCat){
        try {
            itemCatService.update(itemCat);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //批量删除产品
    @GetMapping("delete")
    public boolean deleteAll(Integer[] ids){
        try {
            itemCatService.deleteAll(ids);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    @GetMapping("findModule")
    public List<ItemCat> findModule(Integer parentId){
        try {
            return itemCatService.findModule(parentId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
