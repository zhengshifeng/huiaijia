package com.flf.controller;

import com.base.controller.BaseController;
import com.base.criteria.CommodityCategoryCriteria;
import com.base.criteria.Criteria;
import com.base.util.HttpUtil;
import com.flf.entity.*;
import com.flf.service.*;
import com.flf.util.*;
import com.wms.InventorySync;
import com.wms.entity.WmsInventory;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.*;

/**
 * <br>
 * <b>功能：</b>HajCommodityController<br>
 * <br>
 */
@Controller
@RequestMapping(value = "/commodity")
public class HajCommodityController extends BaseController {

	private static final Logger log = Logger.getLogger(HajCommodityController.class);

	@Autowired(required = false)
	private HajCommodityService service;

	@Autowired(required = false)
	private HajAreasService areasService;

	@Autowired
	private HajCommodityTypeService commodityTypeService;

	@Autowired
	private HajSupplyChainService supplyChainService;

	@Autowired
	private HajPromotionAreaService promotionAreaService;

	@Autowired
	private HajActivityService activityService;

	@Autowired
	private RedisSpringProxy redisSpringProxy;

	@Autowired(required = false)
	private HajCommodityBrandService commodityBrandService;

	@Autowired(required = false)
	private HajCouponCommodityService couponCommodityService;

	@Autowired(required = false)
	private HajCommodityBrandService brandService;

	@Autowired(required = false)
	private HajCategoryThreeService threeService;

	@Autowired(required = false)
	private HajSpecialTopicService topicService;

	/**
	 * 查询所有商品列表
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(HajCommodityVo vo, HttpSession session) throws Exception {
		ModelAndView mv = new ModelAndView();

		userSession = (User) session.getAttribute(Const.SESSION_USER);
		vo.setAreaCode(userSession.getAreaCode());

		//根据前端搜索条件参数进行必要封装
		if (vo!=null) {
			String plate = vo.getPlate();
			if (plate!=null) {
				if ("mustBuy".equals(plate)) {
					vo.setMustBuy(1);
				}
				if ("familyPreferences".equals(plate)) {
					vo.setFamilyPreferences(1);
				}
				if ("ishot".equals(plate)) {
					vo.setIshot(1);
				}
			}
		}

		//商品信息列表
		List<HajCommodityVo> commodityList = service.listPage(vo);

		//获取大小类列表
		commodityTypeService.putTypeList2MV(mv, vo.getCommodityAttr(), vo.getParentTypeId());

		// 促销专区列表
		HajPromotionArea promotionArea = new HajPromotionArea();
		promotionArea.setDisplay(1);
		promotionArea.setAreaCode(vo.getAreaCode());
		List<HajPromotionArea> promotionAreaList = promotionAreaService.listPage(promotionArea);
		mv.addObject("promotionAreaList", promotionAreaList);

		// 商品品牌列表
		List<HajCommodityBrand> brandList = brandService.getAllBrands();
		mv.addObject("brandList", brandList);

		//有效专题活动列表
		List<HajSpecialTopic> specialTopicList = topicService.getSpecialTopicList();
		mv.addObject("specialTopicList", specialTopicList);

		Criteria criteria = new Criteria();
		Map<String, Object> condition = new HashMap<>();
		condition.put("status", 1);
		condition.put("areaCode", userSession.getAreaCode());//地区表的城市代码
		criteria.setCondition(condition);
		List<HajActivity> activityList = activityService.queryByList(criteria);
		mv.addObject("activityList", activityList);

		mv.addObject("commodityList", commodityList);
		mv.addObject("vo", vo);
		mv.addObject("cityList", areasService.listCity());

		List<HajSupplyChain> supplyChainNames = supplyChainService.getSupplyChainNames();
		mv.addObject("supplyChainNames", supplyChainNames);
		mv.setViewName("commodityList");

		return mv;
	}


	/**
	 * 商品类目管理列表
	 * @param vo
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/ccList")
	public ModelAndView commodityList(CommodityCategoryCriteria vo, HttpSession session) throws Exception {
		ModelAndView mv = new ModelAndView();

		userSession = (User) session.getAttribute(Const.SESSION_USER);
		vo.setAreaCode(userSession.getAreaCode());

		//获取商品列表
		List<Map<String, Object>> commodityList = service.getCommodityList(vo);

		//根据属性，大类ID将多级商品分类列表存到ModelAndView中
		commodityTypeService.putTypeList2MV(mv, vo.getCommodityAttr(), vo.getParentTypeId());

		// 商品品牌列表
		List<HajCommodityBrand> brandList = brandService.getAllBrands();

		//查询供应商列表
		List<HajSupplyChain> supplyChainNames = supplyChainService.getSupplyChainNames();

		//返回查询类目对象
		List<HajCategoryThreeVo> twoList = threeService.getTwoCategoryByOneId(vo.getOnec());	//二级类目列表
		List<HajCategoryThree> threeList = threeService.getThreeCategoryByTwoId(vo.getTwoc());	//三级类目列表

		mv.addObject("twoList", twoList);
		mv.addObject("threeList", threeList);
		mv.addObject("supplyChainNames", supplyChainNames);
		mv.addObject("commodityList", commodityList);
		mv.addObject("brandList", brandList);
		mv.addObject("vo", vo);

		mv.setViewName("commodityCategoryList");

		return mv;
	}

	/**
	 * 跳转到商品添加页面
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/add")
	public ModelAndView add(HttpSession session) throws Exception {
		ModelAndView mv = new ModelAndView();

		String commodityNo = Tools.date2Str(new Date(), "yyMMddHHmmssSSS");
		HajCommodityVo vo = new HajCommodityVo();
		vo.setCommodityNo(redisSpringProxy.read("SystemConfiguration_commodityNoPrefix") + commodityNo);
		vo.setCostPrice(BigDecimal.ZERO);
		vo.setMarketPrice(BigDecimal.ZERO);
		vo.setOriginalPrice(BigDecimal.ZERO);
		vo.setPromotionPrice(BigDecimal.ZERO);
		vo.setSortingBatch(1);
		mv.addObject("vo", vo);
		commonData(mv, session);
		return mv;
	}


	/**
	 * 根据商品id查询对应的商品，返回给前端修改页面填充数据
	 *
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edit")
	public ModelAndView edit(@RequestParam Integer id, HttpSession session) throws Exception {
		ModelAndView mv = new ModelAndView();

		HajCommodityVo vo = service.getCommodityById(id);
		mv.addObject("vo", vo);

		commodityTypeService.putTypeList2MV(mv, vo.getCommodityAttr(), vo.getParentTypeId());

		commonData(mv, session);
		return mv;
	}

	/**
	 * 商品编辑页面填充的一些数据列表
	 *
	 * @param mv
	 * @throws Exception
	 */
	private void commonData(ModelAndView mv, HttpSession session) throws Exception {
		userSession = (User) session.getAttribute(Const.SESSION_USER);
		String sessionAreaCode = userSession.getAreaCode();

		List<HajSupplyChain> supplyChainNames = supplyChainService.getSupplyChainNames();
		mv.addObject("supplyChainNames", supplyChainNames);

		List<HajCommodityBrand> brands = commodityBrandService.getAllBrands();
		mv.addObject("brands", brands);

		// 活动列表
		Criteria criteria = new Criteria();
		Map<String, Object> condition = new HashMap<>();
		condition.put("status", 1);
		condition.put("areaCode", sessionAreaCode);
		criteria.setCondition(condition);
		List<HajActivity> activityList = activityService.queryByList(criteria);
		mv.addObject("activityList", activityList);

		addImg2Model(mv);

		mv.addObject("cityList", areasService.listCity());

		mv.addObject("isAdmin", Tools.isEmpty(userSession.getAreaCode()));
		mv.addObject("userSession", userSession);

		mv.setViewName("commodityEdit");
	}

