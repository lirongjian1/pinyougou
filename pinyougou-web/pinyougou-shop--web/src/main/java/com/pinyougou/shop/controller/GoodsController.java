package com.pinyougou.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.Goods;
import com.pinyougou.pojo.ItemCat;
import com.pinyougou.service.GoodsService;
import com.pinyougou.service.ItemCatService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @autor lrj
 * @date 2020/4/11 0011 0:37
 */
@RestController
@RequestMapping("goods")
public class GoodsController {



    @Reference(timeout = 10000)
    private GoodsService goodsService;

    @PostMapping("save")
    public boolean save(@RequestBody Goods goods){
        try {
            //先获取用户登录的用户名 就是获取商家id 添加的时候 添加这个id
            String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
            goods.setSellerId(sellerId);
            goodsService.save(goods);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }




}
