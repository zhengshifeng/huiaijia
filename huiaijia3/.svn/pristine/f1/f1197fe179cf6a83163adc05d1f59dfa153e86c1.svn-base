package com.flf.controller.inivite.api;

import com.base.criteria.Criteria;
import com.base.util.HttpUtil;
import com.flf.entity.HajFrontUser;
import com.flf.entity.invite.HajInviteCode;
import com.flf.service.HajFrontUserService;
import com.flf.service.IHajSMSPushService;
import com.flf.service.RedisSpringProxy;
import com.flf.service.invite.HajInviteCodeService;
import com.flf.service.invite.HajMemberInvitationsService;
import com.flf.util.MD5Tools;
import com.utils.Result;
import com.utils.ResultCode;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : RockDing
 * @version : 1.0
 * @ClassName: InviteApiController
 * @Description: app邀请api
 * @Copyright : 深圳市南山软件产业基地1A栋705
 * @Company : 深圳市橙社网络科技有限公司
 * @date 2017年12月23日 上午11:37:33
 */
@Controller
@RequestMapping("app/invite")
public class InviteApiController {
	private final static Logger LOGGER = LoggerFactory.getLogger(InviteApiController.class);
	@Autowired
	private HajFrontUserService frontUserService;
	@Autowired
	private HajMemberInvitationsService memberInvitationsService;
	@Autowired
	private HajInviteCodeService inviteCodeService;
	@Autowired
	private IHajSMSPushService sMSPushService;
	@Autowired
	private RedisSpringProxy redisSpringProxy;

	/**
	 * @param sign
	 * @param inviter
	 * @return HajMemberInvitations    返回类型
	 * @throws
	 * @Title: queryMemberInvitations
	 * @Description: 查询邀请人列表
	 */
	@RequestMapping("/queryMemberInvitations")
	@ResponseBody
	public Result queryMemberInvitations(String sign, String inviter, HttpServletResponse response) {

		HttpUtil.accessControlAllowOrigin(response);
		//1、参数校验
		if (StringUtils.isBlank(sign)) {
			return Result.ERROR(ResultCode.PARAMTS_NOT_NULL, "sign=" + sign, "参数不能为空！");
		}
		if (StringUtils.isBlank(inviter)) {
			return Result.ERROR(ResultCode.PARAMTS_NOT_NULL, "inviter=" + inviter, "参数不能为空！");
		}
		//簽名驗證
		if (!MD5Tools.checkSign(sign)) {
			LOGGER.error("/queryMemberInvitations==>签名校验失败！sign=" + sign);
			return Result.valueOf(ResultCode.SIGN_FAIL, sign, "签名校验失败！");
		}
		//业务处理
		try {
			List<Map> list = memberInvitationsService.queryByInviter(inviter);
			Integer successCnt = memberInvitationsService.countByInviterSuccess(inviter);
			HashMap<String, Object> m = new HashMap<>();
			m.put("inviteeSize", successCnt);
			if (list != null && list.size() > 0) {
				for (Map map : list) {
					Date date = (Date) map.get("create_time");
					SimpleDateFormat myFmt = new SimpleDateFormat("yyyy.MM.dd");
					map.put("create_time", myFmt.format(date));
				}
			}
			m.put("invitees", list);
			return Result.SUCCESS(m, "成功！");
		} catch (Exception e) {
			String eStr = ExceptionUtils.getFullStackTrace(e);
			LOGGER.error("/queryMemberInvitations==>业务处理异常e={}", eStr);
			return Result.ERROR(ResultCode.BUSINESS_EXCEPTION, null, "\"/queryMemberInvitations==>业务处理异常e={}\",eStr");
		}
	}


