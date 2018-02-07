package com.flf.service;

import com.base.service.BaseService;
import com.flf.entity.HajPurchaseOrder;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.List;
import java.util.Map;

/**
 * 
 * <br>
 * <b>功能：</b>HajPurchaseOrderService<br>
 */
public interface HajPurchaseOrderService extends BaseService {

	int savePurchaseOrder(Map<String, Object> map);

	void updateOrderHandle(String nowDate);

	List<Map<String, Object>> listPageOrder(HajPurchaseOrder purchase);

	Map<String, Object> queryTotalPurchase(HajPurchaseOrder purchase);

	List<Map<String, Object>> listAllpurchaseList(HajPurchaseOrder purchase);

	XSSFWorkbook excelHebingDetail(HajPurchaseOrder purchase);

	List<Map<String, Object>> listPageToDayPurchaseList(HajPurchaseOrder purchase);

	Map<String, Object> queryTotalToDayPurchaseList(HajPurchaseOrder purchase);

	XSSFWorkbook excelToDayPurchase(HajPurchaseOrder purchase);

	int savePurchaseOrderDateTime(Map<String, Object> obj);

	List<HajPurchaseOrder> jdWmsPoOrder();

}
