package com.flf.mapper;

import java.util.List;
import java.util.Map;

import com.base.criteria.Criteria;
import com.base.dao.BaseDao;
import com.flf.entity.HajFinancial;
import com.flf.entity.HajTradingHistory;

/**
 * 
 * <br>
 * <b>功能：</b>HajTradingHistoryDao<br>
 */
public interface HajTradingHistoryMapper extends BaseDao {

	List<HajTradingHistory> getTradingHistoryByUserId(Criteria criteria);

	List<HajTradingHistory> listPageTradingHistory(HajTradingHistory vo);

	List<Map<String, Object>> excelbatchFinancial(HajFinancial orderVo);
}
