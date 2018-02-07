package com.flf.service.impl;

import com.base.criteria.CommodityCategoryCriteria;
import com.base.criteria.Criteria;
import com.base.service.BaseServiceImpl;
import com.flf.controller.erp.entity.ERPMaterielVo;
import com.flf.entity.*;
import com.flf.mapper.*;
import com.flf.service.*;
import com.flf.util.*;
import com.flf.util.wms.WmsSender;
import com.wms.entity.WmsInventory;
import org.apache.axis.client.Call;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.ServiceException;
import javax.xml.rpc.encoding.XMLType;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.flf.util.ExcelUtil.getCellValue;

/**
 * <br>
 * <b>功能：</b>HajCommodityService<br>
 *
 * @author SevenWong<br>
 * @date 2016年7月19日下午4:34:55
 */
@Service("hajCommodityService")
public class HajCommodityServiceImpl extends BaseServiceImpl implements HajCommodityService {
	private final static Logger log = Logger.getLogger(HajCommodityServiceImpl.class);

	private final static String COMMODITY_PREFIX = "SP";
	private final static String IP = Config.getConfigProperties("request.ip", "115.29.170.224");
	private final static String IMAGE_PREFIX = "http://" + IP + "/commodity_images/";
	private final static String IMAGE_SUFFIX_BIG = "_506x524.jpg";
	private final static String IMAGE_SUFFIX_SMALL = "_260x260.jpg";

	@Autowired
	private HajCommodityMapper dao;

	@Autowired
	private HajCommodityPromotionAreaMapper hcpaDao;

	@Override
	public HajCommodityMapper getDao() {
		return dao;
	}

	@Autowired
	private HajUserFamilyService hajUserFamilyService;

	@Autowired
	private HajCommodityTypeMapper typeMapper;

	@Autowired
	private RedisSpringProxy redisSpringProxy;

	@Autowired
	private HajCommodityFailureService hajCommodityFailureService;

	@Autowired(required = false)
	private HajActivityService hajActivityService;

	@Autowired(required = false)
	private HajSupplyChainService hajSupplyChainService;

	@Autowired(required = false)
	private HajAreasService areasService;

	@Autowired(required = false)
	private HajCouponCommodityService couponCommodityService;

	@Autowired(required = false)
	private HajCouponBrandService couponBrandService;

	@Autowired(required = false)
	private HajCouponTypeService couponTypeService;

	@Autowired
	private HajSpecialTopicCommodityMapper htcDao;


	/**
	 * 1.0
	 */
	@Override
	public List<Map<String, Object>> getCommodityByTypeId(Integer typeId, Criteria criteria) throws Exception {
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("typeId", typeId);
		condition.put("areaCode", Const.DEFAULT_AREA_CODE);
		criteria.setCondition(condition);
		// criteria.setTotalCount(dao.getCommodityCountByTypeId(condition));
		return dao.getCommodityByTypeId(criteria);
	}

	/**
	 * 2.0
	 */
	@Override
	public List<Map<String, Object>> getCommodityByTypeId(Integer parentId, Integer typeId, String sort,
														  Integer communityId, Criteria criteria) throws Exception {
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("parentId", parentId);
		condition.put("typeId", typeId);
		condition.put("sort", sort);
		condition.put("communityId", communityId);
		condition.put("areaCode", Const.DEFAULT_AREA_CODE);
		criteria.setCondition(condition);
		return dao.getCommodityByTypeId(criteria);
	}

	/**
	 * 2.1
	 */
	@Override
	public List<Map<String, Object>> getCommodityByTypeId(String commodityAttr, Integer bigTypeId, Integer typeId,
														  Integer currentPage, String sort, Integer communityId, Criteria criteria) throws Exception {
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("commodityAttr", commodityAttr);
		condition.put("parentId", bigTypeId);
		condition.put("typeId", typeId);
		condition.put("sort", sort);
		condition.put("communityId", communityId);
		criteria.setCondition(condition);
		return dao.getCommodityByTypeId(criteria);
	}

	@Override
	public Map<String, Object> getCommodityByName(String commodityName, String cityCode) {
		HajCommodity commodity = new HajCommodity();
		commodity.setName(commodityName);
		commodity.setAreaCode(cityCode);
		return dao.getCommodityByName(commodity);
	}

	/**
	 * 创建商品索引(所有)
	 *
	 * @throws Exception
	 */
	@Override
	public void createCommodityIndex() throws Exception {
		List<Map<String, Object>> commodityList = dao.queryAllList();
		Map<String, Object> objMap;
		String indexId = null;
		if (commodityList != null && commodityList.size() > 0) {
			/* 集合对象用于批量加入文件数据生成索引文件 * */
			String indexName = (String) redisSpringProxy.read("SystemConfiguration_indexName");
			String typeName = (String) redisSpringProxy.read("SystemConfiguration_typeName");
			objMap = commodityList.get(0);
			if (null != objMap && null != objMap.get("id") && objMap.size() > 0) {
				indexId = objMap.get("id").toString();
			}
			if (ElasticsearchUtil.get(indexName, typeName, indexId)) {
				// 先删除索引，再添加
				ElasticsearchUtil.deleteIndexByQuery(indexName, typeName);
			}

			ElasticsearchUtil.createIndex(commodityList, indexName, typeName, "name");
			log.info("创建商品索引(所有)成功！");
		}
	}

	/**
	 * 创建商品索引(单个)
	 *
	 * @throws Exception
	 */
	@Override
	public void createCommodityIndex(int id) throws Exception {
		List<Map<String, Object>> commodityList = dao.queryAllListById(id);
		if (commodityList != null && commodityList.size() > 0) {
			/** 集合对象用于批量加入文件数据生成索引文件 **/
			String indexName = (String) redisSpringProxy.read("SystemConfiguration_indexName");
			String typeName = (String) redisSpringProxy.read("SystemConfiguration_typeName");
			if (ElasticsearchUtil.get(indexName, typeName, String.valueOf(id))) {
				// 先删除索引，再添加
				ElasticsearchUtil.delete(indexName, typeName, String.valueOf(id));
			}

			ElasticsearchUtil.createIndex(commodityList, indexName, typeName, "name");
			log.info("索引id:" + id + "创建成功！");
		} else {
			// 查询不到就删除当前商品索引
			deleteCommodityIndex(id);
		}
	}

	/**
	 * 删除商品索引(单个)
	 *
	 * @throws Exception
	 */
	@Override
	public void deleteCommodityIndex(int id) throws Exception {
		/** 集合对象用于批量加入文件数据生成索引文件 **/
		String indexName = (String) redisSpringProxy.read("SystemConfiguration_indexName");
		String typeName = (String) redisSpringProxy.read("SystemConfiguration_typeName");
		if (ElasticsearchUtil.get(indexName, typeName, String.valueOf(id))) {
			// 先删除索引，再添加
			ElasticsearchUtil.delete(indexName, typeName, String.valueOf(id));
		}

		log.info("索引id:" + id + "删除成功！");
	}

	@Override
	public List<Map<String, Object>> searchCommodity(String wd, Integer communityId, Criteria criteria, String areaCode)
			throws Exception {
		List<Map<String, Object>> list = new ArrayList<>();

		// 检索开始时间
		long startTime = System.currentTimeMillis();

		log.info("商品搜索关键字：" + wd);

		// 高亮字段
		String[] highFields = new String[]{"name", "alias"};

		String indexName = (String) redisSpringProxy.read("SystemConfiguration_indexName");
		String typeName = (String) redisSpringProxy.read("SystemConfiguration_typeName");

		SearchResponse searchResponse = ElasticsearchUtil.searcher(indexName, typeName, criteria.getCurrentPage(),
				criteria.getPageSize(), wd, highFields, "alias");

		// 总记录数
		Long total = searchResponse.getHits().totalHits();

		log.info("【搜索】命中总数：" + total);

		// 该小区已屏蔽的商品列表
		List<Integer> shieldCommodityIds = new ArrayList<>();
		if (communityId != null && communityId > 0) {
			shieldCommodityIds = hajCommodityFailureService.getCommodityIdsByComnunityId(communityId);
		}

		// 限时抢购活动还未开始时，如果有用户结算此活动内的商品，则将促销价调为0，APP将自动按原价结算
		HajActivity buyLimit = hajActivityService.queryActivityByName("今日特价", areaCode);
		boolean buyLimitStart = isBuyLimitStart(buyLimit);

		SearchHits searchHits = searchResponse.getHits();
		SearchHit[] hits = searchHits.getHits();
		Map<String, Object> commodity;
		Integer commodityId;
		Integer activityId;
		String commodityAreaCode;
		for (SearchHit hit : hits) {
			commodity = new HashMap<>();
			commodityId = Integer.parseInt(String.valueOf(hit.getSource().get("id")));
			activityId = Integer.parseInt(String.valueOf(hit.getSource().get("activityId")));
			commodityAreaCode = String.valueOf(hit.getSource().get("areaCode"));

			// 过滤已屏蔽的商品或非用户所在地的商品
			if (!shieldCommodityIds.contains(commodityId) && areaCode.equals(commodityAreaCode)) {

				// =============================
				// 封装数据...
				// =============================
				commodity = commodityPackaging(hit, commodity);

				if (buyLimit != null && buyLimit.getId().equals(activityId) && !buyLimitStart) {
					log.info("今日特价未开始，商品[" + commodity.get("name") + "]将按原价返回	");
					commodity.put("promotionPrice", 0);
				}

				if (commodity.size() > 0) {
					list.add(commodity);
				}
			}
		}
		log.info("【搜索】【过滤已屏蔽商品】后总数：" + list.size());

		// 检索完成时间
		long endTime = System.currentTimeMillis();

		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(endTime - startTime);

		// 检索花费时间
		double spendTime = c.get(Calendar.MILLISECOND);
		log.info("检索完成，花费时间：" + spendTime);
		return list;
	}

