package com.flf.mapper;

import com.base.criteria.CommodityCategoryCriteria;
import com.base.criteria.Criteria;
import com.base.dao.BaseDao;
import com.flf.entity.*;
import com.wms.entity.WmsInventory;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * <br>
 * <b>功能：</b>HajCommodityDao<br>
 */
public interface HajCommodityMapper extends BaseDao {

	List<Map<String, Object>> getCommodityByTypeId(Criteria criteria);

	List<Map<String, Object>> queryAllList();

	List<Map<String, Object>> queryAllListById(Integer id);

	List<Map<String, Object>> getCommodityByParentId(Integer parentId);

	int getCommodityCountByTypeId(Map<String, Object> condition);

	Map<String, Object> getCommodityByName(HajCommodity commodity);

	List<Map<String, Object>> getCommodityByActionId(int activityId);

	void updateCommodityActivityId(HajCommodity commodity);

	List<HashMap<String, Object>> getFamilyPreferences(Criteria criteria);

	List<HajCommodityVo> getNewProducts(@Param("communityId") Integer communityId,
											  @Param("areaCode") String areaCode);

	List<HajCommodityVo> getSearchCommodity(@Param("communityId") Integer communityId,
			@Param("areaCode") String areaCode);

	List<HajCommodity> getSearchRecord();

	void deleteActivity(Integer activityId);

	List<HajCommodityVo> getBySupplyChainId(Integer supplyChainid);

	List<HajCommodityVo> listPage(HajCommodityVo commodityVo);


	List<Map<String, Object>> getCommodityList(CommodityCategoryCriteria criteria);


	HajCommodityVo getCommodityById(Integer id);

	HajCommodity getCommodity(Integer id);

	void deleteCommotidyActivity(int activityId);

	List<HajCommodity> getAllComodityList();

	List<Map<String, Object>> getAllCommodityPrice();

	Integer getIdByCommodityNo(String commodityNo);

	void updatePrice(HajCommodity commodity);

	List<HajCommodityVo> getAllCommodity4Export(HajCommodityVo params);

	int updateUpCommoidtyList(String[] typeId);

	/**
	 * 获取团购商品
	 * 
	 * @author SevenWong<br>
	 * @date 2016年9月8日下午2:14:32
	 * @param commodityId
	 *            可根据商品ID查询单个商品，不为空或者不为0时生效
	 * @return
	 */
	List<HashMap<String, Object>> getTeamPurchasingCommodity(@Param("commodityId") Integer commodityId,
			@Param("communityId") Integer communityId, @Param("areaCode") String areaCode);

	/**
	 * 根据商品编号获取商品库存
	 * 
	 * @author SevenWong<br>
	 * @date 2016年9月29日下午1:47:22
	 * @return
	 */
	int getInventoryByNO(String commodityNo);

	/**
	 * 根据订单详情中的商品名及数量减少相应的库存
	 * 
	 * @author SevenWong<br>
	 * @date 2016年9月29日下午2:58:02
	 */
	int updateInventoryReduce(HajOrderDetails orderDetails);

	/**
	 * 根据订单详情中的商品名及数量增加相应的库存
	 * 
	 * @author SevenWong<br>
	 * @date 2016年9月29日下午4:23:22
	 * @param orderDetails
	 */
	void updateInventoryAdd(HajOrderDetails orderDetails);

	/**
	 * 下架相应的商品
	 * 
	 * @author SevenWong<br>
	 * @date 2016年9月29日下午4:23:34
	 */
	void updateSoldOut(String commodityNo);

	int getShelvesByNO(String commodityNo);

	List<HajCommodityVo> listPageInventory(HajCommodityVo commodityVo);

	/**
	 * 根据商品类别自动上架该类别下的商品（批量）
	 * 
	 * @author SevenWong<br>
	 * @date 2016年11月10日下午4:50:16
	 * @param typeIds
	 * @return
	 */
	int shangJia(String[] typeIds);

	HashMap<String, String> getCommoidtyByName(@Param("name") String name, @Param("areaCode") String areaCode);

	int updatePromotionAreaIdTo0(Integer[] commodityId);

	List<Map<String, Object>> getCommodityMustBuy(@Param("communityId") Integer communityId,
												  @Param("areaCode") String areaCode);

	List<Map<String, Object>> getCommoditysMustBuy(@Param("communityId") Integer communityId,
												  @Param("areaCode") String areaCode);

	List<HajCommodityVo> excelInventoryList(HajCommodityVo commodityVo);

	/**
	 * 限时抢购商品
	 * 
	 * @author SevenWong<br>
	 * @date 2016年10月24日下午5:02:45
	 * @return
	 */
	List<Map<String, Object>> getBuyLimitCommodity(@Param("communityId") Integer communityId,
			@Param("areaCode") String areaCode);

	/**
	 * 将商品从活动中移除
	 * 
	 * @author SevenWong<br>
	 * @date 2016年10月28日下午1:44:27
	 * @param activityName
	 * @return
	 */
	int updateCommodityClearActivity(@Param(value = "activityName") String activityName,
			@Param(value = "areaCode") String areaCode);

	/**
	 * 清空商品毛重
	 * 
	 * @author SevenWong<br>
	 * @date 2016年11月2日下午4:58:43
	 * @return
	 */
	int updateResetGrossWeight();

	HashMap<String, Object> getById(Integer commodityId);

	HajCommodity getByName(@Param(value = "name") String name, @Param(value = "areaCode") String areaCode);

	List<HashMap<String, Object>> getCommodityByPromotionAreaId(Map<String, Object> condition);

	List<HashMap<String, Object>> getCommodityByPromotionAreaIdOld(Map<String, Object> condition);

	List<HashMap<String, Object>> getCommodityListByPromotionAreaId(Criteria criteria);

	int updateShelvesById(HajCommodity commodity);

	int updateShelvesByManual(HajCommodity commodity);

	int updateSalesVolume();

	int getCountByType(HajCommodityType commodityType);

	void updateUpcommodityByNo(@Param(value = "commodityNo") String commodityNo,
			@Param(value = "inventory") int inventory);

	void updateCommodityUpByNo(String commodityNo);

	List<HashMap<String, Object>> getCommodityAttrList(@Param(value = "communityId") Integer communityId,
			@Param(value = "areaCode") String areaCode);

	HajCommodity getCommodityByNo(String commodityNo);

	List<String> getInvtSyncSkuList();

	int updateInventory(WmsInventory invt);

	int getCountByActivity(HajActivity activity);

	List<HashMap<String, Object>> listByActivity(HajActivity activity);

	List<String> listByTypeIds(List<Integer> typeIds);

	List<String> listByBrandIds(List<Integer> brandIds);

	List<HashMap<String, Object>> getByMark(HajCommodity commodity);

	List<HashMap<String, Object>> getByMarkPage(Criteria criteria);

	/**
	 * 根据fMaterialId判断是否存在
	 */
	int getTotalBycloudsId(Integer fMaterialId);

	/**
	 * 同步erp更新物料
	 * @param commodity
	 * @return
	 */
	int  updateCommodityByCloudsId(HajCommodity commodity);

	/**
	 * 同步所有商品的erp库存
	 */
	int updateCommodityInventory(List<Map<String,Object>> list);


	/**
	 * 获取三级类目下的商品列表
	 * @param criteria
	 * @return
	 */
	List<Map<String, Object>> getCategoryListByThreeId(Criteria criteria);


	/**
	 * 批量添加商品到板块中
	 * @param commodity
	 */
	void saveToCommodityPlate(HajCommodity commodity);


}
