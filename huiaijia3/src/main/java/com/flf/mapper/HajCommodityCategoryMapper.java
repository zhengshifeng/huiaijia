package com.flf.mapper;

import com.base.dao.BaseDao;
import com.flf.entity.HajCommodityCategory;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 *
 * <br>
 * <b>功能：</b>HajCommodityCategoryDao<br>
 */
public interface HajCommodityCategoryMapper extends BaseDao {

	List<HajCommodityCategory> listPage(HajCommodityCategory po);

	List<HashMap<String, Object>> list4app(HajCommodityCategory dto);

	/**
	 * 添加商品到制定三级类目中,如果类目存在此商品则不做处理
	 * @param comcate
	 */
	void saveCommoditysToThreeCate(HajCommodityCategory comcate);


	/**
	 * 批量移除类目中的商品
	 * @param comcate
	 */
	void deleteCommodityFromCategory(HajCommodityCategory comcate);


	/**
	 * 根据三级类目id获取旗下商品列表
	 * @param threeId
	 * @return
	 */
	int getCommodityByThreeId(@Param(value = "threeId") Integer threeId);

}
