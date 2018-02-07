package com.flf.controller;

import com.flf.entity.HajCouponUser;
import com.flf.service.HajCouponUserService;
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
 * <b>功能：</b>HajCouponUserController<br>
 * <br>
 */ 
@Controller
@RequestMapping(value = "/hajCouponUser")
public class HajCouponUserController {
	
	private final static Logger log = Logger.getLogger(HajCouponUserController.class);
	
	@Autowired(required = false)
	private HajCouponUserService service;

	@RequestMapping(value = "/list")
	public ModelAndView list(HajCouponUser vo) {
		ModelAndView mv = new ModelAndView();

		List<HajCouponUser> hajCouponUserList = service.listPage(vo);
		mv.addObject("vo", vo);
		mv.addObject("hajCouponUserList", hajCouponUserList);
		mv.setViewName("hajCouponUserList");

		return mv;
	}

	@RequestMapping(value = "/add")
	public ModelAndView add() throws Exception {
		ModelAndView mv = new ModelAndView();

		mv.setViewName("hajCouponUserEdit");
		return mv;
	}

	@RequestMapping(value = "/edit")
	public ModelAndView edit(@RequestParam Integer id) throws Exception {
		ModelAndView mv = new ModelAndView();
		HajCouponUser vo = service.queryById(id);
		mv.addObject("vo", vo);
		mv.setViewName("hajCouponUserEdit");
		return mv;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public void save(HajCouponUser po, PrintWriter out) throws Exception {
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
