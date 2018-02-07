package com.flf.service.invite.impl;

import com.base.criteria.Criteria;
import com.base.service.BaseServiceImpl;
import com.flf.entity.invite.HajMemberInvitations;
import com.flf.mapper.HajMemberInvitationsMapper;
import com.flf.service.invite.HajMemberInvitationsService;
import com.utils.Pager;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * <br>
 * <b>功能：</b>HajMemberInvitationsService<br>
 */
@Service("hajMemberInvitationsService")
public class HajMemberInvitationsServiceImpl extends BaseServiceImpl implements HajMemberInvitationsService {
	private final static Logger log = Logger.getLogger(HajMemberInvitationsServiceImpl.class);
	
	@Autowired
	private HajMemberInvitationsMapper dao;
 
	@Override
	public HajMemberInvitationsMapper getDao() {
		return dao;
	}

	@Override
	public List<HajMemberInvitations> listPage(HajMemberInvitations po) {
		return dao.listPage(po);
	}

	@Override
	public List<HashMap<String, Object>> list4app(HajMemberInvitations dto) {
		return dao.list4app(dto);
	}

	@Override
	public Pager<HajMemberInvitations> selectByListWithPage(Map params, Integer currentPage,
			Integer pageSize) {
		
		//2、查询总记录数
		Criteria criteria=new Criteria();
		criteria.setCondition(params);
		int total=dao.queryByCount(criteria);
		//3、查询数据
		Criteria c=new Criteria();
		c.setCondition(params);
		c.setCurrentPage(currentPage);
		c.setPageSize(pageSize);
		c.setTotalCount(total);
		List<HajMemberInvitations> list=dao.queryByList(c);
		
		Pager<HajMemberInvitations> pager = new Pager<>();
		pager.setCurrentPage(currentPage);
		pager.setPageSize(pageSize);
		pager.setRecordTotal(total);
		pager.setContent(list);
		return pager;
	}
	
	/**
	 * 分页查询
	 */
	@Override
	public Pager<HajMemberInvitations> selectWithPage(String inviter, String inviteCode, Integer status,
			String ascOrDesc, Pager<HajMemberInvitations> pager) {
		//1、查询总记录数
		Criteria criteria = new Criteria();
		HashMap<String, Object> params = new HashMap<>();
		if(StringUtils.isNotBlank(inviter)) {
			params.put("inviter", inviter);
		}
		if(StringUtils.isNotBlank(inviteCode)) {
			params.put("inviteCode", inviteCode);
		}
		if(null!=status) {
			params.put("status", status);
		}
		criteria.setCondition(params);
		int recordTotal=dao.queryByCount(criteria);
		//2、查询数据
		
		criteria.setCurrentPage(pager.getCurrentPage()==0?1:pager.getCurrentPage());
		criteria.setPageSize(pager.getPageSize());
		if(StringUtils.isNotBlank(ascOrDesc)) {
			criteria.setOrderByClause("create_time "+ascOrDesc);
		}else {
			criteria.setOrderByClause("create_time asc");
		}
		criteria.setCondition(params);
		List<HajMemberInvitations> list = dao.queryByList(criteria);
		//3、封装分页对象返回
		pager.setRecordTotal(recordTotal);
		pager.setContent(list);
		return pager;
	}

	@Override
	public void updateByBatch(String[] idz, Integer opt) {
		for (int i = 0; i < idz.length; i++) {
			HajMemberInvitations t = dao.queryById(idz[i]);
			t.setOptStatus(opt);
			dao.update(t);
		}
	}

	@Override
	public List<Map> queryByInviter(String inviter) {
		List<Map> list=dao.queryByInviter(inviter);
		return list;
	}
	
	@Override
	public Integer countByInviterSuccess(String inviter) {
		Integer cnt=dao.countByInviterSuccess(inviter);
		return cnt;
	}
	
	@Override
	public void insertInvitations(String inviteCode,String invitee,String inviter) {
		HajMemberInvitations hi = new HajMemberInvitations();
		hi.setInviteCode(inviteCode);
		hi.setInvitee(invitee);
		hi.setInviter(inviter);
		hi.setOptStatus(0);
		hi.setStatus(0);
		hi.setUpdateTime(new Date());
		hi.setCreateTime(new Date());
		dao.add(hi);
	}
	public List<HajMemberInvitations> getMemberInvitationsList() {
		return dao.getMemberInvitationsList();
	}
	public void updateMemberInvitations(String invitee) {
		dao.updateMemberInvitations(invitee);
	}

	@Override
	public Map<String, Object> queryTotalInvitePerson(HajMemberInvitations hajMemberInvitations) {
	return 	dao.queryTotalInvitePerson(hajMemberInvitations);
	}


}
