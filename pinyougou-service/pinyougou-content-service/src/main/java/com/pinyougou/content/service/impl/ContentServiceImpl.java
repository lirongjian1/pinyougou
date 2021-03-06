package com.pinyougou.content.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.common.pojo.PageResult;
import com.pinyougou.pojo.Content;
import com.pinyougou.mapper.ContentMapper;
import com.pinyougou.service.ContentService;
import java.util.List;
import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import java.io.Serializable;
import java.util.Arrays;
/**
 * ContentServiceImpl 服务接口实现类
 * @date 2020-03-23 19:16:17
 * @version 1.0
 */
@Service(interfaceName = "com.pinyougou.service.ContentService")
@Transactional
public class ContentServiceImpl implements ContentService {

	@Autowired
	private ContentMapper contentMapper;
	//注入reditemplate
	@Autowired
	private RedisTemplate redisTemplate;


	/**
	 * 查询所有的轮播图广告
	 * @param categoryId
	 * @return
	 */
	@Override
	public List<Content> findContentByCategoryId(Long categoryId) {
		//1.先看redis有没有数据 没有就查询数据库
		List<Content> contentList =null;
		try {
			contentList = (List<Content>) redisTemplate.boundValueOps("content").get();
			//3.如果redis中查询到的数据不为空就直接返回数据给浏览器
			if (contentList != null && contentList.size()>0){
				return contentList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			 contentList = contentMapper.findContentByCategoryId(categoryId);
			 //2.吧数据查询到的数据写入redis中
			try {
				redisTemplate.boundValueOps("content").set(contentList);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return contentList;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/** 添加方法 */
	public void save(Content content){

		try {
			contentMapper.insertSelective(content);
			redisTemplate.delete("content");
		}catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}

	/** 修改方法 */
	public void update(Content content){
		try {
			contentMapper.updateByPrimaryKeySelective(content);
			/** 清除Redis缓存 */
			redisTemplate.delete("content");

		}catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}

	/** 根据主键id删除 */
	public void delete(Serializable id){
		try {
			contentMapper.deleteByPrimaryKey(id);
		}catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}

	/** 批量删除 */
	public void deleteAll(Serializable[] ids){
		try {
			// 创建示范对象
			Example example = new Example(Content.class);
			// 创建条件对象
			Example.Criteria criteria = example.createCriteria();
			// 创建In条件
			criteria.andIn("id", Arrays.asList(ids));
			// 根据示范对象删除
			contentMapper.deleteByExample(example);
			/** 清除Redis缓存 */
			redisTemplate.delete("content");

		}catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}

	/** 根据主键id查询 */
	public Content findOne(Serializable id){
		try {
			return contentMapper.selectByPrimaryKey(id);
		}catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}

	/** 查询全部 */
	public List<Content> findAll(){
		try {
			return contentMapper.selectAll();
		}catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}

	/** 多条件分页查询 */
	public PageResult findByPage(Content content, int page, int rows){
		try {
			PageInfo<Content> pageInfo = PageHelper.startPage(page, rows)
				.doSelectPageInfo(new ISelect() {
					@Override
					public void doSelect() {
						contentMapper.selectAll();
					}
				});
			return new PageResult(pageInfo.getTotal(),pageInfo.getList());
		}catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}


}