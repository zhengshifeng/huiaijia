package com.flf.mapper;

import com.base.dao.BaseDao;
import com.flf.entity.HajFrontUser;
import com.flf.entity.HajIntegralMall;
import com.flf.entity.HajIntegralMallVo;

import java.util.HashMap;
import java.util.List;

/**
 *
 * <br>
 * <b>功能：</b>HajIntegralMallDao<br>
 */
public interface HajIntegralMallMapper extends BaseDao {

	List<HajIntegralMallVo> listPage(HajIntegralMall po);

	List<HashMap<String, Object>> list4app(HajFrontUser user);

}
