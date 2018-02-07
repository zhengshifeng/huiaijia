package com.flf.mapper;

import com.base.dao.BaseDao;
import com.flf.entity.HajCommodityPromotionArea;

import java.util.List;

/**
 *
 * <br>
 * <b>功能：</b>HajCommodityPromotionAreaDao<br>
 */
public interface HajCommodityPromotionAreaMapper extends BaseDao {

	List<HajCommodityPromotionArea> listPage(HajCommodityPromotionArea po);

	/**
	 * 添加指定商品到专区中，如果专区中无此商品则添加，否则不做处理
	 * @param hajCommodityPromotionArea
	 * @return
	 */
	void saveToCommodityZone(HajCommodityPromotionArea hajCommodityPromotionArea);



	/**
	 * 从指定专区中删除指定商品
	 * @param hajCommodityPromotionArea
	 */
	void deleteCommodityFromTheZone(HajCommodityPromotionArea hajCommodityPromotionArea);
}
