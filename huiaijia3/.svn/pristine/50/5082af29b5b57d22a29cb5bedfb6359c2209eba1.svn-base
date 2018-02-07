package com.flf.mapper;

import com.base.dao.BaseDao;
import com.flf.entity.HajSupplyChain;
import com.flf.entity.HajSupplyChainVo;

import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>HajSupplyChainDao<br>
 */
public interface HajSupplyChainMapper extends BaseDao {

	List<HajSupplyChainVo> getSupplyList(HajSupplyChainVo vo) throws Exception;
	
	List<HajSupplyChainVo> listPage(HajSupplyChainVo vo) throws Exception;
	
	HajSupplyChainVo getSupplyChainVoById(Integer id) throws Exception;
	
	List<HajSupplyChain> getSupplyChainNames() throws Exception;
	
	HajSupplyChain getSupplyChainByName(String name) throws Exception;

	HajSupplyChain getByNo(String supplyNo);

	int getTotalByCloudsSupplierId(Integer CloudsSupplierId);

	/**
	 * 根据CloudsSupplierId修改供应商
	 * @param supplyChain
	 */
	void  updateByCloudsSupplierId(HajSupplyChain supplyChain);

}
