package com.flf.service.impl;

import com.alipay.util.AlipayUtil;
import com.base.service.BaseServiceImpl;
import com.flf.entity.*;
import com.flf.mapper.HajOrderSaleMapper;
import com.flf.resolver.RollbackException;
import com.flf.service.*;
import com.flf.util.DateUtil;
import com.flf.util.Tools;
import com.flf.util.gexin.AppPush;
import com.flf.vo.HajOrderSaleDTO;
import com.weixin.PayUtil;
import com.weixin.xcx.XcxPayUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <br>
 * <b>功能：</b>HajOrderSaleService<br>
 */
@Service("hajOrderSaleService")
public class HajOrderSaleServiceImpl extends BaseServiceImpl implements HajOrderSaleService {
	private final static Logger log = Logger.getLogger(HajOrderSaleServiceImpl.class);

	@Autowired
	private HajOrderSaleMapper dao;

	@Autowired(required = false)
	private HajOrderService hajOrderService;

	@Autowired(required = false)
	private HajOrderDetailsService orderDetailsService;

	@Autowired(required = false)
	private HajFrontUserService hajFrontUserService;

	@Autowired(required = false)
	private HajOrderPaymentService orderPaymentService;

	@Autowired(required = false)
	private HajTradingHistoryService hajTradingHistoryService;

	@Autowired(required = false)
	private RedisSpringProxy redisSpringProxy;

	@Autowired(required = false)
	private HajOrderProblemService orderProblemService;

	@Override
	public HajOrderSaleMapper getDao() {
		return dao;
	}

	@Override
	public List<Map<String, Object>> listPageHajOrderSale(HajOrderSaleDTO sale) {
		if (sale.getSort() != null) {
			if (sale.getSort() == 1) {
				sale.setOrderBy(" os.`createTime` ASC");
			} else if (sale.getSort() == 2) {
				sale.setOrderBy(" os.`dealTime` ASC");
			} else if (sale.getSort() == 3) {
				sale.setOrderBy(" o.`createTime` DESC");
			} else {
				sale.setOrderBy(null);
			}
		}
		return dao.listPageHajOrderSale(sale);
	}

	@Override
	public int updateSaleStatus(Integer saleId) {
		return dao.updateSaleStatus(saleId);
	}

