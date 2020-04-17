package com.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.common.pojo.PageResult;
import com.pinyougou.pojo.Goods;
import com.pinyougou.service.GoodsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @autor lrj
 * @date 2020/4/15 0015 22:46
 */
@RestController
@RequestMapping("goods")
public class GoodsController {

    @Reference(timeout = 10000)
    private GoodsService goodsService;

    //查询全部未审核的商品
    @GetMapping("findByPage")
    public PageResult findByPage(Goods goods,Integer page, Integer rows){

        goods.setAuditStatus("0");
        try {
            if (StringUtils.isNoneBlank(goods.getGoodsName())){
                goods.setGoodsName(new String(goods.getGoodsName().getBytes("ISO8859-1"),"UTF-8"));
            }
           return goodsService.findByPage(goods,page,rows);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //修改状态码(0位审核 1通过 2 不通过)
    @GetMapping("updateStatus")
    public boolean updateStatus(Long[] ids,Integer status){
        try {
            goodsService.updateStatus("audit_status",ids,status);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /** 批量删除(逻辑删除 只是对goods表中的iddelete的列修改成1) */
    @GetMapping("delete")
    public boolean delete(Long[] ids){
        try {
            goodsService.updateStatus("is_delete",ids,1);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
