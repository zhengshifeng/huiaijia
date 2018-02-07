package com.flf.mapper;

import com.base.dao.BaseDao;
import com.flf.entity.HajCategoryThree;
import com.flf.entity.HajCategoryThreeVo;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 *
 * <br>
 * <b>功能：</b>HajCategoryThreeDao<br>
 */
public interface HajCategoryThreeMapper extends BaseDao {

	List<HajCategoryThree> listPage(HajCategoryThree po);

	List<HashMap<String, Object>> list4app(HajCategoryThree dto);

	//获取所有的类目对象
	List<HajCategoryThree> getThreeList();

	//通过一级类目获取二级类目
	List<HajCategoryThree> getTwoByparentId(Integer parentId);


	//获取一级类目
	List<HajCategoryThree> getOneCategory(@Param("oneId") Integer oneId);

	//获取二级类目
	List<HajCategoryThree> getTwoCategory(@Param("oneId") Integer oneId,@Param("twoId") Integer twoId);

	//获取三级类目
	List<HajCategoryThree> getThreeCategory(@Param("twoId") Integer twoId,@Param("threeId") Integer threeId);

	//获取二级类目列表
	List<HajCategoryThreeVo> getTwoCategoryByOneId(@Param("oneId") Integer oneId);


	//根据二级类目id获取其下三级类目
	List<HajCategoryThree> getThreeCategoryByTwoId(@Param("twoId") Integer twoId);

}
