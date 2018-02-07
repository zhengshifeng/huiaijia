package com.flf.controller;

import com.flf.entity.Menu;
import com.flf.entity.Role;
import com.flf.service.MenuService;
import com.flf.service.RoleService;
import com.flf.util.JSONUtils;
import com.flf.util.RightsHelper;
import com.flf.util.Tools;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/role")
public class RoleController {

	@Autowired
	private RoleService roleService;
	@Autowired
	private MenuService menuService;

	/**
	 * 显示角色列表
	 */
	@RequestMapping
	public String list(Map<String, Object> map) {
		List<Role> roleList = roleService.listAllRoles();
		map.put("roleList", roleList);
		return "roles";
	}


	/**
	 * 删除角色
	 */
	@RequestMapping(value = "/del")
	public void delete(@RequestParam Integer roleId, HttpServletResponse httpServletResponse) {
		roleService.deleteRoleById(roleId);
		try {
			JSONUtils.printStr("1", httpServletResponse);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 保存角色信息
	 */
	private boolean saveRole(Role role) {
		boolean flag;
		if (role.getRoleId() != null && role.getRoleId() > 0) {
			flag = roleService.updateRoleBaseInfo(role);
		} else {
			flag = roleService.insertRole(role);
		}

		return flag;
	}

	/**
	 * 请求角色授权页面
	 */
	@RequestMapping(value = "/auth")
	public String auth(@RequestParam int roleId, Model model) {
		List<Menu> menuList;
		menuList = new ArrayList<>();

		List<Menu> allMenu = menuService.listAllMenu();
		Role role = roleService.getRoleById(roleId);
		String roleRights = (role == null) ? null : role.getRights();
		for (Menu menu : allMenu) {
			menu.setIcon(null);
			menu.setHasMenu(RightsHelper.testRights(roleRights, menu.getMenuId()));
			menuList.add(menu);

			List<Menu> subMenuList = menu.getSubMenu();
			menu.setParentFlag(subMenuList.size() > 0);
			for (Menu sub : subMenuList) {
				sub.setIcon(null);
				sub.setHasMenu(RightsHelper.testRights(roleRights, sub.getMenuId()));
				sub.setSubMenu(null);
				menuList.add(sub);
			}
			menu.setSubMenu(null);
		}
		JSONArray arr = JSONArray.fromObject(menuList);
		String json = arr.toString();
		json = json.replaceAll("menuId", "id")
				.replaceAll("menuName", "name")
				.replaceAll("hasMenu", "checked")
				.replaceAll("parentFlag", "isParent")
				.replace("parentId","pId");
		model.addAttribute("zTreeNodes", json);
		model.addAttribute("roleId", roleId);
		model.addAttribute("role", role);
		return "authorization";
	}

	/**
	 * 保存角色权限
	 */
	@RequestMapping(value = "/auth/save")
	public void saveAuth(@RequestParam Integer roleId, @RequestParam String roleName,
			@RequestParam String menuIds,
			HttpServletResponse httpServletResponse) {
		try {
			Role role = new Role();
			if (roleId != null) {
				role = roleService.getRoleById(roleId);
			}
			role.setRoleName(roleName);
			saveRole(role);

			role = roleService.getRoleById(role.getRoleId());
			if (Tools.isNotEmpty(menuIds)) {
				BigInteger rights = RightsHelper.sumRights(Tools.str2StrArray(menuIds));
				role.setRights(rights.toString());
			} else {
				role.setRights("");
			}

			roleService.updateRoleRights(role);
			JSONUtils.printStr("1", httpServletResponse);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				JSONUtils.printStr("0", httpServletResponse);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

	}
}
