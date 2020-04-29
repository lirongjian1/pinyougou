package com.pinyougou.solr.util;

import com.alibaba.fastjson.JSON;
import com.pinyougou.mapper.ItemMapper;
import com.pinyougou.pojo.Item;
import com.pinyougou.pojo.SolrItem;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Create by chen zhi yang
 * 2020/4/21 22:47
 */
@Component
public class SolrUitl {

    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private SolrTemplate solrTemplate;
    /** 添加或修改 */
    public void importItemData(){
        //创建item 封装查询条件
        Item item = new Item();
        //查询正常的商品
        item.setStatus("1");
        List<Item> itemList = itemMapper.select(item);
        //创建索引库表
        List<SolrItem> solrItemList= new ArrayList<>();
        for (Item item1 : itemList) {
            SolrItem solrItem = new SolrItem();
            solrItem.setId(item1.getId());
            solrItem.setBrand(item1.getBrand());
            solrItem.setCategory(item1.getCategory());
            solrItem.setGoodsId(item1.getGoodsId());
            solrItem.setImage(item1.getImage());
            solrItem.setPrice(item1.getPrice());
            solrItem.setSeller(item1.getSeller());
            solrItem.setTitle(item1.getTitle());
            solrItem.setUpdateTime(item1.getUpdateTime());
            //得到规格列
            Map<String,Object> specMap = JSON.parseObject(item1.getSpec(), Map.class);
            solrItem.setSpecMap(specMap);
            solrItemList.add(solrItem);
        }

        //这个对象可以获取状态码
        UpdateResponse updateResponse = solrTemplate.saveBeans(solrItemList);
        System.out.println(updateResponse.getStatus());
        //如果状态码等于0 则保存数据成功
        if (updateResponse.getStatus() == 0){
            solrTemplate.commit();
        } else {
            solrTemplate.rollback();
        }
    }

    /**
     * 再main方法中获取ioc容器
     * @param args
     */
    public static void main(String[] args) {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        SolrUitl solrUitl = context.getBean(SolrUitl.class);
        solrUitl.importItemData();
    }
}
