package com.flf.service;

import com.base.service.BaseService;
import com.flf.entity.HajCouponInfo;
import com.flf.entity.HajCouponInfoVo;
import com.flf.entity.HajCouponUseInfoVo;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.List;

/**
 *
 * <br>
 * <b>功能：</b>HajCouponInfoService<br>
 */
public interface HajCouponInfoService extends BaseService {

	List<HajCouponInfo> listPage(HajCouponInfo po);

	HajCouponInfo getCouponInfoByNo(String couponNo);

	String updateCouponIssue(HajCouponInfoVo vo, HajCouponInfo couponInfo);

	List<HajCouponUseInfoVo> listPageCouponUseInfo(HajCouponUseInfoVo vo);

	List<HajCouponInfo> listTodayInvalidCoupon();

	Double queryRateById(Integer couponId);

	XSSFWorkbook export2excel(HajCouponInfo vo);

	List<HajCouponInfo> listByCouponIds(List<String> couponList);
}
