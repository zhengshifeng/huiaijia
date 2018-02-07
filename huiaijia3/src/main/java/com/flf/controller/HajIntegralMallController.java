package com.flf.controller;

import com.base.controller.BaseController;
import com.flf.entity.HajCouponInfo;
import com.flf.entity.HajIntegralMall;
import com.flf.entity.HajIntegralMallVo;
import com.flf.entity.User;
import com.flf.service.HajCouponInfoService;
import com.flf.service.HajIntegralMallService;
import com.flf.service.RedisSpringProxy;
import com.flf.util.Const;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.PrintWriter;
import java.util.*;

/**
 * <br>
 * <b>功能：</b>HajIntegralMallController<br>
 * <br>
 */
@Controller
@RequestMapping(value = "/integralMall")
public class HajIntegralMallController extends BaseController {

	private final static Logger log = Logger.getLogger(HajIntegralMallController.class);

	@Autowired(required = false)
	private HajIntegralMallService service;

	@Autowired
	private RedisSpringProxy redisSpringProxy;

	@Autowired(required = false)
	private HajCouponInfoService couponService;

	@RequestMapping(value = "/list")
	public ModelAndView list(HttpSession session, HajIntegralMall vo) {
		ModelAndView mv = new ModelAndView();

		userSession = (User) session.getAttribute(Const.SESSION_USER);
		vo.setAreaCode(userSession.getAreaCode());

		List<HajIntegralMallVo> integralMallList = service.listPage(vo);
		mv.addObject("vo", vo);
		mv.addObject("integralMallList", integralMallList);
		mv.setViewName("integralMallList");

		return mv;
	}

	@RequestMapping(value = "/add")
	public ModelAndView add() throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.addObject("defaultImage", redisSpringProxy.read("SystemConfiguration_commodity_upload_img_small"));
		mv.setViewName("integralMallEdit");
		return mv;
	}

	@RequestMapping(value = "/edit")
	public ModelAndView edit(@RequestParam Integer id) throws Exception {
		ModelAndView mv = new ModelAndView();
		HajIntegralMall vo = service.queryById(id);
		mv.addObject("defaultImage", redisSpringProxy.read("SystemConfiguration_commodity_upload_img_small"));
		mv.addObject("vo", vo);
		mv.setViewName("integralMallEdit");
		return mv;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public void save(HajIntegralMall po, PrintWriter out) throws Exception {
		String result = "success";
		if (po != null) {
			if (po.getId() == null) {
				po.setCreateTime(new Date());
				Integer commodityId = po.getCommodityId();
				if (null != commodityId) {
					HajCouponInfo hajCouponInfo = couponService.queryById(commodityId);
					if (null != hajCouponInfo) {
						service.add(po);
					} else {
						result = "优惠券ID或者商品ID不对";
					}
				} else {
					result = "优惠券ID或者商品ID不能为空";
				}
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
	 * 文件上传（上传商品图片）
	 *
	 * @param file
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public void uploadFile(@RequestParam("fileName") MultipartFile file, HttpServletRequest request,
						   HttpServletResponse response) throws Exception {
		response.setContentType("application/json;charset=UTF-8");

		// 手动上传图片默认保存在同一目录下，然后更新文件后缀版本号
		String subPath = "integral_mall_images" + File.separator;
		String filePath = FileUpload.uploadFile(file, subPath, request);
		Map<String, Object> result = new HashMap<>();
		if (!"".equals(filePath)) {
			filePath = filePath + "?v=" + new Random().nextInt(1000);
			result.put("filePath", filePath);
			result.put("result", "success");
		} else {
			result.put("result", "error");
		}
		JSONUtils.printObject(result, response);
	}

}
