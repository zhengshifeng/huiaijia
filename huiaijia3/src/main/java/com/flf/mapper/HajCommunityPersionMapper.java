package com.flf.mapper;

import com.base.criteria.VillageManagerCriteria;
import com.base.dao.BaseDao;
import com.flf.entity.HajCommunityPersion;
import com.flf.entity.HajCommunityPersionVo;
import com.flf.entity.HajCommunitySorterVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * <br>
 * <b>功能：</b>HajCommunityPersionDao<br>
 */
public interface HajCommunityPersionMapper extends BaseDao {

	List<Map<String, Object>> listPageVillage(VillageManagerCriteria criteria);

	Map<String, Object> exactSearchVillage(@Param("villageName") String villageName, @Param("areaCode") String areaCode);

	Map<String, Object> queryCommunityPersionMap(String userId);

	List<Map<String, Object>> queryAllList();

	HajCommunityPersion getCommunityByName(@Param("communityName") String residential,
			@Param("areaCode") String areaCode);

	List<Map<String, Object>> queryAllListById(int id);

	List<HajCommunityPersion> getAllList();

	Map<String, Object> getHajCommunityByName(String communityName);

	void updateAppointmentNum(Integer id);

	void updateByCommunityName(HajCommunityPersion communityPersion);

	void updateMembersNumberNum(Integer id);

	List<Map<String, Object>> villageCount(VillageManagerCriteria criteria);

	void updateMinByCommunityName(HajCommunityPersion communityPersion);

	List<HajCommunityPersionVo> getAllCommunity4Export(VillageManagerCriteria criteria);

	List<HajCommunityPersionVo> getAllCommunity4Export_new(VillageManagerCriteria criteria);

	int checkCommunityUniqueness(HajCommunityPersionVo vo);

	/**
	 * 根据小区名模糊查找或根据编号精确查找小区
	 * 
	 * @author SevenWong<br>
	 * @date 2016年8月19日上午10:55:58
	 * @param communityNameOrNo
	 * @return
	 */
	List<Map<String, Object>> getCommunityByNameOrNo(String communityNameOrNo);

	List<HajCommunityPersion> queryCommunityListById(String[] ids);

	void updateMemberCount(String villageId);

	Map<String, Object> villageMapCount(VillageManagerCriteria criteria);

	List<Map<String, Object>> listPageSorter(HajCommunitySorterVo vo);

	List<String> getCommunitiesBySorterId(Integer sorterId);

	/**
	 * 根据小区ID 查找城市区域编号
	 */
	String getCityCodeByVillageId(Integer villageId);

	Map<String, Object> queryCommunityPersion(HajCommunityPersion persion);

	void updateByCommunityId(HajCommunityPersion communityPersion);

	void updateMinMembersNumber(HajCommunityPersion communityPersion);

	void updateMinAppointmentNum(HajCommunityPersion communityPersion);

	List<HajCommunityPersion> getActiveCommunityByArea(String areaCode);

	Map<String, Object> villageMapCount1(VillageManagerCriteria criteria);

	Map<String, Object> villageMapCount2(VillageManagerCriteria criteria);

	Map<String, Object> villageMapCount3(VillageManagerCriteria criteria);

	/**
	 * 获取运营数据中心的统计数据
	 * @param areaCode
	 * @return
	 */
	Map<String, Object> operationsDataCenter(@Param("areaCode") String areaCode);

	List<HajCommunityPersion> getByUserIds(Integer[] userIds);

	List<HajCommunityPersion> getCommunityList();

	List<HajCommunityPersion> getCommunityListByCommunityName(String communityName);

}
