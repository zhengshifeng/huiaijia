package com.flf.mapper;

import com.base.dao.BaseDao;
import com.flf.entity.HajCouponType;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 *
 * <br>
 * <b>功能：</b>HajCouponTypeDao<br>
 */
public interface HajCouponTypeMapper extends BaseDao {

	List<HajCouponType> listPage(HajCouponType po);

	List<Map<String, Object>> getListByTypeId(int commodityType);

	List<Integer> listTypeByCoupon(Integer couponId);

	void deleteByCoupon(Integer couponId);

	void addBatch(@Param("list") List<Integer> list, @Param("couponId") Integer couponId);

	List<Integer> listCouponIdByTypeId(Integer typeId);
}
