package com.flf.controller.app;

import com.flf.entity.HajCouponInfo;
import com.flf.service.HajCouponInfoService;
import com.flf.service.HajCouponUserService;
import com.flf.util.JSONUtils;
import com.flf.util.MD5Tools;
import net.sf.json.JsonConfig;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * <br>
 * <b>功能：</b>HajCouponUserAppController<br>
 * <br>
 */
@Controller
@RequestMapping(value = "hajCouponUserApp")
public class HajCouponUserAppController {

	private final static Logger log = Logger.getLogger(HajCouponUserAppController.class);

	@Autowired(required = false)
	private HajCouponUserService service;

	@Autowired(required = false)
	private HajCouponInfoService couponInfoService;

	/**
	 * 获取用户优惠券列表
	 * 
	 * @param userId
	 * @param sign
	 * @param response
	 */
	@RequestMapping(value = "/getCouponByUserId")
	@ResponseBody
	public void getCouponByUserId(@RequestParam String sign, @RequestParam Integer userId, HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<>();
		try {
			if (MD5Tools.checkSign(sign)) {
				List<Map<String, Object>> couponList = service.getCouponListByUserId(userId);
				if (null != couponList && couponList.size() > 0) {
					jsonMap.put("error", "0");
					jsonMap.put("couponList", couponList);
					jsonMap.put("msg", "成功");
				} else {
					jsonMap.put("error", "0");
					jsonMap.put("msg", "失败");
					jsonMap.put("couponList", "");
				}

				JSONUtils.printObject(jsonMap, response);
			} else {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "签名异常");
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
			e.printStackTrace();
		}
	}

	/**
	 * 获取用户优惠券列表
	 * 
	 * @param userId
	 * @param commodityNos
	 * @param sign
	 * @param response
	 */
	@RequestMapping(value = "/getCouponByCommodityNos")
	@ResponseBody
	public void getCouponByCommodityNos(@RequestParam String sign, @RequestParam Integer userId,
			@RequestParam String commodityNos, HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<>();
		try {
			if (MD5Tools.checkSign(sign)) {
				// 先知道用户有哪些有效的优惠券
				List<Map<String, Object>> couponList = service.getCouponListByCommodity(userId, commodityNos);
				if (couponList.size() > 0) {
					// 根据用户拥有有效的优惠券去查询订单中的商品适用哪些优惠券
					List<Map<String, Object>> commodityList;
					for (Map<String, Object> obj : couponList) {
						// 查询每个优惠券适用哪些商品，一并返回
						commodityList = service.getCouponCommodityById(obj.get("couponId"), commodityNos);
						obj.put("commodityList", commodityList);
					}
					jsonMap.put("error", "0");
					jsonMap.put("couponList", couponList);
					jsonMap.put("msg", "成功");
				} else {
					jsonMap.put("error", "3");
					jsonMap.put("msg", "失败");
					jsonMap.put("couponList", "");
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

	/**
	 * 用户领取优惠券
	 * 
	 * @param userId
	 * @param sign
	 * @param couponNo
	 * @param response
	 */
	@RequestMapping(value = "/saveUserCoupon")
	public void saveUserCoupon(@RequestParam String sign, @RequestParam int userId, @RequestParam String couponNo,
			HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<>();
		try {
			if (MD5Tools.checkSign(sign)) {
				HajCouponInfo couponInfo = couponInfoService.getCouponInfoByNo(couponNo);
				jsonMap = service.saveUserCoupon(userId, couponInfo, jsonMap);
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
	 * 将用户弹出过的优惠券设为已读
	 * 
	 * @param userId
	 * @param sign
	 * @param response
	 */
	@RequestMapping(value = "/updateUserCouponRead")
	public void updateUserCouponRead(@RequestParam String sign, @RequestParam int userId, HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<>();
		try {
			if (MD5Tools.checkSign(sign)) {
				service.updateUserCouponRead(userId);
				jsonMap.put("error", "0");
				jsonMap.put("msg", "更新成功");
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
	 * 获取新用户优惠券<br />
	 * 需求: 4、用户登录后为一元购会员，并没有订单记录【即无历史订单】则为新用户。
	 * 			新用户登录后选择已开通且配送正常的小区才弹出新人优惠券，并自动领取。<br />
	 * 		5、新人优惠券张数不限，有后台设置优惠券id进行领取。不同城市设置的优惠券ID不同。
	 */
	@RequestMapping(value = "/getNewUserCoupon")
	public void getNewUserCoupon(@RequestParam String sign, @RequestParam int userId, HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<>();
		try {
			if (MD5Tools.checkSign(sign)) {
				List<String> newUserCoupons = service.getNewUserCoupon(userId);
				if (newUserCoupons != null && newUserCoupons.size() > 0) {
					List<HajCouponInfo> couponInfoList = couponInfoService.listByCouponIds(newUserCoupons);
					if (couponInfoList.size() > 0) {
						List<Integer> couponIds = new ArrayList<>();
						for (HajCouponInfo couponInfo : couponInfoList) {
							jsonMap = service.saveUserCoupon(userId, couponInfo, jsonMap);
							if (jsonMap.get("error").equals("0")) {
								couponIds.add(couponInfo.getId());
							} else {
								log.info(jsonMap.get("msg"));
							}
						}

						if (couponIds.size() > 0) {
							Map<String, Object> params = new HashMap<>();
							params.put("userId", userId);
							params.put("couponIds", couponIds);

							List<Map<String, Object>> returnCoupons = service.listCouponsByIds(params);

							jsonMap.put("error", "0");
							jsonMap.put("msg", "成功");
							jsonMap.put("couponList", returnCoupons);
						} else {
							jsonMap.put("error", "3");
							jsonMap.put("msg", "无可领取优惠券");
						}
					} else {
						jsonMap.put("error", "3");
						jsonMap.put("msg", "无可领取优惠券");
					}
				} else {
					jsonMap.put("error", "3");
					jsonMap.put("msg", "无可领取优惠券");
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
				JsonConfig jsonConfig = new JsonConfig(); // 建立配置文件
				jsonConfig.setIgnoreDefaultExcludes(false); // 设置默认忽略

				// 此处是亮点，只要将所需忽略字段加到数组中即可，在实际测试中，我发现在所返回数组中，存在大量无用属性
				// 如“ratePersonals”，“channelPersonals”，那么也可以将这两个加到忽略数组中.
				jsonConfig.setExcludes(new String[]{"couponInfo"});
				JSONUtils.printObject(jsonMap, response, jsonConfig);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
