package com.flf.entity;import com.base.entity.BaseEntity;/** * <br> * <b>功能：</b>HajIntegralDetailsEntity<br> */public class HajIntegralDetails extends BaseEntity {	private static final long serialVersionUID = 1L;	private Page page; // 用于分页	private Integer id;//	private Integer userId;//	private String detail;// 积分明细	private String remark;// 积分备注	private java.util.Date createTime;//	public Integer getId() {		return this.id;	}	public void setId(Integer id) {		this.id = id;	}	public Integer getUserId() {		return this.userId;	}	public void setUserId(Integer userId) {		this.userId = userId;	}	public String getDetail() {		return this.detail;	}	public void setDetail(String detail) {		this.detail = detail;	}	public String getRemark() {		return this.remark;	}	public void setRemark(String remark) {		this.remark = remark;	}	public java.util.Date getCreateTime() {		return this.createTime;	}	public void setCreateTime(java.util.Date createTime) {		this.createTime = createTime;	}	public Page getPage() {		return page;	}	public void setPage(Page page) {		this.page = page;	}}