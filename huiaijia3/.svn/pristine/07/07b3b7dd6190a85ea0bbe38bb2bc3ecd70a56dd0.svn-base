package com.flf.controller.app;

import com.flf.entity.HajCommunityPersion;
import com.flf.service.HajCommunityPersionService;
import com.flf.service.RedisSpringProxy;
import com.flf.util.DateUtil;
import com.flf.util.JSONUtils;
import com.flf.util.MD5Tools;
import com.flf.util.Tools;
import com.juhe.WeatherUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

@Controller
@RequestMapping(value = "/systemConfig")
public class HajSystemConfigController {

	private final static Logger log = Logger.getLogger(HajSystemConfigController.class);

	@Autowired
	private RedisSpringProxy redisSpringProxy;

	@Autowired(required = false)
	private HajCommunityPersionService communityPersionService;

	/**
	 * 从后台配置获取小二微信号
	 */
	@RequestMapping(value = "/getServiceWeChatID")
	public void getServiceWeChatID(@RequestParam String sign, String areaCode, Integer communityId,
			HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<>();
		String defaultService; // 默认的售后微信号
		String serviceWeChatID = ""; // 最终返回的微信号
		String afterServiceWechatId = ""; // 最终返回的售后微信号
		String addWeixinDescription = ""; // app添加小二微信的描述文字
		String blockTradingWechatId = ""; // 大宗买卖咨询微信号
		try {
			// 默认的售后微信号，根据areaCode获取，如果没有，默认用深圳的
			areaCode = Tools.getAreaCode(areaCode);
			defaultService = (String) redisSpringProxy.read("SystemConfiguration_weixin_" + areaCode);
			serviceWeChatID = defaultService;
			if (MD5Tools.checkSign(sign)) {
				if (communityId != null && communityId != 0) {
					HajCommunityPersion community = communityPersionService.queryById(communityId);
					if (community != null) {
						if (Tools.isNotEmpty(community.getWxcode())) {
							// 默认读取小区设置的微信号
							serviceWeChatID = community.getWxcode();
						}
						if (Tools.isNotEmpty(community.getAfterServiceWechatId())) {
							// 默认读取小区设置的售后微信号
							afterServiceWechatId = community.getAfterServiceWechatId();
						}

						// 小二微信号
						if (Tools.isEmpty(serviceWeChatID)) {
							// 如果小区中没设置微信号，根据小区所在城市获取小二微信
							serviceWeChatID = (String) redisSpringProxy.read("SystemConfiguration_weixin_"
									+ community.getCommunityCode());

							if (Tools.isEmpty(serviceWeChatID)) {
								// 如果小区没设置城市，读取默认的微信号
								serviceWeChatID = defaultService;
							}
						}

						// 售后微信号
						if (Tools.isEmpty(afterServiceWechatId)) {
							// 如果小区中没设置微信号，根据小区所在城市获取小二微信
							afterServiceWechatId = (String) redisSpringProxy.read("SystemConfiguration_weixin_"
									+ community.getCommunityCode());
							if (Tools.isEmpty(afterServiceWechatId)) {
								// 如果小区没设置城市，读取默认的微信号
								afterServiceWechatId = defaultService;
							}
						}
					}
				}
				addWeixinDescription = (String) redisSpringProxy.read("SystemConfiguration_add_weixin_description");
				blockTradingWechatId = (String) redisSpringProxy.read("SystemConfiguration_block_trading_weixin");
				jsonMap.put("error", "0");
				jsonMap.put("msg", "成功");
			} else {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "签名异常");
			}
		} catch (Exception e) {
			jsonMap.put("error", "2");
			jsonMap.put("msg", "未知异常");
			log.info(e.getMessage(), e);
		} finally {
			try {
				jsonMap.put("result", serviceWeChatID);
				jsonMap.put("afterServiceWechatId", afterServiceWechatId);
				jsonMap.put("description", addWeixinDescription);
				jsonMap.put("blockTradingWechatId", blockTradingWechatId);
				JSONUtils.printObject(jsonMap, response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 根据小区ID获取收取运费的标准<br>
	 * 默认读取后台配置，根据APP传的communityId判断该小区是否需要收取运费，如不收取，则设为0
	 */
	@RequestMapping(value = "/getPostFeeByCommunityId")
	public void getPostFeeByCommunityId(@RequestParam String sign, @RequestParam Integer communityId,
			HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			if (MD5Tools.checkSign(sign)) {
				Map<String, Object> result = new HashMap<>();
				boolean needPostFee = true;

				// 请求后台配置，获取收取运费的标准
				String postFeeStandard = (String) redisSpringProxy.read("SystemConfiguration_post_fee_standard");
				if (Tools.isNotEmpty(postFeeStandard)) {
					String[] arr = postFeeStandard.split("#");
					if (arr.length > 1) {
						result.put("standard", new BigDecimal(arr[0]));
						result.put("postFee", new BigDecimal(arr[1]));
						if (arr.length > 2) {
							result.put("minimumPostFee", new BigDecimal(arr[2]));
						}
					}
				}

				// 根据小区ID判断该小区是否需要收取运费（用户没选小区时传0）
				HajCommunityPersion community = communityPersionService.queryById(communityId);

				if (community != null && community.getNeedPostFee() != null && community.getNeedPostFee() == 0) {
					needPostFee = false;
					result.put("standard", new BigDecimal("0"));
					result.put("postFee", new BigDecimal("0"));
					result.put("minimumPostFee", new BigDecimal("0"));
				}

				result.put("needPostFee", needPostFee);

				jsonMap.put("error", "0");
				jsonMap.put("msg", "成功");
				jsonMap.put("result", result);
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
	 * 根据小区ID获取收取运费的标准<br>
	 * 默认读取后台配置，根据APP传的communityId判断该小区是否需要收取运费，如不收取，则设为0
	 */
	@RequestMapping(value = "/getPostFeeByCommunityId2")
	public void getPostFeeByCommunityId2(@RequestParam String sign, @RequestParam Integer communityId,
			HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<>();
		try {
			if (MD5Tools.checkSign(sign)) {
				List<Map<String, Object>> resultList = new ArrayList<>();
				Map<String, Object> result;
				String postFeeRemark = (String) redisSpringProxy.read("SystemConfiguration_postFeeRemark");

				// 请求后台配置，获取收取运费的标准
				String postFeeStandard = (String) redisSpringProxy.read("SystemConfiguration_post_fee_standard1");
				if (Tools.isNotEmpty(postFeeStandard)) {
					String[] postFees = postFeeStandard.split(";");
					String[] postFees1;
					for (String s : postFees) {
						result = new HashMap<>();
						postFees1 = s.split("#");
						log.info("运费日志最小值：" + postFees1[0] + ",最大值：" + postFees1[1] + ",定值：" + postFees1[2]);
						result.put("minPrice", new BigDecimal(postFees1[0]));
						result.put("maxPrice", new BigDecimal(postFees1[1]));
						result.put("postFee", new BigDecimal(postFees1[2]));
						result.put("needPostFee", true);
						resultList.add(result);
					}
				}

				// 根据小区ID判断该小区是否需要收取运费（用户没选小区时传0）
				HajCommunityPersion community = communityPersionService.queryById(communityId);
				setPostFeeByCommunity(resultList, community);

				jsonMap.put("error", "0");
				jsonMap.put("msg", "成功");
				jsonMap.put("result", resultList);
				jsonMap.put("postFeeRemark", postFeeRemark);
			} else {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "签名异常");
			}
		} catch (Exception e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
			log.info(e.getMessage(),e);
		} finally {
			try {
				JSONUtils.printObject(jsonMap, response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 3.10.1版本后，APP计算运费的标准改为随单收取，且区分城市
	 */
	@RequestMapping(value = "/getPostFeeByCity")
	public void getPostFeeByCity(HttpServletResponse response,
								 @RequestParam String sign,
								 @RequestParam Integer communityId,
								 @RequestParam String areaCode) {
		Map<String, Object> jsonMap = new HashMap<>();
		try {
			if (MD5Tools.checkSign(sign)) {
				List<Map<String, Object>> resultList = new ArrayList<>();
				Map<String, Object> result;
				areaCode = Tools.getAreaCode(areaCode);
				String postFee = (String) redisSpringProxy.read("SystemConfiguration_post_fee_" + areaCode);
				String postFeeRemark = (String) redisSpringProxy.read("SystemConfiguration_post_fee_remark_" + areaCode);

				if (Tools.isNotEmpty(postFee)) {
					String[] postFeeConfig = postFee.split(";");
					String[] postFeeArr;
					for (String s : postFeeConfig) {
						result = new HashMap<>();
						postFeeArr = s.split("#");
						result.put("minPrice", new BigDecimal(postFeeArr[0]));
						result.put("maxPrice", new BigDecimal(postFeeArr[1]));
						result.put("postFee", new BigDecimal(postFeeArr[2]));
						result.put("needPostFee", true);
						resultList.add(result);
					}
				}

				// 根据小区ID判断该小区是否需要收取运费（用户没选小区时传0）
				HajCommunityPersion community = communityPersionService.queryById(communityId);
				setPostFeeByCommunity(resultList, community);

				jsonMap.put("error", "0");
				jsonMap.put("msg", "成功");
				jsonMap.put("result", resultList);
				jsonMap.put("postFeeRemark", postFeeRemark);
			} else {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "签名异常");
			}
		} catch (Exception e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
			log.info(e.getMessage(),e);
		} finally {
			try {
				JSONUtils.printObject(jsonMap, response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void setPostFeeByCommunity(List<Map<String, Object>> resultList, HajCommunityPersion community) {
		Map<String, Object> result;
		if (community != null && community.getNeedPostFee() != null && community.getNeedPostFee() == 0) {
			resultList.clear();
			result = new HashMap<>();
			result.put("minPrice", new BigDecimal("0"));
			result.put("maxPrice", new BigDecimal("0"));
			result.put("postFee", new BigDecimal("0"));
			result.put("needPostFee", false);
			resultList.add(result);
		}
	}

	/**
	 * 获取后台配置参数值
	 */
	@RequestMapping(value = "/getServiceByKey")
	public void getServiceByKey(@RequestParam String sign, String key, HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<>();
		try {
			if (MD5Tools.checkSign(sign)) {
				String value = (String) redisSpringProxy.read("SystemConfiguration_" + key);
				jsonMap.put("error", "0");
				jsonMap.put("msg", "");
				jsonMap.put("result", value);
			} else {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "签名异常");
				jsonMap.put("result", "");
			}
		} catch (Exception e) {
			jsonMap.put("error", "2");
			jsonMap.put("msg", "未知异常");
			jsonMap.put("result", "");
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
	 * 获取后台下单时间
	 */
	@RequestMapping(value = "/getOrderDownTime")
	public void getOrderDownTime(@RequestParam String sign, String areaCode, HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<>();
		try {
			if (MD5Tools.checkSign(sign)) {
				areaCode = Tools.getAreaCode(areaCode);
				String value = (String) redisSpringProxy.read("SystemConfiguration_orderdownTime_" + areaCode);
				jsonMap.put("error", "0");
				jsonMap.put("msg", "");
				jsonMap.put("result", value);
			} else {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "签名异常");
				jsonMap.put("result", "");
			}
		} catch (Exception e) {
			jsonMap.put("error", "2");
			jsonMap.put("msg", "未知异常");
			jsonMap.put("result", "");
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
	 * 获取明日天气数据
	 */
	@RequestMapping(value = "/getTomorrowWeather")
	public void getTomorrowWeather(@RequestParam String sign, String areaCode, HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<>();
		try {
			if (MD5Tools.checkSign(sign)) {
				areaCode = Tools.getAreaCode(areaCode);
				String today = DateUtil.date2Str(new Date(), false);
				String redisKey = "tomorrow_weather_"  + areaCode + "_" + today;

				String weather;
				if (redisSpringProxy.read(redisKey) != null) {
					weather = (String) redisSpringProxy.read(redisKey);
					log.info("获取明日天气数据(redis): " + weather);
				} else {
					String city = Tools.getShortCityByAreaCode(areaCode);
					weather = WeatherUtil.tomorrow(city);

					Long invalidTime = 24L * 60 * 60;
					redisSpringProxy.saveEx(redisKey, invalidTime, weather);
					log.info("获取明日天气数据(http): " + weather);
				}
				jsonMap.put("error", "0");
				jsonMap.put("msg", "成功");
				jsonMap.put("result", weather);
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

}
