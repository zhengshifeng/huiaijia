package com.flf.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flf.service.MenuService;
import com.flf.service.RoleService;
import com.flf.service.UserService;

/**
 * @author Administrator 获取Spring容器中的service bean
 */
@Service
public class ServiceHelper {

	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private MenuService menuService;

	public static Object getService(String serviceName) {
		// WebApplicationContextUtils.
		return Const.WEB_APP_CONTEXT.getBean(serviceName);
	}

	public UserService getUserService() {
		// return (UserService) getService("userService");
		return userService;
	}

	public RoleService getRoleService() {
		// return (RoleService) getService("roleService");
		return roleService;
	}

	public MenuService getMenuService() {
		return menuService;
	}
}
