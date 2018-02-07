package com.flf.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.base.util.HttpUtil;
import com.flf.entity.HajCommodityType;
import com.flf.entity.HajCommunitySorter;
import com.flf.entity.HajSortingOrder;
import com.flf.entity.Page;
import com.flf.entity.User;
import com.flf.service.HajCommodityTypeService;
import com.flf.service.HajCommunitySorterService;
import com.flf.service.HajSortingOrderService;
import com.flf.util.Const;
import com.flf.util.ExcelUtil;
import com.flf.util.Tools;

/**
 * <br>
 * <b>功能：</b>HajSortingOrderAppController<br>
 * <br>
 */
@Controller
@RequestMapping(value = "/sortingOrder")
public class HajSortingOrderAppController {

	@Autowired(required = false)
	private HajSortingOrderService hajSortingOrderService;

	@Autowired
	private HajCommodityTypeService commodityTypeService;

	@Autowired
	private HajCommunitySorterService communitySorterService;

	/**
	 * 后台查询分拣订单列表
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/getAll")
	public String list(HttpSession session, HajSortingOrder sorting, Model model) {
		try {
			User user = (User) session.getAttribute(Const.SESSION_USER);
			if (null != user) {
				sorting.setAreaCode(user.getAreaCode());
			}

			List<Map<String, Object>> sortingList = hajSortingOrderService.listPageOrder(sorting);
			model.addAttribute("sortingList", sortingList);
			model.addAttribute("sorting", sorting);

			Map<String, Object> totalPurchase = hajSortingOrderService.queryTotalSorting(sorting);
			model.addAttribute("totalPurchase", totalPurchase);

			String typeId = sorting.getCommodityAttr();
			if (Tools.notEmpty(typeId) && !"0".equals(typeId)) {
				List<HajCommodityType> typeList = commodityTypeService.getBigTypeByattr(typeId);
				model.addAttribute("parentTypeList", typeList);
				if (sorting.getbTypeName() != "") {
					Integer parentId = Integer.parseInt(sorting.getbTypeName());
					typeList = commodityTypeService.getTypeByParentId(parentId);
				}

				model.addAttribute("subTypeList", typeList);
			}

			/* 分拣小组列表数据 */
			HajCommunitySorter hajCommunitySorter = new HajCommunitySorter();
			Page page = new Page();
			page.setShowCount(100);
			hajCommunitySorter.setPage(page);
			if (null != user) {
				hajCommunitySorter.setAreaCode(user.getAreaCode());
			}
			List<HajCommunitySorter> sorterList = communitySorterService.listPage(hajCommunitySorter);
			model.addAttribute("sorterList", sorterList);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "sorting_orderList";
	}

	/**
	 * 后台查询实时分拣订单列表
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/getTodaySorting")
	public String getTodaySorting(HttpSession session, HajSortingOrder sorting, Model model) {
		try {
			User user = (User) session.getAttribute(Const.SESSION_USER);
			if (null != user) {
				sorting.setAreaCode(user.getAreaCode());
			}
			List<Map<String, Object>> sortingList = hajSortingOrderService.listPageTodaySorting(sorting);
			model.addAttribute("sortingList", sortingList);
			model.addAttribute("sorting", sorting);

			Map<String, Object> totalPurchase = hajSortingOrderService.queryTotalTodaySorting(sorting);
			model.addAttribute("totalPurchase", totalPurchase);

			String typeId = sorting.getCommodityAttr();
			if (Tools.notEmpty(typeId) && !"0".equals(typeId)) {
				List<HajCommodityType> typeList = commodityTypeService.getBigTypeByattr(typeId);
				model.addAttribute("parentTypeList", typeList);
				if (sorting.getbTypeName() != "") {
					Integer parentId = Integer.parseInt(sorting.getbTypeName());
					typeList = commodityTypeService.getTypeByParentId(parentId);
				}

				model.addAttribute("subTypeList", typeList);
			}

			/* 分拣小组列表数据 */
			HajCommunitySorter hajCommunitySorter = new HajCommunitySorter();
			Page page = new Page();
			page.setShowCount(100);
			hajCommunitySorter.setPage(page);
			if (null != user) {
				hajCommunitySorter.setAreaCode(user.getAreaCode());
			}
			List<HajCommunitySorter> sorterList = communitySorterService.listPage(hajCommunitySorter);
			model.addAttribute("sorterList", sorterList);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "today_sorting_orderList";
	}

	/**
	 * 导出采购信息到excel
	 *
	 * @return
	 */
	@RequestMapping(value = "/excel")
	public void export2Excel(HttpSession session, HajSortingOrder purchase, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			User user = (User) session.getAttribute(Const.SESSION_USER);
			if (null != user) {
				purchase.setAreaCode(user.getAreaCode());
			}
			XSSFWorkbook wb = hajSortingOrderService.exportHajSortingOrder(purchase);
			String filename = HttpUtil.encodeFilename(request, "商品分拣单");
			ExcelUtil.output(response, filename, wb);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 合并导出采购信息到excel
	 *
	 * @return
	 */
	@RequestMapping(value = "/exportSortOrder")
	public void exportSortOrder(HttpSession session, HajSortingOrder purchase, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			User user = (User) session.getAttribute(Const.SESSION_USER);
			if (null != user) {
				purchase.setAreaCode(user.getAreaCode());
			}

			XSSFWorkbook wb = hajSortingOrderService.exportSortOrder(purchase);
			String filename = HttpUtil.encodeFilename(request, "分拣单合并");
			ExcelUtil.output(response, filename, wb);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/exportTodaySortOrder")
	public void exportTodaySortOrder(HttpSession session, HajSortingOrder purchase, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			User user = (User) session.getAttribute(Const.SESSION_USER);
			if (null != user) {
				purchase.setAreaCode(user.getAreaCode());
			}
			XSSFWorkbook wb = hajSortingOrderService.exportTodaySortOrder(purchase);
			String filename = HttpUtil.encodeFilename(request, "实时分拣单");
			ExcelUtil.output(response, filename, wb);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
