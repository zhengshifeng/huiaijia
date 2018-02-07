package com.flf.service;

import com.base.criteria.Criteria;
import com.base.service.BaseService;
import com.flf.entity.HajIngredientsReport;
import com.flf.entity.HajIngredientsReportVo;

import java.util.HashMap;
import java.util.List;

/**
 *
 * <br>
 * <b>功能：</b>HajIngredientsReportService<br>
 */
public interface HajIngredientsReportService extends BaseService {

	List<HajIngredientsReport> listPage(HajIngredientsReport po);

	List<HashMap<String, Object>> list4app(HajIngredientsReport dto);

	//获取回显数据
	HajIngredientsReportVo getByReportId(Integer id) throws Exception;

	//返回最新的检测报告列表
	HajIngredientsReport getNewReport4app(String areaCode);

	//获取食材加测报告记录列表
	List<HajIngredientsReport> reportListPage(Criteria criteria);


}
