package com.flf.controller;

import com.flf.entity.HajCommodityCategory;
import com.flf.service.HajCommodityCategoryService;
import com.flf.service.RedisSpringProxy;
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
import java.util.*;

/**
 * 
 * <br>
 * <b>功能：</b>HajCommodityCategoryController<br>
 * <br>
 */ 
@Controller
@RequestMapping(value = "/commodityCategory")
public class HajCommodityCategoryController {
	
	private final static Logger log = Logger.getLogger(HajCommodityCategoryController.class);
	
	@Autowired(required = false)
	private HajCommodityCategoryService service;

	@Autowired(required = false)
	private RedisSpringProxy redisSpringProxy;


	/**
	 * 批量添加商品到三级类目中
	 * @param commodityId
	 * @param threeId
	 * @param response
	 */
	@RequestMapping(value = "/add2category")
	public void add2category(@RequestParam Integer[] commodityId, @RequestParam Integer threeId,
							 HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("error", "success");
		jsonMap.put("msg", "保存成功");
		if (commodityId != null && commodityId.length > 0 && threeId != null) {
			//封装数据
			List<HajCommodityCategory> list = new ArrayList<>();
			for(Integer id : commodityId ){
				HajCommodityCategory  comcate = new HajCommodityCategory();
				comcate.setCommodityId(id);
				comcate.setCateId(threeId);
				comcate.setCreateTime(new Date());
				list.add(comcate);
			}
			//批量插入
			service.saveCommoditysToThreeCate(list);
			//删除缓存
			redisSpringProxy.deletePattern("commodityList_*");				//获取三级类目下的商品接口
			redisSpringProxy.deletePattern("oneDollarPurchaseCategory_*");	//一元购商品
			redisSpringProxy.deletePattern("twoAndThreeCategoryList_*"); 	//二级类目缓存
			redisSpringProxy.deletePattern("oneCategoryList_*"); 			//一级类目缓存
		}

		try {
			JSONUtils.printObject(jsonMap, response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}



	/**
	 * 批量从三级类目中移除
	 * @param commodityId
	 * @param threeId
	 * @param response
	 */
	@RequestMapping(value = "/deleteCommodityToCategory")
	public void deleteCommodityToCategory(@RequestParam Integer[] commodityId, @RequestParam Integer threeId,
							 HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("error", "success");
		jsonMap.put("msg", "保存成功");
		if (commodityId != null && commodityId.length > 0 && threeId != null) {
			//封装数据
			List<HajCommodityCategory> list = new ArrayList<>();
			for(Integer id : commodityId ){
				HajCommodityCategory  comcate = new HajCommodityCategory();
				comcate.setCommodityId(id);
				comcate.setCateId(threeId);
				list.add(comcate);
			}
			//批量移除
			service.deleteCommodityFromCategory(list);
			//删除缓存
			redisSpringProxy.deletePattern("commodityList_*");				//获取三级类目下的商品接口
			redisSpringProxy.deletePattern("oneDollarPurchaseCategory_*"); 	//一元购商品
			redisSpringProxy.deletePattern("twoAndThreeCategoryList_*"); 	//二级类目缓存
			redisSpringProxy.deletePattern("oneCategoryList_*"); 			//一级类目缓存
		}

		try {
			JSONUtils.printObject(jsonMap, response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}





	@RequestMapping(value = "/list")
	public ModelAndView list(HajCommodityCategory vo) {
		ModelAndView mv = new ModelAndView();

		List<HajCommodityCategory> list = service.listPage(vo);
		mv.addObject("vo", vo);
		mv.addObject("list", list);
		mv.setViewName("hajCommodityCategoryList");

		return mv;
	}

	@RequestMapping(value = "/add")
	public ModelAndView add() throws Exception {
		ModelAndView mv = new ModelAndView();

		mv.setViewName("hajCommodityCategoryEdit");
		return mv;
	}

	@RequestMapping(value = "/edit")
	public ModelAndView edit(@RequestParam Integer id) throws Exception {
		ModelAndView mv = new ModelAndView();
		HajCommodityCategory vo = service.queryById(id);
		mv.addObject("vo", vo);
		mv.setViewName("hajCommodityCategoryEdit");
		return mv;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public void save(HajCommodityCategory po, PrintWriter out) throws Exception {
		String result = "success";
		if (po != null) {
			if (po.getId() == null) {
				service.add(po);
			} else {
				service.updateBySelective(po);
			}
		} else {
			result = "error";
		}

		out.print(result);
		out.close();
	}

	@RequestMapping(value = "/delete")
	public void delete(Integer[] id, PrintWriter out) throws Exception {
		service.delete((Object[]) id);
		out.print("success");
		out.close();
	}




}