	/**
	 * 为 searchCommodity()封装数据
	 *
	 * @return
	 * @author SevenWong<br>
	 * @date 2016年8月12日下午4:30:04
	 */
	private Map<String, Object> commodityPackaging(SearchHit hit, Map<String, Object> commodity) {
		commodity.put("id", hit.getSource().get("id"));
		commodity.put("name", hit.getSource().get("name"));
		commodity.put("typeId", hit.getSource().get("typeId"));
		commodity.put("typeName", hit.getSource().get("typeName"));
		commodity.put("recommend", hit.getSource().get("recommend"));
		commodity.put("description", hit.getSource().get("description"));
		commodity.put("imagePath", hit.getSource().get("imagePath"));
		commodity.put("intrapartum", hit.getSource().get("intrapartum"));
		commodity.put("activityId", hit.getSource().get("activityId"));
		commodity.put("smallPic", hit.getSource().get("smallPic"));
		commodity.put("originalPrice", hit.getSource().get("originalPrice"));
		commodity.put("promotionPrice", hit.getSource().get("promotionPrice"));
		commodity.put("marketPrice", hit.getSource().get("marketPrice"));
		commodity.put("producer", hit.getSource().get("producer"));
		commodity.put("weight", hit.getSource().get("weight"));
		commodity.put("activityName", hit.getSource().get("activityName"));
		commodity.put("bigTypeId", hit.getSource().get("bigTypeId"));
		commodity.put("alias", hit.getSource().get("alias"));
		commodity.put("acidDischarge", hit.getSource().get("acidDischarge"));
		commodity.put("praise", hit.getSource().get("praise"));
		commodity.put("mark", hit.getSource().get("mark"));
		commodity.put("label3", hit.getSource().get("label3"));
		commodity.put("mustBuy", hit.getSource().get("mustBuy"));
		commodity.put("stamp", hit.getSource().get("stamp"));
		commodity.put("discountWord", hit.getSource().get("discountWord"));
		commodity.put("promotionAreaId", hit.getSource().get("promotionAreaId"));
		commodity.put("typeDescription", hit.getSource().get("typeDescription"));
		commodity.put("commodityAttr", hit.getSource().get("commodityAttr"));
		commodity.put("areaCode", hit.getSource().get("areaCode"));
		commodity.put("shelves", hit.getSource().get("shelves"));

		return commodity;
	}

	/**
	 * 根据商品父级分类ID 查询所有商品信息
	 */
	@Override
	public List<Map<String, Object>> getCommodityByParentId(Integer parentId) throws Exception {
		return dao.getCommodityByParentId(parentId);
	}

	@Override
	public String batchImport(String filePath, boolean update, HttpServletRequest request) throws Exception {
		// 正常情况下返回success，异常时返回异常行的序列及提示信息
		StringBuilder result = new StringBuilder("");

		StringBuilder noTheseTypesTip = new StringBuilder();
		StringBuilder areaLimitTip = new StringBuilder();
		boolean dataError = false;

		String separator = ",";

		// 构造 XSSFWorkbook 对象，filePath 传入文件路径
		log.info("batchImport()批量导入商品的文件路径：" + filePath);
		XSSFWorkbook xwb = new XSSFWorkbook(filePath);

		// 读取第一张sheet表格内容
		XSSFSheet sheet = xwb.getSheetAt(0);
		xwb.close();// 记得关闭流

		XSSFRow row;
		String content;

		HajCommodity commodity;
		HajCommodityType thisCommodityType;
		Integer idInDB = null;
		String sequence = null;

		int cellIndex;

		String areaCode;
		User userSession = (User) request.getSession().getAttribute(Const.SESSION_USER);

		// 待添加的商品列表，如数据都校验通过，才能开始做插入操作
		List<HajCommodity> commodityInsertList = new ArrayList<>();

		List<HajAreas> cityList = areasService.listCity();

		// 循环输出表格中的内容
		// 变量i从真实数据行开始读取
		for (int i = (sheet.getFirstRowNum() + 1), rows = sheet.getPhysicalNumberOfRows(); i < rows; i++) {
			cellIndex = 0;
			row = sheet.getRow(i);
			if (row == null) {
				continue;
			}

			// 一次只能导入1000条数据
			if (rows > 1000) {
				break;
			}

			try {
				// 序号无需写入数据库
				sequence = getCellValue(row.getCell(cellIndex++));
				if (sequence.indexOf(".") > 0) {
					sequence = sequence.substring(0, sequence.indexOf("."));
				}

				// 序列号为空时，该行视为无效
				if (Tools.isEmpty(sequence)) {
					break;
				}

				// -------------------------------------------------
				// 开始封装数据，特殊字段添加校验
				// -------------------------------------------------

				// 校验用户对应的城市权限
				// 先根据用户输入的“城市名”查找对应的areaCode，如果找不到对应的城市，或者用户没有该城市下的权限，则给出对应的提示
				areaCode = this.getCodeByCity(cityList, getCellValue(row.getCell(cellIndex++)));
				if (Tools.isEmpty(areaCode) ||
						(Tools.isNotEmpty(userSession.getAreaCode())
								&& !Objects.equals(userSession.getAreaCode(), areaCode))) {
					areaLimitTip.append(sequence).append(separator);
					continue;
				}

				cellIndex++; // 跳过供应商编号列

				// 商品类别不存在时，该行视为无效
				thisCommodityType = typeMapper.isTypeExist(getCellValue(row.getCell(cellIndex++)),
						getCellValue(row.getCell(cellIndex++)), getCellValue(row.getCell(cellIndex++)));

				if (thisCommodityType == null) {// 类别输入有误
					noTheseTypesTip.append(sequence).append(separator);
					continue;
				}

				if (update) {
					cellIndex++; // 跳过 品牌编码 列
					idInDB = dao.getIdByCommodityNo(getCellValue(row.getCell(cellIndex++)));
					if (idInDB == null) {
						dataError = true;
						break;
					}
				}

				// 商品名为空时，该行视为无效
				content = getCellValue(row.getCell(cellIndex++));
				if (Tools.isEmpty(content)) {
					dataError = true;
					continue;
				}

				commodity = new HajCommodity();
				if (update) {
					commodity.setId(idInDB);
				}
				commodity.setName(content);
				commodity.setAreaCode(areaCode);
				commodity.setTypeId(thisCommodityType.getId());

				setModifyValues(commodity, row, update);

				commodityInsertList.add(commodity);
			} catch (Exception e) {
				result.append("未知异常");
				log.info(result.toString());
				break;
			}
		}
		result = batchImportResults(result, noTheseTypesTip, areaLimitTip,
				commodityInsertList, update, dataError);
		return result.toString();
	}

