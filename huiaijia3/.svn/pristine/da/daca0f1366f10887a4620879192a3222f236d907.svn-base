package com.flf.controller;

import com.base.controller.BaseController;
import com.base.criteria.PromotionAreaCriteria;
import com.flf.entity.*;
import com.flf.service.*;
import com.flf.util.Const;
import com.flf.util.FileUpload;
import com.flf.util.JSONUtils;
import com.flf.util.Tools;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * <br>
 * <b>功能：</b>HajPromotionAreaAppController<br>
 * <br>
 */
@Controller
@RequestMapping(value = "/promotionArea")
public class HajPromotionAreaController extends BaseController{

	private final static Logger log = Logger.getLogger(HajPromotionAreaController.class);

	@Autowired(required = false)
	private HajPromotionAreaService service;

	@Autowired(required = false)
	private HajCommodityService commodityService;

	@Autowired(required = false)
	private HajAreasService areasService;

	@Autowired
	private HajCategoryService categoryService;

	@Autowired(required = false)
	private HajCategoryThreeService categoryThreeservice;

	@RequestMapping(value = "/list")
	public ModelAndView list(PromotionAreaCriteria vo, HttpSession session) throws Exception {
		ModelAndView mv = new ModelAndView();

		userSession = (User) session.getAttribute(Const.SESSION_USER);
		vo.setAreaCode(userSession.getAreaCode());

		List<Map<String, Object>>  promotionAreaList = service.getPromotionAreaList(vo); //跳转分页
		List<HajAreas> areas =  areasService.listCity();
		mv.addObject("vo", vo);
		mv.addObject("cityList",areas);
		mv.addObject("promotionAreaList", promotionAreaList);
		mv.setViewName("promotionAreaList");

		return mv;
	}

	@RequestMapping(value = "/add")
	public ModelAndView add(HttpSession session) throws Exception {
		ModelAndView mv = new ModelAndView();
		userSession = (User) session.getAttribute(Const.SESSION_USER);

		HajCategory category = new HajCategory();
		category.getPage().setShowCount(100);
		List<HajCategory> categoryList = categoryService.listPage(category);
		mv.addObject("categoryList", categoryList);

		mv.addObject("cityList", areasService.listCity());
		mv.addObject("isAdmin", Tools.isEmpty(userSession.getAreaCode()));
		mv.addObject("userSession", userSession);
		mv.setViewName("promotionAreaEdit");
		return mv;
	}

	@RequestMapping(value = "/edit")
	public ModelAndView edit(@RequestParam Integer id, HttpSession session) throws Exception {
		ModelAndView mv = new ModelAndView();
		userSession = (User) session.getAttribute(Const.SESSION_USER);

		//获取一级类目
		List<HajCategoryThree> cateList =categoryThreeservice.getOneCategory(0);
		//获取分类
		HajCategory category = new HajCategory();
		category.getPage().setShowCount(100);
		List<HajCategory> categoryList = categoryService.listPage(category);

		HajPromotionArea vo = service.queryById(id);
		mv.addObject("vo", vo);
		mv.addObject("cateList", cateList);
		mv.addObject("categoryList", categoryList);
		mv.addObject("cityList", areasService.listCity());
		mv.addObject("isAdmin", Tools.isEmpty(userSession.getAreaCode()));
		mv.addObject("userSession", userSession);
		mv.setViewName("promotionAreaEdit");
		return mv;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public void save(HajPromotionArea vo, PrintWriter out, HttpSession session) throws Exception {
		String result = "success";
		if (vo != null) {
			// 默认按照当前登录用户
			userSession = (User) session.getAttribute(Const.SESSION_USER);

			// 超管修改商品按照前台选择的areaCode保存对应的值
			if (Tools.isEmpty(userSession.getAreaCode())) {
				vo.setAreaCode(vo.getAreaCode());
			} else {
				vo.setAreaCode(userSession.getAreaCode());
			}

//			vo.setDisplay(vo.getDisplay() == null ? 0 : 1);
			if (vo.getDisplay() == null ) {
				vo.setDisplay(0);
			}
			vo.setSort(vo.getSort() == null ? 0 : vo.getSort());
			vo.setCategoryId(vo.getAreaTypeId());
			if (vo.getId() != null && vo.getId() > 0) {
				service.updateBySelective(vo);
			} else {
				service.add(vo);
			}

			commodityService.resetCommodityRedis();
		} else {
			result = "error";
		}

		out.print(result);
		out.close();
	}

	/**
	 * 文件上传（上传图片）
	 */
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public void uploadFile(@RequestParam("fileName") MultipartFile file, HttpServletRequest request,
						   HttpServletResponse response) throws Exception {
		response.setContentType("application/json;charset=UTF-8");

		int imageWidth = 0;
		int imageHeight = 0;
		BufferedImage image = ImageIO.read(file.getInputStream());
		if (image != null) {
			imageWidth = image.getWidth();//获取图片宽度，单位px
			imageHeight = image.getHeight();//获取图片高度，单位px
		}

		// 手动上传图片默认保存在同一目录下，然后更新文件后缀版本号
		String subPath = "images" + File.separator + "promotionArea" + File.separator;
		String filePath = FileUpload.uploadFile(file, subPath, request);
		Map<String, Object> result = new HashMap<>();
		if (!"".equals(filePath)) {
			filePath = filePath + "?w=" + imageWidth + "&h=" + imageHeight;
			result.put("filePath", filePath);
			result.put("result", "success");
		} else {
			result.put("result", "error");
		}
		JSONUtils.printObject(result, response);
	}




	/**
	 * 通过专区类型得到相应的分类
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getCateList",produces={"text/html;charset=UTF-8;","application/json;"})
	@ResponseBody
	public String getCateList(Integer parentId) throws Exception {

		List<Object> list = null;
		switch(parentId)
		{
			//1品牌专区
			case 1:
				list = null;
				break;
			//2推荐专题
			case 2:
				list = null;
				break;
			//3商品类目
			case 3:

				//获取一级类目
				List<HajCategoryThree> cateList =categoryThreeservice.getOneCategory(0);
				return JSONUtils.toJSONString(cateList);

			//4商品分类
			case 4:
				HajCategory category = new HajCategory();
				category.getPage().setShowCount(100);
				List<HajCategory> categoryList = categoryService.listPage(category);
				return JSONUtils.toJSONString(categoryList);

			default:
				list = null;
				break;
		}

		return JSONUtils.toJSONString(list);
	}




}
