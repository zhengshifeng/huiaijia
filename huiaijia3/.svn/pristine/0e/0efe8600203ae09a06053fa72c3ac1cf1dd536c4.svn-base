package com.flf.controller;

import com.flf.constant.HajOrderProblemType;
import com.flf.constant.HajSaleDeptConstant;
import com.flf.entity.*;
import com.flf.resolver.RollbackException;
import com.flf.service.*;
import com.flf.util.*;
import com.flf.vo.HajOrderSaleDTO;
import com.flf.vo.HajOrderSaleVo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 售后退款controller
 */
@Controller
@RequestMapping(value = "/orderSale")
public class HajOrderSaleController {

	private final static Logger log = Logger.getLogger(HajOrderSaleController.class);

	@Autowired(required = false)
	private HajOrderSaleService service;

	@Autowired(required = false)
	private HajOrderService orderService;

	@Autowired(required = false)
	private HajOrderDetailsService orderDetailsService;

	@Autowired(required = false)
	private HajOrderProblemService problemService;

	@Autowired(required = false)
	private HajSaleDeptService saleDeptService;

	/**
	 * 新增售后退款单
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public void saveSaleOrder(HajOrderSaleVo sale, HttpServletResponse response, HttpSession session) {
		Map<String, Object> jsonMap = new HashMap<>();
		try {
			HajOrder order = orderService.queryById(sale.getOrderId());
			User user = (User) session.getAttribute(Const.SESSION_USER);

			// 已申请的退款金额
			BigDecimal totalRefunds = service.getThisOrderSaleTotalRefunds(sale.getOrderId());
			totalRefunds = totalRefunds.add(sale.getMoney());
			if (Tools.isEmpty(sale.getCommodityNo())) { // 整单商品售后
				BigDecimal actualPayment = order.getActualPayment().add(order.getDispensingTip())
						.add(order.getPostFee());

				// 退款总额不能大于订单实付金额
				if (sale.getMoney().compareTo(BigDecimal.ZERO) <= 0 || totalRefunds.compareTo(actualPayment) > 0) {
					jsonMap.put("error", "3");
					jsonMap.put("msg", "该笔订单最多可退￥" + actualPayment.subtract(totalRefunds).add(sale.getMoney()));
				} else {
					// 写入订单问题表，valid先设为0，在售后订单中确认退款后再设为1
					add2orderProblem(sale, jsonMap, order, user);
				}
			} else { // 单个商品售后
				HajOrderDetails orderDetails = null;
				for (int i = 0; i < order.getOrderDetailList().size(); i++) {
					if (sale.getOrderDetailId().equals(order.getOrderDetailList().get(i).getId())) {
						orderDetails = order.getOrderDetailList().get(i);
					} else {
						order.getOrderDetailList().remove(i--);
					}
				}

				if (orderDetails != null) {
					BigDecimal actualPayment = orderDetails.getActualPayment();

					// 退款总额不能大于商品实收金额
					if (sale.getMoney().compareTo(BigDecimal.ZERO) <= 0
							|| sale.getMoney().compareTo(actualPayment) > 0) {
						jsonMap.put("error", "3");
						jsonMap.put("msg", "该商品最多可退￥" + actualPayment);
					} else {
						// 单个商品售后时退款金额不能超过订单金额
						// 订单实付金额
						BigDecimal orderActualPayment = order.getActualPayment()
								.add(order.getDispensingTip())
								.add(order.getPostFee());
						if (sale.getMoney().compareTo(orderActualPayment) > 0) {
							jsonMap.put("error", "3");
							jsonMap.put("msg", "该订单最多可退￥" + orderActualPayment);
						} else {
							if (orderActualPayment.compareTo(totalRefunds) >= 0) {
								// 写入订单问题表，valid先设为0，在售后订单中确认退款后再设为1
								add2orderProblem(sale, jsonMap, order, user);
							} else {
								// 单个商品退款金额不得大于订单实付总额
								BigDecimal refundMoney = orderActualPayment.subtract(totalRefunds.subtract(sale.getMoney()));
								jsonMap.put("error", "3");
								if (refundMoney.compareTo(BigDecimal.ZERO) > 0) {
									jsonMap.put("msg", "该订单最多可退￥" + refundMoney);
								} else {
									jsonMap.put("msg", "该订单可退款金额已达上限");
								}
							}
						}
					}
				} else {
					jsonMap.put("error", "4");
					jsonMap.put("msg", "订单中更没有此商品！");
				}
			}
		} catch (Exception e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常！");
			log.info(e.getMessage(), e);
		} finally {
			try {
				JSONUtils.printObject(jsonMap, response);
			} catch (IOException e) {
				log.info(e.getMessage(), e);
			}
		}
	}

	private void add2orderProblem(HajOrderSaleVo sale, Map<String, Object> jsonMap, HajOrder order, User user)
			throws Exception {
		HajOrderProblem orderProblem = new HajOrderProblem();
		orderProblem.setMethod(1); // 退款
		orderProblem.setType(sale.getType());
		orderProblem.setValid(0);
		orderProblem.setOrderNo(order.getOrderNo());
		orderProblem.setObj(sale.getObj());
		orderProblem.setCreateTime(new Date());
		orderProblem.setCommodityNo(sale.getCommodityNo());
		orderProblem.setNumber(sale.getNumber());
		orderProblem.setOperator(user.getUsername());

		StringBuilder record = new StringBuilder();

		record.append(DateUtil.datetime2Str(orderProblem.getCreateTime())).append('，');
		if (Tools.isEmpty(sale.getCommodityNo())) {
			record.append("整单退款").append('￥').append(sale.getMoney()).append('，');
			if (sale.getDepts() != null) {
				record.append(HajOrderProblemType.getType(orderProblem.getType())).append('，');
			}
			record.append(sale.getQuersion());
		} else {
			orderProblem.setPic(sale.getPic());
			record.append("【").append(sale.getCommodityName());
			record.append('*').append(orderProblem.getNumber()).append("】");
			record.append("退款￥").append(sale.getMoney()).append("，");
			record.append(HajOrderProblemType.getType(orderProblem.getType())).append('，');
			if (sale.getDepts() != null) {
				for (Integer deptId : sale.getDepts()) {
					record.append(HajSaleDeptConstant.getSaleDept(deptId)).append(',');
				}
			}
		}
		record.append("-->by: ").append(orderProblem.getOperator());
		orderProblem.setRecord(record.toString());

		// 售后退款单号
		String refundNo = MakeOrderNum.makeRefundNo();

		// 写入订单问题表，记录次问题是哪次售后操作
		orderProblem.setRefundNo(refundNo);
		int addStatus = problemService.add(orderProblem);

		if (addStatus > 0) {
			HajSaleDept saleDept;
			if (sale.getDepts() != null) {
				for (Integer dept : sale.getDepts()) {
					saleDept = new HajSaleDept();
					saleDept.setProblemId(orderProblem.getId());
					saleDept.setDept(dept);
					saleDept.setCreateTime(orderProblem.getCreateTime());
					saleDeptService.add(saleDept);
				}
			}
			sale.setRefundNo(refundNo);// 插入退款单号
			sale.setIsdeal(0);
			sale.setApplicant(user.getUsername());
			sale.setCreateTime(DateUtil.dateToString(new Date()));
			sale.setProblemId(orderProblem.getId());
			int id = service.add(sale);
			if (id > 0) {
				// 修改订单为待退款
				orderService.updateOrderSaleStatus(sale.getOrderId());
				jsonMap.put("error", "0");
				jsonMap.put("msg", "申请成功！");
			} else {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "申请失败！");
			}
		} else {
			jsonMap.put("error", "4");
			jsonMap.put("msg", "保存订单问题失败！");
		}
	}

	/**
	 * 删除售后订单
	 */
	@RequestMapping(value = "/deleteOrder")
	public void deleteOrder(@RequestParam Integer id,
							HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<>();
		try {
			HajOrderSale sale = service.queryById(id);
			if (null != sale) {
				orderService.updateOrderCompleteStatus(sale);
				service.delete(id);
			}
			jsonMap.put("error", "1");
			jsonMap.put("msg", "删除成功！");
		} catch (Exception e) {
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
	 * 查询售后退款单
	 */
	@RequestMapping(value = "/getAll")
	public String list(HajOrderSaleDTO sale, HttpSession session, Model model) {
		User user = (User) session.getAttribute(Const.SESSION_USER);
		if (null != user) {
			sale.setAreaCode(user.getAreaCode());
		}
		List<Map<String, Object>> saleList = service.listPageHajOrderSale(sale);
		model.addAttribute("saleList", saleList);
		model.addAttribute("sale", sale);
		return "salelist";
	}

	/**
	 * 确认退款
	 */
	@RequestMapping(value = "/saleRefund")
	public void saleRefund(@RequestParam Integer[] saleIds, HttpServletResponse response, HttpSession session) {
		User user = (User) session.getAttribute(Const.SESSION_USER);

		Map<String, Object> jsonMap = new HashMap<>();
		try {
			for (Integer saleId : saleIds) {
				jsonMap = service.updateSaleRefund(saleId, jsonMap, user);
			}
		} catch (FileNotFoundException e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "微信证书异常");
			log.info(e.getMessage(), e);
		} catch (RollbackException e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", e.getMessage());
			log.info(e.getMessage());
		} catch (Exception e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常！");
			log.info(e.getMessage(), e);
		} finally {
			try {
				JSONUtils.printObject(jsonMap, response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@RequestMapping(value = "/afterSaleEdit")
	public ModelAndView afterSaleEdit(@RequestParam Integer orderId,
									  @RequestParam String detailId,
									  @RequestParam String commodityNo) throws Exception {
		ModelAndView mv = new ModelAndView();

		// 获取订单信息
		Map<String, Object> orderMap = orderService.getOrderInfoById(orderId);
		if (orderMap != null) {
			// 获取订单详情信息
			List<Map<String, Object>> detailsList = orderDetailsService.listAllOrderDetails(orderId);
			Map<String, Object> orderDetails = null;
			for (Map<String, Object> map : detailsList) {
				if (detailId.equals(map.get("id").toString())) {
					orderDetails = map;
					break;
				}
			}

			List<HajOrderProblem> orderProblemList = problemService.listByOrderNo(String.valueOf(orderMap
					.get("orderNo")));
			for (int i = 0; i < orderProblemList.size(); i++) {
				// 移除不属于该商品的问题列表
				if (!commodityNo.equals(orderProblemList.get(i).getCommodityNo())) {
					orderProblemList.remove(i--);
				}
			}

			mv.addObject("orderMap", orderMap);
			mv.addObject("detail", orderDetails);
			mv.addObject("orderProblemList", orderProblemList);
		}
		mv.setViewName("afterSaleEdit");
		return mv;
	}
}
