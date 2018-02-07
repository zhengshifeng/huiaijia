package com.flf.controller;

import com.flf.entity.HajAreas;
import com.flf.entity.Role;
import com.flf.entity.User;
import com.flf.service.HajAreasService;
import com.flf.service.RoleService;
import com.flf.service.UserService;
import com.flf.util.Const;
import com.flf.util.RightsHelper;
import com.flf.util.Tools;
import com.flf.view.UserExcelView;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping(value = "/user")
public class UserController {

	private static final Logger log = Logger.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private HajAreasService hajAreasService;


	/**
	 * 显示用户列表
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping
	public ModelAndView list(User user) {
		ModelAndView mv = new ModelAndView();

		List<User> userList = userService.listPageUser(user);
		List<Role> roleList = roleService.listAllRoles();

		mv.addObject("userList", userList);
		mv.addObject("roleList", roleList);
		mv.addObject("user", user);

		mv.setViewName("users");
		return mv;
	}

	/**
	 * 请求新增用户页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add")
	public String toAdd(Model model, HttpSession session) {
		List<Role> roleList = roleService.listAllRoles();
		model.addAttribute("roleList", roleList);

		List<HajAreas> cityList = hajAreasService.listCity();
		model.addAttribute("cityList", cityList);

		User userSession = (User) session.getAttribute(Const.SESSION_USER);
		model.addAttribute("isAdmin", Objects.equals(userSession.getRoleId(), Const.ADMIN_ROLE_ID));

		return "user_info";
	}

	/**
	 * 保存用户信息
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public void saveUser(User user, PrintWriter out) {
		if (user.getUserId() == null || user.getUserId() == 0) {
			if (!userService.insertUser(user)) {
				out.print("failed");
			} else {
				out.print("success");
			}
		} else {
			userService.updateUserBaseInfo(user);
			out.print("success");
		}
	}

	/**
	 * 请求编辑用户页面
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/edit")
	public ModelAndView toEdit(@RequestParam int userId, HttpSession session) {
		ModelAndView mv = new ModelAndView();

		User user = userService.getUserById(userId);
		List<Role> roleList = roleService.listAllRoles();

		List<HajAreas> cityList = hajAreasService.listCity();

		mv.addObject("user", user);
		mv.addObject("roleList", roleList);
		mv.addObject("cityList", cityList);

		User userSession = (User) session.getAttribute(Const.SESSION_USER);
		mv.addObject("isAdmin", Objects.equals(userSession.getRoleId(), Const.ADMIN_ROLE_ID));
		mv.setViewName("user_info");
		return mv;
	}

	/**
	 * 删除某个用户
	 * 
	 * @param userId
	 * @param out
	 */
	@RequestMapping(value = "/delete")
	public void deleteUser(@RequestParam int userId, PrintWriter out) {
		userService.deleteUser(userId);
		out.write("success");
		out.close();
	}

	/**
	 * 请求用户授权页面
	 * 
	 * @param userId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/auth")
	@Deprecated
	public String auth(@RequestParam int userId, Model model) {
		return "authorization";
	}

	/**
	 * 保存用户权限
	 * 
	 * @param userId
	 * @param menuIds
	 * @param out
	 */
	@RequestMapping(value = "/auth/save")
	public void saveAuth(@RequestParam int userId, @RequestParam String menuIds, PrintWriter out) {
		BigInteger rights = RightsHelper.sumRights(Tools.str2StrArray(menuIds));
		User user = userService.getUserById(userId);
		user.setRights(rights.toString());
		userService.updateUserRights(user);
		out.write("success");
		out.close();
	}

	/**
	 * 导出用户信息到excel
	 * 
	 * @return
	 */
	@RequestMapping(value = "/excel")
	public ModelAndView export2Excel() {
		Map<String, Object> dataMap = new HashMap<>();
		List<String> titles = new ArrayList<>();
		titles.add("用户名");
		titles.add("名称");
		titles.add("角色");
		titles.add("最近登录");
		dataMap.put("titles", titles);
		List<User> userList = userService.listAllUser();
		dataMap.put("userList", userList);
		UserExcelView erv = new UserExcelView();
		return new ModelAndView(erv, dataMap);
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format, true));
	}
}
