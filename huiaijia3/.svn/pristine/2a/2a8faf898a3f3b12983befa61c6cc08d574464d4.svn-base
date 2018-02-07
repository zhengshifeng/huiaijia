package com.flf.controller;

import com.base.controller.BaseController;
import com.base.util.HttpUtil;
import com.flf.entity.HajOrderPayment;
import com.flf.entity.HajOrderPaymentVo;
import com.flf.entity.User;
import com.flf.service.HajOrderPaymentService;
import com.flf.util.Const;
import com.flf.util.DateUtil;
import com.flf.util.ExcelUtil;
import com.flf.util.JSONUtils;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * <br>
 * <b>功能：</b>HajOrderPaymentController<br>
 * <br>
 */ 
@Controller
@RequestMapping(value = "/orderPayment")
public class HajOrderPaymentController extends BaseController{
	
	private final static Logger log = Logger.getLogger(HajOrderPaymentController.class);
	
	@Autowired(required = false)
	private HajOrderPaymentService service;

	@RequestMapping(value = "/list")
	public ModelAndView list(HajOrderPaymentVo vo, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();

		userSession = (User) request.getSession().getAttribute(Const.SESSION_USER);
		vo.setAreaCode(userSession.getAreaCode());

		List<HajOrderPaymentVo> orderPaymentList = service.list(vo);
		mv.addObject("vo", vo);
		mv.addObject("orderPaymentList", orderPaymentList);
		mv.setViewName("orderPaymentList");

		return mv;
	}

	@RequestMapping(value = "/add")
	public ModelAndView add() throws Exception {
		ModelAndView mv = new ModelAndView();

		mv.setViewName("orderPaymentEdit");
		return mv;
	}

	@RequestMapping(value = "/edit")
	public ModelAndView edit(@RequestParam Integer id) throws Exception {
		ModelAndView mv = new ModelAndView();
		HajOrderPayment vo = service.queryById(id);
		mv.addObject("vo", vo);
		mv.setViewName("orderPaymentEdit");
		return mv;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public void save(HajOrderPayment po, PrintWriter out) throws Exception {
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

	@RequestMapping(value = "/export2Excel")
	public void export2Excel(HajOrderPaymentVo vo, HttpServletResponse response, HttpServletRequest request) {
		try {
			String filename = "订单支付查询";
			filename = HttpUtil.encodeFilename(request, filename);
			filename = DateUtil.dateformat(new Date(), "MM-dd") + filename;

			userSession = (User) request.getSession().getAttribute(Const.SESSION_USER);
			vo.setAreaCode(userSession.getAreaCode());

			XSSFWorkbook wb = service.export2Excel(vo);
			ExcelUtil.output(response, filename, wb);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/calcOrderPayment")
	public void calcOrderPayment(HajOrderPaymentVo vo, HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<>();
		try {
			BigDecimal orderPaid = service.calcOrderPayment(vo);
			BigDecimal orderRefund = service.calcOrderRefund(vo);
			BigDecimal received = orderPaid.subtract(orderRefund);

			jsonMap.put("orderPaid", orderPaid);
			jsonMap.put("orderRefund", orderRefund);
			jsonMap.put("received", received);
		} catch (Exception e) {
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
