package com.flf.service;

import java.util.List;
import java.util.Map;

import com.base.service.BaseService;

/**
 * 
 * <br>
 * <b>功能：</b>HajUserFamilyService<br>
 */
public interface HajUserFamilyService extends BaseService {

	Map<String, Object> queryPriceByName(String name) throws Exception;

	List<Map<String, Object>> getAllCommodityList() throws Exception;

}
