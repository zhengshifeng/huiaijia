package com.flf.controller;

import com.base.util.HttpUtil;
import com.flf.entity.HajCommodityWastageRate;
import com.flf.entity.HajCommodityWastageRateVO;
import com.flf.service.HajCommodityTypeService;
import com.flf.service.HajCommodityWastageRateService;
import com.flf.util.DateUtil;
import com.flf.util.ExcelUtil;
import com.flf.util.JSONUtils;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.*;

/**
 * 
 * <br>
 * <b>功能：</b>HajCommodityWastageRateController<br>
 * <br>
 */
@Controller
@RequestMapping(value = "/hajCommodityWastageRate")
public class HajCommodityWastageRateController {

	private final static Logger log = Logger.getLogger(HajCommodityWastageRateController.class);

	@Autowired(required = false)
	private HajCommodityWastageRateService service;

	@Autowired(required = false)
	private HajCommodityTypeService commodityTypeService;

	@RequestMapping(value = "/list")
	public ModelAndView list(HajCommodityWastageRateVO vo) {
		ModelAndView mv = new ModelAndView();
		List<HashMap<String, Object>> commodityWastageRateList = service.listPage(vo);

		commodityTypeService.putTypeList2MV(mv, vo.getCommodityAttr(), vo.getParentTypeId());

		mv.addObject("vo", vo);

		// 获得昨天的日期，日期选择不得大于昨天
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		mv.addObject("now", DateUtil.date2Str(cal.getTime()));
		mv.addObject("commodityWastageRateList", commodityWastageRateList);
		mv.setViewName("commodityWastageRateList");
		return mv;
	}

	@RequestMapping(value = "/add")
	public ModelAndView add() throws Exception {
		ModelAndView mv = new ModelAndView();

		mv.setViewName("");
		return mv;
	}

	@RequestMapping(value = "/edit")
	public ModelAndView edit(@RequestParam Integer id) throws Exception {
		ModelAndView mv = new ModelAndView();

		mv.setViewName("");
		return mv;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public void save(HajCommodityWastageRate po, HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<>();
		jsonMap.put("status", "error");
		jsonMap.put("rate", "未知异常");
		try {
			if (po != null) {
				if (po.getOutputWeight() != null && po.getOutputWeight().compareTo(BigDecimal.ZERO) > 0) {
					// 损耗率% = （出库重量 - 包装净重）/包装净重*100%
					po.setWastageRate(//
							po.getOutputWeight().subtract(po.getGrossWeight())//
									.divide(po.getGrossWeight(), 2, BigDecimal.ROUND_DOWN)//
									.multiply(new BigDecimal("100")));

					if (po.getId() == null) {
						service.add(po);
					} else {
						HajCommodityWastageRate pojo = new HajCommodityWastageRate();
						pojo.setId(po.getId());
						pojo.setOutputWeight(po.getOutputWeight());
						pojo.setWastageRate(po.getWastageRate());

						service.updateBySelective(po);
					}
					jsonMap.put("status", "success");
					jsonMap.put("rate", po.getWastageRate());
				}
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

	@RequestMapping(value = "/delete")
	public void delete(Integer[] id, PrintWriter out) throws Exception {
		out.print("success");
		out.close();
	}

	@RequestMapping(value = "/export2excel")
	public void export2excel(HajCommodityWastageRateVO vo, HttpServletResponse response, HttpServletRequest request) {
		try {
			String filename = "自主加工损耗表";
			filename = HttpUtil.encodeFilename(request, filename);
			filename = DateUtil.dateformat(new Date(), "MM-dd") + filename;

			XSSFWorkbook wb = service.export2excel(vo);
			ExcelUtil.output(response, filename, wb);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
