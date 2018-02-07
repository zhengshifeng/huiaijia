package com.flf.service;

import com.base.criteria.Criteria;
import com.base.service.BaseService;
import com.flf.entity.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * <br>
 * <b>功能：</b>HajOrderService<br>
 */
public interface HajOrderService extends BaseService {

	List<com.flf.entity.HajOrder> getOrderByUserId(Integer userId, Criteria criteria, String createTime);

	List<Map<String, Object>> getOrderTimeByUserId(Criteria criteria) throws Exception;

	int getOrderBySource(Integer userId);

	HajOrder getOrderByOrderNo(String orderNo);

	Map<String, Object> getCommodityCost(List<HajOrderDetails> orderDetailList, int isGrouponOrder, String cityCode)
			throws Exception;

	List<Map<String, Object>> listAllOrder(OrderVO orderVo) throws Exception;

	void deleteOrderById(Integer orderId) throws Exception;

	HajOrder updateOrderStatus(String orderNo);

	Map<String, Object> getOrderInfoById(int orderId);

	List<Map<String, Object>> getOrderByDate(String nowDate);

	int saveHajOrder(HajOrder order, String orderNo);

	HajOrder updateCancelOrder(String orderNo) throws Exception;

	void updateOrderSaleStatus(Integer orderId);

	int updateSaleStatus(String orderNo);

	List<Map<String, Object>> getOrderByNowDate();

	void updateOrderCompleteStatus(HajOrderSale sale);

	/**
	 * 根据订单返回是否可取消状态
	 *
	 * return 0: 不显示取消按钮; 1: 可取消; 2: 显示非激活状态的取消按钮
	 */
	int orderCancelStatus(HajOrder order);

	Map<String, Object> orderCountByTimeForResidential(Map<String, Object> map);

	HajOrder getOrderByCreateTime(Integer userId, String createTime, String orderNo);

	/**
	 * 根据userId返回今天已付款的订单数量
	 * 
	 * @author SevenWong<br>
	 * @date 2016年6月16日下午6:13:30
	 * @param userId
	 * @return
	 */
	int getTodayOrderCountByUserId(Integer userId);

	List<Map<String, Object>> listAllordreList(OrderVO orderVo);

	List<Map<String, Object>> getSortingOrderByDate();

	void updateOrderSorting();

	XSSFWorkbook excelOrderReportDetail(OrderVO orderVo);

	List<Map<String, Object>> getHajPayOrderByUserId(Criteria criteria);

	/**
	 * 合并导出订单详情
	 * 
	 * @author SevenWong<br>
	 * @date 2016年9月26日上午11:07:06
	 * @param orderVo
	 * @return
	 */
	XSSFWorkbook excelHebingOrderReportDetail(OrderVO orderVo);

	List<HajOrder> getTodayPayOrder();

	void updateOrderCostPrice(HajOrder order);

	List<HashMap<String, Object>> getOrderByDateTime(String dateTime);

	/**
	 * 根据用户ID或者手机号查找是否有待配送的补单
	 *
	 * @param mobile
	 *            用户手机号
	 * @return 返回订单信息（只有订单ID与订单号）
	 */
	HajOrder getBuDanByMobile(String mobile);

	/**
	 * 根据excel批量补单
	 * 
	 * @param filePath
	 * @param request
	 * @return
	 */
	String batchImport(String filePath, HttpServletRequest request) throws Exception;

	List<Map<String, Object>> getOrderPostFeeByDate();

	void updateOrderPostFee();

	void updateHajOrderStatus();

	void updateOrderHandleStatus(String nowDate);

	List<Map<String, Object>> listAllSynchronizeOrder(OrderVO orderVo);

	void synchronizeOrder(Map<String, Object> map, String orderClassification);

	void synchronizehebingOrder(Map<String, Object> map);

	void updateWmshandleStatus(String nowDate);

	int queryTodayCount(String nowDate);

	void updateOrderPayStatus(String orderNo, int paymentWay);

	void deleteOrder(String orderNo);

	List<Map<String, Object>> getClearOrderByDate(String orderClearTime);

	void updateClearOrder(Map<String, Object> map);

	int updateOrderRefundTime(Integer orderId);

	void updateWMShandleStatusByOrderNO(String orderNo);

	boolean isFirstOrder(HajFrontUser user, HajOrder order);

	int getTodayOrderNumber(Integer userId);

	void updateUserUsed(Integer counponId);

	XSSFWorkbook excelHebingskuOrderReportDetail(OrderVO orderVo);

	List<HajOrder> listOrder(Criteria criteria);

	XSSFWorkbook wmsExportShipOrder();

	List<HajOrder> wmsSaleOrderList();

	List<Map<String, Object>>  generatePurchaseOrder();

	List<Map<String, Object>> generateSalesOrderOrder();

	List<Map<String, Object>> generateProductionOrder();

	List<HajOrder> getOrderByInviteList(String invitee);
}
