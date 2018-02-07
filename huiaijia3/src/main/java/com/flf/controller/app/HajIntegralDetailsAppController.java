package com.flf.controller.app;

import com.flf.entity.HajIntegralDetails;
import com.flf.entity.Page;
import com.flf.service.HajIntegralDetailsService;
import com.flf.service.RedisSpringProxy;
import com.flf.util.JSONUtils;
import com.flf.util.MD5Tools;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <br>
 * <b>功能：</b>HajIntegralDetailsAppController<br>
 * <br>
 */
@Controller
@RequestMapping(value = "/integralDetails/app/")
public class HajIntegralDetailsAppController {

	private final static Logger log = Logger.getLogger(HajIntegralDetailsAppController.class);

	@Autowired(required = false)
	private HajIntegralDetailsService service;

	@Autowired(required = false)
	private RedisSpringProxy redisSpringProxy;

	/**
	 * 为APP提供积分明细接口
	 * @param pageNumber 当前页
	 */
	@RequestMapping(value = "/list")
	public void list(HttpServletResponse response,
					 @RequestParam String sign,
					 @RequestParam Integer userId,
					 @RequestParam Integer pageNumber) {
		Map<String, Object> jsonMap = new HashMap<>();
		jsonMap.put("error", "0");
		jsonMap.put("msg", "成功");
		List<HashMap<String, Object>> list = new ArrayList<>();
		try {
			if (MD5Tools.checkSign(sign)) {
				// userID必须传有效值
				if (userId != null && userId > 0) {
					// 过滤的条件
					HajIntegralDetails dto = new HajIntegralDetails();

					// 分页数据
					Page page = new Page();
					page.setCurrentPage(pageNumber);

					dto.setPage(page);
					dto.setUserId(userId);

					/*String redisKey = "integralDetails_app_list_" + userId + "_" + pageNumber;
					if (redisSpringProxy.read(redisKey) != null) {
						log.info("APP积分明细(redis)...");
						list = (List<HashMap<String, Object>>) redisSpringProxy.read(redisKey);
					} else {
						log.info("APP积分明细(db)...");
						list = service.listPage4app(dto);
						redisSpringProxy.save(redisKey, list);
					}*/
					list = service.listPage4app(dto);

					// 当第一页的数据为空时，则记录历史积分
					if (pageNumber < 2 && list.size() == 0) {
						HajIntegralDetails integralDetails = new HajIntegralDetails();
						integralDetails.setUserId(userId);
						integralDetails.setDetail("0");
						service.saveDetail(integralDetails);

						// 然后重新查一次
						list = service.listPage4app(dto);
					}
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
				jsonMap.put("list", list);
				JSONUtils.printObject(jsonMap, response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
