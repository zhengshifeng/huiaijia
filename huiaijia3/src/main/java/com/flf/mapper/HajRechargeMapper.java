package com.flf.mapper;

import com.base.dao.BaseDao;
import com.flf.entity.HajRecharge;
import com.flf.entity.HajRechargeVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * <br>
 * <b>功能：</b>HajRechargeDao<br>
 */
public interface HajRechargeMapper extends BaseDao {

	int updateHajRecharge(@Param("id") int id, @Param("alipayTradeNo") String alipayTradeNo);

	Map<String, Object> getHajRechargeByPrepay_id(String prepay_id);

	List<HajRecharge> listPage(HajRechargeVo vo);

	HajRecharge getByOutTradeNo(String out_trade_no);

	Map<String, Object> queryTotalRecharge(HajRechargeVo vo);

	List<HajRecharge> queryRechargeListForRerund(HajRechargeVo vo);

	Map<String, Object> queryTotalRefund(HajRechargeVo vo);

}
