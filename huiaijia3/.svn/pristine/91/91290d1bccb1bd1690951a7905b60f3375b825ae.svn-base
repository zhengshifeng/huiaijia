package com.flf.service.impl;

import com.base.service.BaseServiceImpl;
import com.flf.entity.HajCommunityUnit;
import com.flf.mapper.HajCommunityUnitMapper;
import com.flf.service.HajCommunityUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "hajCommunityUnitService")
public class HajCommunityUnitServiceImpl extends BaseServiceImpl implements HajCommunityUnitService {

	@Autowired
	HajCommunityUnitMapper dao;

	@Override
	public HajCommunityUnitMapper getDao() {
		return dao;
	}

	@Override
	public HajCommunityUnit getUnit(HajCommunityUnit communityUnit) {
		return dao.getUnit(communityUnit);
	}

	@Override
	public List<HajCommunityUnit> getUnitList(Integer communityId) {
		return dao.getUnitList(communityId);
	}

	@Override
	public Integer getCountOfUnitUsed(Integer unitId) {
		return dao.getCountOfUnitUsed(unitId);
	}

}
