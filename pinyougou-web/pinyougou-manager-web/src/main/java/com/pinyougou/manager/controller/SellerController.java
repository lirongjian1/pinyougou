package com.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.common.pojo.PageResult;
import com.pinyougou.manager.util.StringUtil;
import com.pinyougou.pojo.Seller;
import com.pinyougou.service.SellerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("seller")
public class SellerController {

    @Reference
    private SellerService sellerService;

    @GetMapping("findByPage")
    public PageResult findByPage(Seller seller, Integer page, Integer rows) {
        if (seller != null) {
            System.out.println(seller.getName());
            seller.setName(StringUtil.iSO8859ToUTF8(seller.getName()));
            seller.setNickName(StringUtil.iSO8859ToUTF8(seller.getNickName()));
        }
        try {
            return sellerService.findByPage(seller, page, rows);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //根据商家id 修改状态码
    @GetMapping("updateStatus")
    public boolean updateStatus(String sellerId,String status){
         try {
               sellerService.updateStatus(sellerId,status);
               return true;
                 } catch (Exception e) {
                    e.printStackTrace();
                 }
                 return false;
    }

}
