package com.flf.controller;

import com.flf.entity.HajFeedback;
import com.flf.entity.HajFeedbackVo;
import com.flf.entity.User;
import com.flf.service.HajAreasService;
import com.flf.service.HajFeedbackService;
import com.flf.util.Const;
import com.flf.util.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>HajFeedbackController<br>
 * <br>
 */
@Controller
@RequestMapping(value = "/feedback")
public class HajFeedbackController {

	@Autowired(required = false)
	private HajFeedbackService service;

	@Autowired(required = false)
	private HajAreasService areasService;

	@RequestMapping(value = "/list")
	public ModelAndView list(HttpSession session, HajFeedbackVo vo) throws Exception {
		ModelAndView mv = new ModelAndView();

		User user = (User) session.getAttribute(Const.SESSION_USER);
		if (null != user) {
			vo.setAreaCode(user.getAreaCode());
		}

		List<HajFeedbackVo> feedbackList = service.listPage(vo);

		areasService.putAreaList(mv, vo.getProvinceCode(), vo.getCityCode());

		mv.addObject("feedbackList", feedbackList);
		mv.addObject("vo", vo);

		mv.setViewName("feedbackList");
		return mv;
	}

	@RequestMapping(value = "/update")
	public void update(HajFeedback po, PrintWriter out) throws Exception {
		HajFeedback feedback = new HajFeedback();
		String result = "error";
		if (po != null && po.getId() != null) {
			feedback.setId(po.getId());
			feedback.setResolved(po.getResolved());
			if (Tools.notEmpty(po.getRemark2())) {
				feedback.setRemark2(po.getRemark2());
			}
			service.updateBySelective(feedback);
			result = "success";
		}

		out.print(result);
		out.close();
	}

}
