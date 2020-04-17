package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.pinyougou.common.pojo.PageResult;
import com.pinyougou.mapper.SpecificationOptionMapper;
import com.pinyougou.pojo.SpecificationOption;
import com.pinyougou.pojo.TypeTemplate;
import com.pinyougou.mapper.TypeTemplateMapper;
import com.pinyougou.service.TypeTemplateService;
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
 * TypeTemplateServiceImpl 服务接口实现类
 * @date 2020-03-23 19:16:17
 * @version 1.0
 */
@Service(interfaceName = "com.pinyougou.service.TypeTemplateService")
@Transactional
public class TypeTemplateServiceImpl implements TypeTemplateService {

	@Autowired
	private TypeTemplateMapper typeTemplateMapper;
	@Autowired
	private SpecificationOptionMapper specificationOptionMapper;


	//根据类型模板id查询对应的规格 以及规格选项 用List<Map>封装
	@Override
	public List<Map> findSpecByTemplateId(Integer id) {
		try {
			long id2 = id.longValue();
			TypeTemplate typeTemplate = typeTemplateMapper.selectByPrimaryKey(id2);
			String specIds = typeTemplate.getSpecIds();
			List<Map> listMap = JSON.parseArray(specIds, Map.class);
			//根据类型模板的的SpecIds中的id查询规格选项
			for (Map map : listMap) {
				SpecificationOption specificationOption = new SpecificationOption();
				//先把得到的id转成字符串 再转包装类
				specificationOption.setSpecId(Long.valueOf(map.get("id").toString()));
				List<SpecificationOption> options = specificationOptionMapper.select(specificationOption);
				map.put("options",options);
			}
			return listMap;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/** 添加方法 */
	public void save(TypeTemplate typeTemplate){
		try {
			typeTemplateMapper.insertSelective(typeTemplate);
		}catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}

	/** 修改方法 */
	public void update(TypeTemplate typeTemplate){
		//这个方法会根据id去修改数据
		try {
			typeTemplateMapper.updateByPrimaryKeySelective(typeTemplate);
		}catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}

	/** 根据主键id删除 */
	public void delete(Serializable id){
		try {
			typeTemplateMapper.deleteByPrimaryKey(id);
		}catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}

	/** 批量删除 */
	public void deleteAll(Serializable[] ids){
		try {

			typeTemplateMapper.deleteAll(ids);
		}catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}

	/** 根据主键id查询 */
	public TypeTemplate findOne(Serializable id){
		try {
			return typeTemplateMapper.selectByPrimaryKey(id);
		}catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}

	/** 查询全部 */
	public List<TypeTemplate> findAll(){
		try {
			return typeTemplateMapper.selectAll();
		}catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}

	/** 多条件分页查询 */
	public PageResult findByPage(TypeTemplate typeTemplate, int page, int rows){
		try {
			PageInfo<TypeTemplate> pageInfo = PageHelper.startPage(page, rows)
				.doSelectPageInfo(new ISelect() {
					@Override
					public void doSelect() {
						typeTemplateMapper.findAll(typeTemplate);
					}
				});
			return new PageResult(pageInfo.getTotal(),pageInfo.getList());
		}catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}

    //查询模板表格 要获取它的id和name 要根据id添加分类商品 name显示给用看
	@Override
	public List<Map<String, Object>> findTypeTemplateList() {
		return typeTemplateMapper.findTypeTemplateList();
	}



}