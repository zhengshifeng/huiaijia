package com.flf.controller;

import com.flf.entity.HajCommodityFailure;
import com.flf.entity.HajCommodityVo;
import com.flf.entity.HajCommunityPersion;
import com.flf.service.HajCommodityFailureService;
import com.flf.service.HajCommodityService;
import com.flf.service.HajCommodityTypeService;
import com.flf.service.HajCommunityPersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping(value = "/commodityFailure")
public class HajCommodityFailureController {

	@Autowired
	private HajCommodityFailureService service;

	@Autowired
	private HajCommodityService commodityService;

	@Autowired
	private HajCommodityTypeService commodityTypeService;

	@Autowired
	private HajCommunityPersionService hajCommunityPersionService;

	/**
	 * 在该小区中启用选中的商品
	 * 
	 * @author SevenWong<br>
	 * @date 2016年8月11日下午5:41:07
	 * @param communityId
	 * @param commodityId
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value = "/enable")
	public void enable(Integer[] commodityId, Integer communityId, PrintWriter out) throws Exception {
		Integer existId;
		HajCommodityFailure commodityFailure;
		for (Integer cid : commodityId) {
			commodityFailure = new HajCommodityFailure();
			commodityFailure.setCommodityId(cid);
			commodityFailure.setCommunityId(communityId);
			existId = service.isExist(commodityFailure);
			if (existId != null) {
				service.delete(existId);
				commodityService.createCommodityIndex(cid);
			}
		}

		commodityService.resetCommodityRedis();

		out.print("success");
		out.close();
	}

	/**
	 * 在该小区中禁用选中的商品
	 * 
	 * @author SevenWong<br>
	 * @date 2016年8月11日下午5:41:54
	 * @param communityId
	 * @param commodityId
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value = "/disenable")
	public void disenable(Integer[] commodityId, Integer communityId, PrintWriter out) throws Exception {
		Integer isExist;
		HajCommodityFailure commodityFailure;
		for (Integer id : commodityId) {
			commodityFailure = new HajCommodityFailure();
			commodityFailure.setCommodityId(id);
			commodityFailure.setCommunityId(communityId);
			isExist = service.isExist(commodityFailure);
			if (isExist == null) {
				commodityFailure.setCreateTime(new Date());
				service.add(commodityFailure);

				commodityService.createCommodityIndex(id);
			}
		}

		commodityService.resetCommodityRedis();

		out.print("success");
		out.close();
	}

	@RequestMapping(value = "/list")
	public ModelAndView list(HajCommodityVo vo) throws Exception {
		ModelAndView mv = new ModelAndView();

		HajCommunityPersion queryById = hajCommunityPersionService.queryById(vo.getAreasId());

		List<HashMap<String, Object>> commodityList = service.getCommodityList(vo);

		commodityTypeService.putTypeList2MV(mv, vo.getCommodityAttr(), vo.getParentTypeId());

		mv.addObject("communityName", queryById.getCommunityName());
		mv.addObject("vo", vo);
		mv.addObject("commodityList", commodityList);
		mv.setViewName("commodityFailureList");

		return mv;
	}

}
