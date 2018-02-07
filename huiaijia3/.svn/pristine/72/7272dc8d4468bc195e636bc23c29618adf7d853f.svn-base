package com.flf.mapper;


import com.base.criteria.Criteria;
import com.base.dao.BaseDao;
import com.flf.vo.HajOrderSaleDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
/**
 * 
 * <br>
 * <b>功能：</b>HajOrderSaleDao<br>
 */
public interface HajOrderSaleMapper extends BaseDao {

	List<Map<String, Object>> listPageHajOrderSale(HajOrderSaleDTO sale);

	int queryHajOrderSaleCount(Criteria criteria);

	int updateSaleStatus(Integer saleId);

	BigDecimal getThisOrderSaleTotalRefunds(Integer orderID);
	
}
