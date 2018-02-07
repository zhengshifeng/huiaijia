package com.flf.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.service.BaseServiceImpl;
import com.flf.entity.HajFrontUserUpdateLog;
import com.flf.mapper.HajFrontUserUpdateLogMapper;
import com.flf.service.HajFrontUserUpdateLogService;

/**
 * 
 * <br>
 * <b>功能：</b>HajFrontUserUpdateLogService<br>
 */
@Service("hajFrontUserUpdateLogService")
public class HajFrontUserUpdateLogServiceImpl extends BaseServiceImpl implements HajFrontUserUpdateLogService {
	private final static Logger log = Logger.getLogger(HajFrontUserUpdateLogServiceImpl.class);

	@Autowired
	private HajFrontUserUpdateLogMapper dao;

	@Override
	public HajFrontUserUpdateLogMapper getDao() {
		return dao;
	}

	@Override
	public List<HajFrontUserUpdateLog> listPage(HajFrontUserUpdateLog vo) {
		return dao.listPage(vo);
	}

}
