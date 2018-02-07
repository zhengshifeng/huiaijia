package com.flf.controller;

import com.base.controller.BaseController;
import com.base.criteria.Criteria;
import com.base.util.HttpUtil;
import com.flf.entity.*;
import com.flf.service.*;
import com.flf.util.*;
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
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by SevenWong on 2017/9/4 16:17
 */
@Controller
@RequestMapping(value = "/commodityPrice")
public class HajCommodityPriceController extends BaseController{

	private static final Logger log = Logger.getLogger(HajCommodityController.class);

	@Autowired(required = false)
	private HajCommodityPriceService service;

	@Autowired(required = false)
	private HajCommodityService commodityService;

	@Autowired(required = false)
	private HajAreasService areasService;

	@Autowired(required = false)
	private HajCommodityTypeService commodityTypeService;

	@Autowired(required = false)
	private HajSupplyChainService supplyChainService;

	@Autowired(required = false)
	private HajCommodityBrandService brandService;

	@Autowired(required = false)
	private HajActivityService activityService;

	@RequestMapping(value = "/list")
	public ModelAndView list(HajCommodityVo vo, HttpSession session) throws Exception {
		ModelAndView mv = new ModelAndView();

		userSession = (User) session.getAttribute(Const.SESSION_USER);
		vo.setAreaCode(userSession.getAreaCode());

		List<HajCommodityVo> commodityList = commodityService.listPage(vo);

		commodityTypeService.putTypeList2MV(mv, vo.getCommodityAttr(), vo.getParentTypeId());

		mv.addObject("commodityList", commodityList);
		mv.addObject("vo", vo);
		mv.addObject("cityList", areasService.listCity());

		List<HajSupplyChain> supplyChainNames = supplyChainService.getSupplyChainNames();
		mv.addObject("supplyChainNames", supplyChainNames);

		// 商品品牌列表
		List<HajCommodityBrand> brandList = brandService.getAllBrands();
		mv.addObject("brandList", brandList);
		//供应商列表
		List<HajSupplyChain> supplyChainList  = supplyChainService.getSupplyChainNames();
		mv.addObject("supplyChainList",supplyChainList);

		Criteria criteria = new Criteria();
		Map<String, Object> condition = new HashMap<>();
		condition.put("status", 1);
		condition.put("areaCode", userSession.getAreaCode());
		criteria.setCondition(condition);
		List<HajActivity> activityList = activityService.queryByList(criteria);
		mv.addObject("activityList", activityList);

		mv.setViewName("commodityPriceList");

		return mv;
	}

	@RequestMapping(value = "/edit")
	public ModelAndView edit(@RequestParam Integer id) throws Exception {
		ModelAndView mv = new ModelAndView();

		HajCommodityVo vo = commodityService.getCommodityById(id);

		commodityTypeService.putTypeList2MV(mv, vo.getCommodityAttr(), vo.getParentTypeId());

		List<HajSupplyChain> supplyChainNames = supplyChainService.getSupplyChainNames();

		mv.addObject("vo", vo);
		mv.addObject("supplyChainNames", supplyChainNames);
		mv.addObject("cityList", areasService.listCity());

		mv.setViewName("commodityPriceEdit");
		return mv;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public void save(HajCommodity commodity, String passport, HttpServletResponse response) {
		Map<String, Object> returnMap = new HashMap<>();
		returnMap.put("error", "0");
		returnMap.put("msg", "success");
		try {
			if (commodity != null) {
				HajCommodity beforeUpdate = commodityService.queryById(commodity.getId());
				boolean legalPromotionPrice = true;

				if (beforeUpdate.getActivityId() != null && beforeUpdate.getActivityId() > 0) {
					if (commodity.getPromotionPrice() == null || commodity.getPromotionPrice().compareTo(BigDecimal.ZERO) <= 0) {
						commodity.setPromotionPrice(BigDecimal.ZERO);
						// 如果商品在活动中，并且活动价设置为0，则需要验证通行证
						String passportConfig = Config.getConfigProperties("update.commodity.price.pwd", "");
						passport = MD5Tools.MD5(passport);
						legalPromotionPrice = passportConfig.equals(passport);
					}
				}

				// 当价格不符合规定时，验证用户输入的通行证是否正确
				boolean legalPrice = HajCommodityUtil.checkPriceWithBeforeData(commodity, beforeUpdate);
				if (!legalPrice) {
					String passportConfig = Config.getConfigProperties("update.commodity.price.pwd", "");
					passport = MD5Tools.MD5(passport);
					legalPrice = passportConfig.equals(passport);
				}

				if (legalPromotionPrice && legalPrice) {
					// 为了避免从前台传其他字段，这里重新定义一个对象，只修改这个功能允许修改的字段
					HajCommodity updateCommodity = new HajCommodity();
					updateCommodity.setId(commodity.getId());
					updateCommodity.setMarketPrice(commodity.getMarketPrice());
					updateCommodity.setOriginalPrice(commodity.getOriginalPrice());
					updateCommodity.setCostPrice(commodity.getCostPrice());
					updateCommodity.setPromotionPrice(commodity.getPromotionPrice());
					commodityService.updateBySelective(updateCommodity);

					HajCommodityUtil.resetCommodityRedisAndESIndex(commodityService, commodity.getId());
				} else {
					returnMap.put("error", "2");
					returnMap.put("msg", "价格不符合规定，请输入正确的通行证");
				}
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

	@RequestMapping(value = "/batchUpdate", method = RequestMethod.POST)
	public void batchUpdate(HttpServletRequest request, HttpServletResponse response,
							@RequestParam("fileName") MultipartFile file,@RequestParam String passport) throws Exception {
		response.setContentType("application/json;charset=UTF-8");
		Map<String, Object> resultMap = new HashMap<>();
		String passportConfig = Config.getConfigProperties("update.commodity.price.pwd", "");
		String subPath = "xlsx" + File.separator + "commodity" + File.separator;
		String filePath = FileUpload.uploadExcel(file, subPath);
		if (passportConfig.equals(MD5.MD5Encode(passport))) {

			String result = service.batchUpdate(filePath, request);
			log.info("商品价格批量修改结果: " + result);

			if (Objects.equals(result, "success")) {
				HajCommodityUtil.resetCommodityRedisAndESIndex(commodityService);
				resultMap.put("result", result);
			} else {
				resultMap.put("result", result);
			}
		} else {
			resultMap.put("error", "1");
			resultMap.put("msg", "通行证验证失败！");
			log.info(resultMap.get("msg"));
		}

		JSONUtils.printObject(resultMap, response);
	}

	@RequestMapping(value = "/export")
	public void exportCommodity(HajCommodityVo vo, HttpServletRequest request, HttpServletResponse response,
								HttpSession session) throws IOException {
		userSession = (User) session.getAttribute(Const.SESSION_USER);
		vo.setAreaCode(userSession.getAreaCode());

		String filename = HttpUtil.encodeFilename(request, "商品价格表");
		XSSFWorkbook wb = service.export(vo);
		ExcelUtil.output(response, filename, wb);
	}

}
