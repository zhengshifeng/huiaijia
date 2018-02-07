package com.flf.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.base.dao.BaseDao;
import com.flf.entity.HajWarehouse;

/**
 * 
 * <br>
 * <b>功能：</b>HajWarehouseDao<br>
 */
public interface HajWarehouseMapper extends BaseDao {

	List<Map<String, Object>> listPageHajWarehouse(HajWarehouse warehouse);

	List<Map<String, Object>> getAllWareHouseList(@Param("areaCode") String areaCode);

	List<Map<String, Object>> getWareHouseByName(HajWarehouse warehouse);

}
