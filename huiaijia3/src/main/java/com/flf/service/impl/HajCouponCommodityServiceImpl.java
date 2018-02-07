package com.flf.service.impl;

import com.base.service.BaseServiceImpl;
import com.flf.entity.HajCommodity;
import com.flf.entity.HajCommodityType;
import com.flf.entity.HajCouponCommodity;
import com.flf.mapper.HajCommodityTypeMapper;
import com.flf.mapper.HajCouponCommodityMapper;
import com.flf.service.HajCouponBrandService;
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
 * <br>
 * <b>功能：</b>HajCouponCommodityService<br>
 */
@Service("hajCouponCommodityService")
public class HajCouponCommodityServiceImpl extends BaseServiceImpl implements HajCouponCommodityService {
	private final static Logger log = Logger.getLogger(HajCouponCommodityServiceImpl.class);

	@Autowired
	private HajCouponCommodityMapper dao;

	@Autowired
	private HajCommodityTypeMapper commodityTypeMapper;

	@Autowired
	private HajCouponBrandService hajCouponBrandService;

	@Autowired
	private HajCouponTypeService hajCouponTypeService;

	@Override
	public HajCouponCommodityMapper getDao() {
		return dao;
	}

	@Override
	public List<HajCouponCommodity> listPage(HajCouponCommodity po) {
		return dao.listPage(po);
	}

	@Override
	public List<Map<String, Object>> listWithTreeNodes(HajCouponCommodity po) {
		String[] attributes = { "早餐", "水果", "生鲜", "团购" };
		HajCommodityType type;
		List<HajCommodityType> typeList;

		List<Map<String, Object>> resultList = new ArrayList<>();
		Map<String, Object> resultMap;

		for (String attr : attributes) {
			type = new HajCommodityType();
			type.setCommodityAttr(attr);
			type.setAreaCode(po.getAreaCode());

			resultMap = new HashMap<>();
			resultMap.put("id", attr);
			resultMap.put("name", attr);
			resultMap.put("pId", "0");
			resultMap.put("open", true);
			resultList.add(resultMap);

			typeList = commodityTypeMapper.getParentTypeByAttr(type);
			resultList.addAll(setParentTypeList(typeList, po));
		}
		return resultList;
	}

	@Override
	public void deleteByCoupon(Integer couponId) {
		dao.deleteByCoupon(couponId);
	}

	@Override
	public boolean save(Integer couponId, List<String> commodityList) throws Exception {
		// 先清空之前选择的所有该优惠券下的商品
		dao.deleteByCoupon(couponId);

		// 重新添加
		if (commodityList != null && commodityList.size() > 0) {
			dao.addBatch(commodityList, couponId);
		}
		return true;
	}

	private List<Map<String, Object>> setParentTypeList(List<HajCommodityType> parentTypeList, HajCouponCommodity po) {
		if (parentTypeList != null) {
			List<HajCommodityType> typeList;

			List<Map<String, Object>> resultList = new ArrayList<>();
			Map<String, Object> resultMap;

			for (HajCommodityType parent : parentTypeList) {
				resultMap = new HashMap<>();
				resultMap.put("id", "p_" + parent.getId());
				resultMap.put("name", parent.getTypeName());
				resultMap.put("pId", parent.getCommodityAttr());
				resultMap.put("open", true);
				resultList.add(resultMap);

				parent.setAreaCode(po.getAreaCode());
				typeList = commodityTypeMapper.getTypeByPid(parent);
				resultList.addAll(setTypeList(typeList, po));
			}
			return resultList;
		}
		return null;
	}

	private List<Map<String, Object>> setTypeList(List<HajCommodityType> typeList, HajCouponCommodity po) {
		if (typeList != null) {
			List<Map<String, Object>> resultList = new ArrayList<>();
			Map<String, Object> resultMap;

			for (HajCommodityType type : typeList) {
				resultMap = new HashMap<>();
				resultMap.put("id", "t_" + type.getId());
				resultMap.put("name", type.getTypeName());
				resultMap.put("pId", "p_" + type.getParentId());
				resultMap.put("open", true);
				resultList.add(resultMap);

				type.setAreaCode(po.getAreaCode());
				resultList.addAll(setCommodityList(commodityTypeMapper.getCommodityByType(type), po));
			}
			return resultList;
		}
		return null;
	}

	private List<Map<String, Object>> setCommodityList(List<HashMap<String, Object>> commodityList,
			HajCouponCommodity po) {
		if (commodityList != null) {
			List<Map<String, Object>> resultList = new ArrayList<>();
			Map<String, Object> resultMap;

			List<String> commodityByCoupon = dao.listCommodityByCoupon(po.getCouponId());

			String commodityNo;
			for (HashMap<String, Object> commodity : commodityList) {
				commodityNo = (String) commodity.get("commodityNo");
				resultMap = new HashMap<>();
				resultMap.put("id", commodityNo);
				resultMap.put("name", commodity.get("name"));
				resultMap.put("pId", "t_" + commodity.get("typeId"));
				resultMap.put("checked", commodityByCoupon.contains(commodityNo));
				resultList.add(resultMap);
			}
			return resultList;
		}
		return null;
	}

	/**
	 * 根据品牌或者分类更新优惠卷商品表
	 */
	@Override
	public void updateCouponCommodity(HajCommodity commodity) throws Exception {
		String commodityNo = commodity.getCommodityNo();
		// 根据商品品牌查找优惠券品牌列表
		if (null != commodity.getBrandId()) {
			List<Map<String, Object>> couponBrandList = hajCouponBrandService.getListByBrandId(commodity.getBrandId());
			add2couponCommodity(commodityNo, couponBrandList);
		}

		// 根据商品分类查找优惠券列表
		List<Map<String, Object>> couponTypeList = hajCouponTypeService.getListByTypeId(commodity.getTypeId());
		add2couponCommodity(commodityNo, couponTypeList);
	}

	private void add2couponCommodity(String commodityNo, List<Map<String, Object>> couponTypeList) {
		int couponId;
		List<HajCouponCommodity> couponCommodityList;
		for (Map<String, Object> typeMap : couponTypeList) {
			if (null != typeMap.get("id")) {
				couponId = Integer.parseInt(typeMap.get("id").toString());
				couponCommodityList = dao.getHajCouponCommodity(couponId, commodityNo);
				if (couponCommodityList.size() == 0) {
					saveCouponCommodity(couponId, commodityNo);
				}
			}
		}
	}

	public void saveCouponCommodity(int couponId, String commodityNo) {
		HajCouponCommodity couponCommodity = new HajCouponCommodity();
		couponCommodity.setCouponId(couponId);
		couponCommodity.setCommodityNo(commodityNo);
		dao.add(couponCommodity);
	}

	@Override
	public void deleteAfterCommodityUpdate(String commodityNo, List<Integer> couponIds) {
		dao.deleteAfterCommodityUpdate(commodityNo, couponIds);
	}

}
