package com.flf.service.invite;

import com.base.service.BaseService;
import com.flf.entity.invite.HajMemberInvitations;
import com.utils.Pager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * <br>
 * <b>功能：</b>HajMemberInvitationsService<br>
 */
public interface HajMemberInvitationsService extends BaseService {

	List<HajMemberInvitations> listPage(HajMemberInvitations po);

	List<HashMap<String, Object>> list4app(HajMemberInvitations dto);

	Pager<HajMemberInvitations> selectByListWithPage(Map<String, String> params, Integer currentPage, Integer pageSize);

	Pager<HajMemberInvitations> selectWithPage(String inviter, String inviteCode, Integer status, String ascOrDesc,
			Pager<HajMemberInvitations> pager);

	void updateByBatch(String[] idz, Integer opt);

	List<Map> queryByInviter(String inviter);
	
    Integer countByInviterSuccess(String inviter);
    
	void insertInvitations(String inviteCode,String invitee,String inviter) ;

	List<HajMemberInvitations> getMemberInvitationsList();

	void updateMemberInvitations(String invitee);

	Map<String,Object> queryTotalInvitePerson(HajMemberInvitations hajMemberInvitations);
}
