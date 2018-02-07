package com.flf.mapper;

import com.base.dao.BaseDao;
import com.flf.entity.HajCouponBrand;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 *
 * <br>
 * <b>功能：</b>HajCouponBrandDao<br>
 */
public interface HajCouponBrandMapper extends BaseDao {

	List<HajCouponBrand> listPage(HajCouponBrand po);

	List<Map<String, Object>> getListByBrandId(int brandId);

	List<Integer> listByCoupon(Integer couponId);

	void deleteByCoupon(Integer couponId);

	void addBatch(@Param("list") List<Integer> list, @Param("couponId") Integer couponId);

	List<Integer> listCouponIdByBrandId(Integer brandId);

}
