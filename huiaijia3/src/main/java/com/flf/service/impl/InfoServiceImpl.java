package com.flf.service.impl;

import com.flf.entity.Info;
import com.flf.entity.Page;
import com.flf.mapper.InfoMapper;
import com.flf.service.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InfoServiceImpl implements InfoService{
	@Autowired
	private InfoMapper infoMapper;
	
	public List<Info> listPageInfo(Page page) {
		return infoMapper.listPageInfo(page);
	}
}
