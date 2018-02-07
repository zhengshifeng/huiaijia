package com.flf.service.impl;

import com.base.service.BaseServiceImpl;
import com.flf.entity.HajActivity;
import com.flf.entity.HajCommodity;
import com.flf.mapper.HajActivityCommodityMapper;
import com.flf.mapper.HajActivityMapper;
import com.flf.mapper.HajCommodityMapper;
import com.flf.service.HajActivityService;
import com.flf.service.HajCommodityService;
import com.flf.service.RedisSpringProxy;
import com.flf.util.Tools;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 
 * <br>
 * <b>功能：</b>HajActivityService<br>
 */
@Service("hajActivityService")
public class HajActivityServiceImpl extends BaseServiceImpl implements HajActivityService {

	private final static Logger log = Logger.getLogger(HajActivityServiceImpl.class);

	@Autowired
	private HajActivityMapper dao;

	@Autowired
	private HajCommodityMapper commodityDao;

	@Autowired(required = false)
	private HajCommodityService hajCommodityService;

	@Autowired(required = false)
	private RedisSpringProxy redisSpringProxy;


	@Override
	public HajActivityMapper getDao() {
		return dao;
	}

	@Override
	public List<Map<String, Object>> listAllOrder(HajActivity activity) {
		return dao.listPageOrder(activity);
	}

	/**
	 * 添加活动表
	 */
	@Override
	public boolean insertActivity(HajActivity activity) {
		try {
			int id = this.add(activity);
			if (id > 0 && Tools.isNotEmpty(activity.getCommodityIds())) {
				// 给商品增加活动
				String[] cids = activity.getCommodityIds().split(",");
				modifyCommodityActivity(activity, cids);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	private void modifyCommodityActivity(HajActivity activity, String[] cids) throws Exception {
		if (cids.length > 0) {
			HajCommodity commodity;
			for (String commodityId : cids) {
				if (null != commodityId) {
					commodity = new HajCommodity();
					commodity.setId(Integer.parseInt(commodityId));
					commodity.setActivityId(activity.getId());
					commodity.setPromotionPrice(activity.getDiscountAmount());
					commodityDao.updateCommodityActivityId(commodity);
				}
			}
		}
	}

	/**
	 * 修改活动表
	 */
	@Override
	public boolean updateActivity(HajActivity activity) {
		if (activity.getId() != null && activity.getId() > 0) {
			try {
				this.update(activity);

				if (activity.getStatus() != null && activity.getStatus() == 0) {
					log.info("手动结束活动后，将商品从活动中移除");
					commodityDao.updateCommodityClearActivity(activity.getActivityName(), activity.getAreaCode());//

				}

				redisSpringProxy.deletePattern("promotionActivityCommodity");
				if (Tools.isNotEmpty(activity.getCommodityIds())) {
					// 清空商品活动
					commodityDao.deleteActivity(activity.getId());	//修改商品参与活动的id为0

					// 给商品增加活动
					String[] cids = activity.getCommodityIds().split(",");
					modifyCommodityActivity(activity, cids);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public boolean updateActivityStatus(int id, int status) {
		HajActivity activity;
		try {
			activity = this.queryById(id);
			activity.setStatus(status);
			int resultId = dao.update(activity);
			if (resultId > 0) {
				redisSpringProxy.deletePattern("promotionActivityCommodity");
				if (status == 0) {
					log.info("手动结束活动后，将商品从活动中移除");
					commodityDao.updateCommodityClearActivity(activity.getActivityName(), activity.getAreaCode());
				}
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<Map<String, Object>> getHajActivityCommotidyList(HajActivity activity) {
		return dao.getHajActivityCommotidyList(activity);
	}

	@Override
	public boolean deleteActivity(int activityId) {
		if (activityId > 0) {
			// 删除活动
			dao.delete(activityId);

			commodityDao.deleteCommotidyActivity(activityId);

			hajCommodityService.resetCommodityRedis();

			return true;
		}

		return false;
	}

	@Override
	public HajActivity queryActivityByName(String activityName, String areaCode) {
		return dao.queryActivityByName(activityName, areaCode);
	}

	@Override
	public List<Map<String, Object>> listActivityByType(HajActivity activity) {
		return dao.listActivityByType(activity);
	}

}
