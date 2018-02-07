package com.flf.mapper;

import com.base.dao.BaseDao;
import com.flf.entity.HajCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 * <br>
 * <b>功能：</b>HajCategoryDao<br>
 */
public interface HajCategoryMapper extends BaseDao {

	List<HajCategory> listPage(HajCategory po);

	List<HajCategory> list4App(@Param("areaCode") String areaCode, @Param("communityId") Integer communityId);

}
