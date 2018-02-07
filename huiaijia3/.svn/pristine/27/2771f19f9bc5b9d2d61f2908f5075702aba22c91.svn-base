package com.flf.service;

import com.base.criteria.Criteria;
import com.base.service.BaseService;
import com.flf.entity.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * <br>
 * <b>功能：</b>HajCategoryThreeService<br>
 */
public interface HajCategoryThreeService extends BaseService {

	List<HajCategoryThree> listPage(HajCategoryThree po);

	List<HashMap<String, Object>> list4app(HajCategoryThree dto);

	//获取所有的类目对象
	List<HajCategoryThree> getThreeList();

	//通过一级类目获取二级类目
	List<HajCategoryThree> getTwoByparentId(Integer parentId);

	//获取一级类目
	List<HajCategoryThree> getOneCategory(Integer oneId);

	//获取二级类目
	List<HajCategoryThree> getTwoCategory(Integer oneId,Integer twoId);

	//获取三级类目
	List<HajCategoryThree> getThreeCategory(Integer twoId,Integer threeId);

	//根据一级类目id获取旗下二级类目列表
	List<HajCategoryThreeVo> getTwoCategoryByOneId(Integer oneId);

	//根据二级类目id获取其下三级类目
	List<HajCategoryThree> getThreeCategoryByTwoId(Integer twoId);


	//查询三级类目下的商品列表
	List<Map<String, Object>> getCategoryListByThreeId(Criteria criteria);

	//根据三级类目id判断旗下是否有商品
	int getCommodityByThreeId(Integer threeId);


}
