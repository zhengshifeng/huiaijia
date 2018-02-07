package com.flf.controller;

import com.flf.entity.Menu;
import com.flf.service.MenuService;
import com.flf.util.JSONUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Controller
@RequestMapping(value = "/menu")
public class MenuController {

	@Autowired
	private MenuService menuService;

	/**
	 * 显示菜单列表
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping
	public String list(Model model) {
		List<Menu> menuList = menuService.listAllMenu();
		model.addAttribute("menuList", menuList);
		return "menus";
	}

	/**
	 * 请求新增菜单页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add")
	public String toAdd(Model model) {
		List<Menu> menuList = menuService.listAllParentMenu();
		model.addAttribute("menuList", menuList);
		return "menus_info";
	}

	/**
	 * 请求编辑菜单页面
	 * 
	 * @param menuId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/edit")
	public String toEdit(@RequestParam Integer menuId, Model model) {
		Menu menu = menuService.getMenuById(menuId);
		model.addAttribute("menu", menu);
		if (menu.getParentId() != null && menu.getParentId().intValue() > 0) {
			List<Menu> menuList = menuService.listAllParentMenu();
			model.addAttribute("menuList", menuList);
		}
		return "menus_info";
	}

	/**
	 * 保存菜单信息
	 * 
	 * @param menu
	 * @return
	 */
	@RequestMapping(value = "/save")
	public void save(Menu menu, PrintWriter out) {
		menuService.saveMenu(menu);
		out.print("success");
		out.close();
	}

	/**
	 * 获取当前菜单的所有子菜单
	 * 
	 * @param menuId
	 * @param response
	 */
	@RequestMapping(value = "/sub")
	public void getSub(@RequestParam Integer menuId, HttpServletResponse response) {
		List<Menu> subMenu = menuService.listSubMenuByParentId(menuId);
		try {
			JSONUtils.printObject(subMenu, response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除菜单
	 * 
	 * @param menuId
	 * @param out
	 */
	@RequestMapping(value = "/del")
	public void delete(@RequestParam Integer menuId, PrintWriter out) {
		menuService.deleteMenuById(menuId);
		out.write("success");
		out.flush();
		out.close();
	}
}
