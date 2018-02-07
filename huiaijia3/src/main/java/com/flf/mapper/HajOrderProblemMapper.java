package com.flf.mapper;

import com.base.dao.BaseDao;
import com.flf.entity.HajOrderProblem;

import java.util.List;
import java.util.Map;

/**
 *
 * <br>
 * <b>功能：</b>HajOrderProblemDao<br>
 */
public interface HajOrderProblemMapper extends BaseDao {

	List<HajOrderProblem> listPage(HajOrderProblem po);

	List<HajOrderProblem> listByOrderNo(String orderNo);

	List<Map<String, Object>> listPageOrderProblem(HajOrderProblem vo);

	List<Map<String, Object>> excelOrderProblem(HajOrderProblem orderVo);

	void deleteByOrderNo(String orderNo);

	void deleteByRefundNo(String refundNo);

}
