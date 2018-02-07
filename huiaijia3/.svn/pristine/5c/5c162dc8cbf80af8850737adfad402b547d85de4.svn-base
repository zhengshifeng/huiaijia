package com.flf.util;

import org.springframework.context.ApplicationContext;

public class Const {
	public static final String SESSION_SECURITY_CODE = "sessionSecCode";
	public static final String SESSION_USER = "sessionUser";
	public static final String SESSION_USER_RIGHTS = "sessionUserRights";
	public static final String SESSION_ROLE_RIGHTS = "sessionRoleRights";
	public static final String NO_INTERCEPTOR_PATH = ".*/((login)|(logout)|(code)).*"; // 不对匹配该值的访问路径拦截（正则）
	public static ApplicationContext WEB_APP_CONTEXT = null; // 该值会在web容器启动时由WebAppContextListener初始化

	/**
	 * 超管的角色ID
	 */
	public static final Integer ADMIN_ROLE_ID = 1;

	/**
	 * 默认城市编码（深圳）
	 */
	public static final String DEFAULT_AREA_CODE = "100002";

	/**
	 * 判断用户是否有权限切换后台管理的城市
	 */
	public static final String I_CAN_CHANGE_MANAGE_CITY = "iCanChangeManageCity";

}
