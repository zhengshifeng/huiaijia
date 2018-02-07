package com.flf.service.impl;

import com.alipay.util.AlipayUtil;
import com.base.criteria.Criteria;
import com.base.service.BaseServiceImpl;
import com.flf.entity.*;
import com.flf.mapper.HajOrderDetailsMapper;
import com.flf.mapper.HajOrderMapper;
import com.flf.resolver.RollbackException;
import com.flf.service.*;
import com.flf.util.*;
import com.weixin.PayUtil;
import com.weixin.xcx.XcxPayUtil;
import com.wms.API;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.*;

import static com.flf.util.ExcelUtil.getCellValue;

/**
 * <br>
 * <b>功能：</b>HajOrderService<br>
 */
@Service("hajOrderService")
public class HajOrderServiceImpl extends BaseServiceImpl implements HajOrderService {
	private final static Logger log = Logger.getLogger(HajOrderServiceImpl.class);

	@Autowired
	private HajOrderMapper dao;

	@Autowired
	private HajCommodityService commodityService;

	@Autowired(required = false)
	private HajOrderDetailsService hajOrderDetailsService;

	@Autowired
	private HajOrderDetailsMapper orderDetailsMapper;

	@Autowired
	private RedisSpringProxy redisSpringProxy;

	@Autowired(required = false)
	private HajFrontUserService hajFrontUserService;

	@Autowired(required = false)
	private HajCommunityPersionService hajCommunityPersionService;

	@Autowired
	private HajTradingHistoryService hajTradingHistoryService;

	@Autowired(required = false)
	private HajOrderPaymentService orderPaymentService;

	@Autowired(required = false)
	private HajRechargeService hajRechargeService;

	@Autowired(required = false)
	private HajCouponUserService couponUserService;

	@Autowired(required = false)
	private HajIntegralDetailsService integralDetailsService;

	@Autowired(required = false)
	private HajOrderProblemService orderProblemService;

	@Override
	public HajOrderMapper getDao() {
		return dao;
	}

