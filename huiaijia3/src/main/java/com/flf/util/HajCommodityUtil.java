package com.flf.util;

import com.flf.entity.HajCommodity;
import com.flf.service.HajCommodityService;

import java.math.BigDecimal;

/**
 * Created by SevenWong on 2017/2/16 15:12.00
 */
public class HajCommodityUtil {

	/**
	 * 清空商品（所有）相关的缓存，重建搜索引擎的索引
	 * 注：这两步操作不能放在同一个service方法中完成，因为事务未提交可能导致脏数据的产生
	 * @param commodityService 商品的service
	 */
	public static void resetCommodityRedisAndESIndex(HajCommodityService commodityService) {
		commodityService.resetCommodityRedis();

		try {
			commodityService.createCommodityIndex();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 清空商品（单个商品）相关的缓存，重建搜索引擎的索引
	 * 注：这两步操作不能放在同一个service方法中完成，因为事务未提交可能导致脏数据的产生
	 * @param commodityService 商品的service
	 * @param commodityId 单个商品的ID
	 */
	public static void resetCommodityRedisAndESIndex(HajCommodityService commodityService, Integer commodityId) {
		commodityService.resetCommodityRedis();

		try {
			commodityService.createCommodityIndex(commodityId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 商品的价格需遵循【成本价 <= 活动价 <= 售价 < 市场参考价】
	 * @param commodity
	 * @return
	 */
	public static boolean checkPrice(HajCommodity commodity) {
		if (commodity != null) {
			if (commodity.getPromotionPrice() != null && commodity.getPromotionPrice().compareTo(BigDecimal.ZERO) > 0) {
				return (commodity.getCostPrice().compareTo(commodity.getPromotionPrice()) <= 0
						&& commodity.getPromotionPrice().compareTo(commodity.getOriginalPrice()) <= 0
						&& commodity.getOriginalPrice().compareTo(commodity.getMarketPrice()) < 0);
			} else {
				return (commodity.getCostPrice().compareTo(commodity.getOriginalPrice()) <= 0
						&& commodity.getOriginalPrice().compareTo(commodity.getMarketPrice()) < 0);
			}
		}
		return false;
	}

	/**
	 * 验证商品价格的合法性。
	 * 先与修改前的价格进行比较，如果有修改，则进行下一步验证。否则直接通过
	 * @param commodity
	 * @param beforeData
	 * @return
	 */
	public static boolean checkPriceWithBeforeData(HajCommodity commodity, HajCommodity beforeData) {
		if (beforeData != null) {
			if (commodity != null) {
				// 校验价格是否有变动
				if (commodity.getCostPrice().compareTo(beforeData.getCostPrice()) == 0
						&& commodity.getOriginalPrice().compareTo(beforeData.getOriginalPrice()) == 0
						&& commodity.getMarketPrice().compareTo(beforeData.getMarketPrice()) == 0
						&& commodity.getPromotionPrice().compareTo(beforeData.getPromotionPrice()) == 0) {
					return true;
				} else {
					return checkPrice(commodity);
				}
			}
		} else {
			return checkPrice(commodity);
		}
		return false;
	}

}
