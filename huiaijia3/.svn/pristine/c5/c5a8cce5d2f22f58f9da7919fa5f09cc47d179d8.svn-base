package com.flf.mapper;

import com.base.dao.BaseDao;
import com.flf.entity.HajCouponSendUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 * <br>
 * <b>功能：</b>HajCouponSendUserDao<br>
 */
public interface HajCouponSendUserMapper extends BaseDao {

	List<HajCouponSendUser> listPage(HajCouponSendUser po);

	List<Integer> listSendUserByCoupon(Integer couponId);

	void deleteByCoupon(Integer couponId);

	int addBatch(@Param("list") List<Integer> list, @Param("couponId") Integer couponId);

}
