package com.flf.service;

import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.base.criteria.Criteria;
import com.base.service.BaseService;
import com.flf.entity.HajFinancial;
import com.flf.entity.HajOrder;
import com.flf.entity.HajTradingHistory;

/**
 * 
 * <br>
 * <b>功能：</b>HajTradingHistoryService<br>
 */
public interface HajTradingHistoryService extends BaseService {

	/**
	 * 添加交易记录表
	 */
	public int saveTradeRecord(HajTradingHistory trading) throws Exception;

	public void updateCancleOrderTradeRecord(HajOrder order);

	public List<HajTradingHistory> getTradingHistoryByUserId(Criteria criteria);

	List<HajTradingHistory> listPageTradingHistory(HajTradingHistory vo);

	public void updateOrderFailByOrderNo(String orderNo);

	public XSSFWorkbook excelbatchFinancial(HajFinancial orderVo);
}
