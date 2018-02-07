package com.flf.controller;

import com.base.criteria.Criteria;
import com.base.criteria.UserManagerCriteria;
import com.base.util.HttpUtil;
import com.flf.entity.*;
import com.flf.service.*;
import com.flf.util.*;
import com.flf.view.CustomerExcelView;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 汇爱家用户管理模块Controller
 * 
 */

@Controller
public class HajUserMangerController {

	private final static Logger log = Logger.getLogger(HajUserMangerController.class);

	@Autowired(required = false)
	private HajFrontUserService service;

	@Autowired(required = false)
	private HajUserFamilyService hajUserFamilyService;

	@Autowired(required = false)
	private HajTradingHistoryService hajTradingHistoryService;

	@Autowired(required = false)
	private HajAreasService areasService;

	@Autowired(required = false)
	private HajCommunityPersionService communityPersionService;

	@Autowired(required = false)
	private HajCommunityUnitService communityUnitService;

	@Autowired(required = false)
	private HajFrontUserUpdateLogService hajFrontUserUpdateLogService;

	@Autowired
	private RedisSpringProxy redisSpringProxy;

	@Autowired(required = false)
	private HajOrderPostFeeService hajOrderPostFeeService;

	@Autowired(required = false)
	private HajCommunityBuildingService buildingService;

	@Autowired(required = false)
	private HajCommunityBuildingService communityBuildingService;


