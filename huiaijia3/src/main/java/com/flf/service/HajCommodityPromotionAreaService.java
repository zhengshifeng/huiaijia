package com.flf.service;

import com.base.service.BaseService;
import com.flf.entity.HajCommodityPromotionArea;

import java.util.List;

/**
 *
 * <br>
 * <b>功能：</b>HajCommodityPromotionAreaService<br>
 */
public interface HajCommodityPromotionAreaService extends BaseService {

	List<HajCommodityPromotionArea> listPage(HajCommodityPromotionArea po);

}
