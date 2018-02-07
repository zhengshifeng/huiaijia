package com.flf.controller.app;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * APP共享功能
 * 
 * @author SevenWong<br>
 * @date 2016年6月21日下午5:08:50
 */
@Controller
public class PromotionController {

	/**
	 * APP分享链接
	 * 
	 * @author SevenWong<br>
	 * @date 2016年6月30日下午5:34:02
	 * @param community
	 * @return
	 */
	@RequestMapping(value = "/promotion")
	public ModelAndView share(@RequestParam String community) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("community", community);
		mv.setViewName("/mobile/share");
		return mv;
	}

	/**
	 * 扫描二维码下载APP
	 * 
	 * @author SevenWong<br>
	 * @date 2016年6月30日下午5:33:45
	 * @return
	 */
	@RequestMapping(value = "/scan/download")
	public ModelAndView download() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/mobile/scan_download");
		return mv;
	}

}
