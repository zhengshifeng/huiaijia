package com.flf.service;

import java.util.List;
import java.util.Map;

import com.base.service.BaseService;
import com.flf.entity.HajWarehouse;

/**
 * 
 * <br>
 * <b>功能：</b>HajWarehouseService<br>
 */
public interface HajWarehouseService extends BaseService {

	List<Map<String, Object>> listPageHajWarehouse(HajWarehouse warehouse);

	List<Map<String, Object>> getAllWareHouseList(String areaCode);

	List<Map<String, Object>> getWareHouseByName(HajWarehouse warehouse);

}
