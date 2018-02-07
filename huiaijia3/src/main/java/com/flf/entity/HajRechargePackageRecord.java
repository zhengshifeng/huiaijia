package com.flf.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 充值套餐记录实体
 */
public class HajRechargePackageRecord implements Serializable {
	private Integer id;  //充值套餐记录Id
	private Integer userId ;//用户Id
	private String  phone ;//手机号码
	private String name;  //套餐名称
	private BigDecimal purchaseAmount; //购买金额
	private BigDecimal donationAmount;//赠送金额
	private BigDecimal accountAmount; //到账金额
	private String  beginTime; //开始时间
	private String endTime ; //结束时间
	private Date payTime;//支付时间
	private Integer payStatus;//支付状态   //付款状态（1未付款、2已付款、3交易关闭、4待退款，5已退款）
	private Integer payWay;//支付方式   //0:余额;1:微信;2:支付宝
	private String  payNumber;//支付流水号
	private BigDecimal refundAmount; //退款金额
	private Page page ;
	private String remark; //备注
	private BigDecimal refundableAmount ;//可退款金额
	private Integer packageId; //套餐Id
	private Integer  rechargePackageType;



	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPurchaseAmount() {
		return purchaseAmount;
	}

	public void setPurchaseAmount(BigDecimal purchaseAmount) {
		this.purchaseAmount = purchaseAmount;
	}

	public BigDecimal getDonationAmount() {
		return donationAmount;
	}

	public void setDonationAmount(BigDecimal donationAmount) {
		this.donationAmount = donationAmount;
	}

	public BigDecimal getAccountAmount() {
		return accountAmount;
	}

	public void setAccountAmount(BigDecimal accountAmount) {
		this.accountAmount = accountAmount;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	public Integer getPayWay() {
		return payWay;
	}

	public void setPayWay(Integer payWay) {
		this.payWay = payWay;
	}

	public String getPayNumber() {
		return payNumber;
	}

	public void setPayNumber(String payNumber) {
		this.payNumber = payNumber;
	}

	public BigDecimal getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigDecimal getRefundableAmount() {
		return refundableAmount;
	}

	public void setRefundableAmount(BigDecimal refundableAmount) {
		this.refundableAmount = refundableAmount;
	}

	public Integer getPackageId() {
		return packageId;
	}

	public void setPackageId(Integer packageId) {
		this.packageId = packageId;
	}

	public Integer getRechargePackageType() {
		return rechargePackageType;
	}

	public void setRechargePackageType(Integer rechargePackageType) {
		this.rechargePackageType = rechargePackageType;
	}
}
