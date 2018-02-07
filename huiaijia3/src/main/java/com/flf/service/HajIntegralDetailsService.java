package com.flf.service;

import com.base.service.BaseService;
import com.flf.entity.HajIntegralDetails;

import java.util.HashMap;
import java.util.List;

/**
 *
 * <br>
 * <b>功能：</b>HajIntegralDetailsService<br>
 */
public interface HajIntegralDetailsService extends BaseService {

	List<HajIntegralDetails> listPage(HajIntegralDetails po);

	List<HashMap<String, Object>> listPage4app(HajIntegralDetails dto);

	/**
	 * 所有记录积分详情的，统一使用此功能（唯一入口）
	 */
	void saveDetail(HajIntegralDetails integralDetails) throws Exception;

}
