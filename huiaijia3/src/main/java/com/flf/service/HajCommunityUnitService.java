package com.flf.service;

import com.base.service.BaseService;
import com.flf.entity.HajCommunityUnit;

import java.util.List;

public interface HajCommunityUnitService extends BaseService {

	List<HajCommunityUnit> getUnitList(Integer communityId);

	HajCommunityUnit getUnit(HajCommunityUnit communityUnit);

	Integer getCountOfUnitUsed(Integer unitId);


}
