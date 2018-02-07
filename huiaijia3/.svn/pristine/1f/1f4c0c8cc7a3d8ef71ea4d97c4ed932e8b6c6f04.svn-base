package com.flf.service.impl;

import com.base.service.BaseServiceImpl;
import com.flf.entity.HajAreas;
import com.flf.mapper.HajAreasMapper;
import com.flf.service.HajAreasService;
import com.flf.util.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>HajAreasService<br>
 */
@Service("hajAreasService")
public class HajAreasServiceImpl extends BaseServiceImpl implements HajAreasService {
	@Autowired
	private HajAreasMapper dao;

	@Override
	public HajAreasMapper getDao() {
		return dao;
	}

	@Override
	public List<HajAreas> getAreaByPCode(String pCode) throws Exception {
		return dao.getAreaByPCode(pCode);
	}

	@Override
	public HajAreas getAreaByCode(String code) throws Exception {
		return dao.getAreaByCode(code);
	}

	@Override
	public HajAreas getCodeByAreas(HashMap<String, String> params) {
		return dao.getCodeByAreas(params);
	}

	@Override
	public void putAreaList(ModelAndView mv, String provinceCode, String cityCode) {
		// 省份数据列表
		List<HajAreas> areaList = dao.getAreaByPCode("0");
		mv.addObject("provinceList", areaList);
		// 根据查询的地区设置回显的地区数据
		if (Tools.notEmpty(provinceCode) && !"0".equals(provinceCode)) {
			areaList = dao.getAreaByPCode(provinceCode);
			mv.addObject("cityList", areaList);

			if (Tools.notEmpty(cityCode) && !"0".equals(cityCode)) {
				areaList = dao.getAreaByPCode(cityCode);
				mv.addObject("communityList", areaList);
			}
		}
	}

	@Override
	public List<HajAreas> listCity() {
		return dao.listCity();
	}

	@Override
	public List<HajAreas> getAreaProvinceList() {
		return dao.getAreaProvinceList();
	}

	@Override
	public HajAreas getAreaByCity(String city) {
		return dao.getAreaByCity(city);
	}
}
