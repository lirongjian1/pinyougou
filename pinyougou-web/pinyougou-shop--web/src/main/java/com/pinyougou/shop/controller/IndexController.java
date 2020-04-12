package com.pinyougou.shop.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping
public class IndexController {

    @GetMapping("showLoginName")
    public Map<String,String> showLoginName(){
        try {
            String name = SecurityContextHolder.getContext().getAuthentication().getName();
            Map<String , String> map = new HashMap<>();
            map.put("loginName",name);
            return map;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
