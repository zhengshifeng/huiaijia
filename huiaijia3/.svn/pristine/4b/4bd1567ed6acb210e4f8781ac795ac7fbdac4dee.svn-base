package com.flf.service.impl;

import com.base.criteria.Criteria;
import com.base.service.BaseServiceImpl;
import com.flf.entity.HajFinancial;
import com.flf.entity.HajOrder;
import com.flf.entity.HajTradingHistory;
import com.flf.mapper.HajOrderMapper;
import com.flf.mapper.HajTradingHistoryMapper;
import com.flf.service.HajTradingHistoryService;
import com.flf.util.DateUtil;
import com.flf.util.HajOrderUtil;
import com.flf.util.Tools;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 
 * <br>
 * <b>功能：</b>HajTradingHistoryService<br>
 */
@Service("hajTradingHistoryService")
public class HajTradingHistoryServiceImpl extends BaseServiceImpl implements HajTradingHistoryService {

	@Autowired
	private HajTradingHistoryMapper dao;
	@Autowired
	private HajOrderMapper orderDao;

	@Override
	public HajTradingHistoryMapper getDao() {
		return dao;
	}

	/**
	 * 添加交易记录
	 */
	@Override
	public int saveTradeRecord(HajTradingHistory trading) throws Exception {
		if (trading.getId() != null && trading.getId().intValue() > 0) {
			dao.update(trading);
		} else {
			dao.add(trading);
		}
		return trading.getId();
	}

