package com.flf.controller;

import com.flf.entity.HajCategory;
import com.flf.entity.HajCommodityType;
import com.flf.service.HajCategoryService;
import com.flf.service.HajCommodityService;
import com.flf.service.HajCommodityTypeService;
import com.flf.service.RedisSpringProxy;
import com.flf.util.FileUpload;
import com.flf.util.JSONUtils;
import com.flf.util.Tools;
import com.flf.util.wms.WmsSender;
import net.sf.json.JSONArray;
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
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 
 * <br>
 * <b>功能：</b>HajCommodityTypeController<br>
 * <br>
 */
@Controller
@RequestMapping(value = "/commodityType")
public class HajCommodityTypeController {

	@Autowired(required = false)
	private HajCommodityTypeService service;

	@Autowired
	private HajCommodityService commodityService;

	@Autowired
	private RedisSpringProxy redisSpringProxy;

	@Autowired
	private HajCategoryService categoryService;


	/**
	 * 后台商品分类列表2.0，梯级显示全部分类
	 * 
	 * @author SevenWong<br>
	 * @date 2016年7月28日下午3:06:20
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(String attribute) throws Exception {
		ModelAndView mv = new ModelAndView();

		String commodityAttr = "生鲜";

		if ("fruits".equals(attribute)) {
			commodityAttr = "水果";
		} else if ("group".equals(attribute)) {
			commodityAttr = "团购";
		} else if ("breakfast".equals(attribute)) {
			commodityAttr = "早餐";
		} else {
			attribute = "default";
		}

		List<HajCommodityType> commodityTypeList = service.list(commodityAttr);

		mv.addObject("commodityTypeList", commodityTypeList);
		mv.addObject("attribute", attribute);
		mv.setViewName("commodityTypeList");
		return mv;
	}

	/**
	 * 跳转到商品分类添加页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/add")
	public ModelAndView add() throws Exception {
		ModelAndView mv = new ModelAndView();

		List<HajCommodityType> parentTypeList = service.getBigTypeByattr("生鲜");
		mv.addObject("parentTypeList", parentTypeList);

		HajCategory category = new HajCategory();
		category.getPage().setShowCount(100);
		List<HajCategory> categoryList = categoryService.listPage(category);
		mv.addObject("categoryList", categoryList);

		mv.setViewName("commodityTypeEdit");
		return mv;
	}

	/**
	 * 保存添加或修改的商品分类<br>
	 * 大分类parentId=0 前台ajax调用
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public void save(HajCommodityType commodityType, PrintWriter out) throws Exception {
		String result;

		commodityType.setDisplay(commodityType.getDisplay() == null ? 1 : commodityType.getDisplay());

		int typeCount = service.getTypeCount(commodityType);

		if (typeCount < 1) {
			if (commodityType.getId() == null) {
				service.add(commodityType);

				// 同步修改至京东WMS
				WmsSender.categoryInfoInsert(commodityType);
			} else {
				service.update(commodityType);

				// 同步修改至京东WMS
				WmsSender.categoryInfoUpdate(commodityType);
			}
			result = "success";
			commodityService.resetCommodityRedis();
		} else {
			result = "repeat";
		}

		out.print(result);
		out.close();
	}

	/**
	 * 根据商品分类id查询对应的商品分类，返回给前端修改页面填充数据
	 */
	@RequestMapping(value = "/edit")
	public ModelAndView edit(@RequestParam int id, String attribute) throws Exception {
		ModelAndView mv = new ModelAndView();

		HajCommodityType commodityType = service.queryById(id);
		List<HajCommodityType> parentTypeList = service.getBigTypeByattr(commodityType.getCommodityAttr());

		HajCategory category = new HajCategory();
		category.getPage().setShowCount(100);
		if (commodityType.getParentId() != null && commodityType.getParentId() > 0) {
			// 如果是小类，则取父类的categoryId
			HajCommodityType parentType = service.queryById(commodityType.getParentId());
			if (parentType.getCategoryId() != null) {
				HajCategory getCategoryById = categoryService.queryById(parentType.getCategoryId());

				if (getCategoryById != null ) {
					mv.addObject("category", getCategoryById.getName());
				}
			}
		}

		List<HajCategory> categoryList = categoryService.listPage(category);

		mv.addObject("vo", commodityType);
		mv.addObject("parentTypeList", parentTypeList);
		mv.addObject("attribute", attribute);
		mv.addObject("defaultImage", redisSpringProxy.read("SystemConfiguration_commodity_upload_img_small"));
		mv.addObject("categoryList", categoryList);

		mv.setViewName("commodityTypeEdit");
		return mv;
	}

	/**
	 * 根据商品分类ID删除对应的商品分类<br>
	 * 前台ajax调用
	 * 
	 * @param id
	 *            单个或多个类别id
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public void delete(Integer[] id, HttpServletResponse response) throws Exception {
		service.delete(id);

		commodityService.resetCommodityRedis();

		JSONUtils.printObject("{status: 'success'}", response);
	}

	/**
	 * 根据商品属性查询大类
	 */
	@RequestMapping(value = "/getBigType")
	public void getBigType(@RequestParam String commodityAttr, HttpServletResponse response,HttpServletRequest request ) {
		try {
			response.setCharacterEncoding("utf-8");
			List<HajCommodityType> commodityTypeList = service.getBigTypeByattr(commodityAttr);
			JSONArray arr = JSONArray.fromObject(commodityTypeList);
			PrintWriter out;
			out = response.getWriter();
			String json = arr.toString();
			out.write(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据商品大类查询小类
	 */
	@RequestMapping(value = "/getSmallType")
	public void getSmallType(@RequestParam int parentId, HttpServletResponse response) {
		List<HajCommodityType> commodityTypeList = service.getTypeByParentId(parentId);
		JSONArray arr = JSONArray.fromObject(commodityTypeList);
		PrintWriter out;
		try {
			response.setCharacterEncoding("utf-8");
			out = response.getWriter();
			String json = arr.toString();
			out.write(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 文件上传（上传分类icon图片）
	 *
	 * @param file
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public void uploadFile(@RequestParam("fileName") MultipartFile file, HttpServletRequest request,
						   HttpServletResponse response) throws Exception {
		response.setContentType("application/json;charset=UTF-8");

		// 手动上传图片默认保存在同一目录下，然后更新文件后缀版本号
		String subPath = "commodityType" + File.separator + "icon" + File.separator;
		String filePath = FileUpload.uploadFile(file, subPath, request);
		Map<String, Object> result = new HashMap<>();
		if (Tools.isNotEmpty(filePath)) {
			filePath = filePath + "?v=" + new Random().nextInt(1000);
			result.put("filePath", filePath);
			result.put("result", "success");
		} else {
			result.put("result", "error");
		}
		JSONUtils.printObject(result, response);
	}
}
