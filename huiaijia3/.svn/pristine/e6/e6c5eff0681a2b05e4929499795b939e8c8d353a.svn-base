package com.flf.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.service.BaseServiceImpl;
import com.flf.entity.HajWithdrawals;
import com.flf.mapper.HajWithdrawalsMapper;
import com.flf.service.HajWithdrawalsService;
import com.flf.util.Tools;

/**
 * 
 * <br>
 * <b>功能：</b>HajWithdrawalsService<br>
 */
@Service("hajWithdrawalsService")
public class HajWithdrawalsServiceImpl extends BaseServiceImpl implements HajWithdrawalsService {
	private final static Logger log = Logger.getLogger(HajWithdrawalsServiceImpl.class);

	@Autowired
	private HajWithdrawalsMapper dao;

	@Override
	public HajWithdrawalsMapper getDao() {
		return dao;
	}

	@Override
	public List<Map<String, Object>> listAllOrder(HajWithdrawals withdrawals) {
		return dao.listPageAllOrder(withdrawals);
	}

	@Override
	public int updateWithdrawals(HajWithdrawals withdrawals) {
		return dao.updateWithdrawals(withdrawals);
	}

	@Override
	public XSSFWorkbook exportWithdrawals(HajWithdrawals withdrawals) {

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

		List<Map<String, Object>> communityPersions = dao.exportWithdrawals(withdrawals);
		Map<String, Object> vo = null;
		String returnStatus = "";
		String tkStatus = "";
		for (int i = 0, len = communityPersions.size(); i < len; i++) {
			vo = communityPersions.get(i);
			row = sheet.createRow(i + 1);

			row.createCell(0).setCellValue(getMapValue(vo.get("id").toString()));
			row.createCell(1).setCellValue(getMapValue(vo.get("userId").toString()));
			row.createCell(2).setCellValue(getMapValue(vo.get("mobilePhone")));
			row.createCell(3).setCellValue(getMapValue(vo.get("money")));
			row.createCell(4).setCellValue(getMapValue(vo.get("createTime")));
			row.createCell(5).setCellValue(getMapValue(getStatus(vo.get("status").toString(), returnStatus)));
			row.createCell(6).setCellValue(getMapValue(getTKStatus(vo.get("isConfirm").toString(), tkStatus)));
			row.createCell(7).setCellValue(getMapValue(vo.get("operationTime")));
			row.createCell(8).setCellValue(getMapValue(vo.get("remark")));

		}

		return wkb;
	}

	private String getStatus(String status, String returnStatus) {
		switch (status) {
		case "1":
			returnStatus = "成功";
			break;
		case "0":
			returnStatus = "失败";
			break;
		}
		return returnStatus;
	}

	private String getTKStatus(String status, String returnStatus) {
		switch (status) {
		case "1":
			returnStatus = "已退款";
			break;
		case "0":
			returnStatus = "未退款";
			break;
		}
		return returnStatus;
	}

	private String getMapValue(Object obj) {
		return (obj == null) ? Tools.EMPTY : obj.toString();
	}

	private void setDefaultXSSFRow(XSSFRow row, XSSFSheet sheet) {
		// 初始化第一行标题
		row.createCell(0).setCellValue("序号");
		row.createCell(1).setCellValue("用户编号");
		row.createCell(2).setCellValue("手机号码");
		row.createCell(3).setCellValue("申请提现金额");
		row.createCell(4).setCellValue("申请时间");
		row.createCell(5).setCellValue("是否成功");
		row.createCell(6).setCellValue("是否退款");
		row.createCell(7).setCellValue("操作时间");
		row.createCell(8).setCellValue("备注");

	}

}
