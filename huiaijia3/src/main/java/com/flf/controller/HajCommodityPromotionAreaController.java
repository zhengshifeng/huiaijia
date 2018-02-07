package com.flf.controller;

import com.flf.entity.HajCommodityPromotionArea;
import com.flf.service.HajCommodityPromotionAreaService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.PrintWriter;
import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>HajCommodityPromotionAreaController<br>
 * <br>
 */ 
@Controller
@RequestMapping(value = "/hajCommodityPromotionArea")
public class HajCommodityPromotionAreaController {
	
	private final static Logger log = Logger.getLogger(HajCommodityPromotionAreaController.class);
	
	@Autowired(required = false)
	private HajCommodityPromotionAreaService service;

	@RequestMapping(value = "/list")
	public ModelAndView list(HajCommodityPromotionArea vo) {
		ModelAndView mv = new ModelAndView();

		List<HajCommodityPromotionArea> hajCommodityPromotionAreaList = service.listPage(vo);
		mv.addObject("vo", vo);
		mv.addObject("hajCommodityPromotionAreaList", hajCommodityPromotionAreaList);
		mv.setViewName("hajCommodityPromotionAreaList");

		return mv;
	}

	@RequestMapping(value = "/add")
	public ModelAndView add() throws Exception {
		ModelAndView mv = new ModelAndView();

		mv.setViewName("hajCommodityPromotionAreaEdit");
		return mv;
	}

	@RequestMapping(value = "/edit")
	public ModelAndView edit(@RequestParam Integer id) throws Exception {
		ModelAndView mv = new ModelAndView();
		HajCommodityPromotionArea vo = service.queryById(id);
		mv.addObject("vo", vo);
		mv.setViewName("hajCommodityPromotionAreaEdit");
		return mv;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public void save(HajCommodityPromotionArea po, PrintWriter out) throws Exception {
		String result = "success";
		if (po != null) {
			if (po.getId() == null) {
				service.add(po);
			} else {
				service.updateBySelective(po);
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
		out.print("success");
		out.close();
	}
}
