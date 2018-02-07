package com.flf.service;

import com.base.service.BaseService;
import com.flf.entity.HajRecharge;
import com.flf.entity.HajRechargeVo;

import java.util.List;
import java.util.Map;

/**
 * 
 * <br>
 * <b>功能：</b>HajRechargeService<br>
 */
public interface HajRechargeService extends BaseService {

	/**
	 * 更新充值状态为成功
	 * 
	 * @param prepay_id
	 * @param rechargeFor
	 * @param alipayTradeNo
	 *            支付宝充值的订单才需要此字段
	 */
	void updateUserRechargeRecord(String prepay_id, String rechargeFor, String alipayTradeNo);

	List<HajRecharge> list(HajRechargeVo vo);

	HajRecharge getByOutTradeNo(String out_trade_no);

	Map<String, Object> queryTotalRecharge(HajRechargeVo vo);

	List<HajRecharge> queryRechargeListForRerund(HajRechargeVo vo);

	Map<String, Object> queryTotalRefund(HajRechargeVo vo);

	int updateHajRecharge(int id, String alipayTradeNo);

}
