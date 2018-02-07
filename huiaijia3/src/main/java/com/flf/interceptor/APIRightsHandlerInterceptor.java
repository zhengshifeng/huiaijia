package com.flf.interceptor;

import com.base.util.HttpUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

@Service
public class APIRightsHandlerInterceptor extends HandlerInterceptorAdapter {

	private final static Logger log = Logger.getLogger(APIRightsHandlerInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String path = request.getServletPath();
		Enumeration enu = request.getParameterNames();
		StringBuilder params = new StringBuilder();

		// 请求来源信息
		params.append("from: ");
		params.append(HttpUtil.getIp(request));
		params.append(" - ");
		params.append(request.getHeader("user-agent"));
		params.append("\n");

		// 请求的路径及参数
		params.append(path);
		params.append("?");
		String paraName;
		while (enu.hasMoreElements()) {
			paraName = (String) enu.nextElement();
			params.append(paraName).append("=").append(request.getParameter(paraName)).append("&");
		}
		log.info(params.substring(0, params.length() - 1));
		return true;
	}

}
