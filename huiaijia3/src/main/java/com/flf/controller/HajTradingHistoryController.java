package com.flf.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.base.util.HttpUtil;
import com.flf.entity.HajFinancial;
import com.flf.entity.HajTradingHistory;
import com.flf.entity.User;
import com.flf.service.HajTradingHistoryService;
import com.flf.util.Const;
import com.flf.util.DateUtil;
import com.flf.util.ExcelUtil;

/**
 * 
 * <br>
 * <b>功能：</b>HajTradingHistoryController<br>
 * <br>
 */
@Controller
@RequestMapping(value = "/tradingHistory")
public class HajTradingHistoryController {

	@Autowired
	private HajTradingHistoryService service;

	@RequestMapping(value = "/list")
	public ModelAndView list(HajTradingHistory vo) {
		ModelAndView mv = new ModelAndView();

		List<HajTradingHistory> tradingHistoryList = service.listPageTradingHistory(vo);

		mv.addObject("tradingHistoryList", tradingHistoryList);
		mv.addObject("vo", vo);
		mv.setViewName("tradingHistoryList");

		return mv;
	}

	/**
	 * 财务报表
	 */
	@RequestMapping(value = "/financial")
	public ModelAndView list(HajFinancial vo) {

		ModelAndView mv = new ModelAndView();

		mv.addObject("vo", vo);
		mv.setViewName("financial");

		return mv;

	}

	@RequestMapping(value = "/batchFinancial")
	public void batchFinancial(HttpSession session, HajFinancial orderVo, HttpServletResponse response,
			HttpServletRequest request) {
		try {
			String filename = "订单报表导出";
			filename = HttpUtil.encodeFilename(request, filename);
			filename = DateUtil.dateformat(new Date(), "MM-dd") + filename;

			User user = (User) session.getAttribute(Const.SESSION_USER);
			if (null != user) {
				orderVo.setAreaCode(user.getAreaCode());
			}

			XSSFWorkbook wb = service.excelbatchFinancial(orderVo);
			ExcelUtil.output(response, filename, wb);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
