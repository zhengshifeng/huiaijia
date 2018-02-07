package com.flf.mapper;

import com.base.dao.BaseDao;
import com.flf.entity.HajActivityCommodity;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 *
 * <br>
 * <b>功能：</b>HajActivityCommodityDao<br>
 */
public interface HajActivityCommodityMapper extends BaseDao {

	List<HajActivityCommodity> listPage(HajActivityCommodity po);

	List<HashMap<String, Object>> list4app(HajActivityCommodity dto);

	/**
	 * 批量添加商品到活动中
	 * @param hajActivityCommodity
	 */
	void saveToCommodityActivity(HajActivityCommodity hajActivityCommodity);


	/**
	 * 批量移除活动中的商品
	 * @param hajActivityCommodity
	 */
	void deleteCommodityFromTheActivity(HajActivityCommodity hajActivityCommodity);

	/**
	 * 移除活动商品表的指定活动商品
	 * @return
	 */
	void deleteCommodityToActivity( Integer activityId2);
}
