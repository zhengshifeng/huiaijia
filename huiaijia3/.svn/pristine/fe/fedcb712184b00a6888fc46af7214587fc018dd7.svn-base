package com.flf.service;

import com.base.service.BaseService;
import com.flf.entity.HajCommodityVo;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by SevenWong on 2017/9/4 16:56
 */
public interface HajCommodityPriceService extends BaseService{


	XSSFWorkbook export(HajCommodityVo params);

	String batchUpdate(String filePath, HttpServletRequest request) throws Exception;
}
