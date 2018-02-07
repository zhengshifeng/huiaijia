package com.flf.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.service.BaseServiceImpl;
import com.flf.entity.HajSpecialTopicCommodity;
import com.flf.entity.HajSpecialTopicCommodityVo;
import com.flf.mapper.HajSpecialTopicCommodityMapper;
import com.flf.service.HajSpecialTopicCommodityService;

/**
 * 
 * <br>
 * <b>功能：</b>HajSpecialTopicCommodityService<br>
 */
@Service("hajSpecialTopicCommodityService")
public class HajSpecialTopicCommodityServiceImpl extends BaseServiceImpl implements HajSpecialTopicCommodityService {
	private final static Logger log = Logger.getLogger(HajSpecialTopicCommodityServiceImpl.class);

	@Autowired
	private HajSpecialTopicCommodityMapper dao;

	@Override
	public HajSpecialTopicCommodityMapper getDao() {
		return dao;
	}

	@Override
	public List<HajSpecialTopicCommodityVo> getCommodityList(HajSpecialTopicCommodity po) {
		return dao.getCommodityList(po);
	}

}
