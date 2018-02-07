package com.flf.controller.app;

import com.base.criteria.Criteria;
import com.flf.entity.HajIngredientsReport;
import com.flf.entity.HajIngredientsReportConfVo;
import com.flf.entity.HajIngredientsReportVo;
import com.flf.service.HajIngredientsReportConfService;
import com.flf.service.HajIngredientsReportService;
import com.flf.service.RedisSpringProxy;
import com.flf.util.DateUtil;
import com.flf.util.JSONUtils;
import com.flf.util.MD5Tools;
import com.flf.util.Tools;
import net.sf.json.JsonConfig;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * <br>
 * <b>功能：</b>HajIngredientsReportAppController<br>
 * <br>
 */ 
@Controller
@RequestMapping(value = "/hajIngredientsReport")
public class HajIngredientsReportAppController {
	
	private final static Logger log = Logger.getLogger(HajIngredientsReportAppController.class);
	
	@Autowired(required = false)
	private HajIngredientsReportService reportService;

	@Autowired(required = false)
	private HajIngredientsReportConfService confService;

	@Autowired
	private RedisSpringProxy redisSpringProxy;



	/**
	 * 检测记录
	 * @param response
	 * @param sign
	 */
	@RequestMapping(value = "/list")
	public void list(HttpServletResponse response,
					 @RequestParam String sign,@RequestParam Integer currentPage,@RequestParam String areaCode) {
		Map<String, Object> jsonMap = new HashMap<>();
		JsonConfig jsonConfig = new JsonConfig();

		Criteria criteria = new Criteria();
		Map<String, Object> condition = new HashMap<>();
		criteria.setCurrentPage(currentPage);
		condition.put("areaCode", Tools.getAreaCode(areaCode));
		criteria.setCondition(condition);
		try {
			if (MD5Tools.checkSign(sign)) {
				// 签名验证通过，则继续其他逻辑
				jsonMap.put("error", "0");
				jsonMap.put("msg", "成功");

				//获取食材检测报告信息列表
				List<HajIngredientsReport> reportList = new ArrayList<>();
				String redisKey = "HajIngredientsReportList_" + areaCode+"_"+currentPage;

				//判断是否有缓存
				if (redisSpringProxy.read(redisKey) != null) {
					log.info("getHajIngredientsReportList()获取食材检测记录列表缓存中...");
					reportList = (List<HajIngredientsReport>) redisSpringProxy.read(redisKey);
				} else {
					log.info("getHajIngredientsReportList()从数据库获取食材检测记录列表...");
					List<HajIngredientsReport> list = reportService.reportListPage(criteria);
					//日期格式转换
					for (HajIngredientsReport report:list) {
						report.setDate(DateUtil.datetime2StrZHCN(report.getDate()));
						reportList.add(report);
					}
					// 查询到结果就放入缓存中
					if (reportList.size() > 0) {
						redisSpringProxy.save(redisKey, reportList);
					}
				}

				if (reportList.size() > 0) {
					jsonMap.put("error", "0");
					jsonMap.put("msg", "成功");
					jsonMap.put("reportList", reportList);
					jsonMap.put("page", criteria);
				} else {
					jsonMap.put("error", "0");
					jsonMap.put("msg", "为空");
					jsonMap.put("HajCommodity", "");
					jsonMap.put("page", criteria);
				}

				// 过滤掉无用的属性
				jsonConfig.setIgnoreDefaultExcludes(false);
				jsonConfig.setExcludes(new String[]{"areaCode", "beginTime","catename","catesort","createTime","description","endTime","page","updateTime"});
			} else {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "签名异常");
			}
		} catch (Exception e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
			log.error(e.getMessage(), e);
		} finally {
			try {
				JSONUtils.printObject(jsonMap, response, jsonConfig);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	/**
	 * 食材检测报告详情
	 * @param response
	 * @param sign
	 * @param reportId
	 */
	@RequestMapping(value = "/reportDetails")
	public void reportDetails(HttpServletResponse response,
					 @RequestParam String sign,@RequestParam Integer reportId,@RequestParam String areaCode) {
		Map<String, Object> jsonMap = new HashMap<>();
		JsonConfig jsonConfig = new JsonConfig();
		try {
			if (MD5Tools.checkSign(sign)) {
				// 签名验证通过，则继续其他逻辑
				jsonMap.put("error", "0");
				jsonMap.put("msg", "成功");

				HajIngredientsReportVo reportVo =null;
				//获取食材检测报告详细信息
				if (reportId == 0) {
					//返回最新的食材检测报告
					HajIngredientsReport report = reportService.getNewReport4app( Tools.getAreaCode(areaCode));
					if (report != null) {
						reportId = report.getId();
						reportVo = reportService.getByReportId(reportId);
					}
				} else {
					reportVo = reportService.getByReportId(reportId);
				}

				//获取检测报告的分类对象列表
				List<HajIngredientsReportConfVo> reportConf = confService.getByReportId(reportId);
				for (HajIngredientsReportConfVo conf :reportConf) {
					if (Tools.isNotEmpty(conf.getCateimgs())) {
						conf.setConfImgs(conf.getCateimgs().split(","));
					}
				}
				if (reportVo != null) {
					reportVo.setConfVoList(reportConf);
					jsonMap.put("reportDetails", reportVo);
				} else {
					jsonMap.put("reportDetails", reportVo);
					jsonMap.put("msg", "该城市还没上传食材检测报告!");
				}

				// 过滤掉无用的属性
				jsonConfig.setIgnoreDefaultExcludes(false);
				jsonConfig.setExcludes(new String[]{"areaCode", "beginTime", "cateimgs","catesort","createTime","endTime","page","updateTime","confVo"});
			} else {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "签名异常");
			}
		} catch (Exception e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
			log.error(e.getMessage(), e);
		} finally {
			try {
				JSONUtils.printObject(jsonMap, response, jsonConfig);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}



}
