package com.flf.interceptor;

import com.flf.entity.User;
import com.flf.util.Const;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Service
public class LoginHandlerInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String path = request.getServletPath();
		if (path.matches(Const.NO_INTERCEPTOR_PATH)) {
			return true;
		} else {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute(Const.SESSION_USER);
			if (user != null) {
				return true;
			} else {
				response.sendRedirect(request.getContextPath() + "/login.html");
				return false;
			}
		}
	}

}
