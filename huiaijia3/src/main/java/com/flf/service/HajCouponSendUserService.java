package com.flf.service;

import com.base.service.BaseService;
import com.flf.entity.HajCouponInfo;
import com.flf.entity.HajCouponSendUser;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 *
 * <br>
 * <b>功能：</b>HajCouponSendUserService<br>
 */
public interface HajCouponSendUserService extends BaseService {

	List<HajCouponSendUser> listPage(HajCouponSendUser po);

	void deleteByCoupon(Integer couponId);

	String batchImport(String filePath, HajCouponInfo couponInfo) throws IOException;

	Map<String,Object> batchImport_new(String filePath, HajCouponInfo couponInfo) throws IOException;

	List<Integer> listSendUserByCoupon(Integer couponId);

}
