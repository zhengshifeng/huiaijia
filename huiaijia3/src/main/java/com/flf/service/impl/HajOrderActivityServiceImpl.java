package com.flf.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.service.BaseServiceImpl;
import com.flf.mapper.HajOrderActivityMapper;
import com.flf.service.HajOrderActivityService;

/**
 * 
 * <br>
 * <b>功能：</b>HajOrderActivityService<br>
 */
@Service("hajOrderActivityService")
public class HajOrderActivityServiceImpl extends BaseServiceImpl implements HajOrderActivityService {

	@Autowired
	private HajOrderActivityMapper dao;

	@Override
	public HajOrderActivityMapper getDao() {
		return dao;
	}

}
