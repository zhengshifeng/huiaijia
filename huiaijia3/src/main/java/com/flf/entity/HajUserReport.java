package com.flf.entity;

import com.base.entity.BaseEntity;

/**
 * 
 * <br>
 * <b>功能：</b>HajUserReportEntity<br>
 */
public class HajUserReport extends BaseEntity {
	private static final long serialVersionUID = -5211560676035998340L;
	private java.lang.Integer id;//
	private java.lang.Integer userId;// 用户ID
	private java.lang.String villageCode;// 小区名称
	private java.lang.String reportTime;// 上报时间
	private java.lang.String area;// 区域
	private java.lang.String pushStatus;// 推送状态

	public java.lang.String getPushStatus() {
		return pushStatus;
	}

	public void setPushStatus(java.lang.String pushStatus) {
		this.pushStatus = pushStatus;
	}

	public java.lang.String getArea() {
		return area;
	}

	public void setArea(java.lang.String area) {
		this.area = area;
	}

	public java.lang.Integer getId() {
		return this.id;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	public java.lang.Integer getUserId() {
		return this.userId;
	}

	public void setUserId(java.lang.Integer userId) {
		this.userId = userId;
	}

	public java.lang.String getVillageCode() {
		return this.villageCode;
	}

	public void setVillageCode(java.lang.String villageCode) {
		this.villageCode = villageCode;
	}

	public java.lang.String getReportTime() {
		return this.reportTime;
	}

	public void setReportTime(java.lang.String reportTime) {
		this.reportTime = reportTime;
	}
}
