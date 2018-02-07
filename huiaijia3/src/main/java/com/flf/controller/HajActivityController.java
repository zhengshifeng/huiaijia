package com.flf.controller;

import com.base.controller.BaseController;
import com.flf.entity.HajActivity;
import com.flf.entity.User;
import com.flf.service.HajActivityCommodityService;
import com.flf.service.HajActivityService;
import com.flf.service.HajAreasService;
import com.flf.service.HajCommodityService;
import com.flf.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <br>
 * <b>功能：</b>HajActivityController<br>
 * <br>
 */
@Controller
@RequestMapping(value = "/hajbackActivity")
public class HajActivityController extends BaseController {

	@Autowired(required = false)
	private HajActivityService hajActivityService;

	@Autowired(required = false)
	private HajCommodityService hajCommodityService;

	@Autowired(required = false)
	private HajAreasService areasService;

	@Autowired(required = false)
	private HajActivityCommodityService activityCommodityService;


	/**
	 * 后台查询所有活动记录
	 */
	@RequestMapping(value = "/getAll")

	public String list(HajActivity activity, Model model, HttpSession session) {
		try {
			userSession = (User) session.getAttribute(Const.SESSION_USER);
			activity.setAreaCode(userSession.getAreaCode());

			List<Map<String, Object>> activityList = hajActivityService.listAllOrder(activity);
			model.addAttribute("activityList", activityList);
			model.addAttribute("activity", activity);
			model.addAttribute("cityList", areasService.listCity());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "activityList";
	}

	/**
	 * 查看详情记录
	 */
	@RequestMapping(value = "/getDetail")
	public String getDetail(@RequestParam int activityId, Model model) {
		try {
			// 查询活动对象
			HajActivity activity = hajActivityService.queryById(activityId);
			model.addAttribute("activity", activity);
			// 查询活动的商品列表
			List<Map<String, Object>> commodityList = hajCommodityService.getCommodityByActionId(activityId);
			model.addAttribute("commodityList", commodityList);
			model.addAttribute("cityList", areasService.listCity());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "activityDetail";
	}

	/**
	 * 跳转到添加页面
	 */
	@RequestMapping(value = "/toAdd")
	public ModelAndView toAdd(HajActivity activity, HttpSession session) throws Exception {
		ModelAndView mv = new ModelAndView();
		userSession = (User) session.getAttribute(Const.SESSION_USER);

		List<Map<String, Object>> commodityList = null;
		HajActivity activity2 = new HajActivity();
		if (activity.getSbTypeName() != null) {
			if (activity.getId() != null) {
				activity2 = hajActivityService.queryById(activity.getId());
			}
			activity2.setSbTypeName(activity.getSbTypeName());
			activity2.setsTypeName(activity.getsTypeName());

			// 超管修改商品按照前台选择的areaCode保存对应的值
			if (Tools.isEmpty(userSession.getAreaCode())) {
				activity2.setAreaCode(activity.getAreaCode());
			} else {
				activity2.setAreaCode(userSession.getAreaCode());
			}
			commodityList = hajActivityService.getHajActivityCommotidyList(activity2);
		}

		mv.addObject("commodityList", commodityList);
		mv.addObject("activity", activity2);
		mv.addObject("cityList", areasService.listCity());
		mv.addObject("isAdmin", Tools.isEmpty(userSession.getAreaCode()));
		mv.addObject("userSession", userSession);
		mv.setViewName("addActivity");

		return mv;
	}

	/**
	 * 跳转到编辑页面
	 */
	@RequestMapping(value = "/toEdit")
	public ModelAndView toEdit(String activityId, HttpSession session) throws Exception {
		ModelAndView mv = new ModelAndView();
		userSession = (User) session.getAttribute(Const.SESSION_USER);

		HajActivity activity = hajActivityService.queryById(activityId);
		List<Map<String, Object>> commodityList = null;
		if (activity.getSbTypeName() != null) {
			commodityList = hajActivityService.getHajActivityCommotidyList(activity);
		}
		mv.addObject("commodityList", commodityList);
		mv.addObject("activity", activity);
		mv.addObject("cityList", areasService.listCity());
		mv.addObject("isAdmin", Tools.isEmpty(userSession.getAreaCode()));
		mv.addObject("userSession", userSession);
		mv.setViewName("addActivity");
		return mv;
	}

	/**
	 * 添加活动
	 */
	@RequestMapping(value = "/saveActivity")
	public void saveActivity(HajActivity activity, HttpSession session, HttpServletResponse response) {
		Map<String, Object> returnMap = new HashMap<>();
		returnMap.put("error", "0");
		try {
			// 默认按照当前登录用户
			userSession = (User) session.getAttribute(Const.SESSION_USER);

			if (Tools.isEmpty(userSession.getAreaCode())) {
				activity.setAreaCode(activity.getAreaCode());
			} else {
				activity.setAreaCode(userSession.getAreaCode());
			}

			if (activity.getId() != null && activity.getId() > 0) {
				hajActivityService.updateActivity(activity);
			} else {
				hajActivityService.insertActivity(activity);
			}
			HajCommodityUtil.resetCommodityRedisAndESIndex(hajCommodityService);
		} catch (Exception e) {
			e.printStackTrace();
			returnMap.put("error", "1");
		} finally {
			try {
				JSONUtils.printObject(returnMap, response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 开启或者关闭活动
	 */
	@RequestMapping(value = "/updateActivity")
	public void updateActivityStatus(@RequestParam int activityId, @RequestParam int status,
									 HttpServletResponse httpServletResponse) {
		try {
			boolean flag = true;
			//手动结束活动时,删除该活动下的商品信息
			if (status == 0) {
				activityCommodityService.deleteCommodityToActivity(activityId);
			}
			if (activityId > 0) {
				flag = hajActivityService.updateActivityStatus(activityId, status);
			}
			if (flag) {
				HajCommodityUtil.resetCommodityRedisAndESIndex(hajCommodityService);

				JSONUtils.printStr("1", httpServletResponse);
			} else {
				JSONUtils.printStr("0", httpServletResponse);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 删除活动id
	 */
	@RequestMapping(value = "/deleteActivity")
	public void deleteActivity(@RequestParam int activityId, HttpServletResponse httpServletResponse) {
		try {
			boolean flag = true;
			if (activityId > 0) {
				// 暂时屏蔽此功能
				// flag = hajActivityService.deleteActivity(activityId);
			}
			if (flag) {
				JSONUtils.printStr("1", httpServletResponse);
			} else {
				JSONUtils.printStr("0", httpServletResponse);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public void uploadFile(@RequestParam("fileName") MultipartFile file, HttpServletRequest request,
						   HttpServletResponse response) throws Exception {
		response.setContentType("application/json;charset=UTF-8");

		String subPath = "activity" + File.separator;

		String filePath = FileUpload.uploadFile(file, subPath, request);
		Map<String, Object> result = new HashMap<>();
		if (!"".equals(filePath)) {
			result.put("filePath", filePath);
			result.put("result", "success");
		} else {
			result.put("result", "error");
		}
		JSONUtils.printObject(result, response);
	}
}
