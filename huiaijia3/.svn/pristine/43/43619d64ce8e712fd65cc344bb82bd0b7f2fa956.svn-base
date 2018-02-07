package com.flf.service;

import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.base.service.BaseService;
import com.flf.entity.HajSortingOrder;

/**
 * 
 * <br>
 * <b>功能：</b>HajSortingOrderService<br>
 */
public interface HajSortingOrderService extends BaseService {

	int saveSortingOrder(Map<String, Object> map);

	List<Map<String, Object>> listPageOrder(HajSortingOrder sorting);

	Map<String, Object> queryTotalSorting(HajSortingOrder sorting);

	XSSFWorkbook exportHajSortingOrder(HajSortingOrder purchase);

	XSSFWorkbook exportSortOrder(HajSortingOrder purchase);

	List<Map<String, Object>> listPageTodaySorting(HajSortingOrder sorting);

	Map<String, Object> queryTotalTodaySorting(HajSortingOrder sorting);

	XSSFWorkbook exportTodaySortOrder(HajSortingOrder purchase);

}
