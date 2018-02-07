package com.flf.controller;

import com.flf.entity.HajCommunityBuilding;
import com.flf.entity.HajCommunityPersion;
import com.flf.service.HajCommunityBuildingService;
import com.flf.service.HajCommunityPersionService;
import com.flf.util.JSONUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <br>
 * <b>功能：</b>HajCommunityBuildingController<br>
 * <br>
 */
@Controller
@RequestMapping(value = "/communityBuilding")
public class HajCommunityBuildingController {

	private final static Logger log = Logger.getLogger(HajCommunityBuildingController.class);

	@Autowired(required = false)
	private HajCommunityBuildingService service;

	@Autowired(required = false)
	private HajCommunityPersionService communityService;

	@RequestMapping(value = "/list")
	public ModelAndView list(@RequestParam Integer communityId) {
		ModelAndView mv = new ModelAndView();
		try {
			HajCommunityPersion community = communityService.queryById(communityId);
			List<Map<String, Object>> returnList;
			returnList = new ArrayList<>();
			Map<String, Object> map;

			List<HajCommunityBuilding> list = service.getListByCommunityId(communityId);
			for (HajCommunityBuilding building : list) {
				map = new HashMap<>();
				map.put("id", building.getId());
				map.put("name", building.getName());
				map.put("pId", building.getParentId());
				map.put("open", true);
				returnList.add(map);
			}
			String buildings = JSONUtils.toJSONString(returnList);
			mv.addObject("buildings", buildings);
			mv.addObject("community", community);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mv.setViewName("communityBuildingList");
		return mv;
	}

	@RequestMapping(value = "/add")
	public ModelAndView add() throws Exception {
		ModelAndView mv = new ModelAndView();

		mv.setViewName("hajCommunityBuildingEdit");
		return mv;
	}

	@RequestMapping(value = "/edit")
	public ModelAndView edit(@RequestParam Integer id) throws Exception {
		ModelAndView mv = new ModelAndView();
		HajCommunityBuilding vo = service.queryById(id);
		mv.addObject("vo", vo);
		mv.setViewName("hajCommunityBuildingEdit");
		return mv;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public void save(HajCommunityBuilding po, HttpServletResponse response) throws Exception {
		Map<String, Object> jsonMap = new HashMap<>();
		jsonMap.put("error", "0");
		jsonMap.put("msg", "保存成功！");
		try {
			if (po != null) {
				if (po.getId() != null && po.getId() > 0) {
					service.updateBySelective(po);
				} else {
					po.setId(null);
					service.add(po);
				}
			} else {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "数据为空！");
			}
		} catch (Exception e) {
			log.info(e.getMessage(), e);
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常！");
		} finally {
			try {
				JSONUtils.printObject(jsonMap, response);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	@RequestMapping(value = "/delete")
	public void delete(Integer[] id, PrintWriter out) throws Exception {
		service.delete((Object[]) id);
		out.print("success");
		out.close();
	}
}
