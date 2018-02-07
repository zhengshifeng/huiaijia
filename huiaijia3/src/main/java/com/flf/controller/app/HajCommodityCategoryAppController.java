package com.flf.controller.app;

import com.flf.service.HajCommodityCategoryService;
import com.flf.util.JSONUtils;
import com.flf.util.MD5Tools;
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
 * 
 * <br>
 * <b>功能：</b>HajCommodityCategoryAppController<br>
 * <br>
 */ 
@Controller
@RequestMapping(value = "/hajCommodityCategory/app/")
public class HajCommodityCategoryAppController {
	
	private final static Logger log = Logger.getLogger(HajCommodityCategoryAppController.class);
	
	@Autowired(required = false)
	private HajCommodityCategoryService service;

	@RequestMapping(value = "/list")
	public void list(HttpServletResponse response,
					 @RequestParam String sign) {
		Map<String, Object> jsonMap = new HashMap<>();
		jsonMap.put("error", "0");
		jsonMap.put("msg", "成功");
		List<HashMap<String, Object>> list = new ArrayList<>();
		try {
			if (MD5Tools.checkSign(sign)) {
				// 签名验证通过，则继续其他逻辑
				// ...
			} else {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "签名异常");
			}
		} catch (Exception e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
			log.error(e.getMessage(), e);
		} finally {
			try {
				jsonMap.put("list", list);
				JSONUtils.printObject(jsonMap, response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
