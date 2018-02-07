package com.flf.controller;

import com.flf.entity.HajCommunitySorter;
import com.flf.entity.User;
import com.flf.service.HajCommunitySorterService;
import com.flf.util.Const;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>HajCommunitySorterController<br>
 * <br>
 */
@Controller
@RequestMapping(value = "/hajCommunitySorter")
public class HajCommunitySorterController {

	private final static Logger log = Logger.getLogger(HajCommunitySorterController.class);

	@Autowired(required = false)
	private HajCommunitySorterService service;

	@RequestMapping(value = "/list")
	public ModelAndView list(HttpSession session, HajCommunitySorter vo) {
		ModelAndView mv = new ModelAndView();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		if (null != user) {
			vo.setAreaCode(user.getAreaCode());
		}
		List<HajCommunitySorter> communitySorterList = service.listPage(vo);
		mv.addObject("vo", vo);
		mv.addObject("communitySorterList", communitySorterList);
		mv.setViewName("communitySorterList");

		return mv;
	}

	@RequestMapping(value = "/add")
	public ModelAndView add() throws Exception {
		ModelAndView mv = new ModelAndView();

		mv.setViewName("communitySorterEdit");
		return mv;
	}

	@RequestMapping(value = "/edit")
	public ModelAndView edit(@RequestParam Integer id) throws Exception {
		ModelAndView mv = new ModelAndView();
		HajCommunitySorter vo = service.queryById(id);
		mv.addObject("vo", vo);
		mv.setViewName("communitySorterEdit");
		return mv;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public void save(HttpSession session, HajCommunitySorter po, PrintWriter out) throws Exception {
		String result = "success";
		if (po != null) {
			User user = (User) session.getAttribute(Const.SESSION_USER);
			if (null != user) {
				po.setAreaCode(user.getAreaCode());
			}

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
