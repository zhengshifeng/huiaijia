package com.flf.service.invite.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.service.BaseServiceImpl;
import com.flf.entity.invite.HajInviteCode;
import com.flf.mapper.HajInviteCodeMapper;
import com.flf.service.invite.HajInviteCodeService;
import com.utils.IdGeneraterUtils;

/**
 * 
 * <br>
 * <b>功能：</b>HajInviteCodeService<br>
 */
@Service("hajInviteCodeService")
public class HajInviteCodeServiceImpl extends BaseServiceImpl implements HajInviteCodeService {
	private final static Logger log = Logger.getLogger(HajInviteCodeServiceImpl.class);
	
	@Autowired
	private HajInviteCodeMapper dao;
 
	@Override
	public HajInviteCodeMapper getDao() {
		return dao;
	}

	@Override
	public List<HajInviteCode> listPage(HajInviteCode po) {
		return dao.listPage(po);
	}

	@Override
	public List<HashMap<String, Object>> list4app(HajInviteCode dto) {
		return dao.list4app(dto);
	}
	
	@Override
	public void insertInviteCode(String memberId,String mobilePhone) {
		HajInviteCode code =new HajInviteCode();
		String ivCode = IdGeneraterUtils.generateInviteCode("IV", 5);
		code.setCreateTime(new Date());
		code.setInviteCode(ivCode);
		code.setMemberId(memberId);
		code.setMemberMobile(mobilePhone);
		code.setUpdateTime(new Date());
		code.setStatus(1);
		dao.add(code);
	}

}
