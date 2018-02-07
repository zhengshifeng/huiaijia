package com.flf.service;

import com.base.service.BaseService;
import com.flf.entity.HajCommodityCategory;

import java.util.HashMap;
import java.util.List;

/**
 *
 * <br>
 * <b>功能：</b>HajCommodityCategoryService<br>
 */
public interface HajCommodityCategoryService extends BaseService {

	List<HajCommodityCategory> listPage(HajCommodityCategory po);

	List<HashMap<String, Object>> list4app(HajCommodityCategory dto);

	/**
	 * 批量添加商品到三级类目中
	 * @param list
	 */
	void saveCommoditysToThreeCate(List<HajCommodityCategory> list  );

	/**
	 * 批量移除类目中的商品
	 * @param list
	 */
	void deleteCommodityFromCategory(List<HajCommodityCategory> list );






}
