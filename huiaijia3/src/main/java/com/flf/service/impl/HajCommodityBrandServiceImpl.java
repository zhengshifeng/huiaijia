package com.flf.service.impl;

import com.base.service.BaseServiceImpl;
import com.flf.entity.HajCommodityBrand;
import com.flf.mapper.HajCommodityBrandMapper;
import com.flf.service.HajCommodityBrandService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>HajCommodityBrandService<br>
 */
@Service("hajCommodityBrandService")
public class HajCommodityBrandServiceImpl extends BaseServiceImpl implements HajCommodityBrandService {
	private final static Logger log = Logger.getLogger(HajCommodityBrandServiceImpl.class);

	@Autowired
	private HajCommodityBrandMapper dao;

	@Override
	public HajCommodityBrandMapper getDao() {
		return dao;
	}

	@Override
	public List<HajCommodityBrand> listPage(HajCommodityBrand po) {
		return dao.listPage(po);
	}

	@Override
	public List<HajCommodityBrand> getAllBrands() {
		return dao.getAllBrands();
	}

	@Override
	public HajCommodityBrand getByName(String name) {
		return dao.getByName(name);
	}

}
