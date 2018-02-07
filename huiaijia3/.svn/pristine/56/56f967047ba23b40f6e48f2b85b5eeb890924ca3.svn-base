package com.flf.service.impl;

import com.base.service.BaseServiceImpl;
import com.flf.entity.SystemConfiguration;
import com.flf.mapper.SystemConfigurationMapper;
import com.flf.service.SystemConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>SystemConfigurationService<br>
 */
@Service("systemConfigurationService")
public class SystemConfigurationServiceImpl extends BaseServiceImpl implements SystemConfigurationService {

	@Autowired
	private SystemConfigurationMapper dao;

	@Override
	public SystemConfigurationMapper getDao() {
		return dao;
	}

	@Override
	public List<SystemConfiguration> listPageConfig(SystemConfiguration configuration) {
		return dao.listPageConfig(configuration);
	}

	@Override
	public String getValueByName(String name) {
		return dao.getValueByName(name);
	}

	@Override
	public SystemConfiguration getConfigurationByName(String name) {
		return dao.getConfigurationByName(name);
	}

	@Override
	public void updateByName(SystemConfiguration systemConfiguration) {
		 dao.updateByName(systemConfiguration);
	}

}
