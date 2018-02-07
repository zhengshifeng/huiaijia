package com.flf.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.service.BaseServiceImpl;
import com.flf.entity.HajFeedbackVo;
import com.flf.mapper.HajFeedbackMapper;
import com.flf.service.HajFeedbackService;

/**
 * 
 * <br>
 * <b>功能：</b>HajFeedbackService<br>
 */
@Service("hajFeedbackService")
public class HajFeedbackServiceImpl extends BaseServiceImpl implements HajFeedbackService {

	@Autowired
	private HajFeedbackMapper dao;

	@Override
	public HajFeedbackMapper getDao() {
		return dao;
	}

	@Override
	public List<HajFeedbackVo> listPage(HajFeedbackVo vo) {
		return dao.listPage(vo);
	}

}
