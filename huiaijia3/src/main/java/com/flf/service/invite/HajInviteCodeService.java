package com.flf.service.invite;

import java.util.HashMap;
import java.util.List;

import com.base.service.BaseService;
import com.flf.entity.invite.HajInviteCode;

/**
 *
 * <br>
 * <b>功能：</b>HajInviteCodeService<br>
 */
public interface HajInviteCodeService extends BaseService {

	List<HajInviteCode> listPage(HajInviteCode po);

	List<HashMap<String, Object>> list4app(HajInviteCode dto);
	
	public void insertInviteCode(String memberId,String mobilePhone) ;

}
