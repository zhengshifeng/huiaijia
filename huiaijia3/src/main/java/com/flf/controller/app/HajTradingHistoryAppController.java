package com.flf.controller.app;

import com.base.criteria.Criteria;
import com.flf.entity.HajTradingHistory;
import com.flf.service.HajTradingHistoryService;
import com.flf.util.JSONUtils;
import com.flf.util.MD5Tools;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * <br>
 * <b>功能：</b>HajTradingHistoryAppController<br>
 * <br>
 */
@Controller
@RequestMapping(value = "/hajTradingHistory")
public class HajTradingHistoryAppController {

	private final static Logger log = Logger.getLogger(HajTradingHistoryAppController.class);

	@Autowired(required = false)
	private HajTradingHistoryService hajTradingHistoryService;

	/**
	 * 获取用户交易历史记录
	 * 
	 * @author SevenWong<br>
	 * @date 2016年9月30日上午10:51:10
	 * @param userId
	 * @param sign
	 * @param currentPage
	 * @param response
	 */
	@RequestMapping(value = "/getTradingHistory")
	public void getTradingHistoryByUserId(@RequestParam String userId, @RequestParam String sign,
			@RequestParam Integer currentPage, HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		Criteria criteria = new Criteria();
		criteria.setCurrentPage(currentPage);

		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("userId", userId);
		criteria.setCondition(condition);

		log.info("进入获取用户交易历史记录app接口");
		try {
			// 签名认证
			if (MD5Tools.checkSign(sign)) {
				// 修改订单状态
				List<HajTradingHistory> tradingList = hajTradingHistoryService.getTradingHistoryByUserId(criteria);

				if (tradingList.size() > 0) {
					jsonMap.put("error", "0");
					jsonMap.put("msg", "成功");
					jsonMap.put("tradingList", tradingList);
				} else {
					jsonMap.put("error", "0");
					jsonMap.put("msg", "为空");
					jsonMap.put("tradingList", "");
				}
			} else {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "签名异常");
			}
		} catch (Exception e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
			log.info(e.getMessage(), e);
		} finally {
			try {
				JSONUtils.printObject(jsonMap, response);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

}