	/**
	 * @param sign
	 * @param meberId
	 * @return Result    返回类型
	 * @throws
	 * @Title: queryInviteCode
	 * @Description: 查詢用戶邀請碼
	 */
	@RequestMapping("/queryInviteCode")
	@ResponseBody
	public Result queryInviteCode(String sign, String meberId, HttpServletResponse response) {
		HttpUtil.accessControlAllowOrigin(response);
		//1、参数校验
		if (StringUtils.isBlank(sign)) {
			return Result.ERROR(ResultCode.PARAMTS_NOT_NULL, "sign=" + sign, "参数不能为空！");
		}
		if (StringUtils.isBlank(meberId)) {
			return Result.ERROR(ResultCode.PARAMTS_NOT_NULL, "meberId=" + meberId, "参数不能为空！");
		}
		//簽名驗證
		if (!MD5Tools.checkSign(sign)) {
			LOGGER.error("/queryMemberInvitations==>签名校验失败！sign=" + sign);
			return Result.valueOf(ResultCode.SIGN_FAIL, sign, "签名校验失败！");
		}
		//2、业务处理
		Criteria criteria = new Criteria();
		Map condition = new HashMap<>();
		condition.put("memberId", meberId);
		criteria.setCondition(condition);
		try {
			List<HajInviteCode> queryByList = inviteCodeService.queryByList(criteria);
			if (null != queryByList && queryByList.size() > 0) {
				HajInviteCode hajInviteCode = queryByList.get(0);
				return Result.SUCCESS(hajInviteCode, "成功！");
			}
		} catch (Exception e) {
			String eStr = ExceptionUtils.getFullStackTrace(e);
			LOGGER.error("/queryInviteCode==>业务处理异常e={}", eStr);
			return Result.ERROR(ResultCode.BUSINESS_EXCEPTION, null, "\"/queryInviteCode==>业务处理异常e={}\",eStr");
		}
		return null;
	}

