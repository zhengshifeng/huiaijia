package com.flf.view;

import com.flf.util.Tools;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class RechargePackageRecordExcelView extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
									  HSSFWorkbook workbook, HttpServletRequest request,
									  HttpServletResponse response) throws Exception {
		Date date = new Date();
		String filename = Tools.date2Str(date, "yyyyMMddHHmmss");
		HSSFSheet sheet;
		HSSFCell cell;
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment;filename=" + filename + ".xls");
		sheet = workbook.createSheet("充值套餐记录");

		List<String> titles = (List<String>) model.get("titles");
		int len = titles.size();
		HSSFCellStyle headerStyle = workbook.createCellStyle(); //标题样式
		headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		headerStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		HSSFFont headerFont = workbook.createFont();    //标题字体
		headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		headerFont.setFontHeightInPoints((short) 11);
		headerStyle.setFont(headerFont);
		short width = 20, height = 25 * 20;
		sheet.setDefaultColumnWidth(width);
		for (int i = 0; i < len; i++) { //设置标题
			String title = titles.get(i);
			cell = getCell(sheet, 0, i);
			cell.setCellStyle(headerStyle);
			setText(cell, title);
		}
		sheet.getRow(0).setHeight(height);

		HSSFCellStyle contentStyle = workbook.createCellStyle(); //内容样式
		contentStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		List<Map<String, Object>> rechargePackageRecordList = (List<Map<String, Object>>) model.get("rechargePackageRecordList");
		int userCount = rechargePackageRecordList.size();
		for (int i = 0; i < userCount; i++) {
			Map<String, Object> rechargePackageMap = rechargePackageRecordList.get(i);
			cell = getCell(sheet, i + 1, 0);
			cell.setCellStyle(contentStyle);
			if (null != rechargePackageMap.get("userId")) {
				setText(cell, rechargePackageMap.get("userId").toString());
			} else {
				setText(cell, "");
			}

			cell = getCell(sheet, i + 1, 1);
			cell.setCellStyle(contentStyle);
			if (null != rechargePackageMap.get("phone")) {
				setText(cell, rechargePackageMap.get("phone").toString());
			} else {
				setText(cell, "");
			}

			cell = getCell(sheet, i + 1, 2);
			cell.setCellStyle(contentStyle);

			if (null != rechargePackageMap.get("name")) {
				setText(cell, rechargePackageMap.get("name").toString());
			} else {
				setText(cell, "");
			}

			cell = getCell(sheet, i + 1, 3);
			cell.setCellStyle(contentStyle);
			if (null != rechargePackageMap.get("purchaseAmount")) {
				setText(cell, rechargePackageMap.get("purchaseAmount").toString());
			} else {
				setText(cell, "");
			}

			cell = getCell(sheet, i + 1, 4);
			cell.setCellStyle(contentStyle);
			if (null != rechargePackageMap.get("donationAmount")) {
				setText(cell, rechargePackageMap.get("donationAmount").toString());
			} else {
				setText(cell, "");
			}

			cell = getCell(sheet, i + 1, 5);
			cell.setCellStyle(contentStyle);
			if (null != rechargePackageMap.get("accountAmount")) {
				setText(cell, rechargePackageMap.get("accountAmount").toString());
			} else {
				setText(cell, "");
			}

			cell = getCell(sheet, i + 1, 6);
			cell.setCellStyle(contentStyle);

			if (null != rechargePackageMap.get("payTime")) {
				setText(cell, rechargePackageMap.get("payTime").toString());
			} else {
				setText(cell, "");
			}

			cell = getCell(sheet, i + 1, 7);
			cell.setCellStyle(contentStyle);

			if (null != rechargePackageMap.get("payStatus") && rechargePackageMap.get("payStatus").toString().equals("0")) {
				setText(cell, "失败");
			}
            else if(null != rechargePackageMap.get("payStatus") && rechargePackageMap.get("payStatus").toString().equals("1")) {
				setText(cell, "成功");
			}
			else {
				setText(cell, "");
			}

			cell = getCell(sheet, i + 1, 8);
			cell.setCellStyle(contentStyle);

			if (null != rechargePackageMap.get("payWay")&& rechargePackageMap.get("payWay").toString().equals("2")) {
				setText(cell, "支付宝");
			}
			else if(null != rechargePackageMap.get("payWay") && rechargePackageMap.get("payWay").toString().equals("1")){
				setText(cell, "微信");
			}
			else {
				setText(cell, "");
			}
			cell = getCell(sheet, i + 1, 9);
			cell.setCellStyle(contentStyle);

			if (null != rechargePackageMap.get("payNumber")) {
				setText(cell, rechargePackageMap.get("payNumber").toString());
			} else {
				setText(cell, "");
			}

			cell = getCell(sheet, i + 1, 10);
			cell.setCellStyle(contentStyle);

			if (null != rechargePackageMap.get("refundAmount")) {
				setText(cell, rechargePackageMap.get("refundAmount").toString());
			} else {
				setText(cell, "");
			}


		}

	}

}