	/**
	 * 保存添加或修改的商品<br>
	 * 前台ajax 调用
	 *
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public void save(HajCommodity commodity, HttpSession session, HttpServletResponse response) {
		Map<String, Object> returnMap = new HashMap<>();
		returnMap.put("error", "0");
		returnMap.put("msg", "success");
		try {
			if (commodity != null) {
				commodity.setIntrapartum((commodity.getIntrapartum() == null) ? "0" : "1");
				commodity.setIshot((commodity.getIshot() == null) ? 0 : 1);
				commodity.setFamilyPreferences((commodity.getFamilyPreferences() == null) ? 0 : 1);
				commodity.setAcidDischarge((commodity.getAcidDischarge() == null) ? "0" : "1");
				commodity.setIsNew((commodity.getIsNew() == null) ? 0 : 1);
				commodity.setMustBuy((commodity.getMustBuy() == null) ? 0 : 1);
				commodity.setStamp((commodity.getStamp() == null) ? 0 : 1);
				commodity.setInvalid((commodity.getInvalid() == null) ? 0 : 1);
				commodity.setPraise((commodity.getPraise() == null) ? 0 : commodity.getPraise());
				commodity.setRecommend((commodity.getRecommend() == null) ? 0 : commodity.getRecommend());
				commodity.setSearchCount((commodity.getSearchCount() == null) ? 0 : commodity.getSearchCount());
				commodity.setSortingBatch((commodity.getSortingBatch() == null) ? 1 : commodity.getSortingBatch());
				commodity.setBrandId((commodity.getBrandId() == null) ? 0 : commodity.getBrandId());

				// 默认按照当前登录用户
				userSession = (User) session.getAttribute(Const.SESSION_USER);

				// 具有切换管理城市权限的用户，修改商品按照前台选择的areaCode保存对应的值
				if (Tools.isEmpty(userSession.getAreaCode())) {
					commodity.setAreaCode(commodity.getAreaCode());
				} else {
					commodity.setAreaCode(userSession.getAreaCode());
				}

				// 商品作废后默认下架
				if (commodity.getInvalid() != null && commodity.getInvalid() == 1) {
					commodity.setShelves(0);
				}

				service.save(commodity);
				//删除缓存
				service.resetCommodityRedis();
				HajCommodityUtil.resetCommodityRedisAndESIndex(service, commodity.getId());
			} else {
				returnMap.put("error", "3");
				returnMap.put("msg", "数据为空");
			}
		} catch (DuplicateKeyException e) {
			returnMap.put("error", "4");
			returnMap.put("msg", "此商品已存在");
			log.info(e.getCause());
		} catch (Exception e) {
			returnMap.put("error", "1");
			returnMap.put("msg", "未知异常");
			log.info(e.getMessage(), e);
		} finally {
			try {
				JSONUtils.printObject(returnMap, response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 根据商品ID删除对应的商品<br>
	 * 前台ajax 调用
	 *
	 * @param id
	 * @param out
	 * @throws Exception
	 * @deprecated 屏蔽此功能
	 */
	@Deprecated
	@RequestMapping(value = "/delete")
	public void delete(Integer[] id, PrintWriter out) throws Exception {
		out.print("success");
		out.close();
	}

