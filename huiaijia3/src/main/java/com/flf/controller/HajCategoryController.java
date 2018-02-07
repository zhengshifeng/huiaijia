package com.flf.controller;

import com.flf.entity.HajCategory;
import com.flf.service.HajCategoryService;
import com.flf.service.RedisSpringProxy;
import com.flf.util.JSONUtils;
import com.flf.util.Tools;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <br>
 * <b>功能：</b>HajCategoryController<br>
 * <br>
 */
@Controller
@RequestMapping(value = "/category")
public class HajCategoryController {

	private final static Logger log = Logger.getLogger(HajCategoryController.class);

	@Autowired(required = false)
	private HajCategoryService service;

	@Autowired(required = false)
	private RedisSpringProxy redisSpringProxy;

	@RequestMapping(value = "/list")
	public ModelAndView list(HajCategory vo) {
		ModelAndView mv = new ModelAndView();

		List<HajCategory> categoryList = service.listPage(vo);
		mv.addObject("vo", vo);
		mv.addObject("categoryList", categoryList);
		mv.setViewName("categoryList");

		return mv;
	}

	@RequestMapping(value = "/add")
	public ModelAndView add(@RequestParam String areaCode) throws Exception {
		ModelAndView mv = new ModelAndView();

		HajCategory vo = new HajCategory();
		vo.setAreaCode(areaCode);
		mv.addObject("vo", vo);
		mv.setViewName("categoryEdit");
		return mv;
	}

	@RequestMapping(value = "/edit")
	public ModelAndView edit(@RequestParam Integer id) throws Exception {
		ModelAndView mv = new ModelAndView();
		HajCategory vo = service.queryById(id);
		mv.addObject("vo", vo);
		mv.setViewName("categoryEdit");
		return mv;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public void save(HajCategory po, PrintWriter out) {
		String result = "success";
		if (po != null) {
			try {
				if (po.getId() == null) {
					service.add(po);
				} else {
					service.updateBySelective(po);
				}

				redisSpringProxy.deletePattern("commodityTypeByCategoryId_*");
				redisSpringProxy.deletePattern("categoryList_*");
			} catch (Exception e) {
				result = "error";
				log.info(e.getMessage(), e);
			}
		} else {
			result = "error";
		}

		out.print(result);
		out.close();
	}

	@RequestMapping(value = "/delete")
	public void delete(Integer[] id, PrintWriter out) throws Exception {
		service.delete((Object[]) id);

		redisSpringProxy.deletePattern("commodityTypeByCategoryId_*");
		redisSpringProxy.deletePattern("categoryList_*");
		out.print("success");
		out.close();
	}

	@RequestMapping(value = "/listByAreaCode")
	public void listByAreaCode(HajCategory vo, HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<>();
		try {
			if (Tools.isNotEmpty(vo.getAreaCode())) {
				vo.getPage().setShowCount(100);
				List<HajCategory> categoryList = service.listPage(vo);

				jsonMap.put("error", "0");
				jsonMap.put("msg", "success");
				jsonMap.put("categoryList", categoryList);
			} else {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "请选择城市");
			}
		} catch (Exception e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
			log.info(e.getMessage(), e);
		} finally {
			try {
				JSONUtils.printObject(jsonMap, response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
