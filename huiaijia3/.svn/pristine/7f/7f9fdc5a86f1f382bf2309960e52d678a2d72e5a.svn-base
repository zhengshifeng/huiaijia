package com.flf.service.impl;

import com.base.service.BaseServiceImpl;
import com.flf.entity.HajCommodityBrand;
import com.flf.entity.HajCouponBrand;
import com.flf.mapper.HajCommodityBrandMapper;
import com.flf.mapper.HajCommodityMapper;
import com.flf.mapper.HajCouponBrandMapper;
import com.flf.mapper.HajCouponTypeMapper;
import com.flf.service.HajCouponBrandService;
import com.flf.service.HajCouponCommodityService;
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
 * <b>功能：</b>HajCouponBrandService<br>
 */
@Service("hajCouponBrandService")
public class HajCouponBrandServiceImpl extends BaseServiceImpl implements HajCouponBrandService {
	private final static Logger log = Logger.getLogger(HajCouponBrandServiceImpl.class);

	@Autowired
	private HajCouponBrandMapper dao;

	@Autowired
	private HajCouponTypeMapper couponTypeMapper;

	@Autowired
	private HajCommodityBrandMapper brandMapper;

	@Autowired
	private HajCommodityMapper commodityMapper;

	@Autowired
	private HajCouponCommodityService couponCommodityService;

	@Override
	public HajCouponBrandMapper getDao() {
		return dao;
	}

	@Override
	public List<HajCouponBrand> listPage(HajCouponBrand po) {
		return dao.listPage(po);
	}

	@Override
	public List<Map<String, Object>> getListByBrandId(int brandId) {
		return dao.getListByBrandId(brandId);
	}

	@Override
	public List<HashMap<String, Object>> listWithTreeNodes(Integer couponId) {
		List<HashMap<String, Object>> list = new ArrayList<>();
		HashMap<String, Object> map;

		List<HajCommodityBrand> listAll = brandMapper.listAll();

		List<Integer> couponBrandList = dao.listByCoupon(couponId);

		for (HajCommodityBrand brand : listAll) {
			map = new HashMap<>();
			map.put("id", brand.getId());
			map.put("name", brand.getName());
			map.put("pId", "0");
			map.put("open", "true");
			if (couponBrandList.size() > 0 && couponBrandList.contains(brand.getId())) {
				map.put("checked", "true");
			}
			list.add(map);
		}
		return list;
	}

	@Override
	public boolean save(Integer couponId, List<Integer> brandList) throws Exception {
		// 先清空之前选择的所有该优惠券下的品牌
		dao.deleteByCoupon(couponId);

		// 品类跟品牌只能二选一，所以清掉另一个
		couponTypeMapper.deleteByCoupon(couponId);

		// 重新添加
		if (brandList != null && brandList.size() > 0) {
			dao.addBatch(brandList, couponId);
		}

		// 将品牌下的商品加入到该优惠券中
		List<String> commodityList = commodityMapper.listByBrandIds(brandList);
		couponCommodityService.save(couponId, commodityList);

		return true;
	}

	@Override
	public List<Integer> listCouponIdByBrandId(Integer brandId) {
		return dao.listCouponIdByBrandId(brandId);
	}

}
