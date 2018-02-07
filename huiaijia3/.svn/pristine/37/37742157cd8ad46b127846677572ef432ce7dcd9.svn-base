package com.flf.service;

import com.base.service.BaseService;
import com.flf.entity.HajCouponType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * <br>
 * <b>功能：</b>HajCouponTypeService<br>
 */
public interface HajCouponTypeService extends BaseService {

	List<HajCouponType> listPage(HajCouponType po);

	/**
	 * 根据商品分类id查找优惠券ID列表
	 *
	 * @param typeId
	 * @return
	 */
	List<Map<String, Object>> getListByTypeId(int typeId);

	List<HashMap<String, Object>> listWithTreeNodes(Integer couponId);

	boolean save(Integer couponId, List<Integer> typeList) throws Exception;

	List<Integer> listCouponIdByTypeId(Integer typeId);
}
