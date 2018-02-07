package com.flf.service.impl;

import com.base.service.BaseServiceImpl;
import com.flf.entity.HajSaleDept;
import com.flf.mapper.HajSaleDeptMapper;
import com.flf.service.HajSaleDeptService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>HajSaleDeptService<br>
 */
@Service("hajSaleDeptService")
public class HajSaleDeptServiceImpl extends BaseServiceImpl implements HajSaleDeptService {
	private final static Logger log = Logger.getLogger(HajSaleDeptServiceImpl.class);
	
	@Autowired
	private HajSaleDeptMapper dao;
 
	@Override
	public HajSaleDeptMapper getDao() {
		return dao;
	}

	@Override
	public List<HajSaleDept> listPage(HajSaleDept po) {
		return dao.listPage(po);
	}

}
