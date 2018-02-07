package com.flf.controller.app;

import com.flf.entity.HajCommodityGroupBuying;
import com.flf.service.HajCommodityGroupBuyingService;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * <br>
 * <b>功能：</b>HajCommodityGroupBuyingAppController<br>
 * <br>
 */ 
@Controller
@RequestMapping(value = "/commodityGroupBuying/app/")
public class HajCommodityGroupBuyingAppController {
	
	private final static Logger log = Logger.getLogger(HajCommodityGroupBuyingAppController.class);
	
	@Autowired(required = false)
	private HajCommodityGroupBuyingService service;

	@Autowired(required = false)
	private RedisSpringProxy redisSpringProxy;

	@RequestMapping(value = "/list")
	public void list(HttpServletResponse response,
					 @RequestParam String sign,
					 @RequestParam String areaCode) {
		Map<String, Object> jsonMap = new HashMap<>();
		jsonMap.put("error", "0");
		jsonMap.put("msg", "成功");
		List<HashMap<String, Object>> list = new ArrayList<>();
		try {
			if (MD5Tools.checkSign(sign)) {
				String redisKey = "commodityGroupBuyingList_" + areaCode;

				// if (redisSpringProxy.read(redisKey) != null) {
				// 	log.info("团购商品列表(redis):" + redisKey);
				// 	list = (List<HashMap<String, Object>>) redisSpringProxy.read(redisKey);
				// } else {
					log.info("团购商品列表(db):" + redisKey);
					HajCommodityGroupBuying commodityGroupBuying = new HajCommodityGroupBuying();

					areaCode = Tools.getAreaCode(areaCode);
					commodityGroupBuying.setAreaCode(areaCode);
					list = service.list4app(commodityGroupBuying);
				// }
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

	@RequestMapping(value = "/detail")
	public void detail(HttpServletResponse response,
					 @RequestParam String sign,
					 @RequestParam Integer commodityId) {
		Map<String, Object> jsonMap = new HashMap<>();
		jsonMap.put("error", "0");
		jsonMap.put("msg", "成功");
		HashMap<String, Object> detail = new HashMap<>();
		try {
			if (MD5Tools.checkSign(sign)) {
				String redisKey = "commodityGroupBuyingDetail_" + commodityId;
				HajCommodityGroupBuying commodityGroupBuying = new HajCommodityGroupBuying();
				commodityGroupBuying.setCommodityId(commodityId);
				detail = service.detail4app(commodityGroupBuying);
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
				jsonMap.put("detail", detail);
				JSONUtils.printObject(jsonMap, response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
