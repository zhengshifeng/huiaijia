package com.flf.util;

import com.alipay.util.AlipayUtil;
import com.flf.entity.HajRecharge;
import com.flf.service.HajRechargeService;
import com.weixin.PayUtil;
import com.weixin.xcx.XcxPayUtil;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by SevenWong on 2017/2/16 17:16.
 * 查询退款金额工具类
 */
public class HajRefund extends Thread {

	private Logger log = Logger.getLogger(HajRefund.class);

	private HajRecharge hajRecharge;

	private HajRechargeService hajRechargeService;

	public HajRefund(HajRecharge hajRecharge, HajRechargeService hajRechargeService) {
		this.hajRecharge = hajRecharge;
		this.hajRechargeService = hajRechargeService;
	}

	@Override
	public synchronized void run() {
		log.info(currentThread().getName() + "启动准备处理数据");
		try {
			if (null != hajRecharge) {
				// 2支付宝 1微信
				int rechargeType = hajRecharge.getRechargeType();
				if (rechargeType == 1) {
					// 微信
					BigDecimal refundWeiXin;
					if (hajRecharge.getApplication() == 1) {
						refundWeiXin = XcxPayUtil.refundQuery(hajRecharge.getBankNo());
					} else {
						refundWeiXin = PayUtil.refundQuery(hajRecharge.getBankNo());
					}
					if (!refundWeiXin.equals(BigDecimal.ZERO)) {
						hajRecharge.setRefundNum(refundWeiXin);
						hajRecharge.setIsRefund("1");
						hajRecharge.setRefundUpdateTime(DateUtil.datetime2Str(new Date()));
						hajRechargeService.updateBySelective(hajRecharge);
					}
				} else {
					// 支付宝
					String refundAlipay = AlipayUtil.alipayRefundStatus(hajRecharge.getBankNo());
					if (refundAlipay != null) {
						hajRecharge.setIsRefund("1");
						hajRecharge.setRefundNum(new BigDecimal(refundAlipay));
						hajRecharge.setRefundUpdateTime(DateUtil.datetime2Str(new Date()));
						hajRechargeService.updateBySelective(hajRecharge);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info(currentThread().getName() + "处理数据时出错" + e);
		} finally {
			log.info(currentThread().getName() + "处理完成");
		}
	}
}
