package com.flf.service.impl;

import com.base.service.BaseServiceImpl;
import com.flf.entity.HajCommunitySorter;
import com.flf.mapper.HajCommunitySorterMapper;
import com.flf.service.HajCommunitySorterService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>HajCommunitySorterService<br>
 */
@Service("hajCommunitySorterService")
public class HajCommunitySorterServiceImpl extends BaseServiceImpl implements HajCommunitySorterService {
	private final static Logger log = Logger.getLogger(HajCommunitySorterServiceImpl.class);
	
	@Autowired
	private HajCommunitySorterMapper dao;
 
	@Override
	public HajCommunitySorterMapper getDao() {
		return dao;
	}

	@Override
	public List<HajCommunitySorter> listPage(HajCommunitySorter po) {
		return dao.listPage(po);
	}

}
