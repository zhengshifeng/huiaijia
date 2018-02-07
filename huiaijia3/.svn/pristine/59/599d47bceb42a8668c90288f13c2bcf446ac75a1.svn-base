package com.flf.controller.app;

import com.base.criteria.Criteria;
import com.flf.entity.*;
import com.flf.resolver.RollbackException;
import com.flf.service.*;
import com.flf.service.invite.HajInviteCodeService;
import com.flf.util.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <br>
 * <b>功能：</b>HajFrontUserAppController<br>
 * <br>
 */
@Controller
@RequestMapping(value = "/hajFrontUser")
public class HajFrontUserAppController {

	private final static Logger log = Logger.getLogger(HajFrontUserAppController.class);

	@Autowired(required = false)
	private HajFrontUserService hajFrontUserService;

	@Autowired(required = false)
	private HajCommunityPersionService communityPersionService;

	@Autowired
	private RedisSpringProxy redisSpringProxy;

	@Autowired
	private HajCommunityUnitService hajCommunityUnitService;

	@Autowired
	private HajFrontUserUpdateLogService hajFrontUserUpdateLogService;

	@Autowired
	private HajIntegralDetailsService integralDetailsService;

	@Autowired
	private HajOrderService orderService;

	@Autowired
	private HajInviteCodeService inviteCodeService;

