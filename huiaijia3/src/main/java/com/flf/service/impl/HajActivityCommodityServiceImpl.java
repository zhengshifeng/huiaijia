package com.flf.service.impl;

import com.base.service.BaseServiceImpl;
import com.flf.entity.HajActivityCommodity;
import com.flf.mapper.HajActivityCommodityMapper;
import com.flf.service.HajActivityCommodityService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>HajActivityCommodityService<br>
 */
@Service("hajActivityCommodityService")
public class HajActivityCommodityServiceImpl extends BaseServiceImpl implements HajActivityCommodityService {
	private final static Logger log = Logger.getLogger(HajActivityCommodityServiceImpl.class);
	
	@Autowired
	private HajActivityCommodityMapper dao;
 
	@Override
	public HajActivityCommodityMapper getDao() {
		return dao;
	}

	@Override
	public List<HajActivityCommodity> listPage(HajActivityCommodity po) {
		return dao.listPage(po);
	}

	@Override
	public List<HashMap<String, Object>> list4app(HajActivityCommodity dto) {
		return dao.list4app(dto);
	}

	@Override
	public void deleteCommodityToActivity(Integer activityId) {
		dao.deleteCommodityToActivity(activityId);
	}
}
