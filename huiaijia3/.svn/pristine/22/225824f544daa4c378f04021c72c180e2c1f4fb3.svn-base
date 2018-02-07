package com.flf.mapper;

import com.base.dao.BaseDao;
import com.flf.entity.HajAreas;

import java.util.HashMap;
import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>HajAreasDao<br>
 */
public interface HajAreasMapper extends BaseDao {

	List<HajAreas> getAreaByPCode(String pCode);

	HajAreas getAreaByCode(String code);

	HajAreas getCodeByAreas(HashMap<String, String> params);

	List<HajAreas> listCity();

	List<HajAreas> getAreaCodeList();

	List<HajAreas> getAreaProvinceList();

	HajAreas getAreaByCity(String city);

	Integer getTotalByCode(String code);



}
