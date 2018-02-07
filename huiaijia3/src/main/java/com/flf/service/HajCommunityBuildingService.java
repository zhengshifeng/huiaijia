package com.flf.service;

import com.base.service.BaseService;
import com.flf.entity.HajCommunityBuilding;

import java.util.List;

/**
 *
 * <br>
 * <b>功能：</b>HajCommunityBuildingService<br>
 */
public interface HajCommunityBuildingService extends BaseService {

	List<HajCommunityBuilding> listPage(HajCommunityBuilding po);

	List<HajCommunityBuilding> getListByCommunityId(Integer communityId);

	List<HajCommunityBuilding> getList4api(Integer communityId);

	List<HajCommunityBuilding> getBuildList(Integer parentId);




}
