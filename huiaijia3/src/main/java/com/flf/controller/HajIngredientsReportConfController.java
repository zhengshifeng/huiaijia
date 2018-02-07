package com.flf.controller;

import com.flf.entity.HajIngredientsReportConf;
import com.flf.entity.HajIngredientsReportConfVo;
import com.flf.service.HajIngredientsReportConfService;
import com.flf.service.RedisSpringProxy;
import com.flf.util.JSONUtils;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * <br>
 * <b>功能：</b>HajIngredientsReportConfController<br>
 * <br>
 */ 
@Controller
@RequestMapping(value = "/ingredientsReportConf")
public class HajIngredientsReportConfController {
	
	private final static Logger log = Logger.getLogger(HajIngredientsReportConfController.class);
	
	@Autowired(required = false)
	private HajIngredientsReportConfService confService;


	/**
	 * 检测报告分类保存
	 * @param confVo
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveConf", method = RequestMethod.POST)
	public void saveConf(HajIngredientsReportConfVo confVo, HttpServletResponse response,Integer reportId) throws Exception {

		Map<String, Object> returnMap = new HashMap<>();
		returnMap.put("error", "0");

		try {

			if (confVo.getConfImgs() != null && confVo.getConfImgs().length > 0) {
				StringBuilder cateimgs = new StringBuilder();
				for (int i = 0; i < confVo.getConfImgs().length; i++) {
					cateimgs.append(confVo.getConfImgs()[i]);
					if (i < confVo.getConfImgs().length) {
						cateimgs.append(",");
					}
				}
				confVo.setCateimgs(cateimgs.toString());
			}

			if (confVo.getId() != null && confVo.getId() > 0) {
				confService.updateBySelective(confVo);		//更新信息
			}
			else {
				confVo.setRepid(reportId);
				confService.add(confVo);					//保存信息
			}

		} catch (Exception e) {
			e.printStackTrace();
			returnMap.put("error", "1");
		} finally {
			try {
				JSONUtils.printObject(returnMap, response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}





	@RequestMapping(value = "/list")
	public ModelAndView list(HajIngredientsReportConf vo) {
		ModelAndView mv = new ModelAndView();

		List<HajIngredientsReportConf> list = confService.listPage(vo);
		mv.addObject("vo", vo);
		mv.addObject("list", list);
		mv.setViewName("hajIngredientsReportConfList");

		return mv;
	}


	@RequestMapping(value = "/addConf")
	public ModelAndView add() throws Exception {
		ModelAndView mv = new ModelAndView();

		mv.setViewName("addIngredientsReportConf");
		return mv;
	}


	/**
	 * 检测报告保存
	 * @param po
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public void save(HajIngredientsReportConf po, PrintWriter out) throws Exception {
		String result = "success";
		if (po != null) {
			if (po.getId() == null) {
				confService.add(po);
			} else {
				confService.updateBySelective(po);
			}

		} else {
			result = "error";
		}

		out.print(result);
		out.close();
	}

	@RequestMapping(value = "/delete")
	public void delete(Integer[] id, PrintWriter out) throws Exception {
		confService.delete((Object[]) id);
		out.print("success");
		out.close();
	}
}