	/**
	 * 后台用户管理批量查询指定用户相关信息
	 * 
	 */
	@RequestMapping(value = "/userManger")
	public ModelAndView userMangerList(HttpSession session, UserManagerCriteria criteria) {
		ModelAndView mv = new ModelAndView();
		try {
			User user = (User) session.getAttribute(Const.SESSION_USER);
			if (null != user) {
				criteria.setAreaCode(user.getAreaCode());
			}

			List<Map<String, Object>> userManagerList = service.getUserManagerList(criteria);

			// 封装计算页面会员年龄
			calcAgeByBirthday(userManagerList);

			areasService.putAreaList(mv, criteria.getProvinceCode(), criteria.getCityCode());

			//返回小区名称列表
			List<HajCommunityPersion> communityList = communityPersionService.getCommunityList();

			mv.addObject("communityList", communityList);
			mv.addObject("userManagerList", userManagerList);
			mv.addObject("criteria", criteria);
			mv.setViewName("customer");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

	private void calcAgeByBirthday(List<Map<String, Object>> userManagerList) throws ParseException {
		if (userManagerList != null && userManagerList.size() > 0) {
			for (Map<String, Object> map : userManagerList) {
				String birthday = (String) map.get("birthday");
				SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD");
				if (birthday != null && !"".equals(birthday)) {
					int age = getAgeByBirthday(sdf.parse(birthday));
					map.put("age", age);
				}
			}
		}
	}

	/**
	 * excel导出客户信息
	 * 
	 */
	@RequestMapping(value = "/exportCustomerInfo")
	public ModelAndView exportCustomerInfo(HttpSession session, UserManagerCriteria criteria) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("用户编号");
		titles.add("用户名");
		titles.add("手机号码");
		titles.add("注册时间");
		titles.add("小区");
		titles.add("小区地址");
		titles.add("用户住址");
		titles.add("收货人");
		titles.add("用户角色");
		titles.add("历史订单数");
		titles.add("历史订单总价");
		titles.add("平均订单金额");
		dataMap.put("titles", titles);
		criteria.setPageSize(65535);
		User user = (User) session.getAttribute(Const.SESSION_USER);
		if (null != user) {
			criteria.setAreaCode(user.getAreaCode());
		}

		List<Map<String, Object>> userManagerList = service.getUserManagerList(criteria);
		dataMap.put("userManagerList", userManagerList);
		CustomerExcelView cev = new CustomerExcelView();
		return new ModelAndView(cev, dataMap);
	}

	/**
	 * 后台用户管理根据id,或者用户名，或者联系方式精确查询用户信息
	 * 
	 */
	@RequestMapping(value = "/getHajUserManagerById")
	public ModelAndView getHajUserManagerById(@RequestParam String parameter, @RequestParam String page) {
		ModelAndView mv = new ModelAndView();
		try {
			Map<String, Object> userManager = null;
			List<Map<String, Object>> userManagerList = service.getUserManagerById(parameter.trim());
			if ("customerView".equals(page) || "customerUpdate".equals(page)) {
				userManager = userManagerList.get(0);
				mv.addObject("userManager", userManager);
			}
			if ("customerView".equals(page)) {
				Map<String, Object> condition = new HashMap<>();
				condition.put("userId", parameter);
				Criteria criteria = new Criteria();
				criteria.setCondition(condition);
				List<HajUserFamily> hajUserFamilyList = hajUserFamilyService.queryByList(criteria);
				mv.addObject("hajUserFamilyList", hajUserFamilyList);
				mv.addObject("criteria", criteria);
				// 计算会员结束时间
				if (userManager.get("memberTerm") != null && userManager.get("memberBeginTime") != null
						&& !userManager.get("memberTerm").equals("") && !userManager.get("memberBeginTime").equals("")) {
					String memberBeginTime = String.valueOf(userManager.get("memberBeginTime"));
					int memberTerm = Integer.parseInt(String.valueOf(userManager.get("memberTerm")));
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					if (!"暂无".equals(memberBeginTime) && !memberBeginTime.equals("")) {
						Date memberBeginDate = sdf.parse(memberBeginTime);
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(memberBeginDate);
						calendar.add(Calendar.DAY_OF_WEEK, memberTerm);
						String memberOverDate = sdf.format(calendar.getTime());
						userManager.put("memberOverDate", memberOverDate);
					}
				}
			}
			// 封装计算页面会员年龄
			calcAgeByBirthday(userManagerList);

			List<Map<String, Object>> userCommodityType = service.getUserCommodityType(parameter);
			mv.addObject("userCommodityType", userCommodityType);

			HajCommunityPersion hajCommunityByName = null;
			if (userManager != null) {
				// 根据用户小区名获取小区
				hajCommunityByName = communityPersionService.getHajCommunityByName(
						String.valueOf(userManager.get("residential")), String.valueOf(userManager.get("areaCode")));

				List<HajCommunityUnit> unitList;
				if (hajCommunityByName != null) {
					// 根据小区ID获取小区单元地址
					unitList = communityUnitService.getUnitList(hajCommunityByName.getId());
					mv.addObject("unitList", unitList);
				}
			}
			List<HajCommunityBuilding> buildingList = null;
			if (hajCommunityByName!=null) {
				buildingList=buildingService.getList4api(hajCommunityByName.getId());
			}

			mv.addObject("userManagerList", userManagerList);
			mv.addObject("parameter", parameter);
			mv.addObject("community", hajCommunityByName);
			mv.addObject("buildingList", buildingList);
			mv.setViewName(page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}


	/**
	 * 联动获取楼栋信息
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getBuildList",produces={"text/html;charset=UTF-8;","application/json;"})
	@ResponseBody
	public String getBuildList(Integer parentId) throws Exception {
		List<HajCommunityBuilding> buildList = buildingService.getBuildList(parentId);
		return JSONUtils.toJSONString(buildList);
	}


	/**
	 * 统计时间段区间订单数，平均订单金额，所以订单金额
	 */
	@RequestMapping(value = "/statisticsOrderByUser", method = RequestMethod.GET)
	public void statisticsOrderByUser(@RequestParam String beginTime, @RequestParam String overTime,
			@RequestParam Integer id, HttpServletResponse response) throws IOException {
		Map<String, Object> map = new HashMap<>();
		map.put("beginTime", beginTime);
		map.put("overTime", overTime);
		map.put("id", id);
		try {
			HashMap<String, Object> statisticsOrder = service.statisticsOrderByUserId(map);
			List<HashMap<String, Object>> statisticsOrderCommodityType = service.statisticsOrderCommodityTypeByUserId(map);
			map.put("statisticsOrder", statisticsOrder);
			map.put("statisticsOrderCommodityType", statisticsOrderCommodityType);
			map.put("success", "true");
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONUtils.printObject(map, response);
	}

	/**
	 * 后台用户管理根据用户id更新用户信息
	 * 
	 */
	@RequestMapping(value = "/updateCustomerById")
	public void updateCustomerById(HajFrontUserVo userManager, HttpServletResponse response, HttpSession session) {
		Map<String, String> jsonMap = new HashMap<>();
		jsonMap.put("result", "success");
		jsonMap.put("msg", "保存成功！");
		boolean updateFlag = true;

		/* 当前操作的用户 */
		User operator = (User) session.getAttribute(Const.SESSION_USER);

		HajFrontUserUpdateLog updateLog;
		try {

			HajFrontUser queryById = service.queryById(userManager.getId());
			if (queryById != null) {
				String isMember = queryById.getIsmember();			//会员状态:1仅注册 2预备会员 3一元购会员 4会员取消 5普通会员
				if ("4".equals(isMember) && "1".equals(userManager.getIsmember())) {
					userManager.setSignNum(0);						//签到次数
					userManager.setMemberBeginTime("暂无");			//会员开始日期
					userManager.setBalance(BigDecimal.ZERO);		//用户余额
				}

				//--------------下边注释旧版留码--------------------------------------------------------------------

//				if (userManager.getCommunityUnitId() != null) {		//haj_community_unit外键
//					HajCommunityUnit unit = communityUnitService.queryById(userManager.getCommunityUnitId());
//					if (unit != null) {
//				String tempAddress = unit.getUnit() + userManager.getFloor() + "层"				//用户住址
//						+ userManager.getHouseNumber();
				//-----------------------------------------------------------------------------------------------

//				if (queryById.getAddress() != null) {
					//只修改楼层和门牌号
					if (userManager.getTerm() == -1 ) {
						if (queryById.getCommunityUnitId()!=null && queryById.getCommunityUnitId()>0) {
							HajCommunityUnit communityUnit = communityUnitService.queryById(queryById.getCommunityUnitId());
							userManager.setAddress(communityUnit.getUnit()+ userManager.getFloor() + "层"					//用户住址
									+ userManager.getHouseNumber());
						}
					} else {
						String communityUnit = "";
						if (userManager.getTerm()!=null) {
							HajCommunityBuilding term = buildingService.queryById(userManager.getTerm());	//小区楼期
							communityUnit+=term.getName();
							userManager.setBuildingId(userManager.getTerm());								//最后一级的楼栋ID
						}

						if (userManager.getBuild()!=null) {
							HajCommunityBuilding build = buildingService.queryById(userManager.getBuild());	//小区楼栋
							communityUnit+=build.getName();
							userManager.setBuildingId(userManager.getBuild());
						}

						if (userManager.getUnit()!=null) {
							HajCommunityBuilding unit = buildingService.queryById(userManager.getUnit());	//小区单元
							communityUnit+=unit.getName();
							userManager.setBuildingId(userManager.getUnit());
						}

						if (userManager.getSeat()!=null) {
							HajCommunityBuilding seat = buildingService.queryById(userManager.getSeat());	//小区楼座
							communityUnit+=seat.getName();
							userManager.setBuildingId(userManager.getSeat());
						}

						String tempAddress = communityUnit + userManager.getFloor() + "层"					//用户住址
									+ userManager.getHouseNumber();
						if (!tempAddress.equals(queryById.getAddress())) {
							userManager.setAddress(tempAddress);											//修改地址

							HajCommunityUnit unitObj = new HajCommunityUnit();
							unitObj.setCommunityId(queryById.getVillageId());
							unitObj.setUnit(communityUnit);
							HajCommunityUnit unitId = communityUnitService.getUnit(unitObj);
							if (unitId != null) {
								userManager.setCommunityUnitId(unitId.getId());
							} else {
								userManager.setCommunityUnitId(0);											//未匹配到楼层关系
							}
							userManager.setFloor(userManager.getFloor());
							userManager.setHouseNumber(userManager.getHouseNumber());
							//获取communityUnitId
							HajCommunityUnit cUnit = new HajCommunityUnit();
							cUnit.setUnit(communityUnit);
							cUnit.setCommunityId(queryById.getVillageId());
							HajCommunityUnit unitAddress = communityUnitService.getUnit(cUnit);
							if (unitAddress!=null) {
								userManager.setCommunityUnitId(unitAddress.getId());
							}

							// 以下两个字段不能由管理后台修改，因此需要从数据库获取
							userManager.setAreaCode(queryById.getAreaCode());								//城市编码
							userManager.setResidential(queryById.getResidential());							//小区名称

						}
					}

					// 地址惟一性判断
					int checkAddressUniqueness = service.checkAddressUniqueness(userManager);
					updateFlag = checkAddressUniqueness < 1;

					// 未选择单元视为清空地址
//					if (userManager.getCommunityUnitId() == 0) {
//						userManager.setAddress("");
//						userManager.setHouseNumber("");
//						userManager.setFloor(0);
//						userManager.setBuildingId(0);
//					}

//				}

				// 更新用户余额增加流水，验证金额合法性
				if (null != userManager.getBalance() //
						&& userManager.getBalance().compareTo(new BigDecimal("0")) > 0//
						&& queryById.getBalance().compareTo(userManager.getBalance()) >= 0) {

					HajTradingHistory trading = new HajTradingHistory();
					trading.setTradingContent("余额提现减少余额：" + userManager.getBalance());

					trading.setCreateTime(DateUtil.dateToString(new Date()));
					trading.setMoney(userManager.getBalance());
					trading.setTradingStatus(1);
					trading.setUserId(userManager.getId());
					trading.setType(0);// 减少
					hajTradingHistoryService.saveTradeRecord(trading);
					service.updateUserwithdrawBalance(userManager.getId(), userManager.getBalance(),
							userManager);

					// ===========================
					// 修改客户信息时做操作记录
					// ===========================
					/* 当前操作的用户 */
					operator = (User) session.getAttribute(Const.SESSION_USER);

					updateLog = new HajFrontUserUpdateLog();
					updateLog.setOperator(operator.getUsername());
					updateLog.setFrontUserId(userManager.getId());
					updateLog.setCreateTime(new Date());
					updateLog.setRecord("余额提现：￥" + userManager.getBalance());
					hajFrontUserUpdateLogService.add(updateLog);
				}

				if (updateFlag) {
					if (null != userManager.getIsmember()) {
						userManager.setAppointmentTime(DateUtil.datetime2Str(new Date()));		//预约时间
					}
					service.updateBySelective(userManager);

					// ===========================
					// 修改客户信息时做操作记录
					// ===========================
					if (queryById.getAddress() != null && !queryById.getAddress().equals(userManager.getAddress())) {
						updateLog = new HajFrontUserUpdateLog();
						updateLog.setOperator(operator.getUsername());
						updateLog.setFrontUserId(userManager.getId());
						updateLog.setCreateTime(new Date());
						if (Tools.isNotEmpty(userManager.getAddress())) {
							updateLog.setRecord("用户地址被更改为:" + userManager.getAddress());
						} else {
							updateLog.setRecord("用户地址被清空");
						}
						hajFrontUserUpdateLogService.add(updateLog);
					}
				} else {
					jsonMap.put("result", "error");
					jsonMap.put("msg", "更新失败，可能该用户的地址已被注册！");
				}
			}

			if (null != userManager.getIsPay()) {
				// 更新用户收取运费状态
				hajOrderPostFeeService.updateOrderPostStatusByUserId(userManager.getId(),
						Integer.parseInt(userManager.getIsPay()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				JSONUtils.printObject(jsonMap, response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 后台用户管理根据用户id删除用户信息
	 * 
	 */
	@RequestMapping(value = "/deleteCustomerById")
	public void deleteCustomerById(@RequestParam Integer id, HttpServletResponse response) {
		try {
			HajFrontUser hajFrontUser = service.queryById(id);
			if (hajFrontUser != null) {
				// 删除用户 清空缓存
				redisSpringProxy.delete("hajfrontUser_" + hajFrontUser.getMobilePhone());
			}

			service.delete(id);
			JSONUtils.printObject("{status:'success'}", response);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 根据用户生日计算年龄
	 */
	private static int getAgeByBirthday(Date birthday) {
		Calendar cal = Calendar.getInstance();

		if (cal.before(birthday)) {
			throw new IllegalArgumentException("The birthDay is before Now.It's unbelievable!");
		}

		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH) + 1;
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

		cal.setTime(birthday);
		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH) + 1;
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

		int age = yearNow - yearBirth;

		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				// monthNow==monthBirth
				if (dayOfMonthNow < dayOfMonthBirth) {
					age--;
				}
			} else {
				// monthNow>monthBirth
				age--;
			}
		}
		return age;
	}

	/**
	 * 
	 * 客户交易统计
	 * 
	 */
	@RequestMapping(value = "/customerDeal")
	public ModelAndView querCustomerDealList(HttpSession session, HajCustomerDeal criteria) {
		ModelAndView mv = new ModelAndView();
		try {
			User user = (User) session.getAttribute(Const.SESSION_USER);
			if (null != user) {
				criteria.setAreaCode(user.getAreaCode());
			}

			List<Map<String, Object>> customer_dealList = service.querCustomer_dealList(criteria);

			mv.addObject("customer_dealList", customer_dealList);
			mv.addObject("criteria", criteria);
			mv.setViewName("customer_deal");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

	/**
	 * 导出客户交易
	 * 
	 * @param response
	 */
	@RequestMapping(value = "/exportCustomerDeal")
	public void exportCustomerDeal(HttpSession session, HajCustomerDeal customerDeal, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			User user = (User) session.getAttribute(Const.SESSION_USER);
			if (null != user) {
				customerDeal.setAreaCode(user.getAreaCode());
			}

			String filename = "客户交易";
			filename = HttpUtil.encodeFilename(request, filename);
			filename = DateUtil.dateformat(new Date(), "MM-dd") + filename;

			XSSFWorkbook wb = service.exportCustomerDeal(customerDeal);
			ExcelUtil.output(response, filename, wb);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 根据用户ID批量更新用户状态
	 * 
	 * @author SevenWong<br>
	 * @date 2016年9月18日下午5:20:29
	 * @param id
	 * @param status
	 * @param response
	 */
	@RequestMapping(value = "/updateUsersStatus")
	public void updateUsersStatus(@RequestParam Integer[] id, @RequestParam Integer status,
			@RequestParam String passport, HttpServletResponse response, HttpSession session) {
		Map<String, Object> jsonMap = new HashMap<>();
		jsonMap.put("error", "0");
		jsonMap.put("msg", "修改成功");
		try {
			String passportConfig = Config.getConfigProperties("update.user.status.pwd", "");
			// 当前操作的用户
			User user = (User) session.getAttribute(Const.SESSION_USER);

			if (passportConfig.equals(MD5.MD5Encode(passport))) {
				jsonMap = beforeUpdateUsersStatus(id, status, jsonMap);

				if (jsonMap.get("error").toString().equals("0")) {
					boolean updateFlag = service.updateUsersStatus(id, status, user.getUsername());
					if (!updateFlag) {
						jsonMap.put("error", "2");
						jsonMap.put("msg", "未知异常");
					}
				}
			} else {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "通行证验证失败！");
				log.info(jsonMap.get("msg"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				JSONUtils.printObject(jsonMap, response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	/**
	 * 根据用户id批量更新用户的小区名称
	 * @param id
	 * @param communityName
	 * @param passport
	 * @param response
	 * @param session
	 */
	@RequestMapping(value = "/updateCommunityName")
	public void updateCommunityName(@RequestParam Integer[] id, @RequestParam String communityName,
								  @RequestParam String passport, HttpServletResponse response, HttpSession session) {
		Map<String, Object> jsonMap = new HashMap<>();
		jsonMap.put("error", "0");
		jsonMap.put("msg", "修改成功");
		try {
			String passportConfig = Config.getConfigProperties("update.user.status.pwd", "");
			// 当前操作的用户
			User user = (User) session.getAttribute(Const.SESSION_USER);

			if (passportConfig.equals(MD5.MD5Encode(passport))) {

				if (jsonMap.get("error").toString().equals("0")) {
					boolean updateFlag = service.updateUsersCommunityName(id, communityName, user.getUsername());

					//清除缓存(用户信息相关接口的缓存和小区相关接口的缓存)

					if (!updateFlag) {
						jsonMap.put("error", "2");
						jsonMap.put("msg", "未知异常");
					}
				}
			} else {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "通行证验证失败！");
				log.info(jsonMap.get("msg"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				JSONUtils.printObject(jsonMap, response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 批量修改用户之前，做一些安全性校验
	 * @param userIds
	 * @param status
	 * @param jsonMap
	 * @return
	 */
	private Map<String, Object> beforeUpdateUsersStatus(Integer[] userIds, Integer status, Map<String, Object> jsonMap) {
		if (userIds.length > 0 && status != null) {
			if (status == 3) {
				// 状态调整为一元购会员才需要判断小区是否激活
				List<HajCommunityPersion> communityList;
				communityList = communityPersionService.getByUserIds(userIds);

				List<String> errorList = new ArrayList<>();
				for (HajCommunityPersion community : communityList) {
					if (!(community.getStatus() != null && community.getStatus() == 1)) {
						errorList.add(community.getTelphone());
					}
				}

				if (errorList.size() > 0) {
					jsonMap.put("error", "1");
					jsonMap.put("msg", "用户" + errorList + "未选择小区或小区未激活");
				}
			}
		} else {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "请选择用户或需要调整的角色");
		}

		return jsonMap;
	}

	/**
	 * 后台下单获取用户信息
	 */
	@RequestMapping(value = "/hajUserInfo")
	public void hajUserInfo(String userCondition, HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		if (null != userCondition && !"".equals(userCondition)) {
			HashMap<String, String> user = service.getUserByCondition(userCondition);
			if (null != user) {
				jsonMap.put("error", "0");
				jsonMap.put("msg", "成功");
			} else {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "失败");
			}
			jsonMap.put("user", user);
		} else {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "用户编号/手机号码/收货人异常");
		}

		try {
			JSONUtils.printObject(jsonMap, response);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	@RequestMapping(value = "/batchCustomer")
	public void batchCustomer(HttpSession session, HajFinancial orderVo, HttpServletResponse response,
			HttpServletRequest request) {
		try {
			String filename = "客户余额明细导出";
			filename = HttpUtil.encodeFilename(request, filename);
			filename = DateUtil.dateformat(new Date(), "MM-dd") + filename;

			User user = (User) session.getAttribute(Const.SESSION_USER);
			if (null != user) {
				orderVo.setAreaCode(user.getAreaCode());
			}

			XSSFWorkbook wb = service.excelbatchCustomer(orderVo);
			ExcelUtil.output(response, filename, wb);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
