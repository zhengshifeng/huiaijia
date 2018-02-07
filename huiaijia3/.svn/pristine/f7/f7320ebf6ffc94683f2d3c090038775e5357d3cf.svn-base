package com.flf.util.wms;

import com.base.util.HttpUtil;
import com.flf.entity.*;
import com.flf.entity.wms.*;
import com.flf.util.JSONUtils;
import com.flf.util.MakeOrderNum;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;

/**
 * Created by SevenWong on 2017/11/14 11:04
 *
 * haj其他业务程序调用京东wms接口的入口
 *
 */
public class WmsSender {

	private final static Logger log = Logger.getLogger(WmsSender.class);

	/**
	 * 向wms新增商品分类
	 */
	public static void categoryInfoInsert(HajCommodityType commodityType) throws Exception {
		categorySave(commodityType, WmsMethod.CATEGORY_INFO_INSERT);
	}

	/**
	 * 向wms修改商品分类
	 */
	public static void categoryInfoUpdate(HajCommodityType commodityType) throws Exception {
		categorySave(commodityType, WmsMethod.CATEGORY_INFO_UPDATE);
	}

	/**
	 * 因为新增、修改分类的逻辑处理都一样，只是请求的接口不同，因此在此处抽取出来
	 */
	private static void categorySave(HajCommodityType commodityType, String method) throws Exception {
		// 封装应用级参数对象信息
		WmsCategoryInfo wmsCategoryInfo = new WmsCategoryInfo();
		wmsCategoryInfo.setTenantId(WmsConfig.TENANT_ID);
		wmsCategoryInfo.setCategoryNo(commodityType.getId().toString());
		wmsCategoryInfo.setCategoryName(commodityType.getTypeName());
		wmsCategoryInfo.setOperateUser("汇爱家");

		String sendResult = sendRequest(wmsCategoryInfo, method);

		JSONObject sendResultMap = JSONObject.fromObject(sendResult);
		String code = (String) sendResultMap.get("code");

		if ("0".equals(code)) {
			String resultCode = (String) sendResultMap.get("resultCode");
			if ("1".equals(resultCode)) {
				log.info("boom~~~~~~~");
			} else {
				log.info(sendResultMap.get("message").toString());
			}
		} else {
			log.info(sendResultMap.get("zh_desc").toString());
		}
	}

	/**
	 * 向wms新增商品
	 */
	public static void transportGoodsInfo(HajCommodity commodity) throws Exception {
		// 封装应用级参数对象信息
		WmsGoodsInfo wmsGoodsInfo = new WmsGoodsInfo();
		wmsGoodsInfo.setDeptNo(WmsConfig.DEPT_CODE);
		wmsGoodsInfo.setIsvGoodsNo(commodity.getSku());
		wmsGoodsInfo.setBarcodes(commodity.getSku());
		wmsGoodsInfo.setThirdCategoryNo(commodity.getTypeId().toString());
		wmsGoodsInfo.setGoodsName(commodity.getName());

		String sendResult = sendRequest(wmsGoodsInfo, WmsMethod.TRANSPORT_GOODS_INFO);
		JSONObject sendResultMap = JSONObject.fromObject(sendResult);
		String code = (String) sendResultMap.get("code");

		if ("0".equals(code)) {
			log.info("boom~~~~~~~");
		} else {
			log.info(sendResultMap.get("zh_desc").toString());
		}
	}

	/**
	 * 向wms修改商品
	 */
	public static void updateGoodsInfo(HajCommodity commodity) throws Exception {
		// 封装应用级参数对象信息
		WmsGoodsInfo wmsGoodsInfo = new WmsGoodsInfo();
		wmsGoodsInfo.setGoodsNo(commodity.getSku());
		wmsGoodsInfo.setGoodsName(commodity.getName());

		String sendResult = sendRequest(wmsGoodsInfo, WmsMethod.UPDATE_GOODS_INFO);
		JSONObject sendResultMap = JSONObject.fromObject(sendResult);
		String code = (String) sendResultMap.get("code");

		if ("0".equals(code)) {
			log.info("boom~~~~~~~");
		} else {
			log.info(sendResultMap.get("zh_desc").toString());
		}
	}

