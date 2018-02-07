package com.flf.controller.app;

import com.flf.entity.HajFrontUser;
import com.flf.entity.HajOrder;
import com.flf.entity.HajTradingHistory;
import com.flf.service.HajFrontUserService;
import com.flf.service.HajOrderService;
import com.flf.service.HajTradingHistoryService;
import com.flf.util.DateUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.Callable;

@Service("asynchronousTaskController")
public class AsynchronousTaskController implements Callable<Integer> {

	private final static Logger log = Logger.getLogger(AsynchronousTaskController.class);

	@Autowired
	private HajFrontUserService hajFrontUserService;

	@Autowired
	private HajTradingHistoryService hajTradingHistoryService;

	@Autowired
	private HajOrderService hajOrderService;

	private HajFrontUser user;// 用户
	private HajOrder order;// 订单对象

	public AsynchronousTaskController() {
	}

	public AsynchronousTaskController(HajFrontUserService hajFrontUserService,
			HajTradingHistoryService hajTradingHistoryService, HajOrderService hajOrderService, HajFrontUser user,
			HajOrder order) {
		this.user = user;
		this.order = order;
		this.hajFrontUserService = hajFrontUserService;
		this.hajTradingHistoryService = hajTradingHistoryService;
		this.hajOrderService = hajOrderService;
	}

	@Override
	@RequestMapping("/call")
	public Integer call() throws Exception {
		int rating = order.getPoints() + Integer.parseInt(order.getDispensingTip().toString())
				+ order.getPostFee().setScale(0, BigDecimal.ROUND_DOWN).intValue();
		BigDecimal actualPayment = order.getActualPayment().add(order.getDispensingTip()).add(order.getPostFee());
		String orderNo = order.getOrderNo();
		long start = System.currentTimeMillis();
		int resultId = 0;
		boolean isresult = hajOrderService.isFirstOrder(user, order);
		if (!isresult) {
			log.info("开始异步处理支付业务userId:" + user.getId() + ",rating:" + rating + ",actualPayment:" + actualPayment);

			HajTradingHistory tarding = new HajTradingHistory();
			if (null != user) {
				log.info("用户手机号码：" + user.getMobilePhone() + ",订单号：orderNo：" + orderNo + "订单金额：" + actualPayment);
				// 减少用户金额 ，增加用户积分
				resultId = hajFrontUserService.updateUserBalancePoints(user.getId(), rating, actualPayment, user);
				if (resultId > 0) {
					tarding.setTradingContent("下单成功扣余额：" + actualPayment);
				} else {
					tarding.setTradingContent("下单失败：" + orderNo);
					actualPayment = BigDecimal.ZERO;
					// hajTradingHistoryService.updateOrderFailByOrderNo(orderNo);
				}

				tarding.setCreateTime(DateUtil.dateToString(new Date()));
				tarding.setMoney(actualPayment);
				tarding.setTradingStatus(1);
				tarding.setUserId(user.getId());
				tarding.setType(0);// 减少
				hajTradingHistoryService.saveTradeRecord(tarding);

				// 修改用户优惠卷为已使用
				if (null != order.getCounponId()) {
					log.info("支付成功！优惠卷ID" + order.getCounponId() + "更新为已使用");
					hajOrderService.updateUserUsed(order.getCounponId());
				}
			}
		}
		log.info("用时 : " + (System.currentTimeMillis() - start) + "毫秒");
		return resultId;
	}
}
