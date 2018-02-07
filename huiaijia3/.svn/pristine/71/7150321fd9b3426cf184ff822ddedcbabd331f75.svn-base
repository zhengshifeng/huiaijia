package com.flf.service;

import com.base.service.BaseService;
import com.flf.entity.HajCouponBrand;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * <br>
 * <b>功能：</b>HajCouponBrandService<br>
 */
public interface HajCouponBrandService extends BaseService {

	List<HajCouponBrand> listPage(HajCouponBrand po);

	/**
	 * 根据品牌id查找优惠券ID列表
	 *
	 * @param brandId
	 * @return
	 */
	List<Map<String, Object>> getListByBrandId(int brandId);

	List<HashMap<String, Object>> listWithTreeNodes(Integer couponId);

	boolean save(Integer couponId, List<Integer> brandList) throws Exception;

	List<Integer> listCouponIdByBrandId(Integer brandId);

}