	/**
	 * 向wms新增订单信息
	 */
	public static String addOrder(HajOrder order) throws Exception {
		// 订单才用合并后的订单，所以订单号需重新生成
		String orderNo = order.getOrderNo();

		// 封装应用级参数对象信息
		WmsSaleOrder wmsSaleOrder = new WmsSaleOrder();
		wmsSaleOrder.setIsvUUID(orderNo);
		wmsSaleOrder.setIsvSource(orderNo);
		wmsSaleOrder.setShopNo(WmsConfig.SHOP_NO);
		wmsSaleOrder.setDepartmentNo(WmsConfig.DEPT_CODE);
		wmsSaleOrder.setWarehouseNo(WmsConfig.WAREHOUSE_NO);
		wmsSaleOrder.setShipperNo(WmsConfig.SHIPPER_NO);
		wmsSaleOrder.setConsigneeName(order.getReceiver());
		wmsSaleOrder.setConsigneeMobile(order.getContactPhone());
		wmsSaleOrder.setConsigneeAddress(order.getAddress());
		wmsSaleOrder.setSalePlatformSource("APP");
		wmsSaleOrder.setOrderMark("02001100000000");

		StringBuilder goodsNo = new StringBuilder();
		StringBuilder quantity = new StringBuilder();
		StringBuilder price = new StringBuilder();
		HajOrderDetails details;
		for (int i = 0, len = order.getOrderDetailList().size(); i < len; i++) {
			details = order.getOrderDetailList().get(i);
			goodsNo.append(details.getCommodityNo().trim()); // 物料码不能带空格
			quantity.append(details.getNumber());
			price.append(details.getActualPayment());
			if (i < len - 1) {
				goodsNo.append(",");
				quantity.append(",");
				price.append(",");
			}
		}
		wmsSaleOrder.setGoodsNo(goodsNo.toString());
		wmsSaleOrder.setQuantity(quantity.toString());
		wmsSaleOrder.setPrice(price.toString());

		String wlyInfo = "communityNo:"+ order.getResidential()
				+";building:"+ order.getApplication() +";floor:"+ order.getRemark();
		wmsSaleOrder.setWlyInfo(wlyInfo);

		String sendResult = sendRequest(wmsSaleOrder, WmsMethod.ADD_ORDER);
		JSONObject sendResultMap = JSONObject.fromObject(sendResult);
		String code = (String) sendResultMap.get("code");

		if ("0".equals(code)) {
			log.info(order.getUserId() + ": boom~~~~~~~");
			return "success";
		} else {
			log.info(order.getUserId() + " send fail: " +sendResultMap.get("zh_desc").toString());
			return order.getUserId() + sendResultMap.get("zh_desc").toString();
		}
	}

	/**
	 * 向wms新增采购单
	 */
	public static String addPoOrder(List<HajPurchaseOrder> purchaseOrderList) throws Exception {
		// 封装应用级参数对象信息
		WmsPoOrder wmsPoOrder = new WmsPoOrder();
		wmsPoOrder.setSpPoOrderNo(MakeOrderNum.makePurchaseOrderNum());
		wmsPoOrder.setDeptNo(WmsConfig.DEPT_CODE);
		wmsPoOrder.setWhNo(WmsConfig.WAREHOUSE_NO);
		wmsPoOrder.setSupplierNo("1");

		StringBuilder commodityNo = new StringBuilder();
		StringBuilder number = new StringBuilder();
		StringBuilder goodsStatus = new StringBuilder();

		HajPurchaseOrder purchaseOrder;
		for (int i = 0, len = purchaseOrderList.size(); i < len; i++) {
			purchaseOrder = purchaseOrderList.get(i);

			commodityNo.append(purchaseOrder.getCommodityNo().trim());
			number.append(purchaseOrder.getNumber());
			goodsStatus.append("良品");
			if (i < len - 1) {
				commodityNo.append(",");
				number.append(",");
				goodsStatus.append(",");
			}
		}
		wmsPoOrder.setDeptGoodsNo(commodityNo.toString());
		wmsPoOrder.setNumApplication(number.toString());
		wmsPoOrder.setGoodsStatus(goodsStatus.toString());

		String sendResult = sendRequest(wmsPoOrder, WmsMethod.ADD_PO_ORDER);
		JSONObject sendResultMap = JSONObject.fromObject(sendResult);
		String code = (String) sendResultMap.get("code");

		if ("0".equals(code)) {
			log.info("boom~~~~~~~");
			return "success";
		} else {
			log.info(sendResultMap.get("zh_desc").toString());
			return sendResultMap.get("zh_desc").toString();
		}
	}

	/**
	 * 向wms新增、修改小区
	 * 如id已存在，则wms会视为修改
	 */
	public static void receiveCommunity(HajCommunityPersion community) throws Exception {
		// 封装应用级参数对象信息
		WmsCommunity wmsCommunity = new WmsCommunity();
		wmsCommunity.setCommunityNo(community.getId().toString());
		wmsCommunity.setCommunityName(community.getCommunityName());

		String sendResult = sendRequest(wmsCommunity, WmsMethod.RECEIVE_COMMUNITY);
		JSONObject sendResultMap = JSONObject.fromObject(sendResult);
		String code = (String) sendResultMap.get("code");

		if ("0".equals(code)) {
			log.info("boom~~~~~~~");
		} else {
			log.info(sendResultMap.get("zh_desc").toString());
		}
	}

	private static String sendRequest(Object applicationParams, String method) throws Exception {
		String jsonString = JSONUtils.toJSONString(applicationParams);

		// 获取系统级参数
		Map<String, Object> map = WmsUtil.getSystemParams(method);

		// 去掉首尾中括号
		jsonString = jsonString.substring(1, jsonString.length() - 1);
		map.put("360buy_param_json", jsonString);

		String sign = WmsUtil.getSign(map);
		map.put("sign", sign);

		String sendResult = HttpUtil.sendPost(WmsConfig.URL, map);

		log.info("sendResult: " + sendResult);

		// 因为返回的json信息第一级的key不确定，所以直接从第二级json开始使用，通过code的值判断是否请求成功
		int secondJsonIndex = sendResult.indexOf("{", 1);
		sendResult = sendResult.substring(secondJsonIndex, sendResult.length() -1);

		return sendResult;
	}

}
