package com.flf.mapper;

import com.base.dao.BaseDao;
import com.flf.entity.invite.HajMemberInvitations;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * <br>
 * <b>功能：</b>HajMemberInvitationsDao<br>
 */
public interface HajMemberInvitationsMapper extends BaseDao {

	List<HajMemberInvitations> listPage(HajMemberInvitations po);

	List<HashMap<String, Object>> list4app(HajMemberInvitations dto);

	List<Map> queryByInviter(@Param("inviter")String inviter);
	
	Integer countByInviterSuccess(@Param("inviter")String inviter);

	List<HajMemberInvitations> getMemberInvitationsList();

	void updateMemberInvitations(String invitee);

	Map<String,Object> queryTotalInvitePerson(HajMemberInvitations hajMemberInvitations);
}
