package com.flf.controller;

import com.base.util.HttpUtil;
import com.flf.constant.HajOrderProblemType;
import com.flf.constant.HajSaleDeptConstant;
import com.flf.entity.*;
import com.flf.resolver.RollbackException;
import com.flf.service.*;
import com.flf.util.*;
import com.flf.view.OrderListExcelView;
import com.flf.vo.HajOrderSaleVo;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * <br>
 * <b>功能：</b>HajOrderController<br>
 * <br>
 */
@Controller
@RequestMapping(value = "/order")
public class HajOrderController {
	private final static Logger log = Logger.getLogger(HajOrderController.class);

	@Autowired
	private HajOrderService hajOrderService;

	@Autowired
	private HajOrderDetailsService hajOrderDetailsService;

	@Autowired(required = false)
	private HajCommodityService commodityService;

	@Autowired
	private RedisSpringProxy redisSpringProxy;

	@Autowired
	private HajCommunitySorterService communitySorterService;

	@Autowired(required = false)
	private HajOrderSaleService orderSaleService;

	@Autowired(required = false)
	private HajOrderProblemService problemService;

	@Autowired(required = false)
	private HajSaleDeptService saleDeptService;

	@Autowired(required = false)
	private HajFrontUserService frontUserService;

	/**
	 * 后台查询订单列表
	 *
	 * @return
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(HttpSession session, OrderVO orderVo) {
		ModelAndView mv = new ModelAndView();
		try {
			User user = (User) session.getAttribute(Const.SESSION_USER);
			if (null != user) {
				orderVo.setAreaCode(user.getAreaCode());
			}
			List<Map<String, Object>> orderList = hajOrderService.listAllOrder(orderVo);
			mv.addObject("orderList", orderList);
			mv.addObject("order", orderVo);

			/* 分拣小组列表数据 */
			HajCommunitySorter hajCommunitySorter = new HajCommunitySorter();
			Page page = new Page();
			page.setShowCount(100);
			hajCommunitySorter.setPage(page);
			if (null != user) {
				hajCommunitySorter.setAreaCode(user.getAreaCode());
			}
			List<HajCommunitySorter> sorterList = communitySorterService.listPage(hajCommunitySorter);
			mv.addObject("sorterList", sorterList);
			mv.setViewName("orderlist");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

