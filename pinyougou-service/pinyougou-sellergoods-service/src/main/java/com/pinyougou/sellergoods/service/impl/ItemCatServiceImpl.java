package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.pojo.ItemCat;
import com.pinyougou.mapper.ItemCatMapper;
import com.pinyougou.service.ItemCatService;
import java.util.List;
import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import java.io.Serializable;
import java.util.Arrays;
/**
 * ItemCatServiceImpl 服务接口实现类
 * @date 2020-03-23 19:16:17
 * @version 1.0
 */
@Service(interfaceName ="com.pinyougou.service.ItemCatService")
@Transactional
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private ItemCatMapper itemCatMapper;


	@Override
	public List<ItemCat> findModule(Integer parentId) {
		return itemCatMapper.findModule(parentId);
	}

	/** 添加方法 */
	public void save(ItemCat itemCat){
		try {
			itemCatMapper.insertSelective(itemCat);
		}catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}

	/** 修改方法 */
	public void update(ItemCat itemCat){
		try {
			itemCatMapper.updateByPrimaryKeySelective(itemCat);
		}catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}

	/** 根据主键id删除 */
	public void delete(Serializable id){
		try {
			itemCatMapper.deleteByPrimaryKey(id);
		}catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}

	/** 批量删除 */
	public void deleteAll(Serializable[] ids){
		try {

			itemCatMapper.deleteAll(ids);
		}catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}

	/** 根据主键id查询 */
	public ItemCat findOne(Serializable id){
		try {
			return itemCatMapper.selectByPrimaryKey(id);
		}catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}

	/** 查询全部 */
	public List<ItemCat> findAll(){
		try {
			return itemCatMapper.selectAll();
		}catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}

	/** 多条件分页查询 */
	public List<ItemCat> findByPage(ItemCat itemCat, int page, int rows){
		try {
			PageInfo<ItemCat> pageInfo = PageHelper.startPage(page, rows)
				.doSelectPageInfo(new ISelect() {
					@Override
					public void doSelect() {
						itemCatMapper.selectAll();
					}
				});
			return pageInfo.getList();
		}catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}



}