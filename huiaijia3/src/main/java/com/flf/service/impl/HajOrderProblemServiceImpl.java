package com.flf.service.impl;

import com.base.service.BaseServiceImpl;
import com.flf.entity.HajOrderProblem;
import com.flf.mapper.HajOrderProblemMapper;
import com.flf.service.HajOrderProblemService;
import com.flf.util.Tools;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * 
 * <br>
 * <b>功能：</b>HajOrderProblemService<br>
 */
@Service("hajOrderProblemService")
public class HajOrderProblemServiceImpl extends BaseServiceImpl implements HajOrderProblemService {
	private final static Logger log = Logger.getLogger(HajOrderProblemServiceImpl.class);

	@Autowired
	private HajOrderProblemMapper dao;

	@Override
	public HajOrderProblemMapper getDao() {
		return dao;
	}

	@Override
	public List<HajOrderProblem> listPage(HajOrderProblem po) {
		return dao.listPage(po);
	}

	@Override
	public List<Map<String, Object>> listPageOrderProblem(HajOrderProblem vo) {
		if (vo.getDept() != null) {
			String[] ids = vo.getDept().split(",");
			vo.setDepts(ids);
		}
		return dao.listPageOrderProblem(vo);
	}

	@Override
	public XSSFWorkbook excelOrderProblem(HajOrderProblem orderVo) {

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

		CellStyle hlink_style = wkb.createCellStyle();
		Font hlink_font = wkb.createFont();
		hlink_font.setUnderline(Font.U_SINGLE);
		hlink_font.setColor(IndexedColors.BLUE.getIndex());
		hlink_style.setFont(hlink_font);

		// ============================================================================
		// 开始写入上报小区数据
		// ============================================================================
		if (orderVo.getDept() != null && !orderVo.getDept().equals("")) {
			String[] ids = orderVo.getDept().split(",");
			orderVo.setDepts(ids);
		}
		List<Map<String, Object>> communityPersions = dao.excelOrderProblem(orderVo);
		Map<String, Object> orderMap = null;
		int cellIndex = 0;
		String returnStatus = "";
		for (int i = 0, len = communityPersions.size(); i < len; i++, cellIndex = 0) {
			orderMap = communityPersions.get(i);

			row = sheet.createRow(i + 1);
			row.createCell(cellIndex++).setCellValue(i + 1);

			row.createCell(cellIndex++).setCellValue(getMapValue(orderMap.get("createTime")));
			row.createCell(cellIndex++).setCellValue(getMapValue(orderMap.get("problemTime")));
			row.createCell(cellIndex++).setCellValue(getMapValue(orderMap.get("mobilePhone")));
			row.createCell(cellIndex++).setCellValue(Tools.getCityNameByAreaCode(getMapValue(orderMap.get("areaCode"))));
			row.createCell(cellIndex++).setCellValue(getMapValue(orderMap.get("residential")));
			row.createCell(cellIndex++).setCellValue(getMapValue(orderMap.get("address")));
			row.createCell(cellIndex++).setCellValue(getMapValue(orderMap.get("orderNo")));
			row.createCell(cellIndex++).setCellValue(objStatus(getMapValue(orderMap.get("obj")), returnStatus));
			row.createCell(cellIndex++).setCellValue(getMapValue(orderMap.get("commodityNo")));
			row.createCell(cellIndex++).setCellValue(getMapValue(orderMap.get("number")));
			row.createCell(cellIndex++).setCellValue(methodStatus(getMapValue(orderMap.get("method")), returnStatus));
			row.createCell(cellIndex++).setCellValue(typeStatus(getMapValue(orderMap.get("type")), returnStatus));
			row.createCell(cellIndex++).setCellValue(getMapValue(orderMap.get("dept")));
			if (Tools.isNotEmpty(String.valueOf(orderMap.get("pic")))) {
				/*
				 * InputStream is =
				 * getInputStream(orderMap.get("pic").toString()); byte[] bytes
				 * = IOUtils.toByteArray(is);
				 * 
				 * // 增加图片到 Workbook int pictureIdx = wkb.addPicture(bytes,
				 * Workbook.PICTURE_TYPE_JPEG); is.close(); Drawing drawing =
				 * sheet.createDrawingPatriarch(); CreationHelper helper =
				 * wkb.getCreationHelper(); ClientAnchor anchor =
				 * helper.createClientAnchor(); anchor.setCol1(cellIndex++);
				 * anchor.setRow1(i + 1);
				 * 
				 * Picture pict = drawing.createPicture(anchor, pictureIdx);
				 * pict.resize();
				 */
				// row.createCell(cellIndex++).setCellValue(value);

				CreationHelper createHelper = wkb.getCreationHelper();
				// row.createCell(cellIndex++).setCellValue("URL Link");

				Cell cell = row.createCell(cellIndex++);
				// URL
				cell.setCellValue("查看凭证");

				Hyperlink link = createHelper.createHyperlink(Hyperlink.LINK_URL);
				link.setAddress(orderMap.get("pic").toString());
				cell.setHyperlink(link);
				cell.setCellStyle(hlink_style);

				// row.createCell(cellIndex++).setCellValue(getMapValue(orderMap.get("pic")));

			}

		}
		return wkb;

	}

