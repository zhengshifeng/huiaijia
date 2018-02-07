package com.flf.service.impl;

import com.base.service.BaseServiceImpl;
import com.flf.entity.HajCommodityType;
import com.flf.entity.HajCouponType;
import com.flf.mapper.HajCommodityMapper;
import com.flf.mapper.HajCommodityTypeMapper;
import com.flf.mapper.HajCouponBrandMapper;
import com.flf.mapper.HajCouponTypeMapper;
import com.flf.service.HajCouponCommodityService;
import com.flf.service.HajCouponTypeService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * <br>
 * <b>功能：</b>HajCouponTypeService<br>
 */
@Service("hajCouponTypeService")
public class HajCouponTypeServiceImpl extends BaseServiceImpl implements HajCouponTypeService {
	private final static Logger log = Logger.getLogger(HajCouponTypeServiceImpl.class);

	@Autowired
	private HajCouponTypeMapper dao;

	@Autowired
	private HajCouponBrandMapper couponBrandMapper;

	@Autowired
	private HajCommodityTypeMapper commodityTypeMapper;

	@Autowired
	private HajCommodityMapper commodityMapper;

	@Autowired
	private HajCouponCommodityService couponCommodityService;

	@Override
	public HajCouponTypeMapper getDao() {
		return dao;
	}

	@Override
	public List<HajCouponType> listPage(HajCouponType po) {
		return dao.listPage(po);
	}

	@Override
	public List<Map<String, Object>> getListByTypeId(int typeId) {
		return dao.getListByTypeId(typeId);
	}

	@Override
	public List<HashMap<String, Object>> listWithTreeNodes(Integer couponId) {
		List<HashMap<String, Object>> list = new ArrayList<>();
		HashMap<String, Object> map;

		setAttrNode(list);
		List<HajCommodityType> listAll = commodityTypeMapper.listAll();

		List<Integer> couponTypeList = dao.listTypeByCoupon(couponId);

		for (HajCommodityType type : listAll) {
			map = new HashMap<>();
			map.put("id", type.getId());
			map.put("name", type.getTypeName());
			if (type.getParentId() == 0) {
				// 配合treeNode数据格式，大类的pid设为属性，看起来就像父类
				map.put("pId", type.getCommodityAttr());
			} else {
				map.put("pId", type.getParentId());
			}
			map.put("open", "true");
			if (couponTypeList.size() > 0 && couponTypeList.contains(type.getId())) {
				map.put("checked", "true");
			}
			list.add(map);
		}
		return list;
	}

	/**
	 * 设置分类的属性节点
	 */
	private void setAttrNode(List<HashMap<String, Object>> list) {
		HashMap<String, Object> map;
		map = new HashMap<>();
		map.put("id", "早餐");
		map.put("name", "早餐");
		map.put("pId", "木有");
		map.put("open", "true");
		list.add(map);

		map = new HashMap<>();
		map.put("id", "水果");
		map.put("name", "水果");
		map.put("pId", "木有");
		map.put("open", "true");
		list.add(map);

		map = new HashMap<>();
		map.put("id", "生鲜");
		map.put("name", "生鲜");
		map.put("pId", "木有");
		map.put("open", "true");
		list.add(map);

		map = new HashMap<>();
		map.put("id", "团购");
		map.put("name", "团购");
		map.put("pId", "木有");
		map.put("open", "true");
		list.add(map);
	}

	@Override
	public boolean save(Integer couponId, List<Integer> typeList) throws Exception {
		// 先清空之前选择的所有该优惠券下的分类
		dao.deleteByCoupon(couponId);

		// 品类跟品牌只能二选一，所以清掉另一个
		couponBrandMapper.deleteByCoupon(couponId);

		// 重新添加
		if (typeList != null && typeList.size() > 0) {
			dao.addBatch(typeList, couponId);
		}

		// 将分类下的商品加入到该优惠券中
		List<String> commodityList = commodityMapper.listByTypeIds(typeList);
		couponCommodityService.save(couponId, commodityList);

		return true;
	}

	@Override
	public List<Integer> listCouponIdByTypeId(Integer typeId) {
		return dao.listCouponIdByTypeId(typeId);
	}

}
