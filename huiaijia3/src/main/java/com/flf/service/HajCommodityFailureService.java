package com.flf.service;

import java.util.HashMap;
import java.util.List;

import com.base.service.BaseService;
import com.flf.entity.HajCommodityFailure;
import com.flf.entity.HajCommodityVo;

public interface HajCommodityFailureService extends BaseService {

	List<HashMap<String, Object>> getCommodityList(HajCommodityVo vo);

	Integer isExist(HajCommodityFailure commodityFailure);

	List<Integer> getCommodityIdsByComnunityId(Integer communityId);

}