	public static void main(String[] args) {
		Workbook wb = new XSSFWorkbook(); // or new HSSFWorkbook();
		CreationHelper createHelper = wb.getCreationHelper();

		CellStyle hlink_style = wb.createCellStyle();
		Font hlink_font = wb.createFont();
		hlink_font.setUnderline(Font.U_SINGLE);
		hlink_font.setColor(IndexedColors.BLUE.getIndex());
		hlink_style.setFont(hlink_font);

		Cell cell;
		Sheet sheet = wb.createSheet("Hyperlinks");
		// URL
		cell = sheet.createRow(0).createCell((short) 0);
		cell.setCellValue("URL Link");

		Hyperlink link = createHelper.createHyperlink(Hyperlink.LINK_URL);
		link.setAddress("http://poi.apache.org/");
		cell.setHyperlink(link);
		cell.setCellStyle(hlink_style);

		FileOutputStream out;
		try {
			out = new FileOutputStream("c://hyperinks.xlsx");
			wb.write(out);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 从服务器获得一个输入流(本例是指从服务器获得一个image输入流)
	public static InputStream getInputStream(String urlPath) {
		InputStream inputStream = null;
		HttpURLConnection httpURLConnection = null;

		try {
			URL url = new URL(urlPath);
			httpURLConnection = (HttpURLConnection) url.openConnection();
			// 设置网络连接超时时间
			httpURLConnection.setConnectTimeout(3000);
			// 设置应用程序要从网络连接读取数据
			httpURLConnection.setDoInput(true);

			httpURLConnection.setRequestMethod("GET");
			int responseCode = httpURLConnection.getResponseCode();
			if (responseCode == 200) {
				// 从服务器返回一个输入流
				inputStream = httpURLConnection.getInputStream();
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return inputStream;

	}

	// 问题类型(0: 其他; 1: 未送达; 100: 未知; 101: 少送; 102: 错送; 103: 重量不足; 104: 品质问题; 105:
	// 受外力破坏; 106: 过期; 107: 未冷藏; 108: 仓库缺货)
	private String typeStatus(String status, String returnStatus) {
		switch (status) {
		case "0":
			returnStatus = "其他";
			break;
		case "1":
			returnStatus = "未送达";
			break;
		case "100":
			returnStatus = "未知";
			break;
		case "101":
			returnStatus = "少送";
			break;
		case "102":
			returnStatus = "错送";
			break;
		case "103":
			returnStatus = "重量不足";
			break;
		case "104":
			returnStatus = "品质问题";
			break;
		case "105":
			returnStatus = "受外力破坏";
			break;
		case "106":
			returnStatus = "过期";
			break;
		case "107":
			returnStatus = "未冷藏";
			break;
		case "108":
			returnStatus = "仓库缺货";
			break;
		default:
			break;
		}
		return returnStatus;
	}

	// 处理办法(0: 补发; 1: 退款)
	private String methodStatus(String status, String returnStatus) {
		switch (status) {
		case "0":
			returnStatus = "补发";
			break;
		case "1":
			returnStatus = "退款";
			break;
		}
		return returnStatus;
	}

	// 处理对象(0: 整单; 1: 商品)
	private String objStatus(String status, String returnStatus) {
		switch (status) {
		case "0":
			returnStatus = "整单";
			break;
		case "1":
			returnStatus = "商品";
			break;
		}
		return returnStatus;
	}

	private String getMapValue(Object obj) {
		return (obj == null) ? Tools.EMPTY : obj.toString();
	}

	private void setDefaultXSSFRow(XSSFRow row, XSSFSheet sheet) {
		int cellIndex = 0;
		// 初始化第一行标题
		row.createCell(cellIndex++).setCellValue("序号");
		row.createCell(cellIndex++).setCellValue("下单日期");
		row.createCell(cellIndex++).setCellValue("处理时间");
		row.createCell(cellIndex++).setCellValue("手机号码");
		row.createCell(cellIndex++).setCellValue("城市");
		row.createCell(cellIndex++).setCellValue("小区");
		row.createCell(cellIndex++).setCellValue("收货地址");
		row.createCell(cellIndex++).setCellValue("订单号");
		row.createCell(cellIndex++).setCellValue("处理对象");
		row.createCell(cellIndex++).setCellValue("商品名称");
		row.createCell(cellIndex++).setCellValue("处理数量");
		row.createCell(cellIndex++).setCellValue("处理办法");
		row.createCell(cellIndex++).setCellValue("问题类型");
		row.createCell(cellIndex++).setCellValue("责任部门");
		row.createCell(cellIndex++).setCellValue("凭证");
	}

	@Override
	public void deleteByOrderNo(String orderNo) {
		dao.deleteByOrderNo(orderNo);
	}

	@Override
	public void deleteByRefundNo(String refundNo) {
		dao.deleteByRefundNo(refundNo);
	}

	@Override
	public List<HajOrderProblem> listByOrderNo(String orderNo) {
		return dao.listByOrderNo(orderNo);
	}
}
