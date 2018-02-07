package com.flf.mapper;

import com.base.criteria.Criteria;
import com.base.dao.BaseDao;
import com.flf.entity.HajOrder;
import com.flf.entity.OrderVO;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <br>
 * <b>功能：</b>HajOrderDao<br>
 */
public interface HajOrderMapper extends BaseDao {

	List<Map<String, Object>> getOrderTimeByUserId(Criteria criteria) throws Exception;

	int getOrderBySource(Integer userId);

	HajOrder getOrderByOrderNo(String orderNo);

	List<Map<String, Object>> listAllOrder(Criteria criteria);

	void deleteOrderById(Integer orderId);

	int updateOrderStatus(@Param("orderNo") String orderNo, @Param("paymentWay") int paymentWay);

	Map<String, Object> getOrderInfoById(int orderId);

	List<Map<String, Object>> getOrderByDate(String nowDate);

	void updateHandleStatus(String nowDate);

	int cancleOrderStatus(String orderNo);

	List<Map<String, Object>> listPageOrder(OrderVO orderVo);

	int updateOrderSaleStatus(Integer id);

	int updateSaleStatus(String orderNo);

	List<Map<String, Object>> getOrderByNowDate();

	int updateOrderCompleteStatus(int id);

	Map<String, Object> orderCountByTimeForResidential(Map<String, Object> map);

	HajOrder getOrderByCreateTime(HajOrder order);

	int getTodayOrderCountByUserId(Integer userId);

	void updateOrderFailByOrderNo(String orderNo);

	List<Map<String, Object>> listAllordreList(OrderVO orderVo);

	List<Map<String, Object>> getSortingOrderByDate();

	void updateOrderSorting();

	List<Map<String, Object>> excelOrderReportDetail(OrderVO orderVo);

	List<Map<String, Object>> getHajPayOrderByUserId(Criteria criteria);

	List<Map<String, Object>> excelHebingOrderReportDetail(OrderVO orderVo);

	List<HajOrder> getTodayPayOrder();

	List<HashMap<String, Object>> getOrderByDateTime(String dateTime);

	HajOrder getBuDanByMobile(String mobile);

	List<Map<String, Object>> getOrderPostFeeByDate();

	void updateOrderPostFee();

	void updateHajOrderStatus();

	void updateUserResidential(@Param("userId") Integer userId, @Param("residential") String residential);

	void updateOrderHandleStatus(String nowDate);

	int queryTodayCount(String nowDate);

	void deleteOrder(String orderNo);

	List<Map<String, Object>> getClearOrderByDate(String orderClearTime);

	int updateClearOrderNo(String orderNo);

	int updateOrderRefundTime(Integer orderId);

	List<Map<String, Object>> listSynchronizeOrder(OrderVO orderVo);

	void updateAllWmshandleStatus(String nowDate);

	List<Map<String, Object>> getOrderIdByUserId(int userId);

	int updateWmshandleStatusByOrderIds(String[] ids);

	void updateWMShandleStatusByOrderNO(String orderNo);

	int getTodayOrderNumber(Integer userId);

	List<Map<String, Object>> excelHebingOrderReportDetail1(OrderVO orderVo);

	List<HajOrder> listOrder(Criteria criteria);

	List<HashMap<String, Object>> wmsExportShipOrder();

	List<HajOrder> wmsSaleOrderList();


	/**
	 * 获取当天订单中指定物料状态为采购的商品
	 *
	 * @return
	 */
	List<Map<String, Object>> generatePurchaseOrder();

	/**
	 * 统计当天订单中带配送的商品
	 *
	 * @return
	 */
	List<Map<String, Object>> generateSalesOrderOrder();

	/**
	 * 统计当天订单中指点物料状态为生产的商品
	 *
	 * @return
	 */
	List<Map<String, Object>> generateProductionOrder();

	List<HajOrder> getOrderByInviteList(String invitee);
}