	private StringBuilder batchImportResults(StringBuilder result, StringBuilder noTheseTypesTip,
											 StringBuilder areaLimitTip, List<HajCommodity> commodityInsertList,
											 boolean update, boolean dataError) throws Exception {
		// 整理提示信息
		if (!dataError && Tools.isEmpty(noTheseTypesTip.toString()) && Tools.isEmpty(areaLimitTip.toString())
				&& Tools.isEmpty(result.toString())) {

			HajCommodity beforeUpdate;

			// 所有验证通过后，写入数据库
			for (HajCommodity commodity : commodityInsertList) {
				if (commodity != null) {
					try {
						if (update) {
							beforeUpdate = dao.queryById(commodity.getId());
							dao.updateBySelective(commodity);

							// 如果-品牌-被修改过，则需要清空该商品在优惠券商品表中的记录
							updateCouponCommodityByBrand(commodity, beforeUpdate);

							// 如果-分类-被修改过，则需要清空该商品在优惠券商品表中的记录
							updateCouponCommodityByType(commodity, beforeUpdate);

							// 同步修改至京东WMS
							if (!beforeUpdate.getName().equals(commodity.getName())) {
								WmsSender.updateGoodsInfo(commodity);
							}
						} else {
							setCommodityValues(commodity);
							dao.add(commodity);

							// 同步新增至京东WMS（这里不用做同步操作，因为批量新增商品时没有录入sku以后修改时wms会视为新增）
							//WmsSender.transportGoodsInfo(commodity);
						}
					} catch (DuplicateKeyException e) {
						result.append(commodity.getName());
						result.append("已存在，不要重复添加！\n\r");
						log.info(e.getCause());
						break;
					}
				}
			}
			if (Tools.isEmpty(result.toString())) {
				result = new StringBuilder("success");
			}
		} else {
			if (Tools.notEmpty(areaLimitTip.toString())) {
				result.append("序号[");
				result.append(areaLimitTip.substring(0, areaLimitTip.length() - 1));
				result.append("]您没有操作该城市内数据的权限.\n");
			}
			if (Tools.notEmpty(noTheseTypesTip.toString())) {
				result.append("序号[");
				result.append(noTheseTypesTip.substring(0, noTheseTypesTip.length() - 1));
				result.append("]后台无该分类！\n");
			}
			if (dataError) {
				result = new StringBuilder("表格数据异常");
			}
		}
		return result;
	}

	/**
	 * 批量导入商品时，如果商品名已存在，则更新数据为excel表中的最新数据
	 *
	 * @author SevenWong<br>
	 * @date 2016年8月4日下午4:38:31
	 */
	private void setModifyValues(HajCommodity commodity, XSSFRow row, boolean update) {
		String supplyChainNo = getCellValue(row.getCell(2));
		HajSupplyChain supplyChain = hajSupplyChainService.getByNo(supplyChainNo);
		commodity.setSupplyChain(supplyChain == null ? "0" : supplyChain.getId().toString());

		int cellIndex = 6;
		if (update) {
			commodity.setBrandId(Integer.valueOf(getCellValue(row.getCell(cellIndex++))));
			cellIndex++; // 跳过商品编号
		}
		cellIndex++; // 跳过商品名称

		commodity.setWeight(getCellValue(row.getCell(cellIndex++)));
		commodity.setProducer(getCellValue(row.getCell(cellIndex++)));
		commodity.setAlias(getCellValue(row.getCell(cellIndex++)));
		commodity.setDescription(getCellValue(row.getCell(cellIndex++)));
		commodity.setOrderClassification(getCellValue(row.getCell(cellIndex++)));
		commodity.setSortingBatch(update ? Integer.valueOf(getCellValue(row.getCell(cellIndex++))) : 1);
		if (update) {
			commodity.setSort(Integer.valueOf(getCellValue(row.getCell(cellIndex++))));
			commodity.setDiscountWord(getCellValue(row.getCell(cellIndex++)));
			cellIndex++;// 跳过商品“上下架”的列
			commodity.setInvalid("是".equals(getCellValue(row.getCell(cellIndex++))) ? 1 : 0);
			if (commodity.getInvalid() == 1) {
				// 商品作废后，自动下架。
				commodity.setShelves(0);
			}
			commodity.setSku(getCellValue(row.getCell(cellIndex++)));
			commodity.setStorageNo(getCellValue(row.getCell(cellIndex++)));
 		    commodity.setAttribute("当天生产".equals(getCellValue(row.getCell(21)))?0
					          :"当天采购".equals(getCellValue(row.getCell(21)))?1:"备货销售".equals(getCellValue(row.getCell(21)))?2:6);//默认给6 没有任何属性

			commodity.setSyncInvt("是".equals(getCellValue(row.getCell(22))) ? 1 : 0);    //添加是否同步库存
		} else {
			// 创建的商品的成本价、售价、参考价均设置为999，活动价设置为0
			commodity.setCostPrice(new BigDecimal(999));
			commodity.setOriginalPrice(new BigDecimal(999));
			commodity.setMarketPrice(new BigDecimal(999));
			commodity.setPromotionPrice(BigDecimal.ZERO);
		}
	}

	/**
	 * 批量导入商品（新增商品）时构造商品字段值
	 *
	 * @param commodity
	 * @author SevenWong<br>
	 * @date 2016年7月11日下午5:35:17
	 */
	private void setCommodityValues(HajCommodity commodity) throws NumberFormatException {
		commodity.setCommodityNo(COMMODITY_PREFIX + Tools.date2Str(new Date(), "yyMMddHHmmssSSS"));

		// 商品图片根据商品名命名
		commodity.setSmallPic(IMAGE_PREFIX + commodity.getCommodityNo() + IMAGE_SUFFIX_SMALL);
		commodity.setImagePath(IMAGE_PREFIX + commodity.getCommodityNo() + IMAGE_SUFFIX_BIG);

		// 其他字段设置默认值
		commodity.setClickNumber(0);
		commodity.setIsTop(0);
		commodity.setIsNew(0);
		commodity.setSearchCount(0);
		commodity.setActivityId(0);
		commodity.setMustBuy(0);
		commodity.setStamp(0);
		commodity.setPraise(0);
		commodity.setRecommend(0);
		commodity.setIntrapartum("0");
		commodity.setAcidDischarge("0");
		commodity.setRecommend(0);
		commodity.setIshot(0);
		commodity.setFamilyPreferences(0);
		commodity.setSalesVolume(0);
		commodity.setPraise(0);
		commodity.setInvalid(0);
		commodity.setShelves(0);
	}

	private String getCodeByCity(List<HajAreas> cityList, String city) {
		String areaCode = "";
		for (HajAreas areas : cityList) {
			if (areas.getName().equals(city)) {
				areaCode = areas.getCode();
				break;
			}
		}
		return areaCode;
	}

	/**
	 * 查询商品根据活动Id
	 */
	@Override
	public List<Map<String, Object>> getCommodityByActionId(int activityId) {
		return dao.getCommodityByActionId(activityId);
	}

	@Override
	public List<HajCommodityVo> listPage(HajCommodityVo commodityVo) throws Exception {
		return dao.listPage(commodityVo);
	}

	@Override
	public List<Map<String, Object>> getCommodityList(CommodityCategoryCriteria criteria) throws Exception {
		return dao.getCommodityList(criteria);
	}

	/**
	 * 新品推荐
	 */
	@Override
	public List<HashMap<String, Object>> getFamilyPreferences(Criteria criteria) {
		return dao.getFamilyPreferences(criteria);
	}

	/**
	 * 家人喜好推荐商品
	 */
	@Override
	public List<HajCommodityVo> getNewProducts(Integer communityId, String areaCode) {
		return dao.getNewProducts(communityId, areaCode);
	}

	/**
	 * 搜索推荐商品
	 */
	@Override
	public List<HajCommodityVo> getSearchCommodity(Integer communityId, String areaCode) {
		return dao.getSearchCommodity(communityId, areaCode);
	}

	/**
	 * 搜索历史记录
	 */
	@Override
	public List<HajCommodity> getSearchRecord() {
		return dao.getSearchRecord();
	}

	@Override
	public List<HajCommodityVo> getBySupplyChainId(Integer supplyChainid) throws Exception {
		return dao.getBySupplyChainId(supplyChainid);
	}

	@Override
	public HajCommodityVo getCommodityById(Integer id) throws Exception {
		return dao.getCommodityById(id);
	}

	@Override
	public HajCommodity getCommodity(Integer id) {
		return dao.getCommodity(id);
	}

	@Override
	public List<HajCommodity> queryAllList() {
		return dao.getAllComodityList();
	}

