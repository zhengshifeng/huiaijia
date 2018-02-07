package com.flf.controller.app;

import com.flf.entity.HajCommunityPersionComment;
import com.flf.service.HajCommunityPersionCommentService;
import com.flf.util.JSONUtils;
import com.flf.util.MD5Tools;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * <br>
 * <b>功能：</b>HajCommunityPersionCommentAppController<br>
 * <br>
 */

@Controller
@RequestMapping(value = "/hajCommunityPersionComment")
public class HajCommunityPersionCommentAppController {

	private final static Logger log = Logger.getLogger(HajCommunityPersionCommentAppController.class);

	@Autowired(required = false)
	private HajCommunityPersionCommentService hajCommunityPersionCommentService;

	/**
	 * 保存点评小区负责人评论信息
	 */
	@RequestMapping(value = "/saveComment", method = RequestMethod.POST)
	public void saveComment(@RequestParam String sign,
			@ModelAttribute HajCommunityPersionComment hajCommunityPersionComment, HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			if (MD5Tools.checkSign(sign)) {
				if (hajCommunityPersionComment != null) {
					int count = hajCommunityPersionCommentService.add(hajCommunityPersionComment);
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
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
