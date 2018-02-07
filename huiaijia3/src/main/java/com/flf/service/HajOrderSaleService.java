package com.flf.service;

import com.base.service.BaseService;
import com.flf.entity.User;
import com.flf.vo.HajOrderSaleDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 
 * <br>
 * <b>功能：</b>HajOrderSaleService<br>
 */
public interface HajOrderSaleService extends BaseService {

	List<Map<String, Object>> listPageHajOrderSale(HajOrderSaleDTO sale);

	int updateSaleStatus(Integer saleId);

	Map<String, Object> updateSaleRefund(Integer saleIds, Map<String, Object> jsonMap, User user) throws Exception;

	BigDecimal getThisOrderSaleTotalRefunds(Integer orderID);

}
