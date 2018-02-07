package com.flf.controller.app;

import com.base.criteria.Criteria;
import com.base.util.HttpUtil;
import com.flf.entity.*;
import com.flf.resolver.RollbackException;
import com.flf.service.*;
import com.flf.util.DateUtil;
import com.flf.util.JSONUtils;
import com.flf.util.MD5Tools;
import com.flf.util.Tools;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <br>
 * <b>功能：</b>HajCommodityAppController<br>
 * <br>
 */
@Controller
@RequestMapping(value = "/hajCommodity")
public class HajCommodityAppController {

	private final static Logger log = Logger.getLogger(HajCommodityAppController.class);

	@Autowired(required = false)
	private HajCommodityService service;

	@Autowired(required = false)
	private HajActivityService activityService;

	@Autowired(required = false)
	private HajCommodityTypeService hajCommodityTypeService;

	@Autowired
	private RedisSpringProxy redisSpringProxy;

	@Autowired(required = false)
	private HajPromotionAreaService areaService;

	/**
	 * 获取所有已上架商品列表
	 */
	@RequestMapping(value = "/getCommodity")
	@Deprecated
	public void getCommodityApp(@RequestParam String sign, HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			if (MD5Tools.checkSign(sign)) {
				List<HajCommodity> HajCommodity = null;
				if (null != redisSpringProxy.read("HajAllCommodity")) {
					log.info("获取所有商品列表缓存中");
					HajCommodity = (List<HajCommodity>) redisSpringProxy.read("HajAllCommodity");
				} else {
					HajCommodity = service.queryAllList();
					// 查询到结果就放入缓存中
					if (HajCommodity.size() > 0) {
						redisSpringProxy.save("HajAllCommodity", HajCommodity);
					}
				}

				if (HajCommodity.size() > 0) {
					jsonMap.put("error", "0");
					jsonMap.put("msg", "成功");
					jsonMap.put("HajCommodity", HajCommodity);
					JSONUtils.printObject(jsonMap, response);
				} else {
					jsonMap.put("error", "0");
					jsonMap.put("msg", "为空");
					jsonMap.put("HajCommodity", "");
					JSONUtils.printObject(jsonMap, response);
				}
			} else {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "签名异常");
				jsonMap.put("HajCommodity", "");
				JSONUtils.printObject(jsonMap, response);
			}
		} catch (Exception e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
			try {
				JSONUtils.printObject(jsonMap, response);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			log.info(e.getMessage(), e);
		}

	}

	/**
	 * 根据商品类型ID获取商品列表1.0
	 *
	 * @param typeId
	 * @param currentPage
	 * @deprecated 推荐使用2.0版本
	 */
	@Deprecated
	@RequestMapping(value = "/getCommodityByTypeId")
	public void getCommodityByTypeIdApp(@RequestParam Integer currentPage, @RequestParam Integer typeId,
										HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		Criteria criteria = new Criteria();
		criteria.setCurrentPage(currentPage);
		try {
			List<Map<String, Object>> hajCommodityList = null;
			String redisKey = "TypeHajCommodity_" + typeId + "_" + currentPage;

			if (redisSpringProxy.read(redisKey) != null) {
				log.info("getCommodityByTypeIdApp1.0()根据商品类型ID:" + typeId + "获取商品列表缓存中");
				hajCommodityList = (List<Map<String, Object>>) redisSpringProxy.read(redisKey);
			} else {
				hajCommodityList = service.getCommodityByTypeId(typeId, criteria);
				// 查询到结果就放入缓存中
				if (hajCommodityList.size() > 0) {
					redisSpringProxy.save(redisKey, hajCommodityList);
				}
			}

			if (hajCommodityList.size() > 0) {
				jsonMap.put("error", "0");
				jsonMap.put("msg", "成功");
				jsonMap.put("HajCommodity", hajCommodityList);
				jsonMap.put("page", criteria);
			} else {
				jsonMap.put("error", "0");
				jsonMap.put("msg", "为空");
				jsonMap.put("HajCommodity", "");
				jsonMap.put("page", criteria);
			}
		} catch (Exception e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
			jsonMap.put("page", criteria);
			e.printStackTrace();
			log.info(e.getMessage(), e);
		} finally {
			try {
				JSONUtils.printObject(jsonMap, response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * APP商品列表2.0（分类，排序）
	 *
	 * @param currentPage
	 * @param typeId
	 * @param sort
	 * @param response
	 * @author SevenWong<br>
	 * @date 2016年7月16日下午5:25:56
	 */
	@Deprecated
	@RequestMapping(value = "/getCommodityByTypeId2")
	public void getCommodityByTypeIdApp2(@RequestParam Integer currentPage, @RequestParam Integer bigTypeId,
										 @RequestParam Integer typeId, String sort, Integer communityId, HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		Criteria criteria = new Criteria();
		criteria.setCurrentPage(currentPage);
		try {
			sort = Tools.isEmpty(sort) ? "recommend" : sort;
			List<Map<String, Object>> hajCommodityList = null;
			String redisKey = "TypeHajCommodity_" + bigTypeId + "_" + typeId + "_" + sort + "_" + currentPage + "_"
					+ communityId;

			if (redisSpringProxy.read(redisKey) != null) {
				log.info("getCommodityByTypeIdApp2.0()redisKey:" + redisKey + " 获取商品列表缓存...");
				hajCommodityList = (List<Map<String, Object>>) redisSpringProxy.read(redisKey);
			} else {
				hajCommodityList = service.getCommodityByTypeId(bigTypeId, typeId, //
						sort, communityId, criteria);

				// 查询到结果就放入缓存中
				if (hajCommodityList.size() > 0) {
					redisSpringProxy.save(redisKey, hajCommodityList);
				}
			}

			if (hajCommodityList.size() > 0) {
				jsonMap.put("error", "0");
				jsonMap.put("msg", "成功");
				jsonMap.put("HajCommodity", hajCommodityList);
				jsonMap.put("page", criteria);
			} else {
				jsonMap.put("error", "0");
				jsonMap.put("msg", "为空");
				jsonMap.put("HajCommodity", "");
				jsonMap.put("page", criteria);
			}
		} catch (Exception e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
			jsonMap.put("page", criteria);
			e.printStackTrace();
			log.info(e.getMessage(), e);
		} finally {
			try {
				JSONUtils.printObject(jsonMap, response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * APP商品列表2.1(增加商品属性字段)
	 *
	 * @param commodityAttr
	 * @param bigTypeId
	 * @param typeId
	 * @param currentPage
	 * @param sort
	 * @param communityId
	 * @param response
	 * @author SevenWong<br>
	 * @date 2016年8月15日下午1:36:13
	 */
	@Deprecated
	@RequestMapping(value = "/getCommodityByTypeId2_1")
	public void getCommodityByTypeIdApp2_1(@RequestParam String commodityAttr, @RequestParam Integer bigTypeId,
										   @RequestParam Integer typeId, @RequestParam Integer currentPage, String sort, Integer communityId,
										   HttpServletResponse response) {
		HttpUtil.accessControlAllowOrigin(response);

		Map<String, Object> jsonMap = new HashMap<String, Object>();
		Criteria criteria = new Criteria();
		criteria.setCurrentPage(currentPage);
		criteria.setPageSize(1000);
		try {
			sort = Tools.isEmpty(sort) ? "recommend" : sort;
			List<Map<String, Object>> hajCommodityList = null;

			// 拼接缓存key
			commodityAttr = ("fruits".equals(commodityAttr)) ? "水果" : "生鲜";
			StringBuilder redisKeySB = new StringBuilder("TypeHajCommodity2.1_");
			redisKeySB.append(commodityAttr).append("_").append(bigTypeId).append("_");
			redisKeySB.append(typeId).append("_").append(currentPage).append("_");
			redisKeySB.append(sort).append("_").append(communityId);
			String redisKey = redisKeySB.toString();

			if (redisSpringProxy.read(redisKey) != null) {
				log.info("getCommodityByTypeIdApp2.1()redisKey:" + redisKey + " 获取商品列表缓存...");
				hajCommodityList = (List<Map<String, Object>>) redisSpringProxy.read(redisKey);
			} else {
				hajCommodityList = service.getCommodityByTypeId(commodityAttr, bigTypeId, typeId,
						currentPage, sort, communityId, criteria);

				// 查询到结果就放入缓存中
				if (hajCommodityList.size() > 0) {
					redisSpringProxy.save(redisKey, hajCommodityList);
				}
			}

			if (hajCommodityList.size() > 0) {
				jsonMap.put("error", "0");
				jsonMap.put("msg", "成功");
				jsonMap.put("HajCommodity", hajCommodityList);
				jsonMap.put("page", criteria);
			} else {
				jsonMap.put("error", "0");
				jsonMap.put("msg", "为空");
				jsonMap.put("HajCommodity", "");
				jsonMap.put("page", criteria);
			}
		} catch (Exception e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
			jsonMap.put("page", criteria);
			log.info(e.getMessage(), e);
		} finally {
			try {
				JSONUtils.printObject(jsonMap, response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * APP全部页面商品列表3.0接口<br>
	 * 此次根据大类ID获取该类下的所有商品及小类信息
	 *
	 * @param sign
	 * @param bigTypeId
	 * @param communityId
	 * @param response
	 * @author SevenWong<br>
	 * @date 2016年11月18日下午4:58:33
	 */
	@RequestMapping(value = "/getCommodityList3_0")
	public void getCommodityList3_0(@RequestParam String sign, @RequestParam Integer bigTypeId, Integer communityId,
									Integer typeId, String areaCode, HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<>();
		HttpUtil.accessControlAllowOrigin(response);
		try {
			if (MD5Tools.checkSign(sign)) {
				List<Map<String, Object>> hajCommodityList;
				areaCode = Tools.getAreaCode(areaCode);

				// 拼接缓存key
				String redisKey = "getCommodityList3_0_" + bigTypeId + "_" + typeId + "_" + communityId + "_"
						+ areaCode;

				if (redisSpringProxy.read(redisKey) != null) {
					log.info("商品列表(redis):" + redisKey);
					hajCommodityList = (List<Map<String, Object>>) redisSpringProxy.read(redisKey);
				} else {
					log.info("商品列表(db):" + redisKey);
					hajCommodityList = new ArrayList<>();

					// 封装查询条件
					Map<String, Object> condition = new HashMap<>();
					condition.put("parentId", bigTypeId);
					condition.put("communityId", communityId);
					condition.put("areaCode", Tools.getAreaCode(areaCode));

					Criteria criteria = new Criteria();
					criteria.setPageSize(1000);

					if (typeId != null && typeId > 0) {
						HajCommodityType type = hajCommodityTypeService.queryById(typeId);
						if (type != null) {
							setCommodity3_0_Data(hajCommodityList, condition, criteria, type);
						}
					} else {
						// 先根据大类ID获取所有小类列表
						List<HajCommodityType> typeByParentId = hajCommodityTypeService.getTypeByParentId(bigTypeId);

						for (HajCommodityType type : typeByParentId) {
							setCommodity3_0_Data(hajCommodityList, condition, criteria, type);
						}
					}

					// 查询结果放入缓存中
					redisSpringProxy.save(redisKey, hajCommodityList);
				}

				jsonMap.put("error", "0");
				jsonMap.put("msg", "成功");
				jsonMap.put("hajCommodityList", hajCommodityList);
			} else {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "签名异常");
			}
		} catch (Exception e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
			e.printStackTrace();
		} finally {
			try {
				JSONUtils.printObject(jsonMap, response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void setCommodity3_0_Data(List<Map<String, Object>> hajCommodityList, Map<String, Object> condition,
									  Criteria criteria, HajCommodityType type) {
		Map<String, Object> commodityMap;
		List<Map<String, Object>> commodityListByTypeId;
		condition.put("typeId", type.getId());
		criteria.setCondition(condition);

		commodityMap = new HashMap<>();
		commodityListByTypeId = service.getCommodityList3_0(criteria);
		if (commodityListByTypeId.size() > 0) {
			commodityMap.put("commodityList", commodityListByTypeId);
			commodityMap.put("smallType", type);
			hajCommodityList.add(commodityMap);
		}
	}

	/**
	 * 商品搜索引擎接口
	 */
	@RequestMapping(value = "/getCommoditySeach")
	public void getCommoditySearch(@RequestParam String wd, @RequestParam String sign,
								   @RequestParam Integer currentPage, Integer communityId, String areaCode, HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<>();
		Criteria criteria = new Criteria();
		criteria.setCurrentPage(currentPage);
		List<Map<String, Object>> hajCommodity = null;
		try {
			if (MD5Tools.checkSign(sign)) {
				areaCode = Tools.getAreaCode(areaCode);
				hajCommodity = service.searchCommodity(wd, communityId, criteria, areaCode);

				if (hajCommodity.size() > 0) {
					jsonMap.put("error", "0");
					jsonMap.put("msg", "成功");
				} else {
					jsonMap.put("error", "0");
					jsonMap.put("msg", "为空");
				}
			} else {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "签名异常");
			}
		} catch (Exception e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
			e.printStackTrace();
		} finally {
			try {
				jsonMap.put("HajCommodity", hajCommodity);
				jsonMap.put("page", criteria);
				JSONUtils.printObject(jsonMap, response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 根据父类查询子类商品集合
	 *
	 * @param parentId currentPage
	 */
	@Deprecated
	@RequestMapping(value = "/getCommodityParentId")
	public void getCommodityParentId(@RequestParam String sign, @RequestParam Integer parentId,
									 HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			if (MD5Tools.checkSign(sign)) {
				List<Map<String, Object>> list = service.getCommodityByParentId(parentId);
				if (list.size() > 0) {
					jsonMap.put("error", "0");
					jsonMap.put("msg", "成功");
					jsonMap.put("HajCommodity", list);
					JSONUtils.printObject(jsonMap, response);
				} else {
					jsonMap.put("error", "0");
					jsonMap.put("msg", "为空");
					jsonMap.put("HajCommodity", "");
					JSONUtils.printObject(jsonMap, response);
				}
			} else {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "签名异常");
				jsonMap.put("HajCommodity", "");
				JSONUtils.printObject(jsonMap, response);
			}
		} catch (Exception e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
			try {
				JSONUtils.printObject(jsonMap, response);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			log.info(e.getMessage(), e);
		}
	}

	/**
	 * 家人喜好（推荐商品），3.0后改为猜你喜欢数据接口
	 */
	@RequestMapping(value = "/getFamilyPreferences")
	public void getFamilyPreferences(@RequestParam String sign, Integer communityId, String areaCode,
									 HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<>();

		try {
			if (MD5Tools.checkSign(sign)) {
				List<HajCommodityVo> hajCommodity;
				areaCode = Tools.getAreaCode(areaCode);
				String redisKey = "FamilyHajCommodity_" + communityId + "_" + areaCode;
				if (null != redisSpringProxy.read(redisKey)) {
					log.info("读取猜你喜欢数据(redis)");
					hajCommodity = (List<HajCommodityVo>) redisSpringProxy.read(redisKey);
				} else {
					log.info("读取猜你喜欢数据(db)");
					hajCommodity = service.getNewProducts(communityId, areaCode);
					redisSpringProxy.save(redisKey, hajCommodity);
				}

				if (hajCommodity.size() > 0) {
					jsonMap.put("error", "0");
					jsonMap.put("msg", "成功");
					jsonMap.put("HajCommodity", hajCommodity);
				} else {
					jsonMap.put("error", "0");
					jsonMap.put("msg", "为空");
					jsonMap.put("HajCommodity", "");
				}
			} else {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "签名异常");
				jsonMap.put("HajCommodity", "");
			}
		} catch (Exception e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "系统异常：" + e);
			log.info(e.getMessage(), e);
		} finally {
			try {
				JSONUtils.printObject(jsonMap, response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	/**
	 * 新品推荐
	 * @param sign
	 * @param communityId
	 * @param areaCode
	 * @param response
	 */
	@RequestMapping(value = "/getFamilyPreferencesNew")
	public void getFamilyPreferencesNew(@RequestParam String sign, Integer communityId, String areaCode,
									 HttpServletResponse response, @RequestParam Integer currentPage) {
		Map<String, Object> jsonMap = new HashMap<>();

		Criteria criteria = new Criteria();
		Map<String, Object> condition = new HashMap<>();
		criteria.setCurrentPage(currentPage);
		condition.put("communityId", communityId);
		condition.put("areaCode", Tools.getAreaCode(areaCode));
		criteria.setCondition(condition);

		try {
			if (MD5Tools.checkSign(sign)) {
				List<HashMap<String, Object>> hajCommodity;
				areaCode = Tools.getAreaCode(areaCode);
				String redisKey = "FamilyHajCommodityNew_" + communityId + "_" + areaCode+"_"+currentPage;
				if (null != redisSpringProxy.read(redisKey)) {
					log.info("读取新品推荐数据(redis)");
					hajCommodity = (List<HashMap<String, Object>>) redisSpringProxy.read(redisKey);
				} else {
					log.info("读取新品推荐数据(db)");
					hajCommodity = service.getFamilyPreferences(criteria);
					redisSpringProxy.save(redisKey, hajCommodity);
				}

				if (hajCommodity.size() > 0) {
					jsonMap.put("error", "0");
					jsonMap.put("msg", "成功");
					jsonMap.put("HajCommodity", hajCommodity);
				} else {
					jsonMap.put("error", "0");
					jsonMap.put("msg", "为空");
					jsonMap.put("HajCommodity", "");
				}
			} else {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "签名异常");
				jsonMap.put("HajCommodity", "");
			}
		} catch (Exception e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "系统异常：" + e);
			log.info(e.getMessage(), e);
		} finally {
			try {
				JSONUtils.printObject(jsonMap, response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}



	/**
	 * 搜索推荐显示商品
	 */
	@RequestMapping(value = "/getSearchCommodity")
	public void getSearchRecommend(@RequestParam String sign, Integer communityId, String areaCode,
								   HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<>();
		try {
			if (MD5Tools.checkSign(sign)) {
				List<HajCommodityVo> hajCommodity;
				areaCode = Tools.getAreaCode(areaCode);
				String redisKey = "SearchHajCommodity_" + communityId + "_" + areaCode;
				if (null != redisSpringProxy.read(redisKey)) {
					log.info("读取搜索推荐商品数据(redis)");
					hajCommodity = (List<HajCommodityVo>) redisSpringProxy.read(redisKey);
				} else {
					log.info("读取搜索推荐商品数据(db)");
					hajCommodity = service.getSearchCommodity(communityId, areaCode);
					redisSpringProxy.save(redisKey, hajCommodity);
				}

				if (hajCommodity.size() > 0) {
					jsonMap.put("error", "0");
					jsonMap.put("msg", "成功");
					jsonMap.put("HajCommodity", hajCommodity);
				} else {
					jsonMap.put("error", "0");
					jsonMap.put("msg", "为空");
					jsonMap.put("HajCommodity", "");
				}
			} else {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "签名异常");
				jsonMap.put("HajCommodity", "");
			}
		} catch (Exception e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
			e.printStackTrace();
		} finally {
			try {
				JSONUtils.printObject(jsonMap, response);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * 获取商品价格
	 * <p>
	 * 由于APP3.3.0版本后，商品名可修改，如果后台修改商品名后，用户再刷新购物车，会导致购物车匹配不到原来的商品
	 * 因此需要更换此接口，改为按商品ID查找价格
	 * </p>
	 */
	@RequestMapping(value = "/getCommoidtyRealPrice")
	public void getCommoidtyRealPrice(@RequestParam String sign, Integer communityId, String areaCode,
									  @RequestParam String names, HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<>();
		try {
			if (MD5Tools.checkSign(sign)) {
				if (null != names && !"".equals(names)) {
					areaCode = Tools.getAreaCode(areaCode);
					List<HajCommodityPrice> realPrice = service.getCommoidtyRealPrice(names, communityId,
							areaCode);

					if (null != realPrice) {
						jsonMap.put("error", "0");
						jsonMap.put("msg", "成功");
						jsonMap.put("HajCommodity", realPrice);
					} else {
						jsonMap.put("error", "0");
						jsonMap.put("msg", "为空");
						jsonMap.put("HajCommodity", "");
					}
				} else {
					jsonMap.put("error", "0");
					jsonMap.put("msg", "购物车为空");
					jsonMap.put("HajCommodity", "");
				}
			} else {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "签名异常");
				jsonMap.put("HajCommodity", "");
			}
		} catch (Exception e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
			e.printStackTrace();
		} finally {
			try {
				JSONUtils.printObject(jsonMap, response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 根据商品ID查询商品价格
	 *
	 * @param sign
	 * @param ids
	 * @param communityId
	 * @param areaCode
	 * @param response
	 */
	@RequestMapping(value = "/getCommodityPrice")
	public void getCommodityPrice(@RequestParam String sign, @RequestParam String ids,
								  @RequestParam Integer communityId, String areaCode, HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<>();
		try {
			if (MD5Tools.checkSign(sign)) {
				if (Tools.isNotEmpty(ids)) {
					areaCode = Tools.getAreaCode(areaCode);
					List<HajCommodityPrice> realPrice = service.getCommodityPrice(ids, communityId,
							areaCode);

					if (null != realPrice) {
						jsonMap.put("error", "0");
						jsonMap.put("msg", "成功");
					} else {
						jsonMap.put("error", "0");
						jsonMap.put("msg", "为空");
					}
					jsonMap.put("HajCommodity", realPrice);
				} else {
					jsonMap.put("error", "0");
					jsonMap.put("msg", "购物车为空");
					jsonMap.put("HajCommodity", "");
				}
			} else {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "签名异常");
				jsonMap.put("HajCommodity", "");
			}
		} catch (Exception e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
			e.printStackTrace();
		} finally {
			try {
				JSONUtils.printObject(jsonMap, response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 根据页面获取团购商品，有id时根据id返回单个团购商品，否则返回团购商品列表
	 *
	 * @param commodityId
	 * @param response
	 * @author SevenWong<br>
	 * @date 2016年9月8日下午2:26:18
	 */
	@RequestMapping(value = "/getTeamPurchasingCommodity")
	public void getTeamPurchasingCommodity(Integer commodityId, Integer communityId, String areaCode,
										   HttpServletResponse response) {
		HttpUtil.accessControlAllowOrigin(response);

		Map<String, Object> jsonMap = new HashMap<>();
		try {
			List<HashMap<String, Object>> teamPurchasingCommodity;
			areaCode = Tools.getAreaCode(areaCode);
			String redisKey = "teamPurchasingCommodity_" + commodityId + "_" + communityId + "_" + areaCode;

			// 先从缓存读取，缓存没有才读数据库
			if (redisSpringProxy.read(redisKey) != null) {
				log.info("从缓存中读取团购商品...");
				teamPurchasingCommodity = (List<HashMap<String, Object>>) redisSpringProxy.read(redisKey);
			} else {
				log.info("从数据库中读取团购商品...");
				teamPurchasingCommodity = service.getTeamPurchasingCommodity(commodityId, communityId,
						areaCode);
				redisSpringProxy.save(redisKey, teamPurchasingCommodity);
			}

			if (commodityId != null && commodityId > 0) {
				// 团购商品详情，返回单个商品
				if (teamPurchasingCommodity.size() > 0) {
					jsonMap.put("error", "0");
					jsonMap.put("msg", "成功");
					jsonMap.put("commodity", teamPurchasingCommodity.get(0));
				} else {
					jsonMap.put("error", "3");
					jsonMap.put("msg", "没有该商品，或者活动已结束");
				}
			} else {
				// 团购商品列表
				if (teamPurchasingCommodity.size() > 0) {
					jsonMap.put("error", "0");
					jsonMap.put("msg", "成功");
					jsonMap.put("commodityList", teamPurchasingCommodity);
				} else {
					jsonMap.put("error", "2");
					jsonMap.put("msg", "没有找到团购商品，敬请期待");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
		} finally {
			try {
				JSONUtils.printObject(jsonMap, response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 获取入店必买商品列表
	 * 3.0后改为 业主推荐TOP5
	 *
	 * @param response
	 * @author SevenWong<br>
	 * @date 2016年10月24日上午11:47:24
	 */
	@RequestMapping(value = "/getCommodityMustBuy")
	public void getCommodityMustBuy(@RequestParam String sign, Integer communityId, String areaCode,
									HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<>();
		jsonMap.put("error", "0");
		jsonMap.put("msg", "成功");
		List<Map<String, Object>> mustBuyList = null;

		try {
			if (MD5Tools.checkSign(sign)) {
				areaCode = Tools.getAreaCode(areaCode);
				String redisKey = "commodityMustBuyList_" + communityId + "_" + areaCode;
				if (redisSpringProxy.read(redisKey) != null) {
					log.info("获取入店必买商品列表(redis)");
					mustBuyList = (List<Map<String, Object>>) redisSpringProxy.read(redisKey);
				} else {
					log.info("获取入店必买商品列表(db)");
					mustBuyList = service.getCommoditysMustBuy(communityId, areaCode);
					redisSpringProxy.save(redisKey, mustBuyList);
				}

				// HashMap<String, Object> appConfig = appConfigService.getAll();
				// jsonMap.put("banner", appConfig.get("bannerRecommend"));
			} else {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "签名异常");
			}
		} catch (Exception e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
			e.printStackTrace();
		} finally {
			try {
				jsonMap.put("mustBuyList", mustBuyList);
				JSONUtils.printObject(jsonMap, response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	/**
	 * 新业主推荐
	 * @param sign
	 * @param communityId
	 * @param areaCode
	 * @param response
	 */
	@RequestMapping(value = "/getCommodityMustBuyNew")
	public void getCommodityMustBuyNew(@RequestParam String sign, Integer communityId, String areaCode,
									HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<>();
		jsonMap.put("error", "0");
		jsonMap.put("msg", "成功");
		List<Map<String, Object>> mustBuyList = null;

		try {
			if (MD5Tools.checkSign(sign)) {
				areaCode = Tools.getAreaCode(areaCode);
				String redisKey = "commodityMustBuyListNew_" + communityId + "_" + areaCode;
				if (redisSpringProxy.read(redisKey) != null) {
					log.info("获取入店必买商品列表(redis)");
					mustBuyList = (List<Map<String, Object>>) redisSpringProxy.read(redisKey);
				} else {
					log.info("获取入店必买商品列表(db)");
					mustBuyList = service.getCommodityMustBuy(communityId, areaCode);
					redisSpringProxy.save(redisKey, mustBuyList);
				}

				// HashMap<String, Object> appConfig = appConfigService.getAll();
				// jsonMap.put("banner", appConfig.get("bannerRecommend"));
			} else {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "签名异常");
			}
		} catch (Exception e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
			e.printStackTrace();
		} finally {
			try {
				jsonMap.put("mustBuyList", mustBuyList);
				JSONUtils.printObject(jsonMap, response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}



	/**
	 * 获取限时抢购（今日特价）的商品
	 *
	 * @param sign
	 * @param response
	 * @author SevenWong<br>
	 * @date 2016年10月24日下午5:07:58
	 */
	@RequestMapping(value = "/getBuyLimitCommodity")
	public void getBuyLimitCommodity(@RequestParam String sign, Integer communityId, String areaCode,
									 HttpServletResponse response) {

		HttpUtil.accessControlAllowOrigin(response);

		Map<String, Object> jsonMap = new HashMap<>();
		jsonMap.put("error", "0");
		jsonMap.put("msg", "成功");
		jsonMap.put("now", DateUtil.dateformat(new Date(), "yyyy/MM/dd HH:mm:ss"));

		HajActivity promotionActivity = null;
		List<Map<String, Object>> buyLimitCommodityList;

		try {
			if (MD5Tools.checkSign(sign)) {
				areaCode = Tools.getAreaCode(areaCode);
				String redisKey = "promotionActivityCommodity_" + communityId + "_" + areaCode;
				if (redisSpringProxy.read(redisKey) != null) {
					log.info("获取今日特价的商品(redis)");
					promotionActivity = (HajActivity) redisSpringProxy.read(redisKey);
				} else {
					log.info("获取今日特价的商品(db)");
					promotionActivity = activityService.queryActivityByName("今日特价", areaCode);

					if (promotionActivity != null && 1 == promotionActivity.getStatus()) {
						buyLimitCommodityList = service.getBuyLimitCommodity(communityId, areaCode);
						promotionActivity.setCommodityList(buyLimitCommodityList);
						redisSpringProxy.save(redisKey, promotionActivity);
					}
				}

				// 超过活动时间，将活动商品调为原价（活动价设为0）；结束此活动
				if (promotionActivity != null) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					Date endTime = sdf.parse(promotionActivity.getEndTime());
					Date now = new Date();
					if (now.getTime() > endTime.getTime()) {
						log.info("超过活动时间，将商品从活动中移除，并结束此活动");
						int row = service.updateCommodityClearActivity("今日特价", areaCode);
						if (row > 0) {
							service.resetCommodityRedis();
						}

						// 结束此活动
						HajActivity activity = new HajActivity();
						activity.setId(promotionActivity.getId());
						activity.setStatus(0);
						activityService.updateBySelective(activity);

						redisSpringProxy.delete(redisKey);
					}
				}
			} else {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "签名异常");
			}
		} catch (Exception e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
			e.printStackTrace();
		} finally {
			try {
				jsonMap.put("promotionActivity", promotionActivity);
				JSONUtils.printObject(jsonMap, response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@RequestMapping(value = "/getById")
	public void getById(@RequestParam String sign, @RequestParam Integer commodityId, HttpServletResponse response) {
		HttpUtil.accessControlAllowOrigin(response);

		Map<String, Object> jsonMap = new HashMap<>();
		try {
			if (MD5Tools.checkSign(sign)) {
				Map<String, Object> commodity;

				String redisKey = "commodityById_" + commodityId;

				if (redisSpringProxy.read(redisKey) != null) {
					log.info("根据ID获取商品(redis):" + redisKey);
					commodity = (Map<String, Object>) redisSpringProxy.read(redisKey);
				} else {
					log.info("根据ID获取商品(db):" + redisKey);
					commodity = service.getById(commodityId);
					redisSpringProxy.save(redisKey, commodity);
				}

				jsonMap.put("error", "0");
				jsonMap.put("msg", "成功");
				jsonMap.put("commodity", commodity);
			} else {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "签名异常");
			}
		} catch (Exception e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
			e.printStackTrace();
		} finally {
			try {
				JSONUtils.printObject(jsonMap, response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 根据首页促销专区ID获取改专区下的商品列表
	 *
	 * @param sign
	 * @param promotionAreaId
	 * @param response
	 */
	@RequestMapping(value = "/getCommodityListByPromotionAreaId")
	public void getCommodityByPromotionAreaId(@RequestParam String sign, @RequestParam Integer promotionAreaId,
											  Integer communityId, String areaCode, HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<>();
		jsonMap.put("error", "0");
		jsonMap.put("msg", "成功");

		Criteria criteria = new Criteria();
		Map<String, Object> condition = new HashMap<>();
		condition.put("pageSize", 50);
		condition.put("promotionAreaId", promotionAreaId);
		condition.put("communityId", communityId);
		areaCode = Tools.getAreaCode(areaCode);
		condition.put("areaCode", areaCode);
		criteria.setCondition(condition);

		List<HashMap<String, Object>> commodityList = null;
		try {
			if (MD5Tools.checkSign(sign)) {
				String redisKey = "promotionAreaCommodityList_" + promotionAreaId + "_" + communityId + "_" + areaCode;
				if (redisSpringProxy.read(redisKey) != null) {
					log.info("获取专区商品详情列表(缓存)...");
					commodityList = (List<HashMap<String, Object>>) redisSpringProxy.read(redisKey);
				} else {
					log.info("获取专区商品详情列表(数据库)...");
					commodityList = service.getCommodityByPromotionAreaId(condition);
					redisSpringProxy.save(redisKey, commodityList);
				}
			} else {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "签名异常");
			}
		} catch (Exception e1) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
			e1.printStackTrace();
		} finally {
			jsonMap.put("commodityList", commodityList);
			try {
				JSONUtils.printObject(jsonMap, response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	/**
	 * (分页)推荐专区商品列表 : 根据专区id获取专区商品列表
	 * @param sign
	 * @param promotionAreaId
	 * @param communityId
	 * @param areaCode
	 * @param currentPage
	 * @param response
	 */
	@RequestMapping(value = "/getCommoditysByPromotionAreaId")
	public void getCommoditysByPromotionAreaId(@RequestParam String sign, @RequestParam Integer promotionAreaId,
											  Integer communityId, String areaCode,@RequestParam Integer currentPage, HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<>();
		jsonMap.put("error", "0");
		jsonMap.put("msg", "成功");

		Criteria criteria = new Criteria();
		Map<String, Object> condition = new HashMap<>();
		criteria.setCurrentPage(currentPage);
		condition.put("promotionAreaId", promotionAreaId);
		condition.put("communityId", communityId);
		condition.put("areaCode", Tools.getAreaCode(areaCode));
		criteria.setCondition(condition);

		List<HashMap<String, Object>>  commodityList = null;
		try {
			if (MD5Tools.checkSign(sign)) {
				String redisKey = "commodityListByPromotionArea_" + promotionAreaId + "_" + communityId + "_" + areaCode+ "_" +currentPage ;
				if (redisSpringProxy.read(redisKey) != null) {
					log.info("获取专区商品列表(缓存)...");
					commodityList = (List<HashMap<String, Object>> ) redisSpringProxy.read(redisKey);
				} else {
					log.info("获取专区商品列表(数据库)...");
					commodityList = service.getCommodityListByPromotionAreaId(criteria);
					redisSpringProxy.save(redisKey, commodityList);
				}
			} else {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "签名异常");
			}
		} catch (Exception e1) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
			e1.printStackTrace();
		} finally {
			jsonMap.put("commodityList", commodityList);
			try {
				JSONUtils.printObject(jsonMap, response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}



	/**
	 * 获取商品属性及属性下可售商品的数量（包括新品推荐跟爆品热卖）
	 *
	 * @param response
	 * @param sign
	 * @param communityId
	 * @param areaCode
	 */
	@RequestMapping(value = "/getCommodityAttributes")
	public void getCommodityAttributes(HttpServletResponse response, @RequestParam String sign,
									   @RequestParam Integer communityId, @RequestParam String areaCode) {
		Map<String, Object> jsonMap = new HashMap<>();
		jsonMap.put("error", "0");
		jsonMap.put("msg", "成功");

		List<HashMap<String, Object>> commodityAttrList = null;
		try {
			if (MD5Tools.checkSign(sign)) {
				String redisKey = "commodityAttrList_" + areaCode + "_" + communityId;
				if (redisSpringProxy.read(redisKey) != null) {
					log.info("获取商品属性列表(缓存)...");
					commodityAttrList = (List<HashMap<String, Object>>) redisSpringProxy.read(redisKey);
				} else {
					log.info("获取商品属性列表(数据库)...");
					areaCode = Tools.getAreaCode(areaCode);
					commodityAttrList = service.getCommodityAttrList(communityId, areaCode);

					redisSpringProxy.save(redisKey, commodityAttrList);
				}
			} else {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "签名异常");
			}
		} catch (Exception e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
			e.printStackTrace();
		} finally {
			jsonMap.put("commodityAttrList", commodityAttrList);
			try {
				JSONUtils.printObject(jsonMap, response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@RequestMapping(value = "/listByActivity")
	public void listByActivity(HttpServletResponse response,
							   @RequestParam String sign,
							   String type,
							   Integer activityId,
							   @RequestParam String areaCode) {
		HttpUtil.accessControlAllowOrigin(response);
		Map<String, Object> jsonMap = new HashMap<>();
		jsonMap.put("error", "0");
		jsonMap.put("msg", "成功");

		List<HashMap<String, Object>> commodityList = null;
		try {
			if (MD5Tools.checkSign(sign)) {
				String redisKey = "listByActivity_" + areaCode + "_" + type + "_" + activityId;
				if (redisSpringProxy.read(redisKey) != null) {
					log.info("获取活动商品列表(redis)...");
					commodityList = (List<HashMap<String, Object>>) redisSpringProxy.read(redisKey);
				} else {
					log.info("获取活动商品列表(db)...");
					areaCode = Tools.getAreaCode(areaCode);
					Integer activityType = 0;
					HajActivity activity = new HajActivity();
					if (Tools.isNotEmpty(type)) {
						switch (type) {
							case "new":
								activityType = 3;
								break;
							case "hot":
								activityType = 4;
								break;
						}
						activity.setActivityType(activityType);
					} else if (activityId != null) {
						activity.setId(activityId);
					} else {
						throw new RollbackException("活动类型或活动ID为空");
					}

					activity.setAreaCode(areaCode);
					commodityList = service.listByActivity(activity);
					redisSpringProxy.save(redisKey, commodityList);
				}
			} else {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "签名异常");
			}
		} catch (Exception e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
			log.info(e.getMessage(), e);
		} finally {
			jsonMap.put("commodityList", commodityList);
			try {
				JSONUtils.printObject(jsonMap, response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 根据商品编号查找商品
	 *
	 * @param sign
	 * @param response
	 */
	@RequestMapping(value = "/getCommoidtyByNo")
	public void getCommoidtyByNo(@RequestParam String sign, String commodityNo, HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<>();
		try {
			if (MD5Tools.checkSign(sign)) {
				if (null != commodityNo && !"".equals(commodityNo)) {
					HajCommodity hajCommodity = service.getCommodityByNo(commodityNo);

					if (null != hajCommodity) {
						jsonMap.put("error", "0");
						jsonMap.put("msg", "成功");
						jsonMap.put("HajCommodity", hajCommodity);
					} else {
						jsonMap.put("error", "0");
						jsonMap.put("msg", "为空");
						jsonMap.put("HajCommodity", "");
					}
				} else {
					jsonMap.put("error", "0");
					jsonMap.put("msg", "购物车为空");
					jsonMap.put("HajCommodity", "");
				}
			} else {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "签名异常");
				jsonMap.put("HajCommodity", "");
			}
		} catch (Exception e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
			e.printStackTrace();
		} finally {
			try {
				JSONUtils.printObject(jsonMap, response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 根据商品标签获取商品列表（新品、爆品。。。）
	 */
	@RequestMapping(value = "/getByMark")
	public void getByMark(HttpServletResponse response,
							@RequestParam String sign,
							@RequestParam String mark,
							@RequestParam String areaCode) {
		HttpUtil.accessControlAllowOrigin(response);
		Map<String, Object> jsonMap = new HashMap<>();
		jsonMap.put("error", "0");
		jsonMap.put("msg", "成功");

		List<HashMap<String, Object>> commodityList = null;
		try {
			if (MD5Tools.checkSign(sign)) {
				String redisKey = "getByMark_" + areaCode + "_" + mark;
				if (redisSpringProxy.read(redisKey) != null) {
					log.info("根据商品标签获取商品列表(redis)...");
					commodityList = (List<HashMap<String, Object>>) redisSpringProxy.read(redisKey);
				} else {
					log.info("根据商品标签获取商品列表(db)...");
					areaCode = Tools.getAreaCode(areaCode);

					HajCommodity commodity = new HajCommodity();
					commodity.setMark(mark);
					commodity.setAreaCode(areaCode);
					commodityList = service.getByMark(commodity);
					redisSpringProxy.save(redisKey, commodityList);
				}
			} else {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "签名异常");
			}
		} catch (Exception e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
			log.info(e.getMessage(), e);
		} finally {
			jsonMap.put("commodityList", commodityList);
			try {
				JSONUtils.printObject(jsonMap, response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}



	/**
	 * 根据商品标签获取商品列表（新品、爆品。。。）: 分页处理
	 */
	@RequestMapping(value = "/getByMarkPage")
	public void getByMarkPage(HttpServletResponse response,
						  @RequestParam String sign,
						  @RequestParam String mark,
						  @RequestParam String areaCode, @RequestParam Integer currentPage) {
		HttpUtil.accessControlAllowOrigin(response);
		Map<String, Object> jsonMap = new HashMap<>();
		jsonMap.put("error", "0");
		jsonMap.put("msg", "成功");

		Criteria criteria = new Criteria();
		Map<String, Object> condition = new HashMap<>();
		criteria.setCurrentPage(currentPage);
		condition.put("mark", mark);
		condition.put("areaCode", Tools.getAreaCode(areaCode));
		criteria.setCondition(condition);

		List<HashMap<String, Object>> commodityList = null;
		try {
			if (MD5Tools.checkSign(sign)) {
				String redisKey = "getByMarkPage_" + areaCode + "_" + mark+"_"+currentPage;
				if (redisSpringProxy.read(redisKey) != null) {
					log.info("根据商品标签获取商品列表(redis)...");
					commodityList = (List<HashMap<String, Object>>) redisSpringProxy.read(redisKey);
				} else {
					log.info("根据商品标签获取商品列表(db)...");

					commodityList = service.getByMarkPage(criteria);
					redisSpringProxy.save(redisKey, commodityList);
				}
			} else {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "签名异常");
			}
		} catch (Exception e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
			log.info(e.getMessage(), e);
		} finally {
			jsonMap.put("commodityList", commodityList);
			try {
				JSONUtils.printObject(jsonMap, response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