	@Override
	public void resetCommodityRedis() {
		redisSpringProxy.deletePattern("TypeHajCommodity*");
		redisSpringProxy.deletePattern("hajUserFamilyList*");
		redisSpringProxy.deletePattern("FamilyHajCommodity*");
		redisSpringProxy.deletePattern("SearchHajCommodity*");
		redisSpringProxy.deletePattern("HajAllCommodity*");
		redisSpringProxy.deletePattern("commodityPriceByName*");
		redisSpringProxy.deletePattern("hajCommodityType*");
		redisSpringProxy.deletePattern("CommodityTypeByPId*");
		redisSpringProxy.deletePattern("hajCommodityParentType*");
		redisSpringProxy.deletePattern("teamPurchasingCommodity_*");        //团购商品列表
		redisSpringProxy.deletePattern("promotionActivityCommodity*");
		redisSpringProxy.deletePattern("commodityMustBuyList*");
		redisSpringProxy.deletePattern("promotionAreaList*");
		redisSpringProxy.deletePattern("promotionAreaCommodityList_*");        //专区商品
		redisSpringProxy.deletePattern("commodityById_*");
		redisSpringProxy.deletePattern("getCommodityList3_0*");
		redisSpringProxy.deletePattern("specialTopicCommodityList_*");
		redisSpringProxy.deletePattern("specialTopicList*");
		redisSpringProxy.deletePattern("commodityAttrList_*");
		redisSpringProxy.deletePattern("bannerActivityList_*");
		redisSpringProxy.deletePattern("listByActivity_*");
		redisSpringProxy.deletePattern("commodityTypeByCategoryId_*");
		redisSpringProxy.deletePattern("categoryList_*");
		redisSpringProxy.deletePattern("getByMark_*");
		redisSpringProxy.deletePattern("commodityListByPromotionArea_*");    //推荐专区(专区商品)
		redisSpringProxy.deletePattern("promotionAreaList_*");                //首页专区列表(旧)
		redisSpringProxy.deletePattern("promotionAreaAllList_*");            //首页专区列表(新)
		redisSpringProxy.deletePattern("promotionActivityCommodity_*");        //今日特价
		redisSpringProxy.deletePattern("getByMarkPage_*");                    //角标商品(新品、爆品)
		redisSpringProxy.deletePattern("commodityMustBuyList_*");            //业主推荐板块
		redisSpringProxy.deletePattern("SearchHajCommodity_*");                //搜索推荐板块
		redisSpringProxy.deletePattern("FamilyHajCommodity_*");                //家人喜好(新品推荐)板块
		redisSpringProxy.deletePattern("commodityList_*");                    //获取三级类目下的商品缓存

		log.info("清空所有商品相关缓存");
	}

	@Override
	public List<HajCommodityPrice> getCommoidtyRealPrice(String names, Integer communityId, String areaCode)
			throws Exception {
		List<HajCommodityPrice> hajCommodityList = new ArrayList<>();

		List<Map<String, Object>> allCommodityList = hajUserFamilyService.getAllCommodityList();
		if (null != allCommodityList && allCommodityList.size() > 0) {

			/**
			 * 该小区已屏蔽的商品列表
			 */
			List<Integer> shieldingCids = new ArrayList<>();
			if (communityId != null && communityId > 0) {
				shieldingCids = hajCommodityFailureService.getCommodityIdsByComnunityId(communityId);
			}

			// 限时抢购活动还未开始时，如果有用户结算此活动内的商品，则将促销价调为0，APP将自动按原价结算
			HajActivity buyLimit = hajActivityService.queryActivityByName("今日特价", areaCode);
			boolean buyLimitStart = isBuyLimitStart(buyLimit);

			String[] commodities = names.split(",");
			Integer commodityId;
			Integer activityId;
			HajCommodityPrice commodityPrice;
			for (String name : commodities) {
				for (Map<String, Object> mapObject : allCommodityList) {
					if (mapObject.get("name").equals(name)
							&& (null != mapObject.get("areaCode") && areaCode.equals(mapObject.get("areaCode")))) {
						commodityId = Integer.parseInt(mapObject.get("id").toString());
						activityId = Integer.parseInt(mapObject.get("activityId").toString());

						commodityPrice = new HajCommodityPrice();
						commodityPrice.setName(mapObject.get("name").toString());
						commodityPrice.setOriginalPrice(mapObject.get("originalPrice").toString());

						if (buyLimit != null && buyLimit.getId().equals(activityId) && !buyLimitStart) {
							commodityPrice.setPromotionPrice("0");
						} else {
							commodityPrice.setPromotionPrice(mapObject.get("promotionPrice").toString());
						}

						// 已屏蔽的商品设为下架
						if (shieldingCids.contains(commodityId)) {
							commodityPrice.setShelves("0");
						} else {
							commodityPrice.setShelves(mapObject.get("shelves").toString());
						}
						if (null != mapObject.get("areaCode")) {
							commodityPrice.setAreaCode(mapObject.get("areaCode").toString());
						}

						hajCommodityList.add(commodityPrice);
					}
				}
			}

		}
		return hajCommodityList;
	}

	@Override
	public List<HajCommodityPrice> getCommodityPrice(String ids, Integer communityId, String areaCode) throws Exception {
		List<HajCommodityPrice> hajCommodityList = new ArrayList<>();

		List<Map<String, Object>> allCommodityList = hajUserFamilyService.getAllCommodityList();
		if (null != allCommodityList && allCommodityList.size() > 0) {
			/**
			 * 该小区已屏蔽的商品列表
			 */
			List<Integer> shieldingCids = new ArrayList<>();
			if (communityId != null && communityId > 0) {
				shieldingCids = hajCommodityFailureService.getCommodityIdsByComnunityId(communityId);
			}

			// 限时抢购活动还未开始时，如果有用户结算此活动内的商品，则将促销价调为0，APP将自动按原价结算
			HajActivity buyLimit = hajActivityService.queryActivityByName("今日特价", areaCode);
			boolean buyLimitStart = isBuyLimitStart(buyLimit);

			String[] cids = ids.split(",");
			Integer activityId;
			Integer commodityId;
			HajCommodityPrice commodityPrice;
			for (String id : cids) {
				commodityId = Integer.valueOf(id);
				for (Map<String, Object> mapObject : allCommodityList) {
					if (String.valueOf(mapObject.get("id")).equals(id)
							&& (null != mapObject.get("areaCode") && areaCode.equals(mapObject.get("areaCode")))) {
						activityId = Integer.parseInt(mapObject.get("activityId").toString());

						commodityPrice = new HajCommodityPrice();
						commodityPrice.setId(commodityId);
						commodityPrice.setName(mapObject.get("name").toString());
						commodityPrice.setOriginalPrice(mapObject.get("originalPrice").toString());
						commodityPrice.setActivityId(Integer.valueOf(mapObject.get("activityId").toString()));

						if (buyLimit != null && buyLimit.getId().equals(activityId) && !buyLimitStart) {
							commodityPrice.setPromotionPrice("0");
						} else {
							commodityPrice.setPromotionPrice(mapObject.get("promotionPrice").toString());
						}

						// 已屏蔽的商品设为下架
						if (shieldingCids.contains(commodityId)) {
							commodityPrice.setShelves("0");
						} else {
							commodityPrice.setShelves(mapObject.get("shelves").toString());
						}
						if (null != mapObject.get("areaCode")) {
							commodityPrice.setAreaCode(mapObject.get("areaCode").toString());
						}

						hajCommodityList.add(commodityPrice);
					}
				}
			}
		}
		return hajCommodityList;
	}

	private boolean isBuyLimitStart(HajActivity buyLimit) throws ParseException {
		boolean buyLimitStart = false;
		if (buyLimit != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date beginTime = sdf.parse(buyLimit.getBeginTime());
			Date endTime = sdf.parse(buyLimit.getEndTime());
			Date now = new Date();
			if (now.getTime() >= beginTime.getTime() && now.getTime() < endTime.getTime()) {
				buyLimitStart = true;
			}
		}
		return buyLimitStart;
	}

