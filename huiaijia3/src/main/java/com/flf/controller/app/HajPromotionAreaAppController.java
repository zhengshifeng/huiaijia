package com.flf.controller.app;

import com.base.criteria.Criteria;
import com.flf.entity.HajCommodity;
import com.flf.entity.HajPromotionArea;
import com.flf.enums.areaType;
import com.flf.service.HajCommodityService;
import com.flf.service.HajPromotionAreaService;
import com.flf.service.RedisSpringProxy;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/hajPromotionArea")
public class HajPromotionAreaAppController {

	private final static Logger log = Logger.getLogger(HajPromotionAreaAppController.class);

	@Autowired(required = false)
	private HajPromotionAreaService service;

	@Autowired(required = false)
	private HajCommodityService commodityService;

	@Autowired(required = false)
	private RedisSpringProxy redisSpringProxy;


	/**
	 * 促销专区商品列表
	 * @param sign
	 * @param version
	 * @param communityId
	 * @param areaCode
	 * @param response
	 */
	@RequestMapping(value = "/promotionAreaList")
	public void promotionAreaList(@RequestParam String sign, String version, Integer communityId,
								  String areaCode, HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<>();
		jsonMap.put("error", "0");
		jsonMap.put("msg", "成功");

		Criteria criteria = new Criteria();
		Map<String, Object> condition = new HashMap<>();
		condition.put("display", 1);
//		condition.put("indexDisplay", 1);//此字段已作废
		areaCode = Tools.getAreaCode(areaCode);
		condition.put("areaCode", areaCode);
		criteria.setCondition(condition);
		criteria.setOrderByClause("sort DESC, id DESC");

		// 促销专区列表
		List<HajPromotionArea> areaList = new ArrayList<>();
		try {
			if (MD5Tools.checkSign(sign)) {
				String redisKey = "promotionAreaList_" + version + "_" + communityId + "_" + areaCode;
				if (redisSpringProxy.read(redisKey) != null) {
					log.info("获取促销专区列表(缓存)...");
					areaList = (List<HajPromotionArea>) redisSpringProxy.read(redisKey);
				} else {
					log.info("获取促销专区列表(数据库)...");

					//返回除品牌专区之外的其他专区
					List<HajPromotionArea>  promotionAreaList = service.queryByOtherList(criteria);

					// 查询促销专区下商品的条件
					Map<String, Object> subCondition = new HashMap<>();

					// 首页专区商品最多只能显示6个
					subCondition.put("pageSize", 6);

					// 每个促销专区下的商品列表
					List<HashMap<String, Object>> commodityByPromotionAreaId;
					for (int i = 0; i < promotionAreaList.size(); i++) {
						subCondition.put("promotionAreaId", promotionAreaList.get(i).getId());
						subCondition.put("areaCode", areaCode);
						commodityByPromotionAreaId = commodityService.getCommodityByPromotionAreaId(subCondition);

						// 由于3.2.0版本首页进行了更改，促销专区banner图片尺寸变大，因此做了如下调整
						// version字段在3.2.0之后才传过来，因此只要version不为空，则视为新版本，使用新的banner图
						if (Tools.isNotEmpty(version)) {
							promotionAreaList.get(i).setBanner(promotionAreaList.get(i).getBanner3_2());
						}
						if (commodityByPromotionAreaId.size()!=0) {
							promotionAreaList.get(i).setCommodityList(commodityByPromotionAreaId);
							areaList.add(promotionAreaList.get(i));
						}
					}
					redisSpringProxy.save(redisKey, areaList);
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
			jsonMap.put("promotionAreaList", areaList);
			try {
				JSONUtils.printObject(jsonMap, response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	/**
	 * 3_2_0版本 : 所有生效的推荐专区和品牌专区及其商品列表
	 * @param sign
	 * @param communityId
	 * @param areaCode
	 * @param response
	 */
	@RequestMapping(value = "/areaList")
	public void areaList(@RequestParam String sign, Integer communityId,
								  String areaCode, HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<>();
		jsonMap.put("error", "0");
		jsonMap.put("msg", "成功");

		Criteria criteria = new Criteria();
		Map<String, Object> condition = new HashMap<>();
		condition.put("display", 1);
		areaCode = Tools.getAreaCode(areaCode);
		condition.put("areaCode", areaCode);
		criteria.setCondition(condition);
		criteria.setOrderByClause("sort, convert(name USING gbk)");

		// 促销专区列表
		List<HajPromotionArea> areaList = new ArrayList<>();
		try {
			if (MD5Tools.checkSign(sign)) {
				String redisKey = "promotionAreaAllList_" + communityId + "_" + areaCode;
				if (redisSpringProxy.read(redisKey) != null) {
					log.info("获取生效促销专区列表(缓存)...");
					areaList = (List<HajPromotionArea>) redisSpringProxy.read(redisKey);
				} else {
				log.info("获取生效促销专区列表(数据库)...");

				//获取所有生效的专区
				List<HajPromotionArea> promotionAreaList =  service.queryByList(criteria);

				// 查询促销专区下商品的条件
				Map<String, Object> subCondition = new HashMap<>();

				// 首页专区商品最多只能显示6个
				subCondition.put("pageSize", 6);

				// 每个促销专区下的商品列表
				List<HashMap<String, Object>> commodityList;
				for (HajPromotionArea area : promotionAreaList) {
					subCondition.put("promotionAreaId", area.getId());
					subCondition.put("areaCode", areaCode);
					subCondition.put("communityId", communityId);
					if (area.getAreaType()==1 || area.getAreaType()==2) {
						commodityList = commodityService.getCommodityByPromotionAreaIdOld(subCondition);
						if (commodityList.size()!=0) {
							area.setCommodityList(commodityList);
							areaList.add(area);
						}
					}
				}
					redisSpringProxy.save(redisKey, areaList);
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
			jsonMap.put("promotionAreaList", areaList);
			try {
				JSONUtils.printObject(jsonMap, response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}





}
