package com.flf.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.service.BaseServiceImpl;
import com.flf.entity.HajSpecialTopic;
import com.flf.mapper.HajSpecialTopicMapper;
import com.flf.service.HajSpecialTopicService;

/**
 * 
 * <br>
 * <b>功能：</b>HajSpecialTopicService<br>
 */
@Service("hajSpecialTopicService")
public class HajSpecialTopicServiceImpl extends BaseServiceImpl implements HajSpecialTopicService {
	private final static Logger log = Logger.getLogger(HajSpecialTopicServiceImpl.class);

	@Autowired
	private HajSpecialTopicMapper dao;

	@Override
	public HajSpecialTopicMapper getDao() {
		return dao;
	}

	@Override
	public List<HajSpecialTopic> listPage(HajSpecialTopic po) {
		return dao.listPage(po);
	}


	@Override
	public List<HajSpecialTopic> getSpecialTopicList() {
		return dao.getSpecialTopicList();
	}
}