	/**
	 * 获取用户个人信息
	 *
	 * @param sign
	 * @param userId
	 * @param response
	 * @author SevenWong<br>
	 * @date 2016年9月30日下午2:44:39
	 */
	@RequestMapping(value = "/getHajFrontUser")
	public void getHajFrontUserApp(@RequestParam String sign, @RequestParam Integer userId, HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<>();
		try {
			if (MD5Tools.checkSign(sign)) {
				log.info("获取用户个人信息");
				// 下面queryById是旧版本的方式，避免暴露太多敏感信息，所以这里有选择性的返回字段
				// HajFrontUser hajFrontUser = hajFrontUserService.queryById(userId);

				HajFrontUserDTO hajFrontUser = hajFrontUserService.getById4API(userId);

				jsonMap.put("error", "0");
				jsonMap.put("msg", (hajFrontUser != null) ? "成功" : "为空");
				jsonMap.put("hajFrontUser", hajFrontUser);
			} else {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "签名异常");
			}
		} catch (Exception e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
			e.printStackTrace();
		} finally {
			try {
				JSONUtils.printObject(jsonMap, response);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * 通用接口：1.修改用户信息 2.取消会员 3.修改地址 4.签到
	 *
	 * @param sign
	 * @param frontUser
	 * @param response
	 * @author SevenWong<br>
	 * @date 2016年9月30日下午2:46:24
	 */
	@RequestMapping(value = "/hajUserUpdate", method = RequestMethod.POST)
	public void updateUser(@RequestParam String sign, @ModelAttribute HajFrontUser frontUser,
						   HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<>();
		try {
			if (MD5Tools.checkSign(sign)) {
				HajFrontUser queryUserById = hajFrontUserService.queryById(frontUser.getId());

				// 原生会员状态
				frontUser.setAddress(filterEmoji(frontUser.getAddress()));
				frontUser.setReceiver(filterEmoji(frontUser.getReceiver()));
				frontUser.setUsername(filterEmoji(frontUser.getUsername()));

				String tempAddress = frontUser.getAddress();
				if ((frontUser.getAddress() != null && frontUser.getAddress().contains(";"))) {
					// 检查唯一性时去掉分号，用完后还原带分号的地址
					String[] addressArr = frontUser.getAddress().split(";");

					// v3.4.2后，加多一个楼层数
					if (addressArr.length > 2) {
						frontUser.setAddress(addressArr[0] + addressArr[1] + "层" + addressArr[2]);
					} else {
						frontUser.setAddress(frontUser.getAddress().replace(";", ""));
					}

					// 当旧版APP没有传areaCode时，则使用数据库中的areaCode，都没有，则默认使用深圳的areaCode
					frontUser.setAreaCode(Tools.getAreaCode(queryUserById.getAreaCode()));
				}
				int checkAddressUniqueness = hajFrontUserService.checkAddressUniqueness(frontUser);
				log.info("checkAddressUniqueness:" + checkAddressUniqueness);

				// 还原带分号的地址
				frontUser.setAddress(tempAddress);
				if (checkAddressUniqueness < 1) {
					HajCommunityPersion hajCommunityPersion = communityPersionService.getHajCommunityByName(
							queryUserById.getResidential(), queryUserById.getAreaCode());

					// =======================================================
					// 为了规范地址，做了如下处理：
					// 将用户输入的地址根据“单元住址”跟“门牌号”分开保存，用户表保存小区单元表的ID
					// =======================================================
					if (frontUser.getAddress() != null) {
						if (frontUser.getAddress().contains(";") && hajCommunityPersion != null) {
							String[] addressArr = frontUser.getAddress().split(";");
							HajCommunityUnit unit = new HajCommunityUnit();
							unit.setCommunityId(hajCommunityPersion.getId());
							unit.setUnit(addressArr[0]);
							unit = hajCommunityUnitService.getUnit(unit);

							if (unit != null) {
								frontUser.setCommunityUnitId(unit.getId());
								if (addressArr.length == 2) {
									frontUser.setHouseNumber(addressArr[1]);
								} else if (addressArr.length == 3) {
									frontUser.setFloor(Integer.valueOf(addressArr[1]));
									frontUser.setHouseNumber(addressArr[2]);
								}
							} else {
								throw new RollbackException("用户填写的地址后台未录入...");
							}

							// v3.4.2后，加多一个楼层数
							if (addressArr.length > 2) {
								frontUser.setAddress(addressArr[0] + addressArr[1] + "层" + addressArr[2]);
							} else {
								frontUser.setAddress(frontUser.getAddress().replace(";", ""));
							}
						} else {
							throw new RollbackException("用户填写的地址后台未录入...");
						}
					}

					// 更新用户城市编号
					if (null != frontUser.getVillageId()) {
						String cityCode = communityPersionService.getCityCodeByVillageId(frontUser.getVillageId());
						if (null != cityCode) {
							frontUser.setAreaCode(cityCode);
						}
					}

					signIn(frontUser, queryUserById);

					hajFrontUserService.updateBySelective(frontUser);

					if (frontUser.getSignNum() != null) {
						// 签到赠送积分，记录积分明细
						saveIntegralDetails(queryUserById.getId());
					}

					// ===========================
					// 客户地址改变时做操作记录
					// ===========================
					if (frontUser.getAddress() != null
							&& !frontUser.getAddress().equals(queryUserById.getAddress())) {
						HajFrontUserUpdateLog updateLog = new HajFrontUserUpdateLog();
						updateLog.setOperator("用户本人");
						updateLog.setFrontUserId(queryUserById.getId());
						updateLog.setCreateTime(new Date());
						updateLog.setRecord("用户把地址更改为:" + frontUser.getAddress());
						hajFrontUserUpdateLogService.add(updateLog);
					}

					HajFrontUserDTO userDTO = hajFrontUserService.getById4API(frontUser.getId());

					if (null != userDTO) {
						// 取消会员
						if ("4".equals(frontUser.getIsmember())) {
							if (hajCommunityPersion.getStatus() == 1) {
								// 小区正式会员减少1个
								communityPersionService.updateMembersNumberNum(userDTO.getVillageId());
							} else {
								// 小区预备会员减少1个
								communityPersionService.updateAppointmentNum(userDTO.getVillageId());
							}
						}
						userDTO.setUsername(Tools.mobileEncrypt(userDTO.getUsername()));
					}
					jsonMap.put("error", "0");
					jsonMap.put("msg", "成功");
					jsonMap.put("hajFrontUser", userDTO);
				} else {
					jsonMap.put("error", "3");
					jsonMap.put("msg", "地址重复");
				}
			} else {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "签名异常");
			}
		} catch (RollbackException e) {
			jsonMap.put("error", "4");
			jsonMap.put("msg", "您选择的地址不存在，请重新确认");
			log.info(e.getMessage());
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

	/**
	 * 签到赠送积分，记录积分明细
	 */
	private void saveIntegralDetails(Integer userId) {
		HajIntegralDetails integralDetails = new HajIntegralDetails();
		integralDetails.setUserId(userId);
		integralDetails.setRemark("每日签到");
		integralDetails.setDetail("+3");
		try {
			integralDetailsService.saveDetail(integralDetails);
		} catch (Exception e) {
			log.info("签到记录积分异常，useId: " + userId, e);
		}
	}

	/**
	 * 签到功能
	 *
	 * @param userByRequest 此对象中SignStatus、SignNum、Rating三个字段有值才视为此次是签到请求
	 * @param userInDB      数据库中查到的对象
	 */
	private static void signIn(HajFrontUser userByRequest, HajFrontUser userInDB) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		String date = sdf.format(new Date());
		if (!date.equals(userInDB.getSignStatus())// 一天只能签到一次
				&& userByRequest != null
				&& Tools.isNotEmpty(userByRequest.getSignStatus())
				&& (userByRequest.getSignNum() != null && userByRequest.getSignNum() > 0)
				&& (userByRequest.getRating() != null)) {

			// 当用户首次签到时，以下两个字段为空，因此在此处设置默认值
			userInDB.setSignNum(userInDB.getSignNum() == null ? 0 : userInDB.getSignNum());
			userInDB.setRating(userInDB.getRating() == null ? 0 : userInDB.getRating());

			userByRequest.setSignNum(userInDB.getSignNum() + 1);// 签到天数+1
			userByRequest.setRating(userInDB.getRating() + 3);// 签到积分+3
			userByRequest.setSignStatus(date);
			log.info(userInDB.getMobilePhone() + "开始签到");
		} else {
			if (userByRequest != null) {
				userByRequest.setSignNum(null);
				userByRequest.setRating(null);
				userByRequest.setSignStatus(null);
			}
		}
	}

	/**
	 * 用户登录/注册
	 */
	@RequestMapping(value = "/hajUserlogin")
	public void hajUserlogin(@RequestParam String sign, @RequestParam String mobilePhone,
							 @RequestParam String machineNumber, @RequestParam String code, String phoneModel,
							 HttpServletResponse response, String passWord) {
		Map<String, Object> jsonMap = new HashMap<>();
		try {
			if (MD5Tools.checkSign(sign)) {
				if (Tools.notEmpty(mobilePhone) && Tools.notEmpty(machineNumber) && isMobileNO(mobilePhone)) {
					String login_verification_code = (String) redisSpringProxy.read("login_code_" + mobilePhone);
					// 判断是否禁用登录验证码，后台配置
					String disable_vcode = (String) redisSpringProxy.read("SystemConfiguration_disable_vcode");
//					String appstore_checking = (String) redisSpringProxy.read("SystemConfiguration_appstore_checking");
//					String appstore_test_account = (String) redisSpringProxy.read("SystemConfiguration_appstore_test_account");
					boolean disableVcode = Boolean.parseBoolean(disable_vcode);
//					// appstore审核过程中，仅允许 appstore_test_account 可以绕过验证码进行登录
//					if (Boolean.parseBoolean(appstore_checking) && appstore_test_account.equals(mobilePhone)) {
//						disableVcode = true;
//					}
//					log.info(mobilePhone + "-->在redis中的验证码:" + login_verification_code);
					HajFrontUser hajFrontUser = hajFrontUserService.getUserByMobile(mobilePhone);
					//用户有密码情况下登录
					if (passWord != null && passWord != "" && hajFrontUser != null) {
						if (hajFrontUser.getPassword() != null && !hajFrontUser.getPassword().equals(MD5.MD5Encode(passWord))) {
							jsonMap.put("error", "4");
							jsonMap.put("msg", "密码错误");
						} else {
							// 更新最新的设备号
							hajFrontUser.setMachineNumber(machineNumber);
							redisSpringProxy.save("hajfrontUser_" + mobilePhone, hajFrontUser);
							// 更新用户最后登录记录
							hajFrontUserService.updateLoginInfo(hajFrontUser.getId(), machineNumber, phoneModel);
							// 今日订单数
							int todayOrders = orderService.getTodayOrderNumber(hajFrontUser.getId());
							// 今日一元购订单数
							int todayOneYuanOrders = orderService.getTodayOrderCountByUserId(hajFrontUser.getId());
							jsonMap.put("error", "0");
							jsonMap.put("msg", "成功");
							jsonMap.put("result", hajFrontUser);
							jsonMap.put("todayOrders", todayOrders);
							jsonMap.put("todayOneYuanOrders", todayOneYuanOrders);
						}

					} else {
						if (code.equals(login_verification_code) || disableVcode || (passWord != null && passWord != "")) {
							HajFrontUser user = hajFrontUserService.getUserByMobile(mobilePhone);
							if (user != null) {
								// 更新最新的设备号
								user.setMachineNumber(machineNumber);
								redisSpringProxy.save("hajfrontUser_" + mobilePhone, user);
							} else {
								// 数据库没有该用户时，作为新用户注册
								user = hajFrontUserService.save(mobilePhone, machineNumber, phoneModel, passWord);
								redisSpringProxy.save("hajfrontUser_" + mobilePhone, user);
							}
							jsonMap.put("error", "0");
							jsonMap.put("msg", "成功");
							jsonMap.put("result", user);
							// 登录成功后清除验证码
							redisSpringProxy.delete("login_code_" + mobilePhone);
							// 更新用户最后登录记录
							hajFrontUserService.updateLoginInfo(user.getId(), machineNumber, phoneModel);

							// 今日订单数
							int todayOrders = orderService.getTodayOrderNumber(user.getId());

							// 今日一元购订单数
							int todayOneYuanOrders = orderService.getTodayOrderCountByUserId(user.getId());
							jsonMap.put("todayOrders", todayOrders);
							jsonMap.put("todayOneYuanOrders", todayOneYuanOrders);
						} else {
							jsonMap.put("error", "1");
							jsonMap.put("msg", "验证码输入有误");
						}
					}
				} else {
					jsonMap.put("error", "3");
					jsonMap.put("msg", "手机号码格式有误");
					jsonMap.put("result", "0");
					jsonMap.put("mobilePhone", mobilePhone);
				}
			} else {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "签名异常");
				jsonMap.put("result", "0");
				jsonMap.put("user", "");
			}
		} catch (Exception e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
			jsonMap.put("result", "0");
			log.info(jsonMap.get("msg"), e);
		} finally {
			try {
				JSONUtils.printObject(jsonMap, response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	/***获取登录验证码验证***/
	@RequestMapping(value = "/getLoginValidateCode")
	public void getLoginValidateCode(@RequestParam String sign, @RequestParam String code, @RequestParam String phone, HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<>();
		try {
			if (MD5Tools.checkSign(sign)) {
				String login_verification_code = (String) redisSpringProxy.read("login_code_" + phone);
				if (login_verification_code == null) {
					jsonMap.put("error", "3");
					jsonMap.put("msg", "验证码已失效，请重新获取");
				} else if ((login_verification_code != null && !code.equals(login_verification_code))) {
					jsonMap.put("error", "1");
					jsonMap.put("msg", "验证码错误");
				} else {
					jsonMap.put("error", "0");
					jsonMap.put("msg", "成功");
					redisSpringProxy.delete("login_code_" + phone);
				}
			} else {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "签名异常");
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


	/***
	 * 检验手机号码是否存在
	 */
	@RequestMapping(value = "/getValidatePhone")
	public void getValidatePhone(@RequestParam String sign, @RequestParam String phone, HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<>();
		if (MD5Tools.checkSign(sign)) {
			HajFrontUser hajFrontUser = hajFrontUserService.getUserByMobile(phone);
			if (hajFrontUser != null && hajFrontUser.getPassword() != null && !"".equals(hajFrontUser.getPassword())) {
				jsonMap.put("error", "0");
				jsonMap.put("status", "3");
				jsonMap.put("msg", "手机号码对应的账号已在后台，且已设置密码");
			} else if (hajFrontUser != null && ("".equals(hajFrontUser.getPassword()) || hajFrontUser.getPassword() == null)) {
				jsonMap.put("error", "0");
				jsonMap.put("status", "2");
				jsonMap.put("msg", "手机号码对应的账号在后台已注册但未设置密码");
			} else {
				jsonMap.put("error", "0");
				jsonMap.put("status", "1");
				jsonMap.put("msg", "手机号码对应的账号为新注册用户");
			}
		} else {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "签名异常");
		}
		try {
			JSONUtils.printObject(jsonMap, response);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}


	/**
	 * 退出登录
	 */
	@RequestMapping(value = "/hajUserLogout")
	public void hajUserLogout(@RequestParam String sign, @RequestParam String mobilePhone, HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<>();
		if (MD5Tools.checkSign(sign)) {
			if (Tools.isNotEmpty(mobilePhone)) {
				// 退出登录 清空缓存
				redisSpringProxy.delete("hajfrontUser_" + mobilePhone);
				jsonMap.put("error", "0");
				jsonMap.put("msg", "成功");
			} else {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "手机号码异常");
			}
		} else {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "签名异常");
		}
		try {
			JSONUtils.printObject(jsonMap, response);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * 生成小二广播返回给app
	 *
	 * @param sign
	 * @param response
	 * @Author SevenWong
	 */
	@RequestMapping(value = "/getBroadcast")
	public void getBroadcast(@RequestParam String sign, HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		if (MD5Tools.checkSign(sign)) {
			jsonMap.put("error", "0");
			jsonMap.put("msg", "成功");
			jsonMap.put("result", broadcastGenerator());
		} else {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
			jsonMap.put("result", "0");
		}

		try {
			JSONUtils.printObject(jsonMap, response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 生成爱家头条2.0，后台配置中自定义
	 *
	 * @return
	 * @author SevenWong<br>
	 * @date 2016年7月13日下午3:07:06
	 */
	private List<Map<String, String>> broadcastGenerator() {
		List<Map<String, String>> broadcastMapList = new ArrayList<>();
		Map<String, String> broadcastMap = null;

		String broadcastConfig = (String) redisSpringProxy.read("SystemConfiguration_ai_jia_tou_tiao");
		if (Tools.notEmpty(broadcastConfig)) {
			String[] broadcastArr = broadcastConfig.split("@");
			for (int i = 0, len = broadcastArr.length; i < len; i++) {
				broadcastMap = new HashMap<>();
				broadcastMap.put("broadcast", broadcastArr[i]);
				broadcastMapList.add(broadcastMap);
			}
		}

		return broadcastMapList;
	}

	/**
	 * 生成爱家头条1.0，给爱家头条提供小区
	 *
	 * @return
	 * @author SevenWong<br>
	 * @date 2016年7月13日下午2:58:01
	 * @deprecated
	 */
	@SuppressWarnings("unused")
	@Deprecated
	private List<Map<String, String>> broadcastGenerator1() {
		String broadcast = "";
		String community = "";

		// 从小区表中随机读取1条小区名
		List<HajCommunityPersion> communityPersions = communityPersionService.getAllList();
		int len = communityPersions.size();
		int randInt = 0;
		// 返回小二广播给app， 欢迎山水居138****1234用户加入汇爱家！
		List<Map<String, String>> broadcastMapList = new ArrayList<>();
		Map<String, String> broadcastMap = null;
		for (int i = 0; i < 20; i++) {
			broadcastMap = new HashMap<>();
			if (len > 0) {
				randInt = new Random().nextInt(len);
				community = communityPersions.get(randInt).getCommunityName();
			} else {
				// 小区列表无数据，写入默认小区
				community = "山水居";
			}

			// 随机生成1个电话号码138****1234
			// String mobileGenerator = mobileGenerator();
			broadcast = "欢迎".concat(community).concat("新用户加入汇爱家！");
			broadcastMap.put("broadcast", broadcast);
			broadcastMapList.add(broadcastMap);
		}

		return broadcastMapList;
	}

	/**
	 * APP上传头像接口返回头像在服务器上的http地址
	 *
	 * @param file
	 * @param sign
	 * @param request
	 * @param response
	 * @throws Exception
	 * @Author SevenWong
	 */
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public void uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("sign") String sign,
						   HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> jsonMap = new HashMap<>();

		if (MD5Tools.checkSign(sign)) {
			String subPath = "images" + File.separator + "userHead" + File.separator;
			String filePath = FileUpload.uploadFile(file, subPath, request);

			if (!"".equals(filePath)) {
				jsonMap.put("error", "0");
				jsonMap.put("msg", "成功");
				jsonMap.put("result", filePath);
			} else {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "未知异常");
				jsonMap.put("result", "0");
			}
		} else {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
			jsonMap.put("result", "0");
		}
		JSONUtils.printObject(jsonMap, response);
	}

	/**
	 * 将emoji表情处理成"?"保存
	 *
	 * @param source
	 * @return
	 * @author SevenWong<br>
	 * @date 2016年6月30日上午11:32:53
	 */
	private static String filterEmoji(String source) {
		return Tools.filterEmoji(source);
	}

	private static boolean isMobileNO(String mobiles) {
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4])|(18[0-9])|(17[0-8])|(14[5,7,9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	/**
	 * 获取服务器当前时间1.0
	 *
	 * @param sign
	 * @param response
	 * @author SevenWong<br>
	 * @date 2016年10月8日上午11:03:08
	 */
	@RequestMapping(value = "/getServerTime")
	public void getServerTime(@RequestParam String sign, HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			if (MD5Tools.checkSign(sign)) {
				String now = DateUtil.datetime2StrZH_CN(new Date());
				jsonMap.put("msg", "当前服务器时间");
				jsonMap.put("result", now);
				jsonMap.put("error", "0");
			} else {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "签名异常");
			}
			JSONUtils.printObject(jsonMap, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取服务器当前时间2.0
	 * <p>
	 * 当用户使用的设备或APP版本号发生改变时，记录到用户表中
	 *
	 * @param sign
	 * @param mobilePhone
	 * @param machineNumber
	 * @param response
	 * @author SevenWong<br>
	 * @date 2016年10月8日上午11:02:40
	 */
	@RequestMapping(value = "/getServerTime2")
	public void getServerTime2(@RequestParam String sign, String mobilePhone, //
							   String machineNumber, String phoneModel, String appVersion, //
							   HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<>();
		String now = DateUtil.datetime2StrZH_CN(new Date());
		boolean flag = true;
		try {
			if (MD5Tools.checkSign(sign)) {
				if (!Tools.isEmpty(mobilePhone) && Tools.isNotEmpty(machineNumber)) {
					Map<String, Object> condition = new HashMap<>();
					condition.put("mobilePhone", mobilePhone);
					Criteria criteria = new Criteria();
					criteria.setCondition(condition);
					List<HajFrontUser> list = hajFrontUserService.queryByList(criteria);
					if (list.size() > 0) {
						HajFrontUser user = list.get(0);

						phoneModel = (phoneModel == null) ? "" : phoneModel;
						appVersion = (appVersion == null) ? "" : appVersion;

						if (Tools.isNotEmpty(user.getMachineNumber()) && //
								!machineNumber.equals(user.getMachineNumber())) {
							jsonMap.put("error", "1");
							jsonMap.put("msg", "您的登录信息已过期，请重新登录。");
							log.info("您的登录信息已过期，请重新登录。");
							flag = false;
						} else if (!appVersion.equals(user.getAppVersion())) {
							// 当用户使用的设备或APP版本号发生改变时，记录到用户表中

							HajFrontUser user4Update = new HajFrontUser();
							user4Update.setId(user.getId());

							int updateFlag = 0;
							if (Tools.isNotEmpty(appVersion) && !appVersion.equals(user.getAppVersion())) {
								user4Update.setAppVersion(appVersion);
								updateFlag++;
							}
							if (Tools.isNotEmpty(phoneModel) && !phoneModel.equals(user.getPhoneModel())) {
								user4Update.setPhoneModel(phoneModel);
								updateFlag++;
							}
							if (Tools.isNotEmpty(machineNumber) && !machineNumber.equals(user.getMachineNumber())) {
								user4Update.setMachineNumber(machineNumber);
								updateFlag++;
							}

							if (updateFlag > 0) {
								log.info("更新用户" + mobilePhone + "使用的机型或APP版本...");

								hajFrontUserService.updateBySelective(user4Update);

								// 清除用户缓存
								redisSpringProxy.delete("hajfrontUser_" + mobilePhone);

								// 读取用户最新的信息，并写入缓存
								list = hajFrontUserService.queryByList(criteria);
								if (list.size() > 0) {
									user = list.get(0);
									redisSpringProxy.save("hajfrontUser_" + mobilePhone, user);
								}
							}
						}
					}
				}
			} else {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "签名异常");
				flag = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (flag) {
				jsonMap.put("msg", "当前服务器时间");
				jsonMap.put("result", now);
				jsonMap.put("error", "0");
			}
			try {
				JSONUtils.printObject(jsonMap, response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 获取用户当前余额
	 */
	@RequestMapping(value = "/getUserBalance")
	public void getUserBalance(@RequestParam String sign, @RequestParam Integer userId, HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			if (MD5Tools.checkSign(sign)) {
				log.info("进入用户中心，获取用户余额接口，用户id：" + userId);
				HajFrontUser hajFrontUser = hajFrontUserService.queryById(userId);
				BigDecimal balance = hajFrontUser.getBalance();

				jsonMap.put("error", "0");
				jsonMap.put("msg", "成功");
				jsonMap.put("balance", balance);
			} else {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "签名异常");
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

	/***获取修改密码验证码验证***/
	@RequestMapping(value = "/getPwdValidateCode")
	public void getPassWordValidateCode(@RequestParam String sign, @RequestParam String code, @RequestParam String phone, HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<>();
		try {
			if (MD5Tools.checkSign(sign)) {
				String update_password_code = (String) redisSpringProxy.read("update_password_code_" + phone);
				//语音验证码
				String login_code = (String) redisSpringProxy.read("login_code_" + phone);
				if (update_password_code == null && login_code == null) {
					jsonMap.put("error", "3");
					jsonMap.put("msg", "验证码已失效，请重新获取");
				} else if ((update_password_code != null && login_code != null) && !code.equals(update_password_code) && !code.equals(login_code)) {
					jsonMap.put("error", "1");
					jsonMap.put("msg", "验证码错误");
				} else if (update_password_code == null && login_code != null && !code.equals(login_code)) {
					jsonMap.put("error", "1");
					jsonMap.put("msg", "验证码错误");
				} else if (login_code == null && update_password_code != null && !code.equals(update_password_code)) {
					jsonMap.put("error", "1");
					jsonMap.put("msg", "验证码错误");
				} else {
					jsonMap.put("error", "0");
					jsonMap.put("msg", "成功");
					redisSpringProxy.delete("update_password_code_" + phone);
					if (login_code != null && login_code != "") {
						redisSpringProxy.delete("login_code_" + phone);
					}
				}
			} else {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "签名异常");
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

	/***修改登录密码***/
	@RequestMapping(value = "/updatePassWord")
	public void updatePassWord(@RequestParam String sign, @RequestParam String passWord, @RequestParam String phone, HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			if (MD5Tools.checkSign(sign)) {
				HajFrontUser hajFrontUser = hajFrontUserService.getUserByMobile(phone);
				log.info("修改登录密码用户手机" + phone);
				if (hajFrontUser != null) {
					HajFrontUser user = new HajFrontUser();
					user.setId(hajFrontUser.getId());
					user.setPassword(MD5.MD5Encode(passWord));
					hajFrontUserService.updateFrontUser(user);
					jsonMap.put("error", "0");
					jsonMap.put("msg", "成功");
				} else {
					jsonMap.put("error", "3");
					jsonMap.put("msg", "未获取用户");
				}

			} else {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "签名异常");
			}
		} catch (Exception e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
			e.printStackTrace();
		} finally {
			try {
				JSONUtils.printObject(jsonMap, response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}


	/***获取修改手机号码验证码验证***/
	@RequestMapping(value = "/getPhoneValidateCode")
	public void getPhoneValidateCode(@RequestParam String sign, @RequestParam String code, @RequestParam String phone, HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<>();
		try {
			if (MD5Tools.checkSign(sign)) {
				String update_phone_code = (String) redisSpringProxy.read("update_phone_code_" + phone);
				//语音验证码
				String login_code = (String) redisSpringProxy.read("login_code_" + phone);
				if (update_phone_code == null && login_code == null) {
					jsonMap.put("error", "3");
					jsonMap.put("msg", "验证码已失效，请重新获取");
				} else if ((update_phone_code != null && login_code != null) && !code.equals(update_phone_code) && !code.equals(login_code)) {
					jsonMap.put("error", "1");
					jsonMap.put("msg", "验证码错误");
				} else if (update_phone_code == null && login_code != null && !code.equals(login_code)) {
					jsonMap.put("error", "1");
					jsonMap.put("msg", "验证码错误");
				} else if (login_code == null && update_phone_code != null && !code.equals(update_phone_code)) {
					jsonMap.put("error", "1");
					jsonMap.put("msg", "验证码错误");
				} else {
					jsonMap.put("error", "0");
					jsonMap.put("msg", "成功");
					redisSpringProxy.delete("update_phone_code_" + phone);
					if (login_code != null && login_code != "") {
						redisSpringProxy.delete("login_code_" + phone);
					}
				}
			} else {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "签名异常");
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


	/***修改手机号码***/
	@RequestMapping(value = "/updatePhone")
	public void updatePhone(@RequestParam String sign, @RequestParam String phone, @RequestParam String code, @RequestParam Integer userId, HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			if (MD5Tools.checkSign(sign)) {
				log.info("进入修改手机号码用户id：" + userId);
				String update_phone_code = (String) redisSpringProxy.read("update_phone_code_" + phone);
				//语音验证码
				String login_code = (String) redisSpringProxy.read("login_code_" + phone);
				if (update_phone_code == null && login_code == null) {
					jsonMap.put("error", "4");
					jsonMap.put("msg", "验证码已失效，请重新获取");
				}  else if ((update_phone_code != null && login_code != null) && !code.equals(update_phone_code) && !code.equals(login_code)) {
					jsonMap.put("error", "1");
					jsonMap.put("msg", "验证码错误");
				} else if (update_phone_code == null && login_code != null && !code.equals(login_code)) {
					jsonMap.put("error", "1");
					jsonMap.put("msg", "验证码错误");
				} else if (login_code == null && update_phone_code != null && !code.equals(update_phone_code)) {
					jsonMap.put("error", "1");
					jsonMap.put("msg", "验证码错误");
				}else {
					HajFrontUser hajFrontUser = hajFrontUserService.getUserByMobile(phone);
					if (hajFrontUser != null) {
						jsonMap.put("error", "3");
						jsonMap.put("msg", "号码已存在，请重新输入!");
					} else {
						HajFrontUser user = new HajFrontUser();
						user.setId(userId);
						user.setMobilePhone(phone);
						hajFrontUserService.updateFrontUser(user);
						redisSpringProxy.delete("update_phone_code_" + phone);
						if (login_code != null && login_code != "") {
							redisSpringProxy.delete("login_code_" + phone);
						}
						jsonMap.put("error", "0");
						jsonMap.put("msg", "成功");
					}
				}
			} else {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "签名异常");
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

	/***支付密码验证接口***/
	@RequestMapping(value = "/getPayPwdValidateCode")
	public void getPayPassWordValidateCode(@RequestParam String sign, @RequestParam String code, @RequestParam String phone, HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<>();
		try {
			if (MD5Tools.checkSign(sign)) {
				String open_paypassword_code = (String) redisSpringProxy.read("open_paypassword_code_" + phone);
				//语音验证码
				String login_code = (String) redisSpringProxy.read("login_code_" + phone);
				if (open_paypassword_code == null && login_code == null) {
					jsonMap.put("error", "3");
					jsonMap.put("msg", "验证码已失效，请重新获取");
				} else if ((open_paypassword_code != null && login_code != null) && !code.equals(open_paypassword_code) && !code.equals(login_code)) {
					jsonMap.put("error", "1");
					jsonMap.put("msg", "验证码错误");
				} else if (open_paypassword_code == null && login_code != null && !code.equals(login_code)) {
					jsonMap.put("error", "1");
					jsonMap.put("msg", "验证码错误");
				} else if (login_code == null && open_paypassword_code != null && !code.equals(open_paypassword_code)) {
					jsonMap.put("error", "1");
					jsonMap.put("msg", "验证码错误");
				} else {
					jsonMap.put("error", "0");
					jsonMap.put("msg", "成功");
					redisSpringProxy.delete("open_paypassword_code_" + phone);
					if (login_code != null && login_code != "") {
						redisSpringProxy.delete("login_code_" + phone);
					}
				}
			} else {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "签名异常");
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


	/***关闭支付密码验证接口***/
	@RequestMapping(value = "/getClosePayPwdValidateCode")
	public void getClosePayPwdValidateCode(@RequestParam String sign, @RequestParam String code, @RequestParam String phone, HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<>();
		try {
			if (MD5Tools.checkSign(sign)) {
				String close_paypassword_code = (String) redisSpringProxy.read("close_paypassword_code_" + phone);
				//语音验证码
				String login_code = (String) redisSpringProxy.read("login_code_" + phone);
				if (close_paypassword_code == null && login_code == null) {
					jsonMap.put("error", "3");
					jsonMap.put("msg", "验证码已失效，请重新获取");
				} else if ((close_paypassword_code != null && login_code != null) && !code.equals(close_paypassword_code) && !code.equals(login_code)) {
					jsonMap.put("error", "1");
					jsonMap.put("msg", "验证码错误");
				} else if (close_paypassword_code == null && login_code != null && !code.equals(login_code)) {
					jsonMap.put("error", "1");
					jsonMap.put("msg", "验证码错误");
				} else if (login_code == null && close_paypassword_code != null && !code.equals(close_paypassword_code)) {
					jsonMap.put("error", "1");
					jsonMap.put("msg", "验证码错误");
				} else {
					jsonMap.put("error", "0");
					jsonMap.put("msg", "成功");
					redisSpringProxy.delete("close_paypassword_code_" + phone);
					if (login_code != null && login_code != "") {
						redisSpringProxy.delete("login_code_" + phone);
					}
				}
			} else {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "签名异常");
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

	/***支付密码确认支付***/
	@RequestMapping(value = "/getPayPwd")
	public void getPayPwd(@RequestParam String sign, @RequestParam String payPwd, @RequestParam Integer userId, HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			if (MD5Tools.checkSign(sign)) {
				log.info("支付密码确认支付用户id：" + userId);
				HajFrontUser hajFrontUser = hajFrontUserService.queryById(userId);
				if (hajFrontUser != null && !hajFrontUser.getPayPassword().equals(MD5.MD5Encode(payPwd))) {
					jsonMap.put("error", "1");
					jsonMap.put("msg", "支付密码错误");
				} else {
					jsonMap.put("error", "0");
					jsonMap.put("msg", "成功");
				}
			} else {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "签名异常");
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


	/***修改支付密码***/
	@RequestMapping(value = "/updatePayPwd")
	public void updatePayPwd(@RequestParam String sign, @RequestParam String payPwd, @RequestParam int payPwdStatus, @RequestParam Integer userId, HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			if (MD5Tools.checkSign(sign)) {
				log.info("进入修改支付密码用户id：" + userId);
				HajFrontUser user = new HajFrontUser();
				user.setId(userId);
				user.setPayPassword(MD5.MD5Encode(payPwd));
				user.setPayPasswordStatus(payPwdStatus);
				hajFrontUserService.updateFrontUser(user);
				jsonMap.put("error", "0");
				jsonMap.put("msg", "成功");
			} else {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "签名异常");
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


}
