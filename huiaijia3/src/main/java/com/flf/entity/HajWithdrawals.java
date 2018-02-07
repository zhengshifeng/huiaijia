package com.flf.entity;

import java.math.BigDecimal;

import com.base.entity.BaseEntity;

/**
 * 
 * <br>
 * <b>功能：</b>HajWithdrawalsEntity<br>
 */
public class HajWithdrawals extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private java.lang.Integer id;//
	private java.lang.Integer userId;// 用户编号
	private BigDecimal money;// 提现金额
	private java.lang.String createTime;// 提现时间
	private java.lang.Integer status;// 是否成功（1是0否）
	private java.lang.Integer isConfirm;// 财务是否确认（1是0否）
	private java.lang.String operationTime;// 操作时间
	private java.lang.String remark;// 备注

	private String mobilePhone;// 手机号码

	private Page page;// 分页

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

	public BigDecimal getMoney() {
		return this.money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public java.lang.String getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(java.lang.String createTime) {
		this.createTime = createTime;
	}

	public java.lang.Integer getStatus() {
		return this.status;
	}

	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}

	public java.lang.Integer getIsConfirm() {
		return this.isConfirm;
	}

	public void setIsConfirm(java.lang.Integer isConfirm) {
		this.isConfirm = isConfirm;
	}

	public java.lang.String getOperationTime() {
		return this.operationTime;
	}

	public void setOperationTime(java.lang.String operationTime) {
		this.operationTime = operationTime;
	}

	public java.lang.String getRemark() {
		return this.remark;
	}

	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

}
