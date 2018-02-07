package com.flf.controller.app;

import com.flf.entity.HajFeedbackVo;
import com.flf.service.HajFeedbackService;
import com.flf.util.JSONUtils;
import com.flf.util.MD5Tools;
import com.flf.util.Tools;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * <br>
 * <b>功能：</b>HajFeedbackAppController<br>
 * <br>
 */
@Controller
@RequestMapping(value = "/hajFeedback")
public class HajFeedbackAppController {

	private final static Logger log = Logger.getLogger(HajFeedbackAppController.class);
	@Autowired(required = false)
	private HajFeedbackService hajFeedbackService;

	/**
	 * 保存意见反馈信息
	 * 
	 * @author SevenWong<br>
	 * @date 2016年10月8日上午11:24:29
	 * @param sign
	 * @param hajFeedback
	 * @param response
	 */
	@RequestMapping(value = "/saveFeedback")
	public void saveFeedback(@RequestParam String sign, @ModelAttribute HajFeedbackVo hajFeedback,
			HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			if (MD5Tools.checkSign(sign)) {
				if (hajFeedback != null) {
					hajFeedback.setTelPhone(hajFeedback.getTelephone());
					hajFeedback.setResolved(0);
					hajFeedback.setContent(Tools.filterEmoji(hajFeedback.getContent()));
					hajFeedback.setCreateTime(Tools.date2Str(new Date()));
					int count = hajFeedbackService.add(hajFeedback);
					if (count > 0) {
						jsonMap.put("error", "0");
						jsonMap.put("msg", "成功");
					} else {
						jsonMap.put("error", "1");
						jsonMap.put("msg", "失败");
					}
				} else {
					jsonMap.put("error", "1");
					jsonMap.put("msg", "参数异常");
				}
			} else {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "签名异常");
			}
		} catch (Exception e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
			log.info(e.getMessage(), e);
		} finally {
			try {
				JSONUtils.printObject(jsonMap, response);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

}
