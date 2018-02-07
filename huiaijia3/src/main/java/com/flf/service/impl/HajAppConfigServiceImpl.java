package com.flf.service.impl;

import com.base.service.BaseServiceImpl;
import com.flf.entity.HajAppConfig;
import com.flf.mapper.HajAppConfigMapper;
import com.flf.service.HajAppConfigService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>HajAppConfigService<br>
 */
@Service("hajAppConfigService")
public class HajAppConfigServiceImpl extends BaseServiceImpl implements HajAppConfigService {
	private final static Logger log = Logger.getLogger(HajAppConfigServiceImpl.class);
	
	@Autowired
	private HajAppConfigMapper dao;
 
	@Override
	public HajAppConfigMapper getDao() {
		return dao;
	}

	@Override
	public List<HajAppConfig> listPage(HajAppConfig po) {
		return dao.listPage(po);
	}

	@Override
	public HashMap<String, Object> getAll() {
		return dao.getAll();
	}

}
