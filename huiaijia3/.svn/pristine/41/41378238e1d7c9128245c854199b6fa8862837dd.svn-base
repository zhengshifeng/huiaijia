package com.flf.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.base.service.BaseServiceImpl;
import java.util.List;
import com.flf.entity.HajUserRating;
import com.flf.mapper.HajUserRatingMapper;
import com.flf.service.HajUserRatingService;

/**
 * 
 * <br>
 * <b>功能：</b>HajUserRatingService<br>
 */
@Service("hajUserRatingService")
public class HajUserRatingServiceImpl extends BaseServiceImpl implements HajUserRatingService {
	private final static Logger log = Logger.getLogger(HajUserRatingServiceImpl.class);
	
	@Autowired
	private HajUserRatingMapper dao;
 
	@Override
	public HajUserRatingMapper getDao() {
		return dao;
	}

	@Override
	public List<HajUserRating> listPage(HajUserRating po) {
		return dao.listPage(po);
	}

}
