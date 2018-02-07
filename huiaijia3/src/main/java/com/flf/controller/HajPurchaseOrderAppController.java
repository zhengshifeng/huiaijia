package com.flf.controller;

import com.base.util.HttpUtil;
import com.flf.entity.HajCommodityType;
import com.flf.entity.HajPurchaseOrder;
import com.flf.entity.HajSupplyChain;
import com.flf.entity.User;
import com.flf.service.HajCommodityTypeService;
import com.flf.service.HajOrderService;
import com.flf.service.HajPurchaseOrderService;
import com.flf.service.HajSupplyChainService;
import com.flf.util.*;
import com.flf.view.PurchaseExcelView;
import com.flf.view.PurchaseSaleExcelView;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

/**
 * 
 * <br>
 * <b>功能：</b>HajPurchaseOrderAppController<br>
 * <br>
 */
@Controller
@RequestMapping(value = "/purchaseOrder")
public class HajPurchaseOrderAppController {

	@Autowired(required = false)
	private HajPurchaseOrderService hajPurchaseOrderService;

	@Autowired
	private HajCommodityTypeService commodityTypeService;

	@Autowired
	private HajOrderService hajOrderService;

	@Autowired
	private HajSupplyChainService supplyChainService;

	/**
	 * 后台查询订单列表
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/getAll")
	public String list(HttpSession session, HajPurchaseOrder purchase, Model model) {
		try {
			User user = (User) session.getAttribute(Const.SESSION_USER);
			if (null != user) {
				purchase.setAreaCode(user.getAreaCode());
			}
			List<Map<String, Object>> purchaseList = hajPurchaseOrderService.listPageOrder(purchase);
			model.addAttribute("purchaseList", purchaseList);
			model.addAttribute("purchase", purchase);
			Map<String, Object> totalPurchase = hajPurchaseOrderService.queryTotalPurchase(purchase);
			model.addAttribute("totalPurchase", totalPurchase);

			String typeId = purchase.getCommodityAttr();
			if (Tools.notEmpty(typeId) && !"0".equals(typeId)) {
				List<HajCommodityType> typeList = commodityTypeService.getBigTypeByattr(typeId);
				model.addAttribute("parentTypeList", typeList);
				if (purchase.getbTypeName() != "") {
					Integer parentId = Integer.parseInt(purchase.getbTypeName());
					typeList = commodityTypeService.getTypeByParentId(parentId);
				}
				model.addAttribute("subTypeList", typeList);
			}

			// 供应商列表
			List<HajSupplyChain> supplyChainNames = supplyChainService.getSupplyChainNames();
			model.addAttribute("supplyChainNames", supplyChainNames);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "purchaseList";
	}

	/**
	 * 导出采购信息到excel
	 * 
	 * @return
	 */
	@RequestMapping(value = "/excel")
	public ModelAndView export2Excel(HttpSession session, HajPurchaseOrder purchase) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("商品编号");
		titles.add("属性");
		titles.add("大类");
		titles.add("小类");
		titles.add("商品名称");
		titles.add("供应商");
		titles.add("数量");
		titles.add("采购单价");
		titles.add("采购合计");
		titles.add("采购时间");
		titles.add("规格");
		titles.add("好评");
		dataMap.put("titles", titles);

		User user = (User) session.getAttribute(Const.SESSION_USER);
		if (null != user) {
			purchase.setAreaCode(user.getAreaCode());
		}

