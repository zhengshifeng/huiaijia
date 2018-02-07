package com.flf.mapper;

import com.base.dao.BaseDao;
import com.flf.entity.HajCouponResidential;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 * <br>
 * <b>功能：</b>HajCouponResidentialDao<br>
 */
public interface HajCouponResidentialMapper extends BaseDao {

	List<HajCouponResidential> listPage(HajCouponResidential po);

	List<Integer> listResidentialByCoupon(Integer couponId);

	void deleteByCoupon(Integer couponId);

	List<HajCouponResidential> getIsResidential(HajCouponResidential couponResidential);

	int addBatch(@Param("list") List<Integer> list, @Param("couponId") Integer couponId);

}
