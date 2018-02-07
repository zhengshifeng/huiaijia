package com.flf.service;

import com.base.criteria.CommodityCategoryCriteria;
import com.base.criteria.Criteria;
import com.base.service.BaseService;
import com.flf.controller.erp.entity.ERPMaterielVo;
import com.flf.entity.*;
import com.wms.entity.WmsInventory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * <br>
 * <b>功能：</b>HajCommodityService<br>
 */
public interface HajCommodityService extends BaseService {

	/**
	 * APP获取商品列表接口1.0
	 * 
	 * @author SevenWong<br>
	 * @date 2016年8月15日上午11:05:43
	 * @param typeId
	 * @param criteria
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> getCommodityByTypeId(Integer typeId, Criteria criteria) throws Exception;

	/**
	 * APP获取商品列表接口2.0
	 * 
	 * @author SevenWong<br>
	 * @date 2016年8月15日上午11:06:18
	 * @param parentId
	 * @param typeId
	 * @param sort
	 * @param communityId
	 * @param criteria
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> getCommodityByTypeId(Integer parentId, Integer typeId, String sort, Integer communityId,
			Criteria criteria) throws Exception;

	/**
	 * APP获取商品列表接口 2.1
	 * 
	 * @author SevenWong<br>
	 * @date 2016年8月15日上午11:06:27
	 * @param commodityAttr
	 * @param bigTypeId
	 * @param typeId
	 * @param currentPage
	 * @param sort
	 * @param communityId
	 * @param criteria
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> getCommodityByTypeId(String commodityAttr, Integer bigTypeId, Integer typeId,
			Integer currentPage, String sort, Integer communityId, Criteria criteria) throws Exception;

	Map<String, Object> getCommodityByName(String commodityName, String cityCode);

	/**
	 * 重建商品索引
	 * 
	 * @author SevenWong<br>
	 * @date 2016年8月11日下午5:40:22
	 * @throws Exception
	 */
	void createCommodityIndex() throws Exception;

	void createCommodityIndex(int id) throws Exception;

	void deleteCommodityIndex(int id) throws Exception;

	List<Map<String, Object>> searchCommodity(String wd, Integer communityId, Criteria criteria, String areaCode)
			throws Exception;

	List<Map<String, Object>> getCommodityByParentId(Integer parentId) throws Exception;

	String batchImport(String filePath, boolean update, HttpServletRequest request) throws Exception;

	List<Map<String, Object>> getCommodityByActionId(int activityId);

	List<HashMap<String, Object>> getFamilyPreferences(Criteria criteria);
	List<HajCommodityVo> getNewProducts(Integer communityId, String areaCode);

	List<HajCommodityVo> getSearchCommodity(Integer communityId, String areaCode);

	List<com.flf.entity.HajCommodity> getSearchRecord();

	/**
	 * 根据supplyChainid获取HajCommodityVo到供应商详情页使用
	 * 
	 * @author SevenWong<br>
	 * @date 2016年9月8日下午4:29:58
	 * @param supplyChainid
	 * @return
	 * @throws Exception
	 */
	public List<HajCommodityVo> getBySupplyChainId(Integer supplyChainid) throws Exception;

	List<HajCommodityVo> listPage(HajCommodityVo commodityVo) throws Exception;

	//类目商品分页
	List<Map<String, Object>> getCommodityList(CommodityCategoryCriteria criteria) throws Exception;

	public HajCommodityVo getCommodityById(Integer id) throws Exception;

	HajCommodity getCommodity(Integer id);

	List<com.flf.entity.HajCommodity> queryAllList();

	/**
	 * 获取商品真实价格
	 *
	 * @author SevenWong<br>
	 * @date 2016年11月8日下午4:09:39
	 * @param names
	 * @param communityId
	 * @param areaCode
	 * @return
	 * @throws Exception
	 */
	List<HajCommodityPrice> getCommoidtyRealPrice(String names, Integer communityId, String areaCode) throws Exception;

