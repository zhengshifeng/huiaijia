package com.flf.entity;

import com.base.entity.BaseEntity;

/**
 * 
 * <br>
 * <b>功能：</b>HajCustomerDeal<br>
 */
public class HajCustomerDeal extends BaseEntity {
	private static final long serialVersionUID = -8332674366811441490L;
	private java.lang.String username;// 用户名（昵称）
	private java.lang.String residential;// 小区
	private String ismember; // 会员状态
	private String beginTime;// 开始时间
	private String endTime;// 结束时间
	private Page page; // 分页

	public java.lang.String getUsername() {
		return username;
	}

	public void setUsername(java.lang.String username) {
		this.username = username;
	}

	public java.lang.String getResidential() {
		return residential;
	}

	public void setResidential(java.lang.String residential) {
		this.residential = residential;
	}

	public String getIsmember() {
		return ismember;
	}

	public void setIsmember(String ismember) {
		this.ismember = ismember;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

}