	/**
	 * 添加用户取掉订单
	 */
	@Override
	public void updateCancleOrderTradeRecord(HajOrder order) {
		HajTradingHistory tarding = new HajTradingHistory();
		tarding.setCreateTime(DateUtil.dateToString(new Date()));
		tarding.setMoney(order.getActualPayment().add(order.getDispensingTip()).add(order.getPostFee()));
		tarding.setTradingStatus(1);
		tarding.setTradingContent("取消订单返还余额："
				+ order.getActualPayment().add(order.getDispensingTip()).add(order.getPostFee()));
		tarding.setUserId(order.getUserId());
		tarding.setType(1);// 增加
		try {
			this.saveTradeRecord(tarding);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 查询用户交易历史记录
	 */
	@Override
	public List<HajTradingHistory> getTradingHistoryByUserId(Criteria criteria) {
		try {
			return dao.getTradingHistoryByUserId(criteria);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<HajTradingHistory> listPageTradingHistory(HajTradingHistory vo) {
		return dao.listPageTradingHistory(vo);
	}

	@Override
	public void updateOrderFailByOrderNo(String orderNo) {
		orderDao.updateOrderFailByOrderNo(orderNo);
	}

	@Override
	public XSSFWorkbook excelbatchFinancial(HajFinancial orderVo) {

		// 创建HSSFWorkbook对象(excel的文档对象)
		XSSFWorkbook wkb = new XSSFWorkbook();

		// 建立新的sheet对象（excel的表单）
		XSSFSheet sheet = wkb.createSheet();
		sheet.setDefaultColumnWidth(15);
		// sheet.setDefaultRowHeightInPoints(20);

		// 在sheet里创建第1行
		XSSFRow row = sheet.createRow(0);

		// 创建单元格并设置单元格内容
		this.setDefaultXSSFRow(row, sheet);

		XSSFCellStyle cellStyle = wkb.createCellStyle();

		XSSFFont font = wkb.createFont();
		font.setBold(true);
		cellStyle.setFont(font);
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_JUSTIFY);
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cellStyle.setFillForegroundColor(HSSFColor.TAN.index);
		// ============================================================================
		// 开始写入上报小区数据
		// ============================================================================
		List<Map<String, Object>> orderList = dao.excelbatchFinancial(orderVo);

		Map<String, Object> vo = null;

		int cellIndex = 0;
		String returnStatus = "";

		for (int i = 0, len = orderList.size(); i < len; i++, cellIndex = 0) {
			vo = orderList.get(i);
			row = sheet.createRow(i + 1);

			row.createCell(cellIndex++).setCellValue(i + 1);
			row.createCell(cellIndex++).setCellValue(getMapValue(vo.get("orderNo")));
			row.createCell(cellIndex++).setCellValue(
					getOrderGrouponOrder(getMapValue(vo.get("isGrouponOrder")), returnStatus));
			row.createCell(cellIndex++).setCellValue(HajOrderUtil.getOrderStatus(getMapValue(vo.get("status"))));

			row.createCell(cellIndex++).setCellValue(getMapValue(vo.get("createTime")));
			row.createCell(cellIndex++).setCellValue(getMapValue(vo.get("mobilePhone")));
			row.createCell(cellIndex++).setCellValue(Tools.getCityNameByAreaCode(getMapValue(vo.get("areaCode"))));

			row.createCell(cellIndex++).setCellValue(getMapValue(vo.get("residential")));
			row.createCell(cellIndex++).setCellValue(getMapValue(vo.get("address")));

			row.createCell(cellIndex++).setCellValue(getPaymentWayet(getMapValue(vo.get("paymentWay")), returnStatus));

			row.createCell(cellIndex++).setCellValue(getMapValue(vo.get("paymentTime")));
			row.createCell(cellIndex++).setCellValue(getMapValue(vo.get("number")));

			row.createCell(cellIndex++).setCellValue(getMapValue(vo.get("commodityCost")));

			row.createCell(cellIndex++).setCellValue(
					getMapValue(Double.parseDouble(vo.get("actualPayment").toString())
							- Double.parseDouble(vo.get("dispensingTip").toString())
							- Double.parseDouble(vo.get("postFee").toString())));
			row.createCell(cellIndex++).setCellValue(getMapValue(vo.get("orderRealPay")));
			row.createCell(cellIndex++).setCellValue(getMapValue(vo.get("dispensingTip")));
			row.createCell(cellIndex++).setCellValue(getMapValue(vo.get("postFee")));

			row.createCell(cellIndex++).setCellValue(getMapValue(vo.get("counponMoney")));

			row.createCell(cellIndex++).setCellValue(getMapValue(vo.get("actualPayment")));
			row.createCell(cellIndex++).setCellValue(getMapValue(vo.get("refundMoney")));

			row.createCell(cellIndex++).setCellValue(getMapValue(vo.get("points")));

		}

		return wkb;

	}

	// 订单支付方式(0:余额;1:微信;2:支付宝)
	private String getPaymentWayet(String status, String returnStatus) {
		switch (status) {
		case "1":
			returnStatus = "微信";
			break;
		case "2":
			returnStatus = "支付宝";
			break;
		case "0":
			returnStatus = "余额";
			break;
		default:
			break;
		}
		return returnStatus;
	}

	// 订单分类1团购0普通订单2补单
	private String getOrderGrouponOrder(String status, String returnStatus) {
		switch (status) {
		case "1":
			returnStatus = "团购订单";
			break;
		case "2":
			returnStatus = "补单";
			break;
		case "0":
			returnStatus = "普通订单";
			break;
		default:
			break;
		}
		return returnStatus;
	}

	/**
	 * 将map中的值转为String，如对象为空则返回空字符串
	 *
	 * @param obj
	 * @return
	 * @author SevenWong<br>
	 * @date 2016年9月23日下午4:56:03
	 */
	private String getMapValue(Object obj) {
		return (obj == null) ? Tools.EMPTY : obj.toString();
	}

	private void setDefaultXSSFRow(XSSFRow row, XSSFSheet sheet) {
		int cellIndex = 0;
		// 初始化第一行标题
		row.createCell(cellIndex++).setCellValue("序号");
		row.createCell(cellIndex++).setCellValue("订单号");
		row.createCell(cellIndex++).setCellValue("订单类型");
		row.createCell(cellIndex++).setCellValue("订单状态");
		row.createCell(cellIndex++).setCellValue("下单时间");
		row.createCell(cellIndex++).setCellValue("注册手机号码");
		row.createCell(cellIndex++).setCellValue("城市");
		row.createCell(cellIndex++).setCellValue("小区名称");
		row.createCell(cellIndex++).setCellValue("收获地址");
		row.createCell(cellIndex++).setCellValue("支付方式");
		row.createCell(cellIndex++).setCellValue("支付时间");
		row.createCell(cellIndex++).setCellValue("商品件数");
		row.createCell(cellIndex++).setCellValue("商品成本");
		row.createCell(cellIndex++).setCellValue("商品实收");
		row.createCell(cellIndex++).setCellValue("商品盈亏");
		row.createCell(cellIndex++).setCellValue("配送小费");
		row.createCell(cellIndex++).setCellValue("配送费");
		row.createCell(cellIndex++).setCellValue("优惠券抵扣");
		row.createCell(cellIndex++).setCellValue("订单实付");
		row.createCell(cellIndex++).setCellValue("退款金额");
		row.createCell(cellIndex++).setCellValue("积分");
	}

}
