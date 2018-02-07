package com.flf.mapper;


import java.math.BigDecimal;
import java.util.Map;

import com.base.dao.BaseDao;
/**
 * 
 * <br>
 * <b>功能：</b>HajUserFamilyDao<br>
 */
public interface HajUserFamilyMapper extends BaseDao {
	
	Map<String, Object> querryPriceByName(String name);

	void updateUserBalancePoints(BigDecimal actualPayment, int rating,int userId);
}
