package com.flf.controller.app;

import com.flf.entity.HajCommunityBuilding;
import com.flf.entity.HajCommunityPersion;
import com.flf.service.HajCommunityBuildingService;
import com.flf.service.HajCommunityPersionService;
import com.flf.util.JSONUtils;
import com.flf.util.MD5Tools;
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
 * <b>功能：</b>HajCommunityBuildingAppController<br>
 * <br>
 */
@Controller
@RequestMapping(value = "/hajCommunityBuilding")
public class HajCommunityBuildingAppController {

	private final static Logger log = Logger.getLogger(HajCommunityBuildingAppController.class);

	@Autowired(required = false)
	private HajCommunityBuildingService service;

	@Autowired(required = false)
	private HajCommunityPersionService communityService;

	@RequestMapping(value = "/list")
	public void getBuildingList(@RequestParam String sign,
								@RequestParam Integer communityId,
								HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<>();
		try {
			if (MD5Tools.checkSign(sign)) {
				if (communityId > 0) {
					HajCommunityPersion community = communityService.queryById(communityId);
					List<HajCommunityBuilding> buildingList = service.getList4api(communityId);
					jsonMap.put("error", "0");
					jsonMap.put("msg", "成功");
					jsonMap.put("buildingList", buildingList);
					jsonMap.put("floor", community.getFloor());
				} else {
					jsonMap.put("error", "3");
					jsonMap.put("msg", "请先选择小区");
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
				JSONUtils.printObject(jsonMap, response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
