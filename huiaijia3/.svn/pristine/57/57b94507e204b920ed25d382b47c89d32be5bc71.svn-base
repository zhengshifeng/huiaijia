package com.flf.controller;

import com.flf.entity.HajCategoryThree;
import com.flf.service.HajCategoryThreeService;
import com.flf.service.RedisSpringProxy;
import com.flf.util.JSONUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * 
 * <br>
 * <b>功能：</b>HajCategoryThreeController<br>
 * <br>
 */ 
@Controller
@RequestMapping(value = "/categoryThree")
public class HajCategoryThreeController {
	
	private final static Logger log = Logger.getLogger(HajCategoryThreeController.class);
	
	@Autowired(required = false)
	private HajCategoryThreeService service;

	@Autowired
	private RedisSpringProxy redisSpringProxy;


	/**
	 * 类目层级管理列表
	 * @return
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list() {
		ModelAndView mv = new ModelAndView();
		try {
			List<Map<String, Object>> returnList = new ArrayList<>();
			List<HajCategoryThree> list = service.getThreeList();
			for (HajCategoryThree three : list) {
				Map map = new HashMap<>();
				map.put("id", three.getId());
				map.put("name", three.getName());
				map.put("pId", three.getParentId());
				map.put("open", true);
				returnList.add(map);
			}
			String buildings = JSONUtils.toJSONString(returnList);
			mv.addObject("buildings", buildings);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mv.setViewName("addCommodityCategoryList");

		return mv;
	}



	@RequestMapping(value = "/add")
	public ModelAndView add() throws Exception {
		ModelAndView mv = new ModelAndView();

		mv.setViewName("hajCategoryThreeEdit");
		return mv;
	}

	@RequestMapping(value = "/edit")
	public ModelAndView edit(@RequestParam Integer id) throws Exception {
		ModelAndView mv = new ModelAndView();
		HajCategoryThree vo = service.queryById(id);
		mv.addObject("vo", vo);
		mv.setViewName("hajCategoryThreeEdit");
		return mv;
	}



	/**
	 * 保存商品类目节点
	 * @param po
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public void save(HajCategoryThree po, HttpServletResponse response) throws Exception {
		Map<String, Object> jsonMap = new HashMap<>();
		jsonMap.put("error", "0");
		jsonMap.put("msg", "保存成功！");
		try {
			if (po != null) {
				if (po.getId() != null && po.getId() > 0) {
					po.setEndTime(new Date());
					service.updateBySelective(po);
				} else {
					po.setId(null);
					service.add(po);
				}
				//清除缓存
				redisSpringProxy.deletePattern("twoAndThreeCategoryList_*");	//获取二三级类目的接口
				redisSpringProxy.deletePattern("oneCategoryList_*");			//获取一级类目的接口
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


	/**
	 * 删除类目节点
	 * @param id
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public void delete(Integer[] id, PrintWriter out) throws Exception {
		service.delete((Object[]) id);
		//清除缓存
		redisSpringProxy.deletePattern("twoAndThreeCategoryList_*");	//获取二三级类目的接口
		redisSpringProxy.deletePattern("oneCategoryList_*");			//获取一级类目的接口
		out.print("success");
		out.close();
	}


	/**
	 * 三级联动获取类目信息列表
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getTwoCate",produces={"text/html;charset=UTF-8;","application/json;"})
	@ResponseBody
	public String getTwoCate(Integer parentId) throws Exception {
		List<HajCategoryThree> list = service.getTwoByparentId(parentId);
		return JSONUtils.toJSONString(list);
	}


	/**
	 * 类目管理查找功能
	 * @param oneId
	 * @param twoId
	 * @param threeId
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findCategoryById")
	public ModelAndView findCategoryById(@RequestParam Integer oneId,@RequestParam Integer twoId,
										 @RequestParam Integer threeId, HttpServletResponse response) throws Exception {

		ModelAndView mv = new ModelAndView();


		//获取一级类目
		List<HajCategoryThree> one =service.getOneCategory(oneId);
		//获取二级类目
		List<HajCategoryThree> two =service.getTwoCategory(oneId,twoId);
		//获取三级类目
		List<HajCategoryThree> three  =service.getThreeCategory(twoId,threeId);
		//拼类目
		List<HashMap<String, Object>> listCate = new ArrayList<>();
		for (HajCategoryThree careThree:three) {
			HashMap<String, Object> cateName = new HashMap<>();
			cateName.put("threeName",careThree.getName());						//三级类目
			cateName.put("icon",careThree.getIcon());							//图标
			cateName.put("threeId",careThree.getId());							//id
			for (HajCategoryThree cateTwo:two) {
				if (careThree.getParentId().equals(cateTwo.getId())) {
					cateName.put("towName",cateTwo.getName());					//二级类目
					for (HajCategoryThree cateOne:one) {
						if (cateTwo.getParentId().equals(cateOne.getId())) {
							cateName.put("oneName",cateOne.getName());			//一级类目
						}
					}
				}
			}
			listCate.add(cateName);
		}
		mv.addObject("listCate", listCate);
		mv.setViewName("categoryManage");

		return mv;
	}






}
