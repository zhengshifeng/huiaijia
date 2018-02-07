package com.flf.controller.app;

import com.flf.service.HajOrderPostFeeService;
import com.flf.util.JSONUtils;
import com.flf.util.MD5Tools;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * <br>
 * <b>功能：订单运费记录表 <br>
 */
@Controller
@RequestMapping(value = "/hajOrderPostFee")
public class HajOrderPostFeeAppController {

	private final static Logger log = Logger.getLogger(HajOrderPostFeeAppController.class);

	@Autowired(required = false)
	private HajOrderPostFeeService hajOrderPostFeeService;

	/**
	 * 获取用户昨日下单金额，运费
	 * 
	 * @param sign
	 * @param response
	 */
	@RequestMapping(value = "/getHajOrderPostFee")
	public void getHajOrderPostFee(@RequestParam String sign, Integer userId, HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			// 签名认证
			if (MD5Tools.checkSign(sign)) {
				Map<String, Object> resultMap = hajOrderPostFeeService.getHajOrderPostFeeByUserId(userId);
				if (null != resultMap && resultMap.size() > 0) {
					jsonMap.put("error", "0");
					jsonMap.put("msg", "success");
					jsonMap.put("result", resultMap);
				} else {
					jsonMap.put("error", "3");
					jsonMap.put("msg", "未查到数据");
					jsonMap.put("result", "");
				}
			} else {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "签名异常");
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
		} finally {
			try {
				JSONUtils.printObject(jsonMap, response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 支付运费
	 */
	@RequestMapping(value = "/updateOrderPostFeeStatus")
	public void updateOrderPostFeeStatus(@RequestParam Integer orderPostFeeId, @RequestParam String sign,
			HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<>();
		try {
			// 签名认证
			if (MD5Tools.checkSign(sign)) {
				int result = hajOrderPostFeeService.updateOrderPostFeeStatus(orderPostFeeId);
				if (result > 0) {
					jsonMap.put("error", "0");
					jsonMap.put("msg", "成功");
				} else {
					jsonMap.put("error", "3");
					jsonMap.put("msg", "失败");
				}
			} else {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "签名异常");
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
		} finally {
			try {
				JSONUtils.printObject(jsonMap, response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
