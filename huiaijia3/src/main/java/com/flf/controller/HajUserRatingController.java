package com.flf.controller;

import java.io.PrintWriter;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import com.flf.entity.HajUserRating;
import com.flf.entity.HajUserRating;
import com.flf.service.HajUserRatingService;

/**
 * 
 * <br>
 * <b>功能：</b>HajUserRatingController<br>
 * <br>
 */ 
@Controller
@RequestMapping(value = "/hajUserRating")
public class HajUserRatingController {
	
	private final static Logger log = Logger.getLogger(HajUserRatingController.class);
	
	@Autowired(required = false)
	private HajUserRatingService service;

	@RequestMapping(value = "/list")
	public ModelAndView list(HajUserRating vo) {
		ModelAndView mv = new ModelAndView();

		List<HajUserRating> hajUserRatingList = service.listPage(vo);
		mv.addObject("vo", vo);
		mv.addObject("hajUserRatingList", hajUserRatingList);
		mv.setViewName("hajUserRatingList");

		return mv;
	}

	@RequestMapping(value = "/add")
	public ModelAndView add() throws Exception {
		ModelAndView mv = new ModelAndView();

		mv.setViewName("hajUserRatingEdit");
		return mv;
	}

	@RequestMapping(value = "/edit")
	public ModelAndView edit(@RequestParam Integer id) throws Exception {
		ModelAndView mv = new ModelAndView();
		HajUserRating vo = service.queryById(id);
		mv.addObject("vo", vo);
		mv.setViewName("hajUserRatingEdit");
		return mv;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public void save(HajUserRating po, PrintWriter out) throws Exception {
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
