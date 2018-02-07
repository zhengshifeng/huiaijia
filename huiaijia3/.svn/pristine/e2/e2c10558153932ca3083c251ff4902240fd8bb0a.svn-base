package com.flf.controller.app;

import com.base.criteria.Criteria;
import com.flf.entity.*;
import com.flf.service.*;
import com.flf.util.*;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * <br>
 * <b>功能：</b>HajOrderAppController<br>
 * <br>
 */
@Controller
@RequestMapping(value = "/hajOrder")
public class HajOrderAppController {

	private final static Logger log = Logger.getLogger(HajOrderAppController.class);
	@Autowired(required = false)
	private HajOrderService hajOrderService;

	@Autowired(required = false)
	private HajFrontUserService hajFrontUserService;

	@Autowired
	private HajTradingHistoryService hajTradingHistoryService;

	@Autowired(required = false)
	private HajCommunityPersionService hajCommunityPersionService;

	@Autowired(required = false)
	private HajCommodityService commodityService;

	@Autowired(required = false)
	private HajIntegralDetailsService integralDetailsService;

	@Autowired
	private RedisSpringProxy redisSpringProxy;

	/**
	 * 获取订单详情接口
	 */
	@RequestMapping(value = "/getOrderInfo")
	public void getOrderInfo(@RequestParam Integer userId, @RequestParam String sign, @RequestParam String createTime,
			String orderNo, HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<>();
		try {
			if (MD5Tools.checkSign(sign)) {
				HajOrder order = hajOrderService.getOrderByCreateTime(userId, createTime, orderNo);
				if (null != order) {
					jsonMap.put("error", "0");
					jsonMap.put("msg", "成功");
					jsonMap.put("HajOrder", order);
					jsonMap.put("orderCancelStatus", hajOrderService.orderCancelStatus(order));
				} else {
					jsonMap.put("error", "0");
					jsonMap.put("msg", "为空");
					jsonMap.put("HajOrder", "");
				}

				Object orderCancelTime = redisSpringProxy.read("SystemConfiguration_orderCancleTime");
				jsonMap.put("orderCancelTime", orderCancelTime);

				JsonConfig jsonConfig = new JsonConfig(); // 建立配置文件
				jsonConfig.setIgnoreDefaultExcludes(false); // 设置默认忽略
				jsonConfig.setExcludes(new String[]{"orderId"});
				JSONUtils.printObject(jsonMap, response, jsonConfig);
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
	 * 添加订单接口，确认下单
	 */
	@RequestMapping(value = "/addOrderapp")
	public void addOrderApp(@RequestParam String orderStr, @RequestParam String sign, HttpServletResponse response) {
		JSONObject jsonObj = JSONObject.fromObject(orderStr);

		Map<String, Class> classMap = new HashMap<>();
		classMap.put("orderDetailList", HajOrderDetails.class);
		// 将JSON转换成Order
		HajOrder order = (HajOrder) JSONObject.toBean(jsonObj, HajOrder.class, classMap);
		String orderNo = null;
		Map<String, Object> jsonMap = new HashMap<>();
		try {
			if (MD5Tools.checkSign(sign)) {
				orderNo = MakeOrderNum.makeOrderNum();
				HajFrontUser user = hajFrontUserService.queryById(order.getUserId());
				String orderdownTime = (String) redisSpringProxy.read("SystemConfiguration_orderdownTime_"
						+ Tools.getAreaCode(user.getAreaCode()));

				if (DateUtil.compare_date(DateUtil.time2Str(new Date()), orderdownTime) == -1) {
					int addStatus = 0;

					// 非补单时判断用户使用的app版本
					if (order.getIsGrouponOrder() != 2) {
						addStatus = checkAppVersion(user, addStatus);
					}

					if (addStatus == 0) {
						// app未传此字段，则默认为0；小程序会传1过来
						order.setApplication(order.getApplication() == null ? 0 : order.getApplication());

						addStatus = hajOrderService.saveHajOrder(order, orderNo);
						log.info("订单号 " + orderNo + " 下单状态：" + addStatus);

						// 扣减库存
						if (addStatus == 0) {
							log.info("【" + orderNo + "】下单成功!进入扣减库存方法中：");
							boolean inventoryIsEnough = commodityService.updateInventoryReduce(order);
							log.info("订单[" + orderNo + "]下单成功！扣减库存结果：" + inventoryIsEnough);
						}
					}

					String msg;
					switch (addStatus) {
					case 0:
						msg = "成功";
						break;
					case 1:
						msg = "新春快乐，19日(年初四)开始营业";
						break;
					case 3:
						msg = "您的账号已暂停使用，如有疑问，请联系小二。";
						break;
					case 4:
						msg = "抱歉，您还不是一元购会员";
						break;
					case 5:
						msg = "订单中包含下架商品";
						break;
					case 6:
						msg = "订单价格已变动或您已下单成功";
						break;
					case 8:
						msg = "您的账号当天已经下过1元购的商品";
						break;
					case 9:
						msg = "商品异常，请清空购物车并重新登录后购买即可";
						break;
					case 10:
						msg = "今日首单蔬菜1元价格异常！";
						break;
					case 11:
						msg = "当前小区已经停止配送！";
						break;
					case 12:
						msg = "当前APP版本过低，请升级后使用！";
						break;
					default:
						msg = "失败";
						break;
					}
					jsonMap.put("error", String.valueOf(addStatus));
					jsonMap.put("msg", msg);

				} else {
					log.info("抱歉，今日已截止下单！");
					jsonMap.put("error", "7");
					jsonMap.put("msg", "抱歉，今日已截止下单！");
				}
				jsonMap.put("orderNo", orderNo);
			} else {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "签名异常");
			}
		} catch (ArithmeticException e) {
			// 此异常为商品库存不足时主动抛出
			// 下架商品的操作需要在此处完成，因为service中抛异常时所有操作都会回滚
			String[] messages = e.getMessage().split(";");
			log.info("订单" + orderNo + "抛异常： " + messages[0]);
			if (messages.length > 1) {
				log.info("库存不足，下架" + messages[1]);
				commodityService.updateSoldOut(messages[1]);
				HajCommodityUtil.resetCommodityRedisAndESIndex(commodityService);
			}
			hajOrderService.deleteOrder(orderNo);
			jsonMap.put("error", "5");
			jsonMap.put("msg", messages[0]);
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

	private int checkAppVersion(HajFrontUser user, int addStatus) {
		boolean legalVersion = false;
		try {
			String appVersionLimit = (String) redisSpringProxy.read("SystemConfiguration_app_version_limit");
			legalVersion = Tools.versionCompare(user.getAppVersion(), appVersionLimit.split("#"));
		} catch (Exception e) {
			log.info("app版本比较发生异常", e);
		}

		if (!legalVersion) {
			log.info("==用户APP版本过低，禁止下单==");
			addStatus = 12;
		}
		return addStatus;
	}

	/**
	 * 查询订单时间app接口(历史订单)<br>
	 *
	 * @param currentPage
	 *            当前页码
	 * @param pageSize
	 *            当前页大小
	 * @param status
	 *            订单状态
	 * @param orderType
	 *            0: 普通订单; 1: 团购订单
	 */
	@RequestMapping(value = "/getOrderTime")
	public void getOrderTime(HttpServletResponse response, @RequestParam String sign,
							 @RequestParam Integer currentPage, @RequestParam Integer pageSize,
							 @RequestParam Integer userId, Integer status, Integer orderType) {
		Map<String, Object> jsonMap = new HashMap<>();
		Criteria criteria = new Criteria();
		criteria.setPageSize(pageSize);
		criteria.setCurrentPage(currentPage);

		Map<String, Object> condition = new HashMap<>();
		condition.put("userId", userId);
		condition.put("status", status);
		condition.put("orderType", orderType);
		criteria.setCondition(condition);
		try {
			if (MD5Tools.checkSign(sign)) {
				List<Map<String, Object>> hajOrderTime = hajOrderService.getOrderTimeByUserId(criteria);

				if (hajOrderTime.size() > 0) {
					jsonMap.put("error", "0");
					jsonMap.put("msg", "成功");
					jsonMap.put("HajOrderTime", hajOrderTime);
					jsonMap.put("page", criteria);
				} else {
					jsonMap.put("error", "0");
					jsonMap.put("msg", "为空");
					jsonMap.put("HajOrderTime", "");
					jsonMap.put("page", criteria);
				}
			} else {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "签名异常");
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
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	@RequestMapping(value = "/getTodayOrderNumber")
	public void getTodayOrderNumber(@RequestParam Integer userId, @RequestParam String sign,
			HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();

		try {
			// 签名认证
			if (MD5Tools.checkSign(sign)) {
				int HajOrderTime = hajOrderService.getTodayOrderNumber(userId);
				jsonMap.put("error", "0");
				jsonMap.put("msg", "成功");
				jsonMap.put("todayOrderNumber", HajOrderTime);
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
	 * 查询用户家人喜好订单数量 来源（家人喜好）
	 *
	 * @userId
	 */
	@RequestMapping(value = "/getOrderBySource")
	public void getOrderBySource(@RequestParam Integer userId, @RequestParam String sign, HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			if (MD5Tools.checkSign(sign)) {
				int orderSourceNumber = hajOrderService.getOrderBySource(userId);

				if (orderSourceNumber > 0) {
					jsonMap.put("error", "0");
					jsonMap.put("msg", "成功");
					jsonMap.put("orderSourceNumber", orderSourceNumber);
				} else {
					jsonMap.put("error", "0");
					jsonMap.put("msg", "为空");
					jsonMap.put("orderSourceNumber", "0");
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
	 * 修改订单为等待配送，支付状态为已支付 orderNo 订单号
	 */
	@RequestMapping(value = "/updateOrderStatus")
	public void updateOrderStatus(@RequestParam String orderNo, @RequestParam String sign, HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<>();
		try {
			if (MD5Tools.checkSign(sign)) {
				HajOrder order = hajOrderService.getOrderByOrderNo(orderNo);
				HajFrontUser user = hajFrontUserService.queryById(order.getUserId());
				String areaCode = Tools.getAreaCode(user.getAreaCode());

				// 后台配置的截止下单时间
				String orderTime = (String) redisSpringProxy.read("SystemConfiguration_orderdownTime_" + areaCode);

				if (DateUtil.compare_date(DateUtil.time2Str(new Date()), orderTime) == -1) {
					// 判断该订单的状态是否需要修改
					if (order.getStatus() == 1) {
						// 判断该用户是否是会员
						if ("3".equals(user.getIsmember()) && Tools.isNotEmpty(user.getAddress())) {
							String residential = order.getResidential();
							HajCommunityPersion hajCommunityPersion = hajCommunityPersionService.getHajCommunityByName(
									residential, user.getAreaCode());
							if (hajCommunityPersion != null) {
								Integer tip = hajCommunityPersion.getTip();
								tip = (tip == null) ? 0 : tip;
								tip = tip + order.getDispensingTip().intValue();
								hajCommunityPersion.setTip(tip);
								hajCommunityPersionService.updateBySelective(hajCommunityPersion);
							}
							// 异步处理
							ExecutorService executor = Executors.newCachedThreadPool();

							Future<Integer> result = executor.submit(new AsynchronousTaskController(
									hajFrontUserService, hajTradingHistoryService, hajOrderService, user, order));
							executor.shutdown();

							log.info("异步处理订单支付用户积分和金额的结果：" + result.isDone() + ",  result.get():" + result.get());
							if (result.get() == 1) {
								// 扣款成功 修改订单状态
								hajOrderService.updateOrderStatus(orderNo);

								// 订单使用余额支付成功后记录积分明细
								int rating = order.getPoints() + Integer.parseInt(order.getDispensingTip().toString())
										+ order.getPostFee().setScale(0, BigDecimal.ROUND_DOWN).intValue();
								this.saveIntegralDetails(user.getId(), rating);

								jsonMap.put("error", "0");
								jsonMap.put("msg", "成功");
								jsonMap.put("orderSourceNumber", order.getId());
							} else {
								jsonMap.put("error", "7");
								jsonMap.put("msg", "1元购订单异常");
							}
						} else {
							jsonMap.put("error", "6");
							jsonMap.put("msg", "您的账号已暂停使用，如有疑问，请联系小二。");
							jsonMap.put("orderSourceNumber", "0");
						}
					} else {
						log.info("订单号：" + orderNo + "状态异常，支付失败");
						jsonMap.put("error", "4");
						jsonMap.put("msg", "订单支付异常，请在待配送中查询订单是否已被支付");
						jsonMap.put("orderSourceNumber", "0");
					}
				} else {
					log.info("当前时间:" + new Date() + "已截至下单");
					jsonMap.put("error", "3");
					jsonMap.put("msg", "抱歉，今日已截止下单！");
					jsonMap.put("orderSourceNumber", "0");
				}
			} else {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "签名异常");
			}
		} catch (ArithmeticException e) {
			// 此异常为商品库存不足时主动抛出

			// 下架商品的操作需要在此处完成，因为service中抛异常时所有操作都会回滚
			String[] messages = e.getMessage().split(";");
			if (messages.length > 1) {
				log.info("库存不足，下架" + messages[1]);
				commodityService.updateSoldOut(messages[1]);
				HajCommodityUtil.resetCommodityRedisAndESIndex(commodityService);
			}

			log.info("订单：" + orderNo + "中，" + messages[0]);
			jsonMap.put("error", "5");
			jsonMap.put("msg", messages[0]);
		} catch (Exception e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
			e.printStackTrace();
		} finally {
			try {
				log.info("更新订单号：" + orderNo + ",error：" + jsonMap.get("error") + ",msg：" + jsonMap.get("msg"));
				JSONUtils.printObject(jsonMap, response);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * 支付成功记录积分明细
	 */
	private void saveIntegralDetails(Integer userId, Integer integral) {
		HajIntegralDetails integralDetails = new HajIntegralDetails();
		integralDetails.setUserId(userId);
		integralDetails.setRemark("订单奖励积分");
		integralDetails.setDetail("+" + integral);
		try {
			integralDetailsService.saveDetail(integralDetails);
		} catch (Exception e) {
			log.info("余额支付成功后记录积分明细异常，useId: " + userId, e);
		}
	}

	/**
	 * 取消订单接口
	 */
	@RequestMapping(value = "/cancleOrder")
	public void cancleOrder(@RequestParam String orderNo, @RequestParam String sign, HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<>();
		try {
			HajOrder order = hajOrderService.getOrderByOrderNo(orderNo);
			// 签名认证
			if (MD5Tools.checkSign(sign)) {
				// 判断订单是否可取消
				int orderCancelStatus = hajOrderService.orderCancelStatus(order);

				if (orderCancelStatus == 1) {
					order = hajOrderService.updateCancelOrder(orderNo);
					if (order.getId() > 0) {
						jsonMap.put("error", "0");
						jsonMap.put("msg", "成功");
						jsonMap.put("orderSourceNumber", order.getId());
						jsonMap.put("orderMoney", order.getActualPayment());
					} else {
						jsonMap.put("error", "0");
						jsonMap.put("msg", "为空");
						jsonMap.put("orderSourceNumber", "0");
					}
				} else {
					jsonMap.put("error", "3");
					jsonMap.put("msg", "抱歉，此订单不支持取消");
				}
			} else {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "签名异常");
			}
		} catch (Exception e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
			log.info(jsonMap.get("msg"), e);
		} finally {
			try {
				JSONUtils.printObject(jsonMap, response);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * 根据userId返回今天已付款的订单数量
	 *
	 * @param sign
	 * @param userId
	 * @param response
	 * @author SevenWong<br>
	 * @date 2016年6月16日下午6:21:03
	 */
	@RequestMapping(value = "/getTodayOrderCountByUserId")
	public void getTodayOrderCountByUserId(@RequestParam String sign, @RequestParam Integer userId,
			HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			// 签名认证
			if (MD5Tools.checkSign(sign)) {
				int orderCount = hajOrderService.getTodayOrderCountByUserId(userId);
				jsonMap.put("error", "0");
				jsonMap.put("msg", "success");
				jsonMap.put("result", orderCount);
			} else {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "签名异常");
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
	 * 等待配送订单接口
	 *
	 * @param userId
	 * @param currentPage
	 * @param pageSize
	 * @param sign
	 * @param response
	 */
	@RequestMapping(value = "/getPayOrderByStatus")
	public void getPayOrderByStatus(@RequestParam Integer userId, Integer currentPage, Integer pageSize,
			@RequestParam String sign, HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		Criteria criteria = new Criteria();
		criteria.setPageSize(pageSize);
		criteria.setCurrentPage(currentPage);

		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("userId", userId);
		criteria.setCondition(condition);
		try {
			// 签名认证
			if (MD5Tools.checkSign(sign)) {
				List<Map<String, Object>> HajPayOrder = hajOrderService.getHajPayOrderByUserId(criteria);

				if (HajPayOrder.size() > 0) {
					jsonMap.put("error", "0");
					jsonMap.put("msg", "成功");
					jsonMap.put("HajPayOrder", HajPayOrder);
					jsonMap.put("page", criteria);
				} else {
					jsonMap.put("error", "0");
					jsonMap.put("msg", "为空");
					jsonMap.put("HajPayOrder", "");
					jsonMap.put("page", criteria);
				}
			} else {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "签名异常");
				jsonMap.put("page", criteria);
			}
		} catch (Exception e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
			jsonMap.put("page", criteria);
			log.info(jsonMap.get("msg"), e);
		} finally {
			try {
				JSONUtils.printObject(jsonMap, response);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

}
