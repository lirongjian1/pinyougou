package com.pinyougou.manager.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
/** 获取登录用户名控制器 */
@RestController
public class IndexController {
    /** 显示登录用户名 */
    @GetMapping("showLoginName")
    public Map<String,String> showLoginName(){
      // 获取登录用户名 通过上下文域获取 因为用户登录之后上下文域有用户的信息
        String loginName = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println(loginName);
        HashMap<String, String> map = new HashMap<>();
        map.put("loginName",loginName);
        return map;
    }
}
