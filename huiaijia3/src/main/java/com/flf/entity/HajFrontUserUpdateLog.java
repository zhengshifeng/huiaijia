package com.flf.entity;

import com.base.entity.BaseEntity;

/**
 * 
 * <br>
 * <b>功能：</b>HajFrontUserUpdateLogEntity<br>
 */
public class HajFrontUserUpdateLog extends BaseEntity {
	private static final long serialVersionUID = 2539072103990489837L;

	private java.lang.Integer id;//
	private java.lang.String record;//
	private java.util.Date createTime;//
	private java.lang.Integer frontUserId;//
	private java.lang.String operator;//

	// 其他参数
	private Page page;
	private String username;

	public java.lang.Integer getId() {
		return id;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	public java.lang.String getRecord() {
		return record;
	}

	public void setRecord(java.lang.String record) {
		this.record = record;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public java.lang.Integer getFrontUserId() {
		return frontUserId;
	}

	public void setFrontUserId(java.lang.Integer frontUserId) {
		this.frontUserId = frontUserId;
	}

	public java.lang.String getOperator() {
		return operator;
	}

	public void setOperator(java.lang.String operator) {
		this.operator = operator;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
