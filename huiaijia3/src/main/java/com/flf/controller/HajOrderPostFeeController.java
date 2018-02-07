package com.flf.controller;

import com.base.util.HttpUtil;
import com.flf.controller.app.HajOrderPostFeeAppController;
import com.flf.entity.HajOrderPostFee;
import com.flf.entity.User;
import com.flf.service.HajOrderPostFeeService;
import com.flf.util.Const;
import com.flf.util.DateUtil;
import com.flf.util.ExcelUtil;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 
 * <br>
 * <b>功能：</b>HajOrderPostFeeAppController<br>
 * <br>
 */
@Controller
@RequestMapping(value = "/orderPostFee")
public class HajOrderPostFeeController {

	private final static Logger log = Logger.getLogger(HajOrderPostFeeAppController.class);
	@Autowired(required = false)
	private HajOrderPostFeeService hajOrderPostFeeService;

	/**
	 * 查询配送费支付情况
	 * 
	 */
	@RequestMapping(value = "/getAll")
	public String list(HttpSession session, Model model, HajOrderPostFee postFee) {
		User user = (User) session.getAttribute(Const.SESSION_USER);
		if (null != user) {
			postFee.setAreaCode(user.getAreaCode());
		}
		//按条件查询配送费集合
		List<Map<String, Object>> saleList = hajOrderPostFeeService.listPageHajOrderPostFee(postFee);
		//按条件查询费送费已支付总额,未支付总额及后台取消总额
		Map<String, BigDecimal> totalsMap = hajOrderPostFeeService.getPostFeeSumByGroup(postFee);
		model.addAttribute("totalsMap",totalsMap);
		model.addAttribute("postFeeList", saleList);
		model.addAttribute("postFee", postFee);
		return "postFeelist";
	}

	/**
	 * 导出配送费
	 */
	@RequestMapping(value = "/excelOrderPostFee")
	public void excelOrderPostFee(HttpSession session, HajOrderPostFee postFee, HttpServletResponse response,
			HttpServletRequest request) {
		try {
			String filename = "配送费";
			filename = HttpUtil.encodeFilename(request, filename);
			filename = DateUtil.dateformat(new Date(), "MM-dd") + filename;

			User user = (User) session.getAttribute(Const.SESSION_USER);
			if (null != user) {
				postFee.setAreaCode(user.getAreaCode());
			}

			XSSFWorkbook wb = hajOrderPostFeeService.excelOrderPostFee(postFee);
			ExcelUtil.output(response, filename, wb);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
