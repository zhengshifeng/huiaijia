package com.flf.service;

import com.base.service.BaseService;
import com.flf.entity.HajAreas;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>HajAreasService<br>
 */
public interface HajAreasService extends BaseService {

	List<HajAreas> getAreaByPCode(String pCode) throws Exception;

	HajAreas getAreaByCode(String code) throws Exception;

	HajAreas getCodeByAreas(HashMap<String, String> params);

	/**
	 * 根据省与市的编码获取3级地区列表，存到ModelAndView中，用于视图层数据回显
	 * 
	 * @param mv
	 * @param provinceCode
	 * @param cityCode
	 * @throws Exception
	 */
	void putAreaList(ModelAndView mv, String provinceCode, String cityCode);

	/**
	 * 获取城市的数据列表
	 * 
	 * @return
	 */
	List<HajAreas> listCity();

	/**
	 * 获取省的接口
	 * 
	 * @return
	 */
	List<HajAreas> getAreaProvinceList();

	HajAreas getAreaByCity(String city);

}
