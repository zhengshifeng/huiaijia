package com.flf.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.criteria.VillageManagerCriteria;
import com.base.service.BaseServiceImpl;
import com.flf.entity.HajUserReport;
import com.flf.mapper.HajUserReportMapper;
import com.flf.service.HajUserReportService;

/**
 * 
 * <br>
 * <b>功能：</b>HajUserReportService<br>
 */
@Service("hajUserReportService")
public class HajUserReportServiceImpl extends BaseServiceImpl implements HajUserReportService {

	@Autowired
	private HajUserReportMapper dao;

	@Override
	public HajUserReportMapper getDao() {
		return dao;
	}

	@Override
	public List<Map<String, Object>> listPageReport(VillageManagerCriteria criteria) {

		return dao.listPageReport(criteria);
	}

	@Override
	public XSSFWorkbook exportUserReport(VillageManagerCriteria criteria) {
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
		List<Map<String, Object>> communityPersions = dao.listAllReport(criteria);
		Map<String, Object> vo = null;

		for (int i = 0, len = communityPersions.size(); i < len; i++) {
			vo = communityPersions.get(i);
			row = sheet.createRow(i + 1);
			row.createCell(0).setCellValue(vo.get("id").toString());
			row.createCell(1).setCellValue(vo.get("username").toString());
			row.createCell(2).setCellValue(vo.get("mobilePhone").toString());
			row.createCell(3).setCellValue(vo.get("area").toString());
			row.createCell(4).setCellValue(vo.get("villageCode").toString());
			row.createCell(5).setCellValue(vo.get("reportTime").toString());
			row.createCell(6).setCellValue((1 == Integer.parseInt(vo.get("pushStatus").toString())) ? "已推送" : "未推送");
		}

		return wkb;
	}

	private void setDefaultXSSFRow(XSSFRow row, XSSFSheet sheet) {
		// 初始化第一行标题
		row.createCell(0).setCellValue("序号");
		row.createCell(1).setCellValue("用户名");
		row.createCell(2).setCellValue("手机号码");
		row.createCell(3).setCellValue("区域");
		row.createCell(4).setCellValue("小区名称");
		row.createCell(5).setCellValue("上报时间");
		row.createCell(6).setCellValue("推送状态");
	}

	@Override
	public HajUserReport checkTheUniqueness(HajUserReport hajUserReport) {
		return dao.checkTheUniqueness(hajUserReport);
	}

	@Override
	public int updatePushStatus(String mobile) {
		return dao.updatePushStatus(mobile);
	}

}
