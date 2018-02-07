package com.flf.service.impl;

import com.base.service.BaseServiceImpl;
import com.flf.entity.HajIngredientsReportConf;
import com.flf.entity.HajIngredientsReportConfVo;
import com.flf.mapper.HajIngredientsReportConfMapper;
import com.flf.service.HajIngredientsReportConfService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>HajIngredientsReportConfService<br>
 */
@Service("hajIngredientsReportConfService")
public class HajIngredientsReportConfServiceImpl extends BaseServiceImpl implements HajIngredientsReportConfService {
	private final static Logger log = Logger.getLogger(HajIngredientsReportConfServiceImpl.class);
	
	@Autowired
	private HajIngredientsReportConfMapper dao;
 
	@Override
	public HajIngredientsReportConfMapper getDao() {
		return dao;
	}

	@Override
	public List<HajIngredientsReportConf> listPage(HajIngredientsReportConf po) {
		return dao.listPage(po);
	}

	@Override
	public List<HashMap<String, Object>> list4app(HajIngredientsReportConf dto) {
		return dao.list4app(dto);
	}


	/**
	 * 获取分类对象集合
	 * @param reportId
	 * @return
	 */
	@Override
	public List<HajIngredientsReportConfVo> getByReportId(Integer reportId) {
		return dao.getByReportId(reportId);
	}

	/**
	 * 获取分类对象
	 * @param confId
	 * @return
	 */
	@Override
	public HajIngredientsReportConfVo getByConfId(Integer confId) {
		return dao.getByConfId(confId);
	}
}
