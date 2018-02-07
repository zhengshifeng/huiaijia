package com.flf.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.base.dao.BaseDao;
import com.flf.entity.HajOrderPostFee;

/**
 * 
 * <br>
 * <b>功能：</b>HajOrderPostFeeDao<br>
 */
public interface HajOrderPostFeeMapper extends BaseDao {

	Map<String, Object> getHajOrderPostFeeByUserId(Integer userId);

	int updateOrderPostFee(HajOrderPostFee orderPostFee);

	void updateOrderPostStatusByUserId(HajOrderPostFee orderPostFee);

	List<Map<String, Object>> listPageHajOrderPostFee(HajOrderPostFee postFee);

	List<Map<String, Object>> excelOrderPostFee(HajOrderPostFee postFee);

	void updateOrderPostRecharge(@Param("rechargeId") Integer rechargeId, @Param("id") int id);

	HajOrderPostFee queryByrechargeId(Integer rechargeId);

	int getTopOrderPostfee(Integer userId);

	List<Map<String, Object>> getPostFeeSumByGroup(HajOrderPostFee postFee);

}
