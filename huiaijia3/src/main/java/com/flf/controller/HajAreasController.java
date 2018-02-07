package com.flf.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.flf.entity.HajAreas;
import com.flf.service.HajAreasService;
import com.flf.util.JSONUtils;

/**
 * 
 * <br>
 * <b>功能：</b>HajAreasAppController<br>
 * <br>
 */
@Controller
@RequestMapping(value = "/areas")
public class HajAreasController {

	@Autowired(required = false)
	private HajAreasService service;

	@RequestMapping(value = "/getAreaByPCode")
	public void getAreaByPCode(String pCode, HttpServletResponse response) throws Exception {
		List<HajAreas> areaList = service.getAreaByPCode(pCode);
		JSONUtils.printArray(areaList, response);
	}

}
