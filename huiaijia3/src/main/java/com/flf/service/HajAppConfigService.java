package com.flf.service;

import com.base.service.BaseService;
import com.flf.entity.HajAppConfig;

import java.util.HashMap;
import java.util.List;

/**
 *
 * <br>
 * <b>功能：</b>HajAppConfigService<br>
 */
public interface HajAppConfigService extends BaseService {

	List<HajAppConfig> listPage(HajAppConfig po);

	HashMap<String, Object> getAll();

}
