package com.flf.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.service.BaseServiceImpl;
import com.flf.mapper.HajShoppingCartMapper;
import com.flf.service.HajShoppingCartService;

/**
 * 
 * <br>
 * <b>功能：</b>HajShoppingCartService<br>
 */
@Service("hajShoppingCartService")
public class HajShoppingCartServiceImpl extends BaseServiceImpl implements HajShoppingCartService {

	@Autowired
	private HajShoppingCartMapper dao;

	@Override
	public HajShoppingCartMapper getDao() {
		return dao;
	}

}
