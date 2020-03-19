package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.mapper.BrandMapper;
import com.pinyougou.pojo.Brand;
import com.pinyougou.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceName = "com.pinyougou.service.BrandService")
@Transactional
public class BrandServiceImpl implements BrandService {
    //第一天 为什么可以注入 看applicationContext-mapper.xml中生成代理对象配置
    @Autowired
    private BrandMapper brandMapper;
   //第一天 查询所有品牌
    @Override
    public List<Brand> findAll() {
        return brandMapper.findAll();
    }
}
