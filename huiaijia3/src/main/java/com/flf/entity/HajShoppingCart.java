package com.flf.entity;

import java.math.BigDecimal;

import com.base.entity.BaseEntity;

/**
 * 
 * <br>
 * <b>功能：</b>HajShoppingCartEntity<br>
 */
public class HajShoppingCart extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2733391580555631328L;
	private java.lang.Integer id;//
	private java.lang.String commodityName;// 商品名称
	private BigDecimal price;// 单价
	private java.lang.Integer number;// 数量
	private java.util.Date createTime;// 创建时间
	private java.lang.Integer userId;// 用户表id（外键）
	private java.lang.Integer status;// 状态是否删除（1是0否）
	private java.lang.String remark1;//
	private java.lang.String remark2;//
	private java.lang.String remark3;//

	public java.lang.Integer getId() {
		return this.id;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	public java.lang.String getCommodityName() {
		return this.commodityName;
	}

	public void setCommodityName(java.lang.String commodityName) {
		this.commodityName = commodityName;
	}

	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public java.lang.Integer getNumber() {
		return this.number;
	}

	public void setNumber(java.lang.Integer number) {
		this.number = number;
	}

	public java.util.Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public java.lang.Integer getUserId() {
		return this.userId;
	}

	public void setUserId(java.lang.Integer userId) {
		this.userId = userId;
	}

	public java.lang.Integer getStatus() {
		return this.status;
	}

	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}

	public java.lang.String getRemark1() {
		return this.remark1;
	}

	public void setRemark1(java.lang.String remark1) {
		this.remark1 = remark1;
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
}
