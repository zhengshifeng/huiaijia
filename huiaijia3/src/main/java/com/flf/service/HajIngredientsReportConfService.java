package com.flf.service;

import com.base.service.BaseService;
import com.flf.entity.HajIngredientsReportConf;
import com.flf.entity.HajIngredientsReportConfVo;

import java.util.HashMap;
import java.util.List;

/**
 *
 * <br>
 * <b>功能：</b>HajIngredientsReportConfService<br>
 */
public interface HajIngredientsReportConfService extends BaseService {

	List<HajIngredientsReportConf> listPage(HajIngredientsReportConf po);

	List<HashMap<String, Object>> list4app(HajIngredientsReportConf dto);

	/**
	 * 获取分类对象集合
	 * @param reportId
	 * @return
	 */
	List<HajIngredientsReportConfVo> getByReportId(Integer reportId);

	/**
	 * 获取分类对象
	 * @param confId
	 * @return
	 */
	HajIngredientsReportConfVo getByConfId (Integer confId);

}
