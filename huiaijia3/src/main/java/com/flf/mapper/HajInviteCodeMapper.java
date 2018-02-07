package com.flf.mapper;

import java.util.HashMap;
import java.util.List;

import com.base.dao.BaseDao;
import com.flf.entity.invite.HajInviteCode;

/**
 *
 * <br>
 * <b>功能：</b>HajInviteCodeDao<br>
 */
public interface HajInviteCodeMapper extends BaseDao {

	List<HajInviteCode> listPage(HajInviteCode po);

	List<HashMap<String, Object>> list4app(HajInviteCode dto);

}
