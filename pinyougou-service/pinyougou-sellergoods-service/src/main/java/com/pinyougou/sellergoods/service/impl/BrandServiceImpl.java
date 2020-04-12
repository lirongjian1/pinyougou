package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.pinyougou.common.pojo.PageResult;
import com.pinyougou.pojo.Brand;
import com.pinyougou.mapper.BrandMapper;
import com.pinyougou.service.BrandService;
import java.util.List;
import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Map;

/**
 * BrandServiceImpl 服务接口实现类
 * @date 2020-03-23 19:16:17
 * @version 1.0
 */
@Service(interfaceName = "com.pinyougou.service.BrandService")
/** 上面指定接口名，产生服务名，不然会用代理类的名称 */
@Transactional
public class BrandServiceImpl implements BrandService {

	@Autowired
	private BrandMapper brandMapper;

	/** 添加方法 */
	public void save(Brand brand){

			brandMapper.insertSelective(brand);
	}

	/** 修改方法 */
	public void update(Brand brand){

			brandMapper.updateByPrimaryKeySelective(brand);
	}

	/** 根据主键id删除 */
	public void delete(Serializable id){
		try {
			brandMapper.deleteByPrimaryKey(id);
		}catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}

	/** 批量删除 */
	public void deleteAll(Serializable[] ids){

		try {
			brandMapper.deleteAll(ids);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** 根据主键id查询 */
	public Brand findOne(Serializable id){
		try {
			return brandMapper.selectByPrimaryKey(id);
		}catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}

	/** 查询全部 */
	public List<Map<String,Object>>findAll(){
		try {
			return brandMapper.findBrand();
		}catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}

	/** day3 多条件分页查询 */
	public PageResult findByPage(Brand brand, Integer page, Integer rows){
		try {
			PageInfo<Brand> pageInfo = PageHelper.startPage(page, rows)
				.doSelectPageInfo(new ISelect() {
					@Override
					public void doSelect() {
						brandMapper.findAll(brand);
					}
				});
			return new PageResult(pageInfo.getTotal(),pageInfo.getList());
		}catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}

	/**
	 * 测试
	 * @param name
	 * @return
	 */
	@Override
	public List<Brand> findByName(String name) {
		char[] chars = name.toCharArray();
		return brandMapper.findByName(chars);
	}
}