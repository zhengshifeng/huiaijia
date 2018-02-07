package com.flf.controller.app;

import com.base.criteria.Criteria;
import com.flf.entity.HajCommunityPersion;
import com.flf.entity.HajFrontUser;
import com.flf.service.HajCommunityPersionService;
import com.flf.service.HajFrontUserService;
import com.flf.util.JSONUtils;
import com.flf.util.MD5Tools;
import com.flf.util.Tools;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
 * <b>功能：</b>HajCommunityPersionAppController<br>
 * <br>
 */
@Controller
@RequestMapping(value = "hajCommunityPersion")
public class HajCommunityPersionAppController {

	private final static Logger log = Logger.getLogger(HajCommunityPersionAppController.class);

	@Autowired(required = false)
	private HajCommunityPersionService service;

	@Autowired(required = false)
	private HajFrontUserService frontUserService;

	/**
	 * 校验用户小区是否激活，并且返回剩余会员份数
	 */
	@RequestMapping(value = "judgeActive", method = RequestMethod.POST)
	public void judgeActive(@RequestParam String sign, @RequestParam String communityName, HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("communityName", communityName);
		Criteria criteria = new Criteria();
		criteria.setCondition(condition);
		try {
			if (MD5Tools.checkSign(sign)) {
				if (communityName != null && !"".equals(communityName)) {
					List<HajCommunityPersion> list = service.queryByList(criteria);
					if (list != null && list.size() > 0) {
						HajCommunityPersion hajCommunityPersion = list.get(0);
						int status = hajCommunityPersion.getStatus();
						int num = hajCommunityPersion.getPlanMemberNumber() - hajCommunityPersion.getMembersNumber();
						if (status == 1) {
							jsonMap.put("error", "0");
							jsonMap.put("msg", "已激活");
							jsonMap.put("flag", true);
							jsonMap.put("num", num);
						} else {
							jsonMap.put("error", "0");
							jsonMap.put("msg", "未激活");
							jsonMap.put("flag", false);
							jsonMap.put("num", num);
						}
					} else {
						jsonMap.put("error", "0");
						jsonMap.put("msg", "不存在该小区");
					}
				} else {
					jsonMap.put("error", "1");
					jsonMap.put("msg", "小区名称为空");
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
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * 根据用户ID查询小区配送负责人信息
	 */
	@RequestMapping(value = "getHajCommunityPersion")
	public void getHajCommunityPersion(@RequestParam String sign, @RequestParam String userId,
			HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			if (MD5Tools.checkSign(sign)) {
				if (userId != null && !"".equals(userId)) {
					Map<String, Object> communityPersionMap = service.queryCommunityPersionMap(userId);
					List<Map<String, Object>> userManagerById = frontUserService.getUserManagerById(userId);
					if (userManagerById != null && userManagerById.size() > 0) {
						jsonMap.put("orderNum", userManagerById.get(0).get("orderNum"));
						if (communityPersionMap != null && communityPersionMap.size() > 0) {
							jsonMap.put("error", "0");
							jsonMap.put("msg", "成功");
							jsonMap.put("hajCommunityPersion", communityPersionMap);
						} else {
							jsonMap.put("error", "1");
							jsonMap.put("msg", "该小区配送负责人信息不存在");
						}
					} else {
						jsonMap.put("error", "1");
						jsonMap.put("msg", "请先登录");
					}
				} else {
					jsonMap.put("error", "1");
					jsonMap.put("msg", "小区名称为空");
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
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * 根据小区名查询小区信息
	 */
	@RequestMapping(value = "getHajCommunityByName")
	public void getHajCommunityByName(@RequestParam String sign, @RequestParam String communityName, String areaCode,
			HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			if (MD5Tools.checkSign(sign)) {
				if (communityName != null && !"".equals(communityName)) {
					areaCode = Tools.getAreaCode(areaCode);
					HajCommunityPersion hajCommunityPersion = service.getHajCommunityByName(communityName, areaCode);
					if (hajCommunityPersion != null) {
						jsonMap.put("error", "0");
						jsonMap.put("msg", "成功");
						jsonMap.put("hajCommunityPersion", hajCommunityPersion);
					} else {
						jsonMap.put("error", "0");
						jsonMap.put("msg", "该小区信息不存在");
					}
				} else {
					jsonMap.put("error", "1");
					jsonMap.put("msg", "小区名称为空");
				}
				JSONUtils.printObject(jsonMap, response);
			} else {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "签名异常");
				JSONUtils.printObject(jsonMap, response);
			}
		} catch (Exception e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
			try {
				JSONUtils.printObject(jsonMap, response);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			log.info(e.getMessage(), e);
		}
	}

	/**
	 * 小区搜索引擎
	 */
	@RequestMapping(value = "/getCommunitySeach")
	public void getCommunitySeachApp(@RequestParam String keyword, @RequestParam String sign,
			@RequestParam Integer currentPage, String areaCode, HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		Criteria criteria = new Criteria();
		criteria.setCurrentPage(currentPage);
		try {
			if (MD5Tools.checkSign(sign)) {
				areaCode = Tools.getAreaCode(areaCode);
				List<HajCommunityPersion> HajCommunityPersion = service.getCommunitySeach(keyword, criteria, areaCode);
				if (HajCommunityPersion.size() > 0) {
					jsonMap.put("error", "0");
					jsonMap.put("msg", "成功");
					jsonMap.put("HajCommunityPersion", HajCommunityPersion);
					jsonMap.put("page", criteria);
				} else {
					jsonMap.put("error", "0");
					jsonMap.put("msg", "为空");
					jsonMap.put("HajCommunityPersion", "");
					jsonMap.put("page", criteria);
				}
			} else {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "签名异常");
				jsonMap.put("HajCommunityPersion", "");
				jsonMap.put("page", criteria);
			}
		} catch (Exception e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
			jsonMap.put("page", criteria);
			log.info(e.getMessage(), e);
		} finally {
			try {
				JSONUtils.printObject(jsonMap, response);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * 用户更换小区后，更新小区注册人数 oldcommunityName 原来小区 communityName 新小区
	 */
	@RequestMapping(value = "/HajCommunityUpdate")
	public void HajCommunityUpdate(@RequestParam String sign, String oldcommunityName, String communityName,
			HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			if (MD5Tools.checkSign(sign)) {
				if (null != communityName && !"".equals(communityName)) {
					if (communityName.equals(oldcommunityName)) {
						jsonMap.put("error", "3");
						jsonMap.put("msg", "不能预约相同小区");
					} else {
						// 新小区注册数+1
						service.updateByCommunityName(communityName);

						if (null != oldcommunityName && !"".equals(oldcommunityName)) {
							// 原小区注册数-1
							service.updateMinByCommunityName(oldcommunityName);
						}
						jsonMap.put("error", "0");
						jsonMap.put("msg", "更新小区注册人数成功");
					}
				} else {
					jsonMap.put("error", "0");
					jsonMap.put("msg", "小区为空");
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
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * 3.0新切换小区接口
	 * 
	 * @param sign
	 * @param oldCommunityId
	 * @param communityId
	 * @param response
	 */
	@RequestMapping(value = "/HajCommunityUpdate3_0")
	public void HajCommunityUpdate3(@RequestParam String sign, int userId, int oldCommunityId, int communityId,
			HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			if (MD5Tools.checkSign(sign)) {
				if (oldCommunityId > 0 && communityId > 0) {
					if (oldCommunityId == communityId) {
						jsonMap.put("error", "3");
						jsonMap.put("msg", "不能预约相同小区");
					} else {
						HajFrontUser hajFrontUser = frontUserService.queryById(userId);
						// 新小区注册数+1
						service.updateByCommunityId(communityId);

						if (oldCommunityId > 0 && communityId > 0) {
							// 原小区注册数-1
							service.updateMinByCommunityId(oldCommunityId, hajFrontUser.getIsmember());
						}
						jsonMap.put("error", "0");
						jsonMap.put("msg", "更新小区注册人数成功");
					}
				} else {
					jsonMap.put("error", "0");
					jsonMap.put("msg", "小区为空");
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
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * 返回用户信息和小区信息
	 */
	@RequestMapping(value = "/getUserInfoAndVillageInfo")
	public void getUserInfoAndVillageInfo(@RequestParam String sign, @RequestParam Integer userId,
			@RequestParam String villageName, HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<>();
		try {
			if (MD5Tools.checkSign(sign)) {
				if (userId != null && userId > 0) {
					HajFrontUser hajFrontUser = frontUserService.queryById(userId);
					jsonMap.put("hajFrontUser", hajFrontUser);

					if (hajFrontUser != null && villageName != null && !"".equals(villageName)) {
						String areaCode = hajFrontUser.getAreaCode();
						if (Tools.isNotEmpty(areaCode)) {
							Map<String, Object> community = service.exactSearchVillage(villageName, areaCode);
							jsonMap.put("community", community);
						}
					} else {
						jsonMap.put("error", "2");
						jsonMap.put("msg", "小区名称异常");
					}
				} else {
					jsonMap.put("error", "2");
					jsonMap.put("msg", "用户ID异常");
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
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * 用户预约小区
	 */
	@RequestMapping(value = "/appointmentVillage")
	@Deprecated
	public void appointmentVillage(@RequestParam String sign, @RequestParam Integer userId,
			@RequestParam Integer villageId, HttpServletResponse response) {
		log.info("用户预约小区1.0接口（已停用），即将重定向到2.0处理业务...");
		StringBuilder redirectURL = new StringBuilder("/hajCommunityPersion/appointmentVillage2_0.action");
		redirectURL.append("?sign=");
		redirectURL.append(sign);
		redirectURL.append("&userId=");
		redirectURL.append(userId);
		redirectURL.append("&villageId=");
		redirectURL.append(villageId);
		try {
			response.sendRedirect(redirectURL.toString());
		} catch (IOException e) {
			e.printStackTrace();
			Map<String, Object> jsonMap = new HashMap<>();
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
			try {
				JSONUtils.printObject(jsonMap, response);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * 用户预约小区
	 */
	@RequestMapping(value = "/appointmentVillage2_0")
	public void appointmentVillage2(@RequestParam String sign, @RequestParam Integer userId,
			@RequestParam Integer villageId, Integer oldCommunityId, HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<>();
		try {
			if (MD5Tools.checkSign(sign)) {
				if (userId != null && villageId != null) {
					int resultId = service.appointmentVillage(userId, villageId, oldCommunityId);
					if (resultId == 1) {
						jsonMap.put("error", "0");
						jsonMap.put("msg", "用户预约小区成功");
					} else if (resultId == 2) {
						jsonMap.put("error", "2");
						jsonMap.put("msg", "小区会员已满");
					} else if (resultId == 3) {
						jsonMap.put("error", "3");
						jsonMap.put("msg", "小区为空");
					} else if (resultId == 4) {
						jsonMap.put("error", "4");
						jsonMap.put("msg", "预约失败");
					} else if (resultId == 5) {
						jsonMap.put("error", "5");
						jsonMap.put("msg", "普通会员预约成功");
					}
				} else {
					jsonMap.put("error", "1");
					jsonMap.put("msg", "用户id或者小区id参数异常");
				}
			} else {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "签名异常");
			}
		} catch (Exception e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
			log.info(e.getMessage(), e);
		} finally {
			try {
				JSONUtils.printObject(jsonMap, response);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * 根据小区ID查询小区信息
	 */
	@RequestMapping(value = "getHajCommunityByID")
	public void getHajCommunityByID(@RequestParam String sign, @RequestParam String communityID,
			HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<>();
		try {
			if (MD5Tools.checkSign(sign)) {
				if (communityID != null && !"".equals(communityID)) {
					List<HajCommunityPersion> communityPersionMap = new ArrayList<>();
					String[] ids = communityID.split(",");
					HajCommunityPersion hajCommunityPersion;
					for (String id : ids) {
						hajCommunityPersion = service.queryById(id);
						if (hajCommunityPersion != null) {
							communityPersionMap.add(hajCommunityPersion);
						}
					}
					if (communityPersionMap.size() > 0) {
						jsonMap.put("error", "0");
						jsonMap.put("msg", "成功");
						jsonMap.put("HajCommunityPersion", communityPersionMap);
					} else {
						jsonMap.put("error", "0");
						jsonMap.put("msg", "该小区信息不存在");
					}
				} else {
					jsonMap.put("error", "1");
					jsonMap.put("msg", "小区ID为空");
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
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

}
