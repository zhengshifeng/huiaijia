package com.flf.controller.xcx;

import com.flf.entity.HajCommunityUnit;
import com.flf.entity.HajFrontUser;
import com.flf.entity.HajFrontUserUpdateLog;
import com.flf.service.HajCommunityUnitService;
import com.flf.service.HajFrontUserService;
import com.flf.service.HajFrontUserUpdateLogService;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/10/20
 */
@Controller
@RequestMapping(value = "/xcx/frontUser")
public class HajFrontUserController {

	private final static Logger log = Logger.getLogger(HajFrontUserController.class);

	@Autowired(required = false)
	private HajCommunityUnitService communityUnitService;

	@Autowired(required = false)
	private HajFrontUserUpdateLogService hajFrontUserUpdateLogService;

	@Autowired(required = false)
	private HajFrontUserService service;

	@RequestMapping(value = "/list")
	public void list(@RequestParam String sign, @RequestParam Integer communityId, HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<>();
		JsonConfig jsonConfig = new JsonConfig();
		try {
			if (MD5Tools.checkSign(sign)) {
				if (communityId != null) {
					// 根据小区ID获取小区单元地址
					List<HajCommunityUnit> unitList = communityUnitService.getUnitList(communityId);
					jsonMap.put("error", "0");
					jsonMap.put("msg", "成功");
					jsonMap.put("unitList", unitList);
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
				JSONUtils.printObject(jsonMap, response, jsonConfig);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@RequestMapping(value = "/updateShippingAddress")
	public void updateShippingAddress(@RequestParam String sign, HajFrontUser userManager,
									  HttpServletResponse response) {
		Map<String, String> jsonMap = new HashMap<>();
		try {
			if (MD5Tools.checkSign(sign)) {
				HajFrontUser queryById = service.queryById(userManager.getId());
				if (queryById != null) {
					if (userManager.getCommunityUnitId() != null) {
						boolean updateFlag = false;

						//如果社区单元id不为null时
						HajCommunityUnit unit = communityUnitService.queryById(userManager.getCommunityUnitId());
						String tempAddress = "";
						if (unit != null) {
							tempAddress = unit.getUnit() + userManager.getFloor() + "层"
									+ userManager.getHouseNumber();
							if (!tempAddress.equals(queryById.getAddress())) {
								userManager.setAddress(tempAddress);
								userManager.setResidential(queryById.getResidential());
								userManager.setAreaCode(queryById.getAreaCode());
								// 地址惟一性判断
								int checkAddressUniqueness = service.checkAddressUniqueness(userManager);
								updateFlag = checkAddressUniqueness < 1;
							}
						}
						if (updateFlag) {
							//要保存的用户的配送信息
							HajFrontUser user = new HajFrontUser();
							user.setId(userManager.getId());
							user.setCommunityUnitId(userManager.getCommunityUnitId());
							user.setFloor(userManager.getFloor());
							user.setHouseNumber(userManager.getHouseNumber());
							user.setReceiver(userManager.getReceiver());
							user.setAddress(tempAddress);
							user.setBuildingId(unit.getBuildingId());

							//更新用户配送信息
							service.updateBySelective(user);

							HajFrontUserUpdateLog updateLog;
							updateLog = new HajFrontUserUpdateLog();
							updateLog.setOperator("用户本人");
							updateLog.setFrontUserId(user.getId());
							updateLog.setCreateTime(new Date());
							updateLog.setRecord("用户把地址更改为:" + user.getAddress());
							hajFrontUserUpdateLogService.add(updateLog);

							jsonMap.put("error", "0");
							jsonMap.put("msg", "保存成功！");
						} else {
							jsonMap.put("error", "3");
							jsonMap.put("msg", "地址已被注册");
						}
					}
				}else{
					jsonMap.put("error", "2");
					jsonMap.put("msg", "签名异常");
				}
			}
		} catch (Exception e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
			e.printStackTrace();
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

