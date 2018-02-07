package com.flf.service;

import com.base.service.BaseService;
import com.flf.entity.HajCommodity;
import com.flf.entity.HajCouponCommodity;

import java.util.List;
import java.util.Map;

/**
 *
 * <br>
 * <b>功能：</b>HajCouponCommodityService<br>
 */
public interface HajCouponCommodityService extends BaseService {

	List<HajCouponCommodity> listPage(HajCouponCommodity po);

	List<Map<String, Object>> listWithTreeNodes(HajCouponCommodity po);

	void deleteByCoupon(Integer couponId);

	boolean save(Integer couponId, List<String> commodityList) throws Exception;

	void updateCouponCommodity(HajCommodity commodity) throws Exception;

	void deleteAfterCommodityUpdate(String commodityNo, List<Integer> couponIds);
}
