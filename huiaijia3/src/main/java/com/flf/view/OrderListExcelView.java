package com.flf.view;

import com.flf.util.DateUtil;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class OrderListExcelView extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String filename = URLEncoder.encode("批量导出", "UTF-8");
		filename += DateUtil.dateformat(new Date(), "yyyyMMddHHmmss");
		HSSFSheet sheet;
		HSSFCell cell;
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment;filename=" + filename + ".xls");
		sheet = workbook.createSheet("商品订单表");

		List<String> titles = (List<String>) model.get("titles");
		int len = titles.size();
		HSSFCellStyle headerStyle = workbook.createCellStyle(); // 标题样式
		headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		headerStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		HSSFFont headerFont = workbook.createFont(); // 标题字体
		headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		headerFont.setFontHeightInPoints((short) 11);
		headerStyle.setFont(headerFont);
		short width = 20, height = 25 * 20;
		sheet.setDefaultColumnWidth(width);
		for (int i = 0; i < len; i++) { // 设置标题
			String title = titles.get(i);
			cell = getCell(sheet, 0, i);
			cell.setCellStyle(headerStyle);
			setText(cell, title);
		}
		sheet.getRow(0).setHeight(height);

		HSSFCellStyle contentStyle = workbook.createCellStyle(); // 内容样式
		contentStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		List<Map<String, Object>> orderList = (List<Map<String, Object>>) model.get("orderList");
		int userCount = orderList.size();
		String residential = "";
		int j = 0;

		for (int i = 0; i < userCount; i++) {
			Map<String, Object> orderMap = orderList.get(i);
			cell = getCell(sheet, i + 1, 0);
			cell.setCellStyle(contentStyle);
			if (!residential.equals(orderMap.get("residential").toString())) {
				j = 0;
			}

			setText(cell, String.valueOf(j + 1));
			residential = orderMap.get("residential").toString();
			cell = getCell(sheet, i + 1, 1);
			cell.setCellStyle(contentStyle);
			if (null != orderMap.get("receiver")) {
				setText(cell, orderMap.get("receiver").toString());
			} else {
				setText(cell, "");
			}

			cell = getCell(sheet, i + 1, 2);
			cell.setCellStyle(contentStyle);
			if (null != orderMap.get("residential")) {
				setText(cell, orderMap.get("residential").toString());
			} else {
				setText(cell, "");
			}

			cell = getCell(sheet, i + 1, 3);
			cell.setCellStyle(contentStyle);
			if (null != orderMap.get("address")) {
				setText(cell, orderMap.get("address").toString());
			} else {
				setText(cell, "");
			}

			cell = getCell(sheet, i + 1, 4);
			cell.setCellStyle(contentStyle);

			if (null != orderMap.get("orderdetails")) {
				setText(cell, orderMap.get("orderdetails").toString());
			} else {
				setText(cell, "");
			}

			cell = getCell(sheet, i + 1, 5);
			cell.setCellStyle(contentStyle);

			if (null != orderMap.get("orderdetails1")) {
				setText(cell, orderMap.get("orderdetails1").toString());
			} else {
				setText(cell, "");
			}

			cell = getCell(sheet, i + 1, 6);
			cell.setCellStyle(contentStyle);

			if (null != orderMap.get("orderdetails2")) {
				setText(cell, orderMap.get("orderdetails2").toString());
			} else {
				setText(cell, "");
			}

			cell = getCell(sheet, i + 1, 7);
			cell.setCellStyle(contentStyle);

			if (null != orderMap.get("orderdetails3")) {
				setText(cell, orderMap.get("orderdetails3").toString());
			} else {
				setText(cell, "");
			}

			cell = getCell(sheet, i + 1, 8);
			cell.setCellStyle(contentStyle);

			if (null != orderMap.get("orderdetails4")) {
				setText(cell, orderMap.get("orderdetails4").toString());
			} else {
				setText(cell, "");
			}

			cell = getCell(sheet, i + 1, 9);
			cell.setCellStyle(contentStyle);
			if (null != orderMap.get("actualPayment")) {
				setText(cell, orderMap.get("actualPayment").toString());
			} else {
				setText(cell, "");
			}

			cell = getCell(sheet, i + 1, 10);
			cell.setCellStyle(contentStyle);
			if (null != orderMap.get("feeWaiver")) {
				setText(cell, orderMap.get("feeWaiver").toString());
			} else {
				setText(cell, "");
			}

			cell = getCell(sheet, i + 1, 11);
			cell.setCellStyle(contentStyle);
			if (null != orderMap.get("totalMoney")) {
				setText(cell, orderMap.get("totalMoney").toString());
			} else {
				setText(cell, "");
			}
			cell = getCell(sheet, i + 1, 12);
			cell.setCellStyle(contentStyle);
			if (null != orderMap.get("mobilePhone")) {
				setText(cell, orderMap.get("mobilePhone").toString());
			} else {
				setText(cell, "");
			}
			cell = getCell(sheet, i + 1, 13);// 订单分类1团购0普通订单2补单
			cell.setCellStyle(contentStyle);
			if (null != orderMap.get("isGrouponOrder")) {
				String isGrouponOrder = orderMap.get("isGrouponOrder").toString();
				if ("1".equals(isGrouponOrder)) {
					setText(cell, "团购");
				} else if ("2".equals(isGrouponOrder)) {
					setText(cell, "补单");
				} else if ("0".equals(isGrouponOrder)) {
					setText(cell, "普通订单");
				}
			} else {
				setText(cell, "");
			}

			cell = getCell(sheet, i + 1, 14);
			cell.setCellStyle(contentStyle);
			if (null != orderMap.get("remark")) {
				setText(cell, orderMap.get("remark").toString());
			} else {
				setText(cell, "");
			}
			j++;
		}

	}

}
