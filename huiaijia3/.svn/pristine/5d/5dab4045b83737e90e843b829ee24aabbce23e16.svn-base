package com.flf.service;

import com.base.criteria.Criteria;
import com.base.criteria.VillageManagerCriteria;
import com.base.service.BaseService;
import com.flf.entity.HajCommunityPersion;
import com.flf.entity.HajCommunitySorterVo;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.List;
import java.util.Map;

/**
 * 
 * <br>
 * <b>功能：</b>HajCommunityPersionService<br>
 */
public interface HajCommunityPersionService extends BaseService {

	List<Map<String, Object>> villageCount(VillageManagerCriteria criteria);

	List<Map<String, Object>> queryVillageManagerList(VillageManagerCriteria criteria);

	Map<String, Object> exactSearchVillage(String villageName, String areaCode);

	Map<String, Object> queryCommunityPersionMap(String communityName);

	List<HajCommunityPersion> getCommunitySeach(String keyword, Criteria criteria, String areaCode) throws Exception;

	void createHajCommunityPersionIndex() throws Exception;

	String batchImport(String filePath) throws Exception;

	String batchUpdate(String filePath) throws Exception;

	void createCommodityIndex(int id) throws Exception;

	void deleteCommunityPersionIndex(int id) throws Exception;

	List<HajCommunityPersion> getAllList();

	HajCommunityPersion getHajCommunityByName(String communityName, String areaCode);

	void updateAppointmentNum(Integer id);

	void updateByCommunityName(String communityName);

	void updateMembersNumberNum(Integer id);

	void updateMinByCommunityName(String communityName);

	int appointmentVillage(Integer userId, Integer villageId, Integer oldCommunityId);

	XSSFWorkbook exportCommunity(VillageManagerCriteria criteria);

	XSSFWorkbook exportCommunity_new(VillageManagerCriteria criteria);

	int appointmentVillage1(Integer userId, Integer villageId);

	List<HajCommunityPersion> queryCommunityListById(String[] split);

	/**
	 * 根据小区ID同步更新小区会员统计人数
	 * 
	 * @author SevenWong<br>
	 * @date 2016年9月18日下午3:59:22
	 * @param ids
	 * @return
	 */
	boolean updateMemberCount(Integer[] ids);

	Map<String, Object> villageMapCount(VillageManagerCriteria criteria);

	List<Map<String, Object>> listPageSorter(HajCommunitySorterVo vo);

	/**
	 * 根据分拣小组ID获取所有改分组下的小区名
	 */
	String getCommunitiesBySorterId(Integer sorterId);

	/**
	 * 根据小区ID 查找城市区域编号
	 */
	String getCityCodeByVillageId(Integer villageId);

	Map<String, Object> queryCommunityPersion(HajCommunityPersion persion);

	void updateByCommunityId(int communityId);

	void updateMinByCommunityId(int oldCommunityId, String memberStatus);

	List<HajCommunityPersion> getActiveCommunityByArea(String areaCode);

	Map<String, Object> villageMapCount1(VillageManagerCriteria criteria);

	Map<String, Object> villageMapCount2(VillageManagerCriteria criteria);

	Map<String, Object> villageMapCount3(VillageManagerCriteria criteria);

	/**
	 * 获取运营数据中心的统计数据
	 * @param areaCode
	 * @return
	 */
	Map<String, Object> operationsDataCenter(String areaCode);

	List<HajCommunityPersion> getByUserIds(Integer[] userIds);

	List<HajCommunityPersion> getCommunityList();
}
