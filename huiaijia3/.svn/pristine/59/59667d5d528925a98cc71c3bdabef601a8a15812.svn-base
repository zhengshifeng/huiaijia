package com.flf.mapper;

import com.base.dao.BaseDao;
import com.flf.entity.HajCouponCommodity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 * <br>
 * <b>功能：</b>HajCouponCommodityDao<br>
 */
public interface HajCouponCommodityMapper extends BaseDao {

	List<HajCouponCommodity> listPage(HajCouponCommodity po);

	List<String> listCommodityByCoupon(Integer couponId);

	void deleteByCoupon(Integer couponId);

	int addBatch(List<HajCouponCommodity> list);

	List<HajCouponCommodity> getHajCouponCommodity(@Param("couponId") int couponId,
			@Param("commodityNo") String commodityNo);

	int addBatch(@Param("list") List<String> list, @Param("couponId") Integer couponId);

	void deleteAfterCommodityUpdate(@Param("commodityNo") String commodityNo, @Param("list") List<Integer> couponIds);

}
