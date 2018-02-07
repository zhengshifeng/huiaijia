package com.flf.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.service.BaseServiceImpl;
import com.flf.entity.HajWarehouse;
import com.flf.mapper.HajWarehouseMapper;
import com.flf.service.HajWarehouseService;

/**
 * 
 * <br>
 * <b>功能：</b>HajWarehouseService<br>
 */
@Service("hajWarehouseService")
public class HajWarehouseServiceImpl extends BaseServiceImpl implements HajWarehouseService {
	private final static Logger log = Logger.getLogger(HajWarehouseServiceImpl.class);

	@Autowired
	private HajWarehouseMapper dao;

	@Override
	public HajWarehouseMapper getDao() {
		return dao;
	}

	@Override
	public List<Map<String, Object>> listPageHajWarehouse(HajWarehouse warehouse) {
		return dao.listPageHajWarehouse(warehouse);
	}

	@Override
	public List<Map<String, Object>> getAllWareHouseList(String areaCode) {
		return dao.getAllWareHouseList(areaCode);
	}

	@Override
	public List<Map<String, Object>> getWareHouseByName(HajWarehouse warehouse) {
		return dao.getWareHouseByName(warehouse);
	}

}
