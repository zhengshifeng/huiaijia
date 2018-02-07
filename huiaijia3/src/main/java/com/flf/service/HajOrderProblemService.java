package com.flf.service;

import com.base.service.BaseService;
import com.flf.entity.HajOrderProblem;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.List;
import java.util.Map;

/**
 *
 * <br>
 * <b>功能：</b>HajOrderProblemService<br>
 */
public interface HajOrderProblemService extends BaseService {

	List<HajOrderProblem> listPage(HajOrderProblem po);

	List<HajOrderProblem> listByOrderNo(String orderNo);

	List<Map<String, Object>> listPageOrderProblem(HajOrderProblem vo);

	XSSFWorkbook excelOrderProblem(HajOrderProblem orderVo);

	void deleteByOrderNo(String orderNo);

	void deleteByRefundNo(String refundNo);

}
