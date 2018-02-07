package com.flf.service.impl;

import com.base.service.BaseServiceImpl;
import com.flf.entity.HajCategory;
import com.flf.mapper.HajCategoryMapper;
import com.flf.service.HajCategoryService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>HajCategoryService<br>
 */
@Service("hajCategoryService")
public class HajCategoryServiceImpl extends BaseServiceImpl implements HajCategoryService {
	private final static Logger log = Logger.getLogger(HajCategoryServiceImpl.class);
	
	@Autowired
	private HajCategoryMapper dao;
 
	@Override
	public HajCategoryMapper getDao() {
		return dao;
	}

	@Override
	public List<HajCategory> listPage(HajCategory po) {
		return dao.listPage(po);
	}

	@Override
	public List<HajCategory> list4App(String areaCode, Integer communityId) {
		return dao.list4App(areaCode, communityId);
	}

}
