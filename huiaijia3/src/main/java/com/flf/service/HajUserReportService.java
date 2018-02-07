package com.flf.service;

import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.base.criteria.VillageManagerCriteria;
import com.base.service.BaseService;
import com.flf.entity.HajUserReport;

/**
 * 
 * <br>
 * <b>功能：</b>HajUserReportService<br>
 */
public interface HajUserReportService extends BaseService {
	List<Map<String, Object>> listPageReport(VillageManagerCriteria criteria);

	XSSFWorkbook exportUserReport(VillageManagerCriteria criteria);

	/**
	 * 检查小区上报唯一性，如果该小区已上报，则无需重复上报
	 * 
	 * @author SevenWong<br>
	 * @date 2016年9月26日下午2:27:47
	 * @param hajUserReport
	 * @return
	 */
	HajUserReport checkTheUniqueness(HajUserReport hajUserReport);

	/**
	 * 修改用户上报短信推送状态为已推送
	 * 
	 * @author SevenWong<br>
	 * @date 2016年10月11日下午3:28:16
	 * @param mobile
	 * @return
	 */
	int updatePushStatus(String mobile);
}
