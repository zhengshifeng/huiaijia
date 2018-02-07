package com.flf.controller.app;

import com.alipay.util.AlipayUtil;
import com.flf.entity.HajOrder;
import com.flf.entity.HajOrderPayment;
import com.flf.entity.HajOrderPostFee;
import com.flf.entity.HajRecharge;
import com.flf.service.HajOrderPaymentService;
import com.flf.service.HajOrderPostFeeService;
import com.flf.service.HajOrderService;
import com.flf.service.HajRechargeService;
import com.flf.util.*;
import com.weixin.PayUtil;
import com.weixin.xcx.XcxPayUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * <br>
 * <b>功能：</b>HajRechargeAppController<br>
 * <br>
 */
@Controller
@RequestMapping(value = "/hajRecharge")
public class HajRechargeAppController {
	private final static Logger log = Logger.getLogger(HajRechargeAppController.class);
	@Autowired(required = false)
	private HajRechargeService hajRechargeService;
	@Autowired(required = false)
	private HajOrderPostFeeService hajOrderPostFeeService;
	@Autowired(required = false)
	private HajOrderPaymentService orderPaymentService;
	@Autowired(required = false)
	private HajOrderService orderService;
	
	/**
	 * 确认充值接口，第三方支付平台预支付接口
	 */
	@RequestMapping(value = "/saveHajRecharge", method = RequestMethod.GET)
	public void saveHajRecharge(@RequestParam String sign, @ModelAttribute HajRecharge recharge,
			HttpServletResponse response) {
		log.info("确认充值、第三方支付平台预支付接口");
		Map<String, Object> jsonMap = new HashMap<>();
		boolean insertFlag = true;
		int orderStatus = 0;
		try {
			if (MD5Tools.checkSign(sign)) {
				if (null != recharge.getUserId() && recharge.getUserId() > 0 && recharge.getRechargeFor() != null) {
					// 订单或配送费支付前判断支付状态，如果已支付，则返回错误提示
					if (recharge.getPaymentTarget() != null) {
						if (recharge.getRechargeFor() == HajRechargeUtil.POST_FEE) {
							HajOrderPostFee hajOrderPostFee = hajOrderPostFeeService.queryById(recharge
									.getPaymentTarget());
							insertFlag = (hajOrderPostFee != null && hajOrderPostFee.getIsPay().equals(0));
							orderStatus = hajOrderPostFee != null ? hajOrderPostFee.getIsPay() : 0;
						} else if (recharge.getRechargeFor() == HajRechargeUtil.ORDER_PAYMENT) {
							HajOrder orderByOrderNo = orderService.getOrderByOrderNo(recharge.getPaymentTarget());
							insertFlag = (orderByOrderNo != null && orderByOrderNo.getStatus().equals(1));
							orderStatus = orderByOrderNo != null ? orderByOrderNo.getStatus() : 0;
						}
					}

					if (insertFlag) {
						// app未传此字段，则默认为0；小程序会传1过来
						recharge.setApplication(recharge.getApplication() == null ? 0 : recharge.getApplication());
						if (recharge.getRechargeType() == 1) {// 微信支付，需要调用接口预支付
							Map<String, Object> objMap;
							if (recharge.getApplication() == 1) {
								objMap = XcxPayUtil.addWeixinOrder(recharge);
							} else {
								objMap = PayUtil.addWeixinOrder(recharge);
							}
							if (null != objMap) {
								recharge.setPrepay_id(objMap.get("prepay_id").toString());
								recharge.setBankNo(objMap.get("out_trade_no").toString());

								jsonMap.put("prepay_id", objMap.get("prepay_id").toString());
								jsonMap.put("nonceStr", objMap.get("nonceStr").toString());

								if (recharge.getApplication() == 1) {
									// 如果是微信小程序发起的预支付，则需要生成paySign
									HashMap<String, Object> paymentObject = XcxPayUtil.getPaySign(objMap.get("prepay_id").toString());
									jsonMap.put("paymentObject", paymentObject);
								}
							}

							// 微信默认单位是分，因此APP传过来的是分，我们需要处理成元再存入数据库
							recharge.setMoney(recharge.getMoney().divide(PayUtil.ONE_HUNDRED, 2,
									BigDecimal.ROUND_HALF_UP));
						} else {// 支付宝支付
							String orderNo = MakeOrderNum.makeRechargeNum();
							recharge.setBankNo(orderNo);
							recharge.setPrepay_id(orderNo);
							jsonMap.put("prepay_id", orderNo);
						}

						// 这一步通知状态必须为1，调用通知接口成功才设为0
						recharge.setNotifyStatus(1);

						// 默认为0
						recharge.setRechargeStatus(0);
						recharge.setRefundNum(BigDecimal.ZERO);
						recharge.setIsRefund("0");

						//添加判断充值套餐Id是否为空,到账金额不能为空
						if(recharge.getRechargePackageId()!=null &&
								recharge.getRechargePackageId()>0 &&
								recharge.getAccountAmount()!=null && recharge.getAccountAmount().doubleValue()>0)
						{
							recharge.setRechargePackageId(recharge.getRechargePackageId());
							recharge.setAccountAmount(recharge.getAccountAmount());
							recharge.setRechargePackageType(1); //0代表默认方式， 1代表充值套餐
						}else{
							recharge.setRechargePackageType(0); //0代表默认方式， 1代表充值套餐
						}
						hajRechargeService.add(recharge);
						jsonMap.put("error", "0");
						jsonMap.put("msg", "成功");
					} else {
						if (orderStatus == 2 || orderStatus == 1) {
							jsonMap.put("error", "3");
							jsonMap.put("msg", "请勿重复支付");
						} else if (orderStatus == 9) {
							jsonMap.put("error", "4");
							jsonMap.put("msg", "支付超时，订单已取消");
						} else {
							jsonMap.put("error", "1");
							jsonMap.put("msg", "未知异常");
						}
					}
				} else {
					jsonMap.put("error", "0");
					jsonMap.put("msg", "为空");
				}
			} else {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "签名异常");
			}
		} catch (Exception e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
			log.error(e.getMessage(), e);
		} finally {
			try {
				jsonMap.put("recharge", recharge);
				JSONUtils.printObject(jsonMap, response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 微信回调地址 notify_url
	 * <p>
	 * <br>
	 * author SevenWong <br>
	 * date 2016年6月13日上午10:21:50
	 */
	@RequestMapping(value = "/hajRechargeNotify")
	public void hajRechargeNotify(HttpServletRequest request, HttpServletResponse response) {
		log.info("hajRechargeNotify()进入微信回调地址");
		String return_code = "SUCCESS";
		String return_msg = "OK";

		String inputLine;
		StringBuilder notifyXml = new StringBuilder();

		try {
			while ((inputLine = request.getReader().readLine()) != null) {
				notifyXml.append(inputLine);
			}
			request.getReader().close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		log.info("微信回调时请求过来的xml数据：\n" + notifyXml);
		String out_trade_no = XMLBeanUtils.getXmlNodeValue(notifyXml.toString(), "out_trade_no");

		// 获取out_trade_no（bankNo），根据out_trade_no返回该条记录
		HajRecharge hajRecharge = hajRechargeService.getByOutTradeNo(out_trade_no);
		if (hajRecharge != null && null != hajRecharge.getRechargeStatus() && hajRecharge.getRechargeStatus() == 0) {
			updateRecordByRechargeFor(hajRecharge);
//			/*********************************2017/12/23 RockDing add 被邀请人员状态更改 start*************************************************/
//			Integer userId = hajRecharge.getUserId();
//			changeInviteeStatus(userId);
//			/*********************************2017/12/23 RockDing add 被邀请人员状态更改 end*************************************************/
		} else if (hajRecharge != null && null != hajRecharge.getRechargeStatus()
				&& hajRecharge.getRechargeStatus() == 1) {
			return_code = "SUCCESS";
			return_msg = "OK";
//			/*********************************2017/12/23 RockDing add 被邀请人员状态更改 start*************************************************/
//			Integer userId = hajRecharge.getUserId();
//			changeInviteeStatus(userId);
//			/*********************************2017/12/23 RockDing add 被邀请人员状态更改 end*************************************************/
		} else {
			return_code = "FAIL";
			return_msg = "未找到该订单";
		}

		String xmlData = "<xml><return_code><![CDATA[" + return_code + "]]></return_code>" + //
				"<return_msg><![CDATA[" + return_msg + "]]></return_msg></xml>";
		response.setContentType("text/xml; charset=UTF-8");
		try {
			response.getWriter().write(xmlData);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 更新充值成功状态(1成功0失败)
	 */
	@Deprecated
	@RequestMapping(value = "/updateHajRecharge")
	public void updateHajRecharge(@RequestParam String sign, @RequestParam String prepay_id,
			HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<>();
		if (MD5Tools.checkSign(sign)) {
			log.info("此接口（APP更新充值成功状态）已停用，即将重定向至 获取充值订单支付状态 接口...");
			StringBuilder redirectURL = new StringBuilder("/hajRecharge/getRechargeStatus.action");
			redirectURL.append("?sign=");
			redirectURL.append(MD5Tools.getSign());
			redirectURL.append("&out_trade_no=");
			redirectURL.append(prepay_id);
			try {
				response.sendRedirect(redirectURL.toString());
			} catch (IOException e) {
				e.printStackTrace();
				jsonMap.put("error", "1");
				jsonMap.put("msg", "未知异常");
			}
		} else {
			jsonMap.put("error", "2");
			jsonMap.put("msg", "签名异常");
		}
		try {
			JSONUtils.printObject(jsonMap, response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取充值订单支付状态，msg = invalid时表示订单已失效，APP无需提示是否成功
	 * <p>
	 * <br>
	 * author SevenWong<br>
	 * <br>
	 * date 2016年6月13日下午4:18:47
	 */
	@RequestMapping(value = "/getRechargeStatus")
	public void getRechargeStatus(@RequestParam String sign, @RequestParam String out_trade_no,
			HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<>();
		try {
			if (MD5Tools.checkSign(sign)) {
				HajRecharge hajRecharge = hajRechargeService.getByOutTradeNo(out_trade_no);
				if (hajRecharge != null) {
					if (hajRecharge.getNotifyStatus() != null && hajRecharge.getNotifyStatus() == 1) {

						// 订单支付状态，判断是否支付成功
						boolean tradeStatus = false;
						if (hajRecharge.getRechargeStatus() != null && hajRecharge.getRechargeStatus() == 1) {
							tradeStatus = true;
							log.info("getRechargeStatus()充值订单支付成功...");
						} else {
							// 数据库查询到未充值成功时，主动请求支付查询订单接口去判断支付状态，看是不是有由于网络原因未接收到回调
							if (hajRecharge.getRechargeType() == 1) {
								log.info("getRechargeStatus()调用微信查询订单状态接口...");
								tradeStatus = wechatTradeQuery(hajRecharge);
							} else if (hajRecharge.getRechargeType() == 2) {
								log.info("getRechargeStatus()调用支付宝查询订单状态接口...");
								String alipayTradeNo = AlipayUtil.alipayTradeStatus(hajRecharge.getBankNo());
								if (tradeStatus = Tools.isNotEmpty(alipayTradeNo)) {
									hajRecharge.setAlipayTradeNo(alipayTradeNo);
								}
							}
						}

						if (tradeStatus) {
							jsonMap.put("error", "0");
							jsonMap.put("msg", "充值成功");

							// 成功后将notifyStatus设为0，APP下次再请求此接口就可以告诉它此次充值已完成（返回invalid），无需再请求
							HajRecharge hajRechargeUpdate = new HajRecharge();
							hajRechargeUpdate.setId(hajRecharge.getId());
							hajRechargeUpdate.setNotifyStatus(0);

							// 同时更新支付宝源交易号到充值表，退款用
							hajRechargeUpdate.setAlipayTradeNo(hajRecharge.getAlipayTradeNo());
							hajRechargeService.updateBySelective(hajRechargeUpdate);

							updateRecordByRechargeFor(hajRecharge);

						} else {
							jsonMap.put("error", "5");
							jsonMap.put("msg", "充值失败或未支付");
						}
					} else {
						jsonMap.put("error", "4");
						jsonMap.put("msg", "invalid");
					}
				} else {
					jsonMap.put("error", "3");
					jsonMap.put("msg", "没找到该流水");
				}
			} else {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "签名异常");
			}
		} catch (Exception e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
		} finally {
			try {
				JSONUtils.printObject(jsonMap, response);
				log.info("返回给APP的充值状态msg:" + jsonMap.get("msg"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 根据第三方支付的类型，修改的对应表的记录
	 * <p>
	 * 以前充值的逻辑不变，新增配送费与订单付款的处理
	 * </p>
	 */
	private void updateRecordByRechargeFor(HajRecharge hajRecharge) {
		int updateStatus = hajRechargeService.updateHajRecharge(hajRecharge.getId(), hajRecharge.getAlipayTradeNo());

		if (updateStatus > 0) {
			if (hajRecharge.getRechargeFor() == HajRechargeUtil.RECHARGE) {
				log.info("更新充值记录和流水用户金额");
				hajRechargeService.updateUserRechargeRecord(hajRecharge.getPrepay_id(),
						String.valueOf(hajRecharge.getRechargeFor()), hajRecharge.getAlipayTradeNo());
			} else if (hajRecharge.getRechargeFor() == HajRechargeUtil.POST_FEE) {
				log.info("更新配送费支付状态");
				hajOrderPostFeeService.updateOrderPostRecharge(hajRecharge.getId(),
						Integer.parseInt(hajRecharge.getPaymentTarget()));

				hajOrderPostFeeService.updateOrderPostFeeByRechargeId(hajRecharge.getId());
			} else if (hajRecharge.getRechargeFor() == HajRechargeUtil.ORDER_PAYMENT) {
				log.info("订单支付表记录此次支付流水");
				// 此次支付为订单付款时，先写入订单支付表，记录此次支付流水
				HajOrderPayment hajOrderPayment = new HajOrderPayment();
				hajOrderPayment.setOrderNo(hajRecharge.getPaymentTarget());
				hajOrderPayment.setRechargeId(hajRecharge.getId());

				// 检查唯一性
				int count = orderPaymentService.getCount(hajOrderPayment);
				if (count == 0) {
					try {
						orderPaymentService.add(hajOrderPayment);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				log.info("修改订单的支付状态");
				orderService.updateOrderPayStatus(hajRecharge.getPaymentTarget(), hajRecharge.getRechargeType());
			}
		}
	}

	/**
	 * 支付宝异步通知地址
	 * <p>
	 * <br>
	 * author SevenWong<br>
	 * <br>
	 * date 2016年7月2日上午10:56:12
	 */
	@RequestMapping("/alipayNotify")
	public void alipayNotify(HttpServletRequest request, PrintWriter out) {
		log.info("进入支付宝异步通知地址...");

		Map<String, String> result = getPostQueryString(request.getParameterMap());
		String tradeStatus = result.get("trade_status");
		String tradeNo = result.get("trade_no");

		if ("TRADE_SUCCESS".equals(tradeStatus) || "TRADE_FINISHED".equals(tradeStatus)) {
			// 获取out_trade_no（bankNo），根据out_trade_no返回该条记录
			String out_trade_no = request.getParameter("out_trade_no");

			HajRecharge hajRecharge = hajRechargeService.getByOutTradeNo(out_trade_no);
			if (hajRecharge != null && null != hajRecharge.getRechargeStatus() && hajRecharge.getRechargeStatus() == 0) {
				log.info("支付成功，更新用户余额及支付状态");

				hajRecharge.setAlipayTradeNo(tradeNo);

				// 同时更新支付宝源交易号到充值表，退款用
				HajRecharge hajRechargeUpdate = new HajRecharge();
				hajRechargeUpdate.setId(hajRecharge.getId());
				hajRechargeUpdate.setAlipayTradeNo(tradeNo);
				hajRechargeService.updateBySelective(hajRechargeUpdate);

				updateRecordByRechargeFor(hajRecharge);
			} else if (hajRecharge != null && null != hajRecharge.getRechargeStatus()
					&& hajRecharge.getRechargeStatus() == 1) {
				log.info("支付已成功或未找到该订单，无需更新，并告诉支付宝停止发通知");
			}
			
//			/*********************************2017/12/23 RockDing add 被邀请人员状态更改 start*************************************************/
//			Integer userId = hajRecharge.getUserId();
//			changeInviteeStatus(userId);
//			/*********************************2017/12/23 RockDing add 被邀请人员状态更改 end*************************************************/
			// 输出success告诉支付宝不用再回调，否则支付宝会在24小时内回调8次
			out.print("success");
		} else {
			log.info("支付宝：交易尚未完成tradeStatus:" + tradeStatus);
		}
	}
	
//	/**
//	 *
//	 * @Title: changeInviteeStatus
//	 * @Description: 更新被邀请者的邀请状态（0 未成功<初始状态>,1成功） 注：此业务能影响其他业务
//	 * @param userId
//	 * void    返回类型
//	 * @throws
//	 */
//	private void changeInviteeStatus(Integer userId) {
//		//1、获取当前支付用户的id ，即是邀请表中的invitee
//		//2、根据invitee 获取本人被邀请的相关数据
//		Criteria criteria= new Criteria();
//		Map condition=new HashMap<>();
//		condition.put("invitee", userId);
//		criteria.setCondition(condition);
//		try {
//			List<HajMemberInvitations> list=memberInvitationsService.queryByList(criteria);
//			if(null!=list&&list.size()>0) {
//				HajMemberInvitations hajMemberInvitations=list.get(0);
//				//3、判断其邀请状态是否为成功状态（status 0未成功 1成功），1则，无需更改，当前用户为老用户，已进行过购物操作，完成了交易
//				//4、否则，更新其状态为成功
//				if(hajMemberInvitations.getStatus()!=null&&hajMemberInvitations.getStatus()==0) {
//					hajMemberInvitations.setStatus(1);
//					memberInvitationsService.update(hajMemberInvitations);
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * 获取支付宝异步通知时请求过来的参数（post），返回交易状态
	 * <p>
	 * <br>
	 * author SevenWong<br>
	 * <br>
	 * date 2016年7月4日下午2:26:48
	 */
	private Map<String, String> getPostQueryString(Map<String, String[]> params) {
		Map<String, String> result = new HashMap<>();
		String[] values;
		String value;
		String trade_status = "";
		String notify_id = "";
		String trade_no = "";
		for (String key : params.keySet()) {
			values = params.get(key);
			for (String val : values) {
				value = val;
				if ("trade_status".equals(key)) {
					trade_status = value;
				}
				if ("notify_id".equals(key)) {
					notify_id = value;
				}
				if ("trade_no".equals(key)) {
					trade_no = value;
				}
			}
		}

		// 支付宝异步通知时验证此通知是否由支付宝发起
		if (!"true".equals(AlipayUtil.notifyVerify(notify_id))) {
			trade_status = "";
			log.warn("警告！此通知非支付宝发起，或支付状态已过期");
		}

		result.put("trade_status", trade_status);
		result.put("trade_no", trade_no);

		return result;
	}

	/**
	 * 调用微信订单查询接口判断该订单是否已完成支付
	 * <p>
	 * <br>
	 * author SevenWong <br>
	 * date 2016年7月5日上午11:31:13
	 */
	private boolean wechatTradeQuery(HajRecharge recharge) {
		Map<String, Object> returnMap;
		if (recharge.getApplication() == 1) {
			returnMap = XcxPayUtil.payQuery(recharge.getBankNo());
		} else {
			returnMap = PayUtil.payQuery(recharge.getBankNo());
		}
		if (returnMap != null && "SUCCESS".equals(returnMap.get("result_code"))) {
			if ("SUCCESS".equals(returnMap.get("trade_state"))) {
				return true;
			}
		}
		return false;
	}

}
