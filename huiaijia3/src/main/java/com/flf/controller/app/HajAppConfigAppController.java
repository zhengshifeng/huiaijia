package com.flf.controller.app;

import com.flf.entity.HajSpecialTopic;
import com.flf.service.HajAppConfigService;
import com.flf.service.HajSpecialTopicService;
import com.flf.service.RedisSpringProxy;
import com.flf.util.JSONUtils;
import com.flf.util.MD5Tools;
import com.flf.util.Tools;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * <br>
 * <b>功能：</b>HajAppConfigAppController<br>
 * <br>
 */ 
@Controller
@RequestMapping(value = "/appConfig")
public class HajAppConfigAppController {
	
	private final static Logger log = Logger.getLogger(HajAppConfigAppController.class);
	
	@Autowired(required = false)
	private HajAppConfigService service;

	@Autowired(required = false)
	private HajSpecialTopicService specialTopicService;

	@Autowired(required = false)
	private RedisSpringProxy redisSpringProxy;

	@RequestMapping(value = "/index")
	public void getCommodityMustBuy(HttpServletResponse response,
									@RequestParam String sign, @RequestParam String areaCode) {
		Map<String, Object> jsonMap = new HashMap<>();
		jsonMap.put("error", "0");
		jsonMap.put("msg", "成功");
		Map<String, Object> appConfig = null;

		try {
			if (MD5Tools.checkSign(sign)) {
				areaCode = Tools.getAreaCode(areaCode);
				String redisKey = "appConfig_index_" + areaCode;
				if (redisSpringProxy.read(redisKey) != null) {
					log.info("获取app首页配置信息(redis)");
					appConfig = (HashMap<String, Object>) redisSpringProxy.read(redisKey);
				} else {
					log.info("获取app首页配置信息(db)");
					appConfig = service.getAll();

					HajSpecialTopic specialTopic = new HajSpecialTopic();
					specialTopic.setInvalid(0);
					specialTopic.setAreaCode(areaCode);

					// 获取所有启用的专题
					List<HajSpecialTopic> listPage = specialTopicService.listPage(specialTopic);
					Map<String, Object> specialTopicMap = new HashMap<>();
					if (listPage.size() > 0) {
						specialTopicMap.put("banner", listPage.get(0).getBanner());
						specialTopicMap.put("url", listPage.get(0).getUrl());
					}
					appConfig.put("specialTopic", specialTopicMap);

					redisSpringProxy.save(redisKey, appConfig);
				}
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
				jsonMap.put("appConfig", appConfig);
				JSONUtils.printObject(jsonMap, response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
