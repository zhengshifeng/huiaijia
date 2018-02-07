package com.flf.controller;

import com.base.util.HttpUtil;
import com.flf.entity.HajFrontUser;
import com.flf.entity.HajTradingHistory;
import com.flf.entity.HajWithdrawals;
import com.flf.entity.User;
import com.flf.service.HajFrontUserService;
import com.flf.service.HajTradingHistoryService;
import com.flf.service.HajWithdrawalsService;
import com.flf.service.IHajSMSPushService;
import com.flf.util.Const;
import com.flf.util.DateUtil;
import com.flf.util.ExcelUtil;
import com.flf.util.JSONUtils;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 
 * <br>
 * <b>功能：</b>HajWithdrawalsAppController<br>
 * <br>
 */
@Controller
@RequestMapping(value = "/withdrawals")
public class HajWithdrawalsController {

	private final static Logger log = Logger.getLogger(HajWithdrawalsController.class);

	@Autowired(required = false)
	private HajWithdrawalsService hajWithdrawalsService;

	@Autowired(required = false)
	private HajFrontUserService hajFrontUserService;

	@Autowired(required = false)
	private IHajSMSPushService hajSMSPushService;

	@Autowired
	private HajTradingHistoryService hajTradingHistoryService;

	/**
	 * 后台提现列表
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/getAll")
	public ModelAndView list(HttpSession session, HajWithdrawals withdrawals) {
		ModelAndView mv = new ModelAndView();
		try {
			User user = (User) session.getAttribute(Const.SESSION_USER);
			if (null != user) {
				withdrawals.setAreaCode(user.getAreaCode());
			}
			List<Map<String, Object>> withdrawalsList = hajWithdrawalsService.listAllOrder(withdrawals);
			mv.addObject("withdrawalsList", withdrawalsList);
			mv.addObject("withdrawals", withdrawals);
			mv.setViewName("withdrawalsList");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

	/**
	 * 确认提现
	 * 
	 */
	@RequestMapping(value = "/confirmWithdrawals")
	public void confirmWithdrawals(HajWithdrawals withdrawals, HttpServletResponse httpServletResponse) {
		try {
			withdrawals.setOperationTime(DateUtil.datetime2Str(new Date()));
			int id = hajWithdrawalsService.updateWithdrawals(withdrawals);
			if (id > 0) {
				withdrawals = hajWithdrawalsService.queryById(withdrawals.getId());
				HajTradingHistory tarding = new HajTradingHistory();
				// 增加用户流水
				tarding.setTradingContent("余额提现成功：" + withdrawals.getMoney());
				tarding.setCreateTime(DateUtil.dateToString(new Date()));
				tarding.setMoney(withdrawals.getMoney());
				tarding.setTradingStatus(1);
				tarding.setUserId(withdrawals.getUserId());
				tarding.setType(0);// 减少
				hajTradingHistoryService.saveTradeRecord(tarding);

				// 提现后短信通知
				HajFrontUser frontUser = hajFrontUserService.queryById(withdrawals.getUserId());
				hajSMSPushService.withdrawal(frontUser.getMobilePhone(), withdrawals.getMoney());
				JSONUtils.printStr("1", httpServletResponse);
			} else {
				JSONUtils.printStr("0", httpServletResponse);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 弹出提现备注窗口
	 */
	@RequestMapping(value = "/withdrawalsPage")
	public ModelAndView withdrawalsPage(int id, int userId, HttpServletResponse httpServletResponse) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("id", id);
		mv.addObject("userId", userId);
		mv.setViewName("withdrawalsPage");
		return mv;
	}

	/**
	 * 导出excel
	 */
	@RequestMapping(value = "/excel")
	public void exportWithdrawals(HajWithdrawals withdrawals, HttpServletRequest request,
								  HttpServletResponse response) {
		try {
			XSSFWorkbook wb = hajWithdrawalsService.exportWithdrawals(withdrawals);
			String filename = HttpUtil.encodeFilename(request, "提现申请");
			ExcelUtil.output(response, filename, wb);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
