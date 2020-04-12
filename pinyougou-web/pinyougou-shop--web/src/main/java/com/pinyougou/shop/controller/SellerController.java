package com.pinyougou.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.Seller;
import com.pinyougou.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("seller")
public class SellerController {

    @Reference(timeout = 10000)
    private SellerService sellerService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //商家注册
    @PostMapping("save")
    public boolean save(@RequestBody Seller seller){
         try {
             //给密码加盐加密 springsecurity里面配置一下密码加密对象 和 设置加密方式
             //上面已经注入灵魂
             //得到密码 用encode方法加密这个密码 设置成加密完成的密码在添加
             seller.setPassword(passwordEncoder.encode(seller.getPassword()));
             sellerService.save(seller);
             System.out.println(sellerService);
               return true;
                 } catch (Exception e) {
              e.printStackTrace();
                 }
        return false;

    }

}
