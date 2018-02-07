package com.flf.controller;

import com.flf.entity.HajCommodity;
import com.flf.entity.HajSpecialTopicCommodity;
import com.flf.entity.HajSpecialTopicCommodityVo;
import com.flf.service.HajCommodityService;
import com.flf.service.HajSpecialTopicCommodityService;
import com.flf.service.RedisSpringProxy;
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
import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * <br>
 * <b>功能：</b>HajSpecialTopicCommodityController<br>
 * <br>
 */
@Controller
@RequestMapping(value = "/hajSpecialTopicCommodity")
public class HajSpecialTopicCommodityController {

	private final static Logger log = Logger.getLogger(HajSpecialTopicCommodityController.class);

	@Autowired(required = false)
	private HajSpecialTopicCommodityService service;

	@Autowired(required = false)
	private HajCommodityService commodityService;

	@Autowired(required = false)
	private RedisSpringProxy redisSpringProxy;

	@RequestMapping(value = "/list")
	public ModelAndView list(HajSpecialTopicCommodity po) throws Exception {
		ModelAndView mv = new ModelAndView();

		List<HajSpecialTopicCommodityVo> commodityList = service.getCommodityList(po);

		mv.addObject("specialTopicId", po.getSpecialTopicId());
		mv.addObject("commodityList", commodityList);
		mv.setViewName("specialTopicCommodityList");
		return mv;
	}

	@RequestMapping(value = "/add")
	public ModelAndView add() throws Exception {
		ModelAndView mv = new ModelAndView();

		mv.setViewName("");
		return mv;
	}

	@RequestMapping(value = "/edit")
	public ModelAndView edit(@RequestParam Integer id) throws Exception {
		ModelAndView mv = new ModelAndView();

		HajSpecialTopicCommodity queryById = service.queryById(id);
		HajCommodity commodity = commodityService.queryById(queryById.getCommodityId());

		mv.addObject("specialTopicId", queryById.getSpecialTopicId());
		mv.addObject("commodity", commodity);
		mv.addObject("defaultImg", redisSpringProxy.read("SystemConfiguration_commodity_upload_img_small"));
		mv.setViewName("specialTopicCommodityEdit");
		return mv;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public void save(HajSpecialTopicCommodity po, PrintWriter out) {
		String result = "success";
		if (po != null) {
			try {
				service.add(po);

				// 将商品进补图片跟进补描述默认使用商品小图跟商品描述
				HajCommodity queryById = commodityService.queryById(po.getCommodityId());
				HajCommodity commodity = new HajCommodity();
				commodity.setId(queryById.getId());
				commodity.setImage3(queryById.getSmallPic());
				commodity.setDescription2(queryById.getDescription());
				commodityService.updateBySelective(commodity);

				redisSpringProxy.deletePattern("specialTopicCommodityList_*");
			} catch (Exception e) {
				result = "error";
				log.info(e.getMessage(), e);
			}
		} else {
			result = "error";
		}

		out.print(result);
		out.close();
	}

	@RequestMapping(value = "/updateCommodity", method = RequestMethod.POST)
	public void updateCommodity(HajCommodity commodity, PrintWriter out) {
		String result = "success";
		if (commodity != null) {
			try {
				commodityService.updateBySelective(commodity);

				redisSpringProxy.deletePattern("specialTopicCommodityList_*");
			} catch (Exception e) {
				result = "error";
				e.printStackTrace();
			}
		} else {
			result = "error";
		}

		out.print(result);
		out.close();
	}

	@RequestMapping(value = "/delete")
	public void delete(Integer id, PrintWriter out) {
		try {
			service.delete(id);

			redisSpringProxy.deletePattern("specialTopicCommodityList_*");
			out.print("success");
		} catch (Exception e) {
			out.print("error");
			e.printStackTrace();
		}
		out.close();
	}

	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public void uploadFile(@RequestParam("fileName") MultipartFile file, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("application/json;charset=UTF-8");

		String subPath = "special_topic" + File.separator //
				+ "commodity" + File.separator;

		String filePath = FileUpload.uploadFile(file, subPath, request);
		Map<String, Object> result = new HashMap<String, Object>();
		if (!"".equals(filePath)) {
			result.put("filePath", filePath);
			result.put("result", "success");
		} else {
			result.put("result", "error");
		}
		JSONUtils.printObject(result, response);
	}

}
