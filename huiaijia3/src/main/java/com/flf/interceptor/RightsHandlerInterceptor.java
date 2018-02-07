package com.flf.interceptor;

import com.base.util.HttpUtil;
import com.flf.entity.Menu;
import com.flf.entity.User;
import com.flf.util.Const;
import com.flf.util.RightsHelper;
import com.flf.util.ServiceHelper;
import com.flf.util.Tools;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class RightsHandlerInterceptor extends HandlerInterceptorAdapter {

	private final static Logger log = Logger.getLogger(RightsHandlerInterceptor.class);

	@Autowired
	private ServiceHelper serviceHelper;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String path = request.getServletPath();
		if (path.matches(Const.NO_INTERCEPTOR_PATH))
			return true;
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		String ip = HttpUtil.getIp(request);

		log.info(ip + "【" + user.getUsername() + "】: " + path);

		Integer menuId = null;
		List<Menu> subList = serviceHelper.getMenuService().listAllSubMenu();
		for (Menu m : subList) {
			String menuUrl = m.getMenuUrl();
			if (Tools.notEmpty(menuUrl)) {
				if (path.contains(menuUrl)) {
					menuId = m.getMenuId();
					break;
				} else {
					String[] arr = menuUrl.split("\\.");
					String regex = "";
					if (arr.length == 2) {
						regex = "/?" + arr[0] + "(/.*)?." + arr[1];

					} else {
						regex = "/?" + menuUrl + "(/.*)?.html";
					}
					if (path.matches(regex)) {
						menuId = m.getMenuId();
						break;
					}
				}
			}
		}
		if (menuId != null) {
			String userRights = (String) session.getAttribute(Const.SESSION_USER_RIGHTS);
			String roleRights = (String) session.getAttribute(Const.SESSION_ROLE_RIGHTS);
			if (RightsHelper.testRights(userRights, menuId) || RightsHelper.testRights(roleRights, menuId)) {
				return true;
			} else {
				System.out.println("该用户没有权限访问此地址！");
				ModelAndView mv = new ModelAndView();
				mv.setViewName("no_rights");
				throw new ModelAndViewDefiningException(mv);
			}
		}
		return super.preHandle(request, response, handler);
	}

}
