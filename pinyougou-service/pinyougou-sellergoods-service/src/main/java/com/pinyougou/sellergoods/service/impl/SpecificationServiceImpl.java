package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.common.pojo.PageResult;
import com.pinyougou.mapper.SpecificationOptionMapper;
import com.pinyougou.pojo.Specification;
import com.pinyougou.mapper.SpecificationMapper;
import com.pinyougou.pojo.SpecificationOption;
import com.pinyougou.service.SpecificationService;
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
 * SpecificationServiceImpl 服务接口实现类
 * @date 2020-03-23 19:16:17
 * @version 1.0
 */
@Service(interfaceName = "com.pinyougou.service.SpecificationService")
/** 上面指定接口名，产生服务名，不然会用代理类的名称 */
@Transactional
public class SpecificationServiceImpl implements SpecificationService {

	@Autowired
	private SpecificationMapper specificationMapper;
	@Autowired
	private SpecificationOptionMapper specificationOptionMapper;


	@Override
	public List<Map<String, Object>> findSpecification() {
		try {
			return specificationMapper.findSpecification();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	/** 添加方法 */
	public void save(Specification specification){
		try {
			specificationMapper.insertSelective(specification);
			specificationOptionMapper.save(specification);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** 修改方法 */
	public void update(Specification specification){
		try {
			//修改规格表
			specificationMapper.updateByPrimaryKeySelective(specification);
			//根据规格id删除规格选项表
			Long id = specification.getId();
			//创建规格选项表实体对象
			SpecificationOption specificationOption = new SpecificationOption();
			//把规格id封装给规格选项表的spec_id
			specificationOption.setSpecId(id);
			//根据规格表的id删除规格选项表
			specificationOptionMapper.delete(specificationOption);
			//再添加即可
			specificationOptionMapper.save(specification);
		}catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}

	/** 根据主键id删除 */
	public void delete(Serializable id){
		try {
			specificationMapper.deleteByPrimaryKey(id);
		}catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}

	/** 批量删除 */
	public void deleteAll(Serializable[] ids){
		try {
			specificationMapper.deleteAll(ids);
			//根据规格id删除规格选项表
			specificationOptionMapper.deleteAll(ids);
		}catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}

	/** 根据主键id查询 */
	public Specification findOne(Serializable id){
		try {
			return specificationMapper.selectByPrimaryKey(id);
		}catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}

	/** 查询全部 */
	public List<Specification> findAll(){
		try {
			return specificationMapper.selectAll();
		}catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}

	/** 多条件分页查询 */
	public PageResult findByPage(Specification specification, Integer page, Integer rows){
		try {
			PageInfo<Specification> pageInfo = PageHelper.startPage(page, rows)
				.doSelectPageInfo(new ISelect() {
					@Override
					public void doSelect() {
						specificationMapper.findAll(specification);
					}
				});
			return new PageResult(pageInfo.getTotal(),pageInfo.getList());
		}catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}

	@Override
	public List<SpecificationOption> findSpecOption(long id) {
		try {
			// 创建规格选项对象封装查询条件
			SpecificationOption so = new SpecificationOption();
			so.setSpecId(id);
			return specificationOptionMapper.select(so);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}



}