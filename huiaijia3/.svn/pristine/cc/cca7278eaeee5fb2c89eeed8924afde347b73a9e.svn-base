package com.flf.service.impl;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.service.BaseServiceImpl;
import com.flf.entity.HajCommodityWastageRateVO;
import com.flf.mapper.HajCommodityWastageRateMapper;
import com.flf.service.HajCommodityWastageRateService;
import com.flf.util.Tools;

/**
 * 
 * <br>
 * <b>功能：</b>HajCommodityWastageRateService<br>
 */
@Service("hajCommodityWastageRateService")
public class HajCommodityWastageRateServiceImpl extends BaseServiceImpl implements HajCommodityWastageRateService {
	private final static Logger log = Logger.getLogger(HajCommodityWastageRateServiceImpl.class);

	@Autowired
	private HajCommodityWastageRateMapper dao;

	@Override
	public HajCommodityWastageRateMapper getDao() {
		return dao;
	}

	@Override
	public List<HashMap<String, Object>> listPage(HajCommodityWastageRateVO vo) {
		if (Tools.isNotEmpty(vo.getBeginDate()) && Tools.isNotEmpty(vo.getEndDate())) {
			return dao.listPageWithoutToday(vo);
		} else {
			return dao.listPage(vo);
		}
	}

	@Override
	public XSSFWorkbook export2excel(HajCommodityWastageRateVO vo) {
		// 创建HSSFWorkbook对象(excel的文档对象)
		XSSFWorkbook wkb = new XSSFWorkbook();

		// 建立新的sheet对象（excel的表单）
		XSSFSheet sheet = wkb.createSheet();
		sheet.setDefaultColumnWidth(10);
		sheet.setDefaultRowHeightInPoints(20);

		// 在sheet里创建第1行
		XSSFRow row = sheet.createRow(0);

		// 创建单元格并设置单元格内容
		this.setDefaultXSSFRow(row, sheet);

		// ============================================================================
		// 开始写入数据
		// ============================================================================
		List<HashMap<String, Object>> list = this.listPage(vo);
		HashMap<String, Object> map = null;

		// excel中列的索引
		int cellIndex = 0;
		for (int i = 0, len = list.size(); i < len; i++) {
			cellIndex = 0;
			map = list.get(i);
			row = sheet.createRow(i + 1);
			row.createCell(cellIndex++).setCellValue(getMapValue(map.get("createTime")));
			row.createCell(cellIndex++).setCellValue(getMapValue(map.get("commodityAttr")));
			row.createCell(cellIndex++).setCellValue(getMapValue(map.get("parentTypeName")));
			row.createCell(cellIndex++).setCellValue(getMapValue(map.get("typeName")));
			row.createCell(cellIndex++).setCellValue(getMapValue(map.get("commodityName")));
			row.createCell(cellIndex++).setCellValue(getMapValue(map.get("packedWeight")));
			row.createCell(cellIndex++).setCellValue(getMapValue(map.get("todaySales")));
			row.createCell(cellIndex++).setCellValue(getMapValue(map.get("grossWeight")));
			row.createCell(cellIndex++).setCellValue(getMapValue(map.get("outputWeight")));
			row.createCell(cellIndex++).setCellValue(getMapValue(map.get("wastageRate")) + "%");
		}

		return wkb;
	}

	/**
	 * 导出列表时设置excel标题
	 * 
	 * @author SevenWong<br>
	 * @date 2016年11月4日下午4:35:21
	 * @param row
	 * @param sheet
	 */
	private void setDefaultXSSFRow(XSSFRow row, XSSFSheet sheet) {
		int cellIndex = 0;

		// 初始化第一行标题
		row.createCell(cellIndex++).setCellValue("保存日期");
		row.createCell(cellIndex++).setCellValue("属性");
		row.createCell(cellIndex++).setCellValue("大类");
		row.createCell(cellIndex++).setCellValue("小类");
		row.createCell(cellIndex++).setCellValue("商品名");
		row.createCell(cellIndex++).setCellValue("包装规格/kg");
		row.createCell(cellIndex++).setCellValue("包装份数/份");
		row.createCell(cellIndex++).setCellValue("包装净重/kg");
		row.createCell(cellIndex++).setCellValue("出库重量/kg");
		row.createCell(cellIndex++).setCellValue("损耗率");
	}

	private String getMapValue(Object obj) {
		return (obj == null) ? Tools.EMPTY : obj.toString();
	}

}
