package com.flf.controller.app;

import com.base.criteria.Criteria;
import com.flf.entity.HajUserFamily;
import com.flf.service.HajCommodityFailureService;
import com.flf.service.HajUserFamilyService;
import com.flf.service.RedisSpringProxy;
import com.flf.util.JSONUtils;
import com.flf.util.MD5Tools;
import com.flf.util.Tools;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
 * <b>功能：</b>HajUserFamilyAppController<br>
 * <br>
 */
@Controller
@RequestMapping(value = "/hajUserFamily")
public class HajUserFamilyAppController {

	private final static Logger log = Logger.getLogger(HajUserFamilyAppController.class);

	@Autowired(required = false)
	private HajUserFamilyService hajUserFamilyService;

	@Autowired
	private RedisSpringProxy redisSpringProxy;

	@Autowired
	private HajCommodityFailureService hajCommodityFailureService;

	/**
	 * 添加家人
	 * 
	 * @author SevenWong<br>
	 * @date 2016年9月28日下午5:56:46
	 * @param sign
	 * @param userFamily
	 * @param response
	 */
	@RequestMapping(value = "/saveUserFamily", method = RequestMethod.POST)
	public void saveUserFamily(@RequestParam String sign, @ModelAttribute HajUserFamily userFamily,
			HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			if (MD5Tools.checkSign(sign)) {
				if (null != userFamily.getUserId() && null != userFamily.getFamilyId()) {
					hajUserFamilyService.add(userFamily);
					redisSpringProxy.deletePattern("hajUserFamilyList_" + userFamily.getUserId() + "*");
					jsonMap.put("error", "0");
					jsonMap.put("msg", "成功");
					jsonMap.put("userFamily", userFamily);
				} else {
					jsonMap.put("error", "0");
					jsonMap.put("msg", "为空");
					jsonMap.put("userFamily", userFamily);
				}
			} else {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "签名异常");
				jsonMap.put("userFamily", "");
			}
		} catch (Exception e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
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
	 * 更新家人喜好
	 * 
	 * @author SevenWong<br>
	 * @date 2016年9月28日下午5:57:25
	 * @param sign
	 * @param userFamily
	 * @param response
	 */
	@RequestMapping(value = "/updateFamily", method = RequestMethod.POST)
	public void updateUserFamily(@RequestParam String sign, @ModelAttribute HajUserFamily userFamily,
			HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			if (MD5Tools.checkSign(sign)) {
				if (null != userFamily.getId() && userFamily.getId() > 0) {
					hajUserFamilyService.updateBySelective(userFamily);

					redisSpringProxy.deletePattern("hajUserFamilyList_" + userFamily.getUserId() + "*");
					jsonMap.put("error", "0");
					jsonMap.put("msg", "成功");
					jsonMap.put("userFamily", userFamily);
				} else {
					jsonMap.put("error", "3");
					jsonMap.put("msg", "未找到该家人喜好");
					jsonMap.put("userFamily", userFamily);
				}
			} else {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "签名异常");
				jsonMap.put("userFamily", "");
			}
		} catch (Exception e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
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
	 * 删除
	 */
	@RequestMapping(value = "/deleteFamily", method = RequestMethod.GET)
	public void deleteFamily(@RequestParam String sign, @RequestParam int id, HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			if (MD5Tools.checkSign(sign)) {
				if (id > 0) {
					HajUserFamily queryById = hajUserFamilyService.queryById(id);
					if (queryById != null) {
						redisSpringProxy.deletePattern("hajUserFamilyList_" + queryById.getUserId() + "*");
						jsonMap.put("error", "0");
						jsonMap.put("msg", "成功");
						hajUserFamilyService.delete(id);
					} else {
						jsonMap.put("error", "1");
						jsonMap.put("msg", "家人喜好角色不存在");
					}
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
	 * 用户家庭成员查询
	 * 
	 * @author SevenWong<br>
	 * @date 2016年6月14日上午11:08:21 修改
	 * @param sign
	 * @param userId
	 * @param response
	 */
	@RequestMapping(value = "/queryFamily", method = RequestMethod.GET)
	public void queryFamily(@RequestParam String sign, @RequestParam Integer userId, Integer communityId,
			HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("userId", userId);
		Criteria criteria = new Criteria();
		criteria.setCondition(condition);
		criteria.setPageSize(100);
		try {
			if (MD5Tools.checkSign(sign)) {
				if (userId > 0) {
					List<HajUserFamily> hajUserFamilyList = new ArrayList<>();
					String redisKey = "hajUserFamilyList_" + userId + "_" + communityId;
					hajUserFamilyList = (List<HajUserFamily>) redisSpringProxy.read(redisKey);
					log.info("queryFamily()从缓存中读取家人喜好hajUserFamilyList:" + hajUserFamilyList);

					if (hajUserFamilyList == null || hajUserFamilyList.size() == 0) {
						redisSpringProxy.delete(redisKey);
						hajUserFamilyList = hajUserFamilyService.queryByList(criteria);

						String commoditys = "";

						// 为了避免多次读取数据库，从缓存中读取商品列表，供addCommodityStatus()使用
						List<Map<String, Object>> allCommodityList = hajUserFamilyService.getAllCommodityList();

						// 循环查询家人喜好菜品是否上架，并在菜品中做标识
						for (int i = 0, len = hajUserFamilyList.size(); i < len; i++) {

							// 得到家人喜好里面的商品列表
							commoditys = hajUserFamilyList.get(i).getCommoditys();

							// 商品列表不为空才往下进行
							if (Tools.isEmpty(commoditys)) {
								continue;
							}

							// 重新组装家人喜好商品（添加商品上架状态）
							commoditys = addCommodityStatus(commoditys, allCommodityList, communityId);

							hajUserFamilyList.get(i).setCommoditys(commoditys);
						}

						// 写入缓存
						redisSpringProxy.save(redisKey, hajUserFamilyList);
					}
					if (hajUserFamilyList != null && hajUserFamilyList.size() > 0) {
						jsonMap.put("error", "0");
						jsonMap.put("msg", "成功");
						jsonMap.put("hajUserFamilyList", hajUserFamilyList);
					} else {
						jsonMap.put("error", "0");
						jsonMap.put("msg", "为空");
						jsonMap.put("hajUserFamilyList", "");
					}
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
	 * 重新组装家人喜好商品（添加商品上架状态1/0）<br>
	 * 
	 * @author SevenWong<br>
	 * @date 2016年6月14日下午2:19:32
	 * @param commoditys
	 * @param communityId
	 * @return
	 * @throws Exception
	 */
	private String addCommodityStatus(String commoditys, List<Map<String, Object>> allCommodityList,
			Integer communityId) throws Exception {
		StringBuilder returnCommodities = new StringBuilder();
		String[] commodities = commoditys.split(";");

		/**
		 * 该小区已屏蔽的商品列表
		 */
		List<Integer> commodityIdsByComnunityId = new ArrayList<>();
		if (communityId != null && communityId > 0) {
			commodityIdsByComnunityId = hajCommodityFailureService.getCommodityIdsByComnunityId(communityId);
		}

		String commodityId;

		for (int i = 0, len = commodities.length; i < len; i++) {
			for (Map<String, Object> map : allCommodityList) {
				commodityId = map.get("id").toString();
				if (commodities[i].split(",")[0].equals(commodityId)) {
					// 重新组装，然后返回
					returnCommodities.append(commodityId).append(",");
					returnCommodities.append(map.get("name")).append(",");

					// 已屏蔽的商品设为下架
					if (commodityIdsByComnunityId.contains(Integer.parseInt(commodityId))) {
						returnCommodities.append(0);
					} else {
						returnCommodities.append(map.get("shelves") == null ? 0 : map.get("shelves"));
					}

					if (i < len - 1) {
						returnCommodities.append(";");
					}
					break;
				}
			}
		}
		return returnCommodities.toString();
	}

	/**
	 * 根据商品名称返回该商品的详细信息
	 * 
	 * @author SevenWong<br>
	 * @date 2016年9月30日上午10:49:23
	 * @param sign
	 * @param name
	 * @param response
	 */
	@RequestMapping(value = "/queryPrice")
	public void queryPriceByName(@RequestParam String sign, @RequestParam String name, HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			if (MD5Tools.checkSign(sign)) {
				if (name != null && !"".equals(name)) {
					Map<String, Object> goods = hajUserFamilyService.queryPriceByName(name);
					jsonMap.put("error", "0");
					jsonMap.put("msg", "成功");
					jsonMap.put("goods", goods);
				} else {
					jsonMap.put("error", "1");
					jsonMap.put("msg", "商品名称为空");
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
			try {
				JSONUtils.printObject(jsonMap, response);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

}
