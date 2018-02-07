package com.flf.service.impl;

import com.base.service.BaseServiceImpl;
import com.flf.entity.HajSortingOrder;
import com.flf.mapper.HajSortingOrderMapper;
import com.flf.service.HajSortingOrderService;
import com.flf.util.DateUtil;
import com.flf.util.MakeOrderNum;
import com.flf.util.Tools;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 
 * <br>
 * <b>功能：</b>HajSortingOrderService<br>
 */
@Service("hajSortingOrderService")
public class HajSortingOrderServiceImpl extends BaseServiceImpl implements HajSortingOrderService {
	private final static Logger log = Logger.getLogger(HajSortingOrderServiceImpl.class);

	@Autowired
	private HajSortingOrderMapper dao;

	@Override
	public HajSortingOrderMapper getDao() {
		return dao;
	}

	@Override
	public int saveSortingOrder(Map<String, Object> map) {

		int result = 0;
		// 小区
		String residential = (String) map.get("residential");
		// 商品编号
		String commodityNo = (String) map.get("commodityNo");
		// 数量
		Object ob = map.get("number");
		int number = Integer.parseInt(ob.toString());
		// 交易单价
		BigDecimal price = (BigDecimal) map.get("costPrice");
		// 总计
		BigDecimal money = (BigDecimal) map.get("totalMoney");
		// 生成采购单号
		String sortingNo = MakeOrderNum.makeSortingOrderNum();
		// 售出合计
		BigDecimal totalSaleMoney = (BigDecimal) map.get("totalSaleMoney");

		log.info("小区：" + residential + ",日期：" + DateUtil.dateToString(new Date()) + ",生成分拣单数据：商品编号：" + commodityNo
				+ ",数量：" + number + ",单价：" + price + ",总计：" + money + ",分拣单号：" + sortingNo);
		HajSortingOrder sorting = new HajSortingOrder();
		sorting.setSortingNo(sortingNo);
		sorting.setNumber(number);
		if (null != price) {
			sorting.setPrice(price);
		}
		sorting.setCreateTime(new Date());
		if (null != money) {
			sorting.setMoney(money);
		}
		if (null != commodityNo) {
			sorting.setCommodityNo(commodityNo);
		}
		if (null != totalSaleMoney) {
			sorting.setSaleMoney(totalSaleMoney);
		}
		if (null != residential) {
			sorting.setResidential(residential);
		}
		try {
			// 查询当天是否已经统计过采购单
			sorting.setVersion(1);
			// 添加统计好的采购单
			int id = dao.add(sorting);
			if (id > 0) {
				result = 1;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}

	@Override
	public List<Map<String, Object>> listPageOrder(HajSortingOrder sorting) {
		if (sorting.getResidentialList() != null && sorting.getResidentialList().length < 1) {
			sorting.setResidentialList(null);
		}
		return dao.listPageOrder(sorting);
	}

	@Override
	public Map<String, Object> queryTotalSorting(HajSortingOrder sorting) {
		if (sorting.getResidentialList() != null && sorting.getResidentialList().length < 1) {
			sorting.setResidentialList(null);
		}
		return dao.queryTotalSorting(sorting);
	}

	@Override
	public XSSFWorkbook exportHajSortingOrder(HajSortingOrder purchase) {
		// 创建HSSFWorkbook对象(excel的文档对象)
		XSSFWorkbook wkb = new XSSFWorkbook();

		// 建立新的sheet对象（excel的表单）
		XSSFSheet sheet = wkb.createSheet();
		sheet.setDefaultColumnWidth(12);
		sheet.setDefaultRowHeightInPoints(20);

		// 在sheet里创建第1行
		XSSFRow row = sheet.createRow(0);

		// 创建单元格并设置单元格内容
		this.setDefaultXSSFRow(row, sheet);

		// ============================================================================
		// 开始写入上报小区数据
		// ============================================================================
		if (purchase.getResidentialList() != null && purchase.getResidentialList().length < 1) {
			purchase.setResidentialList(null);
		}
		List<Map<String, Object>> communityPersions = dao.listAllHajSortingOrder(purchase);
		Map<String, Object> vo = null;

		int cellIndex = 0;
		for (int i = 0, len = communityPersions.size(); i < len; i++) {
			vo = communityPersions.get(i);
			row = sheet.createRow(i + 1);

			row.createCell(cellIndex++).setCellValue(getMapValue(vo.get("commodityNo").toString()));
			row.createCell(cellIndex++).setCellValue(getMapValue(vo.get("commodityAttr").toString()));
			row.createCell(cellIndex++).setCellValue(getMapValue(vo.get("bTypeName").toString()));
			row.createCell(cellIndex++).setCellValue(getMapValue(vo.get("sTypeName").toString()));
			row.createCell(cellIndex++).setCellValue(getMapValue(vo.get("name").toString()));
			if (null != vo.get("supplyChain")) {
				row.createCell(cellIndex++).setCellValue(vo.get("supplyChain").toString());
			} else {
				row.createCell(cellIndex++).setCellValue("");
			}
			row.createCell(cellIndex++).setCellValue(getMapValue(vo.get("residential").toString()));
			row.createCell(cellIndex++).setCellValue(getMapValue(vo.get("number").toString()));
			row.createCell(cellIndex++).setCellValue(getMapValue(vo.get("weight").toString()));
			if (null != vo.get("sku")) {
				row.createCell(cellIndex++).setCellValue(vo.get("sku").toString());
			} else {
				row.createCell(cellIndex++).setCellValue("");
			}
			row.createCell(cellIndex++).setCellValue(vo.get("storageNo").toString());
			cellIndex = 0;
		}

		return wkb;
	}

	private String getMapValue(Object obj) {
		return (obj == null) ? Tools.EMPTY : obj.toString();
	}

	private void setDefaultXSSFRow(XSSFRow row, XSSFSheet sheet) {
		int cellIndex = 0;
		// 初始化第一行标题
		row.createCell(cellIndex++).setCellValue("商品编号");
		row.createCell(cellIndex++).setCellValue("属性");
		row.createCell(cellIndex++).setCellValue("大类");
		row.createCell(cellIndex++).setCellValue("小类");
		row.createCell(cellIndex++).setCellValue("商品名称");
		row.createCell(cellIndex++).setCellValue("供应商");
		row.createCell(cellIndex++).setCellValue("小区");
		row.createCell(cellIndex++).setCellValue("数量");
		row.createCell(cellIndex++).setCellValue("规格");
		row.createCell(cellIndex++).setCellValue("物料编码");
		row.createCell(cellIndex++).setCellValue("货位号");
	}

	@Override
	public XSSFWorkbook exportSortOrder(HajSortingOrder purchase) {
		// 创建HSSFWorkbook对象(excel的文档对象)
		XSSFWorkbook wkb = new XSSFWorkbook();

		// 建立新的sheet对象（excel的表单）
		XSSFSheet sheet = wkb.createSheet();
		sheet.setDefaultColumnWidth(12);
		sheet.setDefaultRowHeightInPoints(20);

		// 在sheet里创建第1行
		XSSFRow row = sheet.createRow(0);

		// 创建单元格并设置单元格内容
		this.setDefaultXSSFRow(row, sheet);

		// ============================================================================
		// 开始写入上报小区数据
		// ============================================================================
		if (purchase.getResidentialList() != null && purchase.getResidentialList().length < 1) {
			purchase.setResidentialList(null);
		}
		List<Map<String, Object>> communityPersions = dao.ListAllexportSortOrder(purchase);
		Map<String, Object> vo = null;

		int cellIndex = 0;
		for (int i = 0, len = communityPersions.size(); i < len; i++) {
			vo = communityPersions.get(i);
			row = sheet.createRow(i + 1);

			row.createCell(cellIndex++).setCellValue(vo.get("commodityNo").toString());
			row.createCell(cellIndex++).setCellValue(vo.get("commodityAttr").toString());
			row.createCell(cellIndex++).setCellValue(vo.get("bTypeName").toString());
			row.createCell(cellIndex++).setCellValue(vo.get("sTypeName").toString());
			row.createCell(cellIndex++).setCellValue(vo.get("name").toString());
			if (null != vo.get("supplyChain")) {
				row.createCell(cellIndex++).setCellValue(vo.get("supplyChain").toString());
			} else {
				row.createCell(cellIndex++).setCellValue("");
			}
			row.createCell(cellIndex++).setCellValue(vo.get("residential").toString());
			row.createCell(cellIndex++).setCellValue(vo.get("number").toString());
			row.createCell(cellIndex++).setCellValue(vo.get("weight").toString());
			row.createCell(cellIndex++).setCellValue(vo.get("sku").toString());
			row.createCell(cellIndex++).setCellValue(vo.get("storageNo").toString());
			cellIndex = 0;
		}

		return wkb;
	}

	@Override
	public List<Map<String, Object>> listPageTodaySorting(HajSortingOrder sorting) {
		if (sorting.getResidentialList() != null && sorting.getResidentialList().length < 1) {
			sorting.setResidentialList(null);
		}
		return dao.listPageTodaySorting(sorting);
	}

	@Override
	public Map<String, Object> queryTotalTodaySorting(HajSortingOrder sorting) {
		return dao.queryTotalTodaySorting(sorting);
	}

	@Override
	public XSSFWorkbook exportTodaySortOrder(HajSortingOrder purchase) {

		// 创建HSSFWorkbook对象(excel的文档对象)
		XSSFWorkbook wkb = new XSSFWorkbook();

		// 建立新的sheet对象（excel的表单）
		XSSFSheet sheet = wkb.createSheet();
		sheet.setDefaultColumnWidth(12);
		sheet.setDefaultRowHeightInPoints(20);

		// 在sheet里创建第1行
		XSSFRow row = sheet.createRow(0);

		// 创建单元格并设置单元格内容
		this.setDefaultXSSFRow1(row, sheet);

		// ============================================================================
		// 开始写入上报小区数据
		// ============================================================================
		if (purchase.getResidentialList() != null && purchase.getResidentialList().length < 1) {
			purchase.setResidentialList(null);
		}
		List<Map<String, Object>> communityPersions = dao.ListAllexportTodaySortOrder(purchase);
		Map<String, Object> vo = null;

		int cellIndex = 0;
		for (int i = 0, len = communityPersions.size(); i < len; i++) {
			vo = communityPersions.get(i);
			row = sheet.createRow(i + 1);

			row.createCell(cellIndex++).setCellValue(vo.get("commodityNo").toString());
			row.createCell(cellIndex++).setCellValue(vo.get("commodityAttr").toString());
			row.createCell(cellIndex++).setCellValue(vo.get("bTypeName").toString());
			row.createCell(cellIndex++).setCellValue(vo.get("sTypeName").toString());
			row.createCell(cellIndex++).setCellValue(vo.get("name").toString());
			row.createCell(cellIndex++).setCellValue(vo.get("residential").toString());
			row.createCell(cellIndex++).setCellValue(vo.get("number").toString());
			row.createCell(cellIndex++).setCellValue(vo.get("weight").toString());
			row.createCell(cellIndex++).setCellValue(vo.get("sortingBatch").toString());
			row.createCell(cellIndex++).setCellValue(vo.get("sku").toString());
			row.createCell(cellIndex++).setCellValue(vo.get("storageNo").toString());
			cellIndex = 0;
		}
		return wkb;
	}

	private void setDefaultXSSFRow1(XSSFRow row, XSSFSheet sheet) {
		int cellIndex = 0;
		// 初始化第一行标题
		row.createCell(cellIndex++).setCellValue("商品编号");
		row.createCell(cellIndex++).setCellValue("属性");
		row.createCell(cellIndex++).setCellValue("大类");
		row.createCell(cellIndex++).setCellValue("小类");
		row.createCell(cellIndex++).setCellValue("商品名称");
		row.createCell(cellIndex++).setCellValue("小区");
		row.createCell(cellIndex++).setCellValue("数量");
		row.createCell(cellIndex++).setCellValue("规格");
		row.createCell(cellIndex++).setCellValue("分拣批次");
		row.createCell(cellIndex++).setCellValue("物料编码");
		row.createCell(cellIndex++).setCellValue("货位号");
	}
}
