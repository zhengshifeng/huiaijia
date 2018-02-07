package com.flf.controller;

import com.flf.entity.HajSaleDept;
import com.flf.service.HajSaleDeptService;
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
 * <b>功能：</b>HajSaleDeptController<br>
 * <br>
 */ 
@Controller
@RequestMapping(value = "/hajSaleDept")
public class HajSaleDeptController {
	
	private final static Logger log = Logger.getLogger(HajSaleDeptController.class);
	
	@Autowired(required = false)
	private HajSaleDeptService service;

	@RequestMapping(value = "/list")
	public ModelAndView list(HajSaleDept vo) {
		ModelAndView mv = new ModelAndView();

		List<HajSaleDept> hajSaleDeptList = service.listPage(vo);
		mv.addObject("vo", vo);
		mv.addObject("hajSaleDeptList", hajSaleDeptList);
		mv.setViewName("hajSaleDeptList");

		return mv;
	}

	@RequestMapping(value = "/add")
	public ModelAndView add() throws Exception {
		ModelAndView mv = new ModelAndView();

		mv.setViewName("hajSaleDeptEdit");
		return mv;
	}

	@RequestMapping(value = "/edit")
	public ModelAndView edit(@RequestParam Integer id) throws Exception {
		ModelAndView mv = new ModelAndView();
		HajSaleDept vo = service.queryById(id);
		mv.addObject("vo", vo);
		mv.setViewName("hajSaleDeptEdit");
		return mv;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public void save(HajSaleDept po, PrintWriter out) throws Exception {
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