	@Override
	public Map<String, Object> updateSaleRefund(Integer saleId, Map<String, Object> jsonMap, User user) throws Exception {
		HajOrderSale orderSale = dao.queryById(saleId);
		if (orderSale == null) {
			throw new RollbackException("未知的订单支付方式，无法发起退款");
		}

		if (orderSale.getIsdeal() == 1 || orderSale.getMoney().compareTo(BigDecimal.ZERO) <= 0) {
			throw new RollbackException("该订单已处理或者退款金额不合法，请检查");
		}

		HajFrontUser frontUser = hajFrontUserService.getUserByMobile(orderSale.getCellPhone());
		if (frontUser == null) {
			throw new RollbackException("该订单用户数据异常，请检查");
		}

		Integer userId = frontUser.getId();

		// 将此次退款的状态改为已处理，返回此次修改的受影响行数
		int updateSaleStatus = dao.updateSaleStatus(saleId);
		if (updateSaleStatus < 1) {
			throw new RollbackException("状态修改失败");
		}

		// 更新退款时间及操作人
		HajOrderSale updateOrderSale = new HajOrderSale();
		updateOrderSale.setId(orderSale.getId());
		updateOrderSale.setDealer(user.getUsername());
		updateOrderSale.setDealTime(new Date());
		dao.updateBySelective(updateOrderSale);

		// 售后退款申请金额
		BigDecimal orderSaleMoney = orderSale.getMoney();
		HajOrder hajOrder = hajOrderService.queryById(orderSale.getOrderId());
		if (hajOrder != null) {
			// 更新订单状态为已退款(仅当所有售后退款都已完成后)
			hajOrderService.updateSaleStatus(hajOrder.getOrderNo());

			HajRecharge rechargeByOrderNo = orderPaymentService.getHajRechargeByOrderNo(hajOrder.getOrderNo());

			// 将此次退款写入交易历史记录表
			HajTradingHistory tradingHistory = new HajTradingHistory();
			boolean refundSuccess;

			// 订单实付金额
			BigDecimal orderActualPayment = hajOrder.getActualPayment().add(
					hajOrder.getDispensingTip().add(hajOrder.getPostFee()));
			if (hajOrder.getPaymentWay() == 0) {
				// 退款用户金额增加
				hajFrontUserService.updateUserBalancePoints(userId, orderSaleMoney);
				tradingHistory.setTradingContent("订单退款至帐户余额增加：" + orderSaleMoney);
				refundSuccess = true;
			} else if (hajOrder.getPaymentWay() == 1) {
				if (hajOrder.getApplication() == 1) {
					// 小程序退款
					refundSuccess = XcxPayUtil.refund(rechargeByOrderNo.getBankNo(),
							orderSale.getRefundNo(),
							orderActualPayment.multiply(XcxPayUtil.ONE_HUNDRED).intValue(),
							orderSaleMoney.multiply(XcxPayUtil.ONE_HUNDRED).intValue());
				} else {
					// APP退款
					refundSuccess = PayUtil.refund(rechargeByOrderNo.getBankNo(),
							orderSale.getRefundNo(),
							orderActualPayment.multiply(PayUtil.ONE_HUNDRED).intValue(),
							orderSaleMoney.multiply(PayUtil.ONE_HUNDRED).intValue());
				}

				tradingHistory.setTradingContent("退款至微信：" + orderSaleMoney);
			} else if (hajOrder.getPaymentWay() == 2) {
				refundSuccess = AlipayUtil.refundFastpayByPlatformNopwd(
						rechargeByOrderNo.getAlipayTradeNo(),
						orderSaleMoney.toString(), 1, orderSale.getRefundNo(),
						orderSale.getQuersion());
				tradingHistory.setTradingContent("退款至支付宝：" + orderSaleMoney);
			} else {
				throw new RollbackException("未知的订单支付方式，无法发起退款");
			}

			if (!refundSuccess) {
				throw new RollbackException("退款失败，可能退款金额不合法");
			}

			tradingHistory.setCreateTime(DateUtil.dateToString(new Date()));
			tradingHistory.setMoney(orderSaleMoney);
			tradingHistory.setTradingStatus(1);
			tradingHistory.setUserId(userId);
			tradingHistory.setType(1);
			hajTradingHistoryService.saveTradeRecord(tradingHistory);

			hajOrderService.updateOrderRefundTime(hajOrder.getId());

			if (orderSale.getProblemId() != null) {
				HajOrderProblem problem = new HajOrderProblem();
				problem.setId(orderSale.getProblemId());
				problem.setValid(1);
				orderProblemService.updateBySelective(problem);

				// 订单详情中记录此次退款
				HajOrderProblem queryProblemById = orderProblemService.queryById(orderSale.getProblemId());
				HajOrderDetails orderDetails = null;
				if (queryProblemById != null && Tools.isNotEmpty(queryProblemById.getCommodityNo())) {
					for (HajOrderDetails details : hajOrder.getOrderDetailList()) {
						if (queryProblemById.getCommodityNo().equals(details.getCommodityNo())) {
							orderDetails = details;
							break;
						}
					}
					if (orderDetails != null) {
						HajOrderDetails detailsUpdate = new HajOrderDetails();
						detailsUpdate.setId(orderDetails.getId());

						if (orderDetails.getAfterSaleRecord() != null) {
							// 每次累加
							detailsUpdate.setAfterSaleRecord(orderDetails.getAfterSaleRecord() +
									"退款*" + queryProblemById.getNumber() + ";");
						} else {
							detailsUpdate.setAfterSaleRecord("退款*" + queryProblemById.getNumber() + ";");
						}
						orderDetailsService.updateBySelective(detailsUpdate);
					}
				}
			}
		}

		// 退款后通过友盟推送到用户APP
		String phoneModel = frontUser.getPhoneModel();
		log.info("售后退款用户id: " + userId + "; 发送推送消息的phoneModel: " + phoneModel);
		if (phoneModel != null) {
			try {
				String title = redisSpringProxy.read("SystemConfiguration_order_refund_push_title").toString();
				String content = redisSpringProxy.read("SystemConfiguration_order_refund_push_content")
						.toString();
				content = content.replace("###", orderSaleMoney.toString());
				AppPush.sendByAlias(String.valueOf(userId), title, content, "refund");
			} catch (Exception e) {
				log.info("发送推送异常", e);
			}
		}

		jsonMap.put("error", "0");
		jsonMap.put("msg", "退款成功！");

		return jsonMap;
	}

	@Override
	public BigDecimal getThisOrderSaleTotalRefunds(Integer orderID) {
		return dao.getThisOrderSaleTotalRefunds(orderID);
	}
}
