package com.flf.mapper;

import java.util.List;
import java.util.Map;

import com.base.criteria.VillageManagerCriteria;
import com.base.dao.BaseDao;
import com.flf.entity.HajUserReport;

/**
 * 
 * <br>
 * <b>功能：</b>HajUserReportDao<br>
 */
public interface HajUserReportMapper extends BaseDao {

	List<Map<String, Object>> listPageReport(VillageManagerCriteria criteria);

	List<Map<String, Object>> listAllReport(VillageManagerCriteria criteria);

	HajUserReport checkTheUniqueness(HajUserReport hajUserReport);

	int updatePushStatus(String mobile);
}
