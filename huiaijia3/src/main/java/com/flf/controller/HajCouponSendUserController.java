package com.flf.controller;

import com.flf.entity.HajCouponInfo;
import com.flf.entity.HajCouponSendUser;
import com.flf.service.HajCouponInfoService;
import com.flf.service.HajCouponSendUserService;
import com.flf.util.FileUpload;
import com.flf.util.JSONUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <br>
 * <b>功能：</b>HajCouponSendUserController<br>
 * <br>
 */
@Controller
@RequestMapping(value = "/hajCouponSendUser")
public class HajCouponSendUserController {

	private final static Logger log = Logger.getLogger(HajCouponSendUserController.class);

	@Autowired(required = false)
	private HajCouponInfoService couponInfoService;

	@Autowired(required = false)
	private HajCouponSendUserService service;

	@RequestMapping(value = "/list")
	public ModelAndView list(HajCouponSendUser vo) {
		ModelAndView mv = new ModelAndView();

		List<HajCouponSendUser> hajCouponSendUserList = service.listPage(vo);
		mv.addObject("vo", vo);
		mv.addObject("hajCouponSendUserList", hajCouponSendUserList);
		mv.setViewName("hajCouponSendUserList");

		return mv;
	}

	@RequestMapping(value = "/add")
	public ModelAndView add() throws Exception {
		ModelAndView mv = new ModelAndView();

		mv.setViewName("hajCouponSendUserEdit");
		return mv;
	}

	@RequestMapping(value = "/edit")
	public ModelAndView edit(@RequestParam Integer id) throws Exception {
		ModelAndView mv = new ModelAndView();
		HajCouponSendUser vo = service.queryById(id);
		mv.addObject("vo", vo);
		mv.setViewName("hajCouponSendUserEdit");
		return mv;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public void save(HajCouponSendUser po, PrintWriter out) throws Exception {
		String result = "success";
		if (po != null) {
			if (po.getId() == null) {
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

	@RequestMapping(value = "/deleteByCoupon")
	public void deleteByCoupon(@RequestParam("couponId") Integer couponId, PrintWriter out) throws Exception {
		if (couponId != null && couponId > 0) {
			service.deleteByCoupon(couponId);
			out.print("success");
		} else {
			out.print("error");
		}
		out.close();
	}

	@RequestMapping(value = "/batchImport", method = RequestMethod.POST)
	public void batchImport(@RequestParam("fileName") MultipartFile file, @RequestParam("couponId") Integer couponId,
							HttpServletResponse response) throws Exception {
		Map<String,Object> result = new HashMap<String,Object>();
		if (couponId != null && couponId > 0) {
			HajCouponInfo couponInfo = couponInfoService.queryById(couponId);
			if (couponInfo != null) {
				response.setContentType("application/json;charset=UTF-8");
				String subPath = "xlsx" + File.separator + "coupon_user" + File.separator;
				String filePath = FileUpload.uploadExcel(file, subPath);
				result = service.batchImport_new(filePath, couponInfo);
			} else {
				result.put("result",false);
				result.put("errorUsers","优惠券不存在");
			}
		} else {
			result.put("result",false);
			result.put("errorUsers","优惠券ID异常");
		}
		JSONUtils.printObject(result, response);
	}
}