	@Override
	public List<HajOrder> getOrderByUserId(Integer userId, Criteria criteria, String createTime) {
		try {
			// return dao.getOrderbyUserId(userId);
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("userId", userId);
			condition.put("createTime", createTime);
			criteria.setCondition(condition);
			return queryByList(criteria);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Map<String, Object>> getOrderTimeByUserId(Criteria criteria) throws Exception {
		try {
			List<Map<String, Object>> list = dao.getOrderTimeByUserId(criteria);
			for (Map<String, Object> map : list) {
				if (String.valueOf(map.get("status")).equals("6")) {
					map.put("refund", map.get("actualPayment"));
				}
				map.put("statusText", HajOrderUtil.getOrderStatus(String.valueOf(map.get("status"))));
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取订单来源为家人喜好订单数量
	 */
	@Override
	public int getOrderBySource(Integer userId) {
		try {
			return dao.getOrderBySource(userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 根据订单号查询订单对象
	 */
	@Override
	public HajOrder getOrderByOrderNo(String orderNo) {
		try {
			return dao.getOrderByOrderNo(orderNo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取商品成本价格
	 */
	@Override
	public Map<String, Object> getCommodityCost(List<HajOrderDetails> orderDetailList, int isGrouponOrder,
												String cityCode) throws Exception {
		DecimalFormat df = new DecimalFormat("#.##");
		Map<String, Object> map = new HashMap<>();

		// 订单详情总价格
		BigDecimal orderTotalActualPayment = BigDecimal.ZERO;
		int source = 2;
		double orderCost = 0.00;
		boolean isfirstOrder = true;
		boolean isfruits = false;
		boolean isShelves = false;
		boolean hasInvalidCommodities = false;
		boolean isCityCode = false;
		boolean isPromotionPrice = false;
		String fruits = (String) redisSpringProxy.read("SystemConfiguration_fruits");
		try {
			Map<String, Object> costPriceMap;
			BigDecimal actualPayment;
			BigDecimal promotionPrice;
			BigDecimal originalPrice;

			// 用户订单中的商品单价
			BigDecimal unitPrice;
			for (HajOrderDetails details : orderDetailList) {
				costPriceMap = commodityService.getCommodityByName(details.getCommodityName().trim(), cityCode);
				if (null != costPriceMap && costPriceMap.size() > 0) {
					/* 如果是后台补单，并且补单原因是“仓库缺货”，则不计入成本 */
					if (isGrouponOrder == 2 && "1".equals(details.getRemark3())) {
						// details.setRemark3(null); // 此字段给后台补单“补单原因”临时使用，用完即删
						details.setTotalMoney(BigDecimal.ZERO);
					} else {
						orderCost += Double.parseDouble(costPriceMap.get("costPrice").toString()) * details.getNumber();
						details.setTotalMoney(BigDecimal.valueOf(Double.parseDouble(costPriceMap.get("costPrice")
								.toString())));
					}

					// 如果家人喜好占比数量大于0
					if (Integer.parseInt(details.getSource()) > 0) {
						source = 1;
					}

					actualPayment = new BigDecimal(df.format(details.getActualPayment()));

					if (actualPayment.doubleValue() / details.getNumber() == 1.00) {
						isfirstOrder = false;
					}
					// 如果包含1元购商品
					if (Double.parseDouble(costPriceMap.get("promotionPrice").toString()) == 1.00) {
						isPromotionPrice = true;
					}

					if (costPriceMap.get("commodityAttr").equals(fruits)) {
						isfruits = true;
					}

					// 有下架商品
					if ("0".equals(costPriceMap.get("shelves").toString())) {
						isShelves = true;
					}
					orderTotalActualPayment = orderTotalActualPayment.add(actualPayment);

					unitPrice = actualPayment.divide(new BigDecimal(details.getNumber()), 2, BigDecimal.ROUND_HALF_UP);
					originalPrice = new BigDecimal(costPriceMap.get("originalPrice").toString());
					promotionPrice = new BigDecimal(costPriceMap.get("promotionPrice").toString());
					details.setCommodityNo(costPriceMap.get("commodityNo").toString());

					// 当单价跟原价、促销价都不相等时，说明价格有变动（后台补单除外）
					if (isGrouponOrder != 2) {
						if (unitPrice.compareTo(originalPrice) != 0 && //
								unitPrice.compareTo(promotionPrice) != 0) {
							// 单价跟原价、促销价都不相等时
							hasInvalidCommodities = true;
						} else if (unitPrice.compareTo(BigDecimal.ZERO) <= 0 || //
								actualPayment.compareTo(BigDecimal.ZERO) <= 0) {
							// 单价或者商品实付小于等于0时
							hasInvalidCommodities = true;
						}
					}
					map.put("orderCost", df.format(orderCost));
					map.put("source", source);
					map.put("isfirstOrder", isfirstOrder);
					map.put("isfruits", isfruits);
					map.put("isShelves", isShelves);
					map.put("hasInvalidCommodities", hasInvalidCommodities);
					map.put("orderTotalActualPayment", orderTotalActualPayment);
					map.put("isPromotionPrice", isPromotionPrice);
				} else {
					isCityCode = true;
					isShelves = true;
				}
				map.put("isCityCode", isCityCode);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return map;
	}

	@Override
	public List<Map<String, Object>> listAllOrder(OrderVO orderVo) throws Exception {
		if (orderVo.getResidentialList() != null && orderVo.getResidentialList().length < 1) {
			orderVo.setResidentialList(null);
		}
		return dao.listPageOrder(orderVo);
	}

	@Override
	public void deleteOrderById(Integer orderId) throws Exception {
		dao.deleteOrderById(orderId);
	}

	@Override
	public HajOrder updateOrderStatus(String orderNo) {
		dao.updateOrderStatus(orderNo, 0);
		return dao.getOrderByOrderNo(orderNo);
	}

	@Override
	public Map<String, Object> getOrderInfoById(int orderId) {
		return dao.getOrderInfoById(orderId);
	}

	/**
	 * 查询当天订单
	 */
	@Override
	public List<Map<String, Object>> getOrderByDate(String nowDate) {
		return dao.getOrderByDate(nowDate);
	}

	@Override
	public int saveHajOrder(HajOrder order, String orderNo) {
		// 下单后返回的订单ID
		int orderId = 0;
		try {
			HajFrontUser user = hajFrontUserService.queryById(order.getUserId());
			if (user == null || Tools.isEmpty(user.getAddress())) {
				log.info("==用户地址信息为空，禁止下单==");
				return 3;
			}

			// 判断该小区是否允许下单的标记
			boolean allowThisCommunity2MakeOrder = true;
			String close_order_residential = (String) redisSpringProxy
					.read("SystemConfiguration_close_order_residential");
			String[] residentials = close_order_residential.split(",");

			for (String residential : residentials) {
				if (user.getVillageId().toString().equals(residential)) {
					allowThisCommunity2MakeOrder = false;
				}
			}
			// 判断后台是否开启下单开关
			String allowMakeOrder = (String) redisSpringProxy.read("SystemConfiguration_close_order");
			boolean allowMakeOrderFlag = Boolean.parseBoolean(allowMakeOrder);
			if (allowThisCommunity2MakeOrder && allowMakeOrderFlag) {
				if (!"3".equals(user.getIsmember())) {
					log.info(order.getUserId() + " 该用户不是一元购会员，禁止下单");
					return 4;
				}

				// 新增小区的配送状态是否为停止配送限制
				HajCommunityPersion communityPersion = hajCommunityPersionService.queryById(user.getVillageId());
				if (null != communityPersion && communityPersion.getDistributionStatus() < 1) {
					log.info("小区：" + communityPersion.getCommunityName() + ",已经停止配送");
					return 11;
				}

				// =================================================
				// ~~~~~~~身份隔离带~~~~~~~非会员请止步
				// =================================================

				order.setOrderNo(orderNo);
				order.setCreateTime(DateUtil.dateToString(new Date()));
				if (order.getIsGrouponOrder() == 2) {// 补单
					order.setPaymentTime(new Date());// 补单支付时间默认为下单时间
					order.setStatus(2);
					order.setPayStatus(2);
				} else {
					order.setStatus(1);
					order.setPayStatus(1);
				}
				// 运费如果为空设置为0
				if (null == order.getPostFee()) {
					order.setPostFee(BigDecimal.ZERO);
					log.info(user.getMobilePhone() + "运费为空，禁止下单");
					return 1;
				}

				// orderPostFeeStatus 用于区分订单收取运费的方式，1: 次日收取; 2: 随单收取
				// 3.10.1版本后改为随单收取，之前的版本没传此字段，因为默认为1
				if (order.getOrderPostFeeStatus() < 1) {
					order.setOrderPostFeeStatus(1);
				}
				order.setHandleStatus(1);// 已审核
				order.setSortingOrderStatus(1);// 生成分拣单状态（默认1，已生成3）
				order.setWmshandleStatus(1);// 是否同步到wms系统（1未同步，2同步中 3已同步）
				order.setDelflag("0");// 未删除

				Map<String, Object> commodityCost = this.getCommodityCost(order.getOrderDetailList(),
						order.getIsGrouponOrder(), user.getAreaCode());

				if ((boolean) commodityCost.get("isCityCode")) {
					log.info("用户所在的城市查不到商品！");
					return 9;
				}
				// 订单来源 1家人喜好 2全部
				order.setSource(commodityCost.get("source").toString());

				// 验证商品是否下架
				if ((boolean) commodityCost.get("isShelves")) {
					// 清空全部商品缓存，以确保用户返回购物车时缓存取到的是最新的数据
					redisSpringProxy.delete("commodityPriceByName");

					log.info("该订单包含下架商品，已清缓存");
					return 5;
				}

				// 判断是否有商品价格发生变动
				if ((boolean) commodityCost.get("hasInvalidCommodities")) {
					log.info("该订单包含价格变动商品");
					return 6;
				}
				BigDecimal counponMoney = BigDecimal.ZERO;
				if (null != order.getCounponMoney()) {
					counponMoney = order.getCounponMoney();
				}

				if (Double.parseDouble(order.getActualPayment().toString()) <= 0
						&& counponMoney.compareTo(BigDecimal.ONE) != 0) {
					log.info("有优惠卷，订单实付为0的情况下 ，不验证金额");
				} else {
					// 判断是否有商品价格发生变动
					if (new BigDecimal(commodityCost.get("orderTotalActualPayment").toString()).compareTo(order
							.getActualPayment().add(counponMoney)) != 0) {
						log.info("该订单包含价格变动商品");
						return 6;
					}
				}

				if (null != commodityCost.get("orderCost").toString()) {
					order.setCommodityCost(commodityCost.get("orderCost").toString());// 商品成本
				}

				// 商品盈亏
				if (null != order.getActualPayment() && null != commodityCost.get("orderCost").toString()) {
					Double profitloss = Double.valueOf(order.getActualPayment().toString())
							- Double.parseDouble(commodityCost.get("orderCost").toString());
					DecimalFormat df = new DecimalFormat(".##");
					order.setProfitloss(Double.parseDouble(df.format(profitloss)));
				}

				// 验证订单详情不为空
				List<HajOrderDetails> detailsList = order.getOrderDetailList();

				// 判断是否允许重复下一元购订单
				String double_make_order = (String) redisSpringProxy.read("SystemConfiguration_disable_order");
				boolean doubleMakeOrder = Boolean.parseBoolean(double_make_order);
				if (doubleMakeOrder) {
					int orderCount = dao.getTodayOrderCountByUserId(order.getUserId());
					boolean isfirstOrder = (boolean) commodityCost.get("isfirstOrder");
					log.info("订单[" + orderNo + "]：orderCount:" + orderCount + ",isfirstOrder:" + isfirstOrder);
					if (!(orderCount == 0 || (orderCount > 0 && isfirstOrder))) {
						// 重复下一元购订单
						return 8;
					}
				}

				if (detailsList.size() > 0) {
					if (order.getIsGrouponOrder() != 1) {
						order.setDeliveryTime(DateUtil.getDeliveryTime());
					}
					// add 返回的对象里面可以取id值
					orderId = this.add(order);
					if (orderId > 0) {
						for (HajOrderDetails details : detailsList) {
							details.setOrderId(order);
							details.setStatus(1);
							hajOrderDetailsService.add(details);
						}
					}
				}
			} else {
				return 1;
			}
		} catch (Exception e) {
			log.info(e.getMessage(), e);
			try {
				log.info("订单生成失败！删除订单号：" + orderNo);
				this.delete(orderId);
				return 1;
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return 0;
	}

	/**
	 * 取消订单
	 */
	@Override
	public HajOrder updateCancelOrder(String orderNo) throws Exception {
		int resultId = dao.cancleOrderStatus(orderNo);
		if (resultId > 0) {
			HajOrder order = dao.getOrderByOrderNo(orderNo);

			// 取消订单成功后增加相应商品的库存
			if (order.getOrderDetailList().size() > 0) {
				for (HajOrderDetails details : order.getOrderDetailList()) {
					commodityService.updateInventoryAdd(details);
				}
			}

			// 删除问题订单列表
			orderProblemService.deleteByRefundNo(order.getOrderNo());

			log.info("订单取消成功后全额退款给用户");

			HajRecharge hajRecharge = orderPaymentService.getHajRechargeByOrderNo(order.getOrderNo());

			// 取消订单退款总额为全额退款
			// 如果订单是第三方支付，则调用无密退款接口
			if (order.getPaymentWay() != null) {
				if (order.getPaymentWay() == 0) {
					// 修改用户金额和积分
					hajFrontUserService.updateUserCancelOrder(order);

					// 修改交易流水记录
					hajTradingHistoryService.updateCancleOrderTradeRecord(order);

					dao.updateOrderRefundTime(order.getId());
				}

				// 订单退款金额
				BigDecimal refundFee = order.getActualPayment()
						.add(order.getDispensingTip())
						.add(order.getPostFee());

				if (hajRecharge != null) {
					boolean isRefund = false;

					if (order.getPaymentWay() == 1) {
						// 微信退款
						try {
							// 微信退款需将单位“元”转为“分”，并且只能为整数
							int refundFee4WX = refundFee.multiply(PayUtil.ONE_HUNDRED).intValue();
							if (order.getApplication() == 1) {
								isRefund = XcxPayUtil.refund(hajRecharge.getBankNo(), hajRecharge.getBankNo(),
										refundFee4WX, refundFee4WX);
							} else {
								isRefund = PayUtil.refund(hajRecharge.getBankNo(), hajRecharge.getBankNo(),
										refundFee4WX, refundFee4WX);
							}
						} catch (Exception e) {
							log.info("调用微信无密退款接口异常", e);
						}
					} else if (order.getPaymentWay() == 2) {
						// 支付宝退款
						try {
							isRefund = AlipayUtil.refundFastpayByPlatformNopwd(
									hajRecharge.getAlipayTradeNo(),
									refundFee.toString(),
									1, order.getOrderNo(), "取消订单后退款");
						} catch (Exception e) {
							log.info("调用支付宝无密退款接口异常", e);
						}
					}

					// 退款提交成功后将该笔订单的退款状态设为已退款，并更新退款时间
					if (isRefund) {
						HajRecharge updateRecharge = new HajRecharge();
						updateRecharge.setId(hajRecharge.getId());
						updateRecharge.setRefundNum(refundFee);
						updateRecharge.setIsRefund("1");
						updateRecharge.setRefundUpdateTime(DateUtil.datetime2Str(new Date()));
						hajRechargeService.updateBySelective(updateRecharge);

						dao.updateOrderRefundTime(order.getId());

						log.info("扣减用户订单积分");
						HajFrontUser user = hajFrontUserService.queryById(order.getUserId());
						HajFrontUser updateUser = new HajFrontUser();
						updateUser.setId(user.getId());

						int orderPoint = refundFee.intValue();
						updateUser.setRating(user.getRating() - orderPoint);
						updateUser.setVersion(user.getVersion() + 1);
						hajFrontUserService.updateBySelective(updateUser);
					} else {
						log.info("调用第三方无密退款接口失败...");
						throw new RollbackException("调用第三方无密退款接口失败...");
					}
				}

				// 补单的不需要记录
				if (order.getIsGrouponOrder() != 2) {
					// 取消订单记录积分明细
					HajIntegralDetails integralDetails = new HajIntegralDetails();
					integralDetails.setUserId(order.getUserId());
					integralDetails.setRemark("取消订单");
					integralDetails.setDetail("-" + refundFee.intValue());
					try {
						integralDetailsService.saveDetail(integralDetails);
					} catch (Exception e) {
						log.info("取消订单记录积分明细异常，useId: " + order.getUserId(), e);
					}
				}

			}
			return order;
		} else {
			return null;
		}
	}

	/**
	 * 修改订单状态为待退款
	 */
	@Override
	public void updateOrderSaleStatus(Integer id) {
		dao.updateOrderSaleStatus(id);
	}

	/**
	 * 修改订单状态为已退款
	 */
	@Override
	public int updateSaleStatus(String orderNo) {
		return dao.updateSaleStatus(orderNo);

	}

	@Override
	public List<Map<String, Object>> getOrderByNowDate() {
		return dao.getOrderByNowDate();
	}

	@Override
	public void updateOrderCompleteStatus(HajOrderSale sale) {
		// 将订单状态改回去
		dao.updateOrderCompleteStatus(sale.getOrderId());

		// 删掉这次售后对应的订单问题表数据
		orderProblemService.deleteByRefundNo(sale.getRefundNo());
	}

	/**
	 * 根据用户id和时间查询订单信息
	 */
	@Override
	public HajOrder getOrderByCreateTime(Integer userId, String createTime, String orderNo) {
		HajOrder order = new HajOrder();
		order.setUserId(userId);
		order.setCreateTime(createTime);
		if (null != orderNo) {
			order.setOrderNo(orderNo);
		}

		order = dao.getOrderByCreateTime(order);
		if (null != order) {
			// 订单取消后，退款金额初售后之外，加上订单实付金额
			if (order.getStatus() == 6) {
				order.setRefund(order.getActualPayment()
						.add(order.getPostFee())
						.add(order.getDispensingTip())
				);
			}

			List<HajOrderDetails> detail = orderDetailsMapper.getDetailByOrderId(order.getId());
			if (null != detail && detail.size() > 0) {
				order.setOrderDetailList(detail);
			}
		}
		return order;
	}

	@Override
	public int orderCancelStatus(HajOrder order) {
		try {
			if (order == null) {
				// 订单为空直接返回失败
				return 0;
			}

			if (order.getStatus() != 2) {
				// 非待配送订单不显示取消按钮
				return 0;
			}

			if (order.getIsGrouponOrder() == 1) {
				// 待配送且是团购订单，则可直接取消，无需判断时间
				return 1;
			}

			// 普通订单则需判断是否在取消时间范围内
			String cancelTime = (String) redisSpringProxy.read("SystemConfiguration_orderCancleTime");

			// 是否在取消时间范围内
			boolean within = DateUtil.compare_date(DateUtil.time2Str(new Date()), cancelTime) == -1;
			boolean isToday = DateUtil.isToday(order.getCreateTime(), DateUtil.datetime2Str(new Date()));
			if (!within || !isToday) {
				// 不在时间范围内不可取消（取消按钮为未激活）
				return 2;
			}

			Criteria criteria = new Criteria();
			Map<String, Object> condition = new HashMap<>();
			condition.put("userId", order.getUserId());
			condition.put("status", 2);
			condition.put("isGrouponOrder", 0);
			condition.put("orderDate", DateUtil.date2Str(new Date()));
			criteria.setCondition(condition);
			criteria.setOrderByClause(" id ASC");

			// 获取今日待配送普通订单列表
			List<HajOrder> orderList = dao.listOrder(criteria);

			if (orderList.size() == 0) {
				// 今日无待配送订单，则不可取消
				return 0;
			}

			// 然后判断个数
			if (orderList.size() == 1) {
				// 今日只有1个订单则按钮激活
				return 1;
			}

			// 多个则判断当前订单号是否首单
			boolean isFirstOrder = Objects.equals(order.getId(), orderList.get(0).getId());
			if (isFirstOrder) {
				// 首单显示按钮，未激活状态
				return 3;
			} else {
				// 非首单则可正常取消
				return 1;
			}
		} catch (Exception e) {
			log.info("获取订单取消状态异常", e);
			return 0;
		}
	}

	@Override
	public Map<String, Object> orderCountByTimeForResidential(Map<String, Object> map) {
		return dao.orderCountByTimeForResidential(map);
	}

	@Override
	public int getTodayOrderCountByUserId(Integer userId) {
		return dao.getTodayOrderCountByUserId(userId);
	}

	@Override
	public List<Map<String, Object>> listAllordreList(OrderVO orderVo) {
		if (orderVo.getResidentialList() != null && orderVo.getResidentialList().length < 1) {
			orderVo.setResidentialList(null);
		}
		return dao.listAllordreList(orderVo);
	}

	@Override
	public List<Map<String, Object>> getSortingOrderByDate() {
		return dao.getSortingOrderByDate();
	}

	@Override
	public void updateOrderSorting() {
		dao.updateOrderSorting();
	}

	@Override
	public XSSFWorkbook excelOrderReportDetail(OrderVO orderVo) {
		// 创建HSSFWorkbook对象(excel的文档对象)
		XSSFWorkbook wkb = new XSSFWorkbook();

		// 建立新的sheet对象（excel的表单）
		XSSFSheet sheet = wkb.createSheet();
		sheet.setDefaultColumnWidth(12);
		sheet.setDefaultRowHeightInPoints(20);

		// 在sheet里创建第1行
		XSSFRow row = sheet.createRow(0);

		// 创建单元格并设置单元格内容
		this.setDefaultXSSFRow(row, sheet);

		// ============================================================================
		// 开始写入上报小区数据
		// ============================================================================
		if (orderVo.getResidentialList() != null && orderVo.getResidentialList().length < 1) {
			orderVo.setResidentialList(null);
		}
		List<Map<String, Object>> communityPersions = dao.excelOrderReportDetail(orderVo);
		Map<String, Object> vo;

		int cellIndex = 0;
		for (int i = 0, len = communityPersions.size(); i < len; i++, cellIndex = 0) {
			vo = communityPersions.get(i);
			row = sheet.createRow(i + 1);

			row.createCell(cellIndex++).setCellValue(i + 1);
			row.createCell(cellIndex++).setCellValue(getMapValue(vo.get("orderNo")));
			row.createCell(cellIndex++).setCellValue(getMapValue(vo.get("createTime")));
			row.createCell(cellIndex++).setCellValue(getMapValue(vo.get("residential")));
			row.createCell(cellIndex++).setCellValue(getMapValue(vo.get("shippingAddress")));
			row.createCell(cellIndex++).setCellValue(getMapValue(vo.get("receiver")));
			row.createCell(cellIndex++).setCellValue(getMapValue(vo.get("address")));
			row.createCell(cellIndex++).setCellValue(getMapValue(vo.get("username")));
			row.createCell(cellIndex++).setCellValue(getMapValue(vo.get("mobilePhone")));
			row.createCell(cellIndex++).setCellValue(getMapValue(vo.get("number")));
			row.createCell(cellIndex++).setCellValue(getMapValue(vo.get("totalMoney")));
			row.createCell(cellIndex++).setCellValue(getMapValue(vo.get("counponMoney")));
			row.createCell(cellIndex++).setCellValue(getMapValue(vo.get("actualPayment")));
			row.createCell(cellIndex++).setCellValue(getMapValue(vo.get("commodityCost")));
			row.createCell(cellIndex++).setCellValue(getMapValue(vo.get("orderRealPay")));
			row.createCell(cellIndex++).setCellValue(getMapValue(vo.get("dispensingTip")));
			row.createCell(cellIndex++).setCellValue(getMapValue(vo.get("postFee")));
			row.createCell(cellIndex++).setCellValue(
					new BigDecimal(getMapValue(vo.get("actualPayment")))
							.add(new BigDecimal(getMapValue(vo.get("dispensingTip"))))
							.add(new BigDecimal(getMapValue(vo.get("postFee")))).toString());
			row.createCell(cellIndex++).setCellValue(getMapValue(vo.get("feeWaiver")));

			row.createCell(cellIndex++).setCellValue(
					new BigDecimal(getMapValue(vo.get("points")))
							.add(new BigDecimal(getMapValue(vo.get("dispensingTip"))))
							.add(new BigDecimal(getMapValue(vo.get("postFee")))).toString());

			row.createCell(cellIndex++).setCellValue(HajOrderUtil.getOrderStatus(getMapValue(vo.get("status"))));
			row.createCell(cellIndex++).setCellValue(getMapValue(vo.get("refundMoney")));
			row.createCell(cellIndex++).setCellValue(getMapValue(vo.get("historyOrderNum")));
		}

		return wkb;
	}

	private void setDefaultXSSFRow(XSSFRow row, XSSFSheet sheet) {
		int cellIndex = 0;
		// 初始化第一行标题
		row.createCell(cellIndex++).setCellValue("序号");
		row.createCell(cellIndex++).setCellValue("订单编号");
		row.createCell(cellIndex++).setCellValue("下单时间");
		row.createCell(cellIndex++).setCellValue("小区名");
		row.createCell(cellIndex++).setCellValue("小区地址");
		row.createCell(cellIndex++).setCellValue("收货人");
		row.createCell(cellIndex++).setCellValue("用户住址");
		row.createCell(cellIndex++).setCellValue("用户名");
		row.createCell(cellIndex++).setCellValue("注册手机号码");
		row.createCell(cellIndex++).setCellValue("商品数量");
		row.createCell(cellIndex++).setCellValue("订单总价");
		row.createCell(cellIndex++).setCellValue("优惠卷抵扣");
		row.createCell(cellIndex++).setCellValue("商品实收");
		row.createCell(cellIndex++).setCellValue("商品成本");
		row.createCell(cellIndex++).setCellValue("商品盈亏");
		row.createCell(cellIndex++).setCellValue("配送小费");
		row.createCell(cellIndex++).setCellValue("运费");
		row.createCell(cellIndex++).setCellValue("订单实付");
		row.createCell(cellIndex++).setCellValue("省下");
		row.createCell(cellIndex++).setCellValue("本单积分");
		row.createCell(cellIndex++).setCellValue("订单状态");
		row.createCell(cellIndex++).setCellValue("退款金额");
		row.createCell(cellIndex++).setCellValue("历史订单数");

	}

	@Override
	public List<Map<String, Object>> getHajPayOrderByUserId(Criteria criteria) {
		return dao.getHajPayOrderByUserId(criteria);
	}

	@Override
	public XSSFWorkbook excelHebingOrderReportDetail(OrderVO orderVo) {

		// 创建HSSFWorkbook对象(excel的文档对象)
		XSSFWorkbook wkb = new XSSFWorkbook();

		// 建立新的sheet对象（excel的表单）
		XSSFSheet sheet = wkb.createSheet();
		sheet.setDefaultColumnWidth(15);
		// sheet.setDefaultRowHeightInPoints(20);

		// 在sheet里创建第1行
		XSSFRow row = sheet.createRow(0);

		// 创建单元格并设置单元格内容
		this.setDefaultXSSFRow1(row, sheet);

		XSSFCellStyle cellStyle = wkb.createCellStyle();

		XSSFFont font = wkb.createFont();
		font.setBold(true);
		cellStyle.setFont(font);
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_JUSTIFY);
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cellStyle.setFillForegroundColor(HSSFColor.TAN.index);
		// ============================================================================
		// 开始写入上报小区数据
		// ============================================================================
		if (orderVo.getResidentialList() != null && orderVo.getResidentialList().length < 1) {
			orderVo.setResidentialList(null);
		}
		List<Map<String, Object>> orderList = dao.excelHebingOrderReportDetail(orderVo);

		int cellIndex = 0;
		int nextRow = 1;
		int unitRow = 5;
		String oldCommunity = "";
		String newCommunity = "";
		String oldUnit = "";
		String newUnit = "";
		String newUnitWithoutCommunity = "";
		Map<String, Object> orderMap = null;
		for (int i = 0, communityIndex = 1, len = orderList.size(); i < len; i++) {
			orderMap = orderList.get(i);

			newCommunity = getMapValue(orderMap.get("residential"));

			// 单元地址前添加小区名，保证唯一性
			newUnitWithoutCommunity = getMapValue(orderMap.get("unit"));
			newUnit = newCommunity + newUnitWithoutCommunity;

			// 空出2行填写小区名
			if (!oldCommunity.equals(newCommunity)) {
				oldCommunity = newCommunity;
				nextRow += 2;
				row = sheet.createRow(i + nextRow - 1);
				row.createCell(unitRow).setCellValue(newCommunity);
				row.getCell(unitRow).setCellStyle(cellStyle);

				// 合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
				sheet.addMergedRegion(new CellRangeAddress(i + nextRow - 1, i + nextRow - 1, unitRow, 13));

				// 不同小区需重新编号
				communityIndex = 1;
			}

			// 再空出一行填写单元名
			if (!oldUnit.equals(newUnit)) {
				oldUnit = newUnit;
				nextRow += 1;
				row = sheet.createRow(i + nextRow - 1);
				row.createCell(unitRow).setCellValue(newUnitWithoutCommunity);
				row.getCell(unitRow).setCellStyle(cellStyle);
			}

			cellIndex = 0;
			row = sheet.createRow(i + nextRow);
			row.createCell(cellIndex++).setCellValue(communityIndex++);

			row.createCell(cellIndex++).setCellValue(getMapValue(orderMap.get("createTime")));
			row.createCell(cellIndex++).setCellValue(getMapValue(orderMap.get("receiver")));
			row.createCell(cellIndex++).setCellValue(getMapValue(orderMap.get("mobilePhone")));
			row.createCell(cellIndex++).setCellValue(newCommunity);
			row.createCell(cellIndex++).setCellValue(getMapValue(orderMap.get("address")));
			row.createCell(cellIndex++).setCellValue(getMapValue(orderMap.get("orderdetails")));
			row.createCell(cellIndex++).setCellValue(countOrderNumer(getMapValue((orderMap.get("orderdetails")))));
			row.createCell(cellIndex++).setCellValue(getMapValue(orderMap.get("orderdetails1")));
			row.createCell(cellIndex++).setCellValue(countOrderNumer(getMapValue((orderMap.get("orderdetails1")))));
			row.createCell(cellIndex++).setCellValue(getMapValue(orderMap.get("orderdetails2")));
			row.createCell(cellIndex++).setCellValue(countOrderNumer(getMapValue((orderMap.get("orderdetails2")))));
			row.createCell(cellIndex++).setCellValue(getMapValue(orderMap.get("orderdetails3")));
			row.createCell(cellIndex++).setCellValue(countOrderNumer(getMapValue((orderMap.get("orderdetails3")))));
			row.createCell(cellIndex++).setCellValue(getMapValue(orderMap.get("orderdetails4")));
			row.createCell(cellIndex++).setCellValue(countOrderNumer(getMapValue((orderMap.get("orderdetails4")))));
			row.createCell(cellIndex++).setCellValue(getMapValue(orderMap.get("actualPayment")));
			row.createCell(cellIndex++).setCellValue(getMapValue(orderMap.get("feeWaiver")));
			row.createCell(cellIndex++).setCellValue(getMapValue(orderMap.get("totalMoney")));
		}
		return wkb;
	}

	private int countOrderNumer(String orderdetails) {
		int resultNumber = 0;
		if (null != orderdetails && !orderdetails.equals("")) {
			String[] details = orderdetails.split(",");
			for (String s : details) {
				if (s.contains("*")) {
					int length = s.split("\\*").length;
					resultNumber += Integer.parseInt(s.split("\\*")[length - 1]);
				}
			}
		}
		return resultNumber;
	}

	private void setDefaultXSSFRow1(XSSFRow row, XSSFSheet sheet) {
		int cellIndex = 0;
		// 初始化第一行标题
		row.createCell(cellIndex++).setCellValue("序号");
		row.createCell(cellIndex++).setCellValue("下单日期");
		row.createCell(cellIndex++).setCellValue("收货人");
		row.createCell(cellIndex++).setCellValue("手机号码");
		row.createCell(cellIndex++).setCellValue("小区");
		row.createCell(cellIndex++).setCellValue("用户住址");
		row.createCell(cellIndex++).setCellValue("订单详情A");
		row.createCell(cellIndex++).setCellValue("A列汇总数");
		row.createCell(cellIndex++).setCellValue("订单详情B");
		row.createCell(cellIndex++).setCellValue("B列汇总数");
		row.createCell(cellIndex++).setCellValue("订单详情C");
		row.createCell(cellIndex++).setCellValue("C列汇总数");
		row.createCell(cellIndex++).setCellValue("订单详情D");
		row.createCell(cellIndex++).setCellValue("D列汇总数");
		row.createCell(cellIndex++).setCellValue("订单详情E");
		row.createCell(cellIndex++).setCellValue("E列汇总数");
		row.createCell(cellIndex++).setCellValue("订单金额");
		row.createCell(cellIndex++).setCellValue("节省金额");
		row.createCell(cellIndex++).setCellValue("总价");
	}

	/**
	 * 将map中的值转为String，如对象为空则返回空字符串
	 *
	 * @param obj
	 * @return
	 * @author SevenWong<br>
	 * @date 2016年9月23日下午4:56:03
	 */
	private String getMapValue(Object obj) {
		return (obj == null) ? Tools.EMPTY : obj.toString();
	}

	@Override
	public List<HajOrder> getTodayPayOrder() {
		return dao.getTodayPayOrder();
	}

	@Override
	public void updateOrderCostPrice(HajOrder order) {
		// List<HajOrderDetails> hajOrderDetails =
		// hajOrderDetailsService.getChainDetailByOrderId(order.getId());
		try {
			Map<String, Object> map = this.getCommodityCost(order.getOrderDetailList(), order.getIsGrouponOrder(), "");
			// 星级会员
			if (null != map.get("orderCost").toString()) {
				order.setCommodityCost(map.get("orderCost").toString());// 商品成本
			}
			// 商品盈亏
			if (null != order.getActualPayment() && null != map.get("orderCost").toString()) {
				Double profitloss = Double.valueOf(order.getActualPayment().toString())
						- Double.parseDouble(map.get("orderCost").toString());
				DecimalFormat df = new DecimalFormat(".##");
				order.setProfitloss(Double.parseDouble(df.format(profitloss)));

			}
			dao.updateBySelective(order);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<HashMap<String, Object>> getOrderByDateTime(String dateTime) {
		return dao.getOrderByDateTime(dateTime);
	}

	@Override
	public HajOrder getBuDanByMobile(String mobile) {
		return dao.getBuDanByMobile(mobile);
	}

	@Override
	public String batchImport(String filePath, HttpServletRequest request) throws Exception {
		// 正常情况下返回success，异常时返回异常行的序列及提示信息
		StringBuilder result = new StringBuilder();

		// 当前操作的用户
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);

		// 构造 XSSFWorkbook 对象，filePath 传入文件路径
		XSSFWorkbook xwb = new XSSFWorkbook(filePath);

		// 读取第一张sheet表格内容
		XSSFSheet sheet = xwb.getSheetAt(0);
		xwb.close();// 记得关闭流

		XSSFRow row;

		/*---------- 声明循环体内需要用到的对象 ----------*/
		HajOrder order;
		HajFrontUser customer;
		HajCommodity commodity;
		String orderReason;
		StringBuilder orderRemark;
		final String SEPARATOR = ",";
		int cellIndex;
		int saveHajOrderResult;

		BigDecimal count;
		String mobile;
		BigDecimal unitPrice;

		/*---------- 声明提示信息 ----------*/
		StringBuilder unknownError = new StringBuilder();
		StringBuilder nonMemberError = new StringBuilder();
		StringBuilder soldOutError = new StringBuilder();

		// 循环输出表格中的内容。变量i从真实数据行开始读取
		for (int i = (sheet.getFirstRowNum() + 1), rows = sheet.getPhysicalNumberOfRows(); i < rows; i++) {
			cellIndex = 0;
			orderRemark = new StringBuilder();
			row = sheet.getRow(i);
			if (row == null)
				continue;

			if ("".equals(getCellValue(row.getCell(cellIndex))))
				continue;

			mobile = getCellValue(row.getCell(cellIndex++));
			customer = hajFrontUserService.getUserByMobile(mobile);
			if (customer == null) {
				nonMemberError.append(i + 1).append(SEPARATOR);
				continue;
			}

			// 超管没有选择地区时跳过此步骤
			if (Tools.isNotEmpty(user.getAreaCode()) && !user.getAreaCode().equals(customer.getAreaCode())) {
				log.info("后台操作员，无权限给该地区的用户补单！用户手机：" + customer.getMobilePhone());
				nonMemberError.append(i + 1).append(SEPARATOR);
				break;
			}

			commodity = commodityService.getByName(getCellValue(row.getCell(cellIndex++)), customer.getAreaCode());
			if (commodity == null) {
				soldOutError.append(i + 1).append(SEPARATOR);
				continue;
			}

			// 超管没有选择地区时跳过此步骤
			if (Tools.isNotEmpty(user.getAreaCode()) && !user.getAreaCode().equals(commodity.getAreaCode())) {
				log.info("后台操作员，无权限给该地区的商品补单！商品：" + commodity.getName());
				soldOutError.append(i + 1).append(SEPARATOR);
				break;
			}

			count = new BigDecimal(getCellValue(row.getCell(cellIndex++)));
			unitPrice = new BigDecimal(getCellValue(row.getCell(cellIndex++)));

			// 补单原因必须填写
			orderReason = getCellValue(row.getCell(cellIndex));

			// 未填写补单原因、查不到用户、查不到商品，则此行数据视为无效
			if (Tools.isEmpty(orderReason)) {
				unknownError.append(i + 1).append(SEPARATOR);
				continue;
			}

			if (!isImportWithError(unknownError, nonMemberError, soldOutError)) {
				// 订单：预计送达时间，收货人，积分，配送小费，联系电话，小区，用户ID，地址，订单实付，商品总价，商品总数，减免费用
				order = btOrder(customer, commodity, count, unitPrice);

				// 订单详情：商品名，商品数量，商品单价，实际支付，来源0，所有来源1，商品分类，补单原因（0质量问题，1仓库缺货）
				btOrderDetail(user, order, commodity, orderReason, orderRemark);

				saveHajOrderResult = this.saveHajOrder(order, MakeOrderNum.makeOrderNum());

				switch (saveHajOrderResult) {
					case 1:
						log.info("批量补单时发现了未知异常");
						unknownError.append(i + 1).append(SEPARATOR);
						break;
					case 4:
						log.info(customer.getMobilePhone() + " 不是一元购会员");
						nonMemberError.append(i + 1).append(SEPARATOR);
						break;
					case 5:
						log.info(commodity.getName() + " 已下架");
						soldOutError.append(i + 1).append(SEPARATOR);
						break;
					case 9:
						log.info("用户所在的城市查不到商品：" + commodity.getName());
						soldOutError.append(i + 1).append(SEPARATOR);
						break;
					case 0:
						// 此单补单校验无误后，并且之前的数据也校验无误，才进行更新库存操作；
						// 否则这一步会显得很多余，因为只要有一个校验不通过就视为批量补单失败，所有数据都得回滚
						if (!isImportWithError(unknownError, nonMemberError, soldOutError)) {
							log.info("补单成功，扣减相应库存。商品：" + commodity.getName());
							commodityService.updateInventoryReduce(order);
						}
						break;
				}
			}
		}

		/* 整理提示信息 */
		if (unknownError.length() > 0) {
			result.append("第[");
			result.append(unknownError.substring(0, unknownError.length() - 1));
			result.append("]行发生了未知异常。请检查表格数据完整性或联系管理员！\n\r");
		}
		if (nonMemberError.length() > 0) {
			result.append("第[");
			result.append(nonMemberError.substring(0, nonMemberError.length() - 1));
			result.append("]行的用户不是会员或者您没有权限对其所在城市进行操作！\n\r");
		}
		if (soldOutError.length() > 0) {
			result.append("第[");
			result.append(soldOutError.substring(0, soldOutError.length() - 1));
			result.append("]行的商品已失效！\n\r");
		}

		/* 出现异常情况时，则抛出回滚事务的异常 */
		if (isImportWithError(unknownError, nonMemberError, soldOutError)) {
			result.append("补单失败，请确认数据准确性后重新补单！");
			throw new RollbackException(result.toString());
		} else {
			result.append("success");
		}
		return result.toString();
	}

	private boolean isImportWithError(StringBuilder unknownError, StringBuilder nonMemberError, StringBuilder soldOutError) {
		return unknownError.length() > 0 || nonMemberError.length() > 0 || soldOutError.length() > 0;
	}

	private void btOrderDetail(User user, HajOrder order, HajCommodity commodity, String orderReason,
							   StringBuilder orderRemark) {
		HajOrderDetails orderDetails;
		List<HajOrderDetails> orderDetailsList;
		orderDetails = new HajOrderDetails();
		orderDetails.setCommodityName(commodity.getName());
		orderDetails.setNumber(order.getNumber());
		orderDetails.setCommodityListPrice(commodity.getOriginalPrice());
		orderDetails.setActualPayment(order.getActualPayment());
		orderDetails.setSource("0");
		orderDetails.setAllSource("1");
		orderDetails.setCommodityType(commodity.getTypeId());

		if ("质量问题".equals(orderReason)) {
			orderDetails.setRemark3("0");
			orderRemark.append(commodity.getName()).append("*").append(order.getNumber()).//
					append("，（质量问题）；~~ by:").append(user.getUsername()).append("~~");
		} else {
			orderDetails.setRemark3("1");
			orderRemark.append(commodity.getName()).append("*").append(order.getNumber()).//
					append("，（仓库缺货）；~~ by:").append(user.getUsername()).append("~~");
		}
		order.setRemark(orderRemark.toString());

		orderDetailsList = new ArrayList<>();
		orderDetailsList.add(orderDetails);

		order.setOrderDetailList(orderDetailsList);
	}

	private HajOrder btOrder(HajFrontUser customer, HajCommodity commodity, BigDecimal count, BigDecimal unitPrice) {
		HajOrder order;
		order = new HajOrder();
		order.setDeliveryTime(DateUtil.getDeliveryTime());
		order.setReceiver(customer.getReceiver());
		order.setPoints(0);
		order.setDispensingTip(BigDecimal.ZERO);
		order.setContactPhone(customer.getMobilePhone());
		order.setResidential(customer.getResidential());
		order.setUserId(customer.getId());
		order.setAddress(customer.getAddress());
		order.setActualPayment(unitPrice.multiply(count));
		order.setTotalMoney(commodity.getMarketPrice().multiply(count));
		order.setNumber(count.intValue());
		order.setFeeWaiver(order.getTotalMoney().subtract(order.getActualPayment()));
		order.setIsGrouponOrder(2);
		order.setToDayOrderNumber("0");
		order.setPostFee(BigDecimal.ZERO);
		order.setApplication(2);
		return order;
	}

	@Override
	public List<Map<String, Object>> getOrderPostFeeByDate() {
		return dao.getOrderPostFeeByDate();
	}

	@Override
	public void updateOrderPostFee() {
		dao.updateOrderPostFee();
	}

	@Override
	public void updateHajOrderStatus() {
		dao.updateHajOrderStatus();
	}

	@Override
	public void updateOrderHandleStatus(String nowDate) {
		dao.updateOrderHandleStatus(nowDate);
	}

	@Override
	public List<Map<String, Object>> listAllSynchronizeOrder(OrderVO orderVo) {
		return dao.listSynchronizeOrder(orderVo);
	}

	@Override
	public void synchronizeOrder(Map<String, Object> map, String orderClassification) {
		try {
			if (null != map && map.size() > 0) {
				// 订单id 用逗号隔开
				String orderId = map.get("orderId").toString();
				String[] orderIds = orderId.split(",");
				boolean istrue = true;
				// 商品分类
				String sememo = orderDetailsMapper.getsbTypeNamebyOrderId(orderIds);
				// 循环orderClassification A,B&C,D&E
				String classificationStr = "";
				String[] classification = orderClassification.split("&");
				if (classification.length > 0) {
					for (int i = 0; i < classification.length; i++) {
						List<WMSOrderDetail> skulst = new ArrayList<WMSOrderDetail>();
						// 商品分类
						WMSOrderDetail wmsDetail = null;
						BigDecimal detailMoney = BigDecimal.ZERO;
						classificationStr = classification[i];
						WMSOrder wmsorder = setWMSOrder(map, classificationStr);

						List<Map<String, Object>> detailList = orderDetailsMapper.listAllSysOrderDetails(
								classificationStr.split(","), orderIds);

						if (detailList.size() > 0) {
							for (Map<String, Object> detail : detailList) {
								wmsDetail = new WMSOrderDetail();
								if (null != detail.get("sku")) {
									wmsDetail.setSku(detail.get("sku").toString());
								} else {
									wmsDetail.setSku(detail.get("commodityNo").toString());
								}
								wmsDetail.setNumsku(detail.get("commodityNo").toString());
								wmsDetail.setQty(detail.get("number").toString());
								wmsDetail.setPri(detail.get("originalPrice").toString());
								wmsDetail.setAmo(detail.get("actualPayment").toString());
								wmsDetail.setPro("0");
								wmsDetail.setPay(detail.get("actualPayment").toString());
								wmsDetail.setDis("0");
								skulst.add(wmsDetail);
								BigDecimal bd = new BigDecimal(detail.get("actualPayment").toString());
								detailMoney = detailMoney.add(bd);
							}

							wmsorder.setSkulst(skulst);
							wmsorder.setActuallyPaid(String.valueOf(detailMoney));
							if (!sememo.equals("")) {
								wmsorder.setSememo(sememo);
							}

							String wmsorderStr = JSONUtils.toJSONString(wmsorder);
							log.info("同步至WMS订单json字符串：" + wmsorderStr);

							String resultStr = HttpClientUtil.sendPost(API.CREATE_DN, wmsorderStr,
									HttpClientUtil.CONTENT_TYPE_JSON);
							log.info("同步至WMS订单的结果：" + resultStr);
							if (resultStr != null) {

								JSONObject jsonobject = JSONObject.fromObject(resultStr);
								HashMap<?, ?> resultMap = JSONUtils.toHashMap(jsonobject);
								String result = resultMap.get("Result").toString();

								log.info("【CancleOrderResultBean：是否成功的状态：】" + result);

								if (result.equals("false")
										&& Tools.isNotEmpty(resultMap.get("CancelReason").toString())) {
									String cancelReason = resultMap.get("CancelReason").toString();
									JSONObject jsonobject1 = JSONObject.fromObject(cancelReason);
									HashMap<?, ?> map1 = JSONUtils.toHashMap(jsonobject1);
									log.info("ErrMsg:" + map1.get("ErrMsg"));
									istrue = false;
								}
							}
						}
					}
				}
				if (istrue) {
					String[] ids = orderId.split(",");
					// 修改订单状态为wmshandleStatus = 3同步完成
					int rId = dao.updateWmshandleStatusByOrderIds(ids);
					log.info("修改订单的同步中至WMS状态resultId：" + rId);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 同步订单到WMS系统
	 *
	 * @throws ParseException
	 */
	@Override
	public void synchronizehebingOrder(Map<String, Object> map) {
		String orderDate;
		boolean istrue = true;
		try {
			orderDate = MakeOrderNum.makeWMSOrderNum();
			String orderNo = map.get("orderNo").toString() + "_" + orderDate;

			WMSOrder wmsorder = new WMSOrder();
			wmsorder.setOrdeno(orderNo);
			wmsorder.setOrdertype("HAJ");
			wmsorder.setFromty("HAJ");
			wmsorder.setFromno(orderNo);
			wmsorder.setOpform("1");
			wmsorder.setPriori("0");
			wmsorder.setCrtime(map.get("createTime").toString());
			wmsorder.setPatime(map.get("createTime").toString());
			wmsorder.setAdtime(map.get("createTime").toString());
			wmsorder.setAduser("SystemAdmin");
			wmsorder.setPiscod("false");
			wmsorder.setIsdelv("true");
			wmsorder.setShopna("深圳汇爱家");
			wmsorder.setShopno("SZHAJ");
			wmsorder.setBunick(map.get("mobilePhone").toString());
			wmsorder.setCustna(map.get("username").toString());
			wmsorder.setRecena(map.get("receiver").toString());
			wmsorder.setPostco("518000");
			wmsorder.setProvco("广东省");
			wmsorder.setProvna("440000");
			wmsorder.setCityco(map.get("cityCode").toString());
			wmsorder.setCityna(map.get("cityName").toString());
			wmsorder.setDistco(map.get("wms_code").toString());
			wmsorder.setDistna(map.get("name").toString());
			wmsorder.setAddres(calAddress(map));
			wmsorder.setMobile(map.get("mobilePhone").toString());
			wmsorder.setTeleph(map.get("mobilePhone").toString());
			wmsorder.setIsInvoice("false");
			wmsorder.setIsTopay("false");
			wmsorder.setOrdeca(map.get("totalMoney").toString());
			wmsorder.setActuallyPaid(map.get("actualPayment").toString());
			wmsorder.setIsspec("false");
			wmsorder.setWhcode(map.get("whcode").toString());
			wmsorder.setCacode(map.get("communityId").toString());// 小区ID
			wmsorder.setCaname(map.get("communityName").toString());// 小区名称
			String orderId = map.get("orderId").toString();
			String[] orderIds = orderId.split(",");
			String sememo = orderDetailsMapper.getsbTypeNamebyOrderId(orderIds);
			wmsorder.setSememo(sememo);
			WMSOrderDetail wmsDetail = null;
			Set<WMSOrderDetail> skulst = new HashSet<WMSOrderDetail>();

			List<Map<String, Object>> detailList = orderDetailsMapper.listAllOrderDetails1(orderIds);
			for (Map<String, Object> detail : detailList) {
				wmsDetail = new WMSOrderDetail();
				wmsDetail.setSku(detail.get("sku").toString());
				wmsDetail.setNumsku(detail.get("commodityNo").toString());
				wmsDetail.setQty(detail.get("number").toString());
				wmsDetail.setPri(detail.get("originalPrice").toString());
				wmsDetail.setAmo(detail.get("actualPayment").toString());
				wmsDetail.setPro("0");
				wmsDetail.setPay(detail.get("actualPayment").toString());
				wmsDetail.setDis("0");
				skulst.add(wmsDetail);
			}
			wmsorder.setSkulst(skulst);

			String wmsorderStr = JSONUtils.toJSONString(wmsorder);
			log.info("同步至WMS订单json字符串：" + wmsorderStr);

			String resultStr = HttpClientUtil.sendPost(API.CREATE_DN, wmsorderStr, HttpClientUtil.CONTENT_TYPE_JSON);
			log.info("同步至WMS订单的结果：" + resultStr);
			if (resultStr != null) {

				JSONObject jsonobject = JSONObject.fromObject(resultStr);
				HashMap<?, ?> resultMap = JSONUtils.toHashMap(jsonobject);
				String result = resultMap.get("Result").toString();

				log.info("【CancleOrderResultBean：是否成功的状态：】" + result);

				if (result.equals("false") && Tools.isNotEmpty(resultMap.get("CancelReason").toString())) {
					String cancelReason = resultMap.get("CancelReason").toString();
					JSONObject jsonobject1 = JSONObject.fromObject(cancelReason);
					HashMap<?, ?> map1 = JSONUtils.toHashMap(jsonobject1);
					log.info("ErrMsg:" + map1.get("ErrMsg"));
					istrue = false;
				}
			}

			if (istrue) {
				String[] ids = orderId.split(",");
				// 修改订单状态为wmshandleStatus = 3同步完成
				int rId = dao.updateWmshandleStatusByOrderIds(ids);
				log.info("修改订单的同步中至WMS状态resultId：" + rId);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public WMSOrder setWMSOrder(Map<String, Object> map, String orderClassification) throws ParseException {
		String orderDate = MakeOrderNum.makeWMSOrderNum();
		String orderNo = map.get("orderNo").toString() + "_" + orderClassification + "_" + orderDate;
		WMSOrder wmsorder = new WMSOrder();
		wmsorder.setOrdeno(orderNo);
		wmsorder.setOrdertype("HAJ");
		wmsorder.setFromty("HAJ");
		wmsorder.setFromno(orderNo);
		wmsorder.setOpform("1");
		wmsorder.setPriori("0");
		wmsorder.setCrtime(map.get("createTime").toString());
		wmsorder.setPatime(map.get("createTime").toString());
		wmsorder.setAdtime(map.get("createTime").toString());
		wmsorder.setAduser("SystemAdmin");
		wmsorder.setPiscod("false");
		wmsorder.setIsdelv("true");
		wmsorder.setShopna("深圳汇爱家");
		wmsorder.setShopno("SZHAJ");
		wmsorder.setBunick(map.get("mobilePhone").toString());
		wmsorder.setCustna(map.get("username").toString());
		wmsorder.setRecena(map.get("receiver").toString());
		wmsorder.setPostco("518000");
		wmsorder.setProvco("广东省");
		wmsorder.setProvna("440000");
		wmsorder.setCityco(map.get("cityCode").toString());
		wmsorder.setCityna(map.get("cityName").toString());
		wmsorder.setDistco(map.get("wms_code").toString());
		wmsorder.setDistna(map.get("name").toString());
		wmsorder.setAddres(calAddress(map));
		wmsorder.setMobile(map.get("mobilePhone").toString());
		wmsorder.setTeleph(map.get("mobilePhone").toString());
		wmsorder.setIsInvoice("false");
		wmsorder.setIsTopay("false");
		wmsorder.setOrdeca(map.get("totalMoney").toString());
		wmsorder.setActuallyPaid(map.get("actualPayment").toString());
		wmsorder.setIsspec("false");
		wmsorder.setWhcode(map.get("whcode").toString());
		wmsorder.setCacode(map.get("communityId").toString());// 小区ID
		wmsorder.setCaname(map.get("communityName").toString());// 小区名称
		return wmsorder;
	}

	public String calAddress(Map<String, Object> map) {
		// unit+floor+'层'+houseNumber
		String newfloor = "";
		String unit = "";
		String houseNumber = "";
		String newSort = "";
		if (null != map.get("unit")) {
			unit = map.get("unit").toString();
		}
		if (null != map.get("floor") && !map.get("floor").equals("")) {
			int floor = Integer.parseInt(map.get("floor").toString());
			if (floor < 10) {
				newfloor = "0" + map.get("floor").toString() + "层";
			} else {
				newfloor = map.get("floor").toString() + "层";
			}
		}
		if (null != map.get("houseNumber")) {
			houseNumber = map.get("houseNumber").toString();
		}
		String sort = map.get("sort").toString();
		int IntSort = Integer.parseInt(sort);
		if (IntSort < 10) {
			newSort = "00" + sort;
		} else if (IntSort < 100) {
			newSort = "0" + sort;
		} else {
			newSort = sort;
		}

		String address = "【" + newSort + "】" + unit + newfloor + houseNumber;
		return address;
	}

	@Override
	public void updateWmshandleStatus(String nowDate) {
		dao.updateAllWmshandleStatus(nowDate);
	}

	@Override
	public int queryTodayCount(String nowDate) {
		return dao.queryTodayCount(nowDate);
	}

	@Override
	public boolean isFirstOrder(HajFrontUser user, HajOrder order) {
		boolean refundSuccess = false;
		try {
			if (user != null) {
				Map<String, Object> commodityCost = this.getCommodityCost(order.getOrderDetailList(),
						order.getIsGrouponOrder(), user.getAreaCode());

				// 判断是否允许重复下一元购订单
				String double_make_order = (String) redisSpringProxy.read("SystemConfiguration_disable_order");
				boolean doubleMakeOrder = Boolean.parseBoolean(double_make_order);
				if (doubleMakeOrder) {
					int orderCount = dao.getTodayOrderCountByUserId(order.getUserId());
					boolean isfirstOrder = (boolean) commodityCost.get("isfirstOrder");
					boolean isPromotionPrice = (boolean) commodityCost.get("isPromotionPrice");
					log.info("订单[" + order.getOrderNo() + "]：orderCount:" + orderCount + ",isfirstOrder:"
							+ isfirstOrder);
					if (!(orderCount == 0 || (orderCount > 0 && isfirstOrder))) {
						// 重复支付 进行退款操作
						refundSuccess = true;
						log.info("重复1元购订单支付，退款结果：" + refundSuccess);
					}

					if (order.getIsGrouponOrder() != 2 && orderCount == 0 && isfirstOrder && isPromotionPrice) {
						// 重复支付 进行退款操作
						refundSuccess = true;
						log.info("重复1元购订单支付，退款结果：" + refundSuccess);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return refundSuccess;
	}

	/**
	 * 微信和支付宝支付的订单 更新支付状态和用户积分
	 */
	@Override
	public void updateOrderPayStatus(String orderNo, int paymentWay) {
		try {
			boolean refundSuccess = false;
			HajOrder order = this.getOrderByOrderNo(orderNo);
			// 判断该订单的状态是否需要修改
			if (order.getStatus() == 1) {
				HajFrontUser user = hajFrontUserService.queryById(order.getUserId());
				// 判断该用户是否是会员
				if (user != null) {

					Map<String, Object> commodityCost = this.getCommodityCost(order.getOrderDetailList(),
							order.getIsGrouponOrder(), user.getAreaCode());

					// 判断是否允许重复下一元购订单
					String double_make_order = (String) redisSpringProxy.read("SystemConfiguration_disable_order");
					boolean doubleMakeOrder = Boolean.parseBoolean(double_make_order);
					if (doubleMakeOrder) {
						int orderCount = dao.getTodayOrderCountByUserId(order.getUserId());
						boolean isfirstOrder = (boolean) commodityCost.get("isfirstOrder");
						boolean isPromotionPrice = (boolean) commodityCost.get("isPromotionPrice");
						log.info("订单[" + orderNo + "]：orderCount:" + orderCount + ",isfirstOrder:" + isfirstOrder);
						if (!(orderCount == 0 || (orderCount > 0 && isfirstOrder))) {
							// 重复支付 进行退款操作
							refundSuccess = this.refund(order, order.getUserId(), paymentWay);
							log.info("重复1元购订单支付，退款结果：" + refundSuccess);
						}

						if (order.getIsGrouponOrder() != 2 && orderCount == 0 && isfirstOrder && isPromotionPrice) {
							// 重复支付 进行退款操作
							refundSuccess = this.refund(order, order.getUserId(), paymentWay);
							log.info("重复1元购订单支付，退款结果：" + refundSuccess);
						}
					}

					if (!refundSuccess) {
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
						int rating = order.getPoints() + Integer.parseInt(order.getDispensingTip().toString())
								+ order.getPostFee().setScale(0, BigDecimal.ROUND_DOWN).intValue();
						// 更新用户积分
						hajFrontUserService.updateRechargeOrderPostFee(user.getId(), BigDecimal.valueOf(rating));

						// 订单使用第三方支付完成后记录积分明细
						HajIntegralDetails integralDetails = new HajIntegralDetails();
						integralDetails.setUserId(user.getId());
						integralDetails.setRemark("订单奖励积分");
						integralDetails.setDetail("+" + rating);
						try {
							integralDetailsService.saveDetail(integralDetails);
						} catch (Exception e) {
							log.info("第三方支付成功后记录积分明细异常，useId: " + user.getId(), e);
						}

						// 扣款成功 修改订单状态
						dao.updateOrderStatus(orderNo, paymentWay);
						// 修改用户优惠卷为已使用
						if (null != order.getCounponId()) {
							log.info("支付成功！优惠卷ID" + order.getCounponId() + "更新为已使用");
							couponUserService.updateUserUsed(order.getCounponId());
						}

					}
				}
			} else if (order.getStatus() == 2) {
				refundSuccess = this.refund(order, order.getUserId(), paymentWay);
				log.info("重复订单支付，退款结果：" + refundSuccess);
			} else if (order.getStatus() == 9) {
				refundSuccess = this.refund(order, order.getUserId(), paymentWay);
				log.info("支付时订单被系统取消，退款结果：" + refundSuccess);
			}
		} catch (Exception e) {
			log.info(orderNo + "第三方支付订单更新状态异常", e);
		}
	}

	/**
	 * 重复支付退款至账户
	 *
	 * @param hajOrder
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	private boolean refund(HajOrder hajOrder, int userId, int paymentWay) throws Exception {
		// 将此次退款写入交易历史记录表
		HajTradingHistory tradingHistory = new HajTradingHistory();
		boolean refundSuccess = false;
		HajRecharge rechargeByOrderNo = orderPaymentService.getHajRechargeByOrderNo(hajOrder.getOrderNo());
		BigDecimal orderActualPayment = hajOrder.getActualPayment().add(
				hajOrder.getDispensingTip().add(hajOrder.getPostFee()));
		String refundNo = MakeOrderNum.makeOrderNum();

		if (paymentWay == 1) {
			if (hajOrder.getApplication() == 1) {
				// 小程序退款
				refundSuccess = XcxPayUtil.refund(rechargeByOrderNo.getBankNo(), refundNo,
						orderActualPayment.multiply(XcxPayUtil.ONE_HUNDRED).intValue(),
						orderActualPayment.multiply(XcxPayUtil.ONE_HUNDRED).intValue());
			} else {
				// APP退款
				refundSuccess = PayUtil.refund(rechargeByOrderNo.getBankNo(), refundNo,
						orderActualPayment.multiply(PayUtil.ONE_HUNDRED).intValue(),
						orderActualPayment.multiply(PayUtil.ONE_HUNDRED).intValue());
			}

			tradingHistory.setTradingContent("退款至微信：" + orderActualPayment);
		} else if (paymentWay == 2) {
			refundSuccess = AlipayUtil.refundFastpayByPlatformNopwd(rechargeByOrderNo.getAlipayTradeNo(),
					orderActualPayment.toString(), 1, refundNo, "下单异常");
			tradingHistory.setTradingContent("退款至支付宝：" + orderActualPayment);
		}
		tradingHistory.setCreateTime(DateUtil.dateToString(new Date()));
		tradingHistory.setMoney(orderActualPayment);
		tradingHistory.setTradingStatus(1);
		tradingHistory.setUserId(userId);
		tradingHistory.setType(1);
		hajTradingHistoryService.saveTradeRecord(tradingHistory);

		dao.updateOrderRefundTime(hajOrder.getId());

		return refundSuccess;
	}

	@Override
	public void deleteOrder(String orderNo) {
		dao.deleteOrder(orderNo);
	}

	/**
	 * 查询超过规定时间未支付的订单
	 */
	@Override
	public List<Map<String, Object>> getClearOrderByDate(String orderClearTime) {
		return dao.getClearOrderByDate(orderClearTime);
	}

	/**
	 * 修改订单状态为系统取消 释放库存 商品自动上架
	 */
	@Override
	public void updateClearOrder(Map<String, Object> map) {
		String commodityIds = (String) redisSpringProxy.read("SystemConfiguration_auto_put_on_cids");
		String putOffShelvesTime = (String) redisSpringProxy.read("SystemConfiguration_putOffShelvesTime");
		String[] commodityIdStr;
		boolean isTrue = false;

		String commodityIds_18 = (String) redisSpringProxy.read("SystemConfiguration_auto_pull_off_shelves_cids_18");
		String putOffShelvesTime_18 = (String) redisSpringProxy.read("SystemConfiguration_putOffShelvesTime_18");
		String[] commodityIdStr18;
		boolean isTrue18 = false;

		Object lock = new Object();
		String orderNo = map.get("orderNo").toString();
		String orderId = map.get("id").toString();
		// 更新订单状态为“系统取消(status =9)”
		int resultId = dao.updateClearOrderNo(orderNo);
		synchronized (lock) {
			if (resultId > 0) {
				// 释放库存，商品上架
				List<HajOrderDetails> detailList = orderDetailsMapper.getChainDetailByOrderId(orderId);
				HajCommodity commodity;
				for (HajOrderDetails details : detailList) {
					if (null != details.getCommodityNo()) {
						commodity = commodityService.getCommodityByNo(details.getCommodityNo());
						// 释放库存
						log.info("清理订单超时未支付库存，商品编号：" + commodity.getCommodityNo() + ",当前库存：" + commodity.getInventory());
						commodityService.updateInventoryAdd(details);
						// 如果商品是下架
						if (commodity.getShelves() == 0) {
							commodityIdStr = commodityIds.split(",");
							isTrue = Arrays.asList(commodityIdStr).contains(commodity.getId().toString());

							commodityIdStr18 = commodityIds_18.split(",");
							isTrue18 = Arrays.asList(commodityIdStr18).contains(commodity.getId().toString());
							// 如果商品在16点下架的商品配置中
							if (isTrue) {
								// 如果未超过16点，此时可以上架,超过就不能上架
								if (DateUtil.compare_date(DateUtil.time2Str(new Date()), putOffShelvesTime) == -1) {
									log.info("商品编号：" + commodity.getCommodityNo() + ",未超过16点，上架");
									commodityService.updateCommodityUpByNo(details.getCommodityNo());
								} else {
									log.info("商品编号：" + commodity.getCommodityNo() + ",已超过16点");
								}
							} else if (isTrue18) {
								// 如果未超过18点，此时可以上架,超过就不能上架
								if (DateUtil.compare_date(DateUtil.time2Str(new Date()), putOffShelvesTime_18) == -1) {
									log.info("商品编号：" + commodity.getCommodityNo() + ",未超过18点，上架");
									commodityService.updateCommodityUpByNo(details.getCommodityNo());
								} else {
									log.info("商品编号：" + commodity.getCommodityNo() + ",已超过18点");
								}
							} else {
								log.info("正常商品编号：" + commodity.getCommodityNo() + "已释放库存并上架");
								commodityService.updateCommodityUpByNo(details.getCommodityNo());
							}

						}
					}
				}
			}
		}

	}

	@Override
	public int updateOrderRefundTime(Integer orderId) {
		return dao.updateOrderRefundTime(orderId);
	}

	@Override
	public void updateWMShandleStatusByOrderNO(String orderNo) {
		if (orderNo.contains("_")) {
			orderNo = orderNo.split("_")[0];
			dao.updateWMShandleStatusByOrderNO(orderNo);
		}
	}

	@Override
	public int getTodayOrderNumber(Integer userId) {
		return dao.getTodayOrderNumber(userId);
	}

	@Override
	public void updateUserUsed(Integer counponId) {
		couponUserService.updateUserUsed(counponId);
	}

	@Override
	public XSSFWorkbook excelHebingskuOrderReportDetail(OrderVO orderVo) {

		// 创建HSSFWorkbook对象(excel的文档对象)
		XSSFWorkbook wkb = new XSSFWorkbook();

		// 建立新的sheet对象（excel的表单）
		XSSFSheet sheet = wkb.createSheet();
		sheet.setDefaultColumnWidth(15);
		// sheet.setDefaultRowHeightInPoints(20);

		// 在sheet里创建第1行
		XSSFRow row = sheet.createRow(0);

		// 创建单元格并设置单元格内容
		this.setDefaultXSSFRow1(row, sheet);

		XSSFCellStyle cellStyle = wkb.createCellStyle();

		XSSFFont font = wkb.createFont();
		font.setBold(true);
		cellStyle.setFont(font);
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_JUSTIFY);
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cellStyle.setFillForegroundColor(HSSFColor.TAN.index);
		// ============================================================================
		// 开始写入上报小区数据
		// ============================================================================
		if (orderVo.getResidentialList() != null && orderVo.getResidentialList().length < 1) {
			orderVo.setResidentialList(null);
		}
		List<Map<String, Object>> orderList = dao.excelHebingOrderReportDetail1(orderVo);

		int cellIndex = 0;
		int nextRow = 1;
		int unitRow = 5;
		String oldCommunity = "";
		String newCommunity = "";
		String oldUnit = "";
		String newUnit = "";
		String newUnitWithoutCommunity = "";
		Map<String, Object> orderMap = null;
		for (int i = 0, communityIndex = 1, len = orderList.size(); i < len; i++) {
			orderMap = orderList.get(i);

			newCommunity = getMapValue(orderMap.get("residential"));

			// 单元地址前添加小区名，保证唯一性
			newUnitWithoutCommunity = getMapValue(orderMap.get("unit"));
			newUnit = newCommunity + newUnitWithoutCommunity;

			// 空出2行填写小区名
			if (!oldCommunity.equals(newCommunity)) {
				oldCommunity = newCommunity;
				nextRow += 2;
				row = sheet.createRow(i + nextRow - 1);
				row.createCell(unitRow).setCellValue(newCommunity);
				row.getCell(unitRow).setCellStyle(cellStyle);

				// 合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
				sheet.addMergedRegion(new CellRangeAddress(i + nextRow - 1, i + nextRow - 1, unitRow, 13));

				// 不同小区需重新编号
				communityIndex = 1;
			}

			// 再空出一行填写单元名
			if (!oldUnit.equals(newUnit)) {
				oldUnit = newUnit;
				nextRow += 1;
				row = sheet.createRow(i + nextRow - 1);
				row.createCell(unitRow).setCellValue(newUnitWithoutCommunity);
				row.getCell(unitRow).setCellStyle(cellStyle);
			}

			cellIndex = 0;
			row = sheet.createRow(i + nextRow);
			row.createCell(cellIndex++).setCellValue(communityIndex++);

			row.createCell(cellIndex++).setCellValue(getMapValue(orderMap.get("createTime")));
			row.createCell(cellIndex++).setCellValue(getMapValue(orderMap.get("receiver")));
			row.createCell(cellIndex++).setCellValue(getMapValue(orderMap.get("mobilePhone")));
			row.createCell(cellIndex++).setCellValue(newCommunity);
			row.createCell(cellIndex++).setCellValue(getMapValue(orderMap.get("address")));
			row.createCell(cellIndex++).setCellValue(getMapValue(orderMap.get("orderdetails")));
			row.createCell(cellIndex++).setCellValue(countOrderNumer(getMapValue((orderMap.get("orderdetails")))));
			row.createCell(cellIndex++).setCellValue(getMapValue(orderMap.get("orderdetails1")));
			row.createCell(cellIndex++).setCellValue(countOrderNumer(getMapValue((orderMap.get("orderdetails1")))));
			row.createCell(cellIndex++).setCellValue(getMapValue(orderMap.get("orderdetails2")));
			row.createCell(cellIndex++).setCellValue(countOrderNumer(getMapValue((orderMap.get("orderdetails2")))));
			row.createCell(cellIndex++).setCellValue(getMapValue(orderMap.get("orderdetails3")));
			row.createCell(cellIndex++).setCellValue(countOrderNumer(getMapValue((orderMap.get("orderdetails3")))));
			row.createCell(cellIndex++).setCellValue(getMapValue(orderMap.get("orderdetails4")));
			row.createCell(cellIndex++).setCellValue(countOrderNumer(getMapValue((orderMap.get("orderdetails4")))));
			row.createCell(cellIndex++).setCellValue(getMapValue(orderMap.get("actualPayment")));
			row.createCell(cellIndex++).setCellValue(getMapValue(orderMap.get("feeWaiver")));
			row.createCell(cellIndex++).setCellValue(getMapValue(orderMap.get("totalMoney")));
		}
		return wkb;

	}

	@Override
	public List<HajOrder> listOrder(Criteria criteria) {
		return dao.listOrder(criteria);
	}

	@Override
	public XSSFWorkbook wmsExportShipOrder() {
		// 创建HSSFWorkbook对象(excel的文档对象)
		XSSFWorkbook wkb = new XSSFWorkbook();

		// 建立新的sheet对象（excel的表单）
		XSSFSheet sheet = wkb.createSheet();
		sheet.setDefaultColumnWidth(12);
		sheet.setDefaultRowHeightInPoints(20);

		// 在sheet里创建第1行
		XSSFRow row = sheet.createRow(0);

		// 创建单元格并设置单元格内容
		this.setDefaultXSSFRow4Wms(row);

		List<HashMap<String, Object>> list = dao.wmsExportShipOrder();
		Map<String, Object> map;

		int cellIndex = 0;
		for (int i = 0, len = list.size(); i < len; i++, cellIndex = 0) {
			map = list.get(i);
			row = sheet.createRow(i + 1);

			row.createCell(cellIndex++).setCellValue(getMapValue(map.get("userId")));
			row.createCell(cellIndex++).setCellValue(getMapValue(map.get("receiver")));
			row.createCell(cellIndex++).setCellValue(getMapValue(map.get("mobilePhone")));
			row.createCell(cellIndex++).setCellValue(getMapValue(map.get("postFee")));
			row.createCell(cellIndex++).setCellValue(getMapValue(map.get("commodityNo")));
			row.createCell(cellIndex++).setCellValue(getMapValue(map.get("sku")));
			row.createCell(cellIndex++).setCellValue(getMapValue(map.get("number")));

			// 目前只查深圳的数据，所以先写死
			row.createCell(cellIndex++).setCellValue("深圳市");
			row.createCell(cellIndex++).setCellValue(getMapValue(map.get("country")));
			row.createCell(cellIndex++).setCellValue(getMapValue(map.get("communityId")));
			row.createCell(cellIndex++).setCellValue(getMapValue(map.get("communityName")));
			row.createCell(cellIndex++).setCellValue(getMapValue(map.get("orderClassification")));
			row.createCell(cellIndex++).setCellValue(getMapValue(map.get("sorterName")));

			// 地址前添加楼栋单元排序【001】
			row.createCell(cellIndex++).setCellValue(setAddressWithSort(map));
		}

		return wkb;
	}

	@Override
	public List<Map<String, Object>> generatePurchaseOrder() {

		return dao.generatePurchaseOrder();
	}

	@Override
	public List<Map<String, Object>> generateSalesOrderOrder() {
		return dao.generateSalesOrderOrder();
	}

	@Override
	public List<Map<String, Object>> generateProductionOrder() {

		return dao.generateProductionOrder();
	}

	public List<HajOrder> getOrderByInviteList(String invitee) {
		return dao.getOrderByInviteList(invitee);
	}

	private void setDefaultXSSFRow4Wms(XSSFRow row) {
		int cellIndex = 0;

		// 初始化第一行标题
		row.createCell(cellIndex++).setCellValue("用户id");
		row.createCell(cellIndex++).setCellValue("收货人");
		row.createCell(cellIndex++).setCellValue("手机号码");
		row.createCell(cellIndex++).setCellValue("运费");
		row.createCell(cellIndex++).setCellValue("商品编码");
		row.createCell(cellIndex++).setCellValue("sku");
		row.createCell(cellIndex++).setCellValue("商品数量");
		row.createCell(cellIndex++).setCellValue("城市");
		row.createCell(cellIndex++).setCellValue("区域");
		row.createCell(cellIndex++).setCellValue("承运商编号");
		row.createCell(cellIndex++).setCellValue("小区");
		row.createCell(cellIndex++).setCellValue("商品分拣详情列");
		row.createCell(cellIndex++).setCellValue("分拣小组");
		row.createCell(cellIndex).setCellValue("地址");
	}

	/**
	 * wms要求地址前添加楼栋单元排序【0001】，位数不足4位时前面补0，楼层不足2位前面补0
	 */
	private String setAddressWithSort(Map<String, Object> map) {
		String result;

		String unit = getMapValue(map.get("unit"));
		String floor = getMapValue(map.get("floor"));
		String houseNumber = getMapValue(map.get("houseNumber"));
		String address = unit + floor + "层" + houseNumber;

		String unitSort = getMapValue(map.get("unitSort"));

		result = "【" + unitSort + "】" + address;
		return result;
	}

	@Override
	public List<HajOrder> wmsSaleOrderList() {
		return dao.wmsSaleOrderList();
	}

}
