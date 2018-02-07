package com.flf.mapper;

import com.base.criteria.Criteria;
import com.base.dao.BaseDao;
import com.flf.entity.HajPurchaseOrder;

import java.util.List;
import java.util.Map;

/**
 * 
 * <br>
 * <b>功能：</b>HajPurchaseOrderDao<br>
 */
public interface HajPurchaseOrderMapper extends BaseDao {

	HajPurchaseOrder getPurchaseOrderBy(String commodityNo);

	int querylistAllOrderCount(Criteria criteria);

	void updatePurchaseOrderByVersion(HajPurchaseOrder purchese);

	List<Map<String, Object>> listPageOrder(HajPurchaseOrder purchase);

	Map<String, Object> queryTotalPurchase(HajPurchaseOrder purchase);

	List<Map<String, Object>> listAllpurchaseList(HajPurchaseOrder purchase);

	List<Map<String, Object>> excelHebingDetail(HajPurchaseOrder purchase);

	List<Map<String, Object>> listPageToDayPurchaseList(HajPurchaseOrder purchase);

	List<Map<String, Object>> excelToDayPurchase(HajPurchaseOrder purchase);

	List<HajPurchaseOrder> jdWmsPoOrder();

}
