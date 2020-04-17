package com.pinyougou.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.common.pojo.PageResult;
import com.pinyougou.pojo.Goods;
import com.pinyougou.pojo.ItemCat;
import com.pinyougou.service.GoodsService;
import com.pinyougou.service.ItemCatService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import sun.security.pkcs11.P11Util;

import java.util.List;
import java.util.Map;

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

        //分页查询所有的商品
    @GetMapping("findByPage")
    public PageResult findByPage(Goods goods, Integer page, Integer rows){

        try {
            if (StringUtils.isNoneBlank(goods.getGoodsName())) {
                try {
                    goods.setGoodsName(new String(goods
                            .getGoodsName().getBytes("ISO8859-1"), "UTF-8"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
             //获取用户登录名字
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            goods.setSellerId(username);
           return goodsService.findByPage(goods,page,rows);
        } catch (Exception e) {
           throw new RuntimeException(e);
        }
    }
    //商品上下架
    @GetMapping("updateMarketable")
    public boolean updateMarketable(Long[] ids,String marketable){
         try {
               goodsService.updateMarketable(ids,marketable);
               return true;
                 } catch (Exception e) {
                     e.printStackTrace();
                     return false;
                 }
    }


}
