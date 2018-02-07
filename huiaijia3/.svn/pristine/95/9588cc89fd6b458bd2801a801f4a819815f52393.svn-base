package com.flf.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.flf.entity.HajFrontUserUpdateLog;
import com.flf.service.HajFrontUserUpdateLogService;

/**
 * 
 * <br>
 * <b>功能：</b>HajFrontUserUpdateLogController<br>
 * <br>
 */
@Controller
@RequestMapping(value = "/frontUserUpdateLog")
public class HajFrontUserUpdateLogController {

	@Autowired(required = false)
	private HajFrontUserUpdateLogService service;

	@RequestMapping(value = "/list")
	private ModelAndView list(HajFrontUserUpdateLog vo) {
		ModelAndView mv = new ModelAndView();

		List<HajFrontUserUpdateLog> frontUserUpdateLogList = service.listPage(vo);

		mv.addObject("vo", vo);
		mv.addObject("frontUserUpdateLogList", frontUserUpdateLogList);
		mv.setViewName("frontUserUpdateLogList");
		return mv;
	}

}
