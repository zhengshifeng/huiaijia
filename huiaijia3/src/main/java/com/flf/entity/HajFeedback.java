package com.flf.entity;

import com.base.entity.BaseEntity;

/**
 * 
 * <br>
 * <b>功能：</b>HajFeedbackEntity<br>
 */
public class HajFeedback extends BaseEntity {
	private static final long serialVersionUID = 1L;
	private java.lang.Integer id;//
	private java.lang.String content;// 反馈内容
	private java.lang.String telPhone;// 反馈电话（联系方式）
	private java.lang.String createTime;// 反馈时间
	private java.lang.String quickComment;//
	private java.lang.String remark2;// 备注
	private java.lang.String remark3;//
	private Integer resolved;// 解决情况(0,未解决;1,已解决;2,已回复;3,不做处理)

	public java.lang.Integer getId() {
		return this.id;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	public java.lang.String getContent() {
		return this.content;
	}

	public void setContent(java.lang.String content) {
		this.content = content;
	}

	public java.lang.String getTelPhone() {
		return this.telPhone;
	}

	public void setTelPhone(java.lang.String telPhone) {
		this.telPhone = telPhone;
	}

	public java.lang.String getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(java.lang.String createTime) {
		this.createTime = createTime;
	}

	public java.lang.String getQuickComment() {
		return quickComment;
	}

	public void setQuickComment(java.lang.String quickComment) {
		this.quickComment = quickComment;
	}

	public java.lang.String getRemark2() {
		return this.remark2;
	}

	public void setRemark2(java.lang.String remark2) {
		this.remark2 = remark2;
	}

	public java.lang.String getRemark3() {
		return this.remark3;
	}

	public void setRemark3(java.lang.String remark3) {
		this.remark3 = remark3;
	}

	public Integer getResolved() {
		return resolved;
	}

	public void setResolved(Integer resolved) {
		this.resolved = resolved;
	}

}
