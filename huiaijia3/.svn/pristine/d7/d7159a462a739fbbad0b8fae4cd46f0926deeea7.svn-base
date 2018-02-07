package com.flf.controller.app;

import com.base.util.HttpUtil;
import com.flf.entity.HajSpecialTopic;
import com.flf.entity.HajSpecialTopicCommodity;
import com.flf.entity.HajSpecialTopicCommodityVo;
import com.flf.service.HajCommodityService;
import com.flf.service.HajSpecialTopicCommodityService;
import com.flf.service.HajSpecialTopicService;
import com.flf.service.RedisSpringProxy;
import com.flf.util.DateUtil;
import com.flf.util.JSONUtils;
import com.flf.util.Tools;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * <br>
 * <b>功能：</b>HajSpecialTopicCommodityAppController<br>
 * <br>
 */
@Controller
@RequestMapping(value = "/specialTopicCommodityApp")
public class HajSpecialTopicCommodityAppController {

	private final static Logger log = Logger.getLogger(HajSpecialTopicCommodityAppController.class);

	@Autowired(required = false)
	private HajSpecialTopicCommodityService service;

	@Autowired(required = false)
	private HajSpecialTopicService specialTopicService;

	@Autowired(required = false)
	private RedisSpringProxy redisSpringProxy;

	@Autowired(required = false)
	private HajCommodityService commodityService;

	@RequestMapping(value = "/list")
	public void list(HttpServletResponse response, Integer communityId, String areaCode) {
		HttpUtil.accessControlAllowOrigin(response);

		Map<String, Object> jsonMap = new HashMap<>();
		jsonMap.put("error", "0");
		jsonMap.put("msg", "成功");
		try {
			List<Map<String, Object>> specialTopicList = new ArrayList<>();
			Map<String, Object> specialTopicCommodity;

			areaCode = Tools.getAreaCode(areaCode);
			String redisKey = "specialTopicCommodityList_" + communityId + "_" + areaCode;
			if (null != redisSpringProxy.read(redisKey)) {
				log.info("读取专题及商品信息(redis)"+redisKey);
				specialTopicList = (List<Map<String, Object>>) redisSpringProxy.read(redisKey);
			} else {
				log.info("读取专题及商品信息(db)"+redisKey);
				HajSpecialTopic specialTopic = new HajSpecialTopic();
				specialTopic.setInvalid(0);
				specialTopic.setAreaCode(areaCode);

				// 获取所有启用的专题
				List<HajSpecialTopic> listPage = specialTopicService.listPage(specialTopic);

				HajSpecialTopicCommodity stc;

                /* 专题内的临时商品列表，因为后续还得根据商品ID单独封装一份数据（h5传给APP使用） */
				List<HajSpecialTopicCommodityVo> commodityList_tmp;

                /* 专题内的正式传给h5的商品列表 */
				List<HajSpecialTopicCommodityVo> commodityList;

                /* h5传给app的商品对象 */
				Map<String, Object> commodity2app;
				if (listPage.size() > 0) {
					HajSpecialTopic po = listPage.get(0);
					commodityList = new ArrayList<>();
					specialTopicCommodity = new HashMap<>();
					stc = new HajSpecialTopicCommodity();

					stc.setSpecialTopicId(po.getId());
					commodityList_tmp = service.getCommodityList(stc);

					for (HajSpecialTopicCommodityVo vo : commodityList_tmp) {
						commodity2app = commodityService.getById(vo.getCommodityId());
						if (commodity2app == null) {
							vo.setShelves(0);
						}
						vo.setCommodity2app(commodity2app);
						commodityList.add(vo);
					}

					specialTopicCommodity.put("specialTopic", po);
					specialTopicCommodity.put("commodityList", commodityList);
					specialTopicList.add(specialTopicCommodity);

					jsonMap.put("startTime", DateUtil.dateformat(po.getStartTime(), "yyyy/MM/dd HH:mm:ss"));
					jsonMap.put("endTime", DateUtil.dateformat(po.getEndTime(), "yyyy/MM/dd HH:mm:ss"));
				}

				redisSpringProxy.save(redisKey, specialTopicList);
			}
			jsonMap.put("currentTime", DateUtil.dateformat(new Date(), "yyyy/MM/dd HH:mm:ss"));
			jsonMap.put("specialTopicList", specialTopicList);
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
