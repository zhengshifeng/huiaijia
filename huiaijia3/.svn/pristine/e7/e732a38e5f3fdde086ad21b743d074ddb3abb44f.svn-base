package com.flf.mapper;

import com.base.dao.BaseDao;
import com.flf.entity.HajOrderPayment;
import com.flf.entity.HajOrderPaymentVo;
import com.flf.entity.HajRecharge;

import java.math.BigDecimal;
import java.util.List;

/**
 *
 * <br>
 * <b>功能：</b>HajOrderPaymentDao<br>
 */
public interface HajOrderPaymentMapper extends BaseDao {

	List<HajOrderPaymentVo> list(HajOrderPaymentVo vo);

	int getCount(HajOrderPayment dto);

	HajRecharge getHajRechargeByOrderNo(String orderNo);

	List<HajOrderPaymentVo> listOrderPaid(HajOrderPaymentVo vo);

	BigDecimal calcOrderRefund(HajOrderPaymentVo vo);
}