	/**
	 * 文件上传（上传商品图片）
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
		String subPath = "commodity_images" + File.separator;
		String filePath = FileUpload.uploadFile(file, subPath, request);
		Map<String, Object> result = new HashMap<>();
		if (!"".equals(filePath)) {
			filePath = filePath + "?v=" + new Random().nextInt(1000);
			result.put("filePath", filePath);
			result.put("result", "success");
		} else {
			result.put("result", "error");
		}

		JSONUtils.printObject(result, response);
	}


	/**
	 * 文件上传（上传类目图标）
	 *
	 * @param file
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value = "/uploadCateFile", method = RequestMethod.POST)
	public void uploadCateFile(@RequestParam("fileName") MultipartFile file,@RequestParam("cateId") String cateId ,HttpServletRequest request,
						   HttpServletResponse response) throws Exception {

		response.setContentType("application/json;charset=UTF-8");

		// 手动上传图片默认保存在同一目录下，然后更新文件后缀版本号
		String subPath = "images" + File.separator + "category" + File.separator;
		String filePath = FileUpload.uploadFile(file, subPath, request);
		Map<String, Object> result = new HashMap<>();
		if (!"".equals(filePath)) {
			filePath = filePath + "?v=" + new Random().nextInt(1000);
			result.put("filePath", filePath);
			result.put("result", "success");
		} else {
			result.put("result", "error");
		}

		JSONUtils.printObject(result, response);

		//保存类目图片
		HajCategoryThree vo = new HajCategoryThree();
		int id = Integer.parseInt( cateId);
		vo.setId(id);
		vo.setIcon(filePath);
		threeService.updateBySelective(vo);

		//清除接口缓存
		redisSpringProxy.deletePattern("oneDollarPurchaseCategory_*");	//一元购接口
		redisSpringProxy.deletePattern("twoAndThreeCategoryList_*");	//获取三级类目接口


	}

	/**
	 * 从excel中批量导入商品
	 *
	 * @throws Exception
	 */
	@RequestMapping(value = "/batchImport", method = RequestMethod.POST)
	public void batchImport(@RequestParam("fileName") MultipartFile file, @RequestParam("update") boolean update,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("application/json;charset=UTF-8");

		String subPath = "xlsx" + File.separator + "commodity" + File.separator;
		String filePath = FileUpload.uploadExcel(file, subPath);

		String result = service.batchImport(filePath, update, request);
		log.info("商品导入结果: " + result);
		Map<String, Object> resultMap = new HashMap<>();
		if (Objects.equals(result, "success")) {
			HajCommodityUtil.resetCommodityRedisAndESIndex(service);
			resultMap.put("result", result);
		} else {
			resultMap.put("result", result);
		}

		JSONUtils.printObject(resultMap, response);
	}

	@RequestMapping(value = "/createIndex")
	public void createCommodityIndex() throws Exception {
		// 创建索引
		service.createCommodityIndex();
	}

	/**
	 * 此方法提供商品编辑页面默认显示的图片
	 *
	 * @param mv
	 * @author SevenWong<br>
	 * @date 2016年6月15日下午2:20:15
	 */
	private void addImg2Model(ModelAndView mv) {
		mv.addObject("bigImage", redisSpringProxy.read("SystemConfiguration_commodity_upload_img_big"));
		mv.addObject("smallImage", redisSpringProxy.read("SystemConfiguration_commodity_upload_img_small"));
	}

	/**
	 * 导出全部商品
	 *
	 * @param response
	 * @throws IOException
	 * @author SevenWong<br>
	 * @date 2016年7月19日下午4:38:50
	 */
	@RequestMapping(value = "/exportCommodity")
	public void exportCommodity(HajCommodityVo vo, HttpServletRequest request, HttpServletResponse response,
			HttpSession session) throws IOException {
		userSession = (User) session.getAttribute(Const.SESSION_USER);
		vo.setAreaCode(userSession.getAreaCode());

		String filename = HttpUtil.encodeFilename(request, "商品列表");
		XSSFWorkbook wb = service.exportCommodity(vo);
		ExcelUtil.output(response, filename, wb);
	}

