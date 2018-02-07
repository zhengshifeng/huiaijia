package com.flf.controller.app;

import com.flf.entity.HajActivity;
import com.flf.service.HajActivityService;
import com.flf.service.RedisSpringProxy;
import com.flf.util.JSONUtils;
import com.flf.util.MD5Tools;
import com.flf.util.Tools;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * <br>
 * <b>功能：</b>HajActivityAppController<br>
 * <br>
 */
@Controller
@RequestMapping(value = "/hajActivity")
public class HajActivityAppController {

	private final static Logger log = Logger.getLogger(HajActivityAppController.class);

	@Autowired(required = false)
	private RedisSpringProxy redisSpringProxy;

	@Autowired(required = false)
	private HajActivityService hajActivityService;

	/**
	 * 充值领取一元购活动资格后，更新活动表useNumber字段接口
	 * 
	 */
	@RequestMapping(value = "/updateHajActivity", method = RequestMethod.GET)
	@ResponseBody
	@Deprecated
	public void updateHajActivity(@RequestParam String sign, @RequestParam Integer id, HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			if (MD5Tools.checkSign(sign)) {
				if (id != null && id > 0) {
					HajActivity hajActivity = hajActivityService.queryById(id);
					int number = hajActivity.getNumber();
					if (hajActivity.getUseNumber() < number) {
						HajActivity hajActivityUd = new HajActivity();
						hajActivityUd.setId(id);
						hajActivityUd.setUseNumber(hajActivity.getUseNumber() + 1);
						hajActivityService.updateBySelective(hajActivityUd);
						jsonMap.put("error", "0");
						jsonMap.put("msg", "成功");
					} else {
						jsonMap.put("error", "0");
						jsonMap.put("msg", "活动份数已领完");
					}
				} else {
					jsonMap.put("error", "1");
					jsonMap.put("msg", "不存在该活动");
				}
			} else {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "签名异常");
			}
		} catch (Exception e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
			try {
				JSONUtils.printObject(jsonMap, response);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}

	}

	/**
	 * .判断是否可以领取一元购活动的名额
	 */
	@RequestMapping(value = "judgeQualification", method = RequestMethod.GET)
	@ResponseBody
	@Deprecated
	public void judgeNumberAndUseNumber(@RequestParam String sign, @RequestParam Integer id,
			HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			if (MD5Tools.checkSign(sign)) {
				if (id != null && id > 0) {
					HajActivity hajActivity = hajActivityService.queryById(id);
					int number = hajActivity.getNumber();
					int userNumber = hajActivity.getUseNumber();
					if (userNumber < number) {
						jsonMap.put("error", "0");
						jsonMap.put("msg", "可以领取");
						jsonMap.put("flag", true);
					} else {
						jsonMap.put("error", "0");
						jsonMap.put("msg", "不可以领取");
						jsonMap.put("flag", false);
					}
				} else {
					jsonMap.put("error", "1");
					jsonMap.put("msg", "不存在该活动");
				}
				JSONUtils.printObject(jsonMap, response);
			} else {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "签名异常");
				JSONUtils.printObject(jsonMap, response);
			}
		} catch (Exception e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
			try {
				JSONUtils.printObject(jsonMap, response);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}

	/**
	 * 查询活动购买数量限制
	 * 
	 * @param sign
	 * @param response
	 */
	@RequestMapping(value = "/getActivityNumber")
	public void getActivityNumber(@RequestParam String sign, String areaCode, HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<>();
		try {
			if (MD5Tools.checkSign(sign)) {
				areaCode = Tools.getAreaCode(areaCode);
				HajActivity hajActivity = hajActivityService.queryActivityByName("1元购", areaCode);
				int number = 0;
				if (null != hajActivity && 1 == hajActivity.getStatus()) {
					number = hajActivity.getNumber();
					jsonMap.put("error", "0");
					jsonMap.put("msg", "成功");
				} else {
					jsonMap.put("error", "0");
					jsonMap.put("msg", "失败");
				}
				jsonMap.put("number", number);
			} else {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "签名异常");
			}
		} catch (Exception e) {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "未知异常");
			e.printStackTrace();
		} finally {
			try {
				JSONUtils.printObject(jsonMap, response);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * 获取首页banner活动
	 */
	@RequestMapping(value = "/bannerActivityList")
	public void bannerActivityList(@RequestParam String sign, String areaCode, HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<>();
		try {
			if (MD5Tools.checkSign(sign)) {
				String redisKey = "bannerActivityList_" + areaCode;

				List<Map<String, Object>> activityList;
				if (redisSpringProxy.read(redisKey) != null) {
					log.info("获取首页banner活动(redis)");
					activityList = (List<Map<String, Object>>) redisSpringProxy.read(redisKey);
				} else {
					log.info("获取首页banner活动(db)");
					HajActivity activity = new HajActivity();
					activity.setAreaCode(Tools.getAreaCode(areaCode));
					activity.setActivityType(2);

					activityList = hajActivityService.listActivityByType(activity);
					redisSpringProxy.save(redisKey, activityList);
				}

				jsonMap.put("error", "0");
				jsonMap.put("msg", "成功");
				jsonMap.put("activityList", activityList);
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
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
