package com.flf.controller.app;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.flf.entity.HajUserReport;
import com.flf.service.HajUserReportService;
import com.flf.util.DateUtil;
import com.flf.util.JSONUtils;
import com.flf.util.MD5Tools;
import com.flf.util.Tools;

/**
 * 
 * <br>
 * <b>功能：</b>HajUserReportAppController<br>
 * <br>
 */
@Controller
@RequestMapping(value = "/userReportApp")
public class HajUserReportAppController {

	private final static Logger log = Logger.getLogger(HajUserReportAppController.class);

	@Autowired(required = false)
	private HajUserReportService hajUserReportService;

	/**
	 * 小区上报
	 * 
	 * @param userId
	 */
	@RequestMapping(value = "/userReport")
	public void userReport(@RequestParam String sign, HajUserReport hajUserReport, HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			if (MD5Tools.checkSign(sign)) {
				if (hajUserReport != null) {
					String area = hajUserReport.getArea();
					if (Tools.isNotEmpty(area)) {
						area = area.replaceAll("-", "");
						hajUserReport.setArea(area);
					}

					HajUserReport checkTheUniqueness = hajUserReportService.checkTheUniqueness(hajUserReport);

					// 同一个用户上报同样的小区地址多次则不写入数据库
					if (checkTheUniqueness == null) {
						log.info("用户上报小区...");
						hajUserReport.setReportTime(DateUtil.datetime2Str(new Date()));
						hajUserReport.setPushStatus("0");
						hajUserReportService.add(hajUserReport);
					}
					jsonMap.put("error", "0");
					jsonMap.put("msg", "成功");
				}
			} else {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "签名异常");
			}
		} catch (Exception e) {
			jsonMap.put("error", "2");
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
