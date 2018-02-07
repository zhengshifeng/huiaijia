package com.flf.service;

import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.base.service.BaseService;
import com.flf.entity.HajWithdrawals;

/**
 * 
 * <br>
 * <b>功能：</b>HajWithdrawalsService<br>
 */
public interface HajWithdrawalsService extends BaseService {

	List<Map<String, Object>> listAllOrder(HajWithdrawals withdrawals);

	int updateWithdrawals(HajWithdrawals withdrawals);

	XSSFWorkbook exportWithdrawals(HajWithdrawals withdrawals);

}
