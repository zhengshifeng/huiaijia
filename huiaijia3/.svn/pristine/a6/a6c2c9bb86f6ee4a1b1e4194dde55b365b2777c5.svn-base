package com.flf.service;
import com.base.service.BaseService;
import com.flf.entity.SystemConfiguration;

import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>SystemConfigurationService<br>
 */
public interface SystemConfigurationService extends BaseService {

	List<SystemConfiguration> listPageConfig(SystemConfiguration configuration);
	
	//通过配置名称获取配置值
	String getValueByName(String name);
	
	SystemConfiguration getConfigurationByName(String name);
	
	void updateByName(SystemConfiguration systemConfiguration);
}
