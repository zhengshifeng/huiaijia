package com.flf.mapper;

import com.base.dao.BaseDao;
import com.flf.entity.HajCommodityType;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * <br>
 * <b>功能：</b>HajCommodityTypeDao<br>
 */
public interface HajCommodityTypeMapper extends BaseDao {

	List<HajCommodityType> getCommodityTypeByParentId(int id);

	List<HajCommodityType> getCommodityTypeByParentIdApp(int id);

	List<Map<String, Object>> getCommodityTypeId(Integer id, Integer communityId);

	void destoryCommodityTypeId(Integer id);

	List<HajCommodityType> getBigTypeByattr(String commodityAttr);

	HajCommodityType getCommodityByTypeName(String typeName);

	List<Map<String, Object>> getCommodityTypeId(Map<String, Object> condition);

	/**
	 * 根据商品属性、大类、小类判断有无该分类
	 * 
	 * @author SevenWong<br>
	 * @date 2016年9月10日上午11:58:25
	 * @param commodityAttr
	 * @param parentType
	 * @param type
	 * @return
	 */
	HajCommodityType isTypeExist(@Param("commodityAttr") String commodityAttr, //
			@Param("parentType") String parentType, @Param("type") String type);

	int getTypeCount(HajCommodityType po);

	List<HajCommodityType> getParentTypeByAttr(HajCommodityType type);

	List<HajCommodityType> getTypeByPid(HajCommodityType type);

	List<HashMap<String, Object>> getCommodityByType(HajCommodityType type);

	List<HajCommodityType> getByCategoryId(@Param("categoryId") Integer categoryId);

	List<HajCommodityType> list(@Param("attr") String attr);

	List<HajCommodityType> listAll();

}
