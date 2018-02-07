package com.flf.controller.app;

import com.base.criteria.Criteria;
import com.flf.entity.*;
import com.flf.enums.areaType;
import com.flf.service.*;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * <br>
 * <b>功能：</b>HajCategoryThreeAppController<br>
 * <br>
 */ 
@Controller
@RequestMapping(value = "/hajCategoryThree/app")
public class HajCategoryThreeAppController {
	
	private final static Logger log = Logger.getLogger(HajCategoryThreeAppController.class);
	
	@Autowired(required = false)
	private HajCategoryThreeService service;

	@Autowired(required = false)
	private RedisSpringProxy redisSpringProxy;

	@Autowired(required = false)
	private HajPromotionAreaService promotionAreaService;

	@Autowired(required = false)
	private SystemConfigurationService systemConfigurationService;


	/**
	 * 一级类目列表
	 * @param response
	 * @param sign
	 */
	@RequestMapping(value = "/oneList")
	public void oneList(HttpServletResponse response, @RequestParam String sign,String areaCode,Integer communityId) {
		Map<String, Object> jsonMap = new HashMap<>();
		JsonConfig jsonConfig = new JsonConfig();
		try {
			if (MD5Tools.checkSign(sign)) {
				List<HajCategoryThree> oneCateList = new ArrayList<>();
				String redisKeyOne = "oneCategoryList_"+areaCode+"_"+communityId;
				if (null != redisSpringProxy.read(redisKeyOne)) {
					log.info("商品一级类目列表(redis)");
					oneCateList = (List<HajCategoryThree>) redisSpringProxy.read(redisKeyOne);
				} else {
					log.info("商品一级类目列表(db)");
					List<HajCategoryThree>  oneList = service.getOneCategory(0);
					for (HajCategoryThree oneCate:oneList) {
						List<HajCategoryThreeVo> twoAndThreeList = new ArrayList<>();
						List<HajCategoryThreeVo> twoList= service.getTwoCategoryByOneId(oneCate.getId());
						for (HajCategoryThreeVo vo:twoList) {
							//获取二级类目下的三级类目列表
							List<HajCategoryThree> threeCategoryList= service.getThreeCategoryByTwoId(vo.getId());

							//判断该三级类目下是否有商品,没有则不返回该类目
							List<HajCategoryThree> list = new ArrayList<>();
							Criteria criteria = new Criteria();
							Map<String, Object> condition = new HashMap<>();
							condition.put("communityId", communityId);
							condition.put("areaCode", Tools.getAreaCode(areaCode));
							for (HajCategoryThree three:threeCategoryList) {
								condition.put("threeId", three.getId());
								criteria.setCondition(condition);
								//获取商品列表
								List<Map<String, Object>> commodityByThreeList = service.getCategoryListByThreeId(criteria);
								if (commodityByThreeList.size()!=0) {
									list.add(three);
								}
							}
							vo.setThreeList(list);
						}

						//如果二级类目下没有三级类目,则不返回该二级类目
						for(HajCategoryThreeVo two:twoList) {
							if (two.getThreeList().size()!=0) {
								twoAndThreeList.add(two);
							}
						}
						if (twoAndThreeList.size()!=0) {
							oneCateList.add(oneCate);
						}

					}

					redisSpringProxy.save(redisKeyOne, oneCateList);
				}
				jsonMap.put("error", "0");
				jsonMap.put("msg", "成功");
				jsonMap.put("oneList", oneCateList);

				// 过滤掉无用的属性
				jsonConfig.setIgnoreDefaultExcludes(false);
				jsonConfig.setExcludes(new String[]{"areaCode", "createTime", "page","endTime","icon"});
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


	/**
	 * 根据一级类目获取二三级类目列表
	 * @param response
	 * @param sign
	 * @param oneId
	 */
	@RequestMapping(value = "/twoList")
	public void twoList(HttpServletResponse response, @RequestParam String sign,@RequestParam Integer oneId,String areaCode,Integer communityId) {
		Map<String, Object> jsonMap = new HashMap<>();
		JsonConfig jsonConfig = new JsonConfig();

		try {
			if (MD5Tools.checkSign(sign)) {
				List<HajCategoryThreeVo> twoAndThreeList = new ArrayList<>();
				String redisKey = "twoAndThreeCategoryList_"+oneId+"_"+areaCode+"_"+communityId;
				if (null != redisSpringProxy.read(redisKey)) {
					log.info("商品二三级类目列表(redis)");
					twoAndThreeList = (List<HajCategoryThreeVo>) redisSpringProxy.read(redisKey);
				} else {
					log.info("商品二三级类目列表(db)");
					List<HajCategoryThreeVo> twoList= service.getTwoCategoryByOneId(oneId);
					for (HajCategoryThreeVo vo:twoList) {
						//获取二级类目下的三级类目列表
						List<HajCategoryThree> threeCategoryList= service.getThreeCategoryByTwoId(vo.getId());

						//判断该三级类目下是否有商品,没有则不返回该类目
						List<HajCategoryThree> list = new ArrayList<>();
						Criteria criteria = new Criteria();
						Map<String, Object> condition = new HashMap<>();
						condition.put("communityId", communityId);
						condition.put("areaCode", Tools.getAreaCode(areaCode));
						for (HajCategoryThree three:threeCategoryList) {
							condition.put("threeId", three.getId());
							criteria.setCondition(condition);
							//获取商品列表
							List<Map<String, Object>> commodityByThreeList = service.getCategoryListByThreeId(criteria);
							if (commodityByThreeList.size()!=0) {
								list.add(three);
							}
						}
						vo.setThreeList(list);
					}

					//如果二级类目下没有三级类目,则不返回该二级类目
					for(HajCategoryThreeVo two:twoList) {
						if (two.getThreeList().size()!=0) {
							twoAndThreeList.add(two);
						}
					}

					redisSpringProxy.save(redisKey, twoAndThreeList);
				}

				jsonMap.put("error", "0");
				jsonMap.put("msg", "成功");
				jsonMap.put("twoAndThreeList", twoAndThreeList);

				// 获取商品类目对应的促销专区banner图
				Map<String, Object> promotionArea = promotionAreaService.getCategoryBannerByCategory(areaType.PRODUCT_CATEGORY.id, oneId,areaCode);
				jsonMap.put("promotionArea", promotionArea);

				// 过滤掉无用的属性
				jsonConfig.setIgnoreDefaultExcludes(false);
				jsonConfig.setExcludes(new String[]{"areaCode", "createTime", "page","endTime","level","parentId"});
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


	/**
	 * 一元购类目列表
	 * @param response
	 * @param sign
	 */
	@RequestMapping(value = "/oneDollarPurchase")
	public void oneDollarPurchase(HttpServletResponse response, @RequestParam String sign,@RequestParam String areaCode ,Integer communityId) {
		Map<String, Object> jsonMap = new HashMap<>();
		JsonConfig jsonConfig = new JsonConfig();

		try {
			if (MD5Tools.checkSign(sign)) {
				HajCategoryThreeVo oneDollarPurchaseCategory=new HajCategoryThreeVo();
				String redisKey = "oneDollarPurchaseCategory_"+areaCode+"_"+communityId;
				if (null != redisSpringProxy.read(redisKey)) {
					log.info("一元购类目列表(redis)");
					oneDollarPurchaseCategory = (HajCategoryThreeVo) redisSpringProxy.read(redisKey);
				} else {
					log.info("一元购类目列表(db)");
					//根据一元购配置名:"one_dollar_purchase"称获取一元购类目id
					SystemConfiguration config = systemConfigurationService.getConfigurationByName("one_dollar_purchase");
					//返回一元购对象
					HajCategoryThree categoryThree = service.queryById(config.getValue());

					//获取一元购下的所有类目列表
					List<HajCategoryThree> threeCategoryList= service.getThreeCategoryByTwoId(Integer.parseInt(config.getValue()));

					//判断该三级类目下是否有商品,没有则不返回该类目
					List<HajCategoryThree> list = new ArrayList<>();
					Criteria criteria = new Criteria();
					Map<String, Object> condition = new HashMap<>();
					condition.put("communityId", communityId);
					condition.put("areaCode", Tools.getAreaCode(areaCode));

					for (HajCategoryThree three:threeCategoryList) {
						condition.put("threeId", three.getId());
						criteria.setCondition(condition);
						//获取商品列表
						List<Map<String, Object>> commodityByThreeList = service.getCategoryListByThreeId(criteria);
						if (commodityByThreeList.size()!=0) {
							list.add(three);
						}
					}
					oneDollarPurchaseCategory.setThreeList(list);
					oneDollarPurchaseCategory.setId(categoryThree.getId());
					oneDollarPurchaseCategory.setName(categoryThree.getName());
					redisSpringProxy.save(redisKey, oneDollarPurchaseCategory);
				}

				jsonMap.put("error", "0");
				jsonMap.put("msg", "成功");
				jsonMap.put("oneDollarPurchase", oneDollarPurchaseCategory);

				// 过滤掉无用的属性
				jsonConfig.setIgnoreDefaultExcludes(false);
				jsonConfig.setExcludes(new String[]{"createTime", "page","endTime","level","parentId","areaCode"});
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



	/**
	 * 获取三级类目下的商品列表
	 * @param response
	 * @param sign
	 * @param threeId
	 */
	@RequestMapping(value = "/getCommodityList")
	public void getCommodityList(HttpServletResponse response, @RequestParam String sign,@RequestParam Integer threeId,
							  Integer currentPage,Integer communityId, String areaCode) {
		Map<String, Object> jsonMap = new HashMap<>();
		JsonConfig jsonConfig = new JsonConfig();

		try {
			if (MD5Tools.checkSign(sign)) {
				List<Map<String, Object>>  commodityByThreeList;
				String redisKey = "commodityList_"+threeId+"_"+areaCode+"_"+communityId+"_"+currentPage;
				if (null != redisSpringProxy.read(redisKey)) {
					log.info("三级类目下的商品列表(redis)");
					commodityByThreeList = (List<Map<String, Object>>) redisSpringProxy.read(redisKey);
				} else {
					log.info("三级类目下的商品列表(db)");

					Criteria criteria = new Criteria();
					Map<String, Object> condition = new HashMap<>();
					criteria.setCurrentPage(currentPage);
					condition.put("communityId", communityId);
					condition.put("areaCode", Tools.getAreaCode(areaCode));
					condition.put("threeId", threeId);
					criteria.setCondition(condition);

					//获取商品列表
					commodityByThreeList = service.getCategoryListByThreeId(criteria);

					redisSpringProxy.save(redisKey, commodityByThreeList);
				}
				jsonMap.put("error", "0");
				jsonMap.put("msg", "成功");
				jsonMap.put("commodityList", commodityByThreeList);

				// 过滤掉无用的属性
//				jsonConfig.setIgnoreDefaultExcludes(false);
//				jsonConfig.setExcludes(new String[]{"acidDischarge", "createTime", "details","endTime","icon"});
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





}
