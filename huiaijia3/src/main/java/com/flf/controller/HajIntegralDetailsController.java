package com.flf.controller;

import com.flf.entity.HajIntegralDetails;
import com.flf.service.HajIntegralDetailsService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>提供积分明细在后台管理中的功能<br>
 * <br>
 */ 
@Controller
@RequestMapping(value = "/integralDetails")
public class HajIntegralDetailsController {
	
	private final static Logger log = Logger.getLogger(HajIntegralDetailsController.class);
	
	@Autowired(required = false)
	private HajIntegralDetailsService service;

	@RequestMapping(value = "/list")
	public ModelAndView list(HajIntegralDetails vo) {
		ModelAndView mv = new ModelAndView();

		List<HajIntegralDetails> list = service.listPage(vo);
		mv.addObject("vo", vo);
		mv.addObject("list", list);

		mv.setViewName("integralDetailsList");
		return mv;
	}
}
