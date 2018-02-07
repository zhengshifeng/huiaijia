package com.flf.service;

import com.base.service.BaseService;
import com.flf.entity.HajOrderPayment;
import com.flf.entity.HajOrderPaymentVo;
import com.flf.entity.HajRecharge;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.math.BigDecimal;
import java.util.List;

/**
 *
 * <br>
 * <b>功能：</b>HajOrderPaymentService<br>
 */
public interface HajOrderPaymentService extends BaseService {

	List<HajOrderPaymentVo> list(HajOrderPaymentVo vo);

	XSSFWorkbook export2Excel(HajOrderPaymentVo vo);

	int getCount(HajOrderPayment dto);

	HajRecharge getHajRechargeByOrderNo(String orderNo);

	/**
	 * 根据条件计算已支付的订单总价
	 * @param vo 前台筛选的条件
	 */
	BigDecimal calcOrderPayment(HajOrderPaymentVo vo);

	/**
	 * 根据条件计算已支付的订单退款金额
	 */
	BigDecimal calcOrderRefund(HajOrderPaymentVo vo);

}