		List<Map<String, Object>> purchaseList = hajPurchaseOrderService.listAllpurchaseList(purchase);
		dataMap.put("purchaseList", purchaseList);
		PurchaseExcelView erv = new PurchaseExcelView();
		ModelAndView mv = new ModelAndView(erv, dataMap);
		return mv;
	}

	@RequestMapping(value = "/salexcel")
	public ModelAndView salexcel(HttpSession session, HajPurchaseOrder purchase) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("商品编号");
		titles.add("属性");
		titles.add("大类");
		titles.add("小类");
		titles.add("商品名称");
		titles.add("供应商");
		titles.add("数量");
		titles.add("采购单价");
		titles.add("采购合计");
		titles.add("采购时间");
		titles.add("规格");
		titles.add("售出合计");
		titles.add("好评");
		titles.add("物料编码");
		dataMap.put("titles", titles);

		User user = (User) session.getAttribute(Const.SESSION_USER);
		if (null != user) {
			purchase.setAreaCode(user.getAreaCode());
		}

		List<Map<String, Object>> purchaseList = hajPurchaseOrderService.listAllpurchaseList(purchase);
		dataMap.put("purchaseList", purchaseList);
		PurchaseSaleExcelView erv = new PurchaseSaleExcelView();
		ModelAndView mv = new ModelAndView(erv, dataMap);
		return mv;
	}

	/**
	 * 合并订单导出
	 * 
	 * @param response
	 */
	@RequestMapping(value = "/excelHebingDetail")
	public void excelHebingDetail(HttpSession session, HajPurchaseOrder purchase, HttpServletResponse response,
			HttpServletRequest request) {
		try {
			String filename = "采购单";
			filename = HttpUtil.encodeFilename(request, filename);
			filename = DateUtil.dateformat(new Date(), "MMdd") + filename;

			User user = (User) session.getAttribute(Const.SESSION_USER);
			if (null != user) {
				purchase.setAreaCode(user.getAreaCode());
			}

			XSSFWorkbook wb = hajPurchaseOrderService.excelHebingDetail(purchase);
			ExcelUtil.output(response, filename, wb);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 后台查询订单列表
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/getToDayPurchaseList")
	public String getToDayPurchaseList(HttpSession session, HajPurchaseOrder purchase, Model model) {
		try {

			User user = (User) session.getAttribute(Const.SESSION_USER);
			if (null != user) {
				purchase.setAreaCode(user.getAreaCode());
			}

			List<Map<String, Object>> purchaseList = hajPurchaseOrderService.listPageToDayPurchaseList(purchase);
			model.addAttribute("purchaseList", purchaseList);
			model.addAttribute("purchase", purchase);
			Map<String, Object> totalPurchase = hajPurchaseOrderService.queryTotalToDayPurchaseList(purchase);
			model.addAttribute("totalPurchase", totalPurchase);

			String typeId = purchase.getCommodityAttr();
			if (Tools.notEmpty(typeId) && !"0".equals(typeId)) {
				List<HajCommodityType> typeList = commodityTypeService.getBigTypeByattr(typeId);
				model.addAttribute("parentTypeList", typeList);
				if (purchase.getbTypeName() != "") {
					Integer parentId = Integer.parseInt(purchase.getbTypeName());
					typeList = commodityTypeService.getTypeByParentId(parentId);
				}
				model.addAttribute("subTypeList", typeList);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "to_day_purchaseList";
	}

	/**
	 * 导出自主加工商品今日采购表
	 * 
	 * @param response
	 */
	@RequestMapping(value = "/excelToDayPurchase")
	public void excelToDayPurchase(HajPurchaseOrder purchase, HttpServletResponse response, HttpServletRequest request) {
		try {
			String filename = "自主加工采购单";
			filename = HttpUtil.encodeFilename(request, filename);
			filename = DateUtil.dateformat(new Date(), "MMdd") + filename;
			XSSFWorkbook wb = hajPurchaseOrderService.excelToDayPurchase(purchase);
			ExcelUtil.output(response, filename, wb);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 恢复当天的采购单数据
	 */
	@RequestMapping(value = "/getOrderByDateTime")
	public void getOrderByDateTime(String dateTime, HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		if (null != dateTime && !"".equals(dateTime)) {
			List<HashMap<String, Object>> orderMapList = hajOrderService.getOrderByDateTime(dateTime);
			for (Map<String, Object> obj : orderMapList) {

				if (null != obj && obj.size() > 0) {
					// 插入采购单号
					obj.put("dateTime", dateTime);
					int result = hajPurchaseOrderService.savePurchaseOrderDateTime(obj);
				}
			}
			jsonMap.put("error", "0");
			jsonMap.put("msg", "成功");

		} else {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "商品名称为空");
		}

		try {
			JSONUtils.printObject(jsonMap, response);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
