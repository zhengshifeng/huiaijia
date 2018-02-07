package com.flf.controller;

import com.base.controller.BaseController;
import com.flf.entity.*;
import com.flf.service.*;
import com.flf.util.Const;
import com.flf.util.DateUtil;
import com.flf.util.JSONUtils;
import com.flf.view.RechargePackageRecordExcelView;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

/**
 * 充值套餐记录管理
 */


@Controller
@RequestMapping(value = "/rechargePackageRecord")
public class HajRechargePackageRecordController extends BaseController {
	private final static Logger log = Logger.getLogger(HajRechargePackageRecordController.class);
	@Autowired
	private HajRechargePackageRecordService hajRechargePackageRecordService;
	@Autowired
	private HajRefundsRecordService hajRefundsRecordService;
	@Autowired(required = false)
	private HajFrontUserService hajFrontUserService;
	@Autowired(required = false)
	private HajWithdrawalsService hajWithdrawalsService;
	@Autowired(required = false)
	private HajTradingHistoryService hajTradingHistoryService;


	@RequestMapping(value = "/list")
	public ModelAndView list(HajRechargePackageRecord hajRechargePackageRecord) throws Exception {
		ModelAndView mv = new ModelAndView();
		hajRechargePackageRecord.setRechargePackageType(1);
		List<HajRechargePackageRecord> rechargePackageRecordList = hajRechargePackageRecordService.listPageRechargePackageRecord(hajRechargePackageRecord);
		mv.addObject("rechargePackageRecordList", rechargePackageRecordList);
		Map<String, Object> totalChargePackageRecord = hajRechargePackageRecordService.queryTotalChargePackageRecord(hajRechargePackageRecord);
		mv.addObject("hajRechargePackageRecord", hajRechargePackageRecord);
		mv.addObject("totalChargePackageRecord", totalChargePackageRecord);
		mv.setViewName("rechargePackageRecordList");
		return mv;
	}


	/**
	 * 保存添加或修改套餐记录<br>
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public void save(HajRechargePackageRecord hajRechargePackageRecord, HttpSession session, HttpServletResponse response) {
		userSession = (User) session.getAttribute(Const.SESSION_USER);
		Map<String, Object> returnMap = new HashMap<>();
		returnMap.put("error", "0");
		returnMap.put("msg", "success");
		try {
			if (hajRechargePackageRecord != null) {
				//查询用户信息
				HajFrontUser user = hajFrontUserService.queryById(hajRechargePackageRecord.getUserId());
				//用户余额必须大于退款金额
				if (null != user && user.getBalance().doubleValue() > 0 &&
						user.getBalance().doubleValue() >= hajRechargePackageRecord.getRefundAmount().doubleValue()) {
					//生成提现流水
					HajWithdrawals withdrawals = new HajWithdrawals();
					withdrawals.setUserId(user.getId());
					withdrawals.setMobilePhone(user.getMobilePhone());
					withdrawals.setCreateTime(DateUtil.datetime2Str(new Date()));
					withdrawals.setIsConfirm(0); // 财务是否确认（1是0否）
					withdrawals.setStatus(0); // 是否成功（1是0否）
					withdrawals.setMoney(hajRechargePackageRecord.getRefundAmount());
					int count = hajWithdrawalsService.add(withdrawals);
					if (count > 0) {
						// 更新用户金额 -退款金额
						hajFrontUserService.updateUserwithdrawBalance(hajRechargePackageRecord.getUserId(),hajRechargePackageRecord.getRefundAmount(),user);
						// 增加用户流水
						HajTradingHistory trading = new HajTradingHistory();
						trading.setTradingContent("余额提现申请中：" + withdrawals.getMoney());
						trading.setCreateTime(DateUtil.dateToString(new Date()));
						trading.setMoney(withdrawals.getMoney());
						trading.setTradingStatus(1);
						trading.setUserId(withdrawals.getUserId());
						trading.setType(0);// 减少
						hajTradingHistoryService.saveTradeRecord(trading);
					}
					hajRechargePackageRecordService.save(hajRechargePackageRecord);
					//生成退款记录
					HajRefundsRecord hajRefundsRecord = new HajRefundsRecord();
					hajRefundsRecord.setOperatorRemark(hajRechargePackageRecord.getRemark());
					hajRefundsRecord.setRechargeId(hajRechargePackageRecord.getId());
					hajRefundsRecord.setRefundAmount(hajRechargePackageRecord.getRefundAmount());
					hajRefundsRecord.setOperator(userSession.getLoginname());
					hajRefundsRecordService.insertRefundsRecord(hajRefundsRecord);
				}
				else {
					returnMap.put("error", "4");
					returnMap.put("msg", "该客户的余额不足，退款失败");
				}
			} else {
				returnMap.put("error", "3");
				returnMap.put("msg", "数据为空");
			}
		} catch (Exception e) {
			returnMap.put("error", "1");
			returnMap.put("msg", "未知异常");
			log.info(e.getMessage(), e);
		} finally {
			try {
				JSONUtils.printObject(returnMap, response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	/***
	 * 充值套餐记录详情
	 * @param id
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/detail")
	public ModelAndView orderDetail(@RequestParam int id, HttpSession session) throws Exception {
		ModelAndView mv = new ModelAndView();
		HajRechargePackageRecord hajRechargePackageRecord = hajRechargePackageRecordService.getHajRechargePackageRecordById(id);
		List<HajRefundsRecord> hajRefundsRecordList = hajRefundsRecordService.getRefundsRecordById(id);
		hajRechargePackageRecord.setRefundableAmount(hajRechargePackageRecord.getPurchaseAmount().subtract(hajRechargePackageRecord.getRefundAmount()));  //可退款金额 =购买金额-退款金额；
		mv.addObject("hajRechargePackageRecord", hajRechargePackageRecord);
		mv.addObject("hajRefundsRecordList", hajRefundsRecordList);
		mv.setViewName("rechargePackageRecordEdit");
		return mv;
	}


	/**
	 * 导出采购信息到excel
	 *
	 * @return
	 */
	@RequestMapping(value = "/excel")
	public ModelAndView export2Excel(HttpSession session, HajRechargePackageRecord hajRechargePackageRecord) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("客户ID");
		titles.add("手机号码");
		titles.add("套餐名称");
		titles.add("购买金额");
		titles.add("赠送金额");
		titles.add("到账金额");
		titles.add("支付时间");
		titles.add("支付状态");
		titles.add("支付方式");
		titles.add("支付流水号");
		titles.add("退款金额");
		dataMap.put("titles", titles);
		hajRechargePackageRecord.setRechargePackageType(1);
		List<Map<String, Object>> rechargePackageRecordList = hajRechargePackageRecordService.listAllRechargePackageRecord(hajRechargePackageRecord);
		dataMap.put("rechargePackageRecordList", rechargePackageRecordList);
		RechargePackageRecordExcelView erv = new RechargePackageRecordExcelView();
		ModelAndView mv = new ModelAndView(erv, dataMap);
		return mv;
	}

}
