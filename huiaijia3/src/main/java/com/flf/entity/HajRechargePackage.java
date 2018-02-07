package com.flf.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 充值套餐实体
 */
public class HajRechargePackage implements Serializable {
	private Integer id;  //套餐Id
	private String name;  //套餐名称
	private BigDecimal purchaseAmount; //购买金额
	private BigDecimal donationAmount;//赠送金额
	private BigDecimal accountAmount; //到账金额
	private Date createTime; //创建时间
	private Integer status; //0代表有效 1代表无效
	private Integer  purchaseNumber; //购物份数
	private Integer sort ;
	private String  beginTime; //开始时间
	private String endTime ; //结束时间
	private Page page;// 分页
	private String remark; //备注
	private String internalRemark; //内部备注
	private String createUser; //创建人

	private Integer totalNumber;//统计购买份数
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getPurchaseNumber() {
		return purchaseNumber;
	}

	public void setPurchaseNumber(Integer purchaseNumber) {
		this.purchaseNumber = purchaseNumber;
	}


	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getInternalRemark() {
		return internalRemark;
	}

	public void setInternalRemark(String internalRemark) {
		this.internalRemark = internalRemark;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Integer getTotalNumber() {
		return totalNumber;
	}

	public void setTotalNumber(Integer totalNumber) {
		this.totalNumber = totalNumber;
	}
}
