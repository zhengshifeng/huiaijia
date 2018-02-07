package com.flf.service;

import com.base.service.BaseService;
import com.flf.entity.HajActivity;

import java.util.List;
import java.util.Map;

/**
 * 
 * <br>
 * <b>功能：</b>HajActivityService<br>
 */
public interface HajActivityService extends BaseService {

	List<Map<String, Object>> listAllOrder(HajActivity activity);

	boolean insertActivity(HajActivity activity);

	boolean updateActivity(HajActivity activity);

	boolean updateActivityStatus(int activityId, int status);

	List<Map<String, Object>> getHajActivityCommotidyList(HajActivity activity);

	boolean deleteActivity(int activityId);

	HajActivity queryActivityByName(String activityName, String areaCode);

	List<Map<String, Object>> listActivityByType(HajActivity activity);

}
