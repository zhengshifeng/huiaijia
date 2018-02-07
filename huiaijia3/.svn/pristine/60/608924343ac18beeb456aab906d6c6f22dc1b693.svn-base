package com.flf.util.geetest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * 使用Get的方式返回challenge和capthca_id,此方式以实现前后端完全分离的开发模式
 */
public class StartCaptchaServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request,
						 HttpServletResponse response) throws ServletException, IOException {

		GeetestLib gtSdk = new GeetestLib(GeetestConfig.getGeetest_id(), GeetestConfig.getGeetest_key(),
				GeetestConfig.isnewfailback());

		String resStr = "{}";

		//自定义userid
		String userid = "huiaj.chengshe.3.0";

		//进行验证预处理
		int gtServerStatus = gtSdk.preProcess(userid);

		//将服务器状态设置到session中
		request.getSession().setAttribute(gtSdk.gtServerStatusSessionKey, gtServerStatus);
		//将userid设置到session中
		request.getSession().setAttribute("userid", userid);

		// 将validated设置到session中，用作后台进行登录时判断是否通过验证
		request.getSession().setAttribute(GeetestConfig.GEETEST_VALIDATED, false);

		resStr = gtSdk.getResponseStr();

		PrintWriter out = response.getWriter();
		out.println(resStr);

	}
}