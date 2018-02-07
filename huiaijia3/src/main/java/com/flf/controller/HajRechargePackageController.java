package com.flf.controller;

import com.base.controller.BaseController;
import com.flf.entity.HajRechargePackage;
import com.flf.entity.User;
import com.flf.service.HajRechargePackageService;
import com.flf.util.Const;
import com.flf.util.JSONUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 充值套餐管理
 */


@Controller
@RequestMapping(value = "/rechargePackage")
public class HajRechargePackageController extends BaseController {
	private final static Logger log = Logger.getLogger(HajRechargePackageController.class);
	@Autowired
	private HajRechargePackageService hajRechargePackageService;
	/***
	 * 充值套餐管理列表
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(HajRechargePackage hajRechargePackage) throws Exception {
		ModelAndView mv = new ModelAndView();
		List<HajRechargePackage> rechargePackageList = hajRechargePackageService.listPageRechargePackage(hajRechargePackage);
		mv.addObject("rechargePackageList", rechargePackageList);
		mv.addObject("hajRechargePackage", hajRechargePackage);
		mv.setViewName("rechargePackageList");
		return mv;
	}

	/***
	 * 添加充值套餐修改新增页面
	 * @return
	 * @throws Exception
	 */

	@RequestMapping(value = "/add")
	public ModelAndView add() throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("rechargePackageEdit");
		return mv;
	}

	/**
	 * 保存添加或修改套餐<br>
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public void save(HajRechargePackage hajRechargePackage, HttpSession session, HttpServletResponse response) {
		userSession = (User) session.getAttribute(Const.SESSION_USER);
		Map<String, Object> returnMap = new HashMap<>();
		returnMap.put("error", "0");
		returnMap.put("msg", "success");
		try {
			if (hajRechargePackage != null) {
				hajRechargePackage.setAccountAmount(hajRechargePackage.getPurchaseAmount().add(hajRechargePackage.getDonationAmount()));
				hajRechargePackage.setCreateUser(userSession.getLoginname());
				hajRechargePackageService.save(hajRechargePackage);
			} else {
				returnMap.put("error", "3");
				returnMap.put("msg", "数据为空");
			}
		} catch (Exception e) {
			returnMap.put("error", "1");
			returnMap.put("msg", "未知异常");
			log.info(e.getMessage(), e);
		} finally {
			try {
				JSONUtils.printObject(returnMap, response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/***
	 * 充值套餐详情
	 * @param id
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/detail")
	public ModelAndView orderDetail(@RequestParam int id, HttpSession session) throws Exception {
		ModelAndView mv = new ModelAndView();
		HajRechargePackage hajRechargePackage = hajRechargePackageService.getHajRechargePackageById(id);
		mv.addObject("hajRechargePackage", hajRechargePackage);
		mv.setViewName("rechargePackageEdit");
		return mv;
	}


}
