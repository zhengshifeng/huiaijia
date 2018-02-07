package com.flf.controller;

import com.base.util.HttpUtil;
import com.flf.entity.HajOrderProblem;
import com.flf.entity.User;
import com.flf.service.HajOrderProblemService;
import com.flf.util.*;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * <br>
 * <b>功能：</b>HajOrderProblemController<br>
 * <br>
 */
@Controller
@RequestMapping(value = "/hajOrderProblem")
public class HajOrderProblemController {

	private final static Logger log = Logger.getLogger(HajOrderProblemController.class);

	@Autowired(required = false)
	private HajOrderProblemService service;

	@RequestMapping(value = "/list")
	public ModelAndView list(HttpSession session, HajOrderProblem vo) {
		ModelAndView mv = new ModelAndView();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		if (null != user) {
			vo.setAreaCode(user.getAreaCode());
		}
		List<Map<String, Object>> hajOrderProblemList = service.listPageOrderProblem(vo);
		mv.addObject("hajOrderProblem", vo);
		mv.addObject("hajOrderProblemList", hajOrderProblemList);
		mv.setViewName("hajOrderProblemList");

		return mv;
	}

	@RequestMapping(value = "/add")
	public ModelAndView add() throws Exception {
		ModelAndView mv = new ModelAndView();

		mv.setViewName("hajOrderProblemEdit");
		return mv;
	}

	@RequestMapping(value = "/edit")
	public ModelAndView edit(@RequestParam Integer id) throws Exception {
		ModelAndView mv = new ModelAndView();
		HajOrderProblem vo = service.queryById(id);
		mv.addObject("vo", vo);
		mv.setViewName("hajOrderProblemEdit");
		return mv;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public void save(HajOrderProblem po, PrintWriter out) throws Exception {
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

	@RequestMapping(value = "/excelOrderProblem")
	public void excelOrderProblem(HttpSession session, HajOrderProblem orderVo, HttpServletResponse response,
			HttpServletRequest request) {
		try {
			String filename = "订单问题列表";
			filename = HttpUtil.encodeFilename(request, filename);
			filename = DateUtil.dateformat(new Date(), "MM-dd") + filename;

			User user = (User) session.getAttribute(Const.SESSION_USER);
			if (null != user) {
				orderVo.setAreaCode(user.getAreaCode());
			}

			XSSFWorkbook wb = service.excelOrderProblem(orderVo);
			ExcelUtil.output(response, filename, wb);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public void uploadFile(@RequestParam("fileName") MultipartFile file, HttpServletRequest request,
						   HttpServletResponse response) throws Exception {
		response.setContentType("application/json;charset=UTF-8");

		String subPath = "order_problem" + File.separator //
				+ DateUtil.date2Str(new Date(), false) + File.separator;

		String filePath = FileUpload.uploadFile(file, subPath, request);
		Map<String, Object> result = new HashMap<>();
		if (!"".equals(filePath)) {
			result.put("filePath", filePath);
			result.put("result", "success");
		} else {
			result.put("result", "error");
		}
		JSONUtils.printObject(result, response);
	}

	@RequestMapping(value = "/orderProblemPicUpload", method = RequestMethod.POST)
	public void orderProblemPicUpload(@RequestParam("fileName") MultipartFile file,
									  HttpServletRequest request,
									  HttpServletResponse response,
									  @RequestParam Integer id
										) throws Exception{
		response.setContentType("application/json;charset=UTF-8");
		//生成文件保存路径
		String subPath = "order_problem" + File.separator //
				+ DateUtil.date2Str(new Date(), false) + File.separator;
		//保存文件
		String filePath = FileUpload.uploadFile(file, subPath, request);
		Map<String, Object> result = new HashMap<>();
		if (!"".equals(filePath)) {
			if(id != null){
				//将路径保存到数据库中
				HajOrderProblem orderProblem = new HajOrderProblem();
				orderProblem.setPic(filePath);
				orderProblem.setId(id);
				service.updateBySelective(orderProblem);
				result.put("result", "success");
				result.put("filePath", filePath);
			}else{
				log.info("问题订单凭证上传，订单编号为null");
				result.put("result", "error");
			}

		} else {
			result.put("result", "error");
		}
		JSONUtils.printObject(result, response);
	}
}
