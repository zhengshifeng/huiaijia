package com.flf.mapper;


import com.base.dao.BaseDao;
import com.flf.entity.SystemConfiguration;

import java.util.List;
/**
 * 
 * <br>
 * <b>功能：</b>SystemConfigurationDao<br>
 */
public interface SystemConfigurationMapper extends BaseDao {

	List<SystemConfiguration> listPageConfig(SystemConfiguration configuration);

	String getValueByName(String name);

	SystemConfiguration getConfigurationByName(String name);

    void updateByName(SystemConfiguration systemConfiguration);
}
