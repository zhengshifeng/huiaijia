package com.flf.controller;

import com.base.controller.BaseController;
import com.flf.entity.HajSpecialTopic;
import com.flf.entity.User;
import com.flf.service.HajAreasService;
import com.flf.service.HajSpecialTopicService;
import com.flf.service.RedisSpringProxy;
import com.flf.util.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * <br>
 * <b>功能：</b>HajSpecialTopicController<br>
 * <br>
 */
@Controller
@RequestMapping(value = "/hajSpecialTopic")
public class HajSpecialTopicController extends BaseController{

	private final static Logger log = Logger.getLogger(HajSpecialTopicController.class);

	@Autowired(required = false)
	private HajSpecialTopicService service;

	@Autowired(required = false)
	private RedisSpringProxy redisSpringProxy;

	@Autowired(required = false)
	private HajAreasService areasService;

	@RequestMapping(value = "/list")
	public ModelAndView list(HajSpecialTopic vo, HttpSession session) {
		ModelAndView mv = new ModelAndView();

		userSession = (User) session.getAttribute(Const.SESSION_USER);
		vo.setAreaCode(userSession.getAreaCode());

		List<HajSpecialTopic> specialTopicList = service.listPage(vo);

		mv.addObject("vo", vo);
		mv.addObject("specialTopicList", specialTopicList);
		mv.setViewName("specialTopicList");
		return mv;
	}

	@RequestMapping(value = "/add")
	public ModelAndView add(HttpSession session) throws Exception {
		ModelAndView mv = new ModelAndView();
		userSession = (User) session.getAttribute(Const.SESSION_USER);

		mv.addObject("cityList", areasService.listCity());
		mv.addObject("isAdmin", Tools.isEmpty(userSession.getAreaCode()));
		mv.addObject("userSession", userSession);
		mv.setViewName("specialTopicEdit");
		return mv;
	}

	@RequestMapping(value = "/edit")
	public ModelAndView edit(@RequestParam Integer id, HttpSession session) throws Exception {
		ModelAndView mv = new ModelAndView();
		userSession = (User) session.getAttribute(Const.SESSION_USER);

		HajSpecialTopic vo = service.queryById(id);
		redisSpringProxy.deletePattern("specialTopicCommodityList_*");
		mv.addObject("vo", vo);
		mv.addObject("cityList", areasService.listCity());
		mv.addObject("isAdmin", Tools.isEmpty(userSession.getAreaCode()));
		mv.addObject("userSession", userSession);
		mv.setViewName("specialTopicEdit");
		return mv;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public void save(HajSpecialTopic po, PrintWriter out, HttpSession session) throws Exception {
		String result = "success";
		if (po != null) {
			// 默认按照当前登录用户
			userSession = (User) session.getAttribute(Const.SESSION_USER);

			// 超管修改商品按照前台选择的areaCode保存对应的值
			if (Tools.isEmpty(userSession.getAreaCode())) {
				po.setAreaCode(po.getAreaCode());
			} else {
				po.setAreaCode(userSession.getAreaCode());
			}

			if (po.getInvalid() == null) {
				po.setInvalid(1);
			}
			if (po.getId() == null) {
				service.add(po);
			} else {
				service.updateBySelective(po);
			}
			redisSpringProxy.deletePattern("specialTopicCommodityList_*");
			redisSpringProxy.deletePattern("appConfig_index_*");
		} else {
			result = "error";
		}

		out.print(result);
		out.close();
	}

	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public void uploadFile(@RequestParam("fileName") MultipartFile file, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("application/json;charset=UTF-8");

		String subPath = "special_topic" + File.separator //
				+ DateUtil.date2Str(new Date(), false) + File.separator;

		String filePath = FileUpload.uploadFile(file, subPath, request);
		Map<String, Object> result = new HashMap<>();
		if (!"".equals(filePath)) {
			result.put("filePath", filePath);
			result.put("result", "success");
		} else {
			result.put("result", "error");
		}
		JSONUtils.printObject(result, response);
	}
}
