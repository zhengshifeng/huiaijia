package com.flf.service.impl;

import com.base.criteria.Criteria;
import com.base.criteria.PromotionAreaCriteria;
import com.base.service.BaseServiceImpl;
import com.flf.entity.HajPromotionArea;
import com.flf.mapper.HajPromotionAreaMapper;
import com.flf.service.HajPromotionAreaService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * <br>
 * <b>功能：</b>HajPromotionAreaService<br>
 */
@Service("hajPromotionAreaService")
public class HajPromotionAreaServiceImpl extends BaseServiceImpl implements HajPromotionAreaService {
	private final static Logger log = Logger.getLogger(HajPromotionAreaServiceImpl.class);

	@Autowired
	private HajPromotionAreaMapper dao;

	@Override
	public HajPromotionAreaMapper getDao() {
		return dao;
	}

	@Override
	public List<HajPromotionArea> listPage(HajPromotionArea vo) {
		return dao.listPage(vo);
	}

	@Override
	public List<Map<String, Object>>  getPromotionAreaList(PromotionAreaCriteria criteria) throws Exception {
		return dao.getPromotionAreaList(criteria);
	}

	@Override
	public HashMap<String, Object> getBannerByCategory(Integer categoryId, String areaCode) {
		return dao.getBannerByCategory(categoryId, areaCode);
	}


	@Override
	public HashMap<String, Object> getCategoryBannerByCategory(Integer areaType, Integer oneId, String areaCode) {
		return dao.getCategoryBannerByCategory(areaType, oneId,areaCode);
	}


	@Override
	public List<HajPromotionArea> queryByOtherList(Criteria criteria) {
		return dao.queryByOtherList(criteria);
	}


}
