package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.common.pojo.PageResult;
import com.pinyougou.pojo.Seller;
import com.pinyougou.mapper.SellerMapper;
import com.pinyougou.pojo.User;
import com.pinyougou.service.SellerService;

import java.util.Date;
import java.util.List;
import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import tk.mybatis.mapper.entity.Example;
import java.io.Serializable;
import java.util.Arrays;
/**
 * SellerServiceImpl 服务接口实现类
 * @date 2020-03-23 19:16:17
 * @version 1.0
 */
@Service(interfaceName = "com.pinyougou.service.SellerService")
@Transactional
public class SellerServiceImpl implements SellerService {

	@Autowired
	private SellerMapper sellerMapper;

	@Override
	public Seller findById(String username) {
		return sellerMapper.findById(username);
	}

	//根据商家id 修改它的状态码
	@Override
	public void updateStatus(String sellerId, String status) {
		 try {
			 Seller seller = new Seller();
			 seller.setSellerId(sellerId);
			 seller.setStatus(status);
			 sellerMapper.updateByPrimaryKeySelective(seller);
		         } catch (Exception e) {
		             throw new RuntimeException(e);
		         }
	}



	/** 注册商家 */
	public void save(Seller seller){
		try {
			//设置状态码 审核是否通过的码 设置注册时间
			seller.setStatus("0");
			seller.setCreateTime(new Date());
			System.out.println(seller);
			sellerMapper.insert(seller);
		}catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}

	/** 多条件分页查询 */
	public PageResult findByPage(Seller seller, int page, int rows){
		try {
			PageInfo<Object> pageInfo = PageHelper.startPage(page, rows).doSelectPageInfo(new ISelect() {
				@Override
				public void doSelect() {
					sellerMapper.findByPage(seller);
				}
			});
			return new PageResult(pageInfo.getTotal(),pageInfo.getList());
		}catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}



	/** 修改方法 */
	public void update(Seller seller){
		try {
			sellerMapper.updateByPrimaryKeySelective(seller);
		}catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}

	/** 根据主键id删除 */
	public void delete(Serializable id){
		try {
			sellerMapper.deleteByPrimaryKey(id);
		}catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}

	/** 批量删除 */
	public void deleteAll(Serializable[] ids){
		try {
			// 创建示范对象
			Example example = new Example(Seller.class);
			// 创建条件对象
			Example.Criteria criteria = example.createCriteria();
			// 创建In条件
			criteria.andIn("id", Arrays.asList(ids));
			// 根据示范对象删除
			sellerMapper.deleteByExample(example);
		}catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}

	/** 根据主键id查询 */
	public Seller findOne(Serializable id){
		try {
			return sellerMapper.selectByPrimaryKey(id);
		}catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}

	/** 查询全部 */
	public List<Seller> findAll(){
		try {
			return sellerMapper.selectAll();
		}catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}

}