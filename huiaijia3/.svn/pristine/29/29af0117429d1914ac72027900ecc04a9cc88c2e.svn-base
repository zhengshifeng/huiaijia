package com.flf.controller.inivite;

import com.flf.entity.invite.HajMemberInvitations;
import com.flf.service.invite.HajMemberInvitationsService;
import com.flf.util.JSONUtils;
import com.utils.Pager;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : RockDing
 * @version : 1.0
 * @ClassName: IviteController
 * @Description: 会员邀请管理，供管理后台使用
 * @Copyright : 深圳市南山软件产业基地1A栋705
 * @Company : 深圳市橙社网络科技有限公司
 * @date 2017年12月21日 下午1:44:12
 */
@RequestMapping("invitations")
@Controller
public class InviteController {
	private final static Logger LOGGER = Logger.getLogger(InviteController.class);
	@Autowired(required = false)
	private HajMemberInvitationsService memberInvitationsService;

	/**
	 * @param inviter
	 * @param inviteCode
	 * @param status
	 * @param ascOrDesc
	 * @param pageSize
	 * @param currentPage
	 * @return ModelAndView    返回类型
	 * @throws
	 * @Title: list
	 * @Description: 查询邀请管理列表
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(String inviter, String inviteCode, Integer status, String ascOrDesc, Integer pageSize, Integer currentPage) {
		ModelAndView mv = new ModelAndView();
		Pager<HajMemberInvitations> pager = new Pager<>();
		pager.setCurrentPage(null == currentPage ? 1 : currentPage);
		pager.setPageSize(null == pageSize ? 10 : pageSize);
		Pager<HajMemberInvitations> page = memberInvitationsService.selectWithPage(inviter, inviteCode, status, ascOrDesc, pager);
		mv.addObject("inviter", inviter);
		mv.addObject("inviteCode", inviteCode);
		mv.addObject("status", status);
		mv.addObject("ascOrDesc", ascOrDesc);
		mv.addObject("page", page);
		mv.setViewName("invitations/invite");
		return mv;
	}

	/**
	 * @param ids
	 * @param opt
	 * @return Map    返回类型
	 * @throws
	 * @Title: optStatus
	 * @Description: 邀请管理操作状态更改
	 */
	@RequestMapping(value = "/optStatus")
	@ResponseBody
	public Map optStatus(String ids, Integer opt) {
		Map result = new HashMap<>();
		if (StringUtils.isBlank(ids)) {
			result.put("code", -1);//0 成功 -1失败
			result.put("msg", "参数不能为空！ids=" + ids + ",opt=" + opt);
			return result;
		}
		if (null == opt) {
			result.put("code", -1);//0 成功 -1失败
			result.put("msg", "参数不能为空！ids=" + ids + ",opt=" + opt);
			return result;
		}

		String[] idz = ids.split(",");
		try {
			memberInvitationsService.updateByBatch(idz, opt);
		} catch (Exception e) {
			String exStr = ExceptionUtils.getFullStackTrace(e);
			result.put("code", -1);//0 成功 -1失败
			result.put("msg", "更新失败！");
			LOGGER.error("邀请人调整处理操作失败!===>接口:/optStatus?ids=" + ids + "&opt=" + opt);
			return result;
		}
		result.put("code", 0);//0 成功 -1失败 3参数不能为空
		result.put("msg", "更新成功！");
		return result;
	}

	@RequestMapping(value = "/totalInvitePerson")
	public void totalInvitePerson(HajMemberInvitations hajMemberInvitations, HttpServletResponse response) throws Exception {
		Map<String, Object> jsonMap = new HashMap<>();
		Map<String, Object> totalInvitePerson = memberInvitationsService.queryTotalInvitePerson(hajMemberInvitations);
		jsonMap.put("inviter", totalInvitePerson.get("inviter")!=null?totalInvitePerson.get("inviter"):"0");
		jsonMap.put("invitee", totalInvitePerson.get("invitee"));
		jsonMap.put("sucInvitee", totalInvitePerson.get("sucInvitee"));
		JSONUtils.printObject(jsonMap, response);
	}


}
