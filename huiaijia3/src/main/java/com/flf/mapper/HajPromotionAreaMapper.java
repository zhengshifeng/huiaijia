package com.flf.mapper;

import com.base.criteria.Criteria;
import com.base.criteria.PromotionAreaCriteria;
import com.base.dao.BaseDao;
import com.flf.entity.HajPromotionArea;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * <br>
 * <b>功能：</b>HajPromotionAreaDao<br>
 */
public interface HajPromotionAreaMapper extends BaseDao {

	List<HajPromotionArea> listPage(HajPromotionArea vo);

	List<Map<String, Object>>  getPromotionAreaList(PromotionAreaCriteria criteria);

	HashMap<String, Object> getBannerByCategory(@Param("categoryId") Integer categoryId,
												@Param("areaCode") String areaCode);

	HashMap<String, Object> getCategoryBannerByCategory(@Param("areaType") Integer areaType,@Param("oneId") Integer oneId,@Param("areaCode") String areaCode);


	List<HajPromotionArea> queryByOtherList(Criteria criteria);

}
