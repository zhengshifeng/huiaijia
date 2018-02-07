package com.flf.controller.app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.flf.entity.HajAreas;
import com.flf.entity.HajAreasList;
import com.flf.entity.HajCity;
import com.flf.service.HajAreasService;
import com.flf.util.JSONUtils;
import com.flf.util.MD5Tools;

/**
 * 
 * <br>
 * <b>功能：</b>HajAreasAppController<br>
 * <br>
 */
@Controller
@RequestMapping(value = "/hajAreas")
public class HajAreasAppController {

	@Autowired(required = false)
	private HajAreasService service;

	/**
	 * 获取省的接口
	 * 
	 * @param sign
	 * @param response
	 */
	@RequestMapping(value = "/getAreaProvince")
	@ResponseBody
	public void getAreaProvince(@RequestParam String sign, HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			if (MD5Tools.checkSign(sign)) {
				List<HajAreas> provinceList = service.getAreaProvinceList();
				if (null != provinceList && provinceList.size() > 0) {
					jsonMap.put("error", "0");
					jsonMap.put("provinceList", provinceList);
					jsonMap.put("msg", "成功");
				} else {
					jsonMap.put("error", "0");
					jsonMap.put("msg", "失败");
					jsonMap.put("provinceList", "");
				}

				JSONUtils.printObject(jsonMap, response);
			} else {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "签名异常");
				JSONUtils.printObject(jsonMap, response);
			}

		} catch (Exception e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
			try {
				JSONUtils.printObject(jsonMap, response);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}

	}

	/**
	 * 根据省获取市的接口
	 * 
	 * @param sign
	 * @param response
	 */
	@RequestMapping(value = "/getAreaCityByPcode")
	@ResponseBody
	public void getAreaCityByPcode(@RequestParam String sign, String pCode, HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			if (MD5Tools.checkSign(sign)) {
				List<HajAreas> cityList = service.getAreaByPCode(pCode);
				if (null != cityList && cityList.size() > 0) {
					jsonMap.put("error", "0");
					jsonMap.put("cityList", cityList);
					jsonMap.put("msg", "成功");
				} else {
					jsonMap.put("error", "0");
					jsonMap.put("msg", "失败");
					jsonMap.put("cityList", "");
				}

				JSONUtils.printObject(jsonMap, response);
			} else {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "签名异常");
				JSONUtils.printObject(jsonMap, response);
			}

		} catch (Exception e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
			try {
				JSONUtils.printObject(jsonMap, response);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}

	}

	/**
	 * 获取省的接口
	 * 
	 * @param sign
	 * @param response
	 */
	@RequestMapping(value = "/getAreaList")
	@ResponseBody
	public void getAreaList(@RequestParam String sign, HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			if (MD5Tools.checkSign(sign)) {
				List<HajAreasList> HajAreasList = new ArrayList();
				List<HajCity> cityList = new ArrayList();
				HajAreasList areaList = null;
				List<HajAreas> provinceList = service.getAreaProvinceList();
				if (null != provinceList && provinceList.size() > 0) {
					for (HajAreas area : provinceList) {
						areaList = new HajAreasList();
						areaList.setAreaCode(area.getCode());
						areaList.setCode(area.getCode());
						areaList.setName(area.getName());
						List<HajAreas> cityLists = service.getAreaByPCode(area.getCode());
						for (HajAreas city : cityLists) {
							HajCity c = new HajCity();
							c.setAreaCode(city.getCode());
							c.setName(city.getName());
							c.setCode(city.getCode());
							List<HajAreas> hajAreas = service.getAreaByPCode(city.getCode());
							c.setHajAreas(hajAreas);
							cityList.add(c);
						}
						areaList.setHajCitys(cityList);
						HajAreasList.add(areaList);
					}
					jsonMap.put("error", "0");
					jsonMap.put("areaList", HajAreasList);
					jsonMap.put("msg", "成功");
				} else {
					jsonMap.put("error", "0");
					jsonMap.put("msg", "失败");
					jsonMap.put("areaList", "");
				}

				JSONUtils.printObject(jsonMap, response);
			} else {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "签名异常");
				JSONUtils.printObject(jsonMap, response);
			}

		} catch (Exception e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
			try {
				JSONUtils.printObject(jsonMap, response);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}

	}

}
