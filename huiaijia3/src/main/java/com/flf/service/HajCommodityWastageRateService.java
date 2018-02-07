package com.flf.service;

import java.util.HashMap;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.base.service.BaseService;
import com.flf.entity.HajCommodityWastageRateVO;

/**
 * 
 * <br>
 * <b>功能：</b>HajCommodityWastageRateService<br>
 */
public interface HajCommodityWastageRateService extends BaseService {

	List<HashMap<String, Object>> listPage(HajCommodityWastageRateVO vo);

	XSSFWorkbook export2excel(HajCommodityWastageRateVO vo);

}