	/**
	 * 库存管理列表
	 *
	 * @param commodityVo
	 * @return
	 * @throws Exception
	 * @author SevenWong<br>
	 * @date 2016年10月8日下午4:24:49
	 */
	@RequestMapping(value = "/inventoryList")
	public ModelAndView inventoryList(HajCommodityVo commodityVo, HttpSession session) throws Exception {
		ModelAndView mv = new ModelAndView();
		userSession = (User) session.getAttribute(Const.SESSION_USER);
		commodityVo.setAreaCode(userSession.getAreaCode());

		List<HajCommodityVo> inventoryList = service.listPageInventory(commodityVo);

		commodityTypeService.putTypeList2MV(mv, commodityVo.getCommodityAttr(), commodityVo.getParentTypeId());

		// 供应商列表
		List<HajSupplyChain> supplyChainNames = supplyChainService.getSupplyChainNames();
		mv.addObject("supplyChainNames", supplyChainNames);

		Criteria criteria = new Criteria();
		Map<String, Object> condition = new HashMap<>();
		condition.put("status", 1);
		condition.put("areaCode", userSession.getAreaCode());
		criteria.setCondition(condition);
		List<HajActivity> activityList = activityService.queryByList(criteria);
		mv.addObject("activityList", activityList);

		mv.addObject("inventoryList", inventoryList);
		mv.addObject("vo", commodityVo);
		mv.setViewName("inventoryList");

		return mv;
	}

	/**
	 * 进入库存修改页面
	 *
	 * @param id
	 * @return
	 * @throws Exception
	 * @author SevenWong<br>
	 * @date 2016年10月8日下午4:43:31
	 */
	@RequestMapping(value = "/inventoryEdit")
	public ModelAndView inventoryEdit(@RequestParam Integer id) throws Exception {
		ModelAndView mv = new ModelAndView();

		HajCommodityVo vo = service.getCommodityById(id);
		mv.addObject("vo", vo);

		commodityTypeService.putTypeList2MV(mv, vo.getCommodityAttr(), vo.getParentTypeId());

		mv.setViewName("inventoryEdit");
		return mv;
	}

