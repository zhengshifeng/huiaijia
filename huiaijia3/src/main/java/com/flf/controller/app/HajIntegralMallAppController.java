package com.flf.controller.app;

import com.flf.entity.HajFrontUser;
import com.flf.service.*;
import com.flf.util.JSONUtils;
import com.flf.util.MD5Tools;
import net.sf.json.JsonConfig;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <br>
 * <b>功能：</b>HajIntegralMallAppController<br>
 * <br>
 */
@Controller
@RequestMapping(value = "/integralMall/app/")
public class HajIntegralMallAppController {

	private final static Logger log = Logger.getLogger(HajIntegralMallAppController.class);

	@Autowired(required = false)
	private HajIntegralMallService service;

	/**
	 * 积分商城列表
	 */
	@RequestMapping(value = "/list")
	public void list(HttpServletResponse response,
					 @RequestParam String sign,
					 @RequestParam String areaCode,
					 @RequestParam Integer userId) {
		Map<String, Object> jsonMap = new HashMap<>();
		jsonMap.put("error", "0");
		jsonMap.put("msg", "成功");
		List<HashMap<String, Object>> list = new ArrayList<>();
		try {
			if (MD5Tools.checkSign(sign)) {
				HajFrontUser user = new HajFrontUser();
				user.setAreaCode(areaCode);
				user.setId(userId);
				list = service.list4app(user);
			} else {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "签名异常");
			}
		} catch (Exception e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
			log.info(e.getMessage(), e);
		} finally {
			try {
				JsonConfig jsonConfig = new JsonConfig(); // 建立配置文件
				jsonConfig.setIgnoreDefaultExcludes(false); // 设置默认忽略
				jsonConfig.setExcludes(new String[]{"yourExchanged", "totalOfExchanged", "commodityType",
						"total", "exchangeLimit", "limitCount"});

				jsonMap.put("list", list);
				JSONUtils.printObject(jsonMap, response, jsonConfig);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@RequestMapping(value = "/exchange")
	public void exchange(HttpServletResponse response,
						 @RequestParam String sign,
						 @RequestParam Integer id,
						 @RequestParam Integer userId) {
		Map<String, Object> jsonMap = new HashMap<>();
		try {
			if (MD5Tools.checkSign(sign)) {
				service.saveIntegralExchange(jsonMap, id, userId);
			} else {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "签名异常");
			}
		} catch (Exception e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
			log.info(e.getMessage(), e);
		} finally {
			try {
				JSONUtils.printObject(jsonMap, response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
