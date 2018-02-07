package com.flf.entity;

import com.base.entity.BaseEntity;

import java.math.BigDecimal;

/**
 * <br>
 * <b>功能：</b>HajRechargeEntity<br>
 */
public class HajRecharge extends BaseEntity {
	private static final long serialVersionUID = 1L;

	private java.lang.Integer id;//
	private java.lang.Integer userId;// 用户表id（外键）
	private java.lang.Integer rechargeType;// 2支付宝 1微信
	private java.util.Date createTime;// 充值时间
	private java.lang.Integer rechargeStatus;// 1成功0失败
	private BigDecimal money;// 充值金额
	private java.lang.String bankNo;// 帐号（支付宝和微信帐号）
	private java.lang.String prepay_id;// 微信预付款id
	private Integer rechargeFor; // 0:余额充值; 1:一元购会员年费; 2:订单支付; 3:支付配送费
	private Integer notifyStatus; // 是否通知（微信支付成功通知）
	private java.lang.String remark3;//
	private BigDecimal refundNum;//退款金额
	private java.lang.String refundUpdateTime;//退款更新时间
	private java.lang.String isRefund;//1(已退款)0（未退款）
	private String alipayTradeNo; // 支付宝交易号，可用于无密退款
	private String paymentTarget; // 针对此次支付的目标对象(订单号或配送费ID)
	private Integer application;// 下单的应用(0, APP; 1, 微信小程序)
	private String openid;// 小程序openid
	/*****添加字段*********/
	private  Integer rechargePackageId;//充值套餐Id
	private  Integer rechargePackageType; //充值套餐类型  0代表默认充值， 1代表充值套餐

	private  BigDecimal  accountAmount; //到账金额

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

	public java.lang.Integer getRechargeType() {
		return this.rechargeType;
	}

	public void setRechargeType(java.lang.Integer rechargeType) {
		this.rechargeType = rechargeType;
	}

	public java.util.Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public java.lang.Integer getRechargeStatus() {
		return this.rechargeStatus;
	}

	public void setRechargeStatus(java.lang.Integer rechargeStatus) {
		this.rechargeStatus = rechargeStatus;
	}

	public BigDecimal getMoney() {
		return this.money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public java.lang.String getBankNo() {
		return bankNo;
	}

	public void setBankNo(java.lang.String bankNo) {
		this.bankNo = bankNo;
	}

	public java.lang.String getPrepay_id() {
		return prepay_id;
	}

	public void setPrepay_id(java.lang.String prepay_id) {
		this.prepay_id = prepay_id;
	}

	public Integer getRechargeFor() {
		return rechargeFor;
	}

	public void setRechargeFor(Integer rechargeFor) {
		this.rechargeFor = rechargeFor;
	}

	public Integer getNotifyStatus() {
		return notifyStatus;
	}

	public void setNotifyStatus(Integer notifyStatus) {
		this.notifyStatus = notifyStatus;
	}

	public java.lang.String getRemark3() {
		return this.remark3;
	}

	public void setRemark3(java.lang.String remark3) {
		this.remark3 = remark3;
	}

	public BigDecimal getRefundNum() {
		return refundNum;
	}

	public void setRefundNum(BigDecimal refundNum) {
		this.refundNum = refundNum;
	}

	public java.lang.String getRefundUpdateTime() {
		return refundUpdateTime;
	}

	public void setRefundUpdateTime(java.lang.String refundUpdateTime) {
		this.refundUpdateTime = refundUpdateTime;
	}

	public java.lang.String getIsRefund() {
		return isRefund;
	}

	public void setIsRefund(java.lang.String isRefund) {
		this.isRefund = isRefund;
	}

	public String getAlipayTradeNo() {
		return alipayTradeNo;
	}

	public void setAlipayTradeNo(String alipayTradeNo) {
		this.alipayTradeNo = alipayTradeNo;
	}

	public String getPaymentTarget() {
		return paymentTarget;
	}

	public void setPaymentTarget(String paymentTarget) {
		this.paymentTarget = paymentTarget;
	}

	public Integer getApplication() {
		return application;
	}

	public void setApplication(Integer application) {
		this.application = application;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public Integer getRechargePackageId() {
		return rechargePackageId;
	}

	public void setRechargePackageId(Integer rechargePackageId) {
		this.rechargePackageId = rechargePackageId;
	}

	public Integer getRechargePackageType() {
		return rechargePackageType;
	}

	public void setRechargePackageType(Integer rechargePackageType) {
		this.rechargePackageType = rechargePackageType;
	}

	public BigDecimal getAccountAmount() {
		return accountAmount;
	}

	public void setAccountAmount(BigDecimal accountAmount) {
		this.accountAmount = accountAmount;
	}
}
