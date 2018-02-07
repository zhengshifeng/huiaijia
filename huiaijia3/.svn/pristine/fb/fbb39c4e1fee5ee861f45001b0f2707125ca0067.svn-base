package com.flf.controller;

import com.flf.entity.SystemConfiguration;
import com.flf.service.HajAppConfigService;
import com.flf.service.RedisSpringProxy;
import com.flf.service.SystemConfigurationService;
import com.flf.util.FileUpload;
import com.flf.util.JSONUtils;
import com.flf.vo.HajAppConfigVo;
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
import java.io.File;
import java.io.PrintWriter;
import java.util.*;

/**
 * <br>
 * <b>功能：</b>HajAppConfigController<br>
 * <br>
 */
@Controller
@RequestMapping(value = "/hajAppConfig")
public class HajAppConfigController {

	private final static Logger log = Logger.getLogger(HajAppConfigController.class);

	@Autowired(required = false)
	private HajAppConfigService service;

	@Autowired(required = false)
	private SystemConfigurationService sysConfigService;

	@Autowired(required = false)
	private RedisSpringProxy redisSpringProxy;

	@RequestMapping(value = "/edit")
	public ModelAndView edit() {
		ModelAndView mv = new ModelAndView();
		HashMap<String, Object> vo = service.getAll();

		String placeholder = (String) redisSpringProxy.read("SystemConfiguration_commodity_upload_img_small");

		mv.addObject("vo", vo);
		mv.addObject("placeholder", placeholder);

		// 爱家头条
		String headlines = (String) redisSpringProxy.read("SystemConfiguration_ai_jia_tou_tiao");

		// 深圳站购物车顶部提示文字
		String shoppingCartTipSZ = (String) redisSpringProxy.read("SystemConfiguration_shopping_car_tip_100002");

		// 广州站购物车顶部提示文字
		String shoppingCartTipGZ = (String) redisSpringProxy.read("SystemConfiguration_shopping_car_tip_100010");

		// 深圳新用户优惠券ID
		String newUsersCouponsSz = (String) redisSpringProxy.read("SystemConfiguration_new_users_coupons_sz");

		// 广州新用户优惠券ID
		String newUsersCouponsGz = (String) redisSpringProxy.read("SystemConfiguration_new_users_coupons_gz");

		mv.addObject("headlines", headlines);
		mv.addObject("shoppingCartTipSZ", shoppingCartTipSZ);
		mv.addObject("shoppingCartTipGZ", shoppingCartTipGZ);
		mv.addObject("newUsersCouponsSz", newUsersCouponsSz);
		mv.addObject("newUsersCouponsGz", newUsersCouponsGz);

		mv.setViewName("appConfig");
		return mv;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public void save(HajAppConfigVo vo, PrintWriter out) throws Exception {
		String result = "success";
		if (vo != null) {
			if (vo.getId() == null) {
				service.add(vo);
			} else {
				service.updateBySelective(vo);

			}

			SystemConfiguration systemConfiguration = new SystemConfiguration();
			systemConfiguration.setName("ai_jia_tou_tiao");
			systemConfiguration.setValue(vo.getHeadlines());
			sysConfigService.updateByName(systemConfiguration);

			systemConfiguration = new SystemConfiguration();
			systemConfiguration.setName("shopping_car_tip_100002");
			systemConfiguration.setValue(vo.getShoppingCartTipSZ());
			sysConfigService.updateByName(systemConfiguration);

			systemConfiguration = new SystemConfiguration();
			systemConfiguration.setName("shopping_car_tip_100010");
			systemConfiguration.setValue(vo.getShoppingCartTipGZ());
			sysConfigService.updateByName(systemConfiguration);

			systemConfiguration = new SystemConfiguration();
			systemConfiguration.setName("new_users_coupons_sz");
			systemConfiguration.setValue(vo.getNewUsersCouponsSz());
			sysConfigService.updateByName(systemConfiguration);

			systemConfiguration = new SystemConfiguration();
			systemConfiguration.setName("new_users_coupons_gz");
			systemConfiguration.setValue(vo.getNewUsersCouponsGz());
			sysConfigService.updateByName(systemConfiguration);

			redisSpringProxy.deletePattern("appConfig_index_*");
			redisSpringProxy.deletePattern("promotionAreaList_*");
			redisSpringProxy.deletePattern("promotionAreaAllList_*");
			redisSpringProxy.save("SystemConfiguration_ai_jia_tou_tiao", vo.getHeadlines());
			redisSpringProxy.save("SystemConfiguration_shopping_car_tip_100002", vo.getShoppingCartTipSZ());
			redisSpringProxy.save("SystemConfiguration_shopping_car_tip_100010", vo.getShoppingCartTipGZ());
			redisSpringProxy.save("SystemConfiguration_new_users_coupons_sz", vo.getNewUsersCouponsSz());
			redisSpringProxy.save("SystemConfiguration_new_users_coupons_gz", vo.getNewUsersCouponsGz());

		} else {
			result = "error";
		}

		out.print(result);
		out.close();
	}

	/**
	 * 文件上传（上传图片）
	 */
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public void uploadFile(@RequestParam("fileName") MultipartFile file, HttpServletRequest request,
						   HttpServletResponse response) throws Exception {
		response.setContentType("application/json;charset=UTF-8");

		// 手动上传图片默认保存在同一目录下，然后更新文件后缀版本号
		String subPath = "images" + File.separator + "appConfig" + File.separator;
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
