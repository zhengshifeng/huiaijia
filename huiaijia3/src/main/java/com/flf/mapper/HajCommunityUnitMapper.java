package com.flf.mapper;

import com.base.dao.BaseDao;
import com.flf.entity.HajCommunityUnit;

import java.util.List;

public interface HajCommunityUnitMapper extends BaseDao {

	HajCommunityUnit getUnit(HajCommunityUnit communityUnit);

	List<HajCommunityUnit> getUnitList(Integer communityId);

	Integer getCountOfUnitUsed(Integer unitId);


}