	/**
	 * 后台查询订单列表
	 *
	 * @return
	 */
	@RequestMapping(value = "/deliveryOrder")
	public ModelAndView deliveryOrder(HttpSession session, OrderVO orderVo) {
		ModelAndView mv = new ModelAndView();
		try {
			User user = (User) session.getAttribute(Const.SESSION_USER);
			if (null != user) {
				orderVo.setAreaCode(user.getAreaCode());
			}
			orderVo.setStatus(2);
			List<Map<String, Object>> orderList = hajOrderService.listAllOrder(orderVo);

			mv.addObject("orderList", orderList);
			mv.addObject("order", orderVo);
			mv.setViewName("deliveryOrderList");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

	/**
	 * 后台删除订单
	 *
	 * @param orderId
	 */
	@RequestMapping(value = "/del")
	public void delete(@RequestParam Integer orderId, HttpServletResponse httpServletResponse) {
		try {
			hajOrderService.deleteOrderById(orderId);
			JSONUtils.printStr("1", httpServletResponse);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 订单详情
	 *
	 * @param orderId
	 */
	@RequestMapping(value = "/orderDetail")
	public ModelAndView orderDetail(@RequestParam int orderId, HttpSession session) throws Exception {
		ModelAndView mv = new ModelAndView();

		// 获取订单信息
		Map<String, Object> orderMap = hajOrderService.getOrderInfoById(orderId);

		// 获取订单详情信息
		List<Map<String, Object>> detailsList = hajOrderDetailsService.listAllOrderDetails(orderId);

		List<HajOrderProblem> orderProblemList = problemService.listByOrderNo(String.valueOf(orderMap.get("orderNo")));
		StringBuilder depts;
		HajOrderProblem problem;
		for (int i = 0; i < orderProblemList.size(); i++) {
			problem = orderProblemList.get(i);
			if (problem.getObj() == 0) {
				depts = new StringBuilder();
				for (HajSaleDept dept : problem.getDeptList()) {
					depts.append(HajSaleDeptConstant.getSaleDept(dept.getDept())).append(',');
				}
				problem.setDept(depts.toString());
			} else {
				// 去掉非整单售后的记录
				orderProblemList.remove(i--);
			}
		}

		HajOrder hajOrder = hajOrderService.queryById(orderId);
		BigDecimal totalRefunds = orderSaleService.getThisOrderSaleTotalRefunds(orderId);
		BigDecimal actualPayment = hajOrder.getActualPayment().add(hajOrder.getDispensingTip())
				.add(hajOrder.getPostFee());

		mv.addObject("orderMap", orderMap);
		mv.addObject("detailsList", detailsList);
		mv.addObject("user", session.getAttribute(Const.SESSION_USER));
		mv.addObject("maxRefundAmount", actualPayment.subtract(totalRefunds));
		mv.addObject("orderProblemList", orderProblemList);
		mv.setViewName("order_details");
		return mv;
	}

	/**
	 * 导出excel
	 */
	@RequestMapping(value = "/excelOrderDetail")
	public ModelAndView excelOrderDetail(HttpSession session, OrderVO orderVo) {
		Map<String, Object> dataMap = new HashMap<String, Object>();

		User user = (User) session.getAttribute(Const.SESSION_USER);
		if (null != user) {
			orderVo.setAreaCode(user.getAreaCode());
		}
		List<String> titles = new ArrayList<String>();
		titles.add("序号");
		titles.add("姓名");
		titles.add("小区");
		titles.add("地址");
		titles.add("订单详情A");
		titles.add("订单详情B");
		titles.add("订单详情C");
		titles.add("订单详情D");
		titles.add("订单详情E");
		titles.add("订单金额");
		titles.add("节省金额");
		titles.add("总价");
		titles.add("手机号码");
		titles.add("订单类型");
		titles.add("备注");
		dataMap.put("titles", titles);
		List<Map<String, Object>> orderList = hajOrderService.listAllordreList(orderVo);
		dataMap.put("orderList", orderList);
		OrderListExcelView erv = new OrderListExcelView();
		ModelAndView mv = new ModelAndView(erv, dataMap);
		return mv;
	}

	@RequestMapping(value = "/excelOrderReportDetail")
	public void excelOrderReportDetail(HttpSession session, OrderVO orderVo, HttpServletResponse response,
			HttpServletRequest request) {
		try {
			String filename = "批量报表";
			filename = HttpUtil.encodeFilename(request, filename);
			filename = DateUtil.dateformat(new Date(), "MM-dd") + filename;

			User user = (User) session.getAttribute(Const.SESSION_USER);
			if (null != user) {
				orderVo.setAreaCode(user.getAreaCode());
			}

			XSSFWorkbook wb = hajOrderService.excelOrderReportDetail(orderVo);
			ExcelUtil.output(response, filename, wb);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 合并订单导出
	 *
	 * @param orderVo
	 * @param response
	 */
	@RequestMapping(value = "/excelHebingOrderDetail")
	public void excelHebingOrderDetail(HttpSession session, OrderVO orderVo, HttpServletResponse response,
			HttpServletRequest request) {
		try {
			String filename = "订单详情";
			filename = HttpUtil.encodeFilename(request, filename);
			filename = DateUtil.dateformat(new Date(), "MM-dd") + filename;

			User user = (User) session.getAttribute(Const.SESSION_USER);
			if (null != user) {
				orderVo.setAreaCode(user.getAreaCode());
			}

			XSSFWorkbook wb = hajOrderService.excelHebingOrderReportDetail(orderVo);
			ExcelUtil.output(response, filename, wb);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 合并订单导出(sku)
	 *
	 * @param orderVo
	 * @param response
	 */
	@RequestMapping(value = "/excelHebingskuOrderDetail")
	public void excelHebingskuOrderDetail(HttpSession session, OrderVO orderVo, HttpServletResponse response,
			HttpServletRequest request) {
		try {
			String filename = "订单详情";
			filename = HttpUtil.encodeFilename(request, filename);
			filename = DateUtil.dateformat(new Date(), "MM-dd") + filename;

			User user = (User) session.getAttribute(Const.SESSION_USER);
			if (null != user) {
				orderVo.setAreaCode(user.getAreaCode());
			}

			XSSFWorkbook wb = hajOrderService.excelHebingskuOrderReportDetail(orderVo);
			ExcelUtil.output(response, filename, wb);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 后台计算订单成本价
	 */
	@RequestMapping(value = "/calOrderDetailCostFee")
	public void calOrderDetailCostFee(HttpServletResponse response) {
		List<HajOrder> orderList = hajOrderService.getTodayPayOrder();
		for (HajOrder order : orderList) {
			hajOrderService.updateOrderCostPrice(order);
		}
		log.info("后台计算订单成本价已完成");
	}

	/**
	 * 后台下单
	 */
	@RequestMapping(value = "/addOrder")
	public ModelAndView addOrder(HttpSession session) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("user", session.getAttribute(Const.SESSION_USER));
		mv.addObject("nowdate", DateUtil.getSpecifiedDayAfter(DateUtil.date2Str(new Date())));
		mv.setViewName("addOrder");
		return mv;
	}

	/**
	 * 补单成功之后扣减库存
	 */
	@RequestMapping(value = "/budanInventoryReduce")
	public void budanInventoryReduce(String orderNo, HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			HajOrder thisOrder = hajOrderService.getOrderByOrderNo(orderNo);
			if (null != thisOrder) {
				boolean inventoryIsEnough = commodityService.updateInventoryReduce(thisOrder);
				if (inventoryIsEnough) {
					log.info("补单，订单N号：" + orderNo + "[补单更新成功！]");
					jsonMap.put("error", "0");
					jsonMap.put("orderId", thisOrder.getId());
					jsonMap.put("msg", "补单更新成功！");
				}
			}
		} catch (ArithmeticException e) {
			// 下架商品的操作需要在此处完成，因为service中抛异常时所有操作都会回滚
			String[] messages = e.getMessage().split(";");
			if (messages.length > 1) {
				log.info("库存不足，下架" + messages[1]);
				commodityService.updateSoldOut(messages[1]);
				HajCommodityUtil.resetCommodityRedisAndESIndex(commodityService);
			}

			log.info("订单：" + orderNo + "中，" + messages[0]);
			jsonMap.put("error", "2");
			jsonMap.put("msg", messages[0]);
		} catch (Exception e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
		} finally {
			try {
				JSONUtils.printObject(jsonMap, response);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

	}

	/**
	 * (后台)订单取消接口 orderNo 订单号
	 */
	@RequestMapping(value = "/cancleOrder")
	public void cancleOrder(@RequestParam String orderNo, HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		log.info("进入后台订单取消接口中orderNo：" + orderNo);
		try {
			String orderCancleTime = (String) redisSpringProxy.read("SystemConfiguration_orderCancleTime");
			HajOrder order = hajOrderService.getOrderByOrderNo(orderNo);

			// 团购订单或者当前时间允许下单则通过
			if (order.getIsGrouponOrder() == 1
					|| (DateUtil.compare_date(DateUtil.time2Str(new Date()), orderCancleTime) == -1 && DateUtil
							.isToday(order.getCreateTime(), DateUtil.datetime2Str(new Date())))) {

				// 取消订单
				order = hajOrderService.updateCancelOrder(orderNo);

				if (null != order && order.getId() > 0) {
					jsonMap.put("error", "0");
					jsonMap.put("msg", "取消成功！");
				} else {
					jsonMap.put("error", "0");
					jsonMap.put("msg", "取消失败！");
				}
			} else {
				jsonMap.put("error", "3");
				jsonMap.put("msg", "抱歉，今日已截止取消订单！");
			}

		} catch (Exception e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "取消失败！");
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
	 * 根据用户手机号查看是否有待配送的补单
	 *
	 * @param mobile
	 *            用户手机号
	 * @param response
	 */
	@RequestMapping(value = "/getBuDanByMobile")
	public void getBuDanByMobile(@RequestParam String mobile, HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			HajOrder order = hajOrderService.getBuDanByMobile(mobile);
			if (order != null) {
				jsonMap.put("msg", "success");
				jsonMap.put("order", order);
			} else {
				jsonMap.put("msg", "none");
			}
		} catch (Exception e) {
			jsonMap.put("msg", "error");
			log.info(e.getMessage(), e);
		} finally {
			try {
				JSONUtils.printObject(jsonMap, response);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	@RequestMapping(value = "/batchImport", method = RequestMethod.POST)
	public void batchImport(@RequestParam("fileName") MultipartFile file, HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("application/json;charset=UTF-8");
		String limitOrderTime = (String) redisSpringProxy.read("SystemConfiguration_backgroud_orderdownTime");

		Map<String, Object> resultMap = new HashMap<>();
		try {
			if (DateUtil.compare_date(DateUtil.time2Str(new Date()), limitOrderTime) == -1) {
				String subPath = "xlsx" + File.separator + "order" + File.separator;
				String filePath = FileUpload.uploadExcel(file, subPath);
				String result = hajOrderService.batchImport(filePath, request);
				resultMap.put("result", result);
			} else {
				resultMap.put("result", "已过下单时间");
			}
		} catch (RollbackException | ArithmeticException e) {
			log.info(e.getMessage());
			resultMap.put("result", e.getMessage());
		} catch (Exception e) {
			log.info(e.getMessage(), e);
		} finally {
			try {
				JSONUtils.printObject(resultMap, response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@RequestMapping(value = "/reorder")
	public void reorder(HttpServletResponse response, HttpSession session, HajOrderSaleVo orderSale) {
		Map<String, Object> jsonMap = new HashMap<>();
		jsonMap.put("error", "0");
		jsonMap.put("msg", "补发成功");
		String orderNo = null;
		try {
			if (orderSale != null && orderSale.getOrderId() != null) {
				HajOrder order = hajOrderService.queryById(orderSale.getOrderId());
				HajOrder reorder = initReorder(orderSale, order);

				orderNo = MakeOrderNum.makeOrderNum();
				HajFrontUser frontUser = frontUserService.queryById(order.getUserId());
				String makeOrderTime = (String) redisSpringProxy.read("SystemConfiguration_orderdownTime_"
						+ Tools.getAreaCode(frontUser.getAreaCode()));

				if (DateUtil.compare_date(DateUtil.time2Str(new Date()), makeOrderTime) == -1) {
					int addStatus;

					if (Tools.isNotEmpty(frontUser.getResidential())
							&& Tools.isNotEmpty(frontUser.getAddress())
							&& Tools.isNotEmpty(frontUser.getReceiver())) {
						addStatus = hajOrderService.saveHajOrder(reorder, orderNo);
					} else {
						addStatus = 10;
					}

					if (addStatus == 0) {
						boolean inventoryIsEnough = commodityService.updateInventoryReduce(reorder);
						log.info("订单[" + orderNo + "]补单成功！扣减库存结果：" + inventoryIsEnough);

						User user = (User) session.getAttribute(Const.SESSION_USER);

						// 写入订单问题表，valid先设为0，在售后订单中确认退款后再设为1
						HajOrderProblem orderProblem = new HajOrderProblem();
						orderProblem.setMethod(0); // 补发
						orderProblem.setType(orderSale.getType());
						orderProblem.setValid(1);
						orderProblem.setOrderNo(order.getOrderNo());
						orderProblem.setObj(orderSale.getObj());
						orderProblem.setCreateTime(new Date());
						orderProblem.setCommodityNo(orderSale.getCommodityNo());
						orderProblem.setNumber(orderSale.getNumber());
						orderProblem.setOperator(user.getUsername());

						StringBuilder record = new StringBuilder();
						record.append(DateUtil.datetime2Str(orderProblem.getCreateTime())).append('，');
						if (Tools.isEmpty(orderSale.getCommodityNo())) {
							record.append("补发整单").append('，');
							if (orderProblem.getType() != null) {
								record.append(HajOrderProblemType.getType(orderProblem.getType())).append('，');
							}
							record.append(orderSale.getQuersion());
						} else {
							orderProblem.setPic(orderSale.getPic());
							record.append("补发商品【").append(orderSale.getCommodityName());
							record.append('*').append(orderProblem.getNumber()).append("】，");
							if (orderProblem.getType() != null) {
								record.append(HajOrderProblemType.getType(orderProblem.getType())).append('，');
							}
							if (orderSale.getDepts() != null) {
								for (Integer deptId : orderSale.getDepts()) {
									record.append(HajSaleDeptConstant.getSaleDept(deptId)).append(',');
								}
							}
						}
						record.append("-->by: ").append(orderProblem.getOperator());
						orderProblem.setRecord(record.toString());

						// 写入订单问题表，记录次问题是哪次售后操作
						orderProblem.setRefundNo(orderNo);
						int addProblemStatus = problemService.add(orderProblem);

						if (addProblemStatus > 0) {
							HajSaleDept saleDept;
							if (orderSale.getDepts() != null) {
								for (Integer dept : orderSale.getDepts()) {
									saleDept = new HajSaleDept();
									saleDept.setProblemId(orderProblem.getId());
									saleDept.setDept(dept);
									saleDept.setCreateTime(orderProblem.getCreateTime());
									saleDeptService.add(saleDept);
								}
							}

							if (orderSale.getOrderDetailId() != null) {
								// 更新单个商品售后记录
								HajOrderDetails orderDetails = null;
								for (HajOrderDetails details : order.getOrderDetailList()) {
									if (orderSale.getOrderDetailId().equals(details.getId())) {
										orderDetails = details;
										break;
									}
								}

								HajOrderDetails detailsUpdate = new HajOrderDetails();
								detailsUpdate.setId(orderSale.getOrderDetailId());
								if (orderDetails != null && orderDetails.getAfterSaleRecord() != null) {
									detailsUpdate.setAfterSaleRecord(orderDetails.getAfterSaleRecord() + "补发*"
											+ orderSale.getNumber() + ";");
								} else {
									detailsUpdate.setAfterSaleRecord("补发*" + orderSale.getNumber() + ";");
								}
								hajOrderDetailsService.updateBySelective(detailsUpdate);
							}
						}
					}

					String msg;
					switch (addStatus) {
					case 0:
						msg = "成功";
						break;
					case 1:
						msg = "未知异常";
						break;
					case 4:
						msg = "该客户非正式会员，请核实。";
						break;
					case 5:
						msg = "订单中包含下架商品";
						break;
					case 6:
						msg = "订单中包含价格变动商品";
						break;
					case 9:
						msg = "订单中包含异地商品";
						break;
					case 10:
						msg = "该客户地址信息未完善，请让完善后再进行售后处理。";
						break;
					default:
						msg = "失败";
						break;
					}
					jsonMap.put("error", String.valueOf(addStatus));
					jsonMap.put("msg", msg);

				} else {
					jsonMap.put("error", "7");
					jsonMap.put("msg", "抱歉，今日已截止下单！");
				}
				jsonMap.put("orderNo", orderNo);
			} else {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "订单为空");
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
	 * 生成补单数据
	 * 
	 * @param orderSale
	 * @param order
	 * @return
	 */
	private HajOrder initReorder(HajOrderSaleVo orderSale, HajOrder order) {
		HajOrder reorder = new HajOrder();
		reorder.setDeliveryTime(DateUtil.getDeliveryTime());
		reorder.setPoints(0);
		reorder.setReceiver(order.getReceiver());
		reorder.setDispensingTip(BigDecimal.ZERO);
		reorder.setContactPhone(order.getContactPhone());
		reorder.setResidential(order.getResidential());
		reorder.setUserId(order.getUserId());
		reorder.setAddress(order.getAddress());
		reorder.setRemark(orderSale.getQuersion());
		reorder.setActualPayment(BigDecimal.ZERO);
		reorder.setIsGrouponOrder(2);
		reorder.setPostFee(BigDecimal.ZERO);
		reorder.setApplication(2);
		List<HajOrderDetails> reorderDetailList = new ArrayList<>();

		HajOrderDetails orderDetail;
		HajOrderDetails reorderDetail;
		BigDecimal totalMoney = BigDecimal.ZERO;
		for (int i = 0; i < order.getOrderDetailList().size(); i++) {
			orderDetail = order.getOrderDetailList().get(i);

			if (orderSale.getOrderDetailId() != null) {
				if (orderSale.getOrderDetailId().equals(order.getOrderDetailList().get(i).getId())) {
					orderDetail = order.getOrderDetailList().get(i);
					orderDetail.setNumber(orderSale.getNumber());
				} else {
					order.getOrderDetailList().remove(i--);
					continue;
				}
			}

			reorderDetail = new HajOrderDetails();
			reorderDetail.setCommodityName(orderDetail.getCommodityName());
			reorderDetail.setNumber(orderDetail.getNumber());
			reorderDetail.setCommodityListPrice(BigDecimal.ZERO);
			reorderDetail.setSource("0");
			reorderDetail.setAllSource("1");
			reorderDetail.setCommodityType(1);
			reorderDetail.setActualPayment(BigDecimal.ZERO);

			// 累加订单总额
			totalMoney = totalMoney.add(orderDetail.getCommodityListPrice());
			reorderDetailList.add(reorderDetail);
		}
		reorder.setTotalMoney(totalMoney);
		if (orderSale.getCommodityNo() != null) {
			reorder.setNumber(orderSale.getNumber());
			reorder.setTotalMoney(totalMoney.multiply(new BigDecimal(reorder.getNumber())));
		} else {
			reorder.setNumber(reorderDetailList.size());
		}
		reorder.setFeeWaiver(reorder.getTotalMoney());
		reorder.setOrderDetailList(reorderDetailList);
		reorder.setToDayOrderNumber("0");
		return reorder;
	}

	@RequestMapping(value = "/wmsExportShipOrder")
	public void wmsExportShipOrder(HttpServletRequest request, HttpServletResponse response) {
		try {
			String filename = "wms发货单数据";
			filename = HttpUtil.encodeFilename(request, filename);
			filename = DateUtil.dateformat(new Date(), "MM-dd-") + filename;

			XSSFWorkbook wb = hajOrderService.wmsExportShipOrder();
			ExcelUtil.output(response, filename, wb);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
