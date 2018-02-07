package com.flf.service.impl;

import com.base.service.BaseServiceImpl;
import com.flf.entity.HajCommunityBuilding;
import com.flf.mapper.HajCommunityBuildingMapper;
import com.flf.service.HajCommunityBuildingService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 
 * <br>
 * <b>功能：</b>HajCommunityBuildingService<br>
 */
@Service("hajCommunityBuildingService")
public class HajCommunityBuildingServiceImpl extends BaseServiceImpl implements HajCommunityBuildingService {
	private final static Logger log = Logger.getLogger(HajCommunityBuildingServiceImpl.class);
	
	@Autowired
	private HajCommunityBuildingMapper dao;
 
	@Override
	public HajCommunityBuildingMapper getDao() {
		return dao;
	}

	@Override
	public List<HajCommunityBuilding> listPage(HajCommunityBuilding po) {
		return dao.listPage(po);
	}

	@Override
	public List<HajCommunityBuilding> getListByCommunityId(Integer communityId) {
		return dao.getListByCommunityId(communityId);
	}

	@Override
	public List<HajCommunityBuilding> getList4api(Integer communityId) {
		return dao.getList4api(communityId);
	}

	@Override
	public List<HajCommunityBuilding> getBuildList(Integer parentId) {
		return dao.getBuildList(parentId);
	}


}
