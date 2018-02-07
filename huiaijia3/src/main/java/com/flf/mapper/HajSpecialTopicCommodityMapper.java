package com.flf.mapper;

import java.util.List;

import com.base.dao.BaseDao;
import com.flf.entity.HajSpecialTopicCommodity;
import com.flf.entity.HajSpecialTopicCommodityVo;

/**
 * 
 * <br>
 * <b>功能：</b>HajSpecialTopicCommodityDao<br>
 */
public interface HajSpecialTopicCommodityMapper extends BaseDao {

	List<HajSpecialTopicCommodityVo> getCommodityList(HajSpecialTopicCommodity po);

	/**
	 * 批量添加商品到专题中,已添加的商品不做处理
	 * @param hajSpecialTopic
	 */
	void saveToCommodityTopic(HajSpecialTopicCommodity hajSpecialTopic);

	/**
	 * 批量移除专题中的商品
	 * @param hajSpecialTopicCommodity
	 */
	void deleteCommodityFromTheTopic(HajSpecialTopicCommodity hajSpecialTopicCommodity);

}
