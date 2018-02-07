package com.flf.entity.invite;

import java.util.Date;

import com.base.entity.BaseEntity;
import com.flf.entity.Page;
/**
 * 
 * <br>
 * <b>功能：</b>HajMemberInvitationsEntity<br>
 */
public class HajMemberInvitations extends BaseEntity {
	private static final long serialVersionUID = 1L;

	private Page page; // 用于分页
	

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}
	
	
	private  Integer currentPage;
	private  Integer pageSize;
	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	@Override
	public String toString() {
		return "HajMemberInvitations [page=" + page + ", inviteCode=" + inviteCode + ", id=" + id + ", inviter="
				+ inviter + ", invitee=" + invitee + ", status=" + status + ", optStatus=" + optStatus + ", createTime="
				+ createTime + ", updateTime=" + updateTime + ", currentPage=" + currentPage + ", pageSize=" + pageSize
				+ "]";
	}
	
}
