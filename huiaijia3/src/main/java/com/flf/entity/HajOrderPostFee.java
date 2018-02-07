package com.flf.entity;

import java.math.BigDecimal;

import com.base.entity.BaseEntity;

/**
 * 
 * <br>
 * <b>功能：</b>HajOrderPostFeeEntity<br>
 */
public class HajOrderPostFee extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private java.lang.Integer id;//
	private BigDecimal orderMoney;// 订单金额
	private BigDecimal postFee;// 订单运费
	private java.lang.Integer userId;// 用户金额
	private java.lang.Integer orderNum;// 订单数量
	private java.lang.Integer isPay;// 是否支付过运费（1是0否）
	private java.lang.String remark;// 备注
	private String payTime; // 支付时间

	private java.lang.Integer rechargeId;// 对应充值表ID

	private String mobilePhone;
	private String beginTime;// 创建开始时间
	private String endTime;// 创建结束时间

	private String payBeginTime;// 支付开始时间
	private String payEndTime;// 支付结束时间

	private String rechargeType;// 2支付宝 1微信 0余额

	private Page page; // 分页

	public java.lang.Integer getId() {
		return this.id;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	public BigDecimal getOrderMoney() {
		return this.orderMoney;
	}

	public void setOrderMoney(BigDecimal orderMoney) {
		this.orderMoney = orderMoney;
	}

	public BigDecimal getPostFee() {
		return this.postFee;
	}

	public void setPostFee(BigDecimal postFee) {
		this.postFee = postFee;
	}

	public java.lang.Integer getUserId() {
		return this.userId;
	}

	public void setUserId(java.lang.Integer userId) {
		this.userId = userId;
	}

	public java.lang.Integer getOrderNum() {
		return this.orderNum;
	}

	public void setOrderNum(java.lang.Integer orderNum) {
		this.orderNum = orderNum;
	}

	public java.lang.Integer getIsPay() {
		return this.isPay;
	}

	public void setIsPay(java.lang.Integer isPay) {
		this.isPay = isPay;
	}

	public java.lang.String getRemark() {
		return this.remark;
	}

	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}

	public java.lang.Integer getRechargeId() {
		return rechargeId;
	}

	public void setRechargeId(java.lang.Integer rechargeId) {
		this.rechargeId = rechargeId;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
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

	public String getPayBeginTime() {
		return payBeginTime;
	}

	public void setPayBeginTime(String payBeginTime) {
		this.payBeginTime = payBeginTime;
	}

	public String getPayEndTime() {
		return payEndTime;
	}

	public void setPayEndTime(String payEndTime) {
		this.payEndTime = payEndTime;
	}

	public String getRechargeType() {
		return rechargeType;
	}

	public void setRechargeType(String rechargeType) {
		this.rechargeType = rechargeType;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}

}
