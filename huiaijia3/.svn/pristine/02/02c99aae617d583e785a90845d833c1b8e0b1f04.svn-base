package com.flf.controller.app;

import com.flf.entity.HajRechargePackage;
import com.flf.service.HajRechargePackageService;
import com.flf.util.JSONUtils;
import com.flf.util.MD5Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 充值套餐接口
 */

@Controller
@RequestMapping(value = "/hajRechargePackage")
public class HajRechargePackageAppController {

	@Autowired
	private HajRechargePackageService  hajRechargePackageService;
	/**
	 * 获取所有充值套餐接口
	 * @param sign
	 * @param response
	 */
	@RequestMapping(value = "/getRechargePackage")
	@ResponseBody
	public void getAreaProvince(@RequestParam String sign, HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<>();
		try {
			if (MD5Tools.checkSign(sign)) {
				List<HajRechargePackage> rechargePackageList = hajRechargePackageService.getRechargePackageList();
				if (null != rechargePackageList && rechargePackageList.size() > 0) {
					jsonMap.put("error", "0");
					jsonMap.put("rechargePackageList", rechargePackageList);
					jsonMap.put("msg", "成功");
				} else {
					jsonMap.put("error", "0");
					jsonMap.put("msg", "失败");
					jsonMap.put("rechargePackageList", "");
				}

				JSONUtils.printObject(jsonMap, response);
			} else {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "签名异常");
				JSONUtils.printObject(jsonMap, response);
			}

		} catch (Exception e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
			try {
				JSONUtils.printObject(jsonMap, response);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}

	}
}
