package com.flf.service.impl;

import com.base.service.BaseServiceImpl;
import com.flf.entity.HajIntegralDetails;
import com.flf.mapper.HajIntegralDetailsMapper;
import com.flf.service.HajIntegralDetailsService;
import com.flf.util.Tools;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>HajIntegralDetailsService<br>
 */
@Service("hajIntegralDetailsService")
public class HajIntegralDetailsServiceImpl extends BaseServiceImpl implements HajIntegralDetailsService {
	private final static Logger log = Logger.getLogger(HajIntegralDetailsServiceImpl.class);
	
	@Autowired
	private HajIntegralDetailsMapper dao;
 
	@Override
	public HajIntegralDetailsMapper getDao() {
		return dao;
	}

	@Override
	public List<HajIntegralDetails> listPage(HajIntegralDetails po) {
		return dao.listPage(po);
	}

	@Override
	public List<HashMap<String, Object>> listPage4app(HajIntegralDetails dto) {
		return dao.listPage4app(dto);
	}

	@Override
	public void saveDetail(HajIntegralDetails integralDetails) throws Exception {
		if (integralDetails == null || integralDetails.getUserId() == null) {
			log.info("记录积分详情时发现对象为空，或用户ID为空，不予记录");
			return;
		}

		// 记录历史积分，如果之前没记录过历史积分才会记录
		this.saveHistoryIntegral(integralDetails);

		// 加这层判断的原因是HajIntegralDetailsAppController调用list为空时，则记录历史积分
		if (Tools.isNotEmpty(integralDetails.getRemark())) {
			// 记录本次详情
			dao.add(integralDetails);
		}
	}

	/**
	 * 如果积分详情表没有记录该用户的积分，则把目前该用户的所有积分当成历史总积分记录
	 */
	private void saveHistoryIntegral(HajIntegralDetails integralDetails) throws Exception {
		HashMap<String, Object> recordsByUserId = dao.getRecordsByUserId(integralDetails.getUserId());
		Integer records = Integer.valueOf(recordsByUserId.get("records").toString());
		Integer integral = Integer.valueOf(recordsByUserId.get("integral").toString());
		if (records == 0 && integral > 0) {
			// 减掉之前处理过的积分
			BigDecimal integralDecimal = new BigDecimal(integral);
			integralDecimal = integralDecimal.subtract(new BigDecimal(integralDetails.getDetail()));
			integral = integralDecimal.intValue();
			log.info("积分明细表无数据，记录历史累计积分: +" + integral);
			HajIntegralDetails historyIntegral = new HajIntegralDetails();
			historyIntegral.setUserId(integralDetails.getUserId());
			historyIntegral.setRemark("历史积分");
			historyIntegral.setDetail("+" + integral);
			dao.add(historyIntegral);
		}
	}

}
