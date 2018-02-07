package com.flf.entity;

import com.base.entity.BaseEntity;

/**
 * <br>
 * <b>功能：</b>HajIntegralMallEntity<br>
 */
public class HajIntegralMall extends BaseEntity {
	private static final long serialVersionUID = 1L;

	private Page page; // 用于分页

	private java.lang.Integer id;//
	private java.lang.Integer commodityId;// 兑换的商品ID（优惠卷id或商品id）
	private java.lang.String commodityName;// 商品名称
	private java.lang.Integer commodityType;// 商品类型（1优惠卷2商品）
	private java.lang.String description;// 商品简介
	private java.lang.String remark;// 备注说明
	private java.lang.Integer total;// 发行量
	private java.lang.Integer integral;// 兑换所需的积分
	private java.lang.Integer exchangeLimit;// 兑换限制（1无限制2每人限制N张）
	private java.lang.Integer limitCount;// 每人限制的张数
	private java.lang.String areaCode;// 城市编号
	private java.lang.Integer hide;// 是否隐藏（1是0否）
	private java.lang.Integer sort;// 排序值
	private java.lang.String image;// 图片
	private java.util.Date createTime;// 创建时间

	public java.lang.Integer getId() {
		return this.id;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	public java.lang.Integer getCommodityId() {
		return this.commodityId;
	}

	public void setCommodityId(java.lang.Integer commodityId) {
		this.commodityId = commodityId;
	}

	public java.lang.String getCommodityName() {
		return this.commodityName;
	}

	public void setCommodityName(java.lang.String commodityName) {
		this.commodityName = commodityName;
	}

	public java.lang.Integer getCommodityType() {
		return this.commodityType;
	}

	public void setCommodityType(java.lang.Integer commodityType) {
		this.commodityType = commodityType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public java.lang.String getRemark() {
		return this.remark;
	}

	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}

	public java.lang.Integer getTotal() {
		return this.total;
	}

	public void setTotal(java.lang.Integer total) {
		this.total = total;
	}

	public java.lang.Integer getIntegral() {
		return this.integral;
	}

	public void setIntegral(java.lang.Integer integral) {
		this.integral = integral;
	}

	public java.lang.Integer getExchangeLimit() {
		return this.exchangeLimit;
	}

	public void setExchangeLimit(java.lang.Integer exchangeLimit) {
		this.exchangeLimit = exchangeLimit;
	}

	public java.lang.Integer getLimitCount() {
		return this.limitCount;
	}

	public void setLimitCount(java.lang.Integer limitCount) {
		this.limitCount = limitCount;
	}

	public java.lang.String getAreaCode() {
		return this.areaCode;
	}

	public void setAreaCode(java.lang.String areaCode) {
		this.areaCode = areaCode;
	}

	public java.lang.Integer getHide() {
		return this.hide;
	}

	public void setHide(java.lang.Integer hide) {
		this.hide = hide;
	}

	public java.lang.Integer getSort() {
		return this.sort;
	}

	public void setSort(java.lang.Integer sort) {
		this.sort = sort;
	}

	public java.lang.String getImage() {
		return this.image;
	}

	public void setImage(java.lang.String image) {
		this.image = image;
	}

	public java.util.Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}
}

