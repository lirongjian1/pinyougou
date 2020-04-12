package com.pinyougou.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.ItemCat;
import com.pinyougou.service.ItemCatService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @autor lrj
 * @date 2020/4/12 0012 0:42
 */
@RestController
@RequestMapping("itemcat")
public class ItemCatController {

    @Reference(timeout = 10000)
    private ItemCatService itemCatService;
    @GetMapping("findItemCatByParentId")
    public List<ItemCat> findItemCatByParentId(Integer parentId){
        try {
            return itemCatService.findModule(parentId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
