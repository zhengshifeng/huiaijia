package com.flf.mapper;

import com.base.dao.BaseDao;
import com.flf.entity.HajCouponUser;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * <br>
 * <b>功能：</b>HajCouponUserDao<br>
 */
public interface HajCouponUserMapper extends BaseDao {

	List<HajCouponUser> listPage(HajCouponUser po);

	List<Map<String, Object>> getCouponListByUserId(Integer userId);

	int getCouponUser(HajCouponUser couponUser);

	List<Map<String, Object>> getCouponCommodityById(@Param("couponId") Object couponId,
			@Param("commodityNos") String[] commodityNos);

	List<Map<String, Object>> getClearUserCouponByDate();

	void updateClearUserCoupon(int id);

	void updateUserUsed(int id);

	void updateUserCouponRead(int userId);

	List<Map<String, Object>> getCouponListByCommodity(@Param("userId") Integer userId,
			@Param("commodityNos") String[] commodityNos);

	List<HajCouponUser> listByCouponId(Integer couponId);

	int updateInvalidateByCouponId(Integer couponId);

	int getCouponCount(Integer couponId);

	void updateClearUserCoupon();

	int addBatch(List<HajCouponUser> list);

	HashMap<String, Object> getUserStatus(Integer userId);

	List<Integer> getReceivedCoupons(HashMap<String, Object> map);

	List<Map<String, Object>> listCouponsByIds(Map<String, Object> map);
	/**
	 * 查询当天派发的优惠卷
	 * @return
	 */
	List<Map<String,Object>> getDistributeCouponByDate();
	/**
	 * 查询当天即将过期的优惠卷
	 * @return
	 */
	List<Map<String,Object>> getOverdueCouponByDate();
}
