package com.flf.view;

import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.flf.util.Tools;

public class CustomerExcelView extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Date date = new Date();
		String filename = URLEncoder.encode("客户列表_", "UTF-8") + Tools.date2Str(date, "yyyyMMddHHmmss");
		HSSFSheet sheet;
		HSSFCell cell;
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment;filename=" + filename + ".xls");
		sheet = workbook.createSheet("客户列表");

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
		List<Map<String, Object>> userManagerList = (List<Map<String, Object>>) model.get("userManagerList");
		int userCount = userManagerList.size();
		for (int i = 0; i < userCount; i++) {
			Map<String, Object> userMap = userManagerList.get(i);
			cell = getCell(sheet, i + 1, 0);
			cell.setCellStyle(contentStyle);
			if (null != userMap.get("id")) {
				setText(cell, userMap.get("id").toString());
			} else {
				setText(cell, "");
			}

			cell = getCell(sheet, i + 1, 1);
			cell.setCellStyle(contentStyle);
			if (null != userMap.get("username")) {
				setText(cell, userMap.get("username").toString());
			} else {
				setText(cell, "");
			}

			cell = getCell(sheet, i + 1, 2);
			cell.setCellStyle(contentStyle);

			if (null != userMap.get("mobilePhone")) {
				setText(cell, userMap.get("mobilePhone").toString());
			} else {
				setText(cell, "");
			}

			cell = getCell(sheet, i + 1, 3);
			cell.setCellStyle(contentStyle);
			if (null != userMap.get("createTime")) {
				setText(cell, userMap.get("createTime").toString());
			} else {
				setText(cell, "");
			}

			cell = getCell(sheet, i + 1, 4);
			cell.setCellStyle(contentStyle);
			if (null != userMap.get("residential")) {
				setText(cell, userMap.get("residential").toString());
			} else {
				setText(cell, "");
			}

			cell = getCell(sheet, i + 1, 5);
			cell.setCellStyle(contentStyle);

			if (null != userMap.get("shippingAddress")) {
				setText(cell, userMap.get("shippingAddress").toString());
			} else {
				setText(cell, "");
			}

			cell = getCell(sheet, i + 1, 6);
			cell.setCellStyle(contentStyle);
			if (null != userMap.get("address")) {
				setText(cell, userMap.get("address").toString());
			} else {
				setText(cell, "");
			}

			cell = getCell(sheet, i + 1, 7);
			cell.setCellStyle(contentStyle);

			if (null != userMap.get("receiver")) {
				setText(cell, userMap.get("receiver").toString());
			} else {
				setText(cell, "");
			}

			cell = getCell(sheet, i + 1, 8);
			cell.setCellStyle(contentStyle);

			if (null != userMap.get("ismember")) {
				// 状态(1仅注册 2预备会员 3会员 4会员取消)
				String ismember = userMap.get("ismember").toString();
				if ("1".equals(ismember)) {
					setText(cell, "仅注册");
				} else if ("2".equals(ismember)) {
					setText(cell, "预备会员");
				} else if ("3".equals(ismember)) {
					setText(cell, "一元购会员");
				} else if ("4".equals(ismember)) {
					setText(cell, "会员取消");
				} else if ("5".equals(ismember)) {
					setText(cell, "普通会员");
				} else {
					setText(cell, "未知");
				}
			} else {
				setText(cell, "");
			}

			cell = getCell(sheet, i + 1, 9);
			cell.setCellStyle(contentStyle);

			if (null != userMap.get("orderNum")) {
				setText(cell, userMap.get("orderNum").toString());
			} else {
				setText(cell, "0");
			}

			cell = getCell(sheet, i + 1, 10);
			cell.setCellStyle(contentStyle);

			if (null != userMap.get("sumMoney")) {
				setText(cell, "￥" + userMap.get("sumMoney").toString());
			} else {
				setText(cell, "￥0");
			}

			cell = getCell(sheet, i + 1, 11);
			cell.setCellStyle(contentStyle);

			if (null != userMap.get("avgMoney")) {
				setText(cell, "￥" + userMap.get("avgMoney").toString());
			} else {
				setText(cell, "￥0");
			}

		}

	}

}
