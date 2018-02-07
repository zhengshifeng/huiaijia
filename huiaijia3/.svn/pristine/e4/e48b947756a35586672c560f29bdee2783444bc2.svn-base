package com.flf.service.impl;

import com.base.service.BaseServiceImpl;
import com.flf.entity.HajCommodityType;
import com.flf.mapper.HajCommodityTypeMapper;
import com.flf.service.HajCommodityService;
import com.flf.service.HajCommodityTypeService;
import com.flf.service.RedisSpringProxy;
import com.flf.util.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * <br>
 * <b>功能：</b>HajCommodityTypeService<br>
 */
@Service("hajCommodityTypeService")
public class HajCommodityTypeServiceImpl extends BaseServiceImpl implements HajCommodityTypeService {

	@Autowired
	private HajCommodityTypeMapper dao;

	@Autowired
	private HajCommodityService commodityService;

	@Autowired
	private RedisSpringProxy redisSpringProxy;

	@Override
	public HajCommodityTypeMapper getDao() {
		return dao;
	}

	@Override
	public List<HajCommodityType> getSonCommodityTypeList(HajCommodityType parentType) throws Exception {
		List<HajCommodityType> sonList = new ArrayList<>();
		List<HajCommodityType> list = dao.getCommodityTypeByParentIdApp(parentType.getId());
		HajCommodityType c;
		for (HajCommodityType type : list) {
			type.setAreaCode(parentType.getAreaCode());
			type.setRemark3(parentType.getRemark3());

			// 如果该分类下没有可购买的商品，则隐藏该分类
			if (commodityService.getCountByType(type) > 0) {
				c = new HajCommodityType();
				c.setTypeName(type.getTypeName());
				c.setId(type.getId());
				c.setDescription(type.getDescription());
				c.setIcon(type.getIcon());
				c.setCommodityAttr(type.getCommodityAttr());
				sonList.add(c);
			}
		}
		return sonList;
	}

	@Override
	public List<HajCommodityType> getCommodityTypeId(Integer typeId, Integer communityId) {
		List<HajCommodityType> clist = new ArrayList<>();
		Map<String, Object> condition = new HashMap<>();
		condition.put("typeId", typeId);
		condition.put("communityId", communityId);
		List<Map<String, Object>> list = dao.getCommodityTypeId(condition);
		if (list.size() > 0) {
			for (Map<String, Object> type : list) {
				HajCommodityType c = new HajCommodityType();
				c.setTypeName(String.valueOf(type.get("name")));
				c.setId(Integer.parseInt(type.get("id").toString()));
				clist.add(c);
			}
		}
		return clist;
	}

	/**
	 * 删除类型之前将所有调用该类型的商品分类ID设为0（未知类别）
	 * 
	 * @param ids
	 * @throws Exception
	 */
	@Override
	public void delete(Integer[] ids) throws Exception {
		HajCommodityType commodityType = null;
		for (Integer id : ids) {
			commodityType = dao.queryById(id);
			dao.destoryCommodityTypeId(id);
			dao.delete(id);
			if (commodityType != null)
				redisSpringProxy.delete("CommodityTypeByPId_" + commodityType.getParentId());
		}
	}

	@Override
	public List<HajCommodityType> getBigTypeByattr(String commodityAttr) {
		return dao.getBigTypeByattr(commodityAttr);
	}

	@Override
	public List<HajCommodityType> getTypeByParentId(int parentId) {
		return dao.getCommodityTypeByParentId(parentId);
	}

	@Override
	public int getTypeCount(HajCommodityType po) {
		return dao.getTypeCount(po);
	}

	@Override
	public void putTypeList2MV(ModelAndView mv, String commodityAttr, Integer parentTypeId) {
		if (Tools.notEmpty(commodityAttr) && !"0".equals(commodityAttr)) {
			List<HajCommodityType> typeList = dao.getBigTypeByattr(commodityAttr);
			mv.addObject("parentTypeList", typeList);//大类列表

			typeList = dao.getCommodityTypeByParentId(parentTypeId);
			mv.addObject("subTypeList", typeList);
		}
	}

	@Override
	public List<HajCommodityType> getParentTypeByAttr(HajCommodityType type) {
		return dao.getParentTypeByAttr(type);
	}

	@Override
	public List<HajCommodityType> getTypeByPid(HajCommodityType type) {
		return dao.getTypeByPid(type);
	}

	@Override
	public List<HajCommodityType> getByCategoryId(Integer categoryId) {
		return dao.getByCategoryId(categoryId);
	}

	@Override
	public List<HajCommodityType> list(String attr) {
		return dao.list(attr);
	}

}
