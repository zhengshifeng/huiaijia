package com.flf.service.impl;

import com.base.service.BaseServiceImpl;
import com.flf.entity.HajCommodityPromotionArea;
import com.flf.mapper.HajCommodityPromotionAreaMapper;
import com.flf.service.HajCommodityPromotionAreaService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>HajCommodityPromotionAreaService<br>
 */
@Service("hajCommodityPromotionAreaService")
public class HajCommodityPromotionAreaServiceImpl extends BaseServiceImpl implements HajCommodityPromotionAreaService {
	private final static Logger log = Logger.getLogger(HajCommodityPromotionAreaServiceImpl.class);
	
	@Autowired
	private HajCommodityPromotionAreaMapper dao;
 
	@Override
	public HajCommodityPromotionAreaMapper getDao() {
		return dao;
	}

	@Override
	public List<HajCommodityPromotionArea> listPage(HajCommodityPromotionArea po) {
		return dao.listPage(po);
	}

}
