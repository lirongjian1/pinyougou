package com.pinyougou.search.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.pojo.SolrItem;
import com.pinyougou.service.SearchService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.*;
import org.springframework.data.solr.core.query.result.HighlightEntry;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.data.solr.core.query.result.ScoredPage;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * Create by chen zhi yang
 * 2020/4/22 16:58
 */

@Service(interfaceName = "com.pinyougou.service.SearchService")
@Transactional
public class SearchServiceImpl implements SearchService {
    public static void main(String[] args) {
        String dd = new String("dd");
        int i = dd.hashCode();
        System.out.println(i);

        StringBuffer stringBuffer = new StringBuffer();
    }
    @Autowired
    private SolrTemplate solrTemplate;
    @Override
    public Map<String, Object> search(Map<String, Object> params) {

        //定义map集合封装返回数据
        Map<String, Object> data = new HashMap<>();
        //获取查询关键字
        String keywords = (String) params.get("keywords");

        //获取当前页面 和页面大小
        Integer page = (Integer) params.get("page");
        if (page == null){
            page=1;
        }
        //获取页面大小
        Integer rows = (Integer) params.get("rows");
        if (rows == null){
            rows=20;
        }



        //判断关键字是否不为空
        if (StringUtils.isNoneBlank(keywords)){

            /*#################1.高亮查询 写在最前面####################*/
            //创建高亮查询对象
            SimpleHighlightQuery simpleHighlightQuery = new SimpleHighlightQuery();
            //创建高亮规格选项对象 制定哪个域做高亮显示 用什么样式显示
            HighlightOptions highlightOptions = new HighlightOptions();
            //指定高亮域
            highlightOptions.addField("title");
            //设置高亮前缀
            highlightOptions.setSimplePrefix("<font color='red'>");
            //设置高亮后缀
            highlightOptions.setSimplePostfix("</font>");
            //设置高亮选项 就是整合查询对象与高亮选项对象
            simpleHighlightQuery.setHighlightOptions(highlightOptions);
            //创建查询条件对象 就是获取前端搜索框传过来的值 设置成查询条件
            Criteria criteria = new Criteria("keywords").is(keywords);
            //添加查询条件 整个查询对象和查询条件对象
            simpleHighlightQuery.addCriteria(criteria);

            /*#################2.过滤查询 写在中间####################*/
            /** 按商品分类过滤 如果传入的商品分类名称不为空就执行查询*/
            if (!"".equals(params.get("category"))){
                //创建查询条件对象 就是获取前端搜索框传过来的值 设置成查询条件
                Criteria criteria1 = new Criteria("category").is(params.get("category"));
                //添加过滤条件 里面再条件查询添加进去
                simpleHighlightQuery.addFilterQuery(new SimpleFilterQuery(criteria1));
            }
            /** 按品牌过滤 */
            if (!"".equals(params.get("brand"))){
                //创建查询条件对象 就是获取前端搜索框传过来的值 设置成查询条件
                Criteria criteria1 = new Criteria("brand").is(params.get("brand"));
                //添加过滤条件 里面再条件查询添加进去
                simpleHighlightQuery.addFilterQuery(new SimpleFilterQuery(criteria1));
            }
            /** 按规格过滤 */
            if (null != params.get("spec")){
                Map<String,Object> spec = (Map<String, Object>) params.get("spec");
                for (String key : spec.keySet()) {
                    //创建查询条件对象 就是获取前端搜索框传过来的值 设置成查询条件
                    Criteria criteria1= new Criteria("spec_"+key).is(spec.get(key));
                    //添加过滤条件 里面再条件查询添加进去
                    simpleHighlightQuery.addFilterQuery(new SimpleFilterQuery(criteria1));
                }
            }
            /** 按价格过滤 */
            if(!"".equals(params.get("price"))){
                //得到价格数组
                String[] prices = params.get("price").toString().split("-");
                /** 如果价格区间起点不等于0 */
                if (!prices[0].equals("0")){
                    //创建查询条件对象 就是获取前端搜索框传过来的值 设置成查询条件
                    Criteria criteria1 = new Criteria("price").greaterThanEqual(prices[0]);
                    //添加过滤条件 里面再条件查询添加进去
                    simpleHighlightQuery.addFilterQuery(new SimpleFilterQuery(criteria1));
                }
                if (!prices[1].equals("*")){
                    //创建查询条件对象 就是获取前端搜索框传过来的值 设置成查询条件
                    Criteria criteria1 = new Criteria("price").lessThanEqual(prices[1]);
                    //添加过滤条件 里面再条件查询添加进去
                    simpleHighlightQuery.addFilterQuery(new SimpleFilterQuery(criteria1));
                }
                }

            /** 添加排序 */
            String sortValue = (String) params.get("sort"); // ASC  DESC
            String sortField = (String) params.get("sortField"); // 排序域
            if(StringUtils.isNoneBlank(sortValue)
                    && StringUtils.isNoneBlank(sortField)){
                Sort sort = new Sort("ASC".equalsIgnoreCase(sortValue) ?
                        Sort.Direction.ASC : Sort.Direction.DESC, sortField);
                /** 增加排序 */
                simpleHighlightQuery.addSort(sort);
            }


            /*#################3.分页查询####################*/
            /** 设置起始记录查询数 */
            simpleHighlightQuery.setOffset((page-1) * rows);
            /** 设置每页显示记录数 */
            simpleHighlightQuery.setRows(rows);


            // 分页查询，得到高亮分页查询对象
            HighlightPage<SolrItem> highlightPage = solrTemplate.queryForHighlightPage(simpleHighlightQuery, SolrItem.class);
            System.out.println(highlightOptions);

            /** 设置总页数 */
            data.put("totalPages",highlightPage.getTotalPages());
            /** 设置总记录数 */
            data.put("total",highlightPage.getTotalElements());

            //循环高亮项集合
            for (HighlightEntry<SolrItem> he : highlightPage.getHighlighted()) {
                //获取检索到的原实体
                SolrItem solrItem = he.getEntity();
                //判断高亮集合及集合中第一个field的高亮内容
                if (he.getHighlights().size()>0 && he.getHighlights().get(0).getSnipplets().size()>0){
                    //设置高亮结果 把标题设置成高亮标题
                    solrItem.setTitle(he.getHighlights().get(0).getSnipplets().get(0));
                }
            }
            data.put("rows",highlightPage.getContent());
        } else {

        //创建查询对象
        Query query = new SimpleQuery("*:*");
            /** 设置起始记录查询数 */
            query.setOffset((page-1) * rows);
            /** 设置每页显示记录数 */
            query.setRows(rows);
        /** 简单分页检索 没有搜索条件 没有高亮显示查询 */
        ScoredPage<SolrItem> maps = solrTemplate.queryForPage(query, SolrItem.class);
            /** 设置总页数 */
            data.put("totalPages",maps.getTotalPages());
            /** 设置总记录数 */
            data.put("total",maps.getTotalElements());
        data.put("rows",maps.getContent());
        }
        return data;
    }
}
