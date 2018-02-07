package com.flf.service.impl;

import com.base.service.BaseServiceImpl;
import com.flf.entity.HajOrderPayment;
import com.flf.entity.HajOrderPaymentVo;
import com.flf.entity.HajRecharge;
import com.flf.entity.Page;
import com.flf.mapper.HajOrderPaymentMapper;
import com.flf.service.HajOrderPaymentService;
import com.flf.util.Tools;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * <br>
 * <b>功能：</b>HajOrderPaymentService<br>
 */
@Service("hajOrderPaymentService")
public class HajOrderPaymentServiceImpl extends BaseServiceImpl implements HajOrderPaymentService {
	private final static Logger log = Logger.getLogger(HajOrderPaymentServiceImpl.class);

	@Autowired
	private HajOrderPaymentMapper dao;

	@Override
	public HajOrderPaymentMapper getDao() {
		return dao;
	}

	@Override
	public List<HajOrderPaymentVo> list(HajOrderPaymentVo vo) {
		vo.setPage(vo.getPage() == null ? new Page() : vo.getPage());
		return dao.list(vo);
	}

	@Override
	public XSSFWorkbook export2Excel(HajOrderPaymentVo vo) {
		// 创建HSSFWorkbook对象(excel的文档对象)
		XSSFWorkbook wkb = new XSSFWorkbook();

		// 建立新的sheet对象（excel的表单）
		XSSFSheet sheet = wkb.createSheet();
		sheet.setDefaultColumnWidth(12);
		sheet.setDefaultRowHeightInPoints(20);

		// 在sheet里创建第1行
		XSSFRow row = sheet.createRow(0);

		// 创建单元格并设置单元格内容
		this.setDefaultXSSFRow(row);

		Page page = new Page();
		page.setShowCount(65535);
		vo.setPage(page);
		List<HajOrderPaymentVo> orderPaymentList = dao.list(vo);
		int cellIndex = 0;
		HajOrderPaymentVo dto;
		for (int i = 0; i < orderPaymentList.size(); i++) {
			dto = orderPaymentList.get(i);
			row = sheet.createRow(i + 1);

			row.createCell(cellIndex++).setCellValue(dto.getOrderNo());
			row.createCell(cellIndex++).setCellValue(dto.getMobilePhone());
			row.createCell(cellIndex++).setCellValue(this.getPaymentWay(dto));
			row.createCell(cellIndex++).setCellValue(dto.getTradeNo());
			row.createCell(cellIndex++).setCellValue((
					"1".equals(dto.getOrderStatus()) || "9".equals(dto.getOrderStatus()
					)) ? "待支付" : "已支付");
			row.createCell(cellIndex++).setCellValue(dto.getActualPayment());
			row.createCell(cellIndex++).setCellValue(dto.getPaymentTime());
			row.createCell(cellIndex++).setCellValue("6".equals(dto.getOrderStatus()) ? dto.getActualPayment() : dto.getTotalRefund());
			row.createCell(cellIndex++).setCellValue(dto.getRefundTime());
			cellIndex = 0;
		}

		return wkb;
	}

	private String getPaymentWay(HajOrderPaymentVo dto) {
		if (dto.getPaymentWay() == null)
			return "未知";

		switch (dto.getPaymentWay()) {
			case "0":
				return "余额";
			case "1":
				return "微信";
			case "2":
				return "支付宝";
			default:
				return "未知";
		}
	}

	private void setDefaultXSSFRow(XSSFRow row) {
		int cellIndex = 0;
		// 初始化第一行标题
		row.createCell(cellIndex++).setCellValue("订单号");
		row.createCell(cellIndex++).setCellValue("手机号码");
		row.createCell(cellIndex++).setCellValue("支付方式");
		row.createCell(cellIndex++).setCellValue("支付流水号");
		row.createCell(cellIndex++).setCellValue("支付状态");
		row.createCell(cellIndex++).setCellValue("订单实付金额");
		row.createCell(cellIndex++).setCellValue("支付时间");
		row.createCell(cellIndex++).setCellValue("退款金额");
		row.createCell(cellIndex++).setCellValue("退款时间");
	}

	@Override
	public int getCount(HajOrderPayment dto) {
		return dao.getCount(dto);
	}

	@Override
	public HajRecharge getHajRechargeByOrderNo(String orderNo) {
		return Tools.isNotEmpty(orderNo) ? dao.getHajRechargeByOrderNo(orderNo) : null;
	}

	@Override
	public BigDecimal calcOrderPayment(HajOrderPaymentVo vo) {
		List<HajOrderPaymentVo> list = dao.listOrderPaid(vo);

		BigDecimal total = BigDecimal.ZERO;
		for (HajOrderPaymentVo dto : list) {
			total = total.add(new BigDecimal(dto.getActualPayment()));
		}
		return total;
	}

	@Override
	public BigDecimal calcOrderRefund(HajOrderPaymentVo vo) {
		return dao.calcOrderRefund(vo);
	}
}
