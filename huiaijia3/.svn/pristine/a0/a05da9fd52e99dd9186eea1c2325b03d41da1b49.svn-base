package com.flf.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.flf.entity.HajWarehouse;
import com.flf.entity.User;
import com.flf.service.HajWarehouseService;
import com.flf.util.Const;

/**
 * 
 * <br>
 * <b>功能：</b>HajWarehouseAppController<br>
 * <br>
 */
@Controller
@RequestMapping(value = "/warehouse")
public class HajWarehouseController {

	private final static Logger log = Logger.getLogger(HajWarehouseController.class);
	@Autowired(required = false)
	private HajWarehouseService hajWarehouseService;

	/**
	 * 请求新增菜单页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/addWarehouse")
	public String toAdd(Model model) {
		return "warehouse_add";
	}

	/**
	 * 添加活动
	 */
	@RequestMapping(value = "/saveWarehouse")
	public void saveWarehouse(HajWarehouse warehouse, HttpSession session, HttpServletResponse response) {
		String result = "";
		try {
			User user = (User) session.getAttribute(Const.SESSION_USER);
			if (null != user) {
				warehouse.setRegion(user.getAreaCode());
			}
			List<Map<String, Object>> wh = hajWarehouseService.getWareHouseByName(warehouse);
			if (null != wh && wh.size() > 0) {
				log.info("存在重复的仓库名称");
			} else {
				hajWarehouseService.add(warehouse);
				result = "success";
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				response.getWriter().print(result);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 查询售后退款单
	 * 
	 */
	@RequestMapping(value = "/getAll")
	public String list(HajWarehouse warehouse, HttpSession session, Model model) {
		User user = (User) session.getAttribute(Const.SESSION_USER);
		if (null != user) {
			warehouse.setAreaCode(user.getAreaCode());
		}
		List<Map<String, Object>> warehouseList = hajWarehouseService.listPageHajWarehouse(warehouse);
		model.addAttribute("warehouseList", warehouseList);
		model.addAttribute("warehouse", warehouse);
		return "warehouselist";
	}

}
