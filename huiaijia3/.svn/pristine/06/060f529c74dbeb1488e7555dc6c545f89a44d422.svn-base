package com.flf.controller.app;

import com.base.criteria.Criteria;
import com.flf.entity.HajFrontUser;
import com.flf.entity.HajTradingHistory;
import com.flf.entity.HajWithdrawals;
import com.flf.service.HajFrontUserService;
import com.flf.service.HajOrderService;
import com.flf.service.HajTradingHistoryService;
import com.flf.service.HajWithdrawalsService;
import com.flf.util.DateUtil;
import com.flf.util.JSONUtils;
import com.flf.util.MD5Tools;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * 提现功能
 * 
 */
@Controller
@RequestMapping(value = "/hajWithdrawal")
public class HajWithdrawalsAppController {

	private final static Logger log = Logger.getLogger(HajWithdrawalsAppController.class);

	@Autowired(required = false)
	private HajWithdrawalsService hajWithdrawalsService;

	@Autowired(required = false)
	private HajFrontUserService hajFrontUserService;

	@Autowired(required = false)
	private HajTradingHistoryService hajTradingHistoryService;

	@Autowired(required = false)
	private HajOrderService hajOrderService;

	/**
	 * 添加提现
	 * 
	 * @param userId
	 * @param sign
	 * @param response
	 */
	@RequestMapping(value = "/saveWithdrawal")
	public void saveWithdrawal(@RequestParam String sign, @RequestParam int userId, HttpServletResponse response) {
		log.info("用户申请提现接口-->userId:" + userId);
		Map<String, Object> jsonMap = new HashMap<>();
		try {
			if (MD5Tools.checkSign(sign)) {
				HajFrontUser user = hajFrontUserService.queryById(userId);
				if (null != user && user.getBalance().doubleValue() > 0) {
					// if (!undoneOrdersCheck(user)) {
					HajWithdrawals withdrawals = new HajWithdrawals();
					withdrawals.setUserId(userId);
					withdrawals.setMobilePhone(user.getMobilePhone());
					withdrawals.setCreateTime(DateUtil.datetime2Str(new Date()));
					withdrawals.setIsConfirm(0); // 财务是否确认（1是0否）
					withdrawals.setStatus(0); // 是否成功（1是0否）
					withdrawals.setMoney(user.getBalance());
					int count = hajWithdrawalsService.add(withdrawals);

					if (count > 0) {
						// 更新用户金额为0
						hajFrontUserService.updateUserMoney(user.getId());

						// 增加用户流水
						HajTradingHistory trading = new HajTradingHistory();
						trading.setTradingContent("余额提现申请中：" + withdrawals.getMoney());
						trading.setCreateTime(DateUtil.dateToString(new Date()));
						trading.setMoney(withdrawals.getMoney());
						trading.setTradingStatus(1);
						trading.setUserId(withdrawals.getUserId());
						trading.setType(0);// 减少
						hajTradingHistoryService.saveTradeRecord(trading);

						jsonMap.put("error", "0");
						jsonMap.put("msg", "成功");
					} else {
						jsonMap.put("error", "1");
						jsonMap.put("msg", "失败");
					}
					/*
					 * } else { jsonMap.put("error", "3"); jsonMap.put("msg",
					 * "您有订单等待配送中，未能进行提现"); }
					 */
				} else {
					jsonMap.put("error", "1");
					jsonMap.put("msg", "失败");
				}
			} else {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "签名异常");
				jsonMap.put("recharge", "");
			}
		} catch (Exception e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
			log.info(e.getMessage(), e);
		} finally {
			try {
				JSONUtils.printObject(jsonMap, response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 根据用户id判断是否有待配送订单
	 * 
	 * @param user
	 * @return
	 * @throws Exception
	 */
	private boolean undoneOrdersCheck(HajFrontUser user) throws Exception {
		Criteria criteria = new Criteria();
		Map<String, Object> condition = new HashMap<>();
		condition.put("userId", user.getId());
		condition.put("status", 2);
		criteria.setCondition(condition);

		int undoneOrderCount = hajOrderService.queryByCount(criteria);

		return undoneOrderCount > 0;
	}

}
