package com.flf.entity;

import com.base.entity.BaseEntity;
/**
 * 
 * <br>
 * <b>功能：</b>HajCommunityPersionCommentEntity<br>
 */
public class HajCommunityPersionComment extends BaseEntity {
	
	
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private java.lang.Integer id;//   
	private java.lang.String quickComment;//   快速评论
	private java.lang.String commentName;//   评论者
	
	
	public java.lang.String getCommentName() {
		return commentName;
	}
	public void setCommentName(java.lang.String commentName) {
		this.commentName = commentName;
	}
	public java.lang.String getQuickComment() {
		return quickComment;
	}
	public void setQuickComment(java.lang.String quickComment) {
		this.quickComment = quickComment;
	}
	public java.lang.Integer getId() {
}
