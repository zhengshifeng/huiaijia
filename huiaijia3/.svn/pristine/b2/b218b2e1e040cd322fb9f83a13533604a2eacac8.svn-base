package com.flf.view;

import com.flf.util.Tools;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class PurchaseSaleExcelView extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Date date = new Date();
		String filename = Tools.date2Str(date, "yyyyMMddHHmmss");
		HSSFSheet sheet;
		HSSFCell cell;
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment;filename=" + filename + ".xls");
		sheet = workbook.createSheet("商品采购");

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
		List<Map<String, Object>> purchaseList = (List<Map<String, Object>>) model.get("purchaseList");
		int userCount = purchaseList.size();
		for (int i = 0; i < userCount; i++) {
			Map<String, Object> purchaseMap = purchaseList.get(i);
			cell = getCell(sheet, i + 1, 0);
			cell.setCellStyle(contentStyle);
			if (null != purchaseMap.get("commodityNo")) {
				setText(cell, purchaseMap.get("commodityNo").toString());
			} else {
				setText(cell, "");
			}

			cell = getCell(sheet, i + 1, 1);
			cell.setCellStyle(contentStyle);
			if (null != purchaseMap.get("commodityAttr")) {
				setText(cell, purchaseMap.get("commodityAttr").toString());
			} else {
				setText(cell, "");
			}

			cell = getCell(sheet, i + 1, 2);
			cell.setCellStyle(contentStyle);

			if (null != purchaseMap.get("bTypeName")) {
				setText(cell, purchaseMap.get("bTypeName").toString());
			} else {
				setText(cell, "");
			}

			cell = getCell(sheet, i + 1, 3);
			cell.setCellStyle(contentStyle);
			if (null != purchaseMap.get("sTypeName")) {
				setText(cell, purchaseMap.get("sTypeName").toString());
			} else {
				setText(cell, "");
			}

			cell = getCell(sheet, i + 1, 4);
			cell.setCellStyle(contentStyle);
			if (null != purchaseMap.get("name")) {
				setText(cell, purchaseMap.get("name").toString());
			} else {
				setText(cell, "");
			}

			cell = getCell(sheet, i + 1, 5);
			cell.setCellStyle(contentStyle);
			if (null != purchaseMap.get("supplyChain")) {
				setText(cell, purchaseMap.get("supplyChain").toString());
			} else {
				setText(cell, "");
			}

			cell = getCell(sheet, i + 1, 6);
			cell.setCellStyle(contentStyle);

			if (null != purchaseMap.get("number")) {
				setText(cell, purchaseMap.get("number").toString());
			} else {
				setText(cell, "");
			}

			cell = getCell(sheet, i + 1, 7);
			cell.setCellStyle(contentStyle);

			if (null != purchaseMap.get("price")) {
				setText(cell, purchaseMap.get("price").toString());
			} else {
				setText(cell, "");
			}

			cell = getCell(sheet, i + 1, 8);
			cell.setCellStyle(contentStyle);

			if (null != purchaseMap.get("money")) {
				setText(cell, purchaseMap.get("money").toString());
			} else {
				setText(cell, "");
			}

			cell = getCell(sheet, i + 1, 9);
			cell.setCellStyle(contentStyle);

			if (null != purchaseMap.get("createTime")) {
				setText(cell, purchaseMap.get("createTime").toString());
			} else {
				setText(cell, "");
			}

			cell = getCell(sheet, i + 1, 10);
			cell.setCellStyle(contentStyle);

			if (null != purchaseMap.get("weight")) {
				setText(cell, purchaseMap.get("weight").toString());
			} else {
				setText(cell, "");
			}

			cell = getCell(sheet, i + 1, 11);
			cell.setCellStyle(contentStyle);

			if (null != purchaseMap.get("saleMoney")) {
				setText(cell, purchaseMap.get("saleMoney").toString());
			} else {
				setText(cell, "");
			}

			cell = getCell(sheet, i + 1, 12);
			cell.setCellStyle(contentStyle);

			if (null != purchaseMap.get("praise")) {
				setText(cell, purchaseMap.get("praise").toString());
			} else {
				setText(cell, "");
			}

			cell = getCell(sheet, i + 1, 13);
			cell.setCellStyle(contentStyle);

			if (null != purchaseMap.get("sku")) {
				setText(cell, purchaseMap.get("sku").toString());
			} else {
				setText(cell, "");
			}
		}

	}

}
