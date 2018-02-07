package com.flf.service;

import com.base.service.BaseService;
import com.flf.entity.HajActivityCommodity;

import java.util.HashMap;
import java.util.List;

/**
 *
 * <br>
 * <b>功能：</b>HajActivityCommodityService<br>
 */
public interface HajActivityCommodityService extends BaseService {

	List<HajActivityCommodity> listPage(HajActivityCommodity po);

	List<HashMap<String, Object>> list4app(HajActivityCommodity dto);

	void deleteCommodityToActivity(Integer activityId);

}
