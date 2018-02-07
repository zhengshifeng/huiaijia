package com.flf.service;

import com.base.service.BaseService;
import com.flf.entity.HajCommodityGroupBuying;
import com.flf.entity.HajCommodityGroupBuyingVo;

import java.util.HashMap;
import java.util.List;

/**
 *
 * <br>
 * <b>功能：</b>HajCommodityGroupBuyingService<br>
 */
public interface HajCommodityGroupBuyingService extends BaseService {

	List<HajCommodityGroupBuying> listPage(HajCommodityGroupBuying po);

	List<HashMap<String, Object>> list4app(HajCommodityGroupBuying dto);

	HashMap<String, Object> detail4app(HajCommodityGroupBuying dto);

	HajCommodityGroupBuyingVo getByCommodityId(Integer commodityId);

}