	@Override
	public XSSFWorkbook exportCommodity(HajCommodityVo params) {
		// 创建HSSFWorkbook对象(excel的文档对象)
		XSSFWorkbook wkb = new XSSFWorkbook();

		// 建立新的sheet对象（excel的表单）
		XSSFSheet sheet = wkb.createSheet();
		sheet.setDefaultColumnWidth(10);
		sheet.setDefaultRowHeightInPoints(20);

		// 在sheet里创建第1行
		XSSFRow row = sheet.createRow(0);

		// 创建单元格并设置单元格内容
		this.setDefaultXSSFRow(row);

		// ============================================================================
		// 开始写入商品数据
		// ============================================================================
		List<HajCommodityVo> commodityList = dao.getAllCommodity4Export(params);
		HajCommodityVo vo;

		// excel中列的索引
		int cellIndex;
		for (int i = 0, len = commodityList.size(); i < len; i++) {
			cellIndex = 0;
			vo = commodityList.get(i);
			row = sheet.createRow(i + 1);
			row.createCell(cellIndex++).setCellValue(i + 1);
			row.createCell(cellIndex++).setCellValue(vo.getCity());
			row.createCell(cellIndex++).setCellValue(vo.getSupplyNo() == null ? "" : vo.getSupplyNo());
			row.createCell(cellIndex++).setCellValue(vo.getCommodityAttr() == null ? "" : vo.getCommodityAttr());
			row.createCell(cellIndex++).setCellValue(vo.getParentTypeName() == null ? "" : vo.getParentTypeName());
			row.createCell(cellIndex++).setCellValue(vo.getTypeName() == null ? "" : vo.getTypeName());
			row.createCell(cellIndex++).setCellValue(vo.getBrandId() == null ? 0 : vo.getBrandId());
			row.createCell(cellIndex++).setCellValue(vo.getCommodityNo() == null ? "" : vo.getCommodityNo());
			row.createCell(cellIndex++).setCellValue(vo.getName() == null ? "" : vo.getName());
			row.createCell(cellIndex++).setCellValue(vo.getWeight() == null ? "" : vo.getWeight());
			row.createCell(cellIndex++).setCellValue(vo.getProducer() == null ? "" : vo.getProducer());
			row.createCell(cellIndex++).setCellValue(vo.getAlias() == null ? "" : vo.getAlias());
			row.createCell(cellIndex++).setCellValue(vo.getDescription() == null ? "" : vo.getDescription());
			row.createCell(cellIndex++).setCellValue(vo.getOrderClassification());
			row.createCell(cellIndex++).setCellValue(vo.getSortingBatch() == null ? 0 : vo.getSortingBatch());
			row.createCell(cellIndex++).setCellValue(vo.getSort() == null ? 0 : vo.getSort());
			row.createCell(cellIndex++).setCellValue(vo.getDiscountWord() == null ? "" : vo.getDiscountWord());
			row.createCell(cellIndex++).setCellValue((Objects.equals(vo.getShelves(), 1)) ? "上架" : "下架");
			row.createCell(cellIndex++).setCellValue((Objects.equals(vo.getInvalid(), 1)) ? "是" : "否");
			row.createCell(cellIndex++).setCellValue(vo.getSku() == null ? "" : vo.getSku());
			row.createCell(cellIndex++).setCellValue(vo.getStorageNo() == null ? "" : vo.getStorageNo());
			row.createCell(cellIndex++).setCellValue((Objects.equals(vo.getAttribute(), 0))
					? "当天生产" : (Objects.equals(vo.getAttribute(), 1))
					? "当天采购" : (Objects.equals(vo.getAttribute(), 2))?"备货销售":"");
			row.createCell(cellIndex++).setCellValue((Objects.equals(vo.getSyncInvt(), 1)) ? "是" : "否");
			row.createCell(cellIndex++).setCellValue(vo.getCloudsId() == null ? 0 : vo.getCloudsId());
		}

		return wkb;
	}

	/**
	 * 导出商品列表时设置excel前两行数据
	 *
	 * @param row
	 * @author SevenWong<br>
	 * @date 2016年7月19日下午4:35:39
	 */
	private void setDefaultXSSFRow(XSSFRow row) {
		int cellIndex = 0;

		// 初始化第一行标题
		row.createCell(cellIndex++).setCellValue("上传序号");
		row.createCell(cellIndex++).setCellValue("供应城市");
		row.createCell(cellIndex++).setCellValue("供应商编号");
		row.createCell(cellIndex++).setCellValue("属性");
		row.createCell(cellIndex++).setCellValue("大类");
		row.createCell(cellIndex++).setCellValue("小类");
		row.createCell(cellIndex++).setCellValue("品牌编码");
		row.createCell(cellIndex++).setCellValue("商品编码");
		row.createCell(cellIndex++).setCellValue("商品名称");
		row.createCell(cellIndex++).setCellValue("规格");
		row.createCell(cellIndex++).setCellValue("产地");
		row.createCell(cellIndex++).setCellValue("商品别名");
		row.createCell(cellIndex++).setCellValue("商品简介");
		row.createCell(cellIndex++).setCellValue("订单详情所在列");
		row.createCell(cellIndex++).setCellValue("分拣批次");
		row.createCell(cellIndex++).setCellValue("排序值");
		row.createCell(cellIndex++).setCellValue("折扣标签");
		row.createCell(cellIndex++).setCellValue("状态");
		row.createCell(cellIndex++).setCellValue("是否作废");
		row.createCell(cellIndex++).setCellValue("商品物料号");
		row.createCell(cellIndex++).setCellValue("货位号");
		row.createCell(cellIndex++).setCellValue("供货属性");
		row.createCell(cellIndex++).setCellValue("是否同步库存");
		row.createCell(cellIndex++).setCellValue("ERP_Id");
		// 设置第二行（备注）
		// row = sheet.createRow(1);
		// 合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
		// sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 12));
		// sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, --cellIndex));
	}

	@Override
	public void updateUpCommoidtyList(String upCommodity) {
		if (Tools.isNotEmpty(upCommodity)) {
			dao.updateUpCommoidtyList(upCommodity.split(","));
		}
	}

	@Override
	public List<HashMap<String, Object>> getTeamPurchasingCommodity(Integer commodityId, Integer communityId,
																	String areaCode) {
		return dao.getTeamPurchasingCommodity(commodityId, communityId, areaCode);
	}

	@Override
	public int getInventoryByNO(String commodityNo) {
		return dao.getInventoryByNO(commodityNo);
	}

	@Override
	public void updateSoldOut(String commodityNo) {
		dao.updateSoldOut(commodityNo);
	}

	@Override
	public void updateInventoryAdd(HajOrderDetails orderDetails) {
		dao.updateInventoryAdd(orderDetails);
	}