	/**
	 * 修改库存及上下架
	 *
	 * @param commodity
	 * @param response
	 * @throws Exception
	 * @author SevenWong<br>
	 * @date 2016年10月8日下午4:21:01
	 */
	@RequestMapping(value = "/updateInventory", method = RequestMethod.POST)
	public void updateInventory(HajCommodity commodity, HttpServletResponse response) throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		if (commodity != null) {
			commodity.setShelves((commodity.getShelves() == null) ? 0 : commodity.getShelves());

			// 库存小于1商品自动下架
			if (commodity.getInventory() != null && commodity.getInventory() < 1) {
				commodity.setShelves(0);
				service.updateBySelective(commodity);
			}

			if (commodity.getShelves() == 0) {
				// 后台手动下架加个标志
				commodity.setShelvesWay(1);
			}

			HajCommodity afterUpdate = service.queryById(commodity.getId());

			//判断商品相关上架信息
			if (commodity.getShelves() == 1) {
				if (!StringUtils.isBlank(afterUpdate.getName()) && !StringUtils.isBlank(afterUpdate.getWeight()) && !StringUtils.isBlank(afterUpdate.getAlias())
						&& !"0".equals(afterUpdate.getSupplyChain()) && !StringUtils.isBlank(afterUpdate.getSupplyChain()) && afterUpdate.getTypeId()!=null
						&& !StringUtils.isBlank(afterUpdate.getSmallPic()) && commodity.getInventory()>0 && !StringUtils.isBlank(afterUpdate.getSku())
						&& afterUpdate.getCostPrice()!=null && afterUpdate.getMarketPrice()!=null && afterUpdate.getPromotionPrice()!=null ) {
					service.updateBySelective(commodity);
					jsonMap.put("error", "0");

					// 上架商品 更新优惠卷商品表
					couponCommodityService.updateCouponCommodity(afterUpdate);

				} else if (StringUtils.isBlank(afterUpdate.getName())) {
					jsonMap.put("error", "1");
					jsonMap.put("msg", "商品名称为空,请修改!");
				} else if (StringUtils.isBlank(afterUpdate.getAlias())) {
					jsonMap.put("error", "1");
					jsonMap.put("msg", "商品别名为空,请修改!");
				} else if (afterUpdate.getTypeId()==null) {
					jsonMap.put("error", "1");
					jsonMap.put("msg", "商品分类属性为空,请修改!");
				} else if (StringUtils.isBlank(afterUpdate.getSupplyChain()) || "0".equals(afterUpdate.getSupplyChain())) {
					jsonMap.put("error", "1");
					jsonMap.put("msg", "商品供应商为空,请修改!");
				} else if (StringUtils.isBlank(afterUpdate.getWeight())) {
					jsonMap.put("error", "1");
					jsonMap.put("msg", "商品规格为空,请修改!");
				} else if (StringUtils.isBlank(afterUpdate.getSmallPic())) {
					jsonMap.put("error", "1");
					jsonMap.put("msg", "商品小图为空,请修改!");
				} else if (commodity.getInventory()<1) {
					jsonMap.put("error", "1");
					jsonMap.put("msg", "商品库存不足,请修改!");
				} else if (StringUtils.isBlank(afterUpdate.getSku())) {
					jsonMap.put("error", "1");
					jsonMap.put("msg", "商品物料编码为空,请修改!");
				} else if (afterUpdate.getCostPrice()==null) {
					jsonMap.put("error", "1");
					jsonMap.put("msg", "商品成本价为空,请修改!");
				} else if (afterUpdate.getMarketPrice()==null) {
					jsonMap.put("error", "1");
					jsonMap.put("msg", "商品市场价为空,请修改!");
				} else if (afterUpdate.getPromotionPrice()==null) {
					jsonMap.put("error", "1");
					jsonMap.put("msg", "商品参考价为空,请修改!");
				}

			} else {
				service.updateBySelective(commodity);
				jsonMap.put("error", "0");
			}

			HajCommodityUtil.resetCommodityRedisAndESIndex(service, commodity.getId());
		} else {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "商品为空!");
		}

		try {
			JSONUtils.printObject(jsonMap, response);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * 后台下单获取商品信息
	 */
	@RequestMapping(value = "/commoidtyByName")
	public void getCommoidtyByName(String name, String areaCode, HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		if (null != name && !"".equals(name)) {
			HashMap<String, String> commodity = service.getCommoidtyByName(name, areaCode);
			if (null != commodity) {
				jsonMap.put("error", "0");
				jsonMap.put("msg", "成功");
			} else {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "失败");
			}
			jsonMap.put("commodity", commodity);
		} else {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "商品名称为空");
		}

		try {
			JSONUtils.printObject(jsonMap, response);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * 将选中的商品添加到指定的促销专区中
	 *
	 * @param commodityId
	 * @param promotionAreaId
	 * @param response
	 * @author SevenWong<br>
	 * @date 2016年10月21日下午4:42:22
	 */
	@RequestMapping(value = "/add2promotionArea")
	public void add2promotionArea(@RequestParam Integer[] commodityId, @RequestParam Integer promotionAreaId,
			HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("error", "success");
		jsonMap.put("msg", "保存成功");
		if (commodityId != null && commodityId.length > 0 && promotionAreaId != null) {
			//封装数据
			List<HajCommodityPromotionArea> hajCommodityPromotionAreas = new ArrayList<HajCommodityPromotionArea>();
			for(Integer id : commodityId ){
				HajCommodityPromotionArea  hcpa = new HajCommodityPromotionArea();
				hcpa.setCommodityId(id);
				hcpa.setAreaId(promotionAreaId);
				hajCommodityPromotionAreas.add(hcpa);
			}
			//批量插入
			service.saveToCommodityZone(hajCommodityPromotionAreas);
			//删除缓存
			service.resetCommodityRedis();
		}

		try {
			JSONUtils.printObject(jsonMap, response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	/**
	 * 批量添加商品到指定专题中
	 * @param commodityId
	 * @param specialTopicId
	 * @param response
	 */
	@RequestMapping(value = "/add2specialTopic")
	public void add2specialTopic(@RequestParam Integer[] commodityId, @RequestParam Integer specialTopicId,
								  HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("error", "success");
		jsonMap.put("msg", "保存成功");
		if (commodityId != null && commodityId.length > 0 && specialTopicId != null) {
			//封装数据
			List<HajSpecialTopicCommodity> hajSpecialTopic = new ArrayList<HajSpecialTopicCommodity>();
			for(Integer id : commodityId ){
				HajSpecialTopicCommodity  htc = new HajSpecialTopicCommodity();
				htc.setCommodityId(id);
				htc.setSpecialTopicId(specialTopicId);
				hajSpecialTopic.add(htc);
			}
			//批量插入
			service.saveToCommodityTopic(hajSpecialTopic);
			//删除缓存
			service.resetCommodityRedis();
		}

		try {
			JSONUtils.printObject(jsonMap, response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	/**
	 * 批量添加商品到活动中
	 * @param commodityId
	 * @param activityId
	 * @param response
	 */
	@RequestMapping(value = "/add2activity")
	public void add2activity(@RequestParam Integer[] commodityId, @RequestParam Integer activityId,
								 HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("error", "success");
		jsonMap.put("msg", "保存成功");
		if (commodityId != null && commodityId.length > 0 && activityId != null) {
			//批量插入
			service.saveToCommodityActivity(commodityId,activityId);
			//删除缓存
			service.resetCommodityRedis();
		}

		try {
			JSONUtils.printObject(jsonMap, response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	/**
	 * 批量添加商品到板块中
	 * @param commodityId
	 * @param plate
	 * @param response
	 */
	@RequestMapping(value = "/add2plate")
	public void add2plate(@RequestParam Integer[] commodityId, @RequestParam String plate,
							 HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("error", "success");
		jsonMap.put("msg", "保存成功");
		if (commodityId != null && commodityId.length > 0 && plate != null) {
			//封装数据
			List<HajCommodity> commodityList = new ArrayList<HajCommodity>();
			for(Integer id : commodityId ){
				//根据商品id,返回商品对象
				HajCommodity hc = service.getCommodity(id);
				if (plate!=null || plate !="") {
					if ("mustBuy".equals(plate)) {
						hc.setMustBuy(1);
					}
					if ("familyPreferences".equals(plate)) {
						hc.setFamilyPreferences(1);
					}
					if ("ishot".equals(plate)) {
						hc.setIshot(1);
					}
				}

				commodityList.add(hc);//需要批量添加的商品
			}
			//批量插入
			service.saveToCommodityPlate(commodityList);
			//删除缓存
			service.resetCommodityRedis();
		}

		try {
			JSONUtils.printObject(jsonMap, response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}




	/**
	 * 将指定的商品从指定的促销专区中移除
	 *
	 * @param commodityId
	 * @param response
	 * @author SevenWong<br>
	 * @date 2016年10月21日下午4:58:33
	 */
	@RequestMapping(value = "/updatePromotionAreaIdTo0")
	public void updatePromotionAreaIdTo0(@RequestParam Integer[] commodityId,@RequestParam Integer promotionAreaId, HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("error", "success");
		jsonMap.put("msg", "保存成功");

		if (commodityId != null && commodityId.length > 0) {
			List<HajCommodityPromotionArea> hajCommodityPromotionAreas = new ArrayList<HajCommodityPromotionArea>();
			//封装数据
			for(Integer id : commodityId){
				HajCommodityPromotionArea hcpa = new HajCommodityPromotionArea();
				hcpa.setCommodityId(id);
				hcpa.setAreaId(promotionAreaId);
				hajCommodityPromotionAreas.add(hcpa);
			}
			//批量删除
			service.deleteCommodityFromTheZone(hajCommodityPromotionAreas);
			//删除缓存
			service.resetCommodityRedis();
		}
		try {
			JSONUtils.printObject(jsonMap, response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	/**
	 * 批量移除指定专题的商品
	 * @param commodityId
	 * @param specialTopicId
	 * @param response
	 */
	@RequestMapping(value = "/updateSpecialTopicIdTo0")
	public void updateSpecialTopicIdTo0(@RequestParam Integer[] commodityId,@RequestParam Integer specialTopicId, HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("error", "success");
		jsonMap.put("msg", "保存成功");

		if (commodityId != null && commodityId.length > 0) {
			List<HajSpecialTopicCommodity> hajSpecialTopicCommodity = new ArrayList<>();
			//封装数据
			for(Integer id : commodityId){
				HajSpecialTopicCommodity hcpa = new HajSpecialTopicCommodity();
				hcpa.setCommodityId(id);
				hcpa.setSpecialTopicId(specialTopicId);
				hajSpecialTopicCommodity.add(hcpa);
			}
			//批量删除
			service.deleteCommodityFromTheTopic(hajSpecialTopicCommodity);
			//删除缓存
			service.resetCommodityRedis();
		}
		try {
			JSONUtils.printObject(jsonMap, response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 批量移除活动中的商品
	 * @param commodityId
	 * @param activityId
	 * @param response
	 */
	@RequestMapping(value = "/updateActivityIdTo0")
	public void updateActivityIdTo0(@RequestParam Integer[] commodityId,@RequestParam Integer activityId, HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("error", "success");
		jsonMap.put("msg", "保存成功");

		if (commodityId != null && commodityId.length > 0 && activityId != null) {
			//批量删除
			service.deleteCommodityFromTheActivity(commodityId,activityId);
			//删除缓存
			service.resetCommodityRedis();
		}
		try {
			JSONUtils.printObject(jsonMap, response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	/**
	 * 批量移除板块中的商品
	 * @param commodityId
	 * @param plate
	 * @param response
	 */
	@RequestMapping(value = "/updateByPlate")
	public void updateByPlate(@RequestParam Integer[] commodityId, @RequestParam String plate,
						  HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("error", "success");
		jsonMap.put("msg", "保存成功");
		if (commodityId != null && commodityId.length > 0 && plate != null ) {
			//封装数据
			List<HajCommodity> commodityList = new ArrayList<>();
			for(Integer id : commodityId ){
				//根据商品id,返回商品对象
				HajCommodity hc = service.getCommodity(id);
				if (plate!=null || plate !="") {
					if ("mustBuy".equals(plate)) {//业主推荐
						hc.setMustBuy(0);
					}
					if ("familyPreferences".equals(plate)) {//新品推荐
						hc.setFamilyPreferences(0);
					}
					if ("ishot".equals(plate)) {//搜索推荐
						hc.setIshot(0);
					}
				}

				commodityList.add(hc);//需要批量移除的商品
			}
			//批量修改
			service.saveToCommodityPlate(commodityList);
			//删除缓存
			service.resetCommodityRedis();
		}

		try {
			JSONUtils.printObject(jsonMap, response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}



	/**
	 * 库存销量导出excel
	 */
	@RequestMapping(value = "/excelInventoryList")
	public void excelInventoryList(HajCommodityVo commodityVo, HttpServletResponse response, HttpServletRequest request) {
		try {
			String filename = "导出销量和库存";
			filename = HttpUtil.encodeFilename(request, filename);
			filename = DateUtil.dateformat(new Date(), "MM-dd") + filename;

			userSession = (User) request.getSession().getAttribute(Const.SESSION_USER);
			commodityVo.setAreaCode(userSession.getAreaCode());

			XSSFWorkbook wb = service.excelInventoryList(commodityVo, false);
			ExcelUtil.output(response, filename, wb);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 库存信息导出
	 */
	@RequestMapping(value = "/excelInventoryListMore")
	public void excelInventoryListMore(HajCommodityVo commodityVo,
									   String passport,
									   HttpServletResponse response,
									   HttpServletRequest request) {
		try {
			String passportConfig = Config.getConfigProperties("update.commodity.price.pwd", "");
			passport = MD5Tools.MD5(passport);

			if (passportConfig.equals(passport)) {
				String filename = "信息导出";
				filename = HttpUtil.encodeFilename(request, filename);
				filename = DateUtil.dateformat(new Date(), "MM-dd") + filename;

				userSession = (User) request.getSession().getAttribute(Const.SESSION_USER);
				commodityVo.setAreaCode(userSession.getAreaCode());

				XSSFWorkbook wb = service.excelInventoryList(commodityVo, true);
				ExcelUtil.output(response, filename, wb);
			} else {
				throw new Exception("通行证错误");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 验证商品价格的通行证是否正确
	 */
	@RequestMapping(value = "/passportAuth/price")
	public void passportAuth(HttpServletResponse response, String passport) {
		Map<String, Object> jsonMap = new HashMap<>();
		jsonMap.put("result", "error");
		try {
			String passportConfig = Config.getConfigProperties("update.commodity.price.pwd", "");
			passport = MD5Tools.MD5(passport);

			if (passportConfig.equals(passport)) {
				jsonMap.put("result", "success");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				JSONUtils.printObject(jsonMap, response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@RequestMapping(value = "/getCommodityListWithoutPage")
	public void getCommodityListWithoutPage(HajCommodityVo commodityVo, HttpServletResponse response,
			HttpSession session) throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();

		userSession = (User) session.getAttribute(Const.SESSION_USER);
		commodityVo.setAreaCode(userSession.getAreaCode());

		List<HajCommodityVo> commodityList = service.listPage(commodityVo);
		jsonMap.put("commodityList", commodityList);

		JSONUtils.printObject(jsonMap, response);
	}

	/**
	 * 批量调整商品状态
	 *
	 * @param ids
	 * @param status
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateStatus")
	public void updateStatus(@RequestParam String[] ids, @RequestParam Integer status, HttpServletResponse response)
			throws Exception {
		Map<String, Object> jsonMap = new HashMap<>();

		if (status != null && ids.length > 0) {

			for (String CommodityId:ids) {

				HajCommodity afterUpdate = service.queryById(CommodityId);

				service.updateShelvesByManual(ids, (status == 1) ? status : 0);

				service.resetCommodityRedis();

				if (status==1) {
					//判断商品相关上架信息
					if (!StringUtils.isBlank(afterUpdate.getName()) && !StringUtils.isBlank(afterUpdate.getWeight())
							&& !StringUtils.isBlank(afterUpdate.getAlias())
							&& !StringUtils.isBlank(afterUpdate.getSupplyChain())
							&& !"0".equals(afterUpdate.getSupplyChain())  && afterUpdate.getTypeId()!=null
							&& !StringUtils.isBlank(afterUpdate.getSmallPic()) && afterUpdate.getInventory()>0
							&& !StringUtils.isBlank(afterUpdate.getSku()) && afterUpdate.getCostPrice()!=null
							&& afterUpdate.getMarketPrice()!=null && afterUpdate.getPromotionPrice()!=null ) {

						service.createCommodityIndex(Integer.valueOf(CommodityId));
						jsonMap.put("error", "0");
						jsonMap.put("msg", "修改上架成功！");

						// 上架商品 更新优惠卷商品表
						couponCommodityService.updateCouponCommodity(afterUpdate);

					} else if (StringUtils.isBlank(afterUpdate.getName())) {
						jsonMap.put("error", "1");
						jsonMap.put("msg","商品编号:["+afterUpdate.getCommodityNo()+"]商品名称为空,请修改!");
						break;
					} else if (StringUtils.isBlank(afterUpdate.getAlias())) {
						jsonMap.put("error", "1");
						jsonMap.put("msg", "商品编号:["+afterUpdate.getCommodityNo()+"]商品别名为空,请修改!");
						break;
					} else if (afterUpdate.getTypeId()==null) {
						jsonMap.put("error", "1");
						jsonMap.put("msg", "商品编号:["+afterUpdate.getCommodityNo()+"]商品分类属性为空,请修改!");
						break;
					} else if (StringUtils.isBlank(afterUpdate.getSupplyChain()) || "0".equals(afterUpdate.getSupplyChain())) {
						jsonMap.put("error", "1");
						jsonMap.put("msg", "商品编号:["+afterUpdate.getCommodityNo()+"]商品供应商为空,请修改!");
						break;
					} else if (StringUtils.isBlank(afterUpdate.getWeight())) {
						jsonMap.put("error", "1");
						jsonMap.put("msg", "商品编号:["+afterUpdate.getCommodityNo()+"]商品规格为空,请修改!");
						break;
					} else if (StringUtils.isBlank(afterUpdate.getSmallPic())) {
						jsonMap.put("error", "1");
						jsonMap.put("msg", "商品编号:["+afterUpdate.getCommodityNo()+"]商品小图为空,请修改!");
						break;
					} else if (afterUpdate.getInventory()<1) {
						jsonMap.put("error", "1");
						jsonMap.put("msg", "商品编号:["+afterUpdate.getCommodityNo()+"]商品库存不足,请修改!");
						break;
					} else if (StringUtils.isBlank(afterUpdate.getSku())) {
						jsonMap.put("error", "1");
						jsonMap.put("msg", "商品编号:["+afterUpdate.getCommodityNo()+"]商品物料编码为空,请修改!");
						break;
					} else if (afterUpdate.getCostPrice()==null) {
						jsonMap.put("error", "1");
						jsonMap.put("msg", "商品编号:["+afterUpdate.getCommodityNo()+"]商品成本价为空,请修改!");
						break;
					} else if (afterUpdate.getMarketPrice()==null) {
						jsonMap.put("error", "1");
						jsonMap.put("msg", "商品编号:["+afterUpdate.getCommodityNo()+"]商品市场价为空,请修改!");
						break;
					} else if (afterUpdate.getPromotionPrice()==null) {
						jsonMap.put("error", "1");
						jsonMap.put("msg", "商品编号:["+afterUpdate.getCommodityNo()+"]商品参考价为空,请修改!");
						break;
					}

				} else{
					service.createCommodityIndex(Integer.valueOf(CommodityId));
					jsonMap.put("error", "0");
					jsonMap.put("msg", "修改下架成功！");
				}

				// 上架商品 更新优惠卷商品表
//				if (afterUpdate.getShelves() == 0 && afterUpdate.getTypeId()!=null) {
//					couponCommodityService.updateCouponCommodity(afterUpdate);
//				}
			}

		} else {
			jsonMap.put("error", "2");
			jsonMap.put("msg", "参数错误！");
		}

		JSONUtils.printObject(jsonMap, response);
	}

	/**
	 * 按需同步所有商品库存
	 */
	@RequestMapping(value = "/wmsInventorySync")
	public void wmsInventorySync(HttpServletResponse response) throws Exception {
		// 1.获取商品列表
		// 2.根据仓库编号及商品sku查询商品库存
		// 3.同步库存到数据库
		Map<String, Object> jsonMap = new HashMap<>();

		// 需要同步库存的商品
		List<String> invtSyncSkuList = service.getInvtSyncSkuList();
		String sku = StringUtils.join(invtSyncSkuList, ',');

		// 仓库编号，目前先使用一个仓库处理，日后可能涉及到多仓对不同小区
		Object warehouseNo = redisSpringProxy.read("SystemConfiguration_warehouse_no");
		List<WmsInventory> invtList = InventorySync.getInvtList(warehouseNo.toString(), sku);

		boolean update = service.wmsInventorySync(invtList);

		if (update) {
			jsonMap.put("error", "0");
			jsonMap.put("msg", "已同步");
		} else {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "同步失败");
		}

		JSONUtils.printObject(jsonMap, response);
	}

	/**
	 * 从 wms 平台获取单个商品库存
	 */
	@RequestMapping(value = "/getWmsSingleInventory")
	public void getWmsSingleInventory(HttpServletResponse response, @RequestParam String sku) throws Exception {
		Map<String, Object> jsonMap = new HashMap<>();

		Object warehouseNo = redisSpringProxy.read("SystemConfiguration_warehouse_no");
		List<WmsInventory> invtList = InventorySync.getInvtList(warehouseNo.toString(), sku);

		if (invtList.size() > 0) {
			jsonMap.put("error", "0");
			jsonMap.put("msg", "成功");
			jsonMap.put("inventory", invtList.get(0).getAvlqty());
		} else {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "获取erp库存失败");
		}

		JSONUtils.printObject(jsonMap, response);
	}


}
