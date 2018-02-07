package com.flf.controller.erp;

import com.flf.entity.HajCommodity;
import com.flf.service.HajCommodityService;
import com.flf.util.JSONUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 同步erp库存
 * Created by Administrator on 2017/11/23.
 */
@Controller
@RequestMapping(value = "/erp/inventory")
public class HajInventoryErpController {

	private final static Logger log = Logger.getLogger(HajInventoryErpController.class);

	@Autowired(required = false)
	private HajCommodityService service;

	@RequestMapping("/syncInventory")
	public void syncInventory(Integer id, HttpServletResponse response) throws Exception {
		Map<String, Object> jsonMap = new HashMap<>();
		if (id != null) {
			//如果id不为null，获取当前商品，同步erp库存
			HajCommodity commodity = service.queryById(id);
			//同步erp
			log.info("id为" + commodity.getId() + "的商品开始同步erp库存");
			service.updateSyncInventoryOne(commodity);
			log.info("id为" + commodity.getId() + "的商品同步erp库存成功");
			//如果id不为null，获取当前商品，同步erp库存
			HajCommodity commodity1 = service.queryById(id);
			jsonMap.put("inventory",commodity1.getInventory());
			jsonMap.put("error", "0");
			jsonMap.put("msg", "成功");
		} else {
			//如果id为null，同步所有商品erp库存
			service.updateSyncInventory();
			log.info("同步所有商品库存");
			jsonMap.put("error", "0");
			jsonMap.put("msg", "成功");
		}
		JSONUtils.printObject(jsonMap, response);

	}


}
