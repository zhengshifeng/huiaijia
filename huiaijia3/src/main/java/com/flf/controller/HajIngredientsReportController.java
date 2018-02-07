package com.flf.controller;

import com.base.controller.BaseController;
import com.flf.entity.*;
import com.flf.service.*;
import com.flf.util.*;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 *
 * <br>
 * <b>功能：</b>HajIngredientsReportController<br>
 * <br>
 */
@Controller
@RequestMapping(value = "/ingredientsReport")
public class HajIngredientsReportController extends BaseController {

	private final static Logger log = Logger.getLogger(HajIngredientsReportController.class);

	@Autowired(required = false)
	private HajIngredientsReportService reportService;

	@Autowired(required = false)
	private HajIngredientsReportConfService confService;

	@Autowired(required = false)
	private HajAreasService areasService;

	@Autowired(required = false)
	private RedisSpringProxy redisSpringProxy;



	/**
	 * 获取食材检测报告历史信息列表
	 * @param report
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/list")
	public String list(HajIngredientsReport report, Model model, HttpSession session) {

		try {
			userSession = (User) session.getAttribute(Const.SESSION_USER);
			report.setAreaCode(userSession.getAreaCode());

			List<HajIngredientsReport> list = reportService.listPage(report);
			model.addAttribute("reportList", list);
			model.addAttribute("report", report);
			model.addAttribute("cityList", areasService.listCity());


		} catch (Exception e) {
			e.printStackTrace();
		}

		return "ingredientsReportList";
	}



	/**
	 * 跳转到检测报告编辑页面
	 * @param reportId
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/toEdit")
	public ModelAndView toEdit(@RequestParam Integer reportId, HttpSession session) throws Exception {
		ModelAndView mv = new ModelAndView();
		userSession = (User) session.getAttribute(Const.SESSION_USER);

		//reportId==0进入新增报告页面,!=0进入的是编辑报告页面
		if (reportId!=0) {
			//获取检测报告对象
			HajIngredientsReportVo report = reportService.getByReportId(reportId);

			//获取检测报告的分类对象集合
			List<HajIngredientsReportConfVo> confVoList = confService.getByReportId(reportId);
			if (confVoList!=null) {
				for (int i=0;i<confVoList.size();i++) {
					HajIngredientsReportConfVo confVo  = confVoList.get(i);
					if (Tools.isNotEmpty(confVo.getCateimgs())) {
						confVo.setConfImgs(confVo.getCateimgs().split(","));
					}
				}
			}
			report.setConfVoList(confVoList);

			mv.addObject("report", report);
		}
		mv.addObject("cityList", areasService.listCity());
		mv.addObject("isAdmin", Tools.isEmpty(userSession.getAreaCode()));
		mv.addObject("userSession", userSession);
		mv.setViewName("addIngredientsReport");
		return mv;
	}


	/**
	 * 编辑检测报告分类
	 * @param confId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/toEditConf")
	public ModelAndView toEditConf(@RequestParam Integer confId) throws Exception {
		ModelAndView mv = new ModelAndView();

		//获取检测报告的分类对象
		HajIngredientsReportConfVo confVo = confService.getByConfId(confId);
		if (confVo!=null) {
			if (Tools.isNotEmpty(confVo.getCateimgs())) {
				confVo.setConfImgs(confVo.getCateimgs().split(","));
			}
		}

		mv.addObject("confVo", confVo);
		mv.setViewName("addIngredientsReportConf");
		return mv;
	}



	/**
	 * 添加食材检测报告
	 * @param report
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveReport", method = RequestMethod.POST)
	public void saveReport(HajIngredientsReportVo report, HttpServletResponse response) throws Exception {

		Map<String, Object> returnMap = new HashMap<>();
		returnMap.put("error", "0");

		try {
			if (report.getId() != null && report.getId() > 0) {
				reportService.updateBySelective(report);	//更新
			} else {
				reportService.add(report);					//保存信息
			}

			//清除缓存(APP返回检测报告列表时需要重新从数据库获取最新数据)
			redisSpringProxy.deletePattern("HajIngredientsReportList_*");


		} catch (Exception e) {
			e.printStackTrace();
			returnMap.put("error", "1");
		} finally {
			try {
				JSONUtils.printObject(returnMap, response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}




	/**
	 * 跳转添加报告分类
	 * @param reportId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/toConf")
	public ModelAndView toConf(@RequestParam Integer reportId,@RequestParam Integer sort) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.addObject("reportId", reportId);
		mv.addObject("sort", sort);
		mv.setViewName("addIngredientsReportConf");
		return mv;
	}



	@RequestMapping(value = "/delete")
	public void delete(Integer[] id, PrintWriter out) throws Exception {
		reportService.delete((Object[]) id);
		out.print("success");
		out.close();
	}
}
