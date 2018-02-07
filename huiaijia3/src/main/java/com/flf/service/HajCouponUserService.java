package com.flf.service;

import com.base.service.BaseService;
import com.flf.entity.HajCouponInfo;
import com.flf.entity.HajCouponUser;

import java.util.List;
import java.util.Map;

/**
 *
 * <br>
 * <b>功能：</b>HajCouponUserService<br>
 */
public interface HajCouponUserService extends BaseService {

	List<HajCouponUser> listPage(HajCouponUser po);

	List<Map<String, Object>> getCouponListByUserId(Integer userId);

	int getCouponUser(HajCouponUser couponUser);

	List<Map<String, Object>> getCouponCommodityById(Object object, String commodityNos);

	List<Map<String, Object>> getClearUserCouponByDate();

	void updateClearUserCoupon();

	void updateUserUsed(int id);

	void updateUserCouponRead(int userId);

	List<Map<String, Object>> getCouponListByCommodity(Integer userId, String commodityNos);

	List<HajCouponUser> listByCouponId(Integer couponId);

	int updateInvalidateByCouponId(Integer couponId);

	int getCouponCount(Integer id);

	Map<String, Object> saveUserCoupon(int userId, HajCouponInfo couponInfo, Map<String, Object> jsonMap) throws Exception;

	List<String> getNewUserCoupon(Integer userId) throws Exception;

	List<Map<String, Object>> listCouponsByIds(Map<String, Object> map);


	/**
	 * 查询当天派发的优惠卷
	 * @return
	 */
	List<Map<String,Object>> getDistributeCouponByDate();

	List<Map<String,Object>> getOverdueCouponByDate();
}