	/**
	 * 购物车根据商品ID获取商品价格
	 * 
	 * @param ids
	 * @param communityId
	 * @param areaCode
	 * @return
	 * @throws Exception
	 */
	List<HajCommodityPrice> getCommodityPrice(String ids, Integer communityId, String areaCode) throws Exception;

	/**
	 * 导出全部商品
	 * 
	 * @author SevenWong<br>
	 * @param vo
	 *            筛选条件
	 * @date 2016年7月19日下午4:36:41
	 * @return
	 */
	XSSFWorkbook exportCommodity(HajCommodityVo vo);

	/**
	 * 清空商品相关的缓存
	 * 
	 * @author SevenWong<br>
	 * @date 2016年8月11日下午5:39:55
	 */
	void resetCommodityRedis();

	void updateUpCommoidtyList(String upCommoidty);

	/**
	 * 根据商品类别自动上架该类别下的商品
	 * 
	 * @author SevenWong<br>
	 * @date 2016年10月17日下午4:28:31
	 * @param typeId
	 */
	void shangJia(String typeId);

	/**
	 * 根据商品id上/下架商品
	 * 
	 * @param ids
	 * @param shelves
	 *            0,下架;1,上架
	 */
	void updateShelvesById(String[] ids, int shelves);

	/**
	 * 后台手动上下架商品
	 */
	void updateShelvesByManual(String[] ids, int shelves);

	/**
	 * 获取团购商品
	 * 
	 * @author SevenWong<br>
	 * @date 2016年9月8日下午2:16:25
	 * @param commodityId
	 *            可根据商品ID查询单个商品，不为空或者不为0时生效
	 * @return
	 */
	List<HashMap<String, Object>> getTeamPurchasingCommodity(Integer commodityId, Integer communityId, String areaCode);

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
	 * @date 2016年10月10日下午3:09:38
	 * @param order
	 * @return 正常情况返回true，告诉controller订单中所有的商品都能正常购买
	 * @throws Exception
	 *             订单中包含已下架的商品或者包含库存不足的商品时抛出异常
	 */
	boolean updateInventoryReduce(HajOrder order) throws Exception;

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

	/**
	 * 后台库存列表
	 * 
	 * @author SevenWong<br>
	 * @date 2016年10月11日下午2:00:56
	 * @param commodityVo
	 * @return
	 * @throws Exception
	 */
	List<HajCommodityVo> listPageInventory(HajCommodityVo commodityVo) throws Exception;

	HashMap<String, String> getCommoidtyByName(String name, String areaCode);

	int updatePromotionAreaIdTo0(Integer[] commodityId);

	/**
	 * 获取入店必买的商品
	 * 
	 * @author SevenWong<br>
	 * @date 2016年10月24日上午11:43:12
	 * @return
	 */
	List<Map<String, Object>> getCommodityMustBuy(Integer communityId, String areaCode);

	List<Map<String, Object>> getCommoditysMustBuy(Integer communityId, String areaCode);

	XSSFWorkbook excelInventoryList(HajCommodityVo commodityVo, boolean more);

	/**
	 * 限时抢购商品
	 * 
	 * @author SevenWong<br>
	 * @date 2016年10月24日下午5:03:05
	 * @return
	 */
	List<Map<String, Object>> getBuyLimitCommodity(Integer communityId, String areaCode);

	/**
	 * 将商品从活动中移除
	 * 
	 * @author SevenWong<br>
	 * @date 2016年10月28日下午1:44:27
	 * @param activityName
	 * @return
	 */
	int updateCommodityClearActivity(String activityName, String areaCode);

	/**
	 * 清空商品毛重
	 * 
	 * @author SevenWong<br>
	 * @date 2016年11月2日下午4:58:43
	 * @return
	 */
	int updateResetGrossWeight();

	/**
	 * 根据商品ID返回单个商品详情
	 * 
	 * @author SevenWong<br>
	 * @date 2016年11月3日上午11:16:31
	 * @param commodityId
	 * @return
	 */
	HashMap<String, Object> getById(Integer commodityId);

	List<Map<String, Object>> getCommodityList3_0(Criteria criteria);

