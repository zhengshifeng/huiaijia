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
		private java.lang.String inviteCode;// 邀请码	private java.lang.Integer id;// 	private java.lang.String inviter;// 邀请人	private java.lang.String invitee;// 被邀请人	private java.lang.Integer status;// 状态 0未成功 1成功	private java.lang.Integer optStatus;// 操作状态 0未处理 1已处理	private Date createTime;// 邀请时间/创建时间	private Date updateTime;// 更新时间	public java.lang.String getInviteCode() {	    return this.inviteCode;	}	public void setInviteCode(java.lang.String inviteCode) {	    this.inviteCode=inviteCode;	}	public java.lang.Integer getId() {	    return this.id;	}	public void setId(java.lang.Integer id) {	    this.id=id;	}	public java.lang.String getInviter() {	    return this.inviter;	}	public void setInviter(java.lang.String inviter) {	    this.inviter=inviter;	}	public java.lang.String getInvitee() {	    return this.invitee;	}	public void setInvitee(java.lang.String invitee) {	    this.invitee=invitee;	}	public java.lang.Integer getStatus() {	    return this.status;	}	public void setStatus(java.lang.Integer status) {	    this.status=status;	}	public java.lang.Integer getOptStatus() {	    return this.optStatus;	}	public void setOptStatus(java.lang.Integer optStatus) {	    this.optStatus=optStatus;	}	public Date getCreateTime() {	    return this.createTime;	}	public void setCreateTime(Date createTime) {	    this.createTime=createTime;	}	public Date getUpdateTime() {	    return this.updateTime;	}	public void setUpdateTime(Date updateTime) {	    this.updateTime=updateTime;	}

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

