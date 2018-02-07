package com.flf.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.service.BaseServiceImpl;
import com.flf.mapper.HajOrderDetailsMapper;
import com.flf.service.HajOrderDetailsService;

/**
 * 
 * <br>
 * <b>功能：</b>HajOrderDetailsService<br>
 */
@Service("hajOrderDetailsService")
public class HajOrderDetailsServiceImpl extends BaseServiceImpl implements HajOrderDetailsService {

	@Autowired
	private HajOrderDetailsMapper dao;

	@Override
	public HajOrderDetailsMapper getDao() {
		return dao;
	}

	@Override
	public List<Map<String, Object>> listAllOrderDetails(int orderId) {
		return dao.listAllOrderDetails(orderId);
	}

}
