package com.flf.mapper;

import com.base.dao.BaseDao;
import com.flf.entity.HajCouponInfo;
import com.flf.entity.HajCouponUseInfoVo;

import java.util.List;

/**
 *
 * <br>
 * <b>功能：</b>HajCouponInfoDao<br>
 */
public interface HajCouponInfoMapper extends BaseDao {

	List<HajCouponInfo> listPage(HajCouponInfo po);

	HajCouponInfo getCouponInfoByNo(String couponNo);

	Integer checkTheSameCoupon(String couponNo);

	List<HajCouponUseInfoVo> listPageCouponUseInfo(HajCouponUseInfoVo vo);

	List<HajCouponInfo> listTodayInvalidCoupon();

	List<HajCouponInfo> listByCouponIds(List<String> couponList);

	Double queryRateById(Integer couponId);

}
