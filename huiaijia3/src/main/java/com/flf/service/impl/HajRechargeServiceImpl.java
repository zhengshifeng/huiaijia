package com.flf.service.impl;

import com.base.service.BaseServiceImpl;
import com.flf.entity.HajFrontUser;
import com.flf.entity.HajRecharge;
import com.flf.entity.HajRechargeVo;
import com.flf.entity.HajTradingHistory;
import com.flf.mapper.HajFrontUserMapper;
import com.flf.mapper.HajRechargeMapper;
import com.flf.mapper.HajTradingHistoryMapper;
import com.flf.service.HajFrontUserService;
import com.flf.service.HajRechargeService;
import com.flf.service.IHajSMSPushService;
import com.flf.util.DateUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <br>
 * <b>功能：</b>HajRechargeService<br>
 */
@Service("hajRechargeService")
public class HajRechargeServiceImpl extends BaseServiceImpl implements HajRechargeService {
	private final static Logger log = Logger.getLogger(HajRechargeServiceImpl.class);

	@Autowired
	private HajRechargeMapper dao;

	@Autowired
	private HajFrontUserMapper userDao;

	@Autowired
	private HajTradingHistoryMapper historyDao;

	@Autowired
	private IHajSMSPushService hajSMSPushService;

	@Autowired
	private HajFrontUserService frontUserService;

	@Override
	public HajRechargeMapper getDao() {
		return dao;
	}

	@Override
	public int updateHajRecharge(int id, String alipayTradeNo) {
		int updateStatus = dao.updateHajRecharge(id, alipayTradeNo);
		log.info("充值单号id: " + id + " 状态更新为成功，updateStatus: " + updateStatus);
		return updateStatus;
	}

	/**
	 * 充值成功后，更新充值状态，增加用户金额，写入交易流水记录
	 */
	@Override
	public void updateUserRechargeRecord(String prepay_id, String rechargeFor, String alipayTradeNo) {
		Map<String, Object> rechargeMap = dao.getHajRechargeByPrepay_id(prepay_id);
		log.error("进行余额更新..............");
		if (null != rechargeMap && rechargeFor.equals("0")) {
			log.error("进入更新余额方法................");
			int userId = Integer.parseInt(rechargeMap.get("userId").toString());
			BigDecimal rechargeMoney=BigDecimal.ZERO;
			if(rechargeMap.get("money")!=null){
				rechargeMoney = new BigDecimal(rechargeMap.get("money").toString());
			}
			BigDecimal accountAmount=BigDecimal.ZERO;
			if(rechargeMap.get("accountAmount")!=null){
				accountAmount=new BigDecimal(rechargeMap.get("accountAmount").toString());
			}
			HajTradingHistory trading = new HajTradingHistory();
			HajFrontUser user = new HajFrontUser();
			user.setId(userId);
			if(accountAmount!=null && accountAmount.doubleValue()>0){
				user.setBalance(accountAmount);
				trading.setTradingContent("充值增加余额：" + accountAmount);
			}else {
				user.setBalance(rechargeMoney);
				trading.setTradingContent("充值增加余额：" + rechargeMoney);
			}

			log.error("修改余额="+user.getId()+"余额="+user.getBalance());
			userDao.updateUserMoneyByRecharge(user);
			log.error("修改成功。。。。。。。。。。。。。。。。。。。。！");
			try {
				// 充值成功短信通知
				HajFrontUser frontUser = frontUserService.queryById(userId);
				if (frontUser != null) {
					hajSMSPushService.rechargeDone(frontUser.getMobilePhone(), rechargeMoney);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			trading.setCreateTime(DateUtil.dateToString(new Date()));

			if(accountAmount!=null && accountAmount.doubleValue()>0){
				trading.setMoney(accountAmount);
			}else {
				trading.setMoney(rechargeMoney);
			}
			trading.setTradingStatus(1);
			trading.setUserId(userId);
			trading.setType(1);// 减少
			historyDao.add(trading);
		}
	}

	@Override
	public List<HajRecharge> list(HajRechargeVo vo) {
		return dao.listPage(vo);
	}

	/**
	 * 根据out_trade_no(bankNo)返回数据库中对应的记录
	 * <p>
	 * author SevenWong<br>
	 * date 2016年6月13日下午2:59:39
	 */
	@Override
	public HajRecharge getByOutTradeNo(String out_trade_no) {
		return dao.getByOutTradeNo(out_trade_no);
	}

	@Override
	public Map<String, Object> queryTotalRecharge(HajRechargeVo vo) {
		return dao.queryTotalRecharge(vo);
	}

	@Override
	public List<HajRecharge> queryRechargeListForRerund(HajRechargeVo vo) {

		return dao.queryRechargeListForRerund(vo);
	}

	@Override
	public Map<String, Object> queryTotalRefund(HajRechargeVo vo) {
		return dao.queryTotalRefund(vo);
	}

}
