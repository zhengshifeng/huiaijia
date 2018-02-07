package com.flf.service.impl;

import com.base.service.BaseServiceImpl;
import com.flf.controller.erp.entity.ERPSupplierVo;
import com.flf.entity.HajSupplyChain;
import com.flf.entity.HajSupplyChainVo;
import com.flf.mapper.HajAreasMapper;
import com.flf.mapper.HajSupplyChainMapper;
import com.flf.service.HajSupplyChainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>HajSupplyChainService<br>
 */
@Service("hajSupplyChainService")
				public class HajSupplyChainServiceImpl extends BaseServiceImpl implements HajSupplyChainService {

	@Autowired
	private HajSupplyChainMapper dao;

	@Autowired
	private HajAreasMapper areasDao;

	@Override
	public HajSupplyChainMapper getDao() {
		return dao;
	}

	@Override
	public List<HajSupplyChainVo> list(HajSupplyChainVo vo) throws Exception {
		List<HajSupplyChainVo> supplyChainList = null;
		supplyChainList = dao.listPage(vo);

		return supplyChainList;
	}

	@Override
	public HajSupplyChainVo getSupplyChainVoById(Integer id) throws Exception {
		return dao.getSupplyChainVoById(id);
	}

	@Override
	public List<HajSupplyChain> getSupplyChainNames() throws Exception {
		return dao.getSupplyChainNames();
	}

	@Override
	public HajSupplyChain getByNo(String supplyNo) {
		return dao.getByNo(supplyNo);
	}

	@Override
	public boolean saveSupplyChain(ERPSupplierVo vo) throws Exception {
		int i = dao.getTotalByCloudsSupplierId(vo.getFSUPPLIERID());
		if(i>0){
			return false;
		}else{
			//根据城市编号查询城市id
			String code = "";
			if("100.01".equals(vo.getFOrgCode())){
				code = "100002";
			}
			if("100.02".equals(vo.getFOrgCode())){
				code = "100010";
			}
			Integer areasId = areasDao.getTotalByCode(code);
			//新增供应商
			HajSupplyChain supplyChain = new HajSupplyChain();
			supplyChain.setCloudsSupplierId(vo.getFSUPPLIERID());
			supplyChain.setSupplyNo(vo.getFSupplyCode());
			supplyChain.setAreaId(areasId);
			if("100.01".equals(vo.getFOrgCode())){
				supplyChain.setName("SZ-"+vo.getFSupplyName());
			}
			if("100.02".equals(vo.getFOrgCode())){
				supplyChain.setName("GZ-"+vo.getFSupplyName());
			}
			dao.add(supplyChain);
			return true;
		}
	}

	@Override
	public void updateSupplyChainBycloudsSupplierId(ERPSupplierVo vo) throws Exception {
		//根据城市编号查询城市id
		String code = "";
		if("100.01".equals(vo.getFOrgCode())){
			code = "100002";
		}
		if("100.02".equals(vo.getFOrgCode())){
			code = "100010";
		}
		Integer areasId = areasDao.getTotalByCode(code);
		//修改供应商
		HajSupplyChain supplyChain = new HajSupplyChain();
		supplyChain.setCloudsSupplierId(vo.getFSUPPLIERID());
		supplyChain.setSupplyNo(vo.getFSupplyCode());
		supplyChain.setAreaId(areasId);
		if("100.01".equals(vo.getFOrgCode())){
			supplyChain.setName("SZ-"+vo.getFSupplyName());
		}
		if("100.02".equals(vo.getFOrgCode())){
 			supplyChain.setName("GZ-"+vo.getFSupplyName());
		}
		dao.updateByCloudsSupplierId(supplyChain);
	}


}
