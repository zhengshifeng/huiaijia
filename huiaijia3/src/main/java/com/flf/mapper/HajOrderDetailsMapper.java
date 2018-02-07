package com.flf.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.base.dao.BaseDao;
import com.flf.entity.HajOrderDetails;

/**
 * 
 * <br>
 * <b>功能：</b>HajOrderDetailsDao<br>
 */
public interface HajOrderDetailsMapper extends BaseDao {

	List<Map<String, Object>> listAllOrderDetails1(@Param("orderIds") String[] orderIds);

	List<Map<String, Object>> listAllOrderDetails(int orderId);

	void updateDetailStatus(String orderNo);

	List<HajOrderDetails> getDetailByOrderId(Integer orderId);

	List<HajOrderDetails> getChainDetailByOrderId(String orderId);

	int getTodayOrderMoneyNoCommoidty(@Param("orderId") String orderId, @Param("commodityAttr") String commodityAttr);

	List<Map<String, Object>> listAllSysOrderDetails(@Param("classificationStr") String[] classificationStr,
			@Param("orderIds") String[] orderIds);

	String getsbTypeNamebyOrderId(@Param("orderIds") String[] orderIds);

}
