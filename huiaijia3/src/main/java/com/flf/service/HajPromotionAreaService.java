package com.flf.service;

import com.base.criteria.Criteria;
import com.base.criteria.PromotionAreaCriteria;
import com.base.service.BaseService;
import com.flf.entity.HajPromotionArea;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * <br>
 * <b>功能：</b>HajPromotionAreaService<br>
 */
public interface HajPromotionAreaService extends BaseService {

	List<HajPromotionArea> listPage(HajPromotionArea vo);

	List<Map<String, Object>>  getPromotionAreaList(PromotionAreaCriteria criteria) throws Exception;

	HashMap<String, Object> getBannerByCategory(Integer categoryId, String areaCode);

	HashMap<String, Object> getCategoryBannerByCategory(Integer areaType,Integer oneId,String areaCode);


	List<HajPromotionArea> queryByOtherList(Criteria criteria);
}
