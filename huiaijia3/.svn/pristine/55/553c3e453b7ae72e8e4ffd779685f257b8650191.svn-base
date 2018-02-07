package com.flf.service;

import java.util.List;
import java.util.Map;

import com.base.service.BaseService;
import com.flf.entity.HajCouponResidential;

/**
 *
 * <br>
 * <b>功能：</b>HajCouponResidentialService<br>
 */
public interface HajCouponResidentialService extends BaseService {

	List<HajCouponResidential> listPage(HajCouponResidential po);

	List<Map<String, Object>> listWithTreeNodes(HajCouponResidential po);

	void deleteByCoupon(Integer couponId);

	boolean save(Integer couponId, List<Integer> communityList) throws Exception;

	boolean getIsResidential(Integer villageId, Integer id);

}
