package com.flf.controller.app;

import com.flf.util.JSONUtils;
import com.flf.util.gexin.AppPush;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 推送消息至APP
 * 
 * @author SevenWong<br>
 * @date 2016年6月20日下午3:59:49
 */
@Controller
@RequestMapping(value="/hajPush")
public class HajPushController {
	
	/**
	 * 根据用户ID发送推送至APP
	 */
	@RequestMapping(value="/sendByAlias")
	public void sendByAlias(HttpServletResponse response, String userId) {
		Map<String, Object> jsonMap = new HashMap<>();
		try {
			String title = "震惊！";
			String body = "震惊！汇爱家产品经理在公共场合竟然...";
			AppPush.sendByAlias(userId, title, body, "refund");
		} catch (Exception e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
			jsonMap.put("result", false);
			e.printStackTrace();
		} finally {
			try {
				JSONUtils.printObject(jsonMap, response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
