package com.flf.controller;

import com.flf.entity.*;
import com.flf.service.HajAreasService;
import com.flf.service.HajCommodityService;
import com.flf.service.HajCommodityTypeService;
import com.flf.service.HajSupplyChainService;
import com.flf.util.DateUtil;
import com.flf.util.JSONUtils;
import com.flf.util.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>HajSupplyChainAppController<br>
 * <br>
 */
@Controller
@RequestMapping(value = "/supplyChain")
public class HajSupplyChainController {

	private final static String SUPPLY_CHAIN_PREFIX = "GYL";

	@Autowired(required = false)
	private HajSupplyChainService service;

	@Autowired(required = false)
	private HajAreasService areasService;

	@Autowired
	private HajCommodityTypeService commodityTypeService;

	@Autowired
	private HajCommodityService commodityService;

	@RequestMapping(value = "/list")
	public ModelAndView list(HajSupplyChainVo vo) throws Exception {
		ModelAndView mv = new ModelAndView();
		List<HajSupplyChainVo> supplyChainList = service.list(vo);
		mv.addObject("supplyChainList", supplyChainList);

		areasService.putAreaList(mv, vo.getProvinceCode(), vo.getCityCode());

		commodityTypeService.putTypeList2MV(mv, vo.getCommodityAttr(), vo.getParentTypeId());

		mv.addObject("vo", vo);
		mv.setViewName("supplyChainList");
		return mv;
	}



	@RequestMapping(value = "/add")
	public ModelAndView add() throws Exception {
		ModelAndView mv = new ModelAndView();

		String supplyNo = Tools.date2Str(new Date(), "yyMMddHHmmssSSS");
		HajSupplyChainVo vo = new HajSupplyChainVo();
		vo.setSupplyNo(SUPPLY_CHAIN_PREFIX.concat(supplyNo));
		vo.setCreateTime(DateUtil.dateToString(new Date()));
		mv.addObject("vo", vo);

		List<HajAreas> provinceList = areasService.getAreaByPCode("0");

		mv.addObject("provinceList", provinceList);
		mv.setViewName("supplyChainEdit");
		return mv;
	}

	@RequestMapping(value = "/edit")
	public ModelAndView edit(Integer id) throws Exception {
		ModelAndView mv = new ModelAndView();

		HajSupplyChainVo vo = service.getSupplyChainVoById(id);
		mv.addObject("vo", vo);

		// 省份数据列表
		List<HajAreas> areaList = areasService.getAreaByPCode("0");
		mv.addObject("provinceList", areaList);

		areasService.putAreaList(mv, vo.getProvinceCode(), vo.getCityCode());

		commodityTypeService.putTypeList2MV(mv, vo.getCommodityAttr(), vo.getParentTypeId());

		mv.setViewName("supplyChainEdit");
		return mv;
	}

	/**
	 * 保存新增或修改后的供应链信息
	 * 
	 * @param vo
	 * @param out
	 * @throws Exception
	 * @Author SevenWong
	 */
	@RequestMapping(value = "/save")
	public void save(HajSupplyChainVo vo, PrintWriter out) throws Exception {
		String result = "success";
		if (vo != null) {
			// 根据code返回areaId
			HajAreas areaByCode = areasService.getAreaByCode(vo.getCommunityCode());
			if (areaByCode != null) {
				vo.setAreaId(areaByCode.getId());
			}

			if (vo.getId() != null && vo.getId() > 0) {
				service.updateBySelective(vo);
			} else {
				service.add(vo);
			}
		} else {
			result = "error";
		}

		out.print(result);
		out.close();
	}

	/**
	 * 批量删除供应商
	 * 
	 * @param id
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public void delete(Integer[] id, HttpServletResponse response) throws Exception {
		for (Integer supplyChainId : id) {
			service.delete(supplyChainId);
		}

		JSONUtils.printObject("{status: 'success'}", response);
	}

	/**
	 * 批量更新供应商状态
	 * 
	 * @param id
	 * @param status
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateStatus")
	public void updateStatus(@RequestParam Integer[] id, @RequestParam Integer status, HttpServletResponse response)
			throws Exception {
		HajSupplyChain chain;
		for (Integer supplyChainId : id) {
			chain = new HajSupplyChain();
			chain.setId(supplyChainId);
			chain.setStatus(status);
			service.updateBySelective(chain);
		}

		JSONUtils.printObject("{status: 'success'}", response);
	}

	@RequestMapping(value = "/info")
	public ModelAndView info(Integer id) throws Exception {
		ModelAndView mv = new ModelAndView();

		HajSupplyChainVo vo = service.getSupplyChainVoById(id);
		mv.addObject("vo", vo);

		List<HajCommodityVo> commodityVoList = commodityService.getBySupplyChainId(id);
		mv.addObject("commodityVoList", commodityVoList);
		mv.setViewName("supplyChainInfo");

		return mv;
	}

}