	@Override
	public boolean updateInventoryReduce(HajOrder order) {
		boolean inventoryIsEnough = true;

		// 库存不足的提示信息
		StringBuilder understockMsg;

		// 单个商品的库存量
		int inventory;

		// 商品状态
		int getShelvesByNo;

		// 是否需要下架商品
		boolean isSoldOut = false;

		// 后台配置的可剩余的最大库存量，如果用户订单中某商品的数量大于此数值，则下架该商品
		Integer excessInventory = Integer.parseInt((String) redisSpringProxy
				.read("SystemConfiguration_excess_inventory"));
		log.info("订单【" + order.getOrderNo() + "】进入扣减库存：");

		// 减库存时受影响的行数，如果没有减成功，则是库存不足
		int rows;
		if (order.getOrderDetailList().size() > 0) {
			for (HajOrderDetails details : order.getOrderDetailList()) {
				// 商品的当前库存
				inventory = dao.getInventoryByNO(details.getCommodityNo());
				log.info(details.getCommodityNo() + " 剩余库存: " + inventory);

				// 商品的当前状态
				getShelvesByNo = dao.getShelvesByNO(details.getCommodityNo());
				log.info("商品编号：" + details.getCommodityNo() + ",商品的当前状态：" + getShelvesByNo);

				// 订单中包含下架商品时，抛异常回滚之前操作
				if (getShelvesByNo < 1) {
					understockMsg = new StringBuilder();
					understockMsg.append(details.getCommodityName());
					understockMsg.append("已下架");
					throw new ArithmeticException(understockMsg.toString());
				}

				if (inventory >= details.getNumber()) {
					// 库存足够，减去订单中相应的库存
					rows = dao.updateInventoryReduce(details);
					if (rows > 0) {
						inventory = dao.getInventoryByNO(details.getCommodityNo());
						if (inventory < 1) {
							// 库存小于1时，下架商品
							updateSoldOut(details.getCommodityNo());
							Integer commodityId = dao.getIdByCommodityNo(details.getCommodityNo());

							log.info("商品售完，清缓存，建索引。commodityId:" + commodityId);
							resetCommodityRedis();
							try {
								createCommodityIndex(commodityId);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					} else {
						inventoryIsEnough = false;
					}
				} else {
					// 库存小于订单中的商品数量
					if (details.getNumber() > excessInventory || inventory < 1) {
						// 库存小于最大滞留库存时，下架商品，controller的catch中操作
						isSoldOut = true;
					}
					inventoryIsEnough = false;
				}

				if (!inventoryIsEnough) {
					understockMsg = new StringBuilder();
					understockMsg.append(details.getCommodityName());
					understockMsg.append("库存不足，剩余");
					understockMsg.append(inventory);
					understockMsg.append("份");

					// 需要将该商品下架时，将以下信息返回
					if (isSoldOut) {
						understockMsg.append(";");
						understockMsg.append(details.getCommodityNo());
					}
					throw new ArithmeticException(understockMsg.toString());
				}
			}
		}

		return true;
	}

	@Override
	public List<HajCommodityVo> listPageInventory(HajCommodityVo commodityVo) throws Exception {
		if (commodityVo.getInvalid() == null) {
			commodityVo.setInvalid(0);
		}

		return dao.listPageInventory(commodityVo);
	}

	@Override
	public void shangJia(String typeId) {
		dao.shangJia(typeId.split(","));
	}

	@Override
	public void updateShelvesById(String[] ids, int shelves) {
		HajCommodity commodity;
		for (String id : ids) {
			commodity = new HajCommodity();
			commodity.setId(Integer.valueOf(id));
			commodity.setShelves(shelves);

			// 不能将已作废的商品上架，可以下架
			commodity.setInvalid(shelves == 1 ? 0 : null);
			dao.updateShelvesById(commodity);

			// 上架商品 更新优惠卷商品表
			commodity = dao.queryById(commodity.getId());
			if (commodity.getShelves() == 1) {
				try {
					couponCommodityService.updateCouponCommodity(commodity);
				} catch (Exception e) {
					log.info(e.getMessage(), e);
				}
			}
		}
	}

	@Override
	public void updateShelvesByManual(String[] ids, int shelves) {
		HajCommodity commodity;
		for (String id : ids) {
			commodity = new HajCommodity();
			commodity.setId(Integer.valueOf(id));
			commodity.setShelves(shelves);

			// 不能将已作废的商品上架，可以下架
			commodity.setInvalid(shelves == 1 ? 0 : null);
			if (shelves == 0) {
				// 后台手动下架加个标志
				commodity.setShelvesWay(1);
			}
			dao.updateShelvesByManual(commodity);

			// 上架商品 更新优惠卷商品表
			commodity = dao.queryById(commodity.getId());
			if (commodity.getShelves() == 1) {
				try {
					couponCommodityService.updateCouponCommodity(commodity);
				} catch (Exception e) {
					log.info(e.getMessage(), e);
				}
			}
		}
	}

	@Override
	public HashMap<String, String> getCommoidtyByName(String name, String areaCode) {
		return dao.getCommoidtyByName(name, areaCode);
	}

	@Override
	public int updatePromotionAreaIdTo0(Integer[] commodityId) {
		return dao.updatePromotionAreaIdTo0(commodityId);
	}

	@Override
	public List<Map<String, Object>> getCommodityMustBuy(Integer communityId, String areaCode) {
		return dao.getCommodityMustBuy(communityId, areaCode);
	}

	@Override
	public List<Map<String, Object>> getCommoditysMustBuy(Integer communityId, String areaCode) {
		return dao.getCommoditysMustBuy(communityId, areaCode);
	}

	@Override
	public XSSFWorkbook excelInventoryList(HajCommodityVo commodityVo, boolean more) {

		// 创建HSSFWorkbook对象(excel的文档对象)
		XSSFWorkbook wkb = new XSSFWorkbook();

		// 建立新的sheet对象（excel的表单）
		XSSFSheet sheet = wkb.createSheet();
		sheet.setDefaultColumnWidth(12);
		sheet.setDefaultRowHeightInPoints(20);

		// 在sheet里创建第1行
		XSSFRow row = sheet.createRow(0);

		// 创建单元格并设置单元格内容
		this.setDefaultXSSFRow1(row, more);

		List<HajCommodityVo> inventoryList = dao.excelInventoryList(commodityVo);
		HajCommodityVo vo;

		int cellIndex = 0;
		for (int i = 0, len = inventoryList.size(); i < len; i++, cellIndex = 0) {
			vo = inventoryList.get(i);
			row = sheet.createRow(i + 1);
			row.createCell(cellIndex++).setCellValue(i + 1);
			row.createCell(cellIndex++).setCellValue(Tools.getCityNameByAreaCode(vo.getAreaCode()));
			row.createCell(cellIndex++).setCellValue(vo.getCommodityNo());
			row.createCell(cellIndex++).setCellValue(vo.getSku() == null ? "" : vo.getSku());
			row.createCell(cellIndex++).setCellValue(vo.getCommodityAttr() == null ? "" : vo.getCommodityAttr());
			row.createCell(cellIndex++).setCellValue(vo.getParentTypeName() == null ? "" : vo.getParentTypeName());
			row.createCell(cellIndex++).setCellValue(vo.getTypeName() == null ? "" : vo.getTypeName());
			row.createCell(cellIndex++).setCellValue(vo.getSupplyName() == null ? "" : vo.getSupplyName());
			row.createCell(cellIndex++).setCellValue(vo.getCommodityName() == null ? "" : vo.getCommodityName());
			row.createCell(cellIndex++).setCellValue(vo.getWeight() == null ? "" : vo.getWeight());
			row.createCell(cellIndex++).setCellValue(vo.getTodaySales() == null ? 0 : vo.getTodaySales());

			if (more) {
				// 信息导出
				row.createCell(cellIndex++).setCellValue(vo.getCostPrice().toString());
				row.createCell(cellIndex++).setCellValue(this.getSalePrice(vo).toString());
				row.createCell(cellIndex++).setCellValue(vo.getCostPrice().multiply(new BigDecimal(vo.getTodaySales())).toString());
				row.createCell(cellIndex++).setCellValue(this.getSalePrice(vo).multiply(new BigDecimal(vo.getTodaySales())).toString());
			}

			row.createCell(cellIndex++).setCellValue(vo.getInventory() == null ? 0 : vo.getInventory());
			row.createCell(cellIndex++).setCellValue(vo.getInventoryInit() == null ? 0 : vo.getInventoryInit());
			row.createCell(cellIndex++).setCellValue(getShelves(vo.getShelves()));
			row.createCell(cellIndex++).setCellValue(vo.getOrderClassification() == null ? "" : vo.getOrderClassification());
			row.createCell(cellIndex++).setCellValue(vo.getStorageNo() == null ? "" : vo.getStorageNo());
		}

		return wkb;
	}

	private BigDecimal getSalePrice(HajCommodityVo vo) {
		BigDecimal salePrice;
		if (vo.getActivityId() != null && vo.getActivityId() > 0) {
			salePrice = vo.getPromotionPrice();
		} else {
			salePrice = vo.getOriginalPrice();
		}
		return salePrice;
	}

	private String getShelves(Integer status) {
		String returnStatus = "";
		switch (status) {
			case 1:
				returnStatus = "上架";
				break;
			case 0:
				returnStatus = "下架";
				break;
			default:
				break;
		}
		return returnStatus;
	}

	private void setDefaultXSSFRow1(XSSFRow row, boolean more) {
		int cellIndex = 0;
		// 初始化第一行标题
		row.createCell(cellIndex++).setCellValue("序号");
		row.createCell(cellIndex++).setCellValue("供应城市");
		row.createCell(cellIndex++).setCellValue("商品编号");
		row.createCell(cellIndex++).setCellValue("物料号");
		row.createCell(cellIndex++).setCellValue("属性");
		row.createCell(cellIndex++).setCellValue("大类");
		row.createCell(cellIndex++).setCellValue("小类");
		row.createCell(cellIndex++).setCellValue("供应商");
		row.createCell(cellIndex++).setCellValue("商品名");
		row.createCell(cellIndex++).setCellValue("规格");
		row.createCell(cellIndex++).setCellValue("今日销量");
		if (more) {
			row.createCell(cellIndex++).setCellValue("成本价");
			row.createCell(cellIndex++).setCellValue("今日售价");
			row.createCell(cellIndex++).setCellValue("成本总额");
			row.createCell(cellIndex++).setCellValue("售出总额");
		}
		row.createCell(cellIndex++).setCellValue("剩余库存量");
		row.createCell(cellIndex++).setCellValue("初始库存量");
		row.createCell(cellIndex++).setCellValue("状态");
		row.createCell(cellIndex++).setCellValue("订单详情所在列");
		row.createCell(cellIndex++).setCellValue("货位号");
	}

	@Override
	public List<Map<String, Object>> getBuyLimitCommodity(Integer communityId, String areaCode) {
		return dao.getBuyLimitCommodity(communityId, areaCode);
	}

	@Override
	public int updateCommodityClearActivity(String activityName, String areaCode) {
		return dao.updateCommodityClearActivity(activityName, areaCode);
	}

	@Override
	public int updateResetGrossWeight() {
		return dao.updateResetGrossWeight();
	}

	@Override
	public HashMap<String, Object> getById(Integer commodityId) {
		return dao.getById(commodityId);
	}

	@Override
	public List<Map<String, Object>> getCommodityList3_0(Criteria criteria) {
		return dao.getCommodityByTypeId(criteria);
	}

	@Override
	public HajCommodity getByName(String name, String areaCode) {
		return dao.getByName(name, areaCode);
	}

	@Override
	public List<HashMap<String, Object>> getCommodityByPromotionAreaId(Map<String, Object> condition) {
		return dao.getCommodityByPromotionAreaId(condition);
	}

	@Override
	public List<HashMap<String, Object>> getCommodityByPromotionAreaIdOld(Map<String, Object> condition) {
		return dao.getCommodityByPromotionAreaIdOld(condition);
	}

	@Override
	public List<HashMap<String, Object>> getCommodityListByPromotionAreaId(Criteria criteria) {
		return dao.getCommodityListByPromotionAreaId(criteria);
	}

	@Override
	public int updateSalesVolume() {
		log.info("开始同步统计销量...");
		return dao.updateSalesVolume();
	}

	@Override
	public int getCountByType(HajCommodityType commodityType) {
		return dao.getCountByType(commodityType);
	}

	/**
	 * 修改商品上架
	 */
	@Override
	public void updateCommodityUpByNo(String commodityNo) {
		dao.updateCommodityUpByNo(commodityNo);
	}

	@Override
	public List<HashMap<String, Object>> getCommodityAttrList(Integer communityId, String areaCode) {
		// 商品的属性及数量
		List<HashMap<String, Object>> commodityAttrList = dao.getCommodityAttrList(communityId, areaCode);

		// 新品跟爆品的数量
		HajCommodity commodity = new HajCommodity();
		List<HashMap<String, Object>> commodityList;

		commodity.setAreaCode(areaCode);
		HashMap<String, Object> map = new HashMap<>();
		map.put("commodityAttr", "新品推荐");
		commodity.setMark("new");
		commodityList = dao.getByMark(commodity);
		map.put("total", commodityList.size());
		commodityAttrList.add(map);

		map = new HashMap<>();
		map.put("commodityAttr", "爆品热卖");
		commodity.setMark("hot");
		commodityList = dao.getByMark(commodity);
		map.put("total", commodityList.size());
		commodityAttrList.add(map);

		return commodityAttrList;
	}

	@Override
	public HajCommodity getCommodityByNo(String commodityNo) {
		return dao.getCommodityByNo(commodityNo);
	}

	@Override
	public List<String> getInvtSyncSkuList() {
		return dao.getInvtSyncSkuList();
	}

	@Override
	public boolean wmsInventorySync(List<WmsInventory> inventoryList) {
		int rows = 0;
		for (WmsInventory invt : inventoryList) {
			if (Tools.isNotEmpty(invt.getSku()) && invt.getAvlqty() != null) {
				rows += dao.updateInventory(invt);
			} else {
				log.info("商品编号或库存为空，同步失败，商品编号: " + invt.getSku());
			}
		}

		return rows > 0;
	}

	@Override
	public int getCountByActivity(HajActivity activity) {
		return dao.getCountByActivity(activity);
	}

	@Override
	public List<HashMap<String, Object>> listByActivity(HajActivity activity) {
		return dao.listByActivity(activity);
	}

	@Override
	public List<HashMap<String, Object>> getByMark(HajCommodity commodity) {
		return dao.getByMark(commodity);
	}

	@Override
	public List<HashMap<String, Object>> getByMarkPage(Criteria criteria) {
		return dao.getByMarkPage(criteria);
	}

	@Override
	public void save(HajCommodity commodity) throws Exception {
		// 没有id的情况视为新增，存在id视为修改
		if (commodity.getId() != null && commodity.getId() > 0) {
			HajCommodity beforeUpdate = dao.queryById(commodity.getId());

			// 商品添加到活动后，如果商品没有活动价，则将该商品的活动价调整为售价一致。
			if (commodity.getActivityId() != null && commodity.getActivityId() > 0) {
				if (beforeUpdate.getPromotionPrice() == null || beforeUpdate.getPromotionPrice().compareTo(BigDecimal.ZERO) <= 0) {
					commodity.setPromotionPrice(beforeUpdate.getOriginalPrice());
				}
			} else {
				// 未参加活动则将活动价设为0
				commodity.setPromotionPrice(BigDecimal.ZERO);
			}

			dao.updateBySelective(commodity);

			// 如果-品牌-被修改过，则需要清空该商品在优惠券商品表中的记录
			updateCouponCommodityByBrand(commodity, beforeUpdate);

			// 如果-分类-被修改过，则需要清空该商品在优惠券商品表中的记录
			updateCouponCommodityByType(commodity, beforeUpdate);

			// 同步修改至京东WMS
			if (!beforeUpdate.getName().equals(commodity.getName())) {
				WmsSender.updateGoodsInfo(commodity);
			}
		} else {
			// 商品上下架在商品供应管理中统一管理，添加时默认下架。修改商品不做处理
			commodity.setShelves(0);

			// 创建的商品的成本价、售价、参考价均设置为999，活动价设置为0
			commodity.setCostPrice(new BigDecimal(999));
			commodity.setOriginalPrice(new BigDecimal(999));
			commodity.setMarketPrice(new BigDecimal(999));
			commodity.setPromotionPrice(BigDecimal.ZERO);
			dao.add(commodity);

			// 同步新增至京东WMS
			WmsSender.transportGoodsInfo(commodity);
		}
	}

	@Override
	public void saveToCommodityZone(List<HajCommodityPromotionArea> hajCommodityPromotionAreas) {
		for (HajCommodityPromotionArea hcpa : hajCommodityPromotionAreas) {
			hcpaDao.saveToCommodityZone(hcpa);
		}
	}

	/**
	 * 批量添加商品到专题中
	 *
	 * @param hajSpecialTopic
	 */
	@Override
	public void saveToCommodityTopic(List<HajSpecialTopicCommodity> hajSpecialTopic) {
		for (HajSpecialTopicCommodity htc : hajSpecialTopic) {
			htcDao.saveToCommodityTopic(htc);
		}
	}

	/**
	 * 批量添加商品到活动中
	 *
	 * @param
	 */
	@Override
	public void saveToCommodityActivity(Integer[] commodityId, Integer activityId) {
		for (Integer id : commodityId) {
			//修改商品活动id
			HajCommodity commodity = dao.getCommodity(id);
			commodity.setActivityId(activityId);
			dao.update(commodity);
		}
	}

	@Override
	public void saveToCommodityPlate(List<HajCommodity> commodityList) {
		for (HajCommodity hc : commodityList) {
//			dao.saveToCommodityPlate(hc);
			dao.update(hc);
		}
	}

	@Override
	public void deleteCommodityFromTheZone(List<HajCommodityPromotionArea> hajCommodityPromotionAreas) {
		for (HajCommodityPromotionArea hcpa : hajCommodityPromotionAreas) {
			hcpaDao.deleteCommodityFromTheZone(hcpa);
		}
	}

	@Override
	public void deleteCommodityFromTheTopic(List<HajSpecialTopicCommodity> hajSpecialTopicCommodity) {
		for (HajSpecialTopicCommodity hcpa : hajSpecialTopicCommodity) {
			htcDao.deleteCommodityFromTheTopic(hcpa);
		}
	}

	@Override
	public void deleteCommodityFromTheActivity(Integer[] commodityId, Integer activityId) {
		for (Integer id : commodityId) {
			//修改商品活动id
			HajCommodity commodity = dao.getCommodity(id);
			commodity.setActivityId(0);
			dao.update(commodity);
		}
	}

	@Override
	public boolean saveCommodity(ERPMaterielVo vo) throws Exception {
		Integer fMaterialId = vo.getFMaterialId();
		int i = dao.getTotalBycloudsId(fMaterialId);
		if (i > 0) {
			return false;
		} else {
			//根据城市编号查询城市id
			String code = "";
			if (AreaCode.ERP_SZ.equals(vo.getFOrgCode())) {
				code = AreaCode.SZ;
			}
			if (AreaCode.ERP_GZ.equals(vo.getFOrgCode())) {
				code = AreaCode.GZ;
			}
			//添加
			HajCommodity hajCommodity = new HajCommodity();
			//生成商品编号
			String commodityNo = Tools.date2Str(new Date(), "yyMMddHHmmssSSS");
			hajCommodity.setCommodityNo(redisSpringProxy.read("SystemConfiguration_commodityNoPrefix") + commodityNo);
			hajCommodity.setCloudsId(vo.getFMaterialId() == null ? 0 : vo.getFMaterialId());
			hajCommodity.setSku(vo.getFItemCode() == null ? "" : vo.getFItemCode());
			hajCommodity.setName(vo.getFItemName() == null ? "" : vo.getFItemName());
			hajCommodity.setAlias(vo.getFItemName() == null ? "" : vo.getFItemName());
			hajCommodity.setWeight(vo.getFItemDesc() == null ? "" : vo.getFItemDesc());
			hajCommodity.setProducer(vo.getFItemAddress() == null ? "" : vo.getFItemAddress());
			hajCommodity.setCostPrice(vo.getFReferenPricee());
			hajCommodity.setOriginalPrice(vo.getFSalePrice());
			hajCommodity.setMarketPrice(vo.getFCostPrice());
			hajCommodity.setSupplyChain(vo.getFSupplyCode() == null ? "" : vo.getFSupplyCode());
			hajCommodity.setAreaCode(code);
			//有些字段不能为空
			hajCommodity.setShelvesWay(1);
			hajCommodity.setAcidDischarge("0");
			hajCommodity.setInventory(0);
			hajCommodity.setMustBuy(0);
			hajCommodity.setStamp(0);
			hajCommodity.setInventoryInit(0);
			hajCommodity.setAttribute(1);
			hajCommodity.setPercentLoss(BigDecimal.ZERO);
			hajCommodity.setOrderClassification("A");
			hajCommodity.setDescription("无");
			hajCommodity.setSyncInvt(0);
			//默认下架
			hajCommodity.setShelves(0);
			dao.add(hajCommodity);

			return true;
		}


	}

	@Override
	public void updateCommodity(ERPMaterielVo vo) throws Exception {
		//根据城市编号查询城市id
		String code = "";
		if (AreaCode.ERP_SZ.equals(vo.getFOrgCode())) {
			code = AreaCode.SZ;
		}
		if (AreaCode.ERP_GZ.equals(vo.getFOrgCode())) {
			code = AreaCode.GZ;
		}
		HajCommodity hajCommodity = new HajCommodity();
		hajCommodity.setName(vo.getFItemName() == null ? "" : vo.getFItemName());
		hajCommodity.setSku(vo.getFItemCode() == null ? "" : vo.getFItemCode());
		hajCommodity.setWeight(vo.getFItemDesc() == null ? "" : vo.getFItemDesc());
		hajCommodity.setProducer(vo.getFItemAddress() == null ? "" : vo.getFItemAddress());
		hajCommodity.setCloudsId(vo.getFMaterialId() == null ? 0 : vo.getFMaterialId());
		hajCommodity.setSupplyChain(vo.getFSupplyCode() == null ? "" : vo.getFSupplyCode());
//		hajCommodity.setCostPrice(vo.getFCostPrice());
//		hajCommodity.setOriginalPrice(vo.getFSalePrice());
//		hajCommodity.setMarketPrice(vo.getFReferenPricee());
		hajCommodity.setAreaCode(code);
		dao.updateCommodityByCloudsId(hajCommodity);
	}

	@Override
	public void updateSyncInventoryOne(HajCommodity commodity) {
		//参数
		Map<String, Object> parameter = new HashMap<String, Object>();
		if (AreaCode.SZ.equals(commodity.getAreaCode())) {
			parameter.put("FOrgCode", AreaCode.ERP_SZ);
		}
		if (AreaCode.GZ.equals(commodity.getAreaCode())) {
			parameter.put("FOrgCode", AreaCode.ERP_GZ);
		}
		parameter.put("FItemCode", commodity.getSku());
		parameter.put("FStockCode", "");

		String InventoryString = JSONObject.valueToString(parameter);
		log.info("同步单个商品请求参数: " + JSONObject.valueToString(parameter));
		org.apache.axis.client.Service service = new org.apache.axis.client.Service();
		Call call = null;
		try {
			call = (Call) service.createCall();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		call.setTargetEndpointAddress("http://120.77.202.243/LGCSWebService/InventoryService.asmx");
		call.setProperty("InventoryString", InventoryString);
		call.addParameter(new QName("http://tempuri.org/", "InventoryString"), XMLType.XSD_STRING, ParameterMode.IN);  //设置要传递的参数
		call.setReturnType(new QName("http://tempuri.org/"), String.class); //要返回的数据类型
		String rtn = null;
		try {
			rtn = (String) call.invoke(new QName("http://tempuri.org/", "SearchInventory"), new Object[]{InventoryString});
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		List<Map<String, Object>> list = JSONUtils.toList(rtn);
		HajCommodity com = new HajCommodity();
		//更新库存 (new Double(list.get(0).get("FQty"))).intValue()
		if (!list.isEmpty() && list.size() > 0) {
			com.setInventory((new Double((Double) list.get(0).get("FQty"))).intValue());
			com.setInventoryInit((new Double((Double) list.get(0).get("FQty"))).intValue());
			com.setId(commodity.getId());
			dao.updateBySelective(com);
		}
	}

	@Override
	public void updateSyncInventory() {
		//参数
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("FOrgCode", "");
		parameter.put("FItemCode", "");
		parameter.put("FStockCode", "");
		org.apache.axis.client.Service service = new org.apache.axis.client.Service();
		Call call = null;
		try {
			call = (Call) service.createCall();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		String InventoryString = JSONObject.valueToString(parameter);

		call.setTargetEndpointAddress("http://120.77.202.243/LGCSWebService/InventoryService.asmx");
		call.setProperty("InventoryString", InventoryString);
		call.addParameter(new QName("http://tempuri.org/", "InventoryString"), XMLType.XSD_STRING, ParameterMode.IN);  //设置要传递的参数
		call.setReturnType(new QName("http://tempuri.org/"), String.class); //要返回的数据类型
		String rtn = null;
		try {
			rtn = (String) call.invoke(new QName("http://tempuri.org/", "SearchInventory"), new Object[]{InventoryString});
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		log.info("同步所有商品库存返回结果：" + rtn);
		List<Map<String, Object>> list = JSONUtils.toList(rtn);
		if (!list.isEmpty() && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				HashMap stringObjectMap = (HashMap) list.get(i);
				if (AreaCode.ERP_SZ.equals(stringObjectMap.get("FOrgCode"))) {
					stringObjectMap.remove("FOrgCode");
					stringObjectMap.put("FOrgCode", AreaCode.SZ);
				}
				if (AreaCode.ERP_GZ.equals(stringObjectMap.get("FOrgCode"))) {
					stringObjectMap.remove("FOrgCode");
					stringObjectMap.put("FOrgCode", AreaCode.GZ);
				}
			}
		}
		//更新所有商品库存
		dao.updateCommodityInventory(list);
	}

	private void updateCouponCommodityByType(HajCommodity commodity, HajCommodity beforeUpdate) throws Exception {
		// 如果beforeUpdate对象为空，则说明是添加商品，添加商品无需处理，因为添加时默认下架
		if (beforeUpdate != null && !Objects.equals(beforeUpdate.getTypeId(), commodity.getTypeId())) {
			HajCommodity afterUpdate = dao.queryById(commodity.getId());

			// 首先，通过之前的分类去查找优惠券分类表查找所有对应的优惠券ID
			List<Integer> listCouponIdByTypeId = couponTypeService.listCouponIdByTypeId(beforeUpdate.getTypeId());

			// 然后根据优惠券ID及商品编号删除该商品在优惠券商品表中的记录
			if (listCouponIdByTypeId.size() > 0) {
				couponCommodityService.deleteAfterCommodityUpdate(afterUpdate.getCommodityNo(), listCouponIdByTypeId);
			}

			if (afterUpdate.getShelves() == 1) {
				// 如果被修改分类的商品是上架状态，则需要将此商品更新到优惠券商品表
				couponCommodityService.updateCouponCommodity(afterUpdate);
			}
		}
	}

	private void updateCouponCommodityByBrand(HajCommodity commodity, HajCommodity beforeUpdate) throws Exception {
		// 如果beforeUpdate对象为空，则说明是添加商品，添加商品无需处理，因为添加时默认下架
		if (beforeUpdate != null && !Objects.equals(beforeUpdate.getBrandId(), commodity.getBrandId())) {
			HajCommodity afterUpdate = dao.queryById(commodity.getId());

			// 首先，通过之前的品牌去查找优惠券品牌表查找所有对应的优惠券ID
			List<Integer> listCouponIdByBrandId = couponBrandService.listCouponIdByBrandId(beforeUpdate.getBrandId());

			// 然后根据优惠券ID及商品编号删除该商品在优惠券商品表中的记录
			if (listCouponIdByBrandId.size() > 0) {
				couponCommodityService.deleteAfterCommodityUpdate(afterUpdate.getCommodityNo(), listCouponIdByBrandId);
			}

			if (afterUpdate.getShelves() == 1) {
				// 如果被修改品牌的商品是上架状态，则需要将此商品更新到优惠券商品表
				couponCommodityService.updateCouponCommodity(afterUpdate);
			}
		}
	}

	public static void main(String[] args) {
		String commodityNo = Tools.date2Str(new Date(), "yyMMddHHmmssSSS");
		System.out.println(commodityNo);
	}
}
