package com.flf.controller;

import com.flf.entity.HajCouponBrand;
import com.flf.entity.HajCouponInfo;
import com.flf.service.HajCouponBrandService;
import com.flf.service.HajCouponInfoService;
import com.flf.util.JSONUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>HajCouponBrandController<br>
 * <br>
 */ 
@Controller
@RequestMapping(value = "/couponBrand")
public class HajCouponBrandController {
	
	private final static Logger log = Logger.getLogger(HajCouponBrandController.class);
	
	@Autowired(required = false)
	private HajCouponBrandService service;

	@Autowired(required = false)
	private HajCouponInfoService couponInfoService;

	@RequestMapping(value = "/list")
	public ModelAndView list(HajCouponBrand vo) {
		ModelAndView mv = new ModelAndView();

		List<HajCouponBrand> hajCouponBrandList = service.listPage(vo);
		mv.addObject("vo", vo);
		mv.addObject("hajCouponBrandList", hajCouponBrandList);
		mv.setViewName("hajCouponBrandList");

		return mv;
	}

	@RequestMapping(value = "/add")
	public ModelAndView add() throws Exception {
		ModelAndView mv = new ModelAndView();

		mv.setViewName("hajCouponBrandEdit");
		return mv;
	}

	@RequestMapping(value = "/edit")
	public ModelAndView edit(@RequestParam Integer id) throws Exception {
		ModelAndView mv = new ModelAndView();
		HajCouponBrand vo = service.queryById(id);
		mv.addObject("vo", vo);
		mv.setViewName("hajCouponBrandEdit");
		return mv;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public void save(@RequestParam Integer couponId, @RequestParam List<Integer> brandList, PrintWriter out) throws Exception {
		String result = "error";

		try {
			if (couponId != null && couponId > 0) {
				boolean flag = service.save(couponId, brandList);
				result = flag ? "success" : result;
			}
		} catch (Exception e) {
			log.info(e.getMessage(), e);
		}

		out.print(result);
		out.close();
	}

	@RequestMapping(value = "/delete")
	public void delete(Integer[] id, PrintWriter out) throws Exception {
		service.delete((Object[]) id);
		out.print("success");
		out.close();
	}

	@RequestMapping(value = "/listWithTreeNodes")
	public ModelAndView listWithTreeNodes(HajCouponBrand vo) throws Exception {
		ModelAndView mv = new ModelAndView();

		List<HashMap<String, Object>> list = service.listWithTreeNodes(vo.getCouponId());

		HajCouponInfo couponInfo = couponInfoService.queryById(vo.getCouponId());

		mv.addObject("vo", vo);
		mv.addObject("couponInfo", couponInfo);
		mv.addObject("treeNodes", JSONUtils.toJSONString(list));
		mv.setViewName("couponBrand");

		return mv;
	}
}
