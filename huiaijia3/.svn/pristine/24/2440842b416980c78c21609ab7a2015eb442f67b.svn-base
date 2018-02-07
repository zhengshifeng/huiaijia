package com.flf.controller;

import com.flf.entity.*;
import com.flf.service.HajAreasService;
import com.flf.service.HajCommunityPersionService;
import com.flf.service.HajCommunitySorterService;
import com.flf.util.Const;
import com.flf.util.JSONUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * <br>
 * <b>功能：</b>HajCommunityPersionController<br>
 * <br>
 */
@Controller
@RequestMapping(value = "/community")
public class HajCommunityPersionController {

	@Autowired(required = false)
	private HajCommunityPersionService service;

	@Autowired(required = false)
	private HajAreasService areasService;

	@Autowired(required = false)
	private HajCommunitySorterService communitySorterService;

	/**
	 * 小区分拣小组管理列表，只能显示已激活的小区
	 * 
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/communitySorterList")
	public ModelAndView communitySorterList(HttpSession session, HajCommunitySorterVo vo) {
		ModelAndView mv = new ModelAndView();
		try {

			/* 地区数据 */
			String provinceCode = vo.getProvinceCode();
			String cityCode = vo.getCityCode();
			areasService.putAreaList(mv, provinceCode, cityCode);

			/* 分拣小组列表数据 */
			HajCommunitySorter hajCommunitySorter = new HajCommunitySorter();
			Page page = new Page();
			page.setShowCount(100);
			hajCommunitySorter.setPage(page);

			User user = (User) session.getAttribute(Const.SESSION_USER);
			if (null != user) {
				hajCommunitySorter.setAreaCode(user.getAreaCode());
				vo.setAreaCode(user.getAreaCode());
			}

			List<HajCommunitySorter> sorterList = communitySorterService.listPage(hajCommunitySorter);
			mv.addObject("sorterList", sorterList);

			List<Map<String, Object>> communitySorterList = service.listPageSorter(vo);
			mv.addObject("communitySorterList", communitySorterList);
			mv.addObject("vo", vo);
			mv.setViewName("communitySorterManager");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

	/**
	 * 将选中的小区添加到指定的分拣小组
	 * 
	 * @param communityIds
	 * @param sorterId
	 * @param response
	 */
	@RequestMapping(value = "/add2Sorter")
	public void add2Sorter(@RequestParam Integer[] communityIds, @RequestParam Integer sorterId,
			HttpServletResponse response) throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("error", "success");
		jsonMap.put("msg", "保存成功");
		if (communityIds != null && communityIds.length > 0 && sorterId != null) {
			HajCommunityPersion communityPersion;
			HajCommunityPersion oldCP;
			for (Integer id : communityIds) {
				oldCP = service.queryById(id);
				communityPersion = new HajCommunityPersion();
				communityPersion.setId(id);
				communityPersion.setSorterId(sorterId);
				communityPersion.setVersion(oldCP.getVersion());
				service.updateBySelective(communityPersion);
			}
		}

		try {
			JSONUtils.printObject(jsonMap, response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将选中的小区从指定的分拣小组中移除
	 * 
	 * @param communityIds
	 * @param response
	 */
	@RequestMapping(value = "/removeSorterId")
	public void removeSorterId(@RequestParam Integer[] communityIds, HttpServletResponse response) throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("error", "success");
		jsonMap.put("msg", "保存成功");

		HajCommunityPersion communityPersion;
		HajCommunityPersion oldCP;
		for (Integer communityId : communityIds) {
			oldCP = service.queryById(communityId);
			communityPersion = new HajCommunityPersion();
			communityPersion.setId(communityId);
			communityPersion.setSorterId(0);
			communityPersion.setVersion(oldCP.getVersion());
			service.updateBySelective(communityPersion);
		}

		try {
			JSONUtils.printObject(jsonMap, response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据分拣小组ID获取该分组下的所有小区名
	 * 
	 * @param sorterId
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getCommunitiesBySorterId")
	public void getCommunitiesBySorterId(@RequestParam Integer sorterId, HttpServletResponse response) throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("error", "success");

		String communities = service.getCommunitiesBySorterId(sorterId);
		jsonMap.put("data", communities);

		try {
			JSONUtils.printObject(jsonMap, response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 运营数据中心页面
	 * @param session
	 * @param mv
	 * @throws Exception
	 */
	@RequestMapping(value = "/orderDataCenter")
	public ModelAndView orderDataCenter(HttpSession session, ModelAndView mv) throws Exception {
		User user = (User) session.getAttribute(Const.SESSION_USER);
		Map<String, Object> operationsData = service.operationsDataCenter(user.getAreaCode());
		mv.addObject("operationsData", operationsData);
		mv.setViewName("orderDataCenter");
		return mv;
	}

}
