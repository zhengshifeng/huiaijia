package com.flf.mapper;

import java.util.List;

import com.base.dao.BaseDao;
import com.flf.entity.HajFrontUserUpdateLog;

/**
 * 
 * <br>
 * <b>功能：</b>HajFrontUserUpdateLogDao<br>
 */
public interface HajFrontUserUpdateLogMapper extends BaseDao {

	List<HajFrontUserUpdateLog> listPage(HajFrontUserUpdateLog vo);

}
