package com.flf.service.impl;

import com.base.service.BaseServiceImpl;
import com.flf.entity.HajPurchaseOrder;
import com.flf.mapper.HajOrderMapper;
import com.flf.mapper.HajPurchaseOrderMapper;
import com.flf.service.HajPurchaseOrderService;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 
 * <br>
 * <b>功能：</b>HajPurchaseOrderService<br>
 */
@Service("hajPurchaseOrderService")
public class HajPurchaseOrderServiceImpl extends BaseServiceImpl implements HajPurchaseOrderService {
	private final static Logger log = Logger.getLogger(HajPurchaseOrderServiceImpl.class);

	@Autowired
	private HajPurchaseOrderMapper dao;

	@Autowired
	private HajOrderMapper orderDao;

	@Override
	public HajPurchaseOrderMapper getDao() {
		return dao;
	}

	@Override
	public int savePurchaseOrder(Map<String, Object> map) {
		int result = 0;

		// 商品编号
		String commodityNo = (String) map.get("commodityNo");

		// 数量
		Object ob = map.get("number");
		int number = Integer.parseInt(ob.toString());

		// 交易单价
		BigDecimal price = (BigDecimal) map.get("costPrice");

		// 采购合计
		BigDecimal money = (BigDecimal) map.get("totalMoney");

		// 售出合计
		BigDecimal totalSaleMoney = (BigDecimal) map.get("totalSaleMoney");

		// 生成采购单号
		String purchaseNo = MakeOrderNum.makePurchaseOrderNum();

		log.info("日期：" + DateUtil.dateToString(new Date()) + ",生成采购单数据：商品编号：" + commodityNo + ",数量：" + number + ",单价："
				+ price + ",总计：" + money + ",采购单号：" + purchaseNo);
		HajPurchaseOrder purchese = new HajPurchaseOrder();
		purchese.setPurchaseNo(purchaseNo);
		purchese.setNumber(number);
		if (null != price) {
			purchese.setPrice(price);
		}
		purchese.setCreateTime(new Date());
		if (null != money) {
			purchese.setMoney(money);
		}
		if (null != commodityNo) {
			purchese.setCommodityNo(commodityNo);
		}
		if (null != totalSaleMoney) {
			purchese.setSaleMoney(totalSaleMoney);
		}
		try {
			purchese.setVersion(1);
			// 添加统计好的采购单
			int id = dao.add(purchese);
			System.out.println("插入采购单号返回的id: " + id);
			if (id > 0) {
				result = 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public void updateOrderHandle(String nowDate) {
		// 修改当天订单已生成采购单的状态
		orderDao.updateHandleStatus(nowDate);
	}

	@Override
	public List<Map<String, Object>> listPageOrder(HajPurchaseOrder purchase) {
		return dao.listPageOrder(purchase);
	}

	/**
	 * 统计采购单汇总
	 */
	@Override
	public Map<String, Object> queryTotalPurchase(HajPurchaseOrder purchase) {
		return dao.queryTotalPurchase(purchase);
	}

	@Override
	public List<Map<String, Object>> listAllpurchaseList(HajPurchaseOrder purchase) {
		return dao.listAllpurchaseList(purchase);
	}

	@Override
	public XSSFWorkbook excelHebingDetail(HajPurchaseOrder purchase) {

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
		// 导出合并采购单
		// ============================================================================
		List<Map<String, Object>> orderList = dao.excelHebingDetail(purchase);
		Map<String, Object> vo = null;

		// 每一行列编号
		int cellIndex = 0;
		for (int i = 0, len = orderList.size(); i < len; i++) {
			vo = orderList.get(i);
			row = sheet.createRow(i + 1);
			row.createCell(cellIndex++).setCellValue(getMapValue(vo.get("commodityNo")));
			row.createCell(cellIndex++).setCellValue(getMapValue(vo.get("sku")));
			row.createCell(cellIndex++).setCellValue(getMapValue(vo.get("commodityAttr")));
			row.createCell(cellIndex++).setCellValue(getMapValue(vo.get("bTypeName")));
			row.createCell(cellIndex++).setCellValue(getMapValue(vo.get("sTypeName")));
			row.createCell(cellIndex++).setCellValue(getMapValue(vo.get("name")));
			row.createCell(cellIndex++).setCellValue(getMapValue(vo.get("supplyChain")));
			row.createCell(cellIndex++).setCellValue(getMapValue(vo.get("number")));
			row.createCell(cellIndex++).setCellValue(getMapValue(vo.get("price")));
			row.createCell(cellIndex++).setCellValue(getMapValue(vo.get("money")));
			row.createCell(cellIndex++).setCellValue(getMapValue(vo.get("saleMoney")));
			row.createCell(cellIndex++).setCellValue(getMapValue(vo.get("weight")));
			row.createCell(cellIndex++).setCellValue(getMapValue(vo.get("praise")));
			cellIndex = 0;
		}
		return wkb;
	}

	private void setDefaultXSSFRow1(XSSFRow row, XSSFSheet sheet) {
		int cellIndex = 0;
		// 初始化第一行标题
		row.createCell(cellIndex++).setCellValue("商品编号");
		row.createCell(cellIndex++).setCellValue("物料编码");
		row.createCell(cellIndex++).setCellValue("属性");
		row.createCell(cellIndex++).setCellValue("大类");
		row.createCell(cellIndex++).setCellValue("小类");
		row.createCell(cellIndex++).setCellValue("商品名称");
		row.createCell(cellIndex++).setCellValue("供应商");
		row.createCell(cellIndex++).setCellValue("数量");
		row.createCell(cellIndex++).setCellValue("采购单价");
		row.createCell(cellIndex++).setCellValue("采购合计");
		row.createCell(cellIndex++).setCellValue("售出合计");
		row.createCell(cellIndex++).setCellValue("规格");
		row.createCell(cellIndex++).setCellValue("好评");
	}

	private String getMapValue(Object obj) {
		return (obj == null) ? Tools.EMPTY : obj.toString();
	}

	@Override
	public List<Map<String, Object>> listPageToDayPurchaseList(HajPurchaseOrder purchase) {
		return dao.listPageToDayPurchaseList(purchase);
	}

	@Override
	public Map<String, Object> queryTotalToDayPurchaseList(HajPurchaseOrder purchase) {
		return null;
	}

	@Override
	public XSSFWorkbook excelToDayPurchase(HajPurchaseOrder purchase) {

		// 创建HSSFWorkbook对象(excel的文档对象)
		XSSFWorkbook wkb = new XSSFWorkbook();

		// 建立新的sheet对象（excel的表单）
		XSSFSheet sheet = wkb.createSheet();
		sheet.setDefaultColumnWidth(12);
		sheet.setDefaultRowHeightInPoints(20);

		// 在sheet里创建第1行
		XSSFRow row = sheet.createRow(0);

		// 创建单元格并设置单元格内容
		this.setDefaultXSSFRow2(row, sheet);

		// ============================================================================
		// 导出合并采购单
		// ============================================================================
		List<Map<String, Object>> orderList = dao.excelToDayPurchase(purchase);
		Map<String, Object> vo = null;

		// 每一行列编号
		int cellIndex = 0;
		for (int i = 0, len = orderList.size(); i < len; i++) {
			vo = orderList.get(i);
			row = sheet.createRow(i + 1);
			row.createCell(cellIndex++).setCellValue(getMapValue(vo.get("commodityAttr")));
			row.createCell(cellIndex++).setCellValue(getMapValue(vo.get("bTypeName")));
			row.createCell(cellIndex++).setCellValue(getMapValue(vo.get("sTypeName")));
			row.createCell(cellIndex++).setCellValue(getMapValue(vo.get("name")));
			row.createCell(cellIndex++).setCellValue(getMapValue(vo.get("createTime")));
			row.createCell(cellIndex++).setCellValue(getMapValue(vo.get("packedWeight")));
			row.createCell(cellIndex++).setCellValue(getMapValue(vo.get("number")));
			row.createCell(cellIndex++).setCellValue(getMapValue(vo.get("packageWeight")));
			row.createCell(cellIndex++).setCellValue(getMapValue(vo.get("wastageRate") + "%"));
			row.createCell(cellIndex++).setCellValue(getMapValue(vo.get("weightofvegetable")));
			row.createCell(cellIndex++).setCellValue(getMapValue(vo.get("grossWeight")));
			row.createCell(cellIndex++).setCellValue(
					Double.parseDouble(getMapValue(vo.get("weightofvegetable")))
							- Double.parseDouble(getMapValue(vo.get("grossWeight"))));
			cellIndex = 0;
		}
		return wkb;
	}

	private void setDefaultXSSFRow2(XSSFRow row, XSSFSheet sheet) {
		int cellIndex = 0;
		// 初始化第一行标题
		row.createCell(cellIndex++).setCellValue("属性");
		row.createCell(cellIndex++).setCellValue("大类");
		row.createCell(cellIndex++).setCellValue("小类");
		row.createCell(cellIndex++).setCellValue("商品名称");
		row.createCell(cellIndex++).setCellValue("下单时间");
		row.createCell(cellIndex++).setCellValue("包装规格/Kg");
		row.createCell(cellIndex++).setCellValue("包装份数/份");
		row.createCell(cellIndex++).setCellValue("包装净重/Kg");
		row.createCell(cellIndex++).setCellValue("昨日损耗率");
		row.createCell(cellIndex++).setCellValue("毛菜重量/Kg");
		row.createCell(cellIndex++).setCellValue("毛菜库存/Kg");
		row.createCell(cellIndex++).setCellValue("毛菜采购量/Kg");
	}

	@Override
	public int savePurchaseOrderDateTime(Map<String, Object> map) {
		int result = 0;
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
		String purchaseNo = MakeOrderNum.makePurchaseOrderNum();
		// 售出合计
		BigDecimal totalSaleMoney = (BigDecimal) map.get("totalSaleMoney");

		log.info("日期：" + map.get("dateTime") + ",生成采购单数据：商品编号：" + commodityNo + ",数量：" + number + ",单价：" + price
				+ ",总计：" + money + ",采购单号：" + purchaseNo);
		HajPurchaseOrder purchese = new HajPurchaseOrder();
		purchese.setPurchaseNo(purchaseNo);
		purchese.setNumber(number);
		if (null != price) {
			purchese.setPrice(price);
		}
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");

		Date date;
		try {
			date = fmt.parse(map.get("dateTime").toString());
			purchese.setCreateTime(date);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		if (null != money) {
			purchese.setMoney(money);
		}
		if (null != commodityNo) {
			purchese.setCommodityNo(commodityNo);
		}
		if (null != totalSaleMoney) {
			purchese.setSaleMoney(totalSaleMoney);
		}
		try {
			// 查询当天是否已经统计过采购单
			purchese.setVersion(1);
			// 添加统计好的采购单
			int id = dao.add(purchese);
			System.out.println("插入采购单号返回的id: " + id);
			if (id > 0) {
				result = 1;
			}
			// }

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<HajPurchaseOrder> jdWmsPoOrder() {
		return dao.jdWmsPoOrder();
	}
}
