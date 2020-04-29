package com.pinyougou.search.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.service.SearchService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Create by chen zhi yang
 * 2020/4/22 16:46
 */

@RestController
@RequestMapping
public class SearchController {

    @Reference(timeout = 10000)
    private SearchService searchService;

    @PostMapping("Search")
    public Map<String,Object> search(@RequestBody Map<String,Object> params){
           return searchService.search(params);

    }
}
