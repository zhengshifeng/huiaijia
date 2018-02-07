package com.flf.controller.app;

import com.flf.entity.HajCategory;
import com.flf.service.HajCategoryService;
import com.flf.service.RedisSpringProxy;
import com.flf.util.JSONUtils;
import com.flf.util.MD5Tools;
import com.flf.util.Tools;
import net.sf.json.JsonConfig;
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
 * <br>
 * <b>功能：</b>HajCategoryAppController<br>
 * <br>
 */
@Controller
@RequestMapping(value = "/category/app")
public class HajCategoryAppController {

	private final static Logger log = Logger.getLogger(HajCategoryAppController.class);

	@Autowired(required = false)
	private HajCategoryService service;

	@Autowired(required = false)
	private RedisSpringProxy redisSpringProxy;

	@RequestMapping(value = "/list")
	public void list(@RequestParam String sign, @RequestParam String areaCode, Integer communityId,
					 HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<>();
		JsonConfig jsonConfig = new JsonConfig();
		try {
			if (MD5Tools.checkSign(sign)) {
				List<HajCategory> categoryList;
				String redisKey = "categoryList_" + areaCode + "_" + communityId;
				areaCode = Tools.getAreaCode(areaCode);
				if (null != redisSpringProxy.read(redisKey)) {
					log.info("商品类目列表(redis)");
					categoryList = (List<HajCategory>) redisSpringProxy.read(redisKey);
				} else {
					log.info("商品类目列表(db)");
					categoryList = service.list4App(areaCode, communityId);

					redisSpringProxy.save(redisKey, categoryList);
				}
				jsonMap.put("error", "0");
				jsonMap.put("msg", "成功");
				jsonMap.put("categoryList", categoryList);

				// 过滤掉无用的属性
				jsonConfig.setIgnoreDefaultExcludes(false);
				jsonConfig.setExcludes(new String[]{"areaCode", "sort", "page"});
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
				JSONUtils.printObject(jsonMap, response, jsonConfig);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
