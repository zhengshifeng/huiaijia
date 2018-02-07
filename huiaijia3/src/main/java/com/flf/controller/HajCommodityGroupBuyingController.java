package com.flf.controller;

import com.flf.entity.HajCommodity;
import com.flf.entity.HajCommodityGroupBuyingVo;
import com.flf.service.HajCommodityGroupBuyingService;
import com.flf.service.HajCommodityService;
import com.flf.util.FileUpload;
import com.flf.util.JSONUtils;
import com.flf.util.Tools;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * <br>
 * <b>功能：</b>HajCommodityGroupBuyingController<br>
 * <br>
 */ 
@Controller
@RequestMapping(value = "/commodityGroupBuying")
public class HajCommodityGroupBuyingController {
	
	private final static Logger log = Logger.getLogger(HajCommodityGroupBuyingController.class);
	
	@Autowired(required = false)
	private HajCommodityGroupBuyingService service;

	@Autowired(required = false)
	private HajCommodityService commodityService;

	@RequestMapping(value = "/edit")
	public ModelAndView edit(@RequestParam Integer commodityId) throws Exception {
		ModelAndView mv = new ModelAndView();

		// 因商品团购表示新增的表，还没有商品数据
		// 因此此处查询不直接查询商品表中的数据，而是查询商品团购表中的数据，待提交后再判断团购表中是需要新增还是修改数据
		HajCommodityGroupBuyingVo vo = service.getByCommodityId(commodityId);

		// 单独请求商品数据，为了显示商品名
		HajCommodity commodity = commodityService.queryById(commodityId);

		if (vo == null) {
			vo = new HajCommodityGroupBuyingVo();
			vo.setCommodityId(commodityId);
		} else {
			if (Tools.isNotEmpty(vo.getSliderPic())) {
				vo.setSliderPicArr(vo.getSliderPic().split(","));
			}
			if (Tools.isNotEmpty(vo.getDetailPic())) {
				vo.setDetailPicArr(vo.getDetailPic().split(","));
			}
		}

		mv.addObject("vo", vo);
		mv.addObject("commodity", commodity);
		mv.setViewName("commodityGroupBuying");
		return mv;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public void save(HajCommodityGroupBuyingVo vo, PrintWriter out) throws Exception {
		String result = "success";
		if (vo != null) {
			if (vo.getSliderPicArr() != null && vo.getSliderPicArr().length > 0) {
				StringBuilder sliderPic = new StringBuilder();
				for (int i = 0; i < vo.getSliderPicArr().length; i++) {
					sliderPic.append(vo.getSliderPicArr()[i]);
					if (i < vo.getSliderPicArr().length) {
						sliderPic.append(",");
					}
				}
				vo.setSliderPic(sliderPic.toString());
			}

			if (vo.getDetailPicArr() != null && vo.getDetailPicArr().length > 0) {
				StringBuilder detailPic = new StringBuilder();
				for (int i = 0; i < vo.getDetailPicArr().length; i++) {
					detailPic.append(vo.getDetailPicArr()[i]);
					if (i < vo.getDetailPicArr().length) {
						detailPic.append(",");
					}
				}
				vo.setDetailPic(detailPic.toString());
			}

			if (vo.getId() == null) {
				service.add(vo);
			} else {
				service.updateBySelective(vo);
			}
		} else {
			result = "error";
		}

		out.print(result);
		out.close();
	}

	@RequestMapping(value = "/multiUploadFile", method = RequestMethod.POST)
	public void multiUploadFile(@RequestParam("fileName") MultipartFile[] files, HttpServletRequest request,
								HttpServletResponse response) throws Exception {
		response.setContentType("application/json;charset=UTF-8");

		// 手动上传图片默认保存在同一目录下，然后更新文件后缀版本号
		String subPath = "images" + File.separator + "group_buying" + File.separator;
		List<String> filePath = new ArrayList<>();
		for (MultipartFile file : files) {
			int imageWidth = 0;
			int imageHeight = 0;
			BufferedImage image = ImageIO.read(file.getInputStream());
			if (image != null) {
				imageWidth = image.getWidth();//获取图片宽度，单位px
				imageHeight = image.getHeight();//获取图片高度，单位px
			}
			filePath.add(FileUpload.uploadFile(file, subPath, request) + "?w=" + imageWidth + "&h=" + imageHeight);
		}
		Map<String, Object> result = new HashMap<>();
		if (filePath.size() > 0) {
			result.put("filePath", filePath);
			result.put("result", "success");
		} else {
			result.put("result", "error");
		}
		JSONUtils.printObject(result, response);
	}

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
		String subPath = "images" + File.separator + "group_buying" + File.separator;
		String filePath = FileUpload.uploadFile(file, subPath, request);
		Map<String, Object> result = new HashMap<>();
		if (Tools.isNotEmpty(filePath)) {
			filePath = filePath + "?w=" + imageWidth + "&h=" + imageHeight;

			result.put("filePath", filePath);
			result.put("result", "success");
		} else {
			result.put("result", "error");
		}
		JSONUtils.printObject(result, response);
	}
}
