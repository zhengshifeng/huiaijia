package com.flf.controller;

import com.flf.entity.HajCouponCommodity;
import com.flf.entity.User;
import com.flf.resolver.RollbackException;
import com.flf.service.HajCouponCommodityService;
import com.flf.util.Const;
import com.flf.util.JSONUtils;
import com.flf.util.Tools;
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
import java.util.Map;

/**
 * <br>
 * <b>功能：</b>HajCouponCommodityController<br>
 * <br>
 */
@Controller
@RequestMapping(value = "/couponCommodity")
public class HajCouponCommodityController {

	private final static Logger log = Logger.getLogger(HajCouponCommodityController.class);

	@Autowired(required = false)
	private HajCouponCommodityService service;

	@RequestMapping(value = "/list")
	public ModelAndView list(HttpSession session, HajCouponCommodity vo) throws RollbackException {
		ModelAndView mv = new ModelAndView();

		User user = (User) session.getAttribute(Const.SESSION_USER);
		if (Tools.isEmpty(user.getAreaCode())) {
			throw new RollbackException("请先选择操作的城市");
		}
		vo.setAreaCode(user.getAreaCode());

		List<Map<String, Object>> commodityList = service.listWithTreeNodes(vo);
		mv.addObject("treeNodes", JSONUtils.toJSONString(commodityList));
		mv.addObject("vo", vo);
		mv.setViewName("couponCommodity");

		return mv;
	}

	@RequestMapping(value = "/add")
	public ModelAndView add() throws Exception {
		ModelAndView mv = new ModelAndView();

		mv.setViewName("hajCouponCommodityEdit");
		return mv;
	}

	@RequestMapping(value = "/edit")
	public ModelAndView edit(@RequestParam Integer id) throws Exception {
		ModelAndView mv = new ModelAndView();
		HajCouponCommodity vo = service.queryById(id);
		mv.addObject("vo", vo);
		mv.setViewName("hajCouponCommodityEdit");
		return mv;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public void save(@RequestParam Integer couponId, @RequestParam List<String> commodityList, PrintWriter out) throws Exception {
		String result = "error";

		try {
			if (couponId != null && couponId > 0) {
				boolean flag = service.save(couponId, commodityList);
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
}