	/**
	 * @param sign          签名
	 * @param code          验证码
	 * @param mobilePhone   手机号
	 * @param machineNumber 机器码
	 * @param phoneModel    用户手机机型
	 * @return Result    返回类型
	 * @throws
	 * @Title: registerUser
	 * @Description: 立即
	 */
	@RequestMapping("/registerUser")
	@ResponseBody
	public Result registerUser(String sign, String code, String mobilePhone, String machineNumber, String phoneModel, String ivCode, HttpServletResponse response) {
		//1、参数校验
		HttpUtil.accessControlAllowOrigin(response);
		if (StringUtils.isBlank(sign)) {
			return Result.ERROR(ResultCode.PARAMTS_NOT_NULL, "sign=" + sign, "sign参数不能为空！");
		}
		if (StringUtils.isBlank(code)) {
			return Result.ERROR(ResultCode.PARAMTS_NOT_NULL, "code=" + code, "code参数不能为空！");
		}
		if (StringUtils.isBlank(mobilePhone)) {
			return Result.ERROR(ResultCode.PARAMTS_NOT_NULL, "mobilePhone=" + mobilePhone, "mobilePhone参数不能为空！");
		}
		if (StringUtils.isBlank(ivCode)) {
			return Result.ERROR(ResultCode.PARAMTS_NOT_NULL, "ivCode=" + ivCode, "ivCode参数不能为空！");
		}
		//簽名驗證
		if (!MD5Tools.checkSign(sign)) {
			LOGGER.error("/registerUser==>签名校验失败！sign=" + sign);
			return Result.valueOf(ResultCode.SIGN_FAIL, sign, "签名校验失败！");
		}
		//2、业务处理
		try {
			List<HajFrontUser> list = null;
			try {
				Criteria criteria = new Criteria();
				Map<String, Object> condition = new HashMap<>();
				condition.put("mobilePhone", mobilePhone);
				criteria.setCondition(condition);
				list = frontUserService.queryByList(criteria);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (null != list && list.size() > 0) {//用户已存在，为老用户
				HajFrontUser user = list.get(0);
				Map<String, Object> ret = new HashMap<>();
				ret.put("isExsit", 1);//0 不存在，1 存在
				ret.put("user", user);
				return Result.valueOf(ResultCode.SUCCESS, ret, "老用户");
			} else {
				//新用户进行注册业务
				String backCode = (String) redisSpringProxy.read("register_code_" + mobilePhone);
				if (null == backCode || !backCode.equals(code)) {
					return Result.ERROR(ResultCode.CODE_NOT_EQ, code, "验证码错误！");
				}
				Map<String, Object> ret = new HashMap<>();
				//根据邀请码查询邀请人信息
				Criteria criteria = new Criteria();
				Map condition = new HashMap<>();
				condition.put("inviteCode", ivCode);
				criteria.setCondition(condition);
				List<HajInviteCode> queryByList = inviteCodeService.queryByList(criteria);
				if (null != queryByList && queryByList.size() > 0) {
					HajInviteCode inviteCode = queryByList.get(0);
					HajFrontUser user = frontUserService.save(mobilePhone, machineNumber, phoneModel, null);
					ret.put("isExsit", 0);//0 不存在，1 存在
					ret.put("user", user);
					//保存邀请人与被邀请人关系
					memberInvitationsService.insertInvitations(ivCode, user.getId().toString(), inviteCode.getMemberId());
				} else {
					return Result.valueOf(ResultCode.CODE_NOT_EQ, ret, "邀请人邀请码错误!");
				}
				return Result.valueOf(ResultCode.SUCCESS, ret, "新用户，已进行注册");
			}
		} catch (Exception e) {
			String eStr = ExceptionUtils.getFullStackTrace(e);
			LOGGER.error("/registerUser==>业务处理异常e={}", eStr);
			return Result.ERROR(ResultCode.BUSINESS_EXCEPTION, null, "\"/registerUser==>业务处理异常e={}");
		}
	}

	/**
	 * @param sign
	 * @param mobilePhone
	 * @return Result    返回类型
	 * @throws
	 * @Title: sendRegisterMsg
	 * @Description: 发送注册短信验证码
	 */
	@RequestMapping("/sendRegisterMsg")
	@ResponseBody
	public Result sendRegisterMsg(String sign, String mobilePhone, HttpServletResponse response) {
		//1、参数校验
		HttpUtil.accessControlAllowOrigin(response);
		if (StringUtils.isBlank(sign)) {
			return Result.ERROR(ResultCode.PARAMTS_NOT_NULL, "sign=" + sign, "sign参数不能为空！");
		}
		if (StringUtils.isBlank(mobilePhone)) {
			return Result.ERROR(ResultCode.PARAMTS_NOT_NULL, "mobilePhone=" + mobilePhone, "mobilePhone参数不能为空！");
		}
		//簽名驗證
		if (!MD5Tools.checkSign(sign)) {
			LOGGER.error("/sendRegisterMsg==>签名校验失败！sign=" + sign);
			return Result.valueOf(ResultCode.SIGN_FAIL, sign, "签名校验失败！");
		}
		//业务
		try {
			Criteria criteria = new Criteria();
			Map<String, Object> condition = new HashMap<>();
			condition.put("mobilePhone", mobilePhone);
			criteria.setCondition(condition);
			List<HajFrontUser> list = frontUserService.queryByList(criteria);
			Map<String, Object> result = new HashMap<>();
			if (null != list && list.size() > 0) {//用户已存在，为老用户
				result.put("isExsit", 1);//0 不存在，1 存在
				result.put("status", false);
				return Result.valueOf(ResultCode.SUCCESS, result, "老用户，不发送验证码");
			} else {//新用户进行注册业务
				result.put("isExsit", 0);//0 不存在，1 存在
				String retSend = sMSPushService.sendRegisterCode(mobilePhone);//// status有4种状态 error: 未知异常; true: 发送成功; false: 发送失败; 其他: 发送异常描述
				LOGGER.error("发送验证码==========="+retSend);
				result.put("status", true);
				return Result.valueOf(ResultCode.SUCCESS, result, "新用户，已发送验证码");
			}

		} catch (Exception e) {
			String eStr = ExceptionUtils.getFullStackTrace(e);
			LOGGER.error("/sendRegisterMsg==>业务处理异常e={}", eStr);
			return Result.ERROR(ResultCode.BUSINESS_EXCEPTION, null, "\"/sendRegisterMsg==>业务处理异常e={}\"");
		}
	}
}
