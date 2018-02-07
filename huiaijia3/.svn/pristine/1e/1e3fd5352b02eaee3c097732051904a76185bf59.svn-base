package com.flf.service;

import com.base.service.BaseService;
import com.flf.controller.erp.entity.ERPSupplierVo;
import com.flf.entity.HajSupplyChain;
import com.flf.entity.HajSupplyChainVo;

import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>HajSupplyChainService<br>
 */
public interface HajSupplyChainService extends BaseService {

	List<HajSupplyChainVo> list(HajSupplyChainVo vo) throws Exception;
	
	HajSupplyChainVo getSupplyChainVoById(Integer id) throws Exception;
	
	List<HajSupplyChain> getSupplyChainNames() throws Exception;

	HajSupplyChain getByNo(String supplyNo);

	/**
	 * 新增供应商
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	boolean saveSupplyChain(ERPSupplierVo vo)throws Exception;

	/**
	 * 修改供应商
	 * @param vo
	 * @throws Exception
	 */
	void updateSupplyChainBycloudsSupplierId(ERPSupplierVo vo)throws Exception;
}
