package com.flf.util;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;

/**
 * Created by SevenWong on 2017/2/17 10:49.45
 */
public class ExcelUtil {

	/**
	 * 跟据cell中的值类型返回对应需要的正常数据，去掉公式及格式，默认返回""字符串
	 */
	public static String getCellValue(XSSFCell cell) {
		String cellValue = "";
		if (cell != null) {
			// 以下是判断数据的类型
			switch (cell.getCellType()) {
				case HSSFCell.CELL_TYPE_NUMERIC: // 数字
					DecimalFormat df = new DecimalFormat("#.##");
					cellValue = df.format(cell.getNumericCellValue());
					break;

				case HSSFCell.CELL_TYPE_STRING: // 字符串
					cellValue = cell.getStringCellValue();
					break;

				case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
					cellValue = cell.getBooleanCellValue() + "";
					break;

				case HSSFCell.CELL_TYPE_FORMULA: // 公式
					cellValue = cell.getCellFormula() + "";
					break;

				default:
					cellValue = "";
					break;
			}
		}
		return cellValue.trim();
	}

	/**
	 * 从工作流中输出excel文件到客户端
	 */
	public static void output(HttpServletResponse response, String filename, XSSFWorkbook wb) {
		OutputStream output = null;
		try {
			output = response.getOutputStream();
			response.reset();
			response.setHeader("Content-disposition", "attachment; filename=" + filename + ".xlsx");
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			wb.write(output);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
					wb.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
