package com.flf.service.impl;

import com.base.criteria.Criteria;
import com.base.service.BaseServiceImpl;
import com.flf.entity.HajIngredientsReport;
import com.flf.entity.HajIngredientsReportVo;
import com.flf.mapper.HajIngredientsReportMapper;
import com.flf.service.HajIngredientsReportService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>HajIngredientsReportService<br>
 */
@Service("hajIngredientsReportService")
public class HajIngredientsReportServiceImpl extends BaseServiceImpl implements HajIngredientsReportService {
	private final static Logger log = Logger.getLogger(HajIngredientsReportServiceImpl.class);


	@Autowired
	private HajIngredientsReportMapper dao;
 
	@Override
	public HajIngredientsReportMapper getDao() {
		return dao;
	}

	@Override
	public List<HashMap<String, Object>> list4app(HajIngredientsReport dto) {
		return null;
	}

	@Override
	public List<HajIngredientsReport> listPage(HajIngredientsReport po) {

		//获取食材检测报告信息列表
		return dao.listPage(po);



	}


	/**
	 * 获取编辑页面回显数据
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@Override
	public HajIngredientsReportVo getByReportId(Integer id) throws Exception {
		return dao.getByReportId(id);
	}


	/**
	 * 返回最新的食材检测报告
	 * @param areaCode
	 * @return
	 */
	@Override
	public HajIngredientsReport getNewReport4app(String areaCode) {
		return dao.getNewReport4app(areaCode);
	}


	/**
	 * 分页获取食材加测记录
	 * @return
	 */
	@Override
	public List<HajIngredientsReport> reportListPage(Criteria criteria) {
		return dao.reportListPage(criteria);
	}
}