	/**
	 * 根据商品名获取商品信息
	 * 
	 * @param name
	 *            商品名
	 * @return 返回商品详情
	 */
	HajCommodity getByName(String name, String areaCode);

	List<HashMap<String, Object>> getCommodityByPromotionAreaId(Map<String, Object> condition);

	List<HashMap<String, Object>> getCommodityByPromotionAreaIdOld(Map<String, Object> condition);

	/**
	 * 分页获取专区的商品列表 : 推荐专区商品列表
	 * @param criteria
	 * @return
	 */
	List<HashMap<String, Object>> getCommodityListByPromotionAreaId(Criteria criteria);

	/**
	 * 将昨日已完成订单的商品销量更新到商品表
	 * 
	 * @return
	 */
	int updateSalesVolume();

	/**
	 * 根据商品的分类信息判断该分类下是否有可购买的商品
	 *
	 * @param commodityType
	 * @return
	 */
	int getCountByType(HajCommodityType commodityType);

	void updateCommodityUpByNo(String commodityNo);

	List<HashMap<String, Object>> getCommodityAttrList(Integer communityId, String areaCode);

	HajCommodity getCommodityByNo(String commodityNo);

	/**
	 * 获取所有需要同步库存的商品
	 */
	List<String> getInvtSyncSkuList();

	/**
	 * 同步 inventoryList 中的商品库存
	 * @param inventoryList
	 * @return
	 */
	boolean wmsInventorySync(List<WmsInventory> inventoryList);

	/**
	 * 获取某种活动内可售商品的数量
	 * @param activity
	 * @return
	 */
	int getCountByActivity(HajActivity activity);

	List<HashMap<String, Object>> listByActivity(HajActivity activity);

	/**
	 * 根据商品标签获取商品列表
	 */
	List<HashMap<String, Object>> getByMark(HajCommodity commodity);

	List<HashMap<String, Object>> getByMarkPage(Criteria criteria);

	void save(HajCommodity commodity) throws Exception;

	/**
	 * 批量添加商品到商品分区中
	 * @param hajCommodityPromotionAreas   商品与商品专区表对象
	 */
	void saveToCommodityZone(List<HajCommodityPromotionArea> hajCommodityPromotionAreas );

	/**
	 * 批量添加商品到专题中
	 * @param hajSpecialTopic
	 */
	void saveToCommodityTopic(List<HajSpecialTopicCommodity> hajSpecialTopic);

	/**
	 * 批量添加商品到活动中
	 * @param
	 */
	void saveToCommodityActivity(Integer[] commodityId, Integer activityId);

	/**
	 * 批量添加商品到板块中
	 * @param commodityList
	 */
	void saveToCommodityPlate(List<HajCommodity> commodityList);

	/**
	 * 批量删除商品专区中的商品
	 * @param hajCommodityPromotionAreas 商品与商品专区表对象
	 */
	void deleteCommodityFromTheZone(List<HajCommodityPromotionArea> hajCommodityPromotionAreas);

	/**
	 * 批量移除专题中的商品
	 * @param hajSpecialTopicCommodity
	 */
	void deleteCommodityFromTheTopic(List<HajSpecialTopicCommodity> hajSpecialTopicCommodity);

	/**
	 * 批量移除活动中商品
	 * @param
	 */
	void deleteCommodityFromTheActivity(Integer[] commodityId, Integer activityId);

	/**
	 * 同步erp新增物料
	 * @param vo  物料参数
	 * @throws Exception
	 */
	boolean  saveCommodity(ERPMaterielVo vo)throws Exception;

	/**
	 * 同步erp更新物料
	 * @param vo  物料参数
	 * @throws Exception
	 */
	void  updateCommodity(ERPMaterielVo vo) throws Exception;


	/**
	 * 同步一个商品的库存
	 * @param commodity 商品
	 * @return
	 */
	void updateSyncInventoryOne(HajCommodity commodity);


	/**
	 * 同步所有商品的erp库存
	 */
	void updateSyncInventory();

}
