package com.flf.controller;

import com.base.util.HttpUtil;
import com.flf.entity.*;
import com.flf.service.HajCouponInfoService;
import com.flf.service.HajCouponSendUserService;
import com.flf.service.HajCouponUserService;
import com.flf.util.*;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * <br>
 * <b>功能：</b>HajCouponInfoController<br>
 * <br>
 */
@Controller
@RequestMapping(value = "/hajCouponInfo")
public class HajCouponInfoController {

	private final static Logger log = Logger.getLogger(HajCouponInfoController.class);

	@Autowired(required = false)
	private HajCouponInfoService service;

	@Autowired(required = false)
	private HajCouponUserService couponUserService;

	@Autowired(required = false)
	private HajCouponSendUserService couponSendUserService;

	@RequestMapping(value = "/list")
	public ModelAndView list(HajCouponInfo vo, HttpSession session) throws Exception {
		ModelAndView mv = new ModelAndView();

		if (vo.getCategory() == null || vo.getCategory() < 1) {
			throw new Exception("优惠券类型异常");
		}

		List<HajCouponInfo> hajCouponInfoList = service.listPage(vo);

		User user = (User) session.getAttribute(Const.SESSION_USER);

		mv.addObject("vo", vo);
		mv.addObject("user", user);
		mv.addObject("hajCouponInfoList", hajCouponInfoList);
		mv.setViewName("hajCouponInfoList");

		return mv;
	}

	@RequestMapping(value = "/add")
	public ModelAndView add(@RequestParam Integer category) throws Exception {
		ModelAndView mv = new ModelAndView();

		mv.addObject("category", category);
		mv.setViewName("hajCouponInfoEdit");
		return mv;
	}

	@RequestMapping(value = "/edit")
	public ModelAndView edit(@RequestParam Integer id) throws Exception {
		ModelAndView mv = new ModelAndView();
		HajCouponInfo vo = service.queryById(id);
		mv.addObject("vo", vo);
		mv.addObject("category", vo.getCategory());
		mv.setViewName("hajCouponInfoEdit");
		return mv;
	}

	@RequestMapping(value = "/selectRate")
	public void selectRate(Integer couponId, HttpServletResponse response) throws Exception {

		Map<String, Object> jsonMap = new HashMap<>();
		try {
			Double rate = service.queryRateById(couponId);
			jsonMap.put("result", rate);
		} catch (Exception e) {
			jsonMap.put("result", "未知异常");
			log.info(e.getMessage(), e);
		}

		JSONUtils.printObject(jsonMap, response);
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public void save(HajCouponInfo po, PrintWriter out, HttpSession session) throws Exception {
		String result = "success";
		if (po != null) {
			if (po.getId() == null) {
				User user = (User) session.getAttribute(Const.SESSION_USER);
				po.setStatus(1);
				po.setSendType(0);
				po.setCreator(user.getUsername());
				if (po.getDateNumbers() == null) {
					po.setDateNumbers(0);
				}
				if (po.getType() == null) {
					po.setType(1);
				}
				service.add(po);
			} else {
				service.updateBySelective(po);
			}
		} else {
			result = "error";
		}

		out.print(result);
		out.close();
	}

	@RequestMapping(value = "/delete")
	public void delete(Integer[] id, PrintWriter out) throws Exception {
		service.delete((Object[]) id);
		out.print("success");
		out.close();
	}

	/**
	 * 发放优惠券页面
	 * 
	 * @param couponId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/issueCoupon")
	public ModelAndView issueCoupon(@RequestParam Integer couponId, @RequestParam Integer category) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.addObject("couponId", couponId);
		mv.addObject("category", category);
		mv.setViewName("issueCoupon");
		return mv;
	}

	/**
	 * 发放详情页
	 * 
	 * @param couponId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/issueDetail")
	public ModelAndView issueDetail(@RequestParam Integer couponId) throws Exception {
		ModelAndView mv = new ModelAndView();
		HajCouponInfo couponInfo = service.queryById(couponId);
		List<Integer> userList = couponSendUserService.listSendUserByCoupon(couponId);
		mv.addObject("vo", couponInfo);
		mv.addObject("userListSize", userList.size());
		mv.setViewName("issueDetail");
		return mv;
	}

	/**
	 * 根据发放优惠券页面选择的条件进行发放
	 * 
	 * @param vo
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/issue")
	public void issue(HajCouponInfoVo vo, HttpServletResponse response) throws Exception {
		Map<String, Object> jsonMap = new HashMap<>();
		String result;

		// 验证通行码
		String passportConfig = Config.getConfigProperties("issue.coupon.pwd", "");
		if (passportConfig.equals(MD5.MD5Encode(vo.getPassport()))) {
			HajCouponInfo couponInfo = service.queryById(vo.getId());
			if (couponInfo != null && couponInfo.getSendType() == 0 && couponInfo.getStatus() != 3) {
				result = service.updateCouponIssue(vo, couponInfo);
			} else {
				result = "此优惠券状态不支持发放";
			}
		} else {
			result = "通行码验证失败";
		}
		log.info("发放结果: " + result);
		jsonMap.put("result", result);
		JSONUtils.printObject(jsonMap, response);
	}

	/**
	 * 修改优惠券状态
	 * @param vo
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateStatus")
	public void updateStatus(HajCouponInfo vo, HttpServletResponse response) throws Exception {
		Map<String, Object> jsonMap = new HashMap<>();
		jsonMap.put("result", "success");
		try {
			// 跟产品讨论后，开启动作放到发放时处理，因此此处已删除开启的逻辑，只剩下关闭的功能
			if (vo.getStatus() == 3) { // 关闭
				HajCouponInfo coupon = new HajCouponInfo();
				coupon.setId(vo.getId());
				coupon.setStatus(3);
				service.updateBySelective(coupon);

				// 将所有用户领取的优惠券设为失效
				couponUserService.updateInvalidateByCouponId(vo.getId());
			} else {
				jsonMap.put("result", "错误的优惠券状态");
			}
		} catch (Exception e) {
			jsonMap.put("result", "未知异常");
			log.info(e.getMessage(), e);
		}

		JSONUtils.printObject(jsonMap, response);
	}

	/**
	 * 优惠券使用详情
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/useInfo")
	public ModelAndView useInfo(HajCouponUseInfoVo vo) throws Exception {
		ModelAndView mv = new ModelAndView();
		HajCouponInfo couponInfo = service.queryById(vo.getId());
		List<HajCouponUseInfoVo> couponInfoList = service.listPageCouponUseInfo(vo);
		mv.addObject("vo", vo);
		mv.addObject("couponInfo", couponInfo);
		mv.addObject("couponInfoList", couponInfoList);
		mv.setViewName("hajCouponInfo");
		return mv;
	}

	/**
	 * 导出使用详情到excel
	 * @param vo
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/export2excel")
	public void export2excel(HajCouponInfo vo, HttpServletRequest request,
							 HttpServletResponse response) throws IOException {
		String filename = HttpUtil.encodeFilename(request, vo.getName()+"-领取和使用详情");
		XSSFWorkbook wb = service.export2excel(vo);
		ExcelUtil.output(response, filename, wb);
	}

	/**
	 * 优惠券分类页
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/categories")
	public ModelAndView categories() throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("hajCouponCategory");
		return mv;
	}
}
