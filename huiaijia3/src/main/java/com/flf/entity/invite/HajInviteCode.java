package com.flf.entity.invite;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.flf.entity.Page;
import com.utils.CustomDateSerializer;
/**
 * 
 * <br>
 * <b>功能：</b>HajInviteCodeEntity<br>
 */
public class HajInviteCode{
	private static final long serialVersionUID = 1L;

	private Page page; // 用于分页
	
	@JsonSerialize(using = CustomDateSerializer.class)
	@JsonSerialize(using = CustomDateSerializer.class)

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}
}
