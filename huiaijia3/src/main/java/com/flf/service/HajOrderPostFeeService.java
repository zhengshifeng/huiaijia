package com.flf.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.base.service.BaseService;
import com.flf.entity.HajOrderPostFee;

/**
 * 
 * <br>
 * <b>功能：</b>HajOrderPostFeeService<br>
 */
public interface HajOrderPostFeeService extends BaseService {

	int saveOrderPostFee(Map<String, Object> map, String thisAttr);

	int saveCalOrderPostFee(Map<String, Object> map);

	Map<String, Object> getHajOrderPostFeeByUserId(Integer userId);

	int updateOrderPostFeeStatus(Integer orderPostFeeId);

	void updateOrderPostStatusByUserId(Integer id, int isPay);

	List<Map<String, Object>> listPageHajOrderPostFee(HajOrderPostFee postFee);

	XSSFWorkbook excelOrderPostFee(HajOrderPostFee postFee);

	void updateOrderPostRecharge(Integer rechargeId, int postFeeId);

	int updateOrderPostFeeByRechargeId(Integer rechargeId);


	Map<String, BigDecimal> getPostFeeSumByGroup(HajOrderPostFee postFee);
}
