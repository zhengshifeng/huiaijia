package com.flf.controller;

import com.flf.entity.HajRecharge;
import com.flf.entity.HajRechargeVo;
import com.flf.entity.User;
import com.flf.service.HajRechargeService;
import com.flf.service.impl.HajRechargeServiceImpl;
import com.flf.util.Const;
import com.flf.util.HajRefund;
import com.flf.util.JSONUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 
 * <br>
 * <b>功能：</b>HajRechargeController<br>
 * <br>
 */
@Controller
@RequestMapping(value = "/recharge")
public class HajRechargeController {
	private final static Logger log = Logger.getLogger(HajRechargeServiceImpl.class);

	@Autowired(required = false)
	private HajRechargeService hajRechargeService;

	@RequestMapping(value = "/list")
	public ModelAndView list(HttpSession session, HajRechargeVo vo) throws Exception {
		ModelAndView mv = new ModelAndView();
        User user = (User) session.getAttribute(Const.SESSION_USER);
        if (null != user) {
			vo.setAreaCode(user.getAreaCode());
		}
		if (vo.getRechargeStatus() == null) {
        	vo.setRechargeStatus(1);
		}
		//设置类型
		vo.setRechargePackageType(0);
		List<HajRecharge> rechargeList = hajRechargeService.list(vo);
		mv.addObject("rechargeList", rechargeList);
		mv.addObject("vo", vo);
		Map<String, Object> totalRechargee = hajRechargeService.queryTotalRecharge(vo);
		Map<String, Object> totalRefund = new HashMap<>();
		if (vo.getRechargeStatus() != null && vo.getRechargeStatus() == 0) {
			totalRefund.put("sumRefundNum", "0.00");
		} else {
			totalRefund = hajRechargeService.queryTotalRefund(vo);
		}

		mv.addObject("totalRechargee", totalRechargee);
		mv.addObject("totalRefund", totalRefund);

		mv.setViewName("rechargeList");
		return mv;
	}

	/**
	 * 根据查询条件查询退款总金额
	 * 
	 */
	@RequestMapping(value = "/queryRefund")
	public void queryRefundTotalMoney(HttpSession session, HajRechargeVo vo, HttpServletResponse response) {
		// 是否需要从客户端查询(1需要0不需要)
		int needQuery = 1;
		Map<String, Object> totalRefund = new HashMap<>();
		if (vo != null && vo.getRechargeStatus() != null) {
			needQuery = vo.getRechargeStatus();
		}
 
		if (needQuery == 1) {
			User user = (User) session.getAttribute(Const.SESSION_USER);
			if (null != user) {
				if (vo == null) {
					vo = new HajRechargeVo();
				}
				vo.setAreaCode(user.getAreaCode());
			}
			//设置类型
			vo.setRechargePackageType(0);
			List<HajRecharge> refundList = hajRechargeService.queryRechargeListForRerund(vo);
			if (refundList != null && refundList.size() > 0) {
				// 创建线程池
				ExecutorService executor = Executors.newCachedThreadPool();
				for (HajRecharge hajRecharge : refundList) {
					HajRefund myTask = new HajRefund(hajRecharge, hajRechargeService);
					executor.execute(myTask);
				}
				executor.shutdown();
				System.out.println("Finished all threads");
			}
			totalRefund = hajRechargeService.queryTotalRefund(vo);
		} else {
			totalRefund.put("sumRefundNum", "0.00");
		}
		try {
			JSONUtils.printObject("{totalRefund:" + totalRefund + "}", response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
