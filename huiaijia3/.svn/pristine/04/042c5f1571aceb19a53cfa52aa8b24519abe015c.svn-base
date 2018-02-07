package com.flf.mapper;

import com.base.criteria.Criteria;
import com.base.dao.BaseDao;
import com.flf.entity.HajActivity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * <br>
 * <b>功能：</b>HajActivityDao<br>
 */
public interface HajActivityMapper extends BaseDao {

	int querylistAllOrderCount(Criteria criteria);

	List<Map<String, Object>> listPageOrder(HajActivity activity);

	List<Map<String, Object>> getHajActivityCommotidyList(HajActivity activity);

	HajActivity queryActivityByName(@Param(value = "activityName") String activityName,
									@Param(value = "areaCode")String areaCode);

	List<Map<String, Object>> listActivityByType(HajActivity activity);

}
