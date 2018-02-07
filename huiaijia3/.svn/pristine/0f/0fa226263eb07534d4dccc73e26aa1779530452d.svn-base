package com.flf.controller.app;

import com.base.criteria.Criteria;
import com.flf.entity.HajCommodityType;
import com.flf.service.HajCommodityTypeService;
import com.flf.service.HajPromotionAreaService;
import com.flf.service.RedisSpringProxy;
import com.flf.util.JSONUtils;
import com.flf.util.MD5Tools;
import com.flf.util.Tools;
import net.sf.json.JsonConfig;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <br>
 * <b>功能：</b>HajCommodityTypeAppController<br>
 * <br>
 */
@Controller
@RequestMapping(value = "/hajCommodityType")
public class HajCommodityTypeAppController {

	private final static Logger log = Logger.getLogger(HajCommodityTypeAppController.class);

	@Autowired(required = false)
	private HajCommodityTypeService service;

	@Autowired(required = false)
	private HajPromotionAreaService promotionAreaService;

	@Autowired
	private RedisSpringProxy redisSpringProxy;

	/**
	 * 获取商品类型列表页面
	 *
	 * @param currentPage
	 */
	@RequestMapping(value = "/getCommodityType")
	public void getCommodityTypeApp(HttpServletResponse response,
									@RequestParam String sign,
									@RequestParam Integer currentPage,
									String commodityAttr,
									String areaCode,
									Integer communityId) {
		Map<String, Object> jsonMap = new HashMap<>();
		Map<String, Object> condition = new HashMap<>();
		condition.put("parentId", "0");
		condition.put("display", 1);

		if ("fruits".equals(commodityAttr)) {
			commodityAttr = "水果";
		} else if ("breakfast".equals(commodityAttr)) {
			commodityAttr = "早餐";
		} else {
			commodityAttr = "生鲜";
		}

		condition.put("commodityAttr", commodityAttr);
		Criteria criteria = new Criteria();
		criteria.setCurrentPage(currentPage);
		criteria.setCondition(condition);

		List<HajCommodityType> commodityTypeList = null;
		areaCode = Tools.getAreaCode(areaCode);
		try {
			if (MD5Tools.checkSign(sign)) {
				String redisKey = "hajCommodityType_" + commodityAttr + "_" + currentPage +
						"_" + areaCode + "_" + communityId;
				if (null != redisSpringProxy.read(redisKey)) {
					log.info("获取商品分类(redis):" + redisKey);
					commodityTypeList = (List<HajCommodityType>) redisSpringProxy.read(redisKey);
				} else {
					log.info("获取商品分类(db):" + redisKey);
					commodityTypeList = service.queryByList(criteria);
					typePacking4app(areaCode, communityId, commodityTypeList);

					if (commodityTypeList.size() > 0) {
						redisSpringProxy.save(redisKey, commodityTypeList);
					}
				}

				if (commodityTypeList.size() > 0) {
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
				jsonMap.put("HajCommodityType", commodityTypeList);
				jsonMap.put("page", criteria);
				JSONUtils.printObject(jsonMap, response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 获取父级商品分类
	 *
	 * @param sign
	 * @param response
	 */
	@RequestMapping(value = "/getParentType")
	public void getParentType(@RequestParam String sign, HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("parentId", "0");
		condition.put("display", 1);
		Criteria criteria = new Criteria();
		criteria.setCondition(condition);
		try {
			if (MD5Tools.checkSign(sign)) {
				List<HajCommodityType> hajCommodityParentType = null;
				if (null != redisSpringProxy.read("hajCommodityParentType")) {
					log.info("进入获取商品父分类缓存中");
					hajCommodityParentType = (List<HajCommodityType>) redisSpringProxy.read("hajCommodityParentType");
				} else {
					log.info("进入获取商品父分类方法中");
					hajCommodityParentType = service.queryByList(criteria);
					redisSpringProxy.save("hajCommodityParentType", hajCommodityParentType);
				}

				JsonConfig jsonConfig = new JsonConfig(); // 建立配置文件
				jsonConfig.setIgnoreDefaultExcludes(false); // 设置默认忽略

				// 此处是亮点，只要将所需忽略字段加到数组中即可，在实际测试中，我发现在所返回数组中，存在大量无用属性
				// 如“ratePersonals”，“channelPersonals”，那么也可以将这两个加到忽略数组中.
				jsonConfig.setExcludes(new String[]{"remark1", "remark2", "remark3", "parentId", "children"});

				if (hajCommodityParentType.size() > 0) {
					jsonMap.put("error", "0");
					jsonMap.put("msg", "成功");
					jsonMap.put("HajCommodityType", hajCommodityParentType);
					JSONUtils.printObject(jsonMap, response, jsonConfig);
				} else {
					jsonMap.put("error", "0");
					jsonMap.put("msg", "为空");
					jsonMap.put("HajCommodityType", "");
					JSONUtils.printObject(jsonMap, response);
				}
			} else {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "签名异常");
				jsonMap.put("HajCommodityType", "");
				JSONUtils.printObject(jsonMap, response);
			}
		} catch (Exception e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
			jsonMap.put("page", criteria);
			try {
				JSONUtils.printObject(jsonMap, response);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			log.info(e.getMessage(), e);
		}
	}

	/**
	 * 商品分类查商品所有集合
	 */
	@RequestMapping(value = "/getCommodityTypeByPId")
	public void getCommodityTypeByPId(@RequestParam String sign, @RequestParam Integer parentId, Integer communityId,
									  HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("parentId", parentId);
		condition.put("display", 1);
		Criteria criteria = new Criteria();
		criteria.setCondition(condition);
		try {
			if (MD5Tools.checkSign(sign)) {
				List<HajCommodityType> hajCommodityType = null;

				String redisKey = "CommodityTypeByPId_" + parentId + "_" + communityId;
				if (null != redisSpringProxy.read(redisKey)) {
					log.info("进入商品分类查商品所有集合缓存中");
					hajCommodityType = (List<HajCommodityType>) redisSpringProxy.read(redisKey);
				} else {
					hajCommodityType = service.queryByList(criteria);
					// 根据父id查找子集合
					for (HajCommodityType type : hajCommodityType) {
						List<HajCommodityType> sonList = service.getCommodityTypeId(type.getId(), communityId);
						type.setChildren(sonList);
					}
					redisSpringProxy.save(redisKey, hajCommodityType);
				}

				JsonConfig jsonConfig = new JsonConfig(); // 建立配置文件
				jsonConfig.setIgnoreDefaultExcludes(false); // 设置默认忽略

				// 此处是亮点，只要将所需忽略字段加到数组中即可，在实际测试中，我发现在所返回数组中，存在大量无用属性，如“ratePersonals”，“channelPersonals”，那么也可以将这两个加到忽略数组中.
				jsonConfig.setExcludes(new String[]{"remark1", "remark2", "remark3", "parentId"});

				if (hajCommodityType.size() > 0) {
					jsonMap.put("error", "0");
					jsonMap.put("msg", "成功");
					jsonMap.put("HajCommodityType", hajCommodityType);
					jsonMap.put("page", criteria);
					JSONUtils.printObject(jsonMap, response, jsonConfig);
				} else {
					jsonMap.put("error", "0");
					jsonMap.put("msg", "为空");
					jsonMap.put("HajCommodityType", "");
					jsonMap.put("page", criteria);
					JSONUtils.printObject(jsonMap, response);
				}
			} else {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "签名异常");
				jsonMap.put("HajCommodityType", "");
				jsonMap.put("page", criteria);
				JSONUtils.printObject(jsonMap, response);
			}
		} catch (Exception e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
			jsonMap.put("page", criteria);
			try {
				JSONUtils.printObject(jsonMap, response);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			log.info(e.getMessage(), e);
		}
	}

	/**
	 * 根据类目ID获取商品分类列表
	 *
	 * @param sign
	 * @param categoryId
	 * @param response
	 */
	@RequestMapping(value = "/getByCategoryId")
	public void getByCategoryId(HttpServletResponse response,
								@RequestParam String sign,
								@RequestParam Integer categoryId,
								String areaCode,
								Integer communityId) {
		Map<String, Object> jsonMap = new HashMap<>();
		JsonConfig jsonConfig = new JsonConfig();
		try {
			if (MD5Tools.checkSign(sign)) {
				areaCode = Tools.getAreaCode(areaCode);
				String redisKey = "commodityTypeByCategoryId_" + categoryId + "_" + areaCode + "_" + communityId;
				List<HajCommodityType> commodityTypeList;
				if (null != redisSpringProxy.read(redisKey)) {
					log.info("根据类目ID获取商品分类列表(redis)");
					commodityTypeList = (List<HajCommodityType>) redisSpringProxy.read(redisKey);
				} else {
					log.info("根据类目ID获取商品分类列表(db)");
					commodityTypeList = service.getByCategoryId(categoryId);
					typePacking4app(areaCode, communityId, commodityTypeList);

					if (commodityTypeList.size() > 0) {
						redisSpringProxy.save(redisKey, commodityTypeList);
					}
				}
				jsonMap.put("error", "0");
				jsonMap.put("msg", "成功");
				jsonMap.put("commodityTypeList", commodityTypeList);

				// 获取商品类目对应的促销专区banner图
				Map<String, Object> promotionArea = promotionAreaService.getBannerByCategory(categoryId, areaCode);
				jsonMap.put("promotionArea", promotionArea);

				// 过滤掉无用的属性
				jsonConfig.setIgnoreDefaultExcludes(false);
				jsonConfig.setExcludes(new String[]{"areaCode", "display", "remark1", "remark2", "remark3"});
			} else {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "签名异常");
			}
		} catch (Exception e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
			log.info(e.getMessage(), e);
		} finally {
			try {
				JSONUtils.printObject(jsonMap, response, jsonConfig);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void typePacking4app(String areaCode, Integer communityId,
								 List<HajCommodityType> commodityTypeList) throws Exception {
		HajCommodityType parentType;
		List<HajCommodityType> sonList;
		for (int i = 0; i < commodityTypeList.size(); i++) {
			parentType = commodityTypeList.get(i);
			parentType.setAreaCode(areaCode);

			// 临时借用，保存小区ID，用于查询，后面会删
			parentType.setRemark3(String.valueOf(communityId));

			// 根据父id查找子集合
			sonList = service.getSonCommodityTypeList(parentType);

			// 之前说好的临时借用，现在还给你
			parentType.setRemark3(null);

			if (sonList.size() > 0) {
				parentType.setChildren(sonList);
			} else {
				// 当子类列表为空时，父类也不返回
				commodityTypeList.remove(i--);
			}
		}
	}
}
